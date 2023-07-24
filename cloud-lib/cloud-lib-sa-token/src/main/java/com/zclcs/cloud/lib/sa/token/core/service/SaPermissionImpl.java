package com.zclcs.cloud.lib.sa.token.core.service;

import cn.dev33.satoken.stp.StpInterface;
import com.zclcs.platform.system.utils.SystemCacheUtil;

import java.util.List;

/**
 * sa-token 权限管理实现类
 *
 * @author zclcs
 */
public class SaPermissionImpl implements StpInterface {

    /**
     * 获取菜单权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return SystemCacheUtil.getPermissionsByUsername((String) loginId);
    }

    /**
     * 获取角色权限列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return SystemCacheUtil.getRoleCodesByUsername((String) loginId);
    }

}
