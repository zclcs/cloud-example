package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户操作日志 Ao
 *
 * @author zclcs
 * @since 2023-09-01 19:55:02.695
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long id;

    /**
     * 操作用户
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String username;

    /**
     * 操作内容
     * 默认值：
     */
    @Size(max = 65535, message = "{noMoreThan}")
    private String operation;

    /**
     * 耗时
     * 默认值：0
     */
    private BigDecimal time;

    /**
     * 操作方法
     * 默认值：
     */
    @Size(max = 65535, message = "{noMoreThan}")
    private String method;

    /**
     * 方法参数
     * 默认值：
     */
    @Size(max = 65535, message = "{noMoreThan}")
    private String params;

    /**
     * 操作者ip
     * 默认值：
     */
    @Size(max = 64, message = "{noMoreThan}")
    private String ip;

    /**
     * 操作地点
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String location;


}