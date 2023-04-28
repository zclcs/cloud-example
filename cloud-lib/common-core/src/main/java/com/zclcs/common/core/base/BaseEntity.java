package com.zclcs.common.core.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author zhouchenglong
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "BaseEntity", description = "基础实体")
public class BaseEntity {

    /**
     * 版本
     */
    @Version
    @TableField("version")
    @Schema(description = "版本")
    private Long version;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    @Schema(description = "租户id")
    private String tenantId;

    /**
     * 创建时间
     */
    @TableField(value = "create_at", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 编辑时间
     */
    @TableField(value = "update_at", fill = FieldFill.UPDATE)
    @Schema(description = "编辑时间")
    private LocalDateTime updateAt;

    /**
     * 编辑人
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    @Schema(description = "编辑人")
    private String updateBy;

}
