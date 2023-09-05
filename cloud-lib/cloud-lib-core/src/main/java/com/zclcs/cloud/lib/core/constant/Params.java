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
    String[] AUTHS = new String[]{"page", "list", "one", "add", "add:batch", "delete", "update", "update:batch", "createOrUpdate:batch"};

    /**
     * 按钮文字
     */
    String[] BUTTON_TEXT = new String[]{"分页", "集合", "单个", "新增", "批量新增", "删除", "修改", "批量修改", "批量新增或修改"};

    /**
     * 代码压缩包后缀
     */
    String SUFFIX = "_code.zip";

}
