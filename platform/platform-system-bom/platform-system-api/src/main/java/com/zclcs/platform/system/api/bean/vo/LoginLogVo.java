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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 登录日志 Vo
 *
 * @author zclcs
 * @since 2023-09-01 20:07:45.093
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginLogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     * 默认值：
     */
    private Long id;

    /**
     * 用户名
     * 默认值：
     */
    private String username;

    /**
     * 登录时间
     * 默认值：CURRENT_TIMESTAMP
     */
    private LocalDateTime loginTime;

    /**
     * 登录地点
     * 默认值：
     */
    private String location;

    /**
     * ip地址
     * 默认值：
     */
    private String ip;

    /**
     * 操作系统
     * 默认值：
     */
    private String system;

    /**
     * 登录类型 @@system_login_log.type
     * 默认值：01
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
     * 默认值：
     */
    private String browser;

    /**
     * 登录时间起
     */
    private LocalDate loginTimeFrom;

    /**
     * 登录时间止
     */
    private LocalDate loginTimeTo;


}