call insert_if_not_exists(database(), 'system_black_list',
                          'request_uri,request_method,limit_from,limit_to,black_status,create_at,create_by',
                          '"/actuator/**","ALL","00:00:00","23:59:59","1",now(),"system"',
                          'request_uri="/actuator/**" and request_method="ALL"');
call insert_if_not_exists(database(), 'system_generator_config',
                          'server_name,author,base_package,entity_package,ao_package,vo_package,mapper_package,mapper_xml_package,service_package,service_impl_package,controller_package,is_trim,trim_value,exclude_columns,create_at,create_by',
                          '"platform-system","zclcs","com.zclcs.platform.system","api.entity","api.entity.ao","api.entity.vo","mapper","mapper","service","service.impl","controller","1","system_","version,tenant_id,create_at,update_at,create_by,update_by,create_name,create_date,update_name,update_date,delete_name,delete_date,deleted",now(),"system"',
                          'server_name="platform-system"');
call insert_if_not_exists(database(), 'system_generator_config',
                          'server_name,author,base_package,entity_package,ao_package,vo_package,mapper_package,mapper_xml_package,service_package,service_impl_package,controller_package,is_trim,trim_value,exclude_columns,create_at,create_by',
                          '"test-test","zclcs","com.zclcs.test.test","api.entity","api.entity.ao","api.entity.vo","mapper","mapper","service","service.impl","controller","1","test_","version,tenant_id,create_at,update_at,create_by,update_by,create_name,create_date,update_name,update_date,delete_name,delete_date,deleted",now(),"system"',
                          'server_name="test-test"');
call insert_if_not_exists(database(), 'system_dept', 'dept_code,parent_code,dept_name,order_num,create_at,create_by',
                          '"DEV","0","开发部",1.0,now(),"system"', 'dept_code="DEV"');
call insert_if_not_exists(database(), 'system_oauth_client_details',
                          'client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove,create_at,create_by',
                          '"swagger",null,"123456","server","password,app,refresh_token,authorization_code,client_credentials","https://zclcs",null,null,null,null,"true",now(),"system"',
                          'client_id="swagger"');
call insert_if_not_exists(database(), 'system_oauth_client_details',
                          'client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove,create_at,create_by',
                          '"zclcs",null,"123456","server","password,app,refresh_token,authorization_code,client_credentials","https://zclcs",null,null,null,null,"true",now(),"system"',
                          'client_id="zclcs"');
call insert_if_not_exists(database(), 'system_role', 'role_code,role_name,remark,create_at,create_by',
                          '"ADMIN","管理员",null,now(),"system"', 'role_code="ADMIN"');
call insert_if_not_exists(database(), 'system_role', 'role_code,role_name,remark,create_at,create_by',
                          '"GUEST","查看",null,now(),"system"', 'role_code="GUEST"');
call insert_if_not_exists(database(), 'system_role', 'role_code,role_name,remark,create_at,create_by',
                          '"DEVELOP","开发",null,now(),"system"', 'role_code="DEVELOP"');
call insert_if_not_exists(database(), 'system_user',
                          'username,real_name,password,dept_id,status,avatar,create_at,create_by',
                          '"admin","管理员","$2a$10$LNmOtAAmYxewGYcxdwiYteFUPG1KWHYtgqwvtsEPyRxs70nT1hZF2","1","1","default.jpg",now(),"system"',
                          'username="admin"');
call insert_if_not_exists(database(), 'system_user',
                          'username,real_name,password,dept_id,status,avatar,create_at,create_by',
                          '"guest","访客","$2a$10$63vb4uvE5bfwvP5BPHVJtObCVop2Tu/8rSW2Dqvm5MYbnBDlCNTBK","1","1","default.jpg",now(),"system"',
                          'username="guest"');
call insert_if_not_exists(database(), 'system_user',
                          'username,real_name,password,dept_id,status,avatar,create_at,create_by',
                          '"develop","开发","$2a$10$63vb4uvE5bfwvP5BPHVJtObCVop2Tu/8rSW2Dqvm5MYbnBDlCNTBK","1","1","default.jpg",now(),"system"',
                          'username="develop"');
call insert_if_not_exists(database(), 'system_user_data_permission', 'user_id,dept_id,create_at,create_by',
                          '(select user_id from system_user where username = "admin"),(select dept_id from system_dept where dept_code = "DEV"),now(),"system"',
                          'user_id=(select user_id from system_user where username = "admin") and dept_id=(select dept_id from system_dept where dept_code = "DEV")');
call insert_if_not_exists(database(), 'system_user_role', 'user_id,role_id,create_at,create_by',
                          '(select user_id from system_user where username = "admin"),(select role_id from system_role where role_code = "ADMIN"),now(),"system"',
                          'user_id=(select user_id from system_user where username = "admin") and role_id=(select role_id from system_role where role_code = "ADMIN")');
call insert_if_not_exists(database(), 'system_user_role', 'user_id,role_id,create_at,create_by',
                          '(select user_id from system_user where username = "guest"),(select role_id from system_role where role_code = "GUEST"),now(),"system"',
                          'user_id=(select user_id from system_user where username = "guest") and role_id=(select role_id from system_role where role_code = "GUEST")');
call insert_if_not_exists(database(), 'system_user_role', 'user_id,role_id,create_at,create_by',
                          '(select user_id from system_user where username = "develop"),(select role_id from system_role where role_code = "DEVELOP"),now(),"system"',
                          'user_id=(select user_id from system_user where username = "develop") and role_id=(select role_id from system_role where role_code = "DEVELOP")');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM","0","系统管理",null,"/system","Layout",null,null,"ant-design:setting-outlined","2","0","0","0","0",null,2.0,now(),"system"',
                          'menu_code="SYSTEM"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER","SYSTEM","用户管理","AccountManagement","user","/cloud/system/user/index",null,"","ant-design:user-switch-outlined","0","0","0","0","0",null,1.0,now(),"system"',
                          'menu_code="SYSTEM_USER"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER_DETAIL","SYSTEM","用户详情页面","AccountDetail","accountDetail/:username","/cloud/system/user/AccountDetail",null,"user:detail:view","ant-design:audit-outlined","0","1","1","0","0",null,2.0,now(),"system"',
                          'menu_code="SYSTEM_USER_DETAIL"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DEPT","SYSTEM","部门管理","DeptManagement","dept","/cloud/system/dept/index",null,"","ant-design:apartment-outlined","0","0","0","0","0",null,4.0,now(),"system"',
                          'menu_code="SYSTEM_DEPT"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER:ADD","SYSTEM_USER","添加用户",null,null,null,null,"user:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_USER:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER:UPDATE","SYSTEM_USER","修改用户",null,null,null,null,"user:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_USER:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER:DELETE","SYSTEM_USER","删除用户",null,null,null,null,"user:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_USER:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER:RESET_PASSWORD","SYSTEM_USER","重置用户密码",null,null,null,null,"user:resetPassword",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_USER:RESET_PASSWORD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MENU","SYSTEM","菜单管理","MenuManagement","menu","/cloud/system/menu/index",null,"","ant-design:menu-fold-outlined","0","0","0","0","0",null,3.0,now(),"system"',
                          'menu_code="SYSTEM_MENU"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MENU:ADD","SYSTEM_MENU","添加菜单",null,null,null,null,"menu:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_MENU:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_ROLE","SYSTEM","角色管理","RoleManagement","role","/cloud/system/role/index",null,"","ant-design:solution-outlined","0","0","0","0","0",null,2.0,now(),"system"',
                          'menu_code="SYSTEM_ROLE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MENU:UPDATE","SYSTEM_MENU","修改菜单",null,null,null,null,"menu:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_MENU:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MENU:DELETE","SYSTEM_MENU","删除菜单",null,null,null,null,"menu:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_MENU:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_ROLE:ADD","SYSTEM_ROLE","添加角色",null,null,null,null,"role:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_ROLE:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_ROLE:UPDATE","SYSTEM_ROLE","修改角色",null,null,null,null,"role:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_ROLE:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DEPT:ADD","SYSTEM_DEPT","添加部门",null,null,null,null,"dept:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_DEPT:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DEPT:UPDATE","SYSTEM_DEPT","修改部门",null,null,null,null,"dept:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_DEPT:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DEPT:DELETE","SYSTEM_DEPT","删除部门",null,null,null,null,"dept:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_DEPT:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_ROLE:DELETE","SYSTEM_ROLE","删除角色",null,null,null,null,"role:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_ROLE:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER:RESET_PASSWORD_MENU","SYSTEM","重置密码","ChangePassword","password","/cloud/system/password/index",null,"user:view","ant-design:radius-setting-outlined","0","0","0","0","0",null,5.0,now(),"system"',
                          'menu_code="SYSTEM_USER:RESET_PASSWORD_MENU"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"DASHBOARD","0","首页",null,"/dashboard","Layout","/dashboard/analysis",null,"ant-design:align-left-outlined","2","0","1","1","0",null,1.0,now(),"system"',
                          'menu_code="DASHBOARD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"ANALYSIS","DASHBOARD","分析页",null,"analysis","/dashboard/analysis/index",null,null,"ant-design:amazon-outlined","0","0","0","1","0","/dashboard",1.0,now(),"system"',
                          'menu_code="ANALYSIS"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"WORKBENCH","DASHBOARD","工作台",null,"workbench","/dashboard/workbench/index",null,null,"ant-design:alert-filled","0","0","0","1","0","/dashboard",2.0,now(),"system"',
                          'menu_code="WORKBENCH"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GEN","0","快速开发",null,"/gen","Layout",null,null,"ant-design:code-filled","2","0","0","0","0",null,4.0,now(),"system"',
                          'menu_code="GEN"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GEN_CONFIG","GEN","代码生成配置管理","GenConfig","config","/cloud/generator/config/index",null,"gen:config","ant-design:contacts-outlined","0","0","0","0","0",null,1.0,now(),"system"',
                          'menu_code="GEN_CONFIG"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GEN_GEN","GEN","代码生成","Gen","gen","/cloud/generator/gen/index",null,"gen:generate","ant-design:code-sandbox-outlined","0","0","0","0","0",null,2.0,now(),"system"',
                          'menu_code="GEN_GEN"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GEN_CONFIG:UPDATE","GEN_CONFIG","修改",null,null,null,null,"generatorConfig:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GEN_CONFIG:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GEN_GEN:GEN","GEN_GEN","生成代码",null,null,null,null,"gen:generate:gen",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GEN_GEN:GEN"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DICT","SYSTEM","字典管理","Dict","dict","/cloud/system/dict/index",null,null,"ant-design:barcode-outlined","0","0","0","0","0",null,6.0,now(),"system"',
                          'menu_code="SYSTEM_DICT"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DICT:ADD","SYSTEM_DICT","新增字典",null,null,null,null,"dictItem:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_DICT:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DICT:UPDATE","SYSTEM_DICT","删除字典",null,null,null,null,"dictItem:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_DICT:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DICT:DELETE","SYSTEM_DICT","修改字典",null,null,null,null,"dictItem:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_DICT:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_CLIENT","SYSTEM","客户端管理","Client","client","/cloud/system/client/index",null,"","ant-design:paper-clip-outlined","0","0","0","0","0",null,7.0,now(),"system"',
                          'menu_code="SYSTEM_CLIENT"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_CLIENT:ADD","SYSTEM_CLIENT","添加",null,null,null,null,"oauthClientDetails:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_CLIENT:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_CLIENT:UPDATE","SYSTEM_CLIENT","更新",null,null,null,null,"oauthClientDetails:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_CLIENT:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_CLIENT:DELETE","SYSTEM_CLIENT","删除",null,null,null,null,"oauthClientDetails:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_CLIENT:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_CLIENT:DECRYPT","SYSTEM_CLIENT","获取密钥",null,null,null,null,"oauthClientDetails:decrypt",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_CLIENT:DECRYPT"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY","0","网关管理",null,"/route","Layout",null,null,"ant-design:gateway-outlined","2","0","0","0","0",null,3.0,now(),"system"',
                          'menu_code="GATEWAY"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_ROUTE_LOG","GATEWAY","网关日志","RouteLog","log","/cloud/route/log/index",null,"","ant-design:login-outlined","0","0","0","0","0",null,1.0,now(),"system"',
                          'menu_code="GATEWAY_ROUTE_LOG"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_RATE_LIMIT_RULE","GATEWAY","限流规则","RateLimitRule","rate/rule","/cloud/route/rate/rule/index",null,"","ant-design:alert-filled","0","0","0","0","0",null,2.0,now(),"system"',
                          'menu_code="GATEWAY_RATE_LIMIT_RULE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_RATE_LIMIT_LOG","GATEWAY","限流日志","RateLimitLog","rate/log","/cloud/route/rate/log/index",null,"","ant-design:ant-design-outlined","0","0","0","0","0",null,3.0,now(),"system"',
                          'menu_code="GATEWAY_RATE_LIMIT_LOG"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_BLACK_LIST","GATEWAY","黑名单管理","BlackListPage","black","/cloud/route/black/index",null,"","ant-design:eye-invisible-filled","0","0","0","0","0",null,4.0,now(),"system"',
                          'menu_code="GATEWAY_BLACK_LIST"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_BLOCK_LOG","GATEWAY","黑名单日志","BlockLog","block","/cloud/route/block/index",null,"","ant-design:tablet-outlined","0","0","0","0","0",null,5.0,now(),"system"',
                          'menu_code="GATEWAY_BLOCK_LOG"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_ROUTE_LOG:DELETE","GATEWAY_ROUTE_LOG","删除网关日志",null,null,null,null,"routeLog:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_ROUTE_LOG:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_RATE_LIMIT_RULE:ADD","GATEWAY_RATE_LIMIT_RULE","新增限流规则",null,null,null,null,"rateLimitRule:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_RATE_LIMIT_RULE:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_RATE_LIMIT_RULE:DELETE","GATEWAY_RATE_LIMIT_RULE","删除限流规则",null,null,null,null,"rateLimitRule:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_RATE_LIMIT_RULE:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_RATE_LIMIT_RULE:UPDATE","GATEWAY_RATE_LIMIT_RULE","修改限流规则",null,null,null,null,"rateLimitRule:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_RATE_LIMIT_RULE:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_RATE_LIMIT_LOG:DELETE","GATEWAY_RATE_LIMIT_LOG","删除限流拦截日志",null,null,null,null,"rateLimitLog:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_RATE_LIMIT_LOG:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_BLACK_LIST:ADD","GATEWAY_BLACK_LIST","新增网关黑名单",null,null,null,null,"blackList:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_BLACK_LIST:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_BLACK_LIST:UPDATE","GATEWAY_BLACK_LIST","修改网关黑名单",null,null,null,null,"blackList:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_BLACK_LIST:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_BLACK_LIST:DELETE","GATEWAY_BLACK_LIST","删除网关黑名单",null,null,null,null,"blackList:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_BLACK_LIST:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_BLOCK_LOG:DELETE","GATEWAY_BLOCK_LOG","删除黑名单日志",null,null,null,null,"blockLog:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_BLOCK_LOG:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER:VIEW","SYSTEM_USER","查看用户",null,null,null,null,"user:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_USER:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_ROLE:VIEW","SYSTEM_ROLE","查看角色",null,null,null,null,"role:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_ROLE:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MENU:VIEW","SYSTEM_MENU","查看菜单",null,null,null,null,"menu:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_MENU:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DEPT:VIEW","SYSTEM_DEPT","查看部门",null,null,null,null,"dept:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_DEPT:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_DICT:VIEW","SYSTEM_DICT","查看字典",null,null,null,null,"dictItem:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_DICT:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_CLIENT:VIEW","SYSTEM_CLIENT","查看客户端",null,null,null,null,"oauthClientDetails:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_CLIENT:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_ROUTE_LOG:VIEW","GATEWAY_ROUTE_LOG","查看网关日志",null,null,null,null,"routeLog:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_ROUTE_LOG:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_RATE_LIMIT_RULE:VIEW","GATEWAY_RATE_LIMIT_RULE","查看限流规则",null,null,null,null,"rateLimitRule:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_RATE_LIMIT_RULE:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_RATE_LIMIT_LOG:VIEW","GATEWAY_RATE_LIMIT_LOG","查看限流日志",null,null,null,null,"rateLimitLog:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_RATE_LIMIT_LOG:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_BLACK_LIST:VIEW","GATEWAY_BLACK_LIST","查看黑名单",null,null,null,null,"blackList:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_BLACK_LIST:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GATEWAY_BLOCK_LOG:VIEW","GATEWAY_BLOCK_LOG","查看黑名单日志",null,null,null,null,"blockLog:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GATEWAY_BLOCK_LOG:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER:UPDATE_STATUS","SYSTEM_USER","禁用账号",null,null,null,null,"user:updateStatus",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_USER:UPDATE_STATUS"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_USER:VIEW_LOG","SYSTEM_USER","查看用户操作日志",null,null,null,null,"log:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_USER:VIEW_LOG"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER","SYSTEM","文件管理","","fileManager","",null,null,"ant-design:file-unknown-outlined","0","0","0","0","0",null,8.0,now(),"system"',
                          'menu_code="FILE_MANAGER"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER_BUCKET","FILE_MANAGER","桶","Bucket","bucket","/cloud/system/bucket/index",null,null,"ant-design:folder-add-filled","0","0","0","0","0",null,1.0,now(),"system"',
                          'menu_code="FILE_MANAGER_BUCKET"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER_FILE","FILE_MANAGER","文件","File","file","/cloud/system/file/index",null,null,"ant-design:file-add-filled","0","0","0","0","0",null,2.0,now(),"system"',
                          'menu_code="FILE_MANAGER_FILE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER_BUCKET:VIEW","FILE_MANAGER_BUCKET","查看桶",null,null,null,null,"bucket:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="FILE_MANAGER_BUCKET:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER_BUCKET:ADD","FILE_MANAGER_BUCKET","添加桶",null,null,null,null,"bucket:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="FILE_MANAGER_BUCKET:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER_BUCKET:DELETE","FILE_MANAGER_BUCKET","删除桶",null,null,null,null,"bucket:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="FILE_MANAGER_BUCKET:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER_FILE:VIEW","FILE_MANAGER_FILE","查看文件",null,null,null,null,"file:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="FILE_MANAGER_FILE:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER_FILE:DELETE","FILE_MANAGER_FILE","删除文件",null,null,null,null,"file:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="FILE_MANAGER_FILE:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"FILE_MANAGER_BUCKET:UPDATE","FILE_MANAGER_BUCKET","修改桶",null,null,null,null,"bucket:update",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="FILE_MANAGER_BUCKET:UPDATE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GEN_CONFIG:ADD","GEN_CONFIG","添加",null,null,null,null,"generatorConfig:add",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GEN_CONFIG:ADD"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GEN_CONFIG:VIEW","GEN_CONFIG","查看",null,null,null,null,"generatorConfig:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GEN_CONFIG:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"GEN_CONFIG:DELETE","GEN_CONFIG","删除",null,null,null,null,"generatorConfig:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="GEN_CONFIG:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR","0","系统监控",null,"/monitor","Layout",null,null,"ant-design:monitor-outlined","2","0","0","0","0",null,5.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_REDIS","SYSTEM_MONITOR","redis控制台","RedisConsole","redis","/cloud/monitor/redis/index",null,null,"ant-design:credit-card-twotone","0","0","0","0","0",null,1.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_REDIS"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_REDIS:VIEW","SYSTEM_MONITOR_REDIS","查看",null,null,null,null,"redis:view",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_REDIS:VIEW"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_REDIS:DELETE","SYSTEM_MONITOR_REDIS","删除",null,null,null,null,"redis:delete",null,"1","0","0","0","0",null,null,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_REDIS:DELETE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_MYSQL","SYSTEM_MONITOR","mysql控制台","MysqlConsole","mysql","/cloud/monitor/mysql/index",null,"dataBase:view","ant-design:console-sql-outlined","0","0","0","0","0",null,2.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_MYSQL"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_NACOS","SYSTEM_MONITOR","nacos控制台","","nacos","",null,"nacos:view","ant-design:reconciliation-filled","0","0","0","0","0",null,3.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_NACOS"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_NACOS_CONFIG","SYSTEM_MONITOR_NACOS","配置列表","NacosConfigPage","config","/cloud/monitor/nacos/config/index",null,"","ant-design:box-plot-filled","0","0","0","0","0",null,1.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_NACOS_CONFIG"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_NACOS_SERVICE","SYSTEM_MONITOR_NACOS","服务列表","NacosServicePage","service","/cloud/monitor/nacos/service/index",null,"","ant-design:ci-circle-filled","0","0","0","0","0",null,2.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_NACOS_SERVICE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_XXL_JOB","SYSTEM_MONITOR","xxlJob控制台","XxlJobJobInfo","xxlJob","/cloud/monitor/xxlJob/index",null,"jobInfo:view","ant-design:field-time-outlined","0","0","0","0","0",null,4.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_XXL_JOB"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_XXL_JOB_JOB_LOG","SYSTEM_MONITOR","xxlJob日志界面","XxlJobLog","jobLog/:jobId/:jobGroup/:executorHandler/:jobDesc","/cloud/monitor/xxlJob/log",null,"jobLog:view","ant-design:account-book-outlined","0","1","1","0","0",null,5.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_XXL_JOB_JOB_LOG"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_RABBITMQ","SYSTEM_MONITOR","rabbitmq控制台",null,"rabbitmq",null,null,"rabbitmq:view","ant-design:pull-request-outlined","0","0","0","0","0",null,5.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_RABBITMQ"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_RABBITMQ_EXCHANGE","SYSTEM_MONITOR_RABBITMQ","交换机","RabbitmqExchange","exchange","/cloud/monitor/rabbitmq/exchange/index",null,null,"ant-design:node-expand-outlined","0","0","0","0","0",null,1.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_RABBITMQ_EXCHANGE"');
call insert_if_not_exists(database(), 'system_menu',
                          'menu_code,parent_code,menu_name,keep_alive_name,path,component,redirect,perms,icon,type,hide_menu,ignore_keep_alive,hide_breadcrumb,hide_children_in_menu,current_active_menu,order_num,create_at,create_by',
                          '"SYSTEM_MONITOR_RABBITMQ_QUEUE","SYSTEM_MONITOR_RABBITMQ","队列","RabbitmqQueue","queue","/cloud/monitor/rabbitmq/queue/index",null,null,"ant-design:pull-request-outlined","0","0","0","0","0",null,2.0,now(),"system"',
                          'menu_code="SYSTEM_MONITOR_RABBITMQ_QUEUE"');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER_DETAIL"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER_DETAIL")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD_MENU"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD_MENU")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "DASHBOARD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "DASHBOARD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "ANALYSIS"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "ANALYSIS")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "WORKBENCH"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "WORKBENCH")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:DECRYPT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:DECRYPT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:UPDATE_STATUS"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:UPDATE_STATUS")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "ADMIN"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "ADMIN") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_USER"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_USER_DETAIL"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER_DETAIL")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD_MENU"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD_MENU")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "DASHBOARD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "DASHBOARD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "ANALYSIS"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "ANALYSIS")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "WORKBENCH"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "WORKBENCH")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "FILE_MANAGER"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "GUEST"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "GUEST") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER_DETAIL"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER_DETAIL")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD_MENU"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:RESET_PASSWORD_MENU")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "DASHBOARD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "DASHBOARD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "ANALYSIS"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "ANALYSIS")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "WORKBENCH"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "WORKBENCH")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GEN"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GEN")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GEN_CONFIG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GEN_CONFIG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GEN_GEN"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GEN_GEN")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GEN_CONFIG:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GEN_CONFIG:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GEN_GEN:GEN"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GEN_GEN:GEN")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:DECRYPT"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:DECRYPT")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_ROLE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MENU:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MENU:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DEPT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_DICT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_DICT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_CLIENT:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_ROUTE_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_RULE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_RATE_LIMIT_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLACK_LIST:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GATEWAY_BLOCK_LOG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:UPDATE_STATUS"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:UPDATE_STATUS")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_USER:VIEW_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_FILE:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:UPDATE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "FILE_MANAGER_BUCKET:UPDATE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GEN_CONFIG:ADD"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GEN_CONFIG:ADD")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GEN_CONFIG:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GEN_CONFIG:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "GEN_CONFIG:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "GEN_CONFIG:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_REDIS"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_REDIS")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_REDIS:VIEW"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_REDIS:VIEW")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_REDIS:DELETE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_REDIS:DELETE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_MYSQL"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_MYSQL")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_NACOS"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_NACOS")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_NACOS_CONFIG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_NACOS_CONFIG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_NACOS_SERVICE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_NACOS_SERVICE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_XXL_JOB"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_XXL_JOB")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_XXL_JOB_JOB_LOG"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_XXL_JOB_JOB_LOG")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_RABBITMQ"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_RABBITMQ")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_RABBITMQ_EXCHANGE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_RABBITMQ_EXCHANGE")');
call insert_if_not_exists(database(), 'system_role_menu', 'role_id,menu_id,create_at,create_by',
                          '(select role_id from system_role where role_code = "DEVELOP"),(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_RABBITMQ_QUEUE"),now(),"system"',
                          'role_id=(select role_id from system_role where role_code = "DEVELOP") and menu_id=(select menu_id from system_menu where menu_code = "SYSTEM_MONITOR_RABBITMQ_QUEUE")');
