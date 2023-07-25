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
public class VueColumnVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 服务名
     */
    @Schema(title = "表格名")
    private String title;

    /**
     * 服务名
     */
    @Schema(title = "表格data映射")
    private String dataIndex;
}
