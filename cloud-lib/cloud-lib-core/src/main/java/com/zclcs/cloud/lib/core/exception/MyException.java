package com.zclcs.cloud.lib.core.exception;

import java.io.Serial;

/**
 * 系统异常
 *
 * @author zclcs
 */
public class MyException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6916154462432027437L;

    public MyException(String message) {
        super(message);
    }
}
