package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.platform.system.api.entity.RoleMenu;
import com.zclcs.platform.system.api.entity.vo.RoleMenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单关联 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:23.376
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<RoleMenuVo> findPageVo(BasePage<RoleMenuVo> basePage, @Param(Constants.WRAPPER) Wrapper<RoleMenuVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<RoleMenuVo> findListVo(@Param(Constants.WRAPPER) Wrapper<RoleMenuVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    RoleMenuVo findOneVo(@Param(Constants.WRAPPER) Wrapper<RoleMenuVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<RoleMenuVo> ew);

}