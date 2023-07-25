package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
public class DataBaseDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表格字段定义
     */
    private List<VueColumnVo> columns;

    /**
     * 数据
     */
    private List<Map<String, Object>> data;
}
