package com.zclcs.platform.system.api.entity.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "GenerateAo", description = "代码生成参数")
public class GenerateAo {

    @NotNull(message = "{required}")
    @Schema(title = "服务名", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long generatorConfigId;

    @NotBlank(message = "{required}")
    @Schema(title = "表名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "{required}")
    @Schema(title = "库名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String datasource;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "是否需要新建菜单 yes_no 默认 否")
    private String isCreateMenu;

    @Schema(title = "菜单名称 不填默认表注释")
    private String menuName;

    @Schema(title = "菜单路径 不填默认类名")
    private String menuPath;

    @Schema(title = "路由组件")
    private String menuComponent;

    @Schema(title = "上级菜单code")
    private String menuCode;

    @Schema(title = "是否创建目录 yes_no 默认 否 默认菜单在该目录下")
    private String isCreateDir;

    @Schema(title = "目录名称")
    private String dirName;

    @Schema(title = "目录路由地址")
    private String dirPath;

}
