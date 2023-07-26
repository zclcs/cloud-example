package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * mysql字段
 *
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SchemaVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 库名+表名
     */
    private String tableSchema;

    /**
     * 字段名称
     */
    private String columnName;
}
