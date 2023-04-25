call insert_or_update(database(), 'xxl_job_user',
                      'username, password, role, permission',
                      '("admin", "e10adc3949ba59abbe56e057f20f883e", 1, NULL)',
                      'username = values(username)');//

call insert_or_update(database(), 'xxl_job_lock',
                      'lock_name',
                      '("schedule_lock")',
                      'lock_name = values(lock_name)');//