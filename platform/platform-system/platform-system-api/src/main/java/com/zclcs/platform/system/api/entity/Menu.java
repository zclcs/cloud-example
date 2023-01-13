package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.common.datasource.starter.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 菜单 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:39:18.238
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_menu")
@Schema(name = "Menu对象", description = "菜单")
public class Menu extends BaseEntity {

    /**
     * 菜单/按钮id
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 上级菜单id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 目录/菜单/按钮名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 页面缓存名称
     */
    @TableField("keep_alive_name")
    private String keepAliveName;

    /**
     * 对应路由path
     */
    @TableField("path")
    private String path;

    /**
     * 对应路由组件component
     */
    @TableField("component")
    private String component;

    /**
     * 打开目录重定向的子菜单
     */
    @TableField("redirect")
    private String redirect;

    /**
     * 权限标识
     */
    @TableField("perms")
    private String perms;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 类型 0菜单 1按钮 2目录
     */
    @TableField("type")
    private String type;

    /**
     * 是否隐藏菜单 1是 0否
     */
    @TableField("hide_menu")
    private String hideMenu;

    /**
     * 是否忽略KeepAlive缓存 1是 0否
     */
    @TableField("ignore_keep_alive")
    private String ignoreKeepAlive;

    /**
     * 隐藏该路由在面包屑上面的显示 1是 0否
     */
    @TableField("hide_breadcrumb")
    private String hideBreadcrumb;

    /**
     * 隐藏所有子菜单 1是 0否
     */
    @TableField("hide_children_in_menu")
    private String hideChildrenInMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    @TableField("current_active_menu")
    private String currentActiveMenu;

    /**
     * 排序
     */
    @TableField("order_num")
    private Double orderNum;


}