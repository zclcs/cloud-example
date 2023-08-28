package com.zclcs.cloud.lib.core.exception;

import java.io.Serial;

/**
 * 文件下载异常
 *
 * @author zclcs
 */
public class FileUploadException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4353976687870027960L;

    public FileUploadException(String message) {
        super(message);
    }
}
