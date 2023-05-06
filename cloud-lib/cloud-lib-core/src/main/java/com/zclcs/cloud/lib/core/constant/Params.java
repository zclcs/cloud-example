package com.zclcs.cloud.lib.core.constant;

/**
 * 请求参数常量
 *
 * @author zclcs
 */
public interface Params {

    /**
     * 默认的菜单图标
     */
    String DEFAULT_MENU_ICON = "ant-design:appstore-add-outlined";

    /**
     * 请求方法所有
     */
    String METHOD_ALL = "all";

    /**
     * 代码生成权限集合
     */
    String[] AUTHS = new String[]{"view", "add", "delete", "update"};

    /**
     * 代码生成权限集合
     */
    String AUTH_VIEW = "view";

    /**
     * 按钮文字
     */
    String[] BUTTON_TEXT = new String[]{"查看", "新增", "删除", "修改"};

    /**
     * 代码压缩包后缀
     */
    String SUFFIX = "_code.zip";

}
