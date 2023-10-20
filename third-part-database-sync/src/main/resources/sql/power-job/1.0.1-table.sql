SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS `app_info`
(
    `id`             bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `app_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `current_server` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `gmt_create`     datetime(6)                                                   NULL DEFAULT NULL,
    `gmt_modified`   datetime(6)                                                   NULL DEFAULT NULL,
    `password`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uidx01_app_info` (`app_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `container_info`
(
    `id`               bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `app_id`           bigint(20)                                                    NULL DEFAULT NULL,
    `container_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `gmt_create`       datetime(6)                                                   NULL DEFAULT NULL,
    `gmt_modified`     datetime(6)                                                   NULL DEFAULT NULL,
    `last_deploy_time` datetime(6)                                                   NULL DEFAULT NULL,
    `source_info`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `source_type`      int(11)                                                       NULL DEFAULT NULL,
    `status`           int(11)                                                       NULL DEFAULT NULL,
    `version`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx01_container_info` (`app_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `instance_info`
(
    `id`                    bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `actual_trigger_time`   bigint(20)                                                    NULL DEFAULT NULL,
    `app_id`                bigint(20)                                                    NULL DEFAULT NULL,
    `expected_trigger_time` bigint(20)                                                    NULL DEFAULT NULL,
    `finished_time`         bigint(20)                                                    NULL DEFAULT NULL,
    `gmt_create`            datetime(6)                                                   NULL DEFAULT NULL,
    `gmt_modified`          datetime(6)                                                   NULL DEFAULT NULL,
    `instance_id`           bigint(20)                                                    NULL DEFAULT NULL,
    `instance_params`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci     NULL,
    `job_id`                bigint(20)                                                    NULL DEFAULT NULL,
    `job_params`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci     NULL,
    `last_report_time`      bigint(20)                                                    NULL DEFAULT NULL,
    `result`                longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci     NULL,
    `running_times`         bigint(20)                                                    NULL DEFAULT NULL,
    `status`                int(11)                                                       NULL DEFAULT NULL,
    `task_tracker_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `type`                  int(11)                                                       NULL DEFAULT NULL,
    `wf_instance_id`        bigint(20)                                                    NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx01_instance_info` (`job_id`, `status`) USING BTREE,
    INDEX `idx02_instance_info` (`app_id`, `status`) USING BTREE,
    INDEX `idx03_instance_info` (`instance_id`, `status`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `job_info`
(
    `id`                   bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `alarm_config`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `app_id`               bigint(20)                                                    NULL DEFAULT NULL,
    `concurrency`          int(11)                                                       NULL DEFAULT NULL,
    `designated_workers`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `dispatch_strategy`    int(11)                                                       NULL DEFAULT NULL,
    `execute_type`         int(11)                                                       NULL DEFAULT NULL,
    `extra`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `gmt_create`           datetime(6)                                                   NULL DEFAULT NULL,
    `gmt_modified`         datetime(6)                                                   NULL DEFAULT NULL,
    `instance_retry_num`   int(11)                                                       NULL DEFAULT NULL,
    `instance_time_limit`  bigint(20)                                                    NULL DEFAULT NULL,
    `job_description`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `job_name`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `job_params`           longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci     NULL,
    `lifecycle`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `log_config`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `max_instance_num`     int(11)                                                       NULL DEFAULT NULL,
    `max_worker_count`     int(11)                                                       NULL DEFAULT NULL,
    `min_cpu_cores`        double                                                        NOT NULL,
    `min_disk_space`       double                                                        NOT NULL,
    `min_memory_space`     double                                                        NOT NULL,
    `next_trigger_time`    bigint(20)                                                    NULL DEFAULT NULL,
    `notify_user_ids`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `processor_info`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `processor_type`       int(11)                                                       NULL DEFAULT NULL,
    `status`               int(11)                                                       NULL DEFAULT NULL,
    `tag`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `task_retry_num`       int(11)                                                       NULL DEFAULT NULL,
    `time_expression`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `time_expression_type` int(11)                                                       NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx01_job_info` (`app_id`, `status`, `time_expression_type`, `next_trigger_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `oms_lock`
(
    `id`            bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `gmt_create`    datetime(6)                                                   NULL DEFAULT NULL,
    `gmt_modified`  datetime(6)                                                   NULL DEFAULT NULL,
    `lock_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `max_lock_time` bigint(20)                                                    NULL DEFAULT NULL,
    `ownerip`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uidx01_oms_lock` (`lock_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `server_info`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `gmt_create`   datetime(6)                                                   NULL DEFAULT NULL,
    `gmt_modified` datetime(6)                                                   NULL DEFAULT NULL,
    `ip`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uidx01_server_info` (`ip`) USING BTREE,
    INDEX `idx01_server_info` (`gmt_modified`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `user_info`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `email`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `extra`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `gmt_create`   datetime(6)                                                   NULL DEFAULT NULL,
    `gmt_modified` datetime(6)                                                   NULL DEFAULT NULL,
    `password`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `phone`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `username`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `web_hook`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `uidx01_user_info` (`username`) USING BTREE,
    INDEX `uidx02_user_info` (`email`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `workflow_info`
(
    `id`                   bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `app_id`               bigint(20)                                                    NULL DEFAULT NULL,
    `extra`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `gmt_create`           datetime(6)                                                   NULL DEFAULT NULL,
    `gmt_modified`         datetime(6)                                                   NULL DEFAULT NULL,
    `lifecycle`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `max_wf_instance_num`  int(11)                                                       NULL DEFAULT NULL,
    `next_trigger_time`    bigint(20)                                                    NULL DEFAULT NULL,
    `notify_user_ids`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `pedag`                longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci     NULL,
    `status`               int(11)                                                       NULL DEFAULT NULL,
    `time_expression`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `time_expression_type` int(11)                                                       NULL DEFAULT NULL,
    `wf_description`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `wf_name`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx01_workflow_info` (`app_id`, `status`, `time_expression_type`, `next_trigger_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `workflow_instance_info`
(
    `id`                    bigint(20)                                                NOT NULL AUTO_INCREMENT,
    `actual_trigger_time`   bigint(20)                                                NULL DEFAULT NULL,
    `app_id`                bigint(20)                                                NULL DEFAULT NULL,
    `dag`                   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `expected_trigger_time` bigint(20)                                                NULL DEFAULT NULL,
    `finished_time`         bigint(20)                                                NULL DEFAULT NULL,
    `gmt_create`            datetime(6)                                               NULL DEFAULT NULL,
    `gmt_modified`          datetime(6)                                               NULL DEFAULT NULL,
    `parent_wf_instance_id` bigint(20)                                                NULL DEFAULT NULL,
    `result`                longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `status`                int(11)                                                   NULL DEFAULT NULL,
    `wf_context`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `wf_init_params`        longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `wf_instance_id`        bigint(20)                                                NULL DEFAULT NULL,
    `workflow_id`           bigint(20)                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uidx01_wf_instance` (`wf_instance_id`) USING BTREE,
    INDEX `idx01_wf_instance` (`workflow_id`, `status`, `app_id`, `expected_trigger_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `workflow_node_info`
(
    `id`               bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `app_id`           bigint(20)                                                    NOT NULL,
    `enable`           bit(1)                                                        NOT NULL,
    `extra`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci     NULL,
    `gmt_create`       datetime(6)                                                   NOT NULL,
    `gmt_modified`     datetime(6)                                                   NOT NULL,
    `job_id`           bigint(20)                                                    NULL DEFAULT NULL,
    `node_name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `node_params`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci     NULL,
    `skip_when_failed` bit(1)                                                        NOT NULL,
    `type`             int(11)                                                       NULL DEFAULT NULL,
    `workflow_id`      bigint(20)                                                    NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx01_workflow_node_info` (`workflow_id`, `gmt_create`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;
