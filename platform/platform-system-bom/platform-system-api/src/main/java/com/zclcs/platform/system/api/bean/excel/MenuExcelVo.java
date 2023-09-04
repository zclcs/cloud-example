package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

/**
 * 菜单 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 20:05:00.313
 */
@Data
public class MenuExcelVo {

    /**
     * 目录/菜单/按钮id
     */
    @ExcelProperty(value = "目录/菜单/按钮id")
    private Long menuId;

    /**
     * 目录/菜单/按钮编码（唯一值）
     */
    @ExcelProperty(value = "目录/菜单/按钮编码（唯一值）")
    private String menuCode;

    /**
     * 上级目录/菜单编码
     */
    @ExcelProperty(value = "上级目录/菜单编码")
    private String parentCode;

    /**
     * 目录/菜单/按钮名称
     */
    @ExcelProperty(value = "目录/菜单/按钮名称")
    private String menuName;

    /**
     * 页面缓存名称
     */
    @ExcelProperty(value = "页面缓存名称")
    private String keepAliveName;

    /**
     * 对应路由path
     */
    @ExcelProperty(value = "对应路由path")
    private String path;

    /**
     * 对应路由组件component
     */
    @ExcelProperty(value = "对应路由组件component")
    private String component;

    /**
     * 打开目录重定向的子菜单
     */
    @ExcelProperty(value = "打开目录重定向的子菜单")
    private String redirect;

    /**
     * 权限标识
     */
    @ExcelProperty(value = "权限标识")
    private String perms;

    /**
     * 图标
     */
    @ExcelProperty(value = "图标")
    private String icon;

    /**
     * 类型 @@system_menu.type
     */
    @ExcelProperty(value = "类型 @@system_menu.type")
    private String type;

    public void setType(String type) {
        this.type = DictCacheUtil.getDictTitle("system_menu.type", type);
    }

    /**
     * 是否隐藏菜单 @@yes_no
     */
    @ExcelProperty(value = "是否隐藏菜单 @@yes_no")
    private String hideMenu;

    public void setHideMenu(String hideMenu) {
        this.hideMenu = DictCacheUtil.getDictTitle("yes_no", hideMenu);
    }

    /**
     * 是否忽略KeepAlive缓存 @@yes_no
     */
    @ExcelProperty(value = "是否忽略KeepAlive缓存 @@yes_no")
    private String ignoreKeepAlive;

    public void setIgnoreKeepAlive(String ignoreKeepAlive) {
        this.ignoreKeepAlive = DictCacheUtil.getDictTitle("yes_no", ignoreKeepAlive);
    }

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no
     */
    @ExcelProperty(value = "隐藏该路由在面包屑上面的显示 @@yes_no")
    private String hideBreadcrumb;

    public void setHideBreadcrumb(String hideBreadcrumb) {
        this.hideBreadcrumb = DictCacheUtil.getDictTitle("yes_no", hideBreadcrumb);
    }

    /**
     * 隐藏所有子菜单 @@yes_no
     */
    @ExcelProperty(value = "隐藏所有子菜单 @@yes_no")
    private String hideChildrenInMenu;

    public void setHideChildrenInMenu(String hideChildrenInMenu) {
        this.hideChildrenInMenu = DictCacheUtil.getDictTitle("yes_no", hideChildrenInMenu);
    }

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    @ExcelProperty(value = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Double orderNum;


}