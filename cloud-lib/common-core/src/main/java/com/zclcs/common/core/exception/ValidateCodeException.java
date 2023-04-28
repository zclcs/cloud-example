package com.zclcs.common.core.exception;

import java.io.Serial;

/**
 * 验证码类型异常
 *
 * @author zclcs
 */
public class ValidateCodeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
