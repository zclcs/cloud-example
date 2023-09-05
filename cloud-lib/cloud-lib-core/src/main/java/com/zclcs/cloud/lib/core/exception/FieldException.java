package com.zclcs.cloud.lib.core.exception;

import java.io.Serial;

/**
 * 字段校验异常
 *
 * @author zclcs
 */
public class FieldException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6916154462432027437L;

    public FieldException(String message) {
        super(message);
    }
}
