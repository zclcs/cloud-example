package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.bean.ao.BlackListAo;
import com.zclcs.platform.system.api.bean.cache.BlackListCacheBean;
import com.zclcs.platform.system.api.bean.entity.BlackList;
import com.zclcs.platform.system.api.bean.vo.BlackListVo;
import com.zclcs.platform.system.mapper.BlackListMapper;
import com.zclcs.platform.system.service.BlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 黑名单 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:40:14.628
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackList> implements BlackListService {

    private final RedisService redisService;
    private final Ip2regionSearcher ip2regionSearcher;
    private final ObjectMapper objectMapper;

    @Override
    public BasePage<BlackListVo> findBlackListPage(BasePageAo basePageAo, BlackListVo blackListVo) {
        BasePage<BlackListVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<BlackListVo> queryWrapper = getQueryWrapper(blackListVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<BlackListVo> findBlackListList(BlackListVo blackListVo) {
        QueryWrapper<BlackListVo> queryWrapper = getQueryWrapper(blackListVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public BlackListVo findBlackList(BlackListVo blackListVo) {
        QueryWrapper<BlackListVo> queryWrapper = getQueryWrapper(blackListVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countBlackList(BlackListVo blackListVo) {
        QueryWrapper<BlackListVo> queryWrapper = getQueryWrapper(blackListVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    @Override
    public void cacheAllBlackList() {
        List<BlackList> list = this.lambdaQuery().list();
        list.forEach(black -> {
            try {
                redisService.sSet(getCacheKey(black), objectMapper.writeValueAsString(BlackListCacheBean.convertToBlackListCacheBean(black)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private QueryWrapper<BlackListVo> getQueryWrapper(BlackListVo blackListVo) {
        QueryWrapper<BlackListVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeRightNotBlank(queryWrapper, "sbl.black_ip", blackListVo.getBlackIp());
        QueryWrapperUtil.likeRightNotBlank(queryWrapper, "sbl.request_uri", blackListVo.getRequestUri());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sbl.request_method", blackListVo.getRequestMethod());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sbl.black_status", blackListVo.getBlackStatus());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sbl.black_ip", blackListVo.getBlackIp());
        QueryWrapperUtil.inNotEmpty(queryWrapper, "sbl.black_id", blackListVo.getBlackIds());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlackList createBlackList(BlackListAo blackListAo) throws JsonProcessingException {
        BlackList blackList = new BlackList();
        BeanUtil.copyProperties(blackListAo, blackList);
        setBlackList(blackList);
        this.save(blackList);
        BlackListCacheBean blackListCacheBean = BlackListCacheBean.convertToBlackListCacheBean(blackList);
        redisService.sSet(getCacheKey(blackList), objectMapper.writeValueAsString(blackListCacheBean));
        return blackList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlackList updateBlackList(BlackListAo blackListAo) throws JsonProcessingException {
        BlackList old = this.lambdaQuery().eq(BlackList::getBlackId, blackListAo.getBlackId()).one();
        BlackList blackList = new BlackList();
        BeanUtil.copyProperties(blackListAo, blackList);
        setBlackList(blackList);
        this.updateById(blackList);
        String oldKey = getCacheKey(old);
        String newKey = getCacheKey(blackList);
        redisService.setRemove(oldKey, objectMapper.writeValueAsString(BlackListCacheBean.convertToBlackListCacheBean(old)));
        redisService.sSet(newKey, objectMapper.writeValueAsString(BlackListCacheBean.convertToBlackListCacheBean(blackList)));
        return blackList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlackList(List<Long> ids) throws JsonProcessingException {
        List<BlackList> list = this.lambdaQuery().in(BlackList::getBlackId, ids).list();
        this.removeByIds(ids);
        for (BlackList blackList : list) {
            redisService.sSet(getCacheKey(blackList), objectMapper.writeValueAsString(BlackListCacheBean.convertToBlackListCacheBean(blackList)));
        }
    }

    private void setBlackList(BlackList blackList) {
        if (StrUtil.isNotBlank(blackList.getBlackIp())) {
            blackList.setLocation(ip2regionSearcher.getAddress(blackList.getBlackIp()));
        } else {
            blackList.setLocation(null);
        }
        if (StrUtil.isBlank(blackList.getLimitFrom())) {
            blackList.setLimitFrom("00:00:00");
        }
        if (StrUtil.isBlank(blackList.getLimitTo())) {
            blackList.setLimitFrom("23:59:59");
        }
    }

    private String getCacheKey(BlackList blackList) {
        return StrUtil.isNotBlank(blackList.getBlackIp()) ?
                RouteEnhanceCacheUtil.getBlackListCacheKey(blackList.getBlackIp()) :
                RouteEnhanceCacheUtil.getBlackListCacheKey();
    }
}
