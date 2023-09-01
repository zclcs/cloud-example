package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * minio文件 Ao
 *
 * @author zclcs
 * @since 2023-09-01 19:54:40.426
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MinioFileAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     * 默认值：
     */
    @Size(max = 64, message = "{noMoreThan}")
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private String id;

    /**
     * 桶id
     * 默认值：0
     */
    @NotNull(message = "{required}")
    private Long bucketId;

    /**
     * 文件名称
     * 默认值：
     */
    @Size(max = 500, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String fileName;

    /**
     * 原文件名称
     * 默认值：
     */
    @Size(max = 500, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String originalFileName;

    /**
     * 文件路径
     * 默认值：
     */
    @Size(max = 1000, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String filePath;

    /**
     * 内容类型
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String contentType;


}