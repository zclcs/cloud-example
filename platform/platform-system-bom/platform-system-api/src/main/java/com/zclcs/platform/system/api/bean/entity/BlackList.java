package com.zclcs.platform.system.api.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 黑名单 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:40:14.628
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_black_list")
public class BlackList extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 黑名单id
     */
    @TableId(value = "black_id", type = IdType.AUTO)
    private Long blackId;

    /**
     * 黑名单ip
     */
    @TableField("black_ip")
    private String blackIp;

    /**
     * 请求uri（支持通配符）
     */
    @TableField("request_uri")
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 限制时间起
     */
    @TableField("limit_from")
    private String limitFrom;

    /**
     * 限制时间止
     */
    @TableField("limit_to")
    private String limitTo;

    /**
     * ip对应地址
     */
    @TableField("location")
    private String location;

    /**
     * 黑名单状态 默认 1 @@enable_disable
     */
    @TableField("black_status")
    private String blackStatus;


}