package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.Log;
import com.zclcs.platform.system.api.entity.vo.LogVo;

import java.util.List;

/**
 * 用户操作日志 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
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
    Integer countLog(LogVo logVo);

    /**
     * 新增日志
     *
     * @param className
     * @param methodName
     * @param params
     * @param ip
     * @param operation
     * @param username
     * @param start
     */
//    @Async(MyConstant.ASYNC_POOL)
    void createLog(String className, String methodName, String params, String ip, String operation, String username, long start);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteLog(List<Long> ids);

}
