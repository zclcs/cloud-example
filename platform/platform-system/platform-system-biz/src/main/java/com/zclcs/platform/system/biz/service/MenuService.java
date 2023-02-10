package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.entity.ao.MenuAo;
import com.zclcs.platform.system.api.entity.router.VueRouter;
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
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param menuVo     menuVo
     * @param username
     * @return BasePage<MenuVo>
     */
    BasePage<MenuVo> findUserMenuPage(BasePageAo basePageAo, MenuVo menuVo, String username);

    /**
     * 查询（所有）
     *
     * @param menuVo   menuVo
     * @param username
     * @return List<MenuVo>
     */
    List<MenuVo> findUserMenuList(MenuVo menuVo, String username);

    /**
     * 查询（单个）
     *
     * @param menuVo   menuVo
     * @param username
     * @return MenuVo
     */
    MenuVo findUserMenu(MenuVo menuVo, String username);

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
     * @param username 用户名
     * @return 用户菜单
     */
    List<MenuVo> findUserSystemMenus(String username);

    /**
     * 获取用户菜单
     *
     * @param menu menu
     * @return 用户菜单
     */
    List<MenuTreeVo> findSystemMenus(MenuVo menu);

    /**
     * 获取用户路由
     *
     * @param username 用户名
     * @return 用户路由
     */
    List<VueRouter<MenuVo>> findUserRouters(String username);

    /**
     * 缓存并获取用户路由
     *
     * @param username 用户名
     * @return 用户路由
     */
    List<VueRouter<MenuVo>> cacheAndGetUserRouters(String username);

    /**
     * 获取用户权限
     *
     * @param username 用户名
     * @return 用户权限
     */
    List<String> findUserPermissions(String username);

    /**
     * 缓存并获取用户权限
     *
     * @param username 用户名
     * @return 用户权限
     */
    List<String> cacheAndGetUserPermissions(String username);

    /**
     * 通过id获取菜单
     *
     * @param menuId 菜单id
     * @return 菜单
     */
    MenuVo findById(Long menuId);

    /**
     * 通过id缓存菜单
     *
     * @param menuId 菜单id
     * @return 菜单
     */
    MenuVo cacheAndGetById(Long menuId);

    /**
     * 通过id删除菜单缓存
     *
     * @param menuId 菜单id
     */
    void deleteCacheById(Long menuId);

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

}
