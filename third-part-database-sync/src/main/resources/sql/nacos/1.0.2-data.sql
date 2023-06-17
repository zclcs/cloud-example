call insert_or_update(database(), 'users',
                      '(`username`, `password`, `enabled`) VALUES ("nacos", "$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu", 1)',
                      'SET `password` = "$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu", `enabled` = 1 WHERE `username` = "nacos"');

call insert_or_update(database(), 'roles',
                      '(`username`, `role`) VALUES ("nacos", "ROLE_ADMIN")',
                      'SET `username` = "nacos", `role` = "ROLE_ADMIN" WHERE `username` = "nacos" AND `role` = "ROLE_ADMIN"');

call insert_or_update(database(), 'tenant_info',
                      '(`id`, `kp`, `tenant_id`, `tenant_name`, `tenant_desc`, `create_source`, `gmt_create`, `gmt_modified`)
  VALUES (1, "1", "{{NACOS_NAMESPACE}}", "{{NACOS_NAMESPACE}}", "{{NACOS_NAMESPACE}}", "nacos", 20230506113058, 20230506113058)',
                      'SET `kp` = "1", `tenant_id` = "{{NACOS_NAMESPACE}}", `tenant_name` = "{{NACOS_NAMESPACE}}", `tenant_desc` = "{{NACOS_NAMESPACE}}", `create_source` = "nacos",
  `gmt_create` = 20230506113058, `gmt_modified` = 20230506113058 WHERE `id` = 1');