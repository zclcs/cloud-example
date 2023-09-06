package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("information_schema.COLUMNS")
public class ColumnInfo {

    /**
     * 名称
     */
    @Column("COLUMN_NAME")
    private String name;

    /**
     * 字段类型
     */
    @Column("COLUMN_KEY")
    private String key;

    /**
     * 是否为主键
     */
    @Column(ignore = true)
    private Boolean isKey;

    /**
     * 是否可以为NULL
     */
    @Column("IS_NULLABLE")
    private String nullable;

    /**
     * 是否可以为空
     */
    @Column(ignore = true)
    private Boolean isNullable;

    /**
     * 类型
     */
    @Column("DATA_TYPE")
    private String type;

    /**
     * 字符串长度限制
     */
    @Column("CHARACTER_MAXIMUM_LENGTH")
    private String charMaxLength;

    /**
     * 字符串是否有长度限制
     */
    @Column(ignore = true)
    private Boolean isCharMaxLength;

    /**
     * 注释
     */
    @Column("COLUMN_COMMENT")
    private String remark;

    /**
     * 默认值
     */
    @Column("COLUMN_DEFAULT")
    private String defaultValue;

    /**
     * 库名
     */
    @Column("TABLE_SCHEMA")
    private String tableSchema;

    /**
     * 表名
     */
    @Column("TABLE_NAME")
    private String tableName;

    /**
     * 注释中是否有字典
     */
    private Boolean hasDict;

    /**
     * 属性名称
     */
    @Column(ignore = true)
    private String field;

    /**
     * 属性名称
     */
    @Column(ignore = true)
    private String fieldUpperCase;

    /**
     * 去掉注释中带的字典
     */
    @Column(ignore = true)
    private String remarkSpiltDict;

    /**
     * 注释中带的字典
     */
    @Column(ignore = true)
    private String remarkDict;

    /**
     * 字典是否是数组
     */
    @Column(ignore = true)
    private Boolean isArray;

    /**
     * 字典是否是tree
     */
    @Column(ignore = true)
    private Boolean isTree;

}
