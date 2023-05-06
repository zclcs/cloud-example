package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文件 Ao
 *
 * @author zclcs
 * @date 2021-10-18 10:37:21.262
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "MinioFileAo对象", description = "文件")
public class MinioFileAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 64, message = "{noMoreThan}")
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(title = "文件id")
    private String id;

    @NotNull(message = "{required}")
    @Schema(title = "桶id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bucketId;

    @Size(max = 500, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(title = "文件名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileName;

    @Size(max = 500, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(title = "原文件名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String originalFileName;

    @Size(max = 1000, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(title = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filePath;


}