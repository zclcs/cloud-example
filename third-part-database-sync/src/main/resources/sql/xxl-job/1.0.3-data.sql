call insert_if_not_exists(database(), 'xxl_job_group', 'app_name,title,address_type,address_list,update_time',
                          '"{{NACOS_NAMESPACE}}-platform-system","系统服务",0,NULL,now()',
                          'app_name="{{NACOS_NAMESPACE}}-platform-system"');
call insert_if_not_exists(database(), 'xxl_job_info', 'job_group,job_desc,add_time,update_time,author,alarm_email,schedule_type,schedule_conf,misfire_strategy,
executor_route_strategy,executor_handler,executor_param,executor_block_strategy,executor_timeout,executor_fail_retry_count,
glue_type,glue_source,glue_remark,glue_updatetime,child_jobid,trigger_status,trigger_last_time,trigger_next_time
',
                          '(select id from xxl_job_group where app_name = "{{NACOS_NAMESPACE}}-platform-system"),"刷新限流配置和黑名单配置",now(),now(),"zclcs","","CRON","0 0/30 * * * ?","FIRE_ONCE_NOW","ROUND","refreshBlackListAndRateLimitRules","","DISCARD_LATER",0,0,"BEAN","","GLUE代码初始化",now(),"",1,0,0',
                          'job_group=(select id from xxl_job_group where app_name = "{{NACOS_NAMESPACE}}-platform-system") and executor_handler="refreshBlackListAndRateLimitRules"');
call insert_if_not_exists(database(), 'xxl_job_info', 'job_group,job_desc,add_time,update_time,author,alarm_email,schedule_type,schedule_conf,misfire_strategy,
executor_route_strategy,executor_handler,executor_param,executor_block_strategy,executor_timeout,executor_fail_retry_count,
glue_type,glue_source,glue_remark,glue_updatetime,child_jobid,trigger_status,trigger_last_time,trigger_next_time
',
                          '(select id from xxl_job_group where app_name = "{{NACOS_NAMESPACE}}-platform-system"),"定时清理日志表",now(),now(),"zclcs","","CRON","0 0 0 1/1 * ?","FIRE_ONCE_NOW","ROUND","cleanLog","5000","DISCARD_LATER",0,0,"BEAN","","GLUE代码初始化",now(),"",1,0,0',
                          'job_group=(select id from xxl_job_group where app_name = "{{NACOS_NAMESPACE}}-platform-system") and executor_handler="cleanLog"');

call insert_if_not_exists(database(), 'xxl_job_group', 'app_name,title,address_type,address_list,update_time',
                          '"{{NACOS_NAMESPACE}}-platform-maintenance","监控服务",0,NULL,now()',
                          'app_name="{{NACOS_NAMESPACE}}-platform-maintenance"');
