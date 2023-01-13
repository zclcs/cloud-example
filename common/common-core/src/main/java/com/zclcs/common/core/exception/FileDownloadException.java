package com.zclcs.common.core.exception;

import java.io.Serial;

/**
 * 文件下载异常
 *
 * @author zclcs
 */
public class FileDownloadException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4353976687870027960L;

    public FileDownloadException(String message) {
        super(message);
    }
}
