package com.zclcs.platform.system.api.bean.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门树
 *
 * @author zclcs
 */
@Data
public class DeptTreeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否顶级部门
     */
    private Boolean harPar;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 排序
     */
    private Double orderNum;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

}
