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
 * @since 2023-09-01 19:55:02.695
 */
public interface LogService extends IService<Log> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param logVo      {@link LogVo}
     * @return {@link LogVo}
     */
    BasePage<LogVo> findLogPage(BasePageAo basePageAo, LogVo logVo);

    /**
     * 查询（所有）
     *
     * @param logVo {@link LogVo}
     * @return {@link LogVo}
     */
    List<LogVo> findLogList(LogVo logVo);

    /**
     * 查询（单个）
     *
     * @param logVo {@link LogVo}
     * @return {@link LogVo}
     */
    LogVo findLog(LogVo logVo);

    /**
     * 统计
     *
     * @param logVo {@link LogVo}
     * @return 统计值
     */
    Long countLog(LogVo logVo);

    /**
     * 新增
     *
     * @param logAo {@link LogAo}
     * @return {@link Log}
     */
    Log createLog(LogAo logAo);

    /**
     * 批量新增
     *
     * @param logAos {@link LogAo}
     * @return {@link Log}
     */
    void createLogBatch(List<LogAo> logAos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteLog(List<Long> ids);

}
