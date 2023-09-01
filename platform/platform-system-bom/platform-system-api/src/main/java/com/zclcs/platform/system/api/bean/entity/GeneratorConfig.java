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
 * 代码生成配置 Entity
 *
 * @author zclcs
 * @since 2023-09-01 20:04:43.904
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_generator_config")
public class GeneratorConfig extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 服务名
     */
    @Column("server_name")
    private String serverName;

    /**
     * 作者
     */
    @Column("author")
    private String author;

    /**
     * 基础包名
     */
    @Column("base_package")
    private String basePackage;

    /**
     * entity文件存放路径
     */
    @Column("entity_package")
    private String entityPackage;

    /**
     * 入参
     */
    @Column("ao_package")
    private String aoPackage;

    /**
     * 出参
     */
    @Column("vo_package")
    private String voPackage;

    /**
     * mapper文件存放路径
     */
    @Column("mapper_package")
    private String mapperPackage;

    /**
     * mapper xml文件存放路径
     */
    @Column("mapper_xml_package")
    private String mapperXmlPackage;

    /**
     * service文件存放路径
     */
    @Column("service_package")
    private String servicePackage;

    /**
     * serviceImpl文件存放路径
     */
    @Column("service_impl_package")
    private String serviceImplPackage;

    /**
     * controller文件存放路径
     */
    @Column("controller_package")
    private String controllerPackage;

    /**
     * 是否去除前缀 @@yes_no
     */
    @Column("is_trim")
    private String isTrim;

    /**
     * 前缀内容
     */
    @Column("trim_value")
    private String trimValue;

    /**
     * 需要排除的字段
     */
    @Column("exclude_columns")
    private String excludeColumns;

    /**
     * 版本 @@generate.version
     */
    @Column("gen_version")
    private String genVersion;

    /**
     * 缓存实体
     */
    @Column("cache_vo_package")
    private String cacheVoPackage;

    /**
     * excel实体
     */
    @Column("excel_vo_package")
    private String excelVoPackage;


}