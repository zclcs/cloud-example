package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.common.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

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
@Schema(title = "Menu对象", description = "菜单")
public class Menu extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 目录/菜单/按钮id
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 目录/菜单/按钮编码（唯一值）
     */
    @TableField("menu_code")
    private String menuCode;

    /**
     * 上级目录/菜单编码
     */
    @TableField("parent_code")
    private String parentCode;

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
     * 类型 @@system_menu.type
     */
    @TableField("type")
    private String type;

    /**
     * 是否隐藏菜单 @@yes_no
     */
    @TableField("hide_menu")
    private String hideMenu;

    /**
     * 是否忽略KeepAlive缓存 @@yes_no
     */
    @TableField("ignore_keep_alive")
    private String ignoreKeepAlive;

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no
     */
    @TableField("hide_breadcrumb")
    private String hideBreadcrumb;

    /**
     * 隐藏所有子菜单 @@yes_no
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