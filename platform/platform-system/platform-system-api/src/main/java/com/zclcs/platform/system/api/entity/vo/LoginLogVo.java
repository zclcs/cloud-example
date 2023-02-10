package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:57.150
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "LoginLogVo对象", description = "登录日志")
public class LoginLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    @Schema(description = "登录地点")
    private String location;

    @Schema(description = "ip地址")
    private String ip;

    @Schema(description = "操作系统")
    private String system;

    @Schema(description = "浏览器")
    private String browser;

    @Schema(description = "登录类型 01 成功 02 失败 03 登出")
    private String loginType;


}