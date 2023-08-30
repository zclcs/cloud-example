package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.LoginLogAo;
import com.zclcs.platform.system.api.bean.entity.LoginLog;
import com.zclcs.platform.system.api.bean.vo.LoginLogVo;

import java.util.List;

/**
 * 登录日志 Service接口
 *
 * @author zclcs
 * @since 2023-01-10 10:39:57.150
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param loginLogVo loginLogVo
     * @return BasePage<LoginLogVo>
     */
    BasePage<LoginLogVo> findLoginLogPage(BasePageAo basePageAo, LoginLogVo loginLogVo);

    /**
     * 查询（所有）
     *
     * @param loginLogVo loginLogVo
     * @return List<LoginLogVo>
     */
    List<LoginLogVo> findLoginLogList(LoginLogVo loginLogVo);

    /**
     * 查询（单个）
     *
     * @param loginLogVo loginLogVo
     * @return LoginLogVo
     */
    LoginLogVo findLoginLog(LoginLogVo loginLogVo);

    /**
     * 统计
     *
     * @param loginLogVo loginLogVo
     * @return LoginLogVo
     */
    Long countLoginLog(LoginLogVo loginLogVo);

    /**
     * 新增
     *
     * @param loginLogAo {@link LoginLogAo}
     */
    void createLoginLog(LoginLogAo loginLogAo);

    /**
     * 新增
     *
     * @param loginLogAos {@link LoginLogAo}
     */
    void createLoginLogBatch(List<LoginLogAo> loginLogAos);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteLoginLog(List<Long> ids);

}
