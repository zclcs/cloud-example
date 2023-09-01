package com.zclcs.cloud.lib.dict.bean.entity;

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
 * 字典 Entity
 *
 * @author zclcs
 * @since 2023-09-01 20:03:54.686
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_dict_item")
public class DictItem extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 字典key
     */
    @Column("dict_name")
    private String dictName;

    /**
     * 父级字典值
     */
    @Column("parent_value")
    private String parentValue;

    /**
     * 值
     */
    @Column("value")
    private String value;

    /**
     * 标签
     */
    @Column("title")
    private String title;

    /**
     * 字典类型 @@system_dict_item.type
     */
    @Column("type")
    private String type;

    /**
     * 是否系统字典 @@yes_no
     */
    @Column("whether_system_dict")
    private String whetherSystemDict;

    /**
     * 描述
     */
    @Column("description")
    private String description;

    /**
     * 排序（升序）
     */
    @Column("sorted")
    private Integer sorted;

    /**
     * 是否禁用 @@yes_no
     */
    @Column("is_disabled")
    private String isDisabled;


}