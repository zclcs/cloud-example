package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.MenuAo;
import com.zclcs.platform.system.api.bean.cache.MenuCacheBean;
import com.zclcs.platform.system.api.bean.entity.Menu;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuTreeVo;
import com.zclcs.platform.system.api.bean.vo.MenuVo;

import java.util.List;

/**
 * 菜单 Service接口
 *
 * @author zclcs
 * @since 2023-01-10 10:39:18.238
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
    Long countMenu(MenuVo menuVo);

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

    /**
     * 查询用户权限
     *
     * @param username 用户名
     * @return 权限
     */
    List<String> findUserPermissions(String username);

    /**
     * 查询用户权限
     *
     * @param username 用户名
     * @return 权限
     */
    List<MenuCacheBean> findUserMenus(String username);

    /**
     * 查询用户路由
     *
     * @param username 用户名
     * @return 路由
     */
    List<VueRouter<MenuVo>> findUserRouters(String username);

}
