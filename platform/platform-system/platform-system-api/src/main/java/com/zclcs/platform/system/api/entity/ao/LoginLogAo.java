package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志 Ao
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
@Schema(name = "LoginLogAo对象", description = "登录日志")
public class LoginLogAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "id")
    private Long id;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "用户名", required = true)
    private String username;

    @NotNull(message = "{required}")
    @Schema(description = "登录时间", required = true)
    private LocalDateTime loginTime;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "登录地点")
    private String location;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "ip地址")
    private String ip;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "操作系统")
    private String system;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "浏览器")
    private String browser;

    @Size(max = 2, message = "{noMoreThan}")
    @Schema(description = "登录类型 01 成功 02 失败 03 登出")
    private String loginType;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(description = "创建人")
    private String createBy;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(description = "编辑人")
    private String updateBy;


}