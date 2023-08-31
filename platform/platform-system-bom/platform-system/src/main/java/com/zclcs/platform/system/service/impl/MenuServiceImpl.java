package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.utils.TreeUtil;
import com.zclcs.platform.system.api.bean.ao.MenuAo;
import com.zclcs.platform.system.api.bean.cache.MenuCacheVo;
import com.zclcs.platform.system.api.bean.entity.Menu;
import com.zclcs.platform.system.api.bean.entity.RoleMenu;
import com.zclcs.platform.system.api.bean.entity.User;
import com.zclcs.platform.system.api.bean.entity.UserRole;
import com.zclcs.platform.system.api.bean.router.RouterMeta;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuTreeVo;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.api.utils.BaseRouterUtil;
import com.zclcs.platform.system.mapper.MenuMapper;
import com.zclcs.platform.system.service.MenuService;
import com.zclcs.platform.system.service.RoleMenuService;
import com.zclcs.platform.system.service.UserRoleService;
import com.zclcs.platform.system.service.UserService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.zclcs.platform.system.api.bean.entity.table.MenuTableDef.MENU;
import static com.zclcs.platform.system.api.bean.entity.table.RoleMenuTableDef.ROLE_MENU;
import static com.zclcs.platform.system.api.bean.entity.table.RoleTableDef.ROLE;
import static com.zclcs.platform.system.api.bean.entity.table.UserRoleTableDef.USER_ROLE;
import static com.zclcs.platform.system.api.bean.entity.table.UserTableDef.USER;

/**
 * 菜单 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:18.238
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final UserService userService;

    @Override
    public BasePage<MenuVo> findMenuPage(BasePageAo basePageAo, MenuVo menuVo) {
        QueryWrapper queryWrapper = getQueryWrapper(menuVo);
        Page<MenuVo> menuVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, MenuVo.class);
        return new BasePage<>(menuVoPage);
    }

    @Override
    public List<MenuVo> findMenuList(MenuVo menuVo) {
        QueryWrapper queryWrapper = getQueryWrapper(menuVo);
        queryWrapper.orderBy(MENU.ORDER_NUM.asc());
        return this.mapper.selectListByQueryAs(queryWrapper, MenuVo.class);
    }

    @Override
    public MenuVo findMenu(MenuVo menuVo) {
        QueryWrapper queryWrapper = getQueryWrapper(menuVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, MenuVo.class);
    }

    @Override
    public Long countMenu(MenuVo menuVo) {
        QueryWrapper queryWrapper = getQueryWrapper(menuVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(MenuVo menuVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        MENU.MENU_ID,
                        MENU.PARENT_CODE,
                        MENU.MENU_NAME,
                        MENU.MENU_CODE,
                        MENU.KEEP_ALIVE_NAME,
                        MENU.PATH,
                        MENU.COMPONENT,
                        MENU.REDIRECT,
                        MENU.PERMS,
                        MENU.TYPE,
                        MENU.HIDE_MENU,
                        MENU.IGNORE_KEEP_ALIVE,
                        MENU.HIDE_BREADCRUMB,
                        MENU.HIDE_CHILDREN_IN_MENU,
                        MENU.CURRENT_ACTIVE_MENU,
                        MENU.ORDER_NUM,
                        MENU.CREATE_AT,
                        MENU.UPDATE_AT
                )
                .where(MENU.MENU_NAME.likeRight(menuVo.getMenuName(), If::hasText))
                .and(MENU.MENU_ID.in(menuVo.getMenuIds(), If::isNotEmpty))
                .and(MENU.TYPE.eq(menuVo.getType(), If::hasText))
                .and(MENU.TYPE.in(menuVo.getTypes(), If::isNotEmpty))
        ;
        return queryWrapper;
    }

    @Override
    public List<MenuTreeVo> findMenus(MenuVo menu) {
        List<MenuVo> menus = findMenuList(menu);
        List<MenuTreeVo> trees = new ArrayList<>();
        buildTrees(trees, menus);

        if (StrUtil.equals(menu.getType(), Dict.TYPE_BUTTON) || StrUtil.isNotBlank(menu.getMenuName())) {
            return trees;
        } else {
            return (List<MenuTreeVo>) TreeUtil.build(trees);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu createMenu(MenuAo menuAo) {
        validateMenuCode(menuAo.getMenuCode(), menuAo.getMenuId());
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuAo, menu);
        setMenu(menu);
        this.save(menu);
        Long menuId = menu.getMenuId();
        SystemCacheUtil.deleteMenuByMenuId(menuId);
        List<Long> roleIds = roleMenuService.queryChain()
                .where(ROLE_MENU.MENU_ID.eq(menuId)).list().stream().map(RoleMenu::getRoleId).toList();
        if (CollectionUtil.isNotEmpty(roleIds)) {
            List<Long> userIdList = userRoleService.queryChain()
                    .where(USER_ROLE.ROLE_ID.in(roleIds)).list().stream().map(UserRole::getUserId).toList();
            if (CollectionUtil.isNotEmpty(userIdList)) {
                Object[] usernames = userService.queryChain()
                        .where(USER.USER_ID.in(userIdList)).list().stream().map(User::getUsername).toList().toArray();
                SystemCacheUtil.deletePermissionsByUsernames(usernames);
                SystemCacheUtil.deleteRoutersByUsernames(usernames);
            }
        }
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu updateMenu(MenuAo menuAo) {
        Menu menu = new Menu();
        menu.setMenuCode(null);
        BeanUtil.copyProperties(menuAo, menu);
        setMenu(menu);
        this.updateById(menu);
        Long menuId = menu.getMenuId();
        SystemCacheUtil.deleteMenuByMenuId(menuId);
        List<Long> roleIds = roleMenuService.queryChain()
                .where(ROLE_MENU.MENU_ID.eq(menuId)).list().stream().map(RoleMenu::getRoleId).toList();
        if (CollectionUtil.isNotEmpty(roleIds)) {
            List<Long> userIdList = userRoleService.queryChain()
                    .where(USER_ROLE.ROLE_ID.in(roleIds)).list().stream().map(UserRole::getUserId).toList();
            if (CollectionUtil.isNotEmpty(userIdList)) {
                Object[] usernames = userService.queryChain()
                        .where(USER.USER_ID.in(userIdList)).list().stream().map(User::getUsername).toList().toArray();
                SystemCacheUtil.deletePermissionsByUsernames(usernames);
                SystemCacheUtil.deleteRoutersByUsernames(usernames);
            }
        }
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(List<Long> ids) {
        List<Long> allMenuIds = new ArrayList<>(ids);
        for (Long id : ids) {
            List<Long> childMenuIds = getChildMenuId(id);
            allMenuIds.addAll(childMenuIds);
        }
        List<Long> distinct = CollectionUtil.distinct(allMenuIds);
        Object[] menuIds = distinct.toArray();
        List<RoleMenu> roleIdList = roleMenuService.queryChain()
                .where(ROLE_MENU.MENU_ID.in(distinct)).list();
        Object[] roleIds = roleIdList.stream().map(RoleMenu::getRoleId).toList().toArray();
        Object[] usernames = new Object[0];
        if (CollectionUtil.isNotEmpty(roleIdList)) {
            List<Long> userIdList = userRoleService.queryChain()
                    .where(USER_ROLE.ROLE_ID.in(roleIds)).list().stream().map(UserRole::getUserId).toList();
            if (CollectionUtil.isNotEmpty(userIdList)) {
                usernames = userService.queryChain()
                        .where(USER.USER_ID.in(userIdList)).list().stream().map(User::getUsername).toList().toArray();
            }
        }
        this.removeByIds(distinct);
        SystemCacheUtil.deleteMenuByMenuIds(menuIds);
        roleMenuService.updateChain().where(ROLE_MENU.MENU_ID.in(distinct));
        if (ArrayUtil.isNotEmpty(roleIds)) {
            SystemCacheUtil.deleteMenuIdsByRoleIds(roleIds);
        }
        if (ArrayUtil.isNotEmpty(usernames)) {
            SystemCacheUtil.deletePermissionsByUsernames(usernames);
            SystemCacheUtil.deleteRoutersByUsernames(usernames);
        }
    }

    private void buildTrees(List<MenuTreeVo> trees, List<MenuVo> menus) {
        menus.forEach(menu -> {
            MenuTreeVo tree = new MenuTreeVo();
            tree.setId(menu.getMenuId());
            tree.setCode(menu.getMenuCode());
            tree.setParentCode(menu.getParentCode());
            tree.setLabel(menu.getMenuName());
            tree.setKeepAliveName(menu.getKeepAliveName());
            tree.setPath(menu.getPath());
            tree.setComponent(menu.getComponent());
            tree.setRedirect(menu.getRedirect());
            tree.setPerms(menu.getPerms());
            tree.setIcon(menu.getIcon());
            tree.setType(menu.getType());
            tree.setHideMenu(menu.getHideMenu());
            tree.setIgnoreKeepAlive(menu.getIgnoreKeepAlive());
            tree.setHideBreadcrumb(menu.getHideBreadcrumb());
            tree.setHideChildrenInMenu(menu.getHideChildrenInMenu());
            tree.setCurrentActiveMenu(menu.getCurrentActiveMenu());
            tree.setOrderNum(menu.getOrderNum());
            tree.setCreateAt(menu.getCreateAt());
            trees.add(tree);
        });
    }

    private void setMenu(Menu menu) {
        if (menu.getParentCode() == null) {
            menu.setParentCode(CommonCore.TOP_PARENT_CODE);

            menu.setType(Dict.TYPE_DIR);
            menu.setComponent(CommonCore.LAYOUT);
        }
        if (Dict.TYPE_BUTTON.equals(menu.getType())) {
            menu.setPath(null);
            menu.setIcon(null);
            menu.setComponent(null);
            menu.setOrderNum(null);
        }
    }

    public List<Long> getChildMenuId(Long menuId) {
        List<Long> menuIds = new ArrayList<>();
        menuIds.add(menuId);
        getChild(menuIds, this.queryChain().where(MENU.MENU_ID.eq(menuId)).one());
        return menuIds;
    }

    private void getChild(List<Long> allDeptId, Menu menu) {
        List<Menu> list = this.queryChain().where(MENU.PARENT_CODE.eq(menu.getMenuCode())).list();
        if (CollUtil.isNotEmpty(list)) {
            for (Menu m : list) {
                allDeptId.add(m.getMenuId());
                getChild(allDeptId, m);
            }
        }
    }

    @Override
    public void validateMenuCode(String menuCode, Long menuId) {
        if (CommonCore.TOP_PARENT_CODE.equals(menuCode)) {
            throw new MyException("菜单编码输入非法值");
        }
        Menu one = this.queryChain().where(MENU.MENU_CODE.eq(menuCode)).one();
        if (one != null && !one.getMenuId().equals(menuId)) {
            throw new MyException("菜单编码重复");
        }
    }

    @Override
    public List<String> findUserPermissions(String username) {
        List<MenuCacheVo> menus = this.findUserMenus(username);
        return menus.stream().filter(Objects::nonNull).map(MenuCacheVo::getPerms)
                .filter(StrUtil::isNotBlank).toList();
    }

    @Override
    public List<MenuCacheVo> findUserMenus(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        MENU.MENU_ID,
                        MENU.PARENT_CODE,
                        MENU.MENU_NAME,
                        MENU.MENU_CODE,
                        MENU.KEEP_ALIVE_NAME,
                        MENU.PATH,
                        MENU.COMPONENT,
                        MENU.REDIRECT,
                        MENU.PERMS,
                        MENU.TYPE,
                        MENU.HIDE_MENU,
                        MENU.IGNORE_KEEP_ALIVE,
                        MENU.HIDE_BREADCRUMB,
                        MENU.HIDE_CHILDREN_IN_MENU,
                        MENU.CURRENT_ACTIVE_MENU,
                        MENU.ORDER_NUM
                ).from(MENU)
                .innerJoin(ROLE_MENU).on(MENU.MENU_ID.eq(ROLE_MENU.MENU_ID))
                .innerJoin(ROLE).on(ROLE_MENU.ROLE_ID.eq(ROLE.ROLE_ID))
                .innerJoin(USER_ROLE).on(ROLE.ROLE_ID.eq(USER_ROLE.ROLE_ID))
                .innerJoin(USER).on(USER_ROLE.USER_ID.eq(USER.USER_ID))
                .where(USER.USERNAME.eq(username))
        ;
        List<MenuCacheVo> menuCacheVos = mapper.selectListByQueryAs(queryWrapper, MenuCacheVo.class);
        return menuCacheVos;
    }

    @Override
    public List<VueRouter<MenuVo>> findUserRouters(String username) {
        List<VueRouter<MenuVo>> routes = new ArrayList<>();
        List<MenuCacheVo> menus = this.findUserMenus(username);
        List<MenuCacheVo> userMenus = menus.stream().filter(Objects::nonNull)
                .filter(menu -> !menu.getType().equals(Dict.MENU_TYPE_1))
                .sorted(Comparator.comparing(MenuCacheVo::getOrderNum)).toList();
        userMenus.forEach(menu -> {
            VueRouter<MenuVo> route = new VueRouter<>();
            route.setId(menu.getMenuId());
            route.setCode(menu.getMenuCode());
            route.setParentCode(menu.getParentCode());
            route.setPath(menu.getPath());
            route.setName(StrUtil.isNotBlank(menu.getKeepAliveName()) ? menu.getKeepAliveName() : menu.getMenuName());
            route.setComponent(menu.getComponent());
            route.setRedirect(menu.getRedirect());
            route.setMeta(new RouterMeta(
                    menu.getMenuName(),
                    menu.getIcon(),
                    Dict.YES_NO_1.equals(menu.getHideMenu()),
                    Dict.YES_NO_1.equals(menu.getIgnoreKeepAlive()),
                    Dict.YES_NO_1.equals(menu.getHideBreadcrumb()),
                    Dict.YES_NO_1.equals(menu.getHideChildrenInMenu()),
                    menu.getCurrentActiveMenu()));
            routes.add(route);
        });
        return BaseRouterUtil.buildVueRouter(routes);
    }
}
