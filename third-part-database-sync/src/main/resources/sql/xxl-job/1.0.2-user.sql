call insert_or_update(database(), 'xxl_job_user',
                      '(`username`, `password`, `role`, `permission`) VALUES ("admin", "e10adc3949ba59abbe56e057f20f883e", 1, NULL)',
                      'SET `username` = "admin", `password` = "e10adc3949ba59abbe56e057f20f883e", `role` = 1, `permission` = NULL WHERE `username` = "admin"');

call insert_if_not_exists(database(), 'xxl_job_lock',
                          'lock_name',
                          '"schedule_lock"',
                          'lock_name="schedule_lock"');