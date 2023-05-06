call insert_or_update(database(), 'xxl_job_user',
                    '(`id`, `username`, `password`, `role`, `permission`) VALUES (1, "admin", "e10adc3949ba59abbe56e057f20f883e", 1, NULL)',
                    'SET `username` = "admin", `password` = "e10adc3949ba59abbe56e057f20f883e", `role` = 1, `permission` = NULL WHERE `id` = 1');//

call insert_or_update(database(), 'xxl_job_lock',
                    '(`lock_name`) VALUES ("schedule_lock")',
                    'SET `lock_name` = "schedule_lock" WHERE `lock_name` = "schedule_lock"');//

call insert_or_update(database(), 'xxl_job_group',
                    '(`app_name`, `title`, `address_type`, `address_list`, `update_time`)
VALUES ("{{NACOS_NAMESPACE}}-platform-system", "系统服务", 0, NULL, now())',
                    'SET `title` = "系统服务", `address_type` = 0, `address_list` = NULL,
`update_time` = now() WHERE `app_name` = "{{NACOS_NAMESPACE}}-platform-system"');//

call insert_or_update(database(), 'xxl_job_info',
                    '(`job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`,
`executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`,
`glue_source`, `glue_remark`, `glue_updatetime`,`child_jobid`, `trigger_status`, `trigger_last_time`, `trigger_next_time`)
VALUES ((select id from xxl_job_group where app_name = "{{NACOS_NAMESPACE}}-platform-system"), "刷新限流配置和黑名单配置", now(),
now(), "zclcs", "", "CRON", "0 0/30 * * * ?", "DO_NOTHING", "FIRST", "refreshBlackListAndRateLimitRules", "", "SERIAL_EXECUTION", 0, 0, "BEAN",
"", "GLUE代码初始化", now(), "", 1, 0, 0)',
                    'SET `job_desc` = "刷新限流配置和黑名单配置", `add_time` = now(), `update_time` = now(),
`author` = "zclcs", `alarm_email` = "", `schedule_type` = "CRON",`schedule_conf` = "0 0/30 * * * ?", `misfire_strategy` = "DO_NOTHING",
`executor_route_strategy` = "FIRST",`executor_param` = "", `executor_block_strategy` = "SERIAL_EXECUTION",`executor_timeout` = 0,
`executor_fail_retry_count` = 0, `glue_type` = "BEAN", `glue_source` = "", `glue_remark` = "GLUE代码初始化",`glue_updatetime` = now(), `child_jobid` = "", `trigger_status` = 1
WHERE `job_group` = (select id from xxl_job_group where app_name = "{{NACOS_NAMESPACE}}-platform-system") and `executor_handler` = "refreshBlackListAndRateLimitRules"');//