package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.common.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * minio桶 Vo
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
@Schema(title = "MinioBucketVo对象", description = "minio桶")
public class MinioBucketVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "桶id")
    private Long id;

    @Schema(title = "桶名称")
    private String bucketName;

    @Schema(title = "桶权限")
    private String bucketPolicy;


}