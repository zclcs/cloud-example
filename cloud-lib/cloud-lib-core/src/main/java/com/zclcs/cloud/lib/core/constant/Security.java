package com.zclcs.cloud.lib.core.constant;

/**
 * @author zclcs
 */
public interface Security {

    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";

    /**
     * 内部
     */
    String FROM_IN = "Y";

    /**
     * 标志
     */
    String FROM = "from";

    /**
     * 请求header
     */
    String HEADER_FROM_IN = FROM + "=" + FROM_IN;

    /**
     * 默认登录URL
     */
    String OAUTH_TOKEN_URL = "/oauth2/token";

    /**
     * 默认登录URL
     */
    String TOKEN_URL = "/login/token";

    /**
     * grant_type
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * 手机号登录
     */
    String APP = "app";

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";

    /**
     * {noop} 加密的特征码
     */
    String NOOP = "{noop}";

    /**
     * 用户状态：有效
     */
    String STATUS_VALID = "1";

    /**
     * 用户状态：锁定
     */
    String STATUS_LOCK = "0";

    /**
     * 用户信息
     */
    String DETAILS_USER = "user_info";

    /**
     * 协议字段
     */
    String DETAILS_LICENSE = "Apache Licence";

    /**
     * 项目的license
     */
    String PROJECT_LICENSE = "https://spdx.org/licenses/Apache-2.0.html";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 客户端ID
     */
    String CLIENT_ID = "clientId";

    /**
     * 短信登录 参数名称
     */
    String SMS_PARAMETER_NAME = "phone";

    /**
     * 授权码模式confirm
     */
    String CUSTOM_CONSENT_PAGE_URI = "/token/confirm_access";

    /**
     * 短信登录 参数名称
     */
    String TOKEN_TYPE = "bearer";
}
