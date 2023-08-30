package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
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
 * 黑名单拦截日志 Vo
 *
 * @author zclcs
 * @since 2023-01-10 10:40:05.798
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlockLogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 拦截日志id
     */
    private Long blockId;

    /**
     * 拦截ip
     */
    private String blockIp;

    /**
     * 被拦截请求URI
     */
    private String requestUri;

    /**
     * 被拦截请求方法
     */
    private String requestMethod;

    /**
     * IP对应地址
     */
    private String location;

    /**
     * 拦截时间
     */
    private LocalDateTime requestTime;

    /**
     * 拦截时间起
     */
    private LocalDate requestTimeFrom;

    /**
     * 拦截时间起终
     */
    private LocalDate requestTimeTo;


}