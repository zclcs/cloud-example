package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色菜单关联 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:21.568
 */
@Data
public class RoleMenuCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;


}