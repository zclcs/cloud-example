package com.zclcs.common.core.constant;

/**
 * 系统常量类
 *
 * @author zclcs
 */
public interface RedisCachePrefixConstant {

    String BLACKLIST_CACHE_KEY_PREFIX = "my:route:blacklist:";

    String RATE_LIMIT_CACHE_KEY_PREFIX = "my:route:rate-limit:";

    String RATE_LIMIT_COUNT_KEY_PREFIX = "my:route:rate-limit:count:";

    /**
     * oauth 缓存前缀
     */
    String PROJECT_OAUTH_ACCESS = "token::access_token";

    /**
     * 验证码 key前缀
     */
    String CODE_PREFIX = "captcha:";

    /**
     * auth_code key前缀
     */
    String AUTH_CODE_PREFIX = "auth_code:";

    /**
     * 缓存 client的 redis key，这里是 hash结构存储
     */
    String CLIENT_DETAILS_PREFIX = "client_details:";

    /**
     * 缓存 用户权限的 redis key
     */
    String USER_PERMISSIONS = "user:permissions:";

    /**
     * 缓存 用户路由的 redis key
     */
    String USER_ROUTES = "user:routes:";

    /**
     * 缓存 用户的 redis key
     */
    String USER = "user:";

    /**
     * 缓存 用户的 redis key
     */
    String USER_MOBILE = "user:mobile:";

    /**
     * 缓存 角色的 redis key
     */
    String ROLE = "role:";

    /**
     * 缓存 菜单的 redis key
     */
    String MENU = "menu:";

}
