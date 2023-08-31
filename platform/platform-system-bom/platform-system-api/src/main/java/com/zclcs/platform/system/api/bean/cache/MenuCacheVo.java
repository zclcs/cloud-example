package com.zclcs.platform.system.api.bean.cache;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单缓存
 *
 * @author zclcs
 * @since 2023-01-10 10:39:18.238
 */
@Data
public class MenuCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 目录/菜单/按钮id
     */
    private Long menuId;

    /**
     * 目录/菜单/按钮编码（唯一值）
     */
    private String menuCode;

    /**
     * 上级目录/菜单编码
     */
    private String parentCode;

    /**
     * 目录/菜单/按钮名称
     */
    private String menuName;

    /**
     * 页面缓存名称
     */
    private String keepAliveName;

    /**
     * 对应路由path
     */
    private String path;

    /**
     * 对应路由组件component
     */
    private String component;

    /**
     * 打开目录重定向的子菜单
     */
    private String redirect;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型 @@system_menu.type
     */
    private String type;

    /**
     * 是否隐藏菜单 @@yes_no
     */
    private String hideMenu;

    /**
     * 是否忽略KeepAlive缓存 @@yes_no
     */
    private String ignoreKeepAlive;

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no
     */
    private String hideBreadcrumb;

    /**
     * 隐藏所有子菜单 @@yes_no
     */
    private String hideChildrenInMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    private String currentActiveMenu;

    /**
     * 排序
     */
    private Double orderNum;


}