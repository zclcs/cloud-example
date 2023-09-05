package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.MenuAo;
import com.zclcs.platform.system.api.bean.cache.MenuCacheVo;
import com.zclcs.platform.system.api.bean.entity.Menu;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuTreeVo;
import com.zclcs.platform.system.api.bean.vo.MenuVo;

import java.util.List;

/**
 * 菜单 Service接口
 *
 * @author zclcs
 * @since 2023-09-01 20:05:00.313
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param menuVo     {@link MenuVo}
     * @return {@link MenuVo}
     */
    BasePage<MenuVo> findMenuPage(BasePageAo basePageAo, MenuVo menuVo);

    /**
     * 查询（所有）
     *
     * @param menuVo {@link MenuVo}
     * @return {@link MenuVo}
     */
    List<MenuVo> findMenuList(MenuVo menuVo);

    /**
     * 查询（单个）
     *
     * @param menuVo {@link MenuVo}
     * @return {@link MenuVo}
     */
    MenuVo findMenu(MenuVo menuVo);

    /**
     * 统计
     *
     * @param menuVo {@link MenuVo}
     * @return 统计值
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
     * @param menuAo {@link MenuAo}
     * @return {@link Menu}
     */
    Menu createMenu(MenuAo menuAo);

    /**
     * 修改
     *
     * @param menuAo {@link MenuAo}
     * @return {@link Menu}
     */
    Menu updateMenu(MenuAo menuAo);

    /**
     * 根据菜单编码新增或修改
     *
     * @param menuAo {@link MenuAo}
     * @return {@link Menu}
     */
    Menu createOrUpdateMenu(MenuAo menuAo);

    /**
     * 删除
     *
     * @param ids 表id集合
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
    List<MenuCacheVo> findUserMenus(String username);

    /**
     * 查询用户路由
     *
     * @param username 用户名
     * @return 路由
     */
    List<VueRouter<MenuVo>> findUserRouters(String username);

}
