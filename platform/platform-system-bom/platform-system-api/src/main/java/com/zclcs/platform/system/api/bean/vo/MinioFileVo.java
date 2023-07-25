package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件 Vo
 *
 * @author zclcs
 * @date 2021-10-18 10:37:21.262
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
     * 桶名称
     */
    private String bucketName;

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