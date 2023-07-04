call add_index_if_not_exists(database(), 'system_login_log', 1,
                             'nk_system_login_log_username', 'username');
call add_index_if_not_exists(database(), 'system_login_log', 1,
                             'nk_system_login_log_ip', 'ip');
call add_index_if_not_exists(database(), 'system_route_log', 1,
                             'nk_system_route_log_route_ip', 'route_ip');
call add_index_if_not_exists(database(), 'system_route_log', 1,
                             'nk_system_route_log_target_server', 'target_server');
call add_index_if_not_exists(database(), 'system_route_log', 1,
                             'nk_system_route_log_request_time', 'request_time');
call add_index_if_not_exists(database(), 'system_rate_limit_log', 1,
                             'nk_system_rate_limit_log_rate_limit_log_ip', 'rate_limit_log_ip');
call add_index_if_not_exists(database(), 'system_rate_limit_log', 1,
                             'nk_system_rate_limit_log_request_time', 'request_time');
call add_index_if_not_exists(database(), 'system_block_log', 1,
                             'nk_system_block_log_block_ip', 'block_ip');
call add_index_if_not_exists(database(), 'system_block_log', 1,
                             'nk_system_block_log_request_time', 'request_time');