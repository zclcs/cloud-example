package com.zclcs.platform.system.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.platform.system.api.entity.Role;
import com.zclcs.platform.system.api.entity.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<RoleVo> findPageVo(BasePage<RoleVo> basePage, @Param(Constants.WRAPPER) Wrapper<RoleVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<RoleVo> findListVo(@Param(Constants.WRAPPER) Wrapper<RoleVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    RoleVo findOneVo(@Param(Constants.WRAPPER) Wrapper<RoleVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<RoleVo> ew);

    /**
     * 通过角色id获取账号
     *
     * @param roleId 角色id
     * @return 账号集合
     */
    List<String> selectUsernamesByRoleId(Long roleId);

}