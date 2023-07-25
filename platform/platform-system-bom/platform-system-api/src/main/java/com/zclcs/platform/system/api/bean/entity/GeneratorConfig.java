package com.zclcs.platform.system.api.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码生成配置 Entity
 *
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_generator_config")
public class GeneratorConfig extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 服务名
     */
    @TableField("server_name")
    private String serverName;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 基础包名
     */
    @TableField("base_package")
    private String basePackage;

    /**
     * entity文件存放路径
     */
    @TableField("entity_package")
    private String entityPackage;

    /**
     * 入参
     */
    @TableField("ao_package")
    private String aoPackage;

    /**
     * 出参
     */
    @TableField("vo_package")
    private String voPackage;

    /**
     * mapper文件存放路径
     */
    @TableField("mapper_package")
    private String mapperPackage;

    /**
     * mapper xml文件存放路径
     */
    @TableField("mapper_xml_package")
    private String mapperXmlPackage;

    /**
     * servcie文件存放路径
     */
    @TableField("service_package")
    private String servicePackage;

    /**
     * serviceImpl文件存放路径
     */
    @TableField("service_impl_package")
    private String serviceImplPackage;

    /**
     * controller文件存放路径
     */
    @TableField("controller_package")
    private String controllerPackage;

    /**
     * 是否去除前缀
     */
    @TableField("is_trim")
    private String isTrim;

    /**
     * 前缀内容
     */
    @TableField("trim_value")
    private String trimValue;

    /**
     * 需要排除的字段
     */
    @TableField("exclude_columns")
    private String excludeColumns;

}