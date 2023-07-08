package com.zclcs.platform.system.api.bean.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "Table", description = "表")
public class Table {

    @Schema(title = "名称")
    private String name;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "库名")
    private String datasource;

    @Schema(title = "数据量（行）")
    private Long dataRows;

    @Schema(title = "表字符集")
    private String tableCollation;

    @Schema(title = "创建时间")
    private LocalDateTime createAt;

    @Schema(title = "修改时间")
    private LocalDateTime updateAt;
}
