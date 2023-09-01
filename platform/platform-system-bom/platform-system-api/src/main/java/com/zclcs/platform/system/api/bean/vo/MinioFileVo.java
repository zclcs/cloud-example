package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * minio文件 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:40.426
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MinioFileVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     * 默认值：
     */
    private String id;

    /**
     * 桶id
     * 默认值：0
     */
    private Long bucketId;

    /**
     * 文件名称
     * 默认值：
     */
    private String fileName;

    /**
     * 原文件名称
     * 默认值：
     */
    private String originalFileName;

    /**
     * 文件路径
     * 默认值：
     */
    private String filePath;

    /**
     * 内容类型
     * 默认值：
     */
    private String contentType;

    /**
     * 桶名称
     */
    private String bucketName;


}