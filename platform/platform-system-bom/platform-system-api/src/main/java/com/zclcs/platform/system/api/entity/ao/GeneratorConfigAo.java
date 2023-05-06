package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "GeneratorConfigAo", description = "代码生成配置表")
public class GeneratorConfigAo {

    @Schema(title = "配置id")
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    @Schema(title = "服务名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String serverName;

    @Schema(title = "作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String author;

    @Schema(title = "基础包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String basePackage;

    @Schema(title = "entity包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String entityPackage;

    @Schema(title = "入参包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String aoPackage;

    @Schema(title = "出参包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String voPackage;

    @Schema(title = "mapper包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String mapperPackage;

    @Schema(title = "mapper xml包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String mapperXmlPackage;

    @Schema(title = "service包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String servicePackage;

    @Schema(title = "serviceImpl包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String serviceImplPackage;

    @Schema(title = "controller包名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    private String controllerPackage;

    @Schema(title = "是否去除前缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{required}")
    @Size(max = 1, message = "{noMoreThan}")
    @DictValid("yes_no")
    private String isTrim;

    @Schema(title = "前缀内容")
    @Size(max = 50, message = "{noMoreThan}")
    private String trimValue;

    @Schema(title = "需要排除的字段")
    @Size(max = 255, message = "{noMoreThan}")
    private String excludeColumns;

}