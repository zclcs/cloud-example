package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
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
 * @since 2023-09-01 19:54:44.135
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
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long id;

    /**
     * 桶名称
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String bucketName;

    /**
     * 桶权限
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String bucketPolicy;


}