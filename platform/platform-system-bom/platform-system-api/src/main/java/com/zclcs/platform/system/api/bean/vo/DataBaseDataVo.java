package com.zclcs.platform.system.api.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
public class DataBaseDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "表格字段定义")
    private List<VueColumnVo> columns;

    @Schema(title = "数据")
    private List<Map<String, Object>> data;
}
