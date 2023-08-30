package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志 Entity
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
@Table("system_login_log")
public class LoginLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id(value = "id", keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户名
     */
    @Column("username")
    private String username;

    /**
     * 登录时间
     */
    @Column("login_time")
    private LocalDateTime loginTime;

    /**
     * 登录地点
     */
    @Column("location")
    private String location;

    /**
     * ip地址
     */
    @Column("ip")
    private String ip;

    /**
     * 操作系统
     */
    @Column("system")
    private String system;

    /**
     * 浏览器
     */
    @Column("browser")
    private String browser;

    /**
     * 登录类型 @@system_login_log.type
     */
    @Column("login_type")
    private String loginType;

}