CREATE DATABASE /*!32312 IF NOT EXISTS */ `third_part_canal_tsdb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `third_part_canal_tsdb`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meta_history
-- ----------------------------
DROP TABLE IF EXISTS `meta_history`;
CREATE TABLE `meta_history`
(
    `id`               bigint(20) UNSIGNED                                      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       datetime                                                 NOT NULL COMMENT '创建时间',
    `gmt_modified`     datetime                                                 NOT NULL COMMENT '修改时间',
    `destination`      varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '通道名称',
    `binlog_file`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT 'binlog文件名',
    `binlog_offest`    bigint(20)                                               NULL DEFAULT NULL COMMENT 'binlog偏移量',
    `binlog_master_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT 'binlog节点id',
    `binlog_timestamp` bigint(20)                                               NULL DEFAULT NULL COMMENT 'binlog应用的时间戳',
    `use_schema`       varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行sql时对应的schema',
    `sql_schema`       varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的schema',
    `sql_table`        varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的table',
    `sql_text`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci      NULL COMMENT '执行的sql',
    `sql_type`         varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT 'sql类型',
    `extra`            text CHARACTER SET utf8 COLLATE utf8_general_ci          NULL COMMENT '额外的扩展信息',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `binlog_file_offest` (`destination`, `binlog_master_id`, `binlog_file`, `binlog_offest`) USING BTREE,
    INDEX `destination` (`destination`) USING BTREE,
    INDEX `destination_timestamp` (`destination`, `binlog_timestamp`) USING BTREE,
    INDEX `gmt_modified` (`gmt_modified`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '表结构变化明细表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for meta_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `meta_snapshot`;
CREATE TABLE `meta_snapshot`
(
    `id`               bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       datetime                                                NOT NULL COMMENT '创建时间',
    `gmt_modified`     datetime                                                NOT NULL COMMENT '修改时间',
    `destination`      varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通道名称',
    `binlog_file`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT 'binlog文件名',
    `binlog_offest`    bigint(20)                                              NULL DEFAULT NULL COMMENT 'binlog偏移量',
    `binlog_master_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT 'binlog节点id',
    `binlog_timestamp` bigint(20)                                              NULL DEFAULT NULL COMMENT 'binlog应用的时间戳',
    `data`             longtext CHARACTER SET utf8 COLLATE utf8_general_ci     NULL COMMENT '表结构数据',
    `extra`            text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '额外的扩展信息',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `binlog_file_offest` (`destination`, `binlog_master_id`, `binlog_file`, `binlog_offest`) USING BTREE,
    INDEX `destination` (`destination`) USING BTREE,
    INDEX `destination_timestamp` (`destination`, `binlog_timestamp`) USING BTREE,
    INDEX `gmt_modified` (`gmt_modified`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '表结构记录表快照表'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
