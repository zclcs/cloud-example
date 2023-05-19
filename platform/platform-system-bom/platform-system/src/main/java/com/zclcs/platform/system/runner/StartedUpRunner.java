package com.zclcs.platform.system.runner;

import com.zclcs.cloud.lib.core.utils.BaseUtil;
import com.zclcs.platform.system.service.DictItemService;
import com.zclcs.platform.system.service.MenuService;
import com.zclcs.platform.system.service.RoleMenuService;
import com.zclcs.platform.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final Environment environment;
    private final RoleService roleService;
    private final MenuService menuService;
    private final DictItemService dictItemService;
    private final RoleMenuService roleMenuService;


    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            BaseUtil.printSystemUpBanner(environment);
//            List<DictItem> dictItems = dictItemService.lambdaQuery().eq(DictItem::getDictName, "area_code").list();
//            for (DictItem dictItem : dictItems) {
//                String sqlFill = "call insert_if_not_exists(database(), '%s', " +
//                        "'%s'," +
//                        "'%s'," +
//                        "'%s');//";
//                String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
//                String values = String.format(valuesFill, getValue(dictItem.getDictName())
//                        , getValue(dictItem.getParentValue()), getValue(dictItem.getValue())
//                        , getValue(dictItem.getTitle()), getValue(dictItem.getType())
//                        , getValue(dictItem.getWhetherSystemDict()), getValue(dictItem.getSorted())
//                        , getValue(dictItem.getIsDisabled()), "now()", getValue("system"));
//                String uniqueSqlFill = "dict_name=%s and value=%s";
//                String uniqueSql = String.format(uniqueSqlFill, getValue(dictItem.getDictName()), getValue(dictItem.getValue()));
//                String sql = String.format(sqlFill, "system_dict_item", "dict_name,parent_value,value,title,type,whether_system_dict,sorted,is_disabled,create_at,create_by", values, uniqueSql);
//                System.out.println(sql);
//            }
//            List<Menu> menus = menuService.list();
//            for (Menu menu : menus) {
//                String sqlFill = "call insert_if_not_exists(database(), '%s', " +
//                        "'%s'," +
//                        "'%s'," +
//                        "'%s');//";
//                String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
//                String values = String.format(valuesFill, getValue(menu.getMenuCode())
//                        , getValue(menu.getParentCode()), getValue(menu.getMenuName())
//                        , getValue(menu.getKeepAliveName()), getValue(menu.getPath())
//                        , getValue(menu.getComponent()), getValue(menu.getRedirect())
//                        , getValue(menu.getPerms()), getValue(menu.getIcon())
//                        , getValue(menu.getType()), getValue(menu.getHideMenu())
//                        , getValue(menu.getIgnoreKeepAlive()), getValue(menu.getHideBreadcrumb())
//                        , getValue(menu.getHideChildrenInMenu()), getValue(menu.getCurrentActiveMenu())
//                        , getValue(menu.getOrderNum()), "now()", getValue("system"));
//                String uniqueSqlFill = "menu_code=%s";
//                String uniqueSql = String.format(uniqueSqlFill, getValue(menu.getMenuCode()));
//                String sql = String.format(sqlFill, "system_menu", "menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms," +
//                        "icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by", values, uniqueSql);
//                System.out.println(sql);
//            }
//            List<RoleMenu> roleMenus = roleMenuService.list();
//            for (RoleMenu roleMenu : roleMenus) {
//                String sqlFill = "call insert_if_not_exists(database(), '%s', " +
//                        "'%s'," +
//                        "'%s'," +
//                        "'%s');//";
//                String valuesFill = "%s,%s,%s,%s";
//                Role role = roleService.getById(roleMenu.getRoleId());
//                Menu menu = menuService.getById(roleMenu.getMenuId());
//                String roleSql = "(select role_id from system_role where role_code = " + getValue(role.getRoleCode()) + ")";
//                String menuSql = "(select menu_id from system_menu where menu_code = " + getValue(menu.getMenuCode()) + ")";
//                String values = String.format(valuesFill, roleSql, menuSql, "now()", getValue("system"));
//                String uniqueSqlFill = "role_id=%s and menu_id=%s";
//                String uniqueSql = String.format(uniqueSqlFill, roleSql, menuSql);
//                String sql = String.format(sqlFill, "system_role_menu", "role_id,menu_id,create_at,create_by", values, uniqueSql);
//                System.out.println(sql);
//            }
        }
    }

    private Object getValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String s) {
            return "\"" + value + "\"";
        } else {
            return value;
        }
    }
}
