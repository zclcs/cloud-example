package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:39:18.238
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "MenuAo对象", description = "菜单")
public class MenuAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "目录/菜单/按钮id")
    private Long menuId;

    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "目录/菜单/按钮编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String menuCode;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(description = "上级目录/菜单编码 不填默认顶级")
    private String parentCode;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "目录/菜单/按钮名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String menuName;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "页面缓存名称")
    private String keepAliveName;

    @Size(max = 255, message = "{noMoreThan}")
    @Schema(description = "对应路由path")
    private String path;

    @Size(max = 255, message = "{noMoreThan}")
    @Schema(description = "对应路由组件component")
    private String component;

    @Size(max = 255, message = "{noMoreThan}")
    @Schema(description = "打开目录重定向的子菜单")
    private String redirect;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "权限标识")
    private String perms;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "图标")
    private String icon;

    @Size(max = 40, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @DictValid(value = "system_menu.type")
    @Schema(description = "类型 @@system_menu.type", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    @Schema(description = "是否隐藏菜单 @@yes_no 默认否", requiredMode = Schema.RequiredMode.REQUIRED)
    private String hideMenu;

    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    @Schema(description = "是否忽略KeepAlive缓存 @@yes_no 默认否", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ignoreKeepAlive;

    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    @Schema(description = "隐藏该路由在面包屑上面的显示 @@yes_no 默认否", requiredMode = Schema.RequiredMode.REQUIRED)
    private String hideBreadcrumb;

    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    @Schema(description = "隐藏所有子菜单 @@yes_no 默认否", requiredMode = Schema.RequiredMode.REQUIRED)
    private String hideChildrenInMenu;

    @Size(max = 255, message = "{noMoreThan}")
    @Schema(description = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(description = "排序")
    private Double orderNum;


}