package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * minio文件 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:40.426
 */
@Data
public class MinioFileCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    private String id;

    /**
     * 桶id
     */
    private Long bucketId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 原文件名称
     */
    private String originalFileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 内容类型
     */
    private String contentType;


}