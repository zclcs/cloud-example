package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码生成配置 Ao
 *
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GeneratorConfigAo {

    /**
     * 配置id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    /**
     * 服务名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String serverName;

    /**
     * 作者
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String author;

    /**
     * 版本 @@generate.version
     * 默认 02
     */
    @NotBlank(message = "{required}")
    @Size(max = 2, message = "{noMoreThan}")
    @DictValid("generate.version")
    private String genVersion;

    /**
     * 基础包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String basePackage;

    /**
     * entity包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String entityPackage;

    /**
     * 入参包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String aoPackage;

    /**
     * 出参包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String voPackage;

    /**
     * 缓存实体
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String cacheVoPackage;

    /**
     * excel实体
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String excelVoPackage;

    /**
     * mapper包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String mapperPackage;

    /**
     * mapper xml包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String mapperXmlPackage;

    /**
     * service包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String servicePackage;

    /**
     * serviceImpl包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String serviceImplPackage;

    /**
     * controller包名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String controllerPackage;

    /**
     * 是否去除前缀
     */
    @NotBlank(message = "{required}")
    @Size(max = 1, message = "{noMoreThan}")
    @DictValid("yes_no")
    private String isTrim;

    /**
     * 前缀内容
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String trimValue;

    /**
     * 需要排除的字段
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String excludeColumns;

}