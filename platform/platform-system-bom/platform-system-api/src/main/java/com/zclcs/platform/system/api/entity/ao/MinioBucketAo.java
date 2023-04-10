package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.AddStrategy;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * minio桶 Ao
 *
 * @author zclcs
 * @date 2021-10-21 16:45:35.202
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "MinioBucketAo对象", description = "minio桶")
public class MinioBucketAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(title = "桶id")
    private Long id;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}", groups = AddStrategy.class)
    @Schema(title = "桶名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bucketName;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(title = "桶权限", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bucketPolicy;


}