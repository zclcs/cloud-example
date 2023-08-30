package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
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

/**
 * 用户操作日志 Entity
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
@Table("system_log")
public class Log extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @Id(value = "id", keyType = KeyType.Auto)
    private Long id;

    /**
     * 操作用户
     */
    @Column("username")
    private String username;

    /**
     * 操作内容
     */
    @Column("operation")
    private String operation;

    /**
     * 耗时
     */
    @Column("time")
    private BigDecimal time;

    /**
     * 操作方法
     */
    @Column("method")
    private String method;

    /**
     * 方法参数
     */
    @Column("params")
    private String params;

    /**
     * 操作者ip
     */
    @Column("ip")
    private String ip;

    /**
     * 操作地点
     */
    @Column("location")
    private String location;


}