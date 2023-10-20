call insert_if_not_exists(database(), 'app_info', 'app_name,gmt_create,gmt_modified,password','"{{NACOS_NAMESPACE}}-platform-system",now(),now(),123456','app_name="{{NACOS_NAMESPACE}}-platform-system"');
call insert_if_not_exists(database(), 'job_info', 'app_id, concurrency, designated_workers, dispatch_strategy, execute_type, extra,
gmt_create, gmt_modified, instance_retry_num, instance_time_limit, job_description, job_name,
job_params, max_instance_num, max_worker_count, min_cpu_cores, min_disk_space,
min_memory_space, next_trigger_time, notify_user_ids, processor_info, processor_type,
status, tag, task_retry_num, time_expression, time_expression_type
','(select id from app_info where app_name = "{{NACOS_NAMESPACE}}-platform-system"),5,"",1,2,null,now(),now(),0,0,"刷新限流配置和黑名单配置","refreshBlackListAndRateLimitRules",null,0,0,0.0,0.0,0.0,null,null,"powerJobTaskHandler#refreshBlackListAndRateLimitRules",1,1,null,1,"0 0/30 * * * ?",2','app_id=(select id from app_info where app_name = "{{NACOS_NAMESPACE}}-platform-system") and job_name="refreshBlackListAndRateLimitRules"');
call insert_if_not_exists(database(), 'job_info', 'app_id, concurrency, designated_workers, dispatch_strategy, execute_type, extra,
gmt_create, gmt_modified, instance_retry_num, instance_time_limit, job_description, job_name,
job_params, max_instance_num, max_worker_count, min_cpu_cores, min_disk_space,
min_memory_space, next_trigger_time, notify_user_ids, processor_info, processor_type,
status, tag, task_retry_num, time_expression, time_expression_type
','(select id from app_info where app_name = "{{NACOS_NAMESPACE}}-platform-system"),5,"",1,2,null,now(),now(),0,0,"刷新sa-same-token","refreshSaSameToken",null,0,0,0.0,0.0,0.0,null,null,"powerJobTaskHandler#refreshSaSameToken",1,1,null,1,"0 0 0/1 * * ?",2','app_id=(select id from app_info where app_name = "{{NACOS_NAMESPACE}}-platform-system") and job_name="refreshSaSameToken"');
call insert_if_not_exists(database(), 'job_info', 'app_id, concurrency, designated_workers, dispatch_strategy, execute_type, extra,
gmt_create, gmt_modified, instance_retry_num, instance_time_limit, job_description, job_name,
job_params, max_instance_num, max_worker_count, min_cpu_cores, min_disk_space,
min_memory_space, next_trigger_time, notify_user_ids, processor_info, processor_type,
status, tag, task_retry_num, time_expression, time_expression_type
','(select id from app_info where app_name = "{{NACOS_NAMESPACE}}-platform-system"),5,"",1,2,null,now(),now(),0,0,"清理日志表","cleanLog","5000",0,0,0.0,0.0,0.0,null,null,"powerJobTaskHandler#cleanLog",1,1,null,1,"0 0 0 1/1 * ?",2','app_id=(select id from app_info where app_name = "{{NACOS_NAMESPACE}}-platform-system") and job_name="cleanLog"');
call insert_if_not_exists(database(), 'app_info', 'app_name,gmt_create,gmt_modified,password','"{{NACOS_NAMESPACE}}-platform-maintenance",now(),now(),123456','app_name="{{NACOS_NAMESPACE}}-platform-maintenance"');
call insert_if_not_exists(database(), 'app_info', 'app_name,gmt_create,gmt_modified,password','"{{NACOS_NAMESPACE}}-platform-gateway",now(),now(),123456','app_name="{{NACOS_NAMESPACE}}-platform-gateway"');
call insert_if_not_exists(database(), 'app_info', 'app_name,gmt_create,gmt_modified,password','"{{NACOS_NAMESPACE}}-test-test",now(),now(),123456','app_name="{{NACOS_NAMESPACE}}-test-test"');
