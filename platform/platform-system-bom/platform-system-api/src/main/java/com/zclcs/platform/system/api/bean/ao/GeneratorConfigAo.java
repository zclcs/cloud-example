package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代码生成配置 Ao
 *
 * @author zclcs
 * @since 2023-09-01 20:04:43.904
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GeneratorConfigAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * 默认值：
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    /**
     * 服务名
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String serverName;

    /**
     * 作者
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String author;

    /**
     * 基础包名
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String basePackage;

    /**
     * entity文件存放路径
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String entityPackage;

    /**
     * 入参
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String aoPackage;

    /**
     * 出参
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String voPackage;

    /**
     * mapper文件存放路径
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String mapperPackage;

    /**
     * mapper xml文件存放路径
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String mapperXmlPackage;

    /**
     * service文件存放路径
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String servicePackage;

    /**
     * serviceImpl文件存放路径
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String serviceImplPackage;

    /**
     * controller文件存放路径
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String controllerPackage;

    /**
     * 是否去除前缀 @@yes_no
     * 默认值：0
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String isTrim;

    /**
     * 前缀内容
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String trimValue;

    /**
     * 需要排除的字段
     * 默认值：
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String excludeColumns;

    /**
     * 版本 @@generate.version
     * 默认值：02
     */
    @DictValid(value = "generate.version", message = "{dict}")
    @Size(max = 2, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String genVersion;

    /**
     * 缓存实体
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String cacheVoPackage;

    /**
     * excel实体
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String excelVoPackage;


}