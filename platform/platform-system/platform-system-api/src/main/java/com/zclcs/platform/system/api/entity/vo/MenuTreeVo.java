package com.zclcs.platform.system.api.entity.vo;


import com.zclcs.common.core.base.Tree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "MenuTreeVo对象", description = "菜单树")
public class MenuTreeVo extends Tree<MenuVo> implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Schema(description = "类型 0菜单 1按钮 2目录")
//    @DictText("menu_type")
    private String type;

    @Schema(description = "是否隐藏菜单 1是 0否")
//    @DictText("yes_no")
    private String hideMenu;

    @Schema(description = "是否忽略KeepAlive缓存 1是 0否")
//    @DictText("yes_no")
    private String ignoreKeepAlive;

    @Schema(description = "隐藏该路由在面包屑上面的显示 1是 0否")
//    @DictText("yes_no")
    private String hideBreadcrumb;

    @Schema(description = "隐藏所有子菜单 1是 0否")
//    @DictText("yes_no")
    private String hideChildrenInMenu;

    @Schema(description = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(description = "排序")
    private Double orderNum;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}