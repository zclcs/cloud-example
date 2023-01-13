package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.Log;
import com.zclcs.platform.system.api.entity.ao.LogAo;
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
     * 新增
     *
     * @param logAo logAo
     * @return Log
     */
    Log createLog(LogAo logAo);

    /**
     * 修改
     *
     * @param logAo logAo
     * @return Log
     */
    Log updateLog(LogAo logAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteLog(List<Long> ids);

}
