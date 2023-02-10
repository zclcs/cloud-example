package com.zclcs.common.security.starter.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zclcs
 */
@UtilityClass
public class PasswordUtil {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();


}
