call insert_or_update(database(), 'xxl_job_user',
                      'username, password, role, permission',
                      '("admin", "e10adc3949ba59abbe56e057f20f883e", 1, NULL)',
                      'username = values(username)');//

call insert_or_update(database(), 'xxl_job_lock',
                      'lock_name',
                      '("schedule_lock")',
                      'lock_name = values(lock_name)');//

call insert_or_update(database(), 'xxl_job_group',
                      'app_name, title, address_type, address_list, update_time',
                      '("{{NACOS_NAMESPACE}}-platform-system", "系统服务", 0, NULL, now())',
                      'app_name = values(app_name)');//

call insert_or_update(database(), 'xxl_job_info',
                      'job_group, job_desc, add_time, update_time, author, alarm_email, schedule_type, schedule_conf, misfire_strategy, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid, trigger_status, trigger_last_time, trigger_next_time',
                      '((select id from xxl_job_group where app_name = "{{NACOS_NAMESPACE}}-platform-system"), "刷新限流配置和黑名单配置", now(), now(), "zclcs", "", "CRON", "0 0/30 * * * ?", "DO_NOTHING", "FIRST", "refreshBlackListAndRateLimitRules", "", "SERIAL_EXECUTION", 0, 0, "BEAN", "", "GLUE代码初始化", now(), "", 1, 0, 0)',
                      'job_group = values(job_group), executor_handler = values(executor_handler)');//