package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * VueColumnVo
 *
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VueColumnVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表格名
     */
    private String title;

    /**
     * 表格data映射
     */
    private String dataIndex;
}
