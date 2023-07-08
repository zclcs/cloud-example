package com.zclcs.cloud.lib.dict.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典项 Entity
 *
 * @author zclcs
 * @date 2023-03-06 10:56:41.301
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_dict_item")
@Schema(title = "DictItem对象", description = "字典项")
public class DictItem extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典key
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 父级字典值
     */
    @TableField("parent_value")
    private String parentValue;

    /**
     * 值
     */
    @TableField("value")
    private String value;

    /**
     * 标签
     */
    @TableField("title")
    private String title;

    /**
     * 字典类型 @@system_dict_item.type
     */
    @TableField("type")
    private String type;

    /**
     * 是否系统字典 @@yes_no
     */
    @TableField("whether_system_dict")
    private String whetherSystemDict;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 排序（升序）
     */
    @TableField("sorted")
    private Integer sorted;

    /**
     * 是否禁用 @@yes_no
     */
    @TableField("is_disabled")
    private String isDisabled;


}