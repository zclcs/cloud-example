package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.utils.AddressUtil;
import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.BlackList;
import com.zclcs.platform.system.api.entity.ao.BlackListAo;
import com.zclcs.platform.system.api.entity.vo.BlackListVo;
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
            String key = StrUtil.isNotBlank(black.getBlackIp()) ?
                    RouteEnhanceCacheUtil.getBlackListCacheKey(black.getBlackIp()) :
                    RouteEnhanceCacheUtil.getBlackListCacheKey();
            this.setCacheBlackList(black);
            redisService.sSet(key, black);
        });
    }

    private QueryWrapper<BlackListVo> getQueryWrapper(BlackListVo blackListVo) {
        QueryWrapper<BlackListVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sbl.black_ip", blackListVo.getBlackIp());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sbl.request_uri", blackListVo.getRequestUri());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sbl.request_method", blackListVo.getRequestMethod());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sbl.black_status", blackListVo.getBlackStatus());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sbl.black_id", blackListVo.getBlackIp());
        QueryWrapperUtil.inNotEmpty(queryWrapper, "sbl.black_id", blackListVo.getBlackIds());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlackList createBlackList(BlackListAo blackListAo) {
        BlackList blackList = new BlackList();
        BeanUtil.copyProperties(blackListAo, blackList);
        setBlackList(blackList);
        this.save(blackList);
        setCacheBlackList(blackList);
        String key = StrUtil.isNotBlank(blackList.getBlackIp()) ?
                RouteEnhanceCacheUtil.getBlackListCacheKey(blackList.getBlackIp()) :
                RouteEnhanceCacheUtil.getBlackListCacheKey();
        redisService.sSet(key, blackList);
        return blackList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlackList updateBlackList(BlackListAo blackListAo) {
        BlackList blackList = new BlackList();
        BeanUtil.copyProperties(blackListAo, blackList);
        setBlackList(blackList);
        this.updateById(blackList);
        if (StrUtil.isNotBlank(blackList.getBlackIp())) {
            String cacheKey = RouteEnhanceCacheUtil.getBlackListCacheKey(blackList.getBlackIp());
            redisService.del(cacheKey);
            List<BlackList> list = this.lambdaQuery().eq(BlackList::getBlackId, blackList.getBlackId()).list();
            list.forEach(this::setCacheBlackList);
            redisService.sSet(cacheKey, list);
        } else {
            String cacheKey = RouteEnhanceCacheUtil.getBlackListCacheKey();
            redisService.del(cacheKey);
            List<BlackList> list = this.lambdaQuery().isNull(BlackList::getBlackId).or().eq(BlackList::getBlackId, "").list();
            list.forEach(this::setCacheBlackList);
            redisService.sSet(cacheKey, list);
        }
        return blackList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlackList(List<Long> ids) {
        List<BlackList> list = this.lambdaQuery().in(BlackList::getBlackId, ids).list();
        this.removeByIds(ids);
        for (BlackList blackList : list) {
            String key = StrUtil.isNotBlank(blackList.getBlackIp()) ?
                    RouteEnhanceCacheUtil.getBlackListCacheKey(blackList.getBlackIp()) :
                    RouteEnhanceCacheUtil.getBlackListCacheKey();
            setCacheBlackList(blackList);
            redisService.setRemove(key, blackList);
        }
    }

    private void setCacheBlackList(BlackList blackList) {
        blackList.setCreateAt(null);
        blackList.setUpdateAt(null);
        blackList.setLocation(null);
    }

    private void setBlackList(BlackList blackList) {
        if (StrUtil.isNotBlank(blackList.getBlackIp())) {
            blackList.setLocation(AddressUtil.getCityInfo(blackList.getBlackIp()));
        } else {
            blackList.setLocation(null);
        }
        if (StrUtil.isNotBlank(blackList.getLimitFrom()) && StrUtil.isNotBlank(blackList.getLimitTo())) {
            blackList.setLimitFrom(DateUtil.parse(blackList.getLimitFrom()).toString(DatePattern.NORM_TIME_PATTERN));
            blackList.setLimitTo(DateUtil.parse(blackList.getLimitTo()).toString(DatePattern.NORM_TIME_PATTERN));
        }
    }
}
