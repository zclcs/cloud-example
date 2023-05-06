package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.Array;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单 Vo
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
@Schema(title = "MenuVo对象", description = "菜单")
public class MenuVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "目录/菜单/按钮id")
    private Long menuId;

    @Schema(description = "目录/菜单/按钮编码（唯一值）")
    private String menuCode;

    @Schema(description = "目录/菜单/按钮id集合")
    private List<Long> menuIds;

    @Schema(description = "上级目录/菜单编码")
    private String parentCode;

    @Schema(description = "目录/菜单/按钮名称")
    private String menuName;

    @Schema(description = "页面缓存名称")
    private String keepAliveName;

    @Schema(description = "对应路由path")
    private String path;

    @Schema(description = "对应路由组件component")
    private String component;

    @Schema(description = "打开目录重定向的子菜单")
    private String redirect;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "类型 @@system_menu.type")
    @DictText(value = "system_menu.type")
    private String type;

    @Schema(description = "菜单类型集合 @@system_menu.type")
    @DictText(value = "system_menu.type", array = @Array(toText = false))
    private List<String> types;

    @Schema(description = "是否隐藏菜单 @@yes_no")
    @DictText(value = "yes_no")
    private String hideMenu;

    @Schema(description = "是否忽略KeepAlive缓存 1是 0否")
    @DictText(value = "yes_no")
    private String ignoreKeepAlive;

    @Schema(description = "隐藏该路由在面包屑上面的显示 1是 0否")
    @DictText(value = "yes_no")
    private String hideBreadcrumb;

    @Schema(description = "隐藏所有子菜单 1是 0否")
    @DictText(value = "yes_no")
    private String hideChildrenInMenu;

    @Schema(description = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(description = "排序")
    private Double orderNum;


}