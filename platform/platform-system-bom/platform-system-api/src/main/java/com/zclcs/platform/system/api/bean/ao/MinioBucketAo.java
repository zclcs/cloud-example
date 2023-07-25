package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.AddStrategy;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
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
public class MinioBucketAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 桶id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    /**
     * 桶名称
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}", groups = AddStrategy.class)
    private String bucketName;

    /**
     * 桶权限
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String bucketPolicy;


}