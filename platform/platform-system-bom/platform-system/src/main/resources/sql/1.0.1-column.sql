call add_column_if_not_exists(database(), 'system_minio_file', 'content_type',
                              'varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT "内容类型"');
call add_column_if_not_exists(database(), 'system_route_log', 'request_time',
                              'datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "请求时间"');
call add_column_if_not_exists(database(), 'system_rate_limit_log', 'request_time',
                              'datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "拦截时间"');
call add_column_if_not_exists(database(), 'system_block_log', 'request_time',
                              'datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "拦截时间"');