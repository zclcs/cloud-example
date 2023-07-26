package com.zclcs.cloud.lib.aop.ao;

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
 * @date 2023-01-10 10:40:01.346
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
     */
    private Long id;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 操作用户
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String username;

    /**
     * 操作内容
     */
    @Size(max = 65535, message = "{noMoreThan}")
    private String operation;

    /**
     * 耗时
     */
    private BigDecimal time;

    /**
     * 操作方法
     */
    @Size(max = 65535, message = "{noMoreThan}")
    private String method;

    /**
     * 方法参数
     */
    @Size(max = 65535, message = "{noMoreThan}")
    private String params;

    /**
     * 操作者ip
     */
    @Size(max = 64, message = "{noMoreThan}")
    private String ip;

    /**
     * 操作地点
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String location;


}