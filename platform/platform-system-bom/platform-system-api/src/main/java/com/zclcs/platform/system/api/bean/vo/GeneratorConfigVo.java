package com.zclcs.platform.system.api.bean.vo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代码生成配置表 Vo
 *
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GeneratorConfigVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 配置id
     */
    private Long id;

    /**
     * 服务名
     */
    private String serverName;

    /**
     * 作者
     */
    private String author;

    /**
     * 基础包名
     */
    private String basePackage;

    /**
     * entity包名
     */
    private String entityPackage;

    /**
     * 入参包名
     */
    private String aoPackage;

    /**
     * 出参包名
     */
    private String voPackage;

    /**
     * mapper包名
     */
    private String mapperPackage;

    /**
     * mapper xml包名
     */
    private String mapperXmlPackage;

    /**
     * service包名
     */
    private String servicePackage;

    /**
     * serviceImpl包名
     */
    private String serviceImplPackage;

    /**
     * controller包名
     */
    private String controllerPackage;

    /**
     * 是否去除前缀 @@yes_no
     */
    @DictText("yes_no")
    private String isTrim;

    /**
     * 前缀内容
     */
    private String trimValue;

    /**
     * 需要排除的字段
     */
    private String excludeColumns;

    /**
     * java文件路径，固定值
     */
    @Builder.Default
    private String javaPath = "/src/main/java/";

    /**
     * 配置文件存放路径，固定值
     */
    @Builder.Default
    private String resourcesPath = "src/main/resources";

    /**
     * 文件生成日期
     */
    @Builder.Default
    private String date = DateUtil.date().toString(DatePattern.NORM_DATETIME_MS_PATTERN);

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 数据表对应的类名
     */
    private String className;

    /**
     * 数据表对应的主键字段名
     */
    private String keyName;

}