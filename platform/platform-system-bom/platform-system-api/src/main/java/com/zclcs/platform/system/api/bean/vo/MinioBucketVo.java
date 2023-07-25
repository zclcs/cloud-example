package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
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
public class MinioBucketVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 桶id
     */
    private Long id;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 桶权限
     */
    private String bucketPolicy;


}