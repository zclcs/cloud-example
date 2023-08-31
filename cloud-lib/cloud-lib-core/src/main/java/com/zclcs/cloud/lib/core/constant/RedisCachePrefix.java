package com.zclcs.cloud.lib.core.constant;

/**
 * 系统常量类
 *
 * @author zclcs
 */
public interface RedisCachePrefix {

    String BLACKLIST_CACHE_KEY_PREFIX = "my:route:blacklist:";

    String RATE_LIMIT_CACHE_KEY_PREFIX = "my:route:rate-limit:";

    String RATE_LIMIT_COUNT_KEY_PREFIX = "my:route:rate-limit:count:";

    String NACOS_TOKEN_PREFIX = "nacos";

    String XXL_JOB_COOKIE_PREFIX = "xxl-job";

    /**
     * 字典 redis key
     */
    String REFRESH_TOKEN_PREFIX = "%s:login:refresh:%s";

    /**
     * 字典 redis key
     */
    String DICT_PREFIX = "dict:%s";

    /**
     * 字典项 redis key
     */
    String DICT_ITEM_PREFIX = "dict:%s:item:%s";

    /**
     * 子级字典 redis key
     */
    String DICT_CHILDREN_PREFIX = "dict:%s:children:%s";

    /**
     * 验证码 key前缀
     */
    String CODE_PREFIX = "captcha:";

    /**
     * client redis key
     */
    String CLIENT_DETAILS_PREFIX = "client_details:%s";

    /**
     * 用户 redis key
     */
    String USER_PREFIX = "user:%s";

    /**
     * 用户 权限 redis key
     */
    String USER_PERMISSIONS_PREFIX = "user:permissions:%s";

    /**
     * 用户 路由 redis key
     */
    String USER_ROUTERS_PREFIX = "user:routers:%s";

    /**
     * 用户-手机号 redis key
     */
    String USER_MOBILE_PREFIX = "user:mobile:%s";

    /**
     * 用户-角色 redis key
     */
    String USER_ROLE_PREFIX = "user:role:%s";

    /**
     * 用户-数据权限 redis key
     */
    String USER_DATA_PERMISSION_PREFIX = "user:data:permission:%s";

    /**
     * 角色redis key
     */
    String ROLE_PREFIX = "role:%s";

    /**
     * 角色-菜单 redis key
     */
    String ROLE_MENU_PREFIX = "role:menu:%s";

    /**
     * 菜单 redis key
     */
    String MENU_PREFIX = "menu:%s";

    /**
     * 部门 redis key
     */
    String DEPT_PREFIX = "dept:%s";

    /**
     * 角色缓存key布隆过滤器
     */
    String BLOOM_FILTER_ROLE_PREFIX = "{bloom_filter_role}";

    /**
     * 菜单缓存key布隆过滤器
     */
    String BLOOM_FILTER_MENU_PREFIX = "{bloom_filter_menu}";

    /**
     * 部门缓存key布隆过滤器
     */
    String BLOOM_FILTER_DEPT_PREFIX = "{bloom_filter_dept}";

}
