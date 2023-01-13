package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

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
@Schema(name = "MenuAo对象", description = "菜单")
public class MenuAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "菜单/按钮id")
    private Long menuId;

    @NotNull(message = "{required}")
    @Schema(description = "上级菜单id", required = true)
    private Long parentId;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "目录/菜单/按钮名称", required = true)
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
    @Schema(description = "类型 0菜单 1按钮 2目录", required = true)
    private String type;

    @Size(max = 40, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "是否隐藏菜单 1是 0否", required = true)
    private String hideMenu;

    @Size(max = 40, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "是否忽略KeepAlive缓存 1是 0否", required = true)
    private String ignoreKeepAlive;

    @Size(max = 40, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "隐藏该路由在面包屑上面的显示 1是 0否", required = true)
    private String hideBreadcrumb;

    @Size(max = 40, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "隐藏所有子菜单 1是 0否", required = true)
    private String hideChildrenInMenu;

    @Size(max = 255, message = "{noMoreThan}")
    @Schema(description = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(description = "排序")
    private Double orderNum;


}