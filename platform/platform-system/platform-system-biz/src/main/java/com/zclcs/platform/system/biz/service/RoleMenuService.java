package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.RoleMenu;
import com.zclcs.platform.system.api.entity.ao.RoleMenuAo;
import com.zclcs.platform.system.api.entity.vo.RoleMenuVo;

import java.util.List;

/**
 * 角色菜单关联 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:23.376
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param roleMenuVo roleMenuVo
     * @return BasePage<RoleMenuVo>
     */
    BasePage<RoleMenuVo> findRoleMenuPage(BasePageAo basePageAo, RoleMenuVo roleMenuVo);

    /**
     * 查询（所有）
     *
     * @param roleMenuVo roleMenuVo
     * @return List<RoleMenuVo>
     */
    List<RoleMenuVo> findRoleMenuList(RoleMenuVo roleMenuVo);

    /**
     * 查询（单个）
     *
     * @param roleMenuVo roleMenuVo
     * @return RoleMenuVo
     */
    RoleMenuVo findRoleMenu(RoleMenuVo roleMenuVo);

    /**
     * 统计
     *
     * @param roleMenuVo roleMenuVo
     * @return RoleMenuVo
     */
    Integer countRoleMenu(RoleMenuVo roleMenuVo);

    /**
     * 新增
     *
     * @param roleMenuAo roleMenuAo
     * @return RoleMenu
     */
    RoleMenu createRoleMenu(RoleMenuAo roleMenuAo);

    /**
     * 修改
     *
     * @param roleMenuAo roleMenuAo
     * @return RoleMenu
     */
    RoleMenu updateRoleMenu(RoleMenuAo roleMenuAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteRoleMenu(List<Long> ids);

}
