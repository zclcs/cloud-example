package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * minio桶 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:44.135
 */
@Data
public class MinioBucketCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 桶id
     */
    private Long id;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 桶权限
     */
    private String bucketPolicy;


}