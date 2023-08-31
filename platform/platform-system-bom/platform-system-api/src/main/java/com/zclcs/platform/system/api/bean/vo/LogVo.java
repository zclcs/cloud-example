package com.zclcs.platform.system.api.bean.vo;

import com.mybatisflex.annotation.Column;
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
 * @since 2023-01-10 10:40:01.346
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
     */
    private Long id;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 耗时
     */
    private BigDecimal time;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 方法参数
     */
    private String params;

    /**
     * 操作者ip
     */
    private String ip;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 创建时间-开始
     */
    @Column(ignore = true)
    private LocalDate createAtFrom;

    /**
     * 创建时间-结束
     */
    @Column(ignore = true)
    private LocalDate createAtTo;


}