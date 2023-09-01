package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.RateLimitLogAo;
import com.zclcs.platform.system.api.bean.entity.RateLimitLog;
import com.zclcs.platform.system.api.bean.vo.RateLimitLogVo;

import java.util.List;

/**
 * 限流拦截日志 Service接口
 *
 * @author zclcs
 * @since 2023-09-01 19:53:54.652
 */
public interface RateLimitLogService extends IService<RateLimitLog> {

    /**
     * 查询（分页）
     *
     * @param basePageAo     {@link BasePageAo}
     * @param rateLimitLogVo {@link RateLimitLogVo}
     * @return {@link RateLimitLogVo}
     */
    BasePage<RateLimitLogVo> findRateLimitLogPage(BasePageAo basePageAo, RateLimitLogVo rateLimitLogVo);

    /**
     * 查询（所有）
     *
     * @param rateLimitLogVo {@link RateLimitLogVo}
     * @return {@link RateLimitLogVo}
     */
    List<RateLimitLogVo> findRateLimitLogList(RateLimitLogVo rateLimitLogVo);

    /**
     * 查询（单个）
     *
     * @param rateLimitLogVo {@link RateLimitLogVo}
     * @return {@link RateLimitLogVo}
     */
    RateLimitLogVo findRateLimitLog(RateLimitLogVo rateLimitLogVo);

    /**
     * 统计
     *
     * @param rateLimitLogVo {@link RateLimitLogVo}
     * @return 统计值
     */
    Long countRateLimitLog(RateLimitLogVo rateLimitLogVo);

    /**
     * 新增
     *
     * @param rateLimitLogAo {@link RateLimitLogAo}
     * @return {@link RateLimitLog}
     */
    RateLimitLog createRateLimitLog(RateLimitLogAo rateLimitLogAo);

    /**
     * 批量新增
     *
     * @param rateLimitLogAos {@link RateLimitLogAo}
     */
    void createRateLimitLogBatch(List<RateLimitLogAo> rateLimitLogAos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteRateLimitLog(List<Long> ids);

}
