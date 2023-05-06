package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.platform.system.api.entity.UserRole;
import com.zclcs.platform.system.api.entity.vo.UserRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:38.682
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<UserRoleVo> findPageVo(BasePage<UserRoleVo> basePage, @Param(Constants.WRAPPER) Wrapper<UserRoleVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<UserRoleVo> findListVo(@Param(Constants.WRAPPER) Wrapper<UserRoleVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    UserRoleVo findOneVo(@Param(Constants.WRAPPER) Wrapper<UserRoleVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<UserRoleVo> ew);

}