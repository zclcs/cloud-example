package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "MinioFileVo对象", description = "文件")
public class MinioFileVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "文件id")
    private String id;

    @Schema(title = "桶id")
    private Long bucketId;

    @Schema(title = "桶名称")
    private String bucketName;

    @Schema(title = "文件名称")
    private String fileName;

    @Schema(title = "原文件名称")
    private String originalFileName;

    @Schema(title = "文件路径")
    private String filePath;

    @Schema(title = "内容类型")
    private String contentType;


}