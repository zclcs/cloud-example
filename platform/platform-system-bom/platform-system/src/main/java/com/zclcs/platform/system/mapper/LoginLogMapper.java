package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.platform.system.api.entity.LoginLog;
import com.zclcs.platform.system.api.entity.vo.LoginLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 登录日志 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:57.150
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<LoginLogVo> findPageVo(BasePage<LoginLogVo> basePage, @Param(Constants.WRAPPER) Wrapper<LoginLogVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<LoginLogVo> findListVo(@Param(Constants.WRAPPER) Wrapper<LoginLogVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    LoginLogVo findOneVo(@Param(Constants.WRAPPER) Wrapper<LoginLogVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<LoginLogVo> ew);

}