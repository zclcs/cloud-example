package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 数据表 Entity
 *
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("information_schema.TABLES")
public class TableInfo {

    /**
     * 名称
     */
    @Column(value = "TABLE_NAME")
    private String name;

    /**
     * 备注
     */
    @Column(value = "TABLE_COMMENT")
    private String remark;

    /**
     * 库名
     */
    @Column(value = "TABLE_SCHEMA")
    private String datasource;

    /**
     * 数据量（行）
     */
    @Column(value = "TABLE_ROWS")
    private Long dataRows;

    /**
     * 表字符集
     */
    @Column(value = "TABLE_COLLATION")
    private String tableCollation;

    /**
     * 创建时间
     */
    @Column(value = "CREATE_TIME")
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    @Column(value = "UPDATE_TIME")
    private LocalDateTime updateAt;
}
