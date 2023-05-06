package com.zclcs.cloud.lib.core.constant;

/**
 * 端点常量
 *
 * @author zclcs
 */
public interface Minio {

    /**
     * bucket权限-读
     */
    String READ_ONLY = "read-only";
    /**
     * bucket权限-写
     */
    String WRITE_ONLY = "write-only";
    /**
     * bucket权限-读写
     */
    String READ_WRITE = "read-write";
    /**
     * bucket权限-无
     */
    String NONE = "none";
}
