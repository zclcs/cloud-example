package com.zclcs.platform.system.api.bean.vo;

import com.mybatisflex.annotation.Column;
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
 * 文件 Vo
 *
 * @author zclcs
 * @since 2021-10-18 10:37:21.262
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

    /**
     * 桶名称
     */
    @Column(ignore = true)
    private String bucketName;


}