call insert_or_update(database(), 'users',
                      'username, password, enabled',
                      '("nacos", "$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu", TRUE)');//

call insert_or_update(database(), 'roles',
                      'username, role',
                      '("nacos", "ROLE_ADMIN")');//

call insert_or_update(database(), 'tenant_info',
                          'kp, tenant_id, tenant_name, tenant_desc, create_source, gmt_create, gmt_modified',
                          '("1", "{{NACOS_NAMESPACE}}", "{{NACOS_NAMESPACE}}", "{{NACOS_NAMESPACE}}", "nacos", current_timestamp(), current_timestamp())');//

call insert_or_update(database(), 'config_info',
                          'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                          tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                          '("tomcat.yaml", "DEFAULT_GROUP", "server:\r\n  tomcat:\r\n    threads:\r\n      max: 500\r\n      min-spare: 20\r\n    accept-count: 300",
                          "9c9c1fe1f8fb8b09ebea47de327ddf2f", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                          "tomcat统一配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("global-configuration.yaml", "DEFAULT_GROUP", "my:\n  redis-cache-prefix: \' ${spring.cloud.nacos.config.namespace}:\'\n  default-password: 123456\n  nacos-namespace: ${spring.cloud.nacos.discovery.namespace}",
                      "e21e9cd46fe2223845ada6d3a8650d8d", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "系统参数统一配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("openfeign.yaml", "DEFAULT_GROUP", "spring:\n  cloud:\n    openfeign:\n      client:\n        config:\n          default:\n            logger-level: full\n            connect-timeout: 10000\n            read-timeout: 10000\n      okhttp:\n        enabled: true\n      httpclient:\n        enabled: false\n      compression:\n        request:\n          enabled: true\n        response:\n          enabled: true\nfeign:\n  sentinel:\n    enabled: true",
                      "2cbdd9daed4debed965dfbef9aac5017", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "fegin远程调用统一配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("sentinel.yaml", "DEFAULT_GROUP", "spring:\r\n  cloud:\r\n    sentinel:\r\n      eager: true\r\n      transport:\r\n        dashboard: ${NACOS_HOST:127.0.0.1}:${SENTINEL_PORT:8858}\r\n      filter:\r\n        enabled: false\r\n      datasource:\r\n        # 流控规则\r\n        flow:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-flow-rules\r\n            data-type: json\r\n            rule-type: flow\r\n        # 熔断降级\r\n        degrade:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-degrade-rules\r\n            data-type: json\r\n            rule-type: degrade\r\n        # 热点参数限流\r\n        param_flow:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-param-rules\r\n            data-type: json\r\n            rule-type: param_flow\r\n        # 系统规则\r\n        system:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-system-rules\r\n            data-type: json\r\n            rule-type: system\r\n        # 来源访问控制（黑白名单）\r\n        authority:\r\n          nacos:\r\n            serverAddr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            dataId: ${spring.application.name}-auth-rules\r\n            data-type: json\r\n            rule-type: authority",
                      "aecd3bfb4478f664414bbbbe6c821e8b", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "sentinel流量哨兵统一配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("hikari.yaml", "DEFAULT_GROUP", "spring:\r\n  datasource:\r\n    hikari:\r\n      connection-timeout: 30000\r\n      max-lifetime: 1800000\r\n      maximum-pool-size: 15\r\n      minimum-idle: 5\r\n      connection-test-query: select 1",
                      "b4b159e1e2ea46491ddf4a0e0917397f", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "hikari连接池统一配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("rabbitmq.yaml", "DEFAULT_GROUP", "spring:\n  rabbitmq:\n    host: ${RABBIT_MQ_HOST:127.0.0.1}\n    port: ${RABBIT_MQ_PORT:5672}\n    username: ${RABBIT_MQ_USERNAME:root}\n    password: ${RABBIT_MQ_PASSWORD:123456}\n    virtual-host: /\n    # 手动提交消息\n    listener:\n      simple:\n        #消息必须手动确认\n        acknowledge-mode: manual\n        #限制每次发送一条数据。\n        prefetch: 1\n    publisher-returns: true\n    publisher-confirm-type: simple\n",
                      "7677ed45ca5c47f164a9bafbff255493", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "rabbitmq配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("redis.yaml", "DEFAULT_GROUP", "spring:\n  data:\n    redis:\n      database: ${REDIS_DATABASE:0}\n      host: ${REDIS_HOST:127.0.0.1}\n      port: ${REDIS_PORT:6379}\n      password: ${REDIS_PASSWORD:123456}\n      lettuce:\n        pool:\n          min-idle: 8\n          max-idle: 500\n          max-active: 2000\n          max-wait: 10000\n      timeout: 5000",
                      "2a486148042b12896e458277106b9c59", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "redis配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("mybatis-plus.yaml", "DEFAULT_GROUP", "mybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n  global-config:\n    banner: false",
                      "9a808f3e26d4e48bbd8900646c75f323", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "mybatis-plus配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("jasypt.yaml", "DEFAULT_GROUP", "# 加解密根密码\r\njasypt:\r\n  encryptor:\r\n    password: zclcs\r\n    algorithm: PBEWithMD5AndDES\r\n    iv-generator-classname: org.jasypt.iv.NoIvGenerator",
                      "eaf193a7bdff16331a6b4a68d22bbe57", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "jasypt配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("system-dict.yaml", "DEFAULT_GROUP", "system:\r\n  dict:\r\n    text-value-default-null: true\r\n    cache:\r\n      enabled: true\r\n      maximum-size: 500\r\n      initial-capacity: 50\r\n      duration: 30s\r\n      miss-num: 50",
                      "80074d97adf513b2794679d182e491d8", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "系统字典配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), "config_info",
                      "data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key",
                      "('gateway-routes.json', 'DEFAULT_GROUP', '[\n    {\n        \"id\": \"platform-auth\",\n        \"uri\": \"lb://platform-auth\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/auth/**\"\n                }\n            }\n        ],\n        \"filters\": [\n            {\n                \"name\": \"ValidateCodeGatewayFilter\"\n            },\n            {\n                \"name\": \"PasswordDecoderFilter\"\n            }\n        ]\n    },\n    {\n        \"id\": \"platform-system\",\n        \"uri\": \"lb://platform-system\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/system/**\"\n                }\n            }\n        ]\n    },\n    {\n        \"id\": \"test-test\",\n        \"uri\": \"lb://test-test\",\n        \"predicates\": [\n            {\n                \"name\": \"Path\",\n                \"args\": {\n                    \"pattern\": \"/test/**\"\n                }\n            }\n        ]\n    }\n]',
                      '0a9923c3357004b4c34dabaceb95967d', now(), now(), NULL, '127.0.0.1', '', '{{NACOS_NAMESPACE}}',
                      '网关路由配置', NULL, NULL, 'json', NULL, '')");//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("minio.yaml", "DEFAULT_GROUP", "minio:\n  host: ${MINIO_HOST:127.0.0.1}\n  port: ${MINIO_PORT:9000}\n  endpoint: http://${minio.host}:${minio.port}\n  root-user: ${MINIO_ROOT_USER:admin}\n  root-password: ${MINIO_ROOT_PASSWORD:minioadmin}\n  domain-name: ${MINIO_DOMAIN_NAME:}",
                      "d85d3216a6be8e2bb3c529a051e60f6b", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "minio", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("swagger-dev.yaml", "DEFAULT_GROUP", "swagger:\n  enabled: true\n  title: api文档\n  version: 1.0.0\n  description: ${spring.application.name}api文档\n  terms-of-service: xxx.com\n  concat-name: zclcs\n  concat-email: 2371219112@qq.com\n  license: Apache 2.0\n  license-url: https://www.apache.org/licenses/LICENSE-2.0.html\n  gateway-endpoint: http://${GATEWAY_HOST:platform-gateway}:${PORT_GATEWAY:8301}\n  token-url: ${swagger.gateway-endpoint}/auth/oauth2/token?scope=server\n  scope: server\n  base-package: com.zclcs\nknife4j:\n  gateway:\n    enabled: true\n    strategy: discover\n    # 指定服务发现的模式聚合微服务文档，并且是默认`default`分组\n    discover:\n      # 指定版本号(Swagger2|OpenAPI3)\n      version: openapi3\n      excluded-services:\n        - platform-gateway\nspringdoc:\n  # 默认是false，需要设置为true\n  default-flat-param-object: true",
                      "e433f4ae07b2784906dd24d9a7ac9554", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "开发环境swagger配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("swagger-prod.yaml", "DEFAULT_GROUP", "knife4j:\n  enable: true\n  production: true\n  gateway:\n    enabled: false",
                      "18c1667d451e8a5a51647c866af9427c", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "生产环境swagger配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("xxl-job.yaml", "DEFAULT_GROUP", "my:\n  xxl:\n    job:\n      admin-addresses: http://${XXL_JOB_HOST:127.0.0.1}:${XXL_JOB_PORT:8080}/xxl-job-admin\n      access-token: ${XXL_JOB_TOKEN:12382e1c-fafc-4cc8-bb04-d276fd1ca7de}\n      app-name: ${spring.cloud.nacos.config.namespace}-${spring.application.name}\n      address:\n      ip:\n      port: 0\n      log-path: ./log/${spring.application.name}/xxl-job\n      log-retention-days: 30",
                      "d7be3cd828b454eecd2270a9c9a04913", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "xxl-job配置", NULL, NULL, "yaml", NULL, "")');//

call insert_or_update(database(), 'config_info',
                      'data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name,
                      tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key',
                      '("platform-system-rabbit-mq-queue.yaml", "DEFAULT_GROUP", "my:\r\n  rabbit:\r\n    mq:\r\n      direct-queues:\r\n        systemLog:\r\n          name-base-on-queue-name: true\r\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-log\r\n          init-dlx: false\r\n        systemLoginLog:\r\n          name-base-on-queue-name: true\r\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-login-log\r\n          init-dlx: false\r\n        systemRateLimitLog:\r\n          name-base-on-queue-name: true\r\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-rate-limit-log\r\n          init-dlx: false\r\n        systemBlockLog:\r\n          name-base-on-queue-name: true\r\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-block-log\r\n          init-dlx: false\r\n        systemRouteLog:\r\n          name-base-on-queue-name: true\r\n          queue-name: ${spring.cloud.nacos.config.namespace}-system-route-log\r\n          init-dlx: false",
                      "e252b9d8f2966b0e957bc34bf9d95082", now(), now(), NULL, "127.0.0.1", "", "{{NACOS_NAMESPACE}}",
                      "platform-system 队列配置", NULL, NULL, "yaml", NULL, "")');//
