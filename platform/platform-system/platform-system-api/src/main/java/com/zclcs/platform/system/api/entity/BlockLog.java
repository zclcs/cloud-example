package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.common.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 黑名单拦截日志 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:40:05.798
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_block_log")
@Schema(name = "BlockLog对象", description = "黑名单拦截日志")
public class BlockLog extends BaseEntity {

    /**
     * 拦截日志id
     */
    @TableId(value = "block_id", type = IdType.AUTO)
    private Long blockId;

    /**
     * 拦截ip
     */
    @TableField("block_ip")
    private String blockIp;

    /**
     * 被拦截请求URI
     */
    @TableField("request_uri")
    private String requestUri;

    /**
     * 被拦截请求方法
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * IP对应地址
     */
    @TableField("location")
    private String location;


}