package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.common.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "FileUploadVo", description = "文件")
public class FileUploadVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "文件名称")
    private String fileName;

    @Schema(title = "原文件名称")
    private String originalFileName;

    @Schema(title = "文件路径")
    private String filePath;

    @Schema(title = "http 地址")
    private String urlHttp;

    @Schema(title = "域名 地址")
    private String urlDomain;
}
