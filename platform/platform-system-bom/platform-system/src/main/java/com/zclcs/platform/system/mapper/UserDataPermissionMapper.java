package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.platform.system.api.entity.UserDataPermission;
import com.zclcs.platform.system.api.entity.vo.UserDataPermissionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据权限关联 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
public interface UserDataPermissionMapper extends BaseMapper<UserDataPermission> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<UserDataPermissionVo> findPageVo(BasePage<UserDataPermissionVo> basePage, @Param(Constants.WRAPPER) Wrapper<UserDataPermissionVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<UserDataPermissionVo> findListVo(@Param(Constants.WRAPPER) Wrapper<UserDataPermissionVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    UserDataPermissionVo findOneVo(@Param(Constants.WRAPPER) Wrapper<UserDataPermissionVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<UserDataPermissionVo> ew);

}