package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.platform.system.api.bean.entity.BlackList;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 黑名单 Entity
 *
 * @author zclcs
 * @since 2023-01-10 10:40:14.628
 */
@Data
public class BlackListCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 黑名单ip
     */
    private String blackIp;

    /**
     * 请求uri（支持通配符）
     */
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    private String requestMethod;

    /**
     * 限制时间起
     */
    private String limitFrom;

    /**
     * 限制时间止
     */
    private String limitTo;

    /**
     * 黑名单状态 默认 1 @@enable_disable
     */
    private String blackStatus;

    public static BlackListCacheVo convertToBlackListCacheBean(BlackList item) {
        if (item == null) {
            return null;
        }
        BlackListCacheVo result = new BlackListCacheVo();
        result.setBlackIp(item.getBlackIp());
        result.setRequestUri(item.getRequestUri());
        result.setRequestMethod(item.getRequestMethod());
        result.setLimitFrom(item.getLimitFrom());
        result.setLimitTo(item.getLimitTo());
        result.setBlackStatus(item.getBlackStatus());
        return result;
    }
}