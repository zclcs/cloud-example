package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zhouc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "SchemaVo对象", description = "mysql字段")
public class SchemaVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "库名+表名")
    private String tableSchema;

    @Schema(title = "字段名称")
    private String columnName;
}
