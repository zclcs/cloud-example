package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.utils.TreeUtil;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.entity.ao.MenuAo;
import com.zclcs.platform.system.api.entity.router.RouterMeta;
import com.zclcs.platform.system.api.entity.router.VueRouter;
import com.zclcs.platform.system.api.entity.vo.MenuTreeVo;
import com.zclcs.platform.system.api.entity.vo.MenuVo;
import com.zclcs.platform.system.api.utils.BaseRouterUtil;
import com.zclcs.platform.system.biz.mapper.MenuMapper;
import com.zclcs.platform.system.biz.service.MenuService;
import com.zclcs.platform.system.biz.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:18.238
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuService roleMenuService;
    private final RedisService redisService;

    @Override
    public BasePage<MenuVo> findMenuPage(BasePageAo basePageAo, MenuVo menuVo) {
        BasePage<MenuVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<MenuVo> findMenuList(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public MenuVo findMenu(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public BasePage<MenuVo> findUserMenuPage(BasePageAo basePageAo, MenuVo menuVo, String username) {
        BasePage<MenuVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.findUserMenuPageVo(basePage, queryWrapper, username);
    }

    @Override
    public List<MenuVo> findUserMenuList(MenuVo menuVo, String username) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        queryWrapper.orderByAsc("sm.order_num");
        return this.baseMapper.findUserMenuListVo(queryWrapper, username);
    }

    @Override
    public MenuVo findUserMenu(MenuVo menuVo, String username) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.findUserMenuOneVo(queryWrapper, username);
    }

    @Override
    public Integer countMenu(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<MenuVo> getQueryWrapper(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sm.menu_name", menuVo.getMenuName());
        QueryWrapperUtil.inNotEmpty(queryWrapper, "sm.menu_id", menuVo.getMenuIds());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sm.type", menuVo.getType());
        QueryWrapperUtil.inNotEmpty(queryWrapper, "sm.type", menuVo.getTypes());
        return queryWrapper;
    }

    @Override
    public List<MenuVo> findUserSystemMenus(String username) {
        QueryWrapper<MenuVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sm.order_num");
        return this.baseMapper.findUserMenuListVo(queryWrapper, username);
    }

    @Override
    public List<MenuTreeVo> findSystemMenus(MenuVo menu) {
        List<MenuVo> menus = findMenuList(menu);
        List<MenuTreeVo> trees = new ArrayList<>();
        buildTrees(trees, menus);

        if (StrUtil.equals(menu.getType(), MyConstant.TYPE_BUTTON) || StrUtil.isNotBlank(menu.getMenuName())) {
            return trees;
        } else {
            return (List<MenuTreeVo>) TreeUtil.build(trees);
        }
    }

    @Override
    public List<VueRouter<MenuVo>> findUserRouters(String username) {
        Object obj = redisService.get(RedisCachePrefixConstant.USER_ROUTES + username);
        if (obj == null) {
            synchronized (this) {
                // 再查一次，防止上个已经抢到锁的线程已经更新过了
                obj = redisService.get(RedisCachePrefixConstant.USER_ROUTES + username);
                if (obj != null) {
                    return (List<VueRouter<MenuVo>>) obj;
                }
                return cacheAndGetUserRouters(username);
            }
        }
        return (List<VueRouter<MenuVo>>) obj;
    }

    @Override
    public List<VueRouter<MenuVo>> cacheAndGetUserRouters(String username) {
        List<VueRouter<MenuVo>> routes = new ArrayList<>();
        List<MenuVo> userSystemMenus = this.findUserSystemMenus(username);
        userSystemMenus.forEach(menu -> {
            VueRouter<MenuVo> route = new VueRouter<>();
            route.setId(menu.getMenuId());
            route.setParentId(menu.getParentId());
            route.setPath(menu.getPath());
            route.setName(StrUtil.isNotBlank(menu.getKeepAliveName()) ? menu.getKeepAliveName() : menu.getMenuName());
            route.setComponent(menu.getComponent());
            route.setRedirect(menu.getRedirect());
            route.setMeta(new RouterMeta(
                    menu.getMenuName(),
                    menu.getIcon(),
                    menu.getHideMenu().equals(MyConstant.YES),
                    menu.getIgnoreKeepAlive().equals(MyConstant.YES),
                    menu.getHideBreadcrumb().equals(MyConstant.YES),
                    menu.getHideChildrenInMenu().equals(MyConstant.YES),
                    menu.getCurrentActiveMenu()));
            routes.add(route);
        });
        List<VueRouter<MenuVo>> vueRouters = BaseRouterUtil.buildVueRouter(routes);
        redisService.set(RedisCachePrefixConstant.USER_ROUTES + username, vueRouters);
        return vueRouters;
    }

    @Override
    public List<String> findUserPermissions(String username) {
        Object obj = redisService.get(RedisCachePrefixConstant.USER_PERMISSIONS + username);
        if (obj == null) {
            synchronized (this) {
                // 再查一次，防止上个已经抢到锁的线程已经更新过了
                obj = redisService.get(RedisCachePrefixConstant.USER_PERMISSIONS + username);
                if (obj != null) {
                    return (List<String>) obj;
                }
                return cacheAndGetUserPermissions(username);
            }
        }
        return (List<String>) obj;
    }

    @Override
    public List<String> cacheAndGetUserPermissions(String username) {
        List<String> userPermissions = this.baseMapper.findUserPermissions(username);
        redisService.set(RedisCachePrefixConstant.USER_PERMISSIONS + username, userPermissions);
        return userPermissions;
    }

    @Override
    public MenuVo findById(Long menuId) {
        Object obj = redisService.get(RedisCachePrefixConstant.MENU + menuId);
        if (obj == null) {
            synchronized (this) {
                // 再查一次，防止上个已经抢到锁的线程已经更新过了
                obj = redisService.get(RedisCachePrefixConstant.MENU + menuId);
                if (obj != null) {
                    return (MenuVo) obj;
                }
                return cacheAndGetById(menuId);
            }
        }
        return (MenuVo) obj;
    }

    @Override
    public MenuVo cacheAndGetById(Long menuId) {
        QueryWrapper<MenuVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sm.menu_id", menuId);
        MenuVo menuVo = this.baseMapper.findOneVo(queryWrapper);
        redisService.set(RedisCachePrefixConstant.MENU + menuId, menuVo);
        return menuVo;
    }

    @Override
    public void deleteCacheById(Long menuId) {
        redisService.del(RedisCachePrefixConstant.MENU + menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu createMenu(MenuAo menuAo) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuAo, menu);
        setMenu(menu);
        this.save(menu);
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu updateMenu(MenuAo menuAo) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuAo, menu);
        setMenu(menu);
        this.updateById(menu);
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
        ArrayList<Long> distinct = CollectionUtil.distinct(allMenuIds);
        removeByIds(distinct);
        roleMenuService.deleteRoleMenusByMenuId(allMenuIds);
    }

    private void buildTrees(List<MenuTreeVo> trees, List<MenuVo> menus) {
        menus.forEach(menu -> {
            MenuTreeVo tree = new MenuTreeVo();
            tree.setId(menu.getMenuId());
            tree.setParentId(menu.getParentId());
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
            trees.add(tree);
        });
    }

    private void setMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(MyConstant.TOP_MENU_ID);

            menu.setType(MyConstant.TYPE_DIR);
            menu.setComponent(MyConstant.LAYOUT);
        }
        if (MyConstant.TYPE_BUTTON.equals(menu.getType())) {
            menu.setPath(null);
            menu.setIcon(null);
            menu.setComponent(null);
            menu.setOrderNum(null);
        }
    }

    public List<Long> getChildMenuId(Long menuId) {
        List<Long> menuIds = new ArrayList<>();
        menuIds.add(menuId);
        getChild(menuIds, this.lambdaQuery().eq(Menu::getMenuId, menuId).one());
        return menuIds;
    }

    private void getChild(List<Long> allDeptId, Menu menu) {
        List<Menu> list = this.lambdaQuery().eq(Menu::getParentId, menu.getMenuId()).list();
        if (CollUtil.isNotEmpty(list)) {
            for (Menu m : list) {
                allDeptId.add(m.getMenuId());
                getChild(allDeptId, m);
            }
        }
    }
}
