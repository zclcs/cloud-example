call add_column_if_not_exists(database(), 'system_minio_file', 'content_type',
                              'varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT "内容类型"');