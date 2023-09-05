package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.collection.CollectionUtil;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.ao.MenuAo;
import com.zclcs.platform.system.api.bean.cache.MenuCacheVo;
import com.zclcs.platform.system.api.bean.entity.Menu;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuTreeVo;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.service.MenuService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zclcs.platform.system.api.bean.entity.table.MenuTableDef.MENU;

/**
 * 菜单
 *
 * @author zclcs
 * @since 2023-01-10 10:39:18.238
 */
@Slf4j
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 菜单查询（分页）
     * 权限: menu:view
     *
     * @see MenuService#findMenuPage(BasePageAo, MenuVo)
     */
    @GetMapping
    @SaCheckPermission("menu:view")
    public BaseRsp<BasePage<MenuVo>> findMenuPage(@Validated BasePageAo basePageAo, @Validated MenuVo menuVo) {
        BasePage<MenuVo> page = this.menuService.findMenuPage(basePageAo, menuVo);
        return RspUtil.data(page);
    }

    /**
     * 菜单查询（集合）
     * 权限: menu:view
     *
     * @see MenuService#findMenuList(MenuVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("menu:view")
    public BaseRsp<List<MenuVo>> findMenuList(@Validated MenuVo menuVo) {
        List<MenuVo> list = this.menuService.findMenuList(menuVo);
        return RspUtil.data(list);
    }

    /**
     * 菜单查询（单个）
     * 权限: menu:view
     *
     * @see MenuService#findMenu(MenuVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("menu:view")
    public BaseRsp<MenuVo> findMenu(@Validated MenuVo menuVo) {
        MenuVo menu = this.menuService.findMenu(menuVo);
        return RspUtil.data(menu);
    }

    /**
     * 菜单树
     * 权限: role:view 或者 menu:view 或者 oauthClientDetails:view
     *
     * @see MenuService#findMenus(MenuVo)
     */
    @GetMapping("/tree")
    @SaCheckPermission(value = {"role:view", "menu:view", "oauthClientDetails:view"}, mode = SaMode.OR)
    public BaseRsp<List<MenuTreeVo>> menuList(MenuVo menu) {
        List<MenuTreeVo> systemMenus = this.menuService.findMenus(menu);
        return RspUtil.data(systemMenus);
    }

    /**
     * 根据菜单id查询菜单
     * 权限: 仅限内部调用
     *
     * @param menuId 菜单id
     * @return 菜单
     */
    @GetMapping(value = "/findByMenuId/{menuId}")
    @Inner
    public MenuCacheVo findByMenuId(@PathVariable Long menuId) {
        return this.menuService.queryChain().where(MENU.MENU_ID.eq(menuId)).oneAs(MenuCacheVo.class);
    }

    /**
     * 根据菜单id集合查询菜单集合
     * 权限: 仅限内部调用
     *
     * @param menuIds 菜单id集合
     * @return 菜单集合
     */
    @GetMapping(value = "/findByMenuIds/{menuIds}")
    @Inner
    public Map<Long, MenuCacheVo> findByMenuIds(@PathVariable List<Long> menuIds) {
        Map<Long, MenuCacheVo> menuMap = new HashMap<>();
        List<MenuCacheVo> menuCacheVos = this.menuService.queryChain().where(MENU.MENU_ID.in(menuIds)).listAs(MenuCacheVo.class);
        if (CollectionUtil.isNotEmpty(menuCacheVos)) {
            for (Long menuId : menuIds) {
                MenuCacheVo menu = CollectionUtil.findOneByField(menuCacheVos, "menuId", menuId);
                menuMap.put(menuId, menu);
            }
        }
        return menuMap;
    }

    /**
     * 获取用户权限
     * 权限: 仅限内部调用
     *
     * @param username 用户名称
     * @return 权限
     */
    @GetMapping(value = "/findUserPermissions/{username}")
    @Inner
    public List<String> findUserPermissions(@PathVariable String username) {
        return this.menuService.findUserPermissions(username);
    }

    /**
     * 获取用户路由
     * 权限: 仅限内部调用
     *
     * @param username 用户名称
     * @return 路由
     */
    @GetMapping(value = "/findUserRouters/{username}")
    @Inner
    public List<VueRouter<MenuVo>> findUserRouters(@PathVariable String username) {
        return this.menuService.findUserRouters(username);
    }

    /**
     * 检查菜单编码
     * 权限: menu:add 或者 menu:update
     *
     * @param menuId   菜单id
     * @param menuCode 菜单编码
     * @return 是否重复
     */
    @GetMapping("/checkMenuCode")
    @SaCheckPermission(value = {"menu:add", "menu:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkMenuCode(@RequestParam(required = false) Long menuId,
                                         @NotBlank(message = "{required}") @RequestParam String menuCode) {
        menuService.validateMenuCode(menuCode, menuId);
        return RspUtil.message();
    }

    /**
     * 新增菜单
     * 权限: menu:add
     *
     * @see MenuService#createMenu(MenuAo)
     */
    @PostMapping
    @SaCheckPermission("menu:add")
    @ControllerEndpoint(operation = "新增菜单")
    public BaseRsp<Menu> addMenu(@RequestBody @Validated MenuAo menuAo) {
        return RspUtil.data(this.menuService.createMenu(menuAo));
    }

    /**
     * 删除菜单
     * 权限: menu:delete
     *
     * @param menuIds 菜单id集合(,分隔)
     * @see MenuService#deleteMenu(List)
     */
    @DeleteMapping("/{menuIds}")
    @SaCheckPermission("menu:delete")
    @ControllerEndpoint(operation = "删除菜单")
    public BaseRsp<String> deleteMenu(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        List<Long> ids = Arrays.stream(menuIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.menuService.deleteMenu(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改菜单
     * 权限: menu:update
     *
     * @see MenuService#updateMenu(MenuAo)
     */
    @PutMapping
    @SaCheckPermission("menu:update")
    @ControllerEndpoint(operation = "修改菜单")
    public BaseRsp<Menu> updateMenu(@RequestBody @Validated({ValidGroups.Crud.Update.class}) MenuAo menuAo) {
        return RspUtil.data(this.menuService.updateMenu(menuAo));
    }
}