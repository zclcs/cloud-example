package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 部门 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:38.826
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeptVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     * 默认值：
     */
    private Long deptId;

    /**
     * 部门编码
     * 默认值：
     */
    private String deptCode;

    /**
     * 上级部门编码
     * 默认值：0
     */
    private String parentCode;

    /**
     * 部门名称
     * 默认值：
     */
    private String deptName;

    /**
     * 排序
     * 默认值：1
     */
    private Double orderNum;

    /**
     * 创建时间-开始
     */
    private LocalDate createTimeFrom;

    /**
     * 创建时间-结束
     */
    private LocalDate createTimeTo;


}