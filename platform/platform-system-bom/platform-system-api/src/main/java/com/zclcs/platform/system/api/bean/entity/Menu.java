package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单 Entity
 *
 * @author zclcs
 * @since 2023-01-10 10:39:18.238
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_menu")
public class Menu extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 目录/菜单/按钮id
     */
    @Id(value = "menu_id", keyType = KeyType.Auto)
    private Long menuId;

    /**
     * 目录/菜单/按钮编码（唯一值）
     */
    @Column("menu_code")
    private String menuCode;

    /**
     * 上级目录/菜单编码
     */
    @Column("parent_code")
    private String parentCode;

    /**
     * 目录/菜单/按钮名称
     */
    @Column("menu_name")
    private String menuName;

    /**
     * 页面缓存名称
     */
    @Column("keep_alive_name")
    private String keepAliveName;

    /**
     * 对应路由path
     */
    @Column("path")
    private String path;

    /**
     * 对应路由组件component
     */
    @Column("component")
    private String component;

    /**
     * 打开目录重定向的子菜单
     */
    @Column("redirect")
    private String redirect;

    /**
     * 权限标识
     */
    @Column("perms")
    private String perms;

    /**
     * 图标
     */
    @Column("icon")
    private String icon;

    /**
     * 类型 @@system_menu.type
     */
    @Column("type")
    private String type;

    /**
     * 是否隐藏菜单 @@yes_no
     */
    @Column("hide_menu")
    private String hideMenu;

    /**
     * 是否忽略KeepAlive缓存 @@yes_no
     */
    @Column("ignore_keep_alive")
    private String ignoreKeepAlive;

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no
     */
    @Column("hide_breadcrumb")
    private String hideBreadcrumb;

    /**
     * 隐藏所有子菜单 @@yes_no
     */
    @Column("hide_children_in_menu")
    private String hideChildrenInMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    @Column("current_active_menu")
    private String currentActiveMenu;

    /**
     * 排序
     */
    @Column("order_num")
    private Double orderNum;


}