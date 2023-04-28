package com.zclcs.platform.system.api.entity.vo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.zclcs.common.core.base.BaseEntity;
import com.zclcs.common.dict.json.annotation.DictText;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "GeneratorConfigVo", description = "代码生成配置表")
public class GeneratorConfigVo extends BaseEntity implements Serializable {

    public static final String TRIM_YES = "1";
    public static final String TRIM_NO = "0";
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "配置id")
    private Long id;

    @Schema(title = "服务名")
    private String serverName;

    @Schema(title = "作者")
    private String author;

    @Schema(title = "基础包名")
    private String basePackage;

    @Schema(title = "entity包名")
    private String entityPackage;

    @Schema(title = "入参包名")
    private String aoPackage;

    @Schema(title = "出参包名")
    private String voPackage;

    @Schema(title = "mapper包名")
    private String mapperPackage;

    @Schema(title = "mapper xml包名")
    private String mapperXmlPackage;

    @Schema(title = "service包名")
    private String servicePackage;

    @Schema(title = "serviceImpl包名")
    private String serviceImplPackage;

    @Schema(title = "controller包名")
    private String controllerPackage;

    @Schema(title = "是否去除前缀 @@yes_no")
    @DictText("yes_no")
    private String isTrim;

    @Schema(title = "前缀内容")
    private String trimValue;

    @Schema(title = "需要排除的字段")
    private String excludeColumns;

    @Schema(title = "java文件路径，固定值")
    @Builder.Default
    private String javaPath = "/src/main/java/";

    @Schema(title = "配置文件存放路径，固定值")
    @Builder.Default
    private String resourcesPath = "src/main/resources";

    @Schema(title = "文件生成日期")
    @Builder.Default
    private String date = DateUtil.date().toString(DatePattern.NORM_DATETIME_MS_PATTERN);

    @Schema(title = "表名")
    private String tableName;

    @Schema(title = "表注释")
    private String tableComment;

    @Schema(title = "数据表对应的类名")
    private String className;

    @Schema(title = "数据表对应的主键字段名")
    private String keyName;

}