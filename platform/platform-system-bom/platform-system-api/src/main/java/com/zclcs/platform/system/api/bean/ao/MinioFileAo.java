package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件 Ao
 *
 * @author zclcs
 * @since 2021-10-18 10:37:21.262
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MinioFileAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @Size(max = 64, message = "{noMoreThan}")
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private String id;

    /**
     * 桶id
     */
    @NotNull(message = "{required}")
    private Long bucketId;

    /**
     * 文件名称
     */
    @Size(max = 500, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String fileName;

    /**
     * 原文件名称
     */
    @Size(max = 500, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String originalFileName;

    /**
     * 文件路径
     */
    @Size(max = 1000, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String filePath;


}