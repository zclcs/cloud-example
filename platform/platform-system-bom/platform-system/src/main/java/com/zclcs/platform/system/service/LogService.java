package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.aop.ao.LogAo;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.entity.Log;
import com.zclcs.platform.system.api.bean.vo.LogVo;

import java.util.List;

/**
 * 用户操作日志 Service接口
 *
 * @author zclcs
 * @since 2023-01-10 10:40:01.346
 */
public interface LogService extends IService<Log> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param logVo      logVo
     * @return BasePage<LogVo>
     */
    BasePage<LogVo> findLogPage(BasePageAo basePageAo, LogVo logVo);

    /**
     * 查询（所有）
     *
     * @param logVo logVo
     * @return List<LogVo>
     */
    List<LogVo> findLogList(LogVo logVo);

    /**
     * 查询（单个）
     *
     * @param logVo logVo
     * @return LogVo
     */
    LogVo findLog(LogVo logVo);

    /**
     * 统计
     *
     * @param logVo logVo
     * @return LogVo
     */
    Long countLog(LogVo logVo);

    /**
     * 新增日志
     *
     * @param logAo
     */
    void createLog(LogAo logAo);

    /**
     * 批量新增日志
     *
     * @param logAos {@link LogAo}
     */
    void createLogBatch(List<LogAo> logAos);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteLog(List<Long> ids);

}
