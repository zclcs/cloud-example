package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.bean.ao.BlackListAo;
import com.zclcs.platform.system.api.bean.cache.BlackListCacheVo;
import com.zclcs.platform.system.api.bean.entity.BlackList;
import com.zclcs.platform.system.api.bean.vo.BlackListVo;
import com.zclcs.platform.system.mapper.BlackListMapper;
import com.zclcs.platform.system.service.BlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.BlackListTableDef.BLACK_LIST;

/**
 * 黑名单 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:40:14.628
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackList> implements BlackListService {

    private final RedisService redisService;
    private final Ip2regionSearcher ip2regionSearcher;
    private final ObjectMapper objectMapper;

    @Override
    public BasePage<BlackListVo> findBlackListPage(BasePageAo basePageAo, BlackListVo blackListVo) {
        QueryWrapper queryWrapper = getQueryWrapper(blackListVo);
        Page<BlackListVo> paginate = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, BlackListVo.class);
        return new BasePage<>(paginate);
    }

    @Override
    public List<BlackListVo> findBlackListList(BlackListVo blackListVo) {
        QueryWrapper queryWrapper = getQueryWrapper(blackListVo);
        return this.mapper.selectListByQueryAs(queryWrapper, BlackListVo.class);
    }

    @Override
    public BlackListVo findBlackList(BlackListVo blackListVo) {
        QueryWrapper queryWrapper = getQueryWrapper(blackListVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, BlackListVo.class);
    }

    @Override
    public Long countBlackList(BlackListVo blackListVo) {
        QueryWrapper queryWrapper = getQueryWrapper(blackListVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    @Override
    public void cacheAllBlackList() {
        List<BlackList> list = this.list();
        list.forEach(black -> {
            try {
                redisService.sSet(getCacheKey(black), objectMapper.writeValueAsString(BlackListCacheVo.convertToBlackListCacheBean(black)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private QueryWrapper getQueryWrapper(BlackListVo blackListVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        BLACK_LIST.BLACK_ID,
                        BLACK_LIST.BLACK_IP,
                        BLACK_LIST.REQUEST_URI,
                        BLACK_LIST.REQUEST_METHOD,
                        BLACK_LIST.LIMIT_FROM,
                        BLACK_LIST.LIMIT_TO,
                        BLACK_LIST.LOCATION,
                        BLACK_LIST.BLACK_STATUS,
                        BLACK_LIST.CREATE_AT,
                        BLACK_LIST.UPDATE_BY
                )
                .where(BLACK_LIST.BLACK_IP.like(blackListVo.getBlackIp(), If::hasText))
                .and(BLACK_LIST.REQUEST_URI.like(blackListVo.getRequestUri(), If::hasText))
                .and(BLACK_LIST.REQUEST_METHOD.eq(blackListVo.getRequestMethod(), If::hasText))
                .and(BLACK_LIST.BLACK_STATUS.eq(blackListVo.getBlackStatus(), If::hasText))
                .and(BLACK_LIST.BLACK_ID.eq(blackListVo.getBlackId()))
        ;
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlackList createBlackList(BlackListAo blackListAo) throws JsonProcessingException {
        BlackList blackList = new BlackList();
        BeanUtil.copyProperties(blackListAo, blackList);
        setBlackList(blackList);
        this.save(blackList);
        BlackListCacheVo blackListCacheVo = BlackListCacheVo.convertToBlackListCacheBean(blackList);
        redisService.sSet(getCacheKey(blackList), objectMapper.writeValueAsString(blackListCacheVo));
        return blackList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlackList updateBlackList(BlackListAo blackListAo) throws JsonProcessingException {
        BlackList old = this.getById(blackListAo.getBlackId());
        BlackList blackList = new BlackList();
        BeanUtil.copyProperties(blackListAo, blackList);
        setBlackList(blackList);
        this.updateById(blackList);
        String oldKey = getCacheKey(old);
        String newKey = getCacheKey(blackList);
        redisService.setRemove(oldKey, objectMapper.writeValueAsString(BlackListCacheVo.convertToBlackListCacheBean(old)));
        redisService.sSet(newKey, objectMapper.writeValueAsString(BlackListCacheVo.convertToBlackListCacheBean(blackList)));
        return blackList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlackList(List<Long> ids) throws JsonProcessingException {
        List<BlackList> list = this.listByIds(ids);
        this.removeByIds(ids);
        for (BlackList blackList : list) {
            redisService.sSet(getCacheKey(blackList), objectMapper.writeValueAsString(BlackListCacheVo.convertToBlackListCacheBean(blackList)));
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
