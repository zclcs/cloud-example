package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.entity.ao.MenuAo;
import com.zclcs.platform.system.api.entity.vo.MenuTreeVo;
import com.zclcs.platform.system.api.entity.vo.MenuVo;

import java.util.List;

/**
 * 菜单 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:18.238
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param menuVo     menuVo
     * @return BasePage<MenuVo>
     */
    BasePage<MenuVo> findMenuPage(BasePageAo basePageAo, MenuVo menuVo);

    /**
     * 查询（所有）
     *
     * @param menuVo menuVo
     * @return List<MenuVo>
     */
    List<MenuVo> findMenuList(MenuVo menuVo);

    /**
     * 查询（单个）
     *
     * @param menuVo menuVo
     * @return MenuVo
     */
    MenuVo findMenu(MenuVo menuVo);

    /**
     * 统计
     *
     * @param menuVo menuVo
     * @return MenuVo
     */
    Integer countMenu(MenuVo menuVo);

    /**
     * 获取用户菜单
     *
     * @param menu menu
     * @return 用户菜单
     */
    List<MenuTreeVo> findMenus(MenuVo menu);

    /**
     * 新增
     *
     * @param menuAo menuAo
     * @return Menu
     */
    Menu createMenu(MenuAo menuAo);

    /**
     * 修改
     *
     * @param menuAo menuAo
     * @return Menu
     */
    Menu updateMenu(MenuAo menuAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteMenu(List<Long> ids);

    /**
     * 验证菜单编码
     *
     * @param menuCode 菜单编码
     * @param menuId   菜单id
     */
    void validateMenuCode(String menuCode, Long menuId);

}
