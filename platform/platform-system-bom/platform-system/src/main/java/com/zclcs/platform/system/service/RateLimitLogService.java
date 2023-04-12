package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.RateLimitLog;
import com.zclcs.platform.system.api.entity.ao.RateLimitLogAo;
import com.zclcs.platform.system.api.entity.vo.RateLimitLogVo;

import java.util.List;

/**
 * 限流拦截日志 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:53.040
 */
public interface RateLimitLogService extends IService<RateLimitLog> {

    /**
     * 查询（分页）
     *
     * @param basePageAo     basePageAo
     * @param rateLimitLogVo rateLimitLogVo
     * @return BasePage<RateLimitLogVo>
     */
    BasePage<RateLimitLogVo> findRateLimitLogPage(BasePageAo basePageAo, RateLimitLogVo rateLimitLogVo);

    /**
     * 查询（所有）
     *
     * @param rateLimitLogVo rateLimitLogVo
     * @return List<RateLimitLogVo>
     */
    List<RateLimitLogVo> findRateLimitLogList(RateLimitLogVo rateLimitLogVo);

    /**
     * 查询（单个）
     *
     * @param rateLimitLogVo rateLimitLogVo
     * @return RateLimitLogVo
     */
    RateLimitLogVo findRateLimitLog(RateLimitLogVo rateLimitLogVo);

    /**
     * 统计
     *
     * @param rateLimitLogVo rateLimitLogVo
     * @return RateLimitLogVo
     */
    Integer countRateLimitLog(RateLimitLogVo rateLimitLogVo);

    /**
     * 新增
     *
     * @param rateLimitLogAo rateLimitLogAo
     * @return RateLimitLog
     */
//    @Async(MyConstant.ASYNC_POOL)
    RateLimitLog createRateLimitLog(RateLimitLogAo rateLimitLogAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteRateLimitLog(List<Long> ids);

}
