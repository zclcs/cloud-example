call insert_or_update(database(), 'users',
                      '(`username`, `password`, `enabled`) VALUES ("nacos", "$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu", 1)',
                      'SET `password` = "$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu", `enabled` = 1 WHERE `username` = "nacos"');

call insert_if_not_exists(database(), 'roles',
                          'username,role',
                          '"nacos","ROLE_ADMIN"',
                          'username="nacos" AND role="ROLE_ADMIN"');

call insert_if_not_exists(database(), 'tenant_info',
                          'kp,tenant_id,tenant_name,tenant_desc,create_source,gmt_create,gmt_modified',
                          '"1","{{NACOS_NAMESPACE}}","{{NACOS_NAMESPACE}}","{{NACOS_NAMESPACE}}", "nacos", 20230506113058, 20230506113058',
                          'tenant_id="{{NACOS_NAMESPACE}}"');