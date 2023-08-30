package com.zclcs.cloud.lib.core.base;

import com.mybatisflex.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 基础实体
 *
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity {

    /**
     * 版本
     */
    @Column("version")
    private Long version;

    /**
     * 租户id
     */
    @Column("tenant_id")
    private String tenantId;

    /**
     * 创建时间
     */
    @Column("create_at")
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @Column("create_by")
    private String createBy;

    /**
     * 编辑时间
     */
    @Column("update_at")
    private LocalDateTime updateAt;

    /**
     * 编辑人
     */
    @Column("update_by")
    private String updateBy;

}
