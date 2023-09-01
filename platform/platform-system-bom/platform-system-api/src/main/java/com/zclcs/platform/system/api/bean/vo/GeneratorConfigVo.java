package com.zclcs.platform.system.api.bean.vo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代码生成配置 Vo
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
public class GeneratorConfigVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * 默认值：
     */
    private Long id;

    /**
     * 服务名
     * 默认值：
     */
    private String serverName;

    /**
     * 作者
     * 默认值：
     */
    private String author;

    /**
     * 基础包名
     * 默认值：
     */
    private String basePackage;

    /**
     * entity文件存放路径
     * 默认值：
     */
    private String entityPackage;

    /**
     * 入参
     * 默认值：
     */
    private String aoPackage;

    /**
     * 出参
     * 默认值：
     */
    private String voPackage;

    /**
     * mapper文件存放路径
     * 默认值：
     */
    private String mapperPackage;

    /**
     * mapper xml文件存放路径
     * 默认值：
     */
    private String mapperXmlPackage;

    /**
     * service文件存放路径
     * 默认值：
     */
    private String servicePackage;

    /**
     * serviceImpl文件存放路径
     * 默认值：
     */
    private String serviceImplPackage;

    /**
     * controller文件存放路径
     * 默认值：
     */
    private String controllerPackage;

    /**
     * 是否去除前缀 @@yes_no
     * 默认值：0
     */
    private String isTrim;

    /**
     * 是否去除前缀 @@yes_no
     */
    private String isTrimText;

    public String getIsTrimText() {
        return DictCacheUtil.getDictTitle("yes_no", this.isTrim);
    }

    /**
     * 前缀内容
     * 默认值：
     */
    private String trimValue;

    /**
     * 需要排除的字段
     * 默认值：
     */
    private String excludeColumns;

    /**
     * 版本 @@generate.version
     * 默认值：02
     */
    private String genVersion;

    /**
     * 版本 @@generate.version
     */
    private String genVersionText;

    public String getGenVersionText() {
        return DictCacheUtil.getDictTitle("generate.version", this.genVersion);
    }

    /**
     * 缓存实体
     * 默认值：
     */
    private String cacheVoPackage;

    /**
     * excel实体
     * 默认值：
     */
    private String excelVoPackage;

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