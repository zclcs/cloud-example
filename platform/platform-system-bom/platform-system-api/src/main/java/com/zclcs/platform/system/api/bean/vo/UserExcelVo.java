package com.zclcs.platform.system.api.bean.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;


}