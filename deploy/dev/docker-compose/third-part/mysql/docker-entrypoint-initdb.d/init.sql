SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `third_part_nacos`
    DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

USE `third_part_nacos`;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`
(
    `id`                 bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL,
    `content`            longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`                varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text CHARACTER SET utf8 COLLATE utf8_bin         NULL COMMENT 'source user',
    `src_ip`             varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'source ip',
    `app_name`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL,
    `tenant_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT '' COMMENT '租户字段',
    `c_desc`             varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL,
    `c_use`              varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `effect`             varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `type`               varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `c_schema`           text CHARACTER SET utf8 COLLATE utf8_bin         NULL,
    `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin         NOT NULL COMMENT '秘钥',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info`
VALUES (1, 'tomcat.yaml', 'DEFAULT_GROUP',
        'server:\r\n  tomcat:\r\n    threads:\r\n      max: 500\r\n      min-spare: 20\r\n    accept-count: 300',
        '9c9c1fe1f8fb8b09ebea47de327ddf2f', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', 'tomcat统一配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (2, 'global-configuration.yaml', 'DEFAULT_GROUP',
        'my:\n  redis-cache-prefix: \'${spring.cloud.nacos.config.namespace}:\'\n  default-password: 123456',
        '3772acde9e08f925918027e7082f687b', '2023-03-24 17:13:24', '2023-03-31 11:31:24', 'nacos', '192.168.33.1', '',
        'dev', '系统参数统一配置', '', '', 'yaml', '', '');
INSERT INTO `config_info`
VALUES (3, 'openfeign.yaml', 'DEFAULT_GROUP',
        'spring:\n  cloud:\n    openfeign:\n      client:\n        config:\n          default:\n            logger-level: full\n            connect-timeout: 10000\n            read-timeout: 10000\n      okhttp:\n        enabled: true\n      httpclient:\n        enabled: false\n      compression:\n        request:\n          enabled: true\n        response:\n          enabled: true\nfeign:\n  sentinel:\n    enabled: true',
        '2cbdd9daed4debed965dfbef9aac5017', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', 'fegin远程调用统一配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (4, 'sentinel.yaml', 'DEFAULT_GROUP',
        'spring:\r\n  cloud:\r\n    sentinel:\r\n      eager: true\r\n      transport:\r\n        dashboard: ${NACOS_HOST:127.0.0.1}:${SENTINEL_PORT:8858}\r\n      filter:\r\n        enabled: false\r\n      datasource:\r\n        # 流控规则\r\n        flow:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-flow-rules\r\n            data-type: json\r\n            rule-type: flow\r\n        # 熔断降级\r\n        degrade:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-degrade-rules\r\n            data-type: json\r\n            rule-type: degrade\r\n        # 热点参数限流\r\n        param_flow:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-param-rules\r\n            data-type: json\r\n            rule-type: param_flow\r\n        # 系统规则\r\n        system:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-system-rules\r\n            data-type: json\r\n            rule-type: system\r\n        # 来源访问控制（黑白名单）\r\n        authority:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-auth-rules\r\n            data-type: json\r\n            rule-type: authority',
        'aecd3bfb4478f664414bbbbe6c821e8b', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', 'sentinel流量哨兵统一配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (5, 'hikari.yaml', 'DEFAULT_GROUP',
        'spring:\r\n  datasource:\r\n    hikari:\r\n      connection-timeout: 30000\r\n      max-lifetime: 1800000\r\n      maximum-pool-size: 15\r\n      minimum-idle: 5\r\n      connection-test-query: select 1',
        'b4b159e1e2ea46491ddf4a0e0917397f', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', 'hikari连接池统一配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (6, 'rabbitmq.yaml', 'DEFAULT_GROUP',
        'spring:\r\n  rabbitmq:\r\n    host: ${RABBIT_MQ_HOST:127.0.0.1}\r\n    port: ${RABBIT_MQ_PORT:5672}\r\n    username: ${RABBIT_MQ_USERNAME:root}\r\n    password: ${RABBIT_MQ_PASSWORD:123456}\r\n    virtual-host: /${spring.cloud.nacos.config.namespace}\r\n    # 手动提交消息\r\n    listener:\r\n      simple:\r\n        #消息必须手动确认\r\n        acknowledge-mode: manual\r\n        #限制每次发送一条数据。\r\n        prefetch: 1\r\n    publisher-returns: true\r\n    publisher-confirm-type: simple',
        'ab86ae2cf11d7416d39a724a1b9def01', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', 'rabbitmq配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (7, 'redis.yaml', 'DEFAULT_GROUP',
        'spring:\n  data:\n    redis:\n      database: ${REDIS_DATABASE:0}\n      host: ${REDIS_HOST:127.0.0.1}\n      port: ${REDIS_PORT:6379}\n      password: ${REDIS_PASSWORD:123456}\n      lettuce:\n        pool:\n          min-idle: 8\n          max-idle: 500\n          max-active: 2000\n          max-wait: 10000\n      timeout: 5000',
        '2a486148042b12896e458277106b9c59', '2023-03-24 17:13:24', '2023-03-27 11:51:15', 'nacos', '192.168.33.1', '',
        'dev', 'redis配置', '', '', 'yaml', '', '');
INSERT INTO `config_info`
VALUES (8, 'mybatis-plus.yaml', 'DEFAULT_GROUP',
        'mybatis-plus:\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\n  type-aliases-package: com.zclcs.platform.system.api.entity\r\n  mapper-locations: classpath:com/zclcs/platform/system/biz/mapper/*.xml',
        '5bbc120fcdd6fbce0b5b47466a517a33', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', 'mybatis-plus', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (9, 'jasypt.yaml', 'DEFAULT_GROUP',
        '# 加解密根密码\r\njasypt:\r\n  encryptor:\r\n    password: zclcs\r\n    algorithm: PBEWithMD5AndDES\r\n    iv-generator-classname: org.jasypt.iv.NoIvGenerator',
        'eaf193a7bdff16331a6b4a68d22bbe57', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', '加解密根密码', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (10, 'swagger.yaml', 'DEFAULT_GROUP',
        'swagger:\r\n  enabled: true\r\n  title: Swagger API\r\n  gateway: http://${GATEWAY_HOST:platform-gateway}:${PORT_GATEWAY:8301}\r\n  token-url: ${swagger.gateway}/auth/oauth2/token\r\n  scope: server\r\n  base-package: com.zclcs',
        'f15072788d118d22895646a906ad13e1', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', 'swagger', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (11, 'system-dict.yaml', 'DEFAULT_GROUP',
        'system:\r\n  dict:\r\n    text-value-default-null: true\r\n    cache:\r\n      enabled: true\r\n      maximum-size: 500\r\n      initial-capacity: 50\r\n      duration: 30s\r\n      miss-num: 50',
        '80074d97adf513b2794679d182e491d8', '2023-03-24 17:13:24', '2023-03-24 17:13:24', NULL, '192.168.33.1', '',
        'dev', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info`
VALUES (29, 'gateway-routes.json', 'DEFAULT_GROUP',
        '[\n  {\n    \"id\": \"platform-auth\",\n    \"uri\": \"lb://platform-auth\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/auth/**\"\n        }\n      }\n    ],\n    \"filters\": [\n      {\n        \"name\": \"ValidateCodeGatewayFilter\"\n      },\n      {\n        \"name\": \"PasswordDecoderFilter\"\n      }\n    ]\n  },\n  {\n    \"id\": \"platform-system\",\n    \"uri\": \"lb://platform-system\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/system/**\"\n        }\n      }\n    ]\n  }\n]',
        '7cc229f94674d9675e235ab2c4d03316', '2023-03-31 09:32:01', '2023-03-31 10:03:53', 'nacos', '192.168.33.1', '',
        'dev', '', '', '', 'json', '', '');
INSERT INTO `config_info`
VALUES (30, 'minio.yaml', 'DEFAULT_GROUP',
        'minio:\r\n  host: ${MINIO_HOST:127.0.0.1}\r\n  port: ${MINIO_PORT:9000}\r\n  endpoint: http://${minio.host}:${minio.port}\r\n  root-user: ${MINIO_ROOT_USER:admin}\r\n  root-password: ${MINIO_ROOT_PASSWORD:minioadmin}\r\n  domain-name: ${DOMAIN_NAME:}',
        '7941222b40d4f2b719d9bf06d468d64f', '2023-04-03 15:19:01', '2023-04-03 15:19:01', NULL, '192.168.33.1', '',
        'dev', NULL, NULL, NULL, 'yaml', NULL, '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`
(
    `id`           bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `datum_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT '内容',
    `gmt_modified` datetime                                         NOT NULL COMMENT '修改时间',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '增加租户字段'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`
(
    `id`                 bigint(20)                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'group_id',
    `app_name`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'app_name',
    `content`            longtext CHARACTER SET utf8 COLLATE utf8_bin      NOT NULL COMMENT 'content',
    `beta_ips`           varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL COMMENT 'betaIps',
    `md5`                varchar(32) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text CHARACTER SET utf8 COLLATE utf8_bin          NULL COMMENT 'source user',
    `src_ip`             varchar(50) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'source ip',
    `tenant_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin          NOT NULL COMMENT '秘钥',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info_beta'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`
(
    `id`           bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin         NULL COMMENT 'source user',
    `src_ip`       varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info_tag'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`
(
    `id`        bigint(20)                                       NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint(20)                                       NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`) USING BTREE,
    UNIQUE INDEX `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`) USING BTREE,
    INDEX `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_tag_relation'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`
(
    `id`                bigint(20) UNSIGNED                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
    `usage`             int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '使用量',
    `max_size`          int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
    `gmt_create`        datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_group_id` (`group_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`
(
    `id`                 bigint(20) UNSIGNED                              NOT NULL,
    `nid`                bigint(20) UNSIGNED                              NOT NULL AUTO_INCREMENT,
    `data_id`            varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `group_id`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `app_name`           varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL COMMENT 'app_name',
    `content`            longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL,
    `md5`                varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `gmt_create`         datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified`       datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `src_user`           text CHARACTER SET utf8 COLLATE utf8_bin         NULL,
    `src_ip`             varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `op_type`            char(10) CHARACTER SET utf8 COLLATE utf8_bin     NULL     DEFAULT NULL,
    `tenant_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin         NOT NULL COMMENT '秘钥',
    PRIMARY KEY (`nid`) USING BTREE,
    INDEX `idx_gmt_create` (`gmt_create`) USING BTREE,
    INDEX `idx_gmt_modified` (`gmt_modified`) USING BTREE,
    INDEX `idx_did` (`data_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 42
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '多租户改造'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info`
VALUES (0, 1, 'tomcat.yaml', 'DEFAULT_GROUP', '',
        'server:\r\n  tomcat:\r\n    threads:\r\n      max: 500\r\n      min-spare: 20\r\n    accept-count: 300',
        '9c9c1fe1f8fb8b09ebea47de327ddf2f', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 2, 'global-configuration.yaml', 'DEFAULT_GROUP', '',
        'my:\r\n  redis-cache-prefix: \'${spring.cloud.nacos.config.namespace}:\'\r\n  default-password: 123456',
        'adf7ac2b60b1ecdec8ea11937559de71', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 3, 'openfeign.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    openfeign:\n      client:\n        config:\n          default:\n            logger-level: full\n            connect-timeout: 10000\n            read-timeout: 10000\n      okhttp:\n        enabled: true\n      httpclient:\n        enabled: false\n      compression:\n        request:\n          enabled: true\n        response:\n          enabled: true\nfeign:\n  sentinel:\n    enabled: true',
        '2cbdd9daed4debed965dfbef9aac5017', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 4, 'sentinel.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  cloud:\r\n    sentinel:\r\n      eager: true\r\n      transport:\r\n        dashboard: ${NACOS_HOST:127.0.0.1}:${SENTINEL_PORT:8858}\r\n      filter:\r\n        enabled: false\r\n      datasource:\r\n        # 流控规则\r\n        flow:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-flow-rules\r\n            data-type: json\r\n            rule-type: flow\r\n        # 熔断降级\r\n        degrade:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-degrade-rules\r\n            data-type: json\r\n            rule-type: degrade\r\n        # 热点参数限流\r\n        param_flow:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-param-rules\r\n            data-type: json\r\n            rule-type: param_flow\r\n        # 系统规则\r\n        system:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-system-rules\r\n            data-type: json\r\n            rule-type: system\r\n        # 来源访问控制（黑白名单）\r\n        authority:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-auth-rules\r\n            data-type: json\r\n            rule-type: authority',
        'aecd3bfb4478f664414bbbbe6c821e8b', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 5, 'hikari.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  datasource:\r\n    hikari:\r\n      connection-timeout: 30000\r\n      max-lifetime: 1800000\r\n      maximum-pool-size: 15\r\n      minimum-idle: 5\r\n      connection-test-query: select 1',
        'b4b159e1e2ea46491ddf4a0e0917397f', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 6, 'rabbitmq.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  rabbitmq:\r\n    host: ${RABBIT_MQ_HOST:127.0.0.1}\r\n    port: ${RABBIT_MQ_PORT:5672}\r\n    username: ${RABBIT_MQ_USERNAME:root}\r\n    password: ${RABBIT_MQ_PASSWORD:123456}\r\n    virtual-host: /${spring.cloud.nacos.config.namespace}\r\n    # 手动提交消息\r\n    listener:\r\n      simple:\r\n        #消息必须手动确认\r\n        acknowledge-mode: manual\r\n        #限制每次发送一条数据。\r\n        prefetch: 1\r\n    publisher-returns: true\r\n    publisher-confirm-type: simple',
        'ab86ae2cf11d7416d39a724a1b9def01', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 7, 'redis.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  data:\r\n    redis:\r\n      database: ${REDIS_DATABASE:0}\r\n      host: ${REDIS_HOST:127.0.0.1}\r\n      port: ${REDIS_PORT:6379}\r\n      lettuce:\r\n        pool:\r\n          min-idle: 8\r\n          max-idle: 500\r\n          max-active: 2000\r\n          max-wait: 10000\r\n      timeout: 5000',
        '5918972f4533c49fa8fb70f49566395e', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 8, 'mybatis-plus.yaml', 'DEFAULT_GROUP', '',
        'mybatis-plus:\r\n  configuration:\r\n    jdbc-type-for-null: null\r\n  global-config:\r\n    banner: false\r\n  type-aliases-package: com.zclcs.platform.system.api.entity\r\n  mapper-locations: classpath:com/zclcs/platform/system/biz/mapper/*.xml',
        '5bbc120fcdd6fbce0b5b47466a517a33', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 9, 'jasypt.yaml', 'DEFAULT_GROUP', '',
        '# 加解密根密码\r\njasypt:\r\n  encryptor:\r\n    password: zclcs\r\n    algorithm: PBEWithMD5AndDES\r\n    iv-generator-classname: org.jasypt.iv.NoIvGenerator',
        'eaf193a7bdff16331a6b4a68d22bbe57', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 10, 'swagger.yaml', 'DEFAULT_GROUP', '',
        'swagger:\r\n  enabled: true\r\n  title: Swagger API\r\n  gateway: http://${GATEWAY_HOST:platform-gateway}:${PORT_GATEWAY:8301}\r\n  token-url: ${swagger.gateway}/auth/oauth2/token\r\n  scope: server\r\n  base-package: com.zclcs',
        'f15072788d118d22895646a906ad13e1', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 11, 'system-dict.yaml', 'DEFAULT_GROUP', '',
        'system:\r\n  dict:\r\n    text-value-default-null: true\r\n    cache:\r\n      enabled: true\r\n      maximum-size: 500\r\n      initial-capacity: 50\r\n      duration: 30s\r\n      miss-num: 50',
        '80074d97adf513b2794679d182e491d8', '2023-03-24 17:13:23', '2023-03-24 17:13:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (7, 12, 'redis.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  data:\r\n    redis:\r\n      database: ${REDIS_DATABASE:0}\r\n      host: ${REDIS_HOST:127.0.0.1}\r\n      port: ${REDIS_PORT:6379}\r\n      lettuce:\r\n        pool:\r\n          min-idle: 8\r\n          max-idle: 500\r\n          max-active: 2000\r\n          max-wait: 10000\r\n      timeout: 5000',
        '5918972f4533c49fa8fb70f49566395e', '2023-03-27 11:51:15', '2023-03-27 11:51:15', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 13, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  cloud:\r\n    gateway:\r\n      httpclient:\r\n        connect-timeout: 10000\r\n        response-timeout: 10s\r\n      loadbalancer:\r\n        use404: true\r\n      default-filters:\r\n        - StripPrefix=1\r\n      discovery:\r\n        locator:\r\n          enabled: true\r\n      routes:\r\n        # 认证中心\r\n        - id: platform-auth\r\n          uri: lb://platform-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            # 验证码处理\r\n            - ValidateCodeGatewayFilter\r\n            # 前端密码解密\r\n            - PasswordDecoderFilter\r\n        #system 模块\r\n        - id: platform-system\r\n          uri: lb://platform-system\r\n          predicates:\r\n            - Path=/system/**\r\n        # 固定路由转发配置 无修改\r\n        - id: openapi\r\n          uri: lb://platform-gateway\r\n          predicates:\r\n            - Path=/v3/api-docs/**\r\n          filters:\r\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs',
        '4672a484bd2abdc4ceadb08a90cd11d6', '2023-03-30 16:05:06', '2023-03-30 16:05:06', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (12, 14, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  cloud:\r\n    gateway:\r\n      httpclient:\r\n        connect-timeout: 10000\r\n        response-timeout: 10s\r\n      loadbalancer:\r\n        use404: true\r\n      default-filters:\r\n        - StripPrefix=1\r\n      discovery:\r\n        locator:\r\n          enabled: true\r\n      routes:\r\n        # 认证中心\r\n        - id: platform-auth\r\n          uri: lb://platform-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            # 验证码处理\r\n            - ValidateCodeGatewayFilter\r\n            # 前端密码解密\r\n            - PasswordDecoderFilter\r\n        #system 模块\r\n        - id: platform-system\r\n          uri: lb://platform-system\r\n          predicates:\r\n            - Path=/system/**\r\n        # 固定路由转发配置 无修改\r\n        - id: openapi\r\n          uri: lb://platform-gateway\r\n          predicates:\r\n            - Path=/v3/api-docs/**\r\n          filters:\r\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs',
        '4672a484bd2abdc4ceadb08a90cd11d6', '2023-03-30 16:08:23', '2023-03-30 16:08:24', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (12, 15, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs',
        'ac216abe56b60570c13bd6a77d1b3d87', '2023-03-30 16:08:58', '2023-03-30 16:08:58', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (12, 16, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        #system 模块\n        - id: platform-system\n          uri: lb://platform-system\n          predicates:\n            - Path=/system/**\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs',
        'f23758462e1cf79878d09c5501fcc853', '2023-03-30 16:09:06', '2023-03-30 16:09:07', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (12, 17, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs',
        'ac216abe56b60570c13bd6a77d1b3d87', '2023-03-30 16:10:13', '2023-03-30 16:10:14', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (12, 18, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs',
        'ac216abe56b60570c13bd6a77d1b3d87', '2023-03-30 16:16:12', '2023-03-30 16:16:13', NULL, '192.168.33.1', 'D',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 19, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  cloud:\r\n    gateway:\r\n      httpclient:\r\n        connect-timeout: 10000\r\n        response-timeout: 10s\r\n      loadbalancer:\r\n        use404: true\r\n      default-filters:\r\n        - StripPrefix=1\r\n      discovery:\r\n        locator:\r\n          enabled: true\r\n      routes:\r\n        # 认证中心\r\n        - id: platform-auth\r\n          uri: lb://platform-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            # 验证码处理\r\n            - ValidateCodeGatewayFilter\r\n            # 前端密码解密\r\n            - PasswordDecoderFilter\r\n        #system 模块\r\n        - id: platform-system\r\n          uri: lb://platform-system\r\n          predicates:\r\n            - Path=/system/**\r\n        # 固定路由转发配置 无修改\r\n        - id: openapi\r\n          uri: lb://pig-gateway\r\n          predicates:\r\n            - Path=/v3/api-docs/**\r\n          filters:\r\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\r\n',
        '1241bc464e4144a54c6c9c0caae71db0', '2023-03-30 16:17:24', '2023-03-30 16:17:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 20, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\r\n  cloud:\r\n    gateway:\r\n      httpclient:\r\n        connect-timeout: 10000\r\n        response-timeout: 10s\r\n      loadbalancer:\r\n        use404: true\r\n      default-filters:\r\n        - StripPrefix=1\r\n      discovery:\r\n        locator:\r\n          enabled: true\r\n      routes:\r\n        # 认证中心\r\n        - id: platform-auth\r\n          uri: lb://platform-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            # 验证码处理\r\n            - ValidateCodeGatewayFilter\r\n            # 前端密码解密\r\n            - PasswordDecoderFilter\r\n        #system 模块\r\n        - id: platform-system\r\n          uri: lb://platform-system\r\n          predicates:\r\n            - Path=/system/**\r\n        # 固定路由转发配置 无修改\r\n        - id: openapi\r\n          uri: lb://pig-gateway\r\n          predicates:\r\n            - Path=/v3/api-docs/**\r\n          filters:\r\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\r\n',
        '1241bc464e4144a54c6c9c0caae71db0', '2023-03-30 16:20:35', '2023-03-30 16:20:36', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 21, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://pig-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n',
        'cc4374ef1b838babb54f7ad4878a5d84', '2023-03-30 16:22:30', '2023-03-30 16:22:31', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 22, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n',
        '1f5945388d030de6de278286f706d58d', '2023-03-30 16:24:24', '2023-03-30 16:24:25', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 23, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        #system 模块\n        - id: platform-system\n          uri: lb://platform-system\n          predicates:\n            - Path=/system/**\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n',
        '7eff6a5f339339d2493859f2432ab948', '2023-03-30 16:27:22', '2023-03-30 16:27:23', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 24, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n',
        '1f5945388d030de6de278286f706d58d', '2023-03-30 16:29:24', '2023-03-30 16:29:24', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 25, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        #system 模块\n        - id: platform-system\n          uri: lb://platform-system\n          predicates:\n            - Path=/system/**\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n',
        '7eff6a5f339339d2493859f2432ab948', '2023-03-30 17:12:13', '2023-03-30 17:12:14', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 26, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        #system 模块\n        - id: platform-system\n          uri: lb://platform-system\n          predicates:\n            - Path=/system/**\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n',
        '7eff6a5f339339d2493859f2432ab948', '2023-03-30 17:15:51', '2023-03-30 17:15:52', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 27, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n',
        '1f5945388d030de6de278286f706d58d', '2023-03-30 17:17:12', '2023-03-30 17:17:12', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 28, 'platform-gateway.yaml', 'DEFAULT_GROUP', '',
        'server:\r\n\r\n  port: ${PORT_PLATFORM_GATEWAY:8301}\r\n\r\nspring:\r\n\r\n  profiles:\r\n    active: ${SPRING_PROFILES_ACTIVE:dev}\r\n\r\n  main:\r\n    allow-bean-definition-overriding: true\r\n\r\n  cloud:\r\n    gateway:\r\n      httpclient:\r\n        connect-timeout: 10000\r\n        response-timeout: 10s\r\n      loadbalancer:\r\n        use404: true\r\n      default-filters:\r\n        - StripPrefix=1\r\n      discovery:\r\n        locator:\r\n          enabled: true\r\n      routes:\r\n        # 认证中心\r\n        - id: platform-auth\r\n          uri: lb://platform-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            # 验证码处理\r\n            - ValidateCodeGatewayFilter\r\n            # 前端密码解密\r\n            - PasswordDecoderFilter\r\n        #system 模块\r\n        - id: platform-system\r\n          uri: lb://platform-system\r\n          predicates:\r\n            - Path=/system/**\r\n        # 固定路由转发配置 无修改\r\n        - id: openapi\r\n          uri: lb://platform-gateway\r\n          predicates:\r\n            - Path=/v3/api-docs/**\r\n          filters:\r\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\r\n\r\ngateway:\r\n  encode-key: \'eXTqsEKIPRsksJSK\'\r\n  ignore-clients:\r\n    - swagger\r\n  services: { \"platform-system\": \"/system\" }\r\n\r\nlogging:\r\n  level:\r\n    com.zclcs: debug\r\n\r\n# spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: ENC(imENTO7M8bLO38LFSIxnzw==)\r\n      client-secret: ENC(i3cDFhs26sa2Ucrfz2hnQw==)\r\n      scope: server\r\n    # 通用放行URL，服务个性化，请在对应配置文件覆盖\r\n    ignore:\r\n      urls:\r\n        - /v3/api-docs\r\n        - /actuator/**\r\n',
        'f1d067e659f747a7b9b109e6ffd2a509', '2023-03-31 09:07:23', '2023-03-31 09:07:24', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (26, 29, 'platform-gateway.yaml', 'DEFAULT_GROUP', '',
        'server:\r\n\r\n  port: ${PORT_PLATFORM_GATEWAY:8301}\r\n\r\nspring:\r\n\r\n  profiles:\r\n    active: ${SPRING_PROFILES_ACTIVE:dev}\r\n\r\n  main:\r\n    allow-bean-definition-overriding: true\r\n\r\n  cloud:\r\n    gateway:\r\n      httpclient:\r\n        connect-timeout: 10000\r\n        response-timeout: 10s\r\n      loadbalancer:\r\n        use404: true\r\n      default-filters:\r\n        - StripPrefix=1\r\n      discovery:\r\n        locator:\r\n          enabled: true\r\n      routes:\r\n        # 认证中心\r\n        - id: platform-auth\r\n          uri: lb://platform-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            # 验证码处理\r\n            - ValidateCodeGatewayFilter\r\n            # 前端密码解密\r\n            - PasswordDecoderFilter\r\n        #system 模块\r\n        - id: platform-system\r\n          uri: lb://platform-system\r\n          predicates:\r\n            - Path=/system/**\r\n        # 固定路由转发配置 无修改\r\n        - id: openapi\r\n          uri: lb://platform-gateway\r\n          predicates:\r\n            - Path=/v3/api-docs/**\r\n          filters:\r\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\r\n\r\ngateway:\r\n  encode-key: \'eXTqsEKIPRsksJSK\'\r\n  ignore-clients:\r\n    - swagger\r\n  services: { \"platform-system\": \"/system\" }\r\n\r\nlogging:\r\n  level:\r\n    com.zclcs: debug\r\n\r\n# spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: ENC(imENTO7M8bLO38LFSIxnzw==)\r\n      client-secret: ENC(i3cDFhs26sa2Ucrfz2hnQw==)\r\n      scope: server\r\n    # 通用放行URL，服务个性化，请在对应配置文件覆盖\r\n    ignore:\r\n      urls:\r\n        - /v3/api-docs\r\n        - /actuator/**\r\n',
        'f1d067e659f747a7b9b109e6ffd2a509', '2023-03-31 09:09:08', '2023-03-31 09:09:09', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (26, 30, 'platform-gateway.yaml', 'DEFAULT_GROUP', '',
        'server:\n\n  port: ${PORT_PLATFORM_GATEWAY:8301}\n\nspring:\n\n  profiles:\n    active: ${SPRING_PROFILES_ACTIVE:dev}\n\n  main:\n    allow-bean-definition-overriding: true\n\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n\ngateway:\n  encode-key: \'eXTqsEKIPRsksJSK\'\n  ignore-clients:\n    - swagger\n  services: { \"platform-system\": \"/system\" }\n\nlogging:\n  level:\n    com.zclcs: debug\n\n# spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(imENTO7M8bLO38LFSIxnzw==)\n      client-secret: ENC(i3cDFhs26sa2Ucrfz2hnQw==)\n      scope: server\n    # 通用放行URL，服务个性化，请在对应配置文件覆盖\n    ignore:\n      urls:\n        - /v3/api-docs\n        - /actuator/**\n',
        '8cfcacfb62fbb3d99dee6832eb6efb13', '2023-03-31 09:11:11', '2023-03-31 09:11:11', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 31, 'gateway-routes.json', 'DEFAULT_GROUP', '',
        '[\r\n  {\r\n    \"id\": \"platform-auth\",\r\n    \"uri\": \"lb://platform-auth\",\r\n    \"predicates\": [\r\n      {\r\n        \"name\": \"Path\",\r\n        \"args\": {\r\n          \"pattern\": \"/a/**\"\r\n        }\r\n      }\r\n    ],\r\n    \"filters\": [\r\n      {\r\n        \"name\": \"StripPrefix\",\r\n        \"args\": {\r\n          \"parts\": \"1\"\r\n        }\r\n      }\r\n    ]\r\n  }\r\n]',
        '76fdc46c7bc86dc46182dca0104071c6', '2023-03-31 09:32:01', '2023-03-31 09:32:01', NULL, '192.168.33.1', 'I',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (29, 32, 'gateway-routes.json', 'DEFAULT_GROUP', '',
        '[\r\n  {\r\n    \"id\": \"platform-auth\",\r\n    \"uri\": \"lb://platform-auth\",\r\n    \"predicates\": [\r\n      {\r\n        \"name\": \"Path\",\r\n        \"args\": {\r\n          \"pattern\": \"/a/**\"\r\n        }\r\n      }\r\n    ],\r\n    \"filters\": [\r\n      {\r\n        \"name\": \"StripPrefix\",\r\n        \"args\": {\r\n          \"parts\": \"1\"\r\n        }\r\n      }\r\n    ]\r\n  }\r\n]',
        '76fdc46c7bc86dc46182dca0104071c6', '2023-03-31 09:39:36', '2023-03-31 09:39:37', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (29, 33, 'gateway-routes.json', 'DEFAULT_GROUP', '',
        '[\n  {\n    \"id\": \"platform-auth\",\n    \"uri\": \"lb://platform-auth\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/auth/**\"\n        }\n      }\n    ],\n    \"filters\": [\n      {\n        \"name\": \"ValidateCodeGatewayFilter\"\n      },\n      {\n        \"name\": \"PasswordDecoderFilter\"\n      }\n    ]\n  },\n  {\n    \"id\": \"openapi\",\n    \"uri\": \"lb://platform-gateway\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/v3/api-docs/**\"\n        }\n      }\n    ],\n    \"filters\": [\n      {\n        \"name\": \"RewritePath\",\n        \"args\": {\n          \"parts\": \"/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\"\n        }\n      }\n    ]\n  }\n]',
        'eef48feeb9fe232eaa39e1690ee44ae3', '2023-03-31 09:43:44', '2023-03-31 09:43:44', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (29, 34, 'gateway-routes.json', 'DEFAULT_GROUP', '',
        '[\n  {\n    \"id\": \"platform-auth\",\n    \"uri\": \"lb://platform-auth\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/auth/**\"\n        }\n      }\n    ],\n    \"filters\": [\n      {\n        \"name\": \"ValidateCodeGatewayFilter\"\n      },\n      {\n        \"name\": \"PasswordDecoderFilter\"\n      }\n    ]\n  },\n  {\n    \"id\": \"openapi\",\n    \"uri\": \"lb://platform-gateway\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/v3/api-docs/**\"\n        }\n      }\n    ],\n    \"filters\": [\n      {\n        \"name\": \"RewritePath\",\n        \"args\": {\n          \"parts\": \"/v3/api-docs/(?<path>.*), /$\\\\{path}\\/$\\\\{path}/v3/api-docs\"\n        }\n      }\n    ]\n  }\n]',
        'af659155079786587c8fa497c35812ae', '2023-03-31 09:58:22', '2023-03-31 09:58:22', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (29, 35, 'gateway-routes.json', 'DEFAULT_GROUP', '',
        '[\n  {\n    \"id\": \"platform-auth\",\n    \"uri\": \"lb://platform-auth\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/auth/**\"\n        }\n      }\n    ],\n    \"filters\": [\n      {\n        \"name\": \"ValidateCodeGatewayFilter\"\n      },\n      {\n        \"name\": \"PasswordDecoderFilter\"\n      }\n    ]\n  },\n  {\n    \"id\": \"openapi\",\n    \"uri\": \"lb://platform-gateway\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/v3/api-docs/**\"\n        }\n      }\n    ],\n    \"filters\": [\n      {\n        \"name\": \"RewritePath\",\n        \"args\": {\n          \"regexp\": \"/v3/api-docs/(?<path>.*), /$\\\\{path}\\/$\\\\{path}/v3/api-docs\"\n        }\n      }\n    ]\n  }\n]',
        '5a36e25729d004134ed50fd6f61e960f', '2023-03-31 10:01:00', '2023-03-31 10:01:00', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (29, 36, 'gateway-routes.json', 'DEFAULT_GROUP', '',
        '[\n  {\n    \"id\": \"platform-auth\",\n    \"uri\": \"lb://platform-auth\",\n    \"predicates\": [\n      {\n        \"name\": \"Path\",\n        \"args\": {\n          \"pattern\": \"/auth/**\"\n        }\n      }\n    ],\n    \"filters\": [\n      {\n        \"name\": \"ValidateCodeGatewayFilter\"\n      },\n      {\n        \"name\": \"PasswordDecoderFilter\"\n      }\n    ]\n  }\n]',
        'f9c762947dcaea2e606df22f96949e22', '2023-03-31 10:03:53', '2023-03-31 10:03:53', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (17, 37, 'gateway.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        #system 模块\n        - id: platform-system\n          uri: lb://platform-system\n          predicates:\n            - Path=/system/**\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n',
        '7eff6a5f339339d2493859f2432ab948', '2023-03-31 11:16:46', '2023-03-31 11:16:47', NULL, '192.168.33.1', 'D',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (26, 38, 'platform-gateway.yaml', 'DEFAULT_GROUP', '',
        'server:\n\n  port: ${PORT_PLATFORM_GATEWAY:8301}\n\nspring:\n\n  profiles:\n    active: ${SPRING_PROFILES_ACTIVE:dev}\n\n  main:\n    allow-bean-definition-overriding: true\n\n  cloud:\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n        response-timeout: 10s\n      loadbalancer:\n        use404: true\n      default-filters:\n        - StripPrefix=1\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        # 认证中心\n        - id: platform-auth\n          uri: lb://platform-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        #system 模块\n        - id: platform-system\n          uri: lb://platform-system\n          predicates:\n            - Path=/system/**\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://platform-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n\ngateway:\n  encode-key: \'eXTqsEKIPRsksJSK\'\n  ignore-clients:\n    - swagger\n  services: { \"platform-system\": \"/system\" }\n\nlogging:\n  level:\n    com.zclcs: debug\n\n# spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(imENTO7M8bLO38LFSIxnzw==)\n      client-secret: ENC(i3cDFhs26sa2Ucrfz2hnQw==)\n      scope: server\n    # 通用放行URL，服务个性化，请在对应配置文件覆盖\n    ignore:\n      urls:\n        - /v3/api-docs\n        - /actuator/**\n',
        '2a32ad3e3ec10bc14823ce223a8b5c93', '2023-03-31 11:16:46', '2023-03-31 11:16:47', NULL, '192.168.33.1', 'D',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (2, 39, 'global-configuration.yaml', 'DEFAULT_GROUP', '',
        'my:\r\n  redis-cache-prefix: \'${spring.cloud.nacos.config.namespace}:\'\r\n  default-password: 123456',
        'adf7ac2b60b1ecdec8ea11937559de71', '2023-03-31 11:24:50', '2023-03-31 11:24:50', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (2, 40, 'global-configuration.yaml', 'DEFAULT_GROUP', '',
        'my:\n  redis-cache-prefix: \'${spring.cloud.nacos.config.namespace}:\'\n  default-password: 123456789',
        '70b9ac937f25a796adadbda796709cdd', '2023-03-31 11:31:23', '2023-03-31 11:31:24', 'nacos', '192.168.33.1', 'U',
        'dev', '');
INSERT INTO `his_config_info`
VALUES (0, 41, 'minio.yaml', 'DEFAULT_GROUP', '',
        'minio:\r\n  host: ${MINIO_HOST:127.0.0.1}\r\n  port: ${MINIO_PORT:9000}\r\n  endpoint: http://${minio.host}:${minio.port}\r\n  root-user: ${MINIO_ROOT_USER:admin}\r\n  root-password: ${MINIO_ROOT_PASSWORD:minioadmin}\r\n  domain-name: ${DOMAIN_NAME:}',
        '7941222b40d4f2b719d9bf06d468d64f', '2023-04-03 15:19:00', '2023-04-03 15:19:01', NULL, '192.168.33.1', 'I',
        'dev', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `role`     varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL,
    `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `action`   varchar(8) CHARACTER SET utf8 COLLATE utf8_bin   NOT NULL,
    UNIQUE INDEX `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `role`     varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    UNIQUE INDEX `idx_user_role` (`username`, `role`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles`
VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`
(
    `id`                bigint(20) UNSIGNED                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
    `usage`             int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '使用量',
    `max_size`          int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) UNSIGNED                                 NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
    `gmt_create`        datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime                                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '租户容量信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`
(
    `id`            bigint(20)                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint(20)                                       NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint(20)                                       NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_tenant_info_kptenantid` (`kp`, `tenant_id`) USING BTREE,
    INDEX `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'tenant_info'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info`
VALUES (1, '1', 'dev', 'dev', 'dev', 'nacos', 1679649196373, 1679649196373);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL,
    `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `enabled`  tinyint(1)                                       NOT NULL,
    PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users`
VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
