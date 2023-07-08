package com.zclcs.platform.system.runner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import com.zclcs.common.db.merge.starter.properties.MyDbMergeProperties;
import com.zclcs.platform.system.api.bean.entity.*;
import com.zclcs.platform.system.properties.PlatformSystemProperties;
import com.zclcs.platform.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zclcs
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GenerateSqlRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final PlatformSystemProperties platformSystemProperties;
    private final BlackListService blackListService;
    private final GeneratorConfigService generatorConfigService;
    private final DeptService deptService;
    private final OauthClientDetailsService oauthClientDetailsService;
    private final RoleService roleService;
    private final UserService userService;
    private final UserDataPermissionService userDataPermissionService;
    private final UserRoleService userRoleService;
    private final MenuService menuService;
    private final DictItemService dictItemService;
    private final RoleMenuService roleMenuService;
    private final MyDbMergeProperties myDbMergeProperties;


    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive() && platformSystemProperties.getGenerateSql()) {
            String systemSql = getBlackListSql() +
                    getGeneratorConfigSql() +
                    getDeptSql() +
                    getOauthClientDetailsSql() +
                    getRoleSql() +
                    getUserSql() +
                    getUserDataPermissionSql() +
                    getUserRoleSql() +
                    getMenuSql() +
                    getRoleMenuSql();
            writeSql(systemSql, "1.0.3-data-system.sql");
            String dictItemSql = getDictItemSql();
            writeSql(dictItemSql, "1.0.4-data-dict_item.sql");
        }
    }

    private String getBlackListSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<BlackList> list = blackListService.list();
        for (BlackList blackList : list) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s,%s";
            String values = String.format(valuesFill, getValue(blackList.getRequestUri())
                    , getValue(blackList.getRequestMethod()), getValue(blackList.getLimitFrom())
                    , getValue(blackList.getLimitTo()), getValue(blackList.getBlackStatus())
                    , "now()", getValue("system"));
            String uniqueSqlFill = "request_uri=%s and request_method=%s";
            String uniqueSql = String.format(uniqueSqlFill, getValue(blackList.getRequestUri()), getValue(blackList.getRequestMethod()));
            String sql = String.format(sqlFill, "system_black_list",
                    "request_uri,request_method,limit_from,limit_to,black_status,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getGeneratorConfigSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<GeneratorConfig> generatorConfigs = generatorConfigService.list();
        for (GeneratorConfig generatorConfig : generatorConfigs) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
            String values = String.format(valuesFill, getValue(generatorConfig.getServerName())
                    , getValue(generatorConfig.getAuthor()), getValue(generatorConfig.getBasePackage())
                    , getValue(generatorConfig.getEntityPackage()), getValue(generatorConfig.getAoPackage())
                    , getValue(generatorConfig.getVoPackage()), getValue(generatorConfig.getMapperPackage())
                    , getValue(generatorConfig.getMapperXmlPackage()), getValue(generatorConfig.getServicePackage())
                    , getValue(generatorConfig.getServiceImplPackage()), getValue(generatorConfig.getControllerPackage())
                    , getValue(generatorConfig.getIsTrim()), getValue(generatorConfig.getTrimValue())
                    , getValue(generatorConfig.getExcludeColumns())
                    , "now()", getValue("system"));
            String uniqueSqlFill = "server_name=%s";
            String uniqueSql = String.format(uniqueSqlFill, getValue(generatorConfig.getServerName()));
            String sql = String.format(sqlFill, "system_generator_config",
                    "server_name,author,base_package,entity_package,ao_package,vo_package,mapper_package,mapper_xml_package," +
                            "service_package,service_impl_package,controller_package,is_trim,trim_value,exclude_columns,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getDeptSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<Dept> list = deptService.list();
        for (Dept dept : list) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s";
            String values = String.format(valuesFill, getValue(dept.getDeptCode())
                    , getValue(dept.getParentCode()), getValue(dept.getDeptName())
                    , getValue(dept.getOrderNum()), "now()", getValue("system"));
            String uniqueSqlFill = "dept_code=%s";
            String uniqueSql = String.format(uniqueSqlFill, getValue(dept.getDeptCode()));
            String sql = String.format(sqlFill, "system_dept",
                    "dept_code,parent_code,dept_name,order_num,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getOauthClientDetailsSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<OauthClientDetails> list = oauthClientDetailsService.list();
        for (OauthClientDetails oauthClientDetails : list) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
            String values = String.format(valuesFill, getValue(oauthClientDetails.getClientId())
                    , getValue(oauthClientDetails.getResourceIds()), getValue(oauthClientDetails.getClientSecret())
                    , getValue(oauthClientDetails.getScope()), getValue(oauthClientDetails.getAuthorizedGrantTypes())
                    , getValue(oauthClientDetails.getWebServerRedirectUri()), getValue(oauthClientDetails.getAuthorities())
                    , getValue(oauthClientDetails.getAccessTokenValidity()), getValue(oauthClientDetails.getRefreshTokenValidity())
                    , getValue(oauthClientDetails.getAdditionalInformation()), getValue(oauthClientDetails.getAutoapprove())
                    , "now()", getValue("system"));
            String uniqueSqlFill = "client_id=%s";
            String uniqueSql = String.format(uniqueSqlFill, getValue(oauthClientDetails.getClientId()));
            String sql = String.format(sqlFill, "system_oauth_client_details",
                    "client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities," +
                            "access_token_validity,refresh_token_validity,additional_information,autoapprove,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getRoleSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<Role> list = roleService.list();
        for (Role role : list) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s";
            String values = String.format(valuesFill, getValue(role.getRoleCode())
                    , getValue(role.getRoleName()), getValue(role.getRemark()),
                    "now()", getValue("system"));
            String uniqueSqlFill = "role_code=%s";
            String uniqueSql = String.format(uniqueSqlFill, getValue(role.getRoleCode()));
            String sql = String.format(sqlFill, "system_role",
                    "role_code,role_name,remark,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getUserSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<User> list = userService.list();
        for (User user : list) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s";
            String values = String.format(valuesFill, getValue(user.getUsername())
                    , getValue(user.getRealName()), getValue(user.getPassword())
                    , getValue(user.getDeptId()), getValue(user.getStatus())
                    , getValue(user.getAvatar()), "now()", getValue("system"));
            String uniqueSqlFill = "username=%s";
            String uniqueSql = String.format(uniqueSqlFill, getValue(user.getUsername()));
            String sql = String.format(sqlFill, "system_user",
                    "username,real_name,password,dept_id,status,avatar,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getUserDataPermissionSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<UserDataPermission> list = userDataPermissionService.list();
        for (UserDataPermission userDataPermission : list) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s";
            User user = userService.getById(userDataPermission.getUserId());
            Dept dept = deptService.getById(userDataPermission.getDeptId());
            String userSql = "(select user_id from system_user where username = " + getValue(user.getUsername()) + ")";
            String deptSql = "(select dept_id from system_dept where dept_code = " + getValue(dept.getDeptCode()) + ")";
            String values = String.format(valuesFill, userSql, deptSql, "now()", getValue("system"));
            String uniqueSqlFill = "user_id=%s and dept_id=%s";
            String uniqueSql = String.format(uniqueSqlFill, userSql, deptSql);
            String sql = String.format(sqlFill, "system_user_data_permission",
                    "user_id,dept_id,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getUserRoleSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<UserRole> list = userRoleService.list();
        for (UserRole userRole : list) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s";
            User user = userService.getById(userRole.getUserId());
            Role role = roleService.getById(userRole.getRoleId());
            String userSql = "(select user_id from system_user where username = " + getValue(user.getUsername()) + ")";
            String roleSql = "(select role_id from system_role where role_code = " + getValue(role.getRoleCode()) + ")";
            String values = String.format(valuesFill, userSql, roleSql, "now()", getValue("system"));
            String uniqueSqlFill = "user_id=%s and role_id=%s";
            String uniqueSql = String.format(uniqueSqlFill, userSql, roleSql);
            String sql = String.format(sqlFill, "system_user_role",
                    "user_id,role_id,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getDictItemSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<DictItem> dictItems = dictItemService.list();
        for (DictItem dictItem : dictItems) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
            String values = String.format(valuesFill, getValue(dictItem.getDictName())
                    , getValue(dictItem.getParentValue()), getValue(dictItem.getValue())
                    , getValue(dictItem.getTitle()), getValue(dictItem.getType())
                    , getValue(dictItem.getWhetherSystemDict()), getValue(dictItem.getSorted())
                    , getValue(dictItem.getIsDisabled()), "now()", getValue("system"));
            String uniqueSqlFill = "dict_name=%s and value=%s";
            String uniqueSql = String.format(uniqueSqlFill, getValue(dictItem.getDictName()), getValue(dictItem.getValue()));
            String sql = String.format(sqlFill, "system_dict_item",
                    "dict_name,parent_value,value,title,type,whether_system_dict,sorted,is_disabled,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getMenuSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<Menu> menus = menuService.list();
        for (Menu menu : menus) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
            String values = String.format(valuesFill, getValue(menu.getMenuCode())
                    , getValue(menu.getParentCode()), getValue(menu.getMenuName())
                    , getValue(menu.getKeepAliveName()), getValue(menu.getPath())
                    , getValue(menu.getComponent()), getValue(menu.getRedirect())
                    , getValue(menu.getPerms()), getValue(menu.getIcon())
                    , getValue(menu.getType()), getValue(menu.getHideMenu())
                    , getValue(menu.getIgnoreKeepAlive()), getValue(menu.getHideBreadcrumb())
                    , getValue(menu.getHideChildrenInMenu()), getValue(menu.getCurrentActiveMenu())
                    , getValue(menu.getOrderNum()), "now()", getValue("system"));
            String uniqueSqlFill = "menu_code=%s";
            String uniqueSql = String.format(uniqueSqlFill, getValue(menu.getMenuCode()));
            String sql = String.format(sqlFill, "system_menu", "menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms," +
                    "icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getRoleMenuSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        List<RoleMenu> roleMenus = roleMenuService.list();
        for (RoleMenu roleMenu : roleMenus) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s";
            Role role = roleService.getById(roleMenu.getRoleId());
            Menu menu = menuService.getById(roleMenu.getMenuId());
            String roleSql = "(select role_id from system_role where role_code = " + getValue(role.getRoleCode()) + ")";
            String menuSql = "(select menu_id from system_menu where menu_code = " + getValue(menu.getMenuCode()) + ")";
            String values = String.format(valuesFill, roleSql, menuSql, "now()", getValue("system"));
            String uniqueSqlFill = "role_id=%s and menu_id=%s";
            String uniqueSql = String.format(uniqueSqlFill, roleSql, menuSql);
            String sql = String.format(sqlFill, "system_role_menu", "role_id,menu_id,create_at,create_by", values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private void writeSql(String sql, String fileName) {
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String finalPath = applicationHome.getDir().getParentFile().getParentFile()
                .getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\"
                + StrUtil.replace(myDbMergeProperties.getSql(), "/", "\\") + fileName;
        FileUtil.touch(finalPath);
        FileUtil.writeString(sql, finalPath, StandardCharsets.UTF_8);
    }

    private Object getValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String s) {
            return "\"" + s + "\"";
        } else {
            return value;
        }
    }
}
