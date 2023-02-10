package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.LoginLog;
import com.zclcs.platform.system.api.entity.ao.LoginLogAo;
import com.zclcs.platform.system.api.entity.vo.LoginLogVo;

import java.util.List;

/**
 * 登录日志 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:57.150
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
    Integer countLoginLog(LoginLogVo loginLogVo);

    /**
     * 新增
     *
     * @param loginLogAo loginLogAo
     */
//    @Async(MyConstant.ASYNC_POOL)
    void createLoginLog(LoginLogAo loginLogAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteLoginLog(List<Long> ids);

}
