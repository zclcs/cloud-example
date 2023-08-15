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

    String NACOS_TOKEN = "nacos";

    String XXL_JOB_COOKIE = "xxl-job";

    /**
     * 字典 redis key
     */
    String REFRESH_TOKEN = "%s:login:refresh:%s";

    /**
     * 字典 redis key
     */
    String DICT = "dict:%s";

    /**
     * 字典项 redis key
     */
    String DICT_ITEM = "dict:%s:item:%s";

    /**
     * 子级字典 redis key
     */
    String DICT_CHILDREN = "dict:%s:children:%s";

    /**
     * 验证码 key前缀
     */
    String CODE_PREFIX = "captcha:";

    /**
     * client redis key
     */
    String CLIENT_DETAILS = "client_details:%s";

    /**
     * 用户 redis key
     */
    String USER = "user:%s";

    /**
     * 用户 权限 redis key
     */
    String USER_PERMISSIONS = "user:permissions:%s";

    /**
     * 用户 路由 redis key
     */
    String USER_ROUTERS = "user:routers:%s";

    /**
     * 用户-手机号 redis key
     */
    String USER_MOBILE = "user:mobile:%s";

    /**
     * 用户-角色 redis key
     */
    String USER_ROLE = "user:role:%s";

    /**
     * 用户-角色缓存key布隆过滤器
     */
    String BLOOM_FILTER_USER_ROLE = "{bloom_filter_user_role}";

    /**
     * 用户-数据权限 redis key
     */
    String USER_DATA_PERMISSION = "user:data:permission:%s";

    /**
     * 用户-数据权限缓存key布隆过滤器
     */
    String BLOOM_FILTER_USER_DATA_PERMISSION = "{bloom_filter_user_data_permission}";

    /**
     * 角色redis key
     */
    String ROLE = "role:%s";

    /**
     * 角色缓存key布隆过滤器
     */
    String BLOOM_FILTER_ROLE = "{bloom_filter_role}";

    /**
     * 角色-菜单 redis key
     */
    String ROLE_MENU = "role:menu:%s";

    /**
     * 角色-菜单缓存key布隆过滤器
     */
    String BLOOM_FILTER_ROLE_MENU = "{bloom_filter_role_menu}";

    /**
     * 菜单 redis key
     */
    String MENU = "menu:%s";

    /**
     * 菜单缓存key布隆过滤器
     */
    String BLOOM_FILTER_MENU = "{bloom_filter_menu}";

    /**
     * 部门 redis key
     */
    String DEPT = "dept:%s";

    /**
     * 部门缓存key布隆过滤器
     */
    String BLOOM_FILTER_DEPT = "{bloom_filter_dept}";

}
