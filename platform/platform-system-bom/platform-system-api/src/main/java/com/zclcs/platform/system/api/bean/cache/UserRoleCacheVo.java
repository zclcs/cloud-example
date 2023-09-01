package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户角色关联 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:58.035
 */
@Data
public class UserRoleCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;


}