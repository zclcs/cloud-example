package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 黑名单 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:59.035
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlackListVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 黑名单id
     * 默认值：
     */
    private Long blackId;

    /**
     * 黑名单ip
     * 默认值：
     */
    private String blackIp;

    /**
     * 请求uri（支持通配符）
     * 默认值：
     */
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     * 默认值：
     */
    private String requestMethod;

    /**
     * 限制时间起
     * 默认值：00:00:00
     */
    private String limitFrom;

    /**
     * 限制时间止
     * 默认值：23:59:59
     */
    private String limitTo;

    /**
     * ip对应地址
     * 默认值：
     */
    private String location;

    /**
     * 黑名单状态 默认 1 @@enable_disable
     * 默认值：1
     */
    private String blackStatus;

    /**
     * 黑名单状态 默认 1 @@enable_disable
     */
    private String blackStatusText;

    public String getBlackStatusText() {
        return DictCacheUtil.getDictTitle("enable_disable", this.blackStatus);
    }
    
    /**
     * 黑名单id集合
     */
    private List<Long> blackIds;

}