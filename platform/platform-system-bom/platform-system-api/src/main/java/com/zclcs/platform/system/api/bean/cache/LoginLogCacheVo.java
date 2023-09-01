package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 登录日志 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 20:07:45.093
 */
@Data
public class LoginLogCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录地点
     */
    private String location;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 操作系统
     */
    private String system;

    /**
     * 登录类型 @@system_login_log.type
     */
    private String loginType;

    /**
     * 登录类型 @@system_login_log.type
     */
    private String loginTypeText;

    public String getLoginTypeText() {
        return DictCacheUtil.getDictTitle("system_login_log.type", this.loginType);
    }

    /**
     * 浏览器
     */
    private String browser;


}