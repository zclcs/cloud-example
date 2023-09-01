package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志 Ao
 *
 * @author zclcs
 * @since 2023-09-01 20:07:45.093
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginLogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    /**
     * 用户名
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String username;

    /**
     * 登录时间
     * 默认值：CURRENT_TIMESTAMP
     */
    @NotNull(message = "{required}")
    private LocalDateTime loginTime;

    /**
     * 登录地点
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String location;

    /**
     * ip地址
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String ip;

    /**
     * 操作系统
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String system;

    /**
     * 登录类型 @@system_login_log.type
     * 默认值：01
     */
    @DictValid(value = "system_login_log.type", message = "{dict}")
    @Size(max = 255, message = "{noMoreThan}")
    private String loginType;

    /**
     * 浏览器
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String browser;


}