package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.exception.MyException;
import com.zclcs.common.core.utils.TreeUtil;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.entity.RoleMenu;
import com.zclcs.platform.system.api.entity.ao.MenuAo;
import com.zclcs.platform.system.api.entity.vo.MenuTreeVo;
import com.zclcs.platform.system.api.entity.vo.MenuVo;
import com.zclcs.platform.system.biz.mapper.MenuMapper;
import com.zclcs.platform.system.biz.service.MenuService;
import com.zclcs.platform.system.biz.service.RoleMenuService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
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

    @Override
    public BasePage<MenuVo> findMenuPage(BasePageAo basePageAo, MenuVo menuVo) {
        BasePage<MenuVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<MenuVo> findMenuList(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        queryWrapper.orderByAsc("tb.order_num");
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public MenuVo findMenu(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countMenu(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<MenuVo> getQueryWrapper(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "tb.menu_name", menuVo.getMenuName());
        QueryWrapperUtil.inNotEmpty(queryWrapper, "tb.menu_id", menuVo.getMenuIds());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "tb.type", menuVo.getType());
        QueryWrapperUtil.inNotEmpty(queryWrapper, "tb.type", menuVo.getTypes());
        return queryWrapper;
    }

    @Override
    public List<MenuTreeVo> findMenus(MenuVo menu) {
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
    @Transactional(rollbackFor = Exception.class)
    public Menu createMenu(MenuAo menuAo) {
        validateMenuCode(menuAo.getMenuCode(), menuAo.getMenuId());
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuAo, menu);
        setMenu(menu);
        this.save(menu);
        SystemCacheUtil.deleteMenuByMenuId(menu.getMenuId());
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
        SystemCacheUtil.deleteMenuByMenuId(menu.getMenuId());
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
        Object[] roleIds = roleMenuService.lambdaQuery()
                .in(RoleMenu::getMenuId, distinct).list().stream().map(RoleMenu::getRoleId).toList().toArray();
        this.removeByIds(distinct);
        SystemCacheUtil.deleteMenuByMenuIds(menuIds);
        roleMenuService.lambdaUpdate().in(RoleMenu::getMenuId, distinct).remove();
        if (ArrayUtil.isNotEmpty(roleIds)) {
            SystemCacheUtil.deleteMenuIdsByRoleIds(roleIds);
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
            menu.setParentCode(MyConstant.TOP_PARENT_CODE);

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
        List<Menu> list = this.lambdaQuery().eq(Menu::getParentCode, menu.getMenuCode()).list();
        if (CollUtil.isNotEmpty(list)) {
            for (Menu m : list) {
                allDeptId.add(m.getMenuId());
                getChild(allDeptId, m);
            }
        }
    }

    @Override
    public void validateMenuCode(String menuCode, Long menuId) {
        if (MyConstant.TOP_PARENT_CODE.equals(menuCode)) {
            throw new MyException("菜单编码输入非法值");
        }
        Menu one = this.lambdaQuery().eq(Menu::getMenuCode, menuCode).one();
        if (one != null && !one.getMenuId().equals(menuId)) {
            throw new MyException("菜单编码重复");
        }
    }
}
