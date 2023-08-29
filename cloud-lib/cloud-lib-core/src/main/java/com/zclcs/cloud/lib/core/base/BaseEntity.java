package com.zclcs.cloud.lib.core.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.mybatisflex.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 基础实体
 *
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity {

    /**
     * 版本
     */
    @Version
    @TableField("version")
    @Column("version")
    private Long version;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    @Column("tenant_id")
    private String tenantId;

    /**
     * 创建时间
     */
    @TableField(value = "create_at", fill = FieldFill.INSERT)
    @Column("create_at")
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @Column("create_by")
    private String createBy;

    /**
     * 编辑时间
     */
    @TableField(value = "update_at", fill = FieldFill.UPDATE)
    @Column("update_at")
    private LocalDateTime updateAt;

    /**
     * 编辑人
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    @Column("update_by")
    private String updateBy;

}
