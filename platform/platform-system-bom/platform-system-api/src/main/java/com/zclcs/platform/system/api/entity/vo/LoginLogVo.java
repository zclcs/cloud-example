package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
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
@Schema(title = "LoginLogVo对象", description = "登录日志")
public class LoginLogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    @Schema(description = "登录时间起")
    private LocalDate loginTimeFrom;

    @Schema(description = "登录时间止")
    private LocalDate loginTimeTo;

    @Schema(description = "登录地点")
    private String location;

    @Schema(description = "ip地址")
    private String ip;

    @Schema(description = "操作系统")
    private String system;

    @Schema(description = "浏览器")
    private String browser;

    @Schema(description = "登录类型")
    @DictText(value = "system_login_log.type")
    private String loginType;


}