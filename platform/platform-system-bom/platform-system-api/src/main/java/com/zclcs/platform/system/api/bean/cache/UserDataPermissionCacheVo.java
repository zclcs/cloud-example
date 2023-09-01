package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户数据权限关联 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:55:16.457
 */
@Data
public class UserDataPermissionCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;


}