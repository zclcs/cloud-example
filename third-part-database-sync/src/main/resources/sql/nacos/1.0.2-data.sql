call insert_or_update(database(), 'users',
                    '(`username`, `password`, `enabled`) VALUES ("nacos", "$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu", 1)',
                    'SET `password` = "$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu", `enabled` = 1 WHERE `username` = "nacos"');//

call insert_or_update(database(), 'roles',
                    '(`username`, `role`) VALUES ("nacos", "ROLE_ADMIN")',
                    'SET `username` = "nacos", `role` = "ROLE_ADMIN" WHERE `username` = "nacos" AND `role` = "ROLE_ADMIN"');//

call insert_or_update(database(), 'tenant_info',
                    '(`id`, `kp`, `tenant_id`, `tenant_name`, `tenant_desc`, `create_source`, `gmt_create`, `gmt_modified`)
VALUES (1, "1", "{{NACOS_NAMESPACE}}", "{{NACOS_NAMESPACE}}", "{{NACOS_NAMESPACE}}", "nacos", 20230506113058, 20230506113058)',
                    'SET `kp` = "1", `tenant_id` = "{{NACOS_NAMESPACE}}", `tenant_name` = "{{NACOS_NAMESPACE}}", `tenant_desc` = "{{NACOS_NAMESPACE}}", `create_source` = "nacos",
`gmt_create` = 20230506113058, `gmt_modified` = 20230506113058 WHERE `id` = 1');//

call insert_or_update(database(), 'config_info',
                    '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
  `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
  "tomcat.yaml", "DEFAULT_GROUP",
  "server:\n  tomcat:\n    threads:\n      max: 500\n      min-spare: 20\n    accept-count: 300\n    relaxed-query-chars: [\' |\', \'{\',\'}\',\'[\',\']\']\n    relaxed-path-chars: [\'|\',\'{\',\'}\',\'[\',\']\']\n" ,
  "2257bf7aa1d66d3c72ef539007315e11", now(), now(), now(), "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "tomcat统一配置", NULL, NULL, "yaml", NULL, "")',
                    'SET `group_id` = "DEFAULT_GROUP",
  `content` = "server:\n  tomcat:\n    threads:\n      max: 500\n      min-spare: 20\n    accept-count: 300\n    relaxed-query-chars: [\' |\', \'{\',\'}\',\'[\',\']\']\n    relaxed-path-chars: [\'|\',\'{\',\'}\',\'[\',\']\']\n",
  `md5` = "2257bf7aa1d66d3c72ef539007315e11", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
  `c_desc` = "tomcat统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
  WHERE `data_id` = "tomcat.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
  `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
  "global-configuration.yaml", "DEFAULT_GROUP",
  "my:\n  redis-cache-prefix: \'${spring.cloud.nacos.config.namespace}:\'\n  default-password: 123456\n",
  "b02cea7d68ed3e3f33dfab2e3b89c6f9", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "系统参数统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
  `content` = "my:\n  redis-cache-prefix: \'${spring.cloud.nacos.config.namespace}:\'\n  default-password: 123456\n",
  `md5` = "b02cea7d68ed3e3f33dfab2e3b89c6f9", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
  `c_desc` = "系统参数统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
  WHERE `data_id` = "global-configuration.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
  `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
  "openfeign.yaml", "DEFAULT_GROUP",
  "spring:\n  cloud:\n    openfeign:\n      client:\n        config:\n          default:\n            logger-level: full\n            connect-timeout: 10000\n            read-timeout: 10000\n      okhttp:\n        enabled: true\n      httpclient:\n        enabled: false\n      compression:\n        request:\n          enabled: true\n        response:\n          enabled: true\nfeign:\n  sentinel:\n    enabled: true\n",
  "23aa2cc7a784b56c2d5a4c0bb66f695d", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "openfeign统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
  `content` = "spring:\n  cloud:\n    openfeign:\n      client:\n        config:\n          default:\n            logger-level: full\n            connect-timeout: 10000\n            read-timeout: 10000\n      okhttp:\n        enabled: true\n      httpclient:\n        enabled: false\n      compression:\n        request:\n          enabled: true\n        response:\n          enabled: true\nfeign:\n  sentinel:\n    enabled: true\n",
  `md5` = "23aa2cc7a784b56c2d5a4c0bb66f695d", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
  `c_desc` = "openfeign统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
  WHERE `data_id` = "openfeign.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
    `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
    "sentinel.yaml", "DEFAULT_GROUP",
    "spring:\n  cloud:\n    sentinel:\n      eager: true\n      transport:\n        dashboard: ${NACOS_HOST:127.0.0.1}:${SENTINEL_PORT:8858}\n      filter:\n        enabled: false\n      datasource:\n        # 流控规则\n        flow:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-flow-rules\n            data-type: json\n            rule-type: flow\n        # 熔断降级\n        degrade:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-degrade-rules\n            data-type: json\n            rule-type: degrade\n        # 热点参数限流\n        param_flow:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-param-rules\n            data-type: json\n            rule-type: param_flow\n        # 系统规则\n        system:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-system-rules\n            data-type: json\n            rule-type: system\n        # 来源访问控制（黑白名单）\n        authority:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-auth-rules\n            data-type: json\n            rule-type: authority\n",
    "72fffb6f750835b2376abb80fd8f4704", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "sentinel流量哨兵统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
    `content` = "spring:\n  cloud:\n    sentinel:\n      eager: true\n      transport:\n        dashboard: ${NACOS_HOST:127.0.0.1}:${SENTINEL_PORT:8858}\n      filter:\n        enabled: false\n      datasource:\n        # 流控规则\n        flow:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-flow-rules\n            data-type: json\n            rule-type: flow\n        # 熔断降级\n        degrade:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-degrade-rules\n            data-type: json\n            rule-type: degrade\n        # 热点参数限流\n        param_flow:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-param-rules\n            data-type: json\n            rule-type: param_flow\n        # 系统规则\n        system:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-system-rules\n            data-type: json\n            rule-type: system\n        # 来源访问控制（黑白名单）\n        authority:\n          nacos:\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-auth-rules\n            data-type: json\n            rule-type: authority\n",
    `md5` = "72fffb6f750835b2376abb80fd8f4704", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
    `c_desc` = "sentinel流量哨兵统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
    WHERE `data_id` = "sentinel.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
    `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
    "hikari.yaml", "DEFAULT_GROUP",
    "spring:\n  datasource:\n    hikari:\n      connection-timeout: 30000\n      max-lifetime: 1800000\n      maximum-pool-size: 15\n      minimum-idle: 5\n      connection-test-query: select 1\n",
    "4a06bcca4f13cd6a23da8a006820cb03", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "hikari数据库连接池统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
    `content` = "spring:\n  datasource:\n    hikari:\n      connection-timeout: 30000\n      max-lifetime: 1800000\n      maximum-pool-size: 15\n      minimum-idle: 5\n      connection-test-query: select 1\n",
    `md5` = "4a06bcca4f13cd6a23da8a006820cb03", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
    `c_desc` = "hikari数据库连接池统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
    WHERE `data_id` = "hikari.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
    `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
    "rabbitmq.yaml", "DEFAULT_GROUP",
    "spring:\n  rabbitmq:\n    host: ${RABBIT_MQ_HOST:127.0.0.1}\n    port: ${RABBIT_MQ_PORT:5672}\n    api-port: ${RABBIT_MQ_API_PORT:15672}\n    username: ${RABBIT_MQ_USERNAME:root}\n    password: ${RABBIT_MQ_PASSWORD:123456}\n    virtual-host: /\n    # 手动提交消息\n    listener:\n      simple:\n        #消息必须手动确认\n        acknowledge-mode: manual\n        #限制每次发送一条数据。\n        prefetch: 1\n    publisher-returns: true\n    publisher-confirm-type: simple\n",
    "b2d5ec094e4f5b80dcb4f4b33588c9f5", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "rabbitmq队列统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
    `content` = "spring:\n  rabbitmq:\n    host: ${RABBIT_MQ_HOST:127.0.0.1}\n    port: ${RABBIT_MQ_PORT:5672}\n    api-port: ${RABBIT_MQ_API_PORT:15672}\n    username: ${RABBIT_MQ_USERNAME:root}\n    password: ${RABBIT_MQ_PASSWORD:123456}\n    virtual-host: /\n    # 手动提交消息\n    listener:\n      simple:\n        #消息必须手动确认\n        acknowledge-mode: manual\n        #限制每次发送一条数据。\n        prefetch: 1\n    publisher-returns: true\n    publisher-confirm-type: simple\n",
    `md5` = "b2d5ec094e4f5b80dcb4f4b33588c9f5", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
    `c_desc` = "rabbitmq队列统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
    WHERE `data_id` = "rabbitmq.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
    `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
    "redis.yaml", "DEFAULT_GROUP",
    "spring:\n  data:\n    redis:\n      database: ${REDIS_DATABASE:0}\n      host: ${REDIS_HOST:127.0.0.1}\n      port: ${REDIS_PORT:6379}\n      password: ${REDIS_PASSWORD:123456}\n      lettuce:\n        pool:\n          min-idle: 8\n          max-idle: 500\n          max-active: 2000\n          max-wait: 10000\n      timeout: 5000\n",
    "359ace705fa0f85af17b481ea5e97da1", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "redis统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
    `content` = "spring:\n  data:\n    redis:\n      database: ${REDIS_DATABASE:0}\n      host: ${REDIS_HOST:127.0.0.1}\n      port: ${REDIS_PORT:6379}\n      password: ${REDIS_PASSWORD:123456}\n      lettuce:\n        pool:\n          min-idle: 8\n          max-idle: 500\n          max-active: 2000\n          max-wait: 10000\n      timeout: 5000\n",
    `md5` = "359ace705fa0f85af17b481ea5e97da1", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
    `c_desc` = "redis统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
    WHERE `data_id` = "redis.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
    `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
    "mybatis-plus.yaml", "DEFAULT_GROUP",
    "mybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n  global-config:\n    banner: false\n",
    "43f9541ea7b9aa528bd6906bfd480728", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "mybatis-plus统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
    `content` = "mybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n  global-config:\n    banner: false\n",
    `md5` = "43f9541ea7b9aa528bd6906bfd480728", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
    `c_desc` = "mybatis-plus统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
    WHERE `data_id` = "mybatis-plus.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
    `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
    "jasypt.yaml", "DEFAULT_GROUP",
    "# 加解密根密码\njasypt:\n  encryptor:\n    password: zclcs\n    algorithm: PBEWithMD5AndDES\n    iv-generator-classname: org.jasypt.iv.NoIvGenerator\n",
    "ab44464f882a9d34cfd188be6fe9b891", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "jasypt配置文件加解密统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
    `content` = "# 加解密根密码\njasypt:\n  encryptor:\n    password: zclcs\n    algorithm: PBEWithMD5AndDES\n    iv-generator-classname: org.jasypt.iv.NoIvGenerator\n",
    `md5` = "ab44464f882a9d34cfd188be6fe9b891", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
    `c_desc` = "jasypt配置文件加解密统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
    WHERE `data_id` = "jasypt.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
    `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
    "system-dict.yaml", "DEFAULT_GROUP",
    "system:\n  dict:\n    text-value-default-null: true\n    cache:\n      enabled: true\n      maximum-size: 500\n      initial-capacity: 50\n      duration: 30s\n      miss-num: 50\n",
    "6ec79c15dcb8b154fa72bf3ea8a4004c", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "系统字典统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
    `content` = "system:\n  dict:\n    text-value-default-null: true\n    cache:\n      enabled: true\n      maximum-size: 500\n      initial-capacity: 50\n      duration: 30s\n      miss-num: 50\n",
    `md5` = "6ec79c15dcb8b154fa72bf3ea8a4004c", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
    `c_desc` = "系统字典统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
    WHERE `data_id` = "system-dict.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      "(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
    `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
    'gateway-routes.json', 'DEFAULT_GROUP',
    '[\n    {\n        \"id\": \"platform-auth\",\n        \"uri\": \"lb://platform-auth\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/auth/**\"\n                }\n            }\n        ],\n        \"filters\": [\n            {\n                \"name\": \"ValidateCodeGatewayFilter\"\n            },\n            {\n                \"name\": \"PasswordDecoderFilter\"\n            }\n        ]\n    },\n    {\n        \"id\": \"platform-system\",\n        \"uri\": \"lb://platform-system\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/system/**\"\n                }\n            }\n        ]\n    },\n    {\n        \"id\": \"test-test\",\n        \"uri\": \"lb://test-test\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/test/**\"\n                }\n            }\n        ]\n    }\n]\n',
    'd71bcf4370c07ec968b83bf7abd64ac9', now(), now(), NULL, '127.0.0.1', '', '{{NACOS_NAMESPACE}}', '网关路由配置', NULL, NULL, 'yaml', NULL, '')",
                      "SET `group_id` = 'DEFAULT_GROUP',
    `content` = '[\n    {\n        \"id\": \"platform-auth\",\n        \"uri\": \"lb://platform-auth\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/auth/**\"\n                }\n            }\n        ],\n        \"filters\": [\n            {\n                \"name\": \"ValidateCodeGatewayFilter\"\n            },\n            {\n                \"name\": \"PasswordDecoderFilter\"\n            }\n        ]\n    },\n    {\n        \"id\": \"platform-system\",\n        \"uri\": \"lb://platform-system\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/system/**\"\n                }\n            }\n        ]\n    },\n    {\n        \"id\": \"test-test\",\n        \"uri\": \"lb://test-test\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/test/**\"\n                }\n            }\n        ]\n    }\n]\n',
    `md5` = 'd71bcf4370c07ec968b83bf7abd64ac9', `gmt_modified` = now(), `src_user` = NULL, `src_ip` = '127.0.0.1', `app_name` = '',
    `c_desc` = '网关路由配置', `c_use` = NULL, `effect` = NULL, `type` = 'yaml', `c_schema` = NULL, `encrypted_data_key` = ''
    WHERE `data_id` = 'gateway-routes.json' and `tenant_id` = '{{NACOS_NAMESPACE}}'");//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
  `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
  "minio.yaml", "DEFAULT_GROUP",
  "minio:\n  host: ${MINIO_HOST:127.0.0.1}\n  port: ${MINIO_PORT:9000}\n  endpoint: http://${minio.host}:${minio.port}\n  root-user: ${MINIO_ROOT_USER:admin}\n  root-password: ${MINIO_ROOT_PASSWORD:minioadmin}\n  domain-name: ${MINIO_DOMAIN_NAME:}\n",
  "9114bebd3fd8f6a6e837ff1a38c22c14", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "minio统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
  `content` = "minio:\n  host: ${MINIO_HOST:127.0.0.1}\n  port: ${MINIO_PORT:9000}\n  endpoint: http://${minio.host}:${minio.port}\n  root-user: ${MINIO_ROOT_USER:admin}\n  root-password: ${MINIO_ROOT_PASSWORD:minioadmin}\n  domain-name: ${MINIO_DOMAIN_NAME:}\n",
  `md5` = "9114bebd3fd8f6a6e837ff1a38c22c14", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
  `c_desc` = "minio统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
  WHERE `data_id` = "minio.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
  `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
  "swagger-dev.yaml", "DEFAULT_GROUP",
  "swagger:\n  enabled: true\n  title: api文档\n  version: 1.0.0\n  description: ${spring.application.name}api文档\n  terms-of-service: xxx.com\n  concat-name: zclcs\n  concat-email: 2371219112@qq.com\n  license: Apache 2.0\n  license-url: https://www.apache.org/licenses/LICENSE-2.0.html\n  gateway-endpoint: http://${GATEWAY_HOST:platform-gateway}:${PORT_GATEWAY:8301}\n  token-url: ${swagger.gateway-endpoint}/auth/oauth2/token?scope=server\n  scope: server\n  base-package: com.zclcs\nknife4j:\n  gateway:\n    enabled: true\n    strategy: discover\n    # 指定服务发现的模式聚合微服务文档，并且是默认`default`分组\n    discover:\n      # 指定版本号(Swagger2|OpenAPI3)\n      version: openapi3\n      excluded-services:\n        - platform-gateway\nspringdoc:\n  # 默认是false，需要设置为true\n  default-flat-param-object: true\n",
  "8982143a6e91f60c840142fc93b154b0", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "开发环境swagger配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
  `content` = "swagger:\n  enabled: true\n  title: api文档\n  version: 1.0.0\n  description: ${spring.application.name}api文档\n  terms-of-service: xxx.com\n  concat-name: zclcs\n  concat-email: 2371219112@qq.com\n  license: Apache 2.0\n  license-url: https://www.apache.org/licenses/LICENSE-2.0.html\n  gateway-endpoint: http://${GATEWAY_HOST:platform-gateway}:${PORT_GATEWAY:8301}\n  token-url: ${swagger.gateway-endpoint}/auth/oauth2/token?scope=server\n  scope: server\n  base-package: com.zclcs\nknife4j:\n  gateway:\n    enabled: true\n    strategy: discover\n    # 指定服务发现的模式聚合微服务文档，并且是默认`default`分组\n    discover:\n      # 指定版本号(Swagger2|OpenAPI3)\n      version: openapi3\n      excluded-services:\n        - platform-gateway\nspringdoc:\n  # 默认是false，需要设置为true\n  default-flat-param-object: true\n",
  `md5` = "8982143a6e91f60c840142fc93b154b0", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
  `c_desc` = "开发环境swagger配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
  WHERE `data_id` = "swagger-dev.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
  `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
  "swagger-prod.yaml", "DEFAULT_GROUP",
  "knife4j:\n  enable: true\n  production: true\n  gateway:\n    enabled: false\n",
  "4cd8d4e6c4b911b136dac372c094bf07", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "生产环境swagger配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
  `content` = "knife4j:\n  enable: true\n  production: true\n  gateway:\n    enabled: false\n",
  `md5` = "4cd8d4e6c4b911b136dac372c094bf07", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
  `c_desc` = "生产环境swagger配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
  WHERE `data_id` = "swagger-prod.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
  `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
  "xxl-job.yaml", "DEFAULT_GROUP",
  "my:\n  xxl:\n    job:\n      admin-addresses: http://${XXL_JOB_HOST:127.0.0.1}:${XXL_JOB_PORT:8080}/xxl-job-admin\n      access-token: ${XXL_JOB_TOKEN:12382e1c-fafc-4cc8-bb04-d276fd1ca7de}\n      app-name: ${spring.cloud.nacos.config.namespace}-${spring.application.name}\n      address:\n      ip:\n      port: 0\n      log-path: ./log/${spring.application.name}/xxl-job\n      log-retention-days: 30\n      admin-username: ${XXL_JOB_ADMIN_USERNAME:admin}\n      admin-password: ${XXL_JOB_ADMIN_PASSWORD:123456}\n",
  "15cfe23e3f1868b2f97a1c31540a47ff", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "xxl-job分布式定时任务统一配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
  `content` = "my:\n  xxl:\n    job:\n      admin-addresses: http://${XXL_JOB_HOST:127.0.0.1}:${XXL_JOB_PORT:8080}/xxl-job-admin\n      access-token: ${XXL_JOB_TOKEN:12382e1c-fafc-4cc8-bb04-d276fd1ca7de}\n      app-name: ${spring.cloud.nacos.config.namespace}-${spring.application.name}\n      address:\n      ip:\n      port: 0\n      log-path: ./log/${spring.application.name}/xxl-job\n      log-retention-days: 30\n      admin-username: ${XXL_JOB_ADMIN_USERNAME:admin}\n      admin-password: ${XXL_JOB_ADMIN_PASSWORD:123456}\n",
  `md5` = "15cfe23e3f1868b2f97a1c31540a47ff", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
  `c_desc` = "xxl-job分布式定时任务统一配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
  WHERE `data_id` = "xxl-job.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//

call insert_or_update(database(), 'config_info',
                      '(`data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`,
  `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (
  "platform-system-rabbit-mq-queue.yaml", "DEFAULT_GROUP",
  "my:\n  rabbit:\n    mq:\n      direct-queues:\n        systemLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-log\n          init-dlx: false\n        systemLoginLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-login-log\n          init-dlx: false\n        systemRateLimitLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-rate-limit-log\n          init-dlx: false\n        systemBlockLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-block-log\n          init-dlx: false\n        systemRouteLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-route-log\n          init-dlx: false\n",
  "45724459b263c7f8ea286b563734644b", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}", "platform-system队列定义共享配置", NULL, NULL, "yaml", NULL, "")',
                      'SET `group_id` = "DEFAULT_GROUP",
  `content` = "my:\n  rabbit:\n    mq:\n      direct-queues:\n        systemLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-log\n          init-dlx: false\n        systemLoginLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-login-log\n          init-dlx: false\n        systemRateLimitLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-rate-limit-log\n          init-dlx: false\n        systemBlockLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-block-log\n          init-dlx: false\n        systemRouteLog:\n          name-base-on-queue-name: true\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-route-log\n          init-dlx: false\n",
  `md5` = "45724459b263c7f8ea286b563734644b", `gmt_modified` = now(), `src_user` = NULL, `src_ip` = "127.0.0.1", `app_name` = "",
  `c_desc` = "platform-system队列定义共享配置", `c_use` = NULL, `effect` = NULL, `type` = "yaml", `c_schema` = NULL, `encrypted_data_key` = ""
  WHERE `data_id` = "platform-system-rabbit-mq-queue.yaml" and `tenant_id` = "{{NACOS_NAMESPACE}}"');//