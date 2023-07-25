package com.zclcs.platform.system.api.bean.vo;


import com.zclcs.cloud.lib.core.bean.Tree;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单树
 *
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTreeVo extends Tree<MenuVo> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    @DictText(value = "system_menu.type")
    private String type;

    /**
     * 是否隐藏菜单 @@yes_no 默认否
     */
    @DictText(value = "yes_no")
    private String hideMenu;

    /**
     * 是否忽略KeepAlive缓存 @@yes_no 默认否
     */
    @DictText(value = "yes_no")
    private String ignoreKeepAlive;

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no 默认否
     */
    @DictText(value = "yes_no")
    private String hideBreadcrumb;

    /**
     * 隐藏所有子菜单 @@yes_no 默认否
     */
    @DictText(value = "yes_no")
    private String hideChildrenInMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    private String currentActiveMenu;

    /**
     * 排序
     */
    private Double orderNum;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;
}
