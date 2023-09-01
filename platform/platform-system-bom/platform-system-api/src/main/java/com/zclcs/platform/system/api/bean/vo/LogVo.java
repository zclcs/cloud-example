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
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 用户操作日志 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:55:02.695
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     * 默认值：
     */
    private Long id;

    /**
     * 操作用户
     * 默认值：
     */
    private String username;

    /**
     * 操作内容
     * 默认值：
     */
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
    private String method;

    /**
     * 方法参数
     * 默认值：
     */
    private String params;

    /**
     * 操作者ip
     * 默认值：
     */
    private String ip;

    /**
     * 操作地点
     * 默认值：
     */
    private String location;

    /**
     * 创建时间-开始
     */
    private LocalDate createAtFrom;

    /**
     * 创建时间-结束
     */
    private LocalDate createAtTo;


}