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

/**
 * 黑名单 Entity
 *
 * @author zclcs
 * @since 2023-01-10 10:40:14.628
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_black_list")
public class BlackList extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 黑名单id
     */
    @Id(value = "black_id", keyType = KeyType.Auto)
    private Long blackId;

    /**
     * 黑名单ip
     */
    @Column("black_ip")
    private String blackIp;

    /**
     * 请求uri（支持通配符）
     */
    @Column("request_uri")
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    @Column("request_method")
    private String requestMethod;

    /**
     * 限制时间起
     */
    @Column("limit_from")
    private String limitFrom;

    /**
     * 限制时间止
     */
    @Column("limit_to")
    private String limitTo;

    /**
     * ip对应地址
     */
    @Column("location")
    private String location;

    /**
     * 黑名单状态 默认 1 @@enable_disable
     */
    @Column("black_status")
    private String blackStatus;


}