package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
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
 * @since 2023-01-10 10:39:57.150
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
     * 主键
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    /**
     * 用户名
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String username;

    /**
     * 登录时间
     */
    @NotNull(message = "{required}")
    private LocalDateTime loginTime;

    /**
     * 登录地点
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String location;

    /**
     * ip地址
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String ip;

    /**
     * 操作系统
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String system;

    /**
     * 浏览器
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String browser;

    /**
     * 登录类型 01 成功 02 失败 03 登出
     */
    @Size(max = 2, message = "{noMoreThan}")
    private String loginType;

    /**
     * 创建人
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String createBy;

    /**
     * 编辑人
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String updateBy;


}