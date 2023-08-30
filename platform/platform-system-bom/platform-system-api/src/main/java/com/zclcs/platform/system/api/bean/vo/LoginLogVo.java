package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
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
 * @since 2023-01-10 10:39:57.150
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
     * 主键
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
     * 登录时间起
     */
    private LocalDate loginTimeFrom;

    /**
     * 登录时间止
     */
    private LocalDate loginTimeTo;

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
     * 浏览器
     */
    private String browser;

    /**
     * 登录类型
     */
    @DictText(value = "system_login_log.type")
    private String loginType;


}