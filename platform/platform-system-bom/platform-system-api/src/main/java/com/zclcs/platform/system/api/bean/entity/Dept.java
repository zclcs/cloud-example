package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门 Entity
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
@Table("system_dept")
public class Dept extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @Id(keyType = KeyType.Auto)
    private Long deptId;

    /**
     * 部门编码
     */
    @Column("dept_code")
    private String deptCode;

    /**
     * 上级部门编码
     */
    @Column("parent_code")
    private String parentCode;

    /**
     * 部门名称
     */
    @Column("dept_name")
    private String deptName;

    /**
     * 排序
     */
    @Column("order_num")
    private Double orderNum;


}