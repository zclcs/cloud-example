package com.zclcs.platform.maintenance.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zclcs
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

    /**
     * 服务名
     */
    @Schema(title = "库名+表名")
    private String tableSchema;

    /**
     * 服务名
     */
    @Schema(title = "字段名称")
    private String columnName;
}
