package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单 Vo
 *
 * @author zclcs
 * @since 2023-09-01 20:05:00.313
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MenuVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 目录/菜单/按钮id
     * 默认值：
     */
    private Long menuId;

    /**
     * 目录/菜单/按钮编码（唯一值）
     * 默认值：
     */
    private String menuCode;

    /**
     * 上级目录/菜单编码
     * 默认值：0
     */
    private String parentCode;

    /**
     * 目录/菜单/按钮名称
     * 默认值：
     */
    private String menuName;

    /**
     * 页面缓存名称
     * 默认值：
     */
    private String keepAliveName;

    /**
     * 对应路由path
     * 默认值：
     */
    private String path;

    /**
     * 对应路由组件component
     * 默认值：
     */
    private String component;

    /**
     * 打开目录重定向的子菜单
     * 默认值：
     */
    private String redirect;

    /**
     * 权限标识
     * 默认值：
     */
    private String perms;

    /**
     * 图标
     * 默认值：
     */
    private String icon;

    /**
     * 类型 @@system_menu.type
     * 默认值：0
     */
    private String type;

    /**
     * 类型 @@system_menu.type
     */
    private String typeText;

    public String getTypeText() {
        return DictCacheUtil.getDictTitle("system_menu.type", this.type);
    }

    /**
     * 是否隐藏菜单 @@yes_no
     * 默认值：0
     */
    private String hideMenu;

    /**
     * 是否隐藏菜单 @@yes_no
     */
    private String hideMenuText;

    public String getHideMenuText() {
        return DictCacheUtil.getDictTitle("yes_no", this.hideMenu);
    }

    /**
     * 是否忽略KeepAlive缓存 @@yes_no
     * 默认值：0
     */
    private String ignoreKeepAlive;

    /**
     * 是否忽略KeepAlive缓存 @@yes_no
     */
    private String ignoreKeepAliveText;

    public String getIgnoreKeepAliveText() {
        return DictCacheUtil.getDictTitle("yes_no", this.ignoreKeepAlive);
    }

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no
     * 默认值：0
     */
    private String hideBreadcrumb;

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no
     */
    private String hideBreadcrumbText;

    public String getHideBreadcrumbText() {
        return DictCacheUtil.getDictTitle("yes_no", this.hideBreadcrumb);
    }

    /**
     * 隐藏所有子菜单 @@yes_no
     * 默认值：0
     */
    private String hideChildrenInMenu;

    /**
     * 隐藏所有子菜单 @@yes_no
     */
    private String hideChildrenInMenuText;

    public String getHideChildrenInMenuText() {
        return DictCacheUtil.getDictTitle("yes_no", this.hideChildrenInMenu);
    }

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     * 默认值：
     */
    private String currentActiveMenu;

    /**
     * 排序
     * 默认值：0
     */
    private Double orderNum;

    /**
     * 目录/菜单/按钮id集合
     */
    private List<Long> menuIds;

    /**
     * 菜单类型集合 @@system_menu.type
     */
    private List<String> types;

    public String getTypesText() {
        return DictCacheUtil.getDictTitleArray("system_menu.type", this.type);
    }


}