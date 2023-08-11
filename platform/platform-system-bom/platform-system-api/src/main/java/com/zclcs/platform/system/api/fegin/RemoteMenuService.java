package com.zclcs.platform.system.api.fegin;

import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.constant.ServiceName;
import com.zclcs.platform.system.api.bean.cache.MenuCacheBean;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteMenuService", value = ServiceName.PLATFORM_SYSTEM_SERVICE)
public interface RemoteMenuService {

    /**
     * 根据菜单id查询菜单
     *
     * @param menuId 菜单id
     * @return {@link MenuCacheBean}
     */
    @GetMapping(value = "/menu/findByMenuId/{menuId}", headers = Security.HEADER_FROM_IN)
    MenuCacheBean findByMenuId(@PathVariable Long menuId);

    /**
     * 根据菜单id集合查询菜单集合
     *
     * @param menuIds 菜单id集合
     * @return 菜单集合
     */
    @GetMapping(value = "/menu/findByMenuIds/{menuIds}", headers = Security.HEADER_FROM_IN)
    Map<Long, MenuCacheBean> findByMenuIds(@PathVariable List<Long> menuIds);

    /**
     * 获取用户权限
     *
     * @param username 用户名称
     * @return 权限
     */
    @GetMapping(value = "/menu/findUserPermissions/{username}", headers = Security.HEADER_FROM_IN)
    List<String> findUserPermissions(@PathVariable String username);

    /**
     * 获取用户路由
     *
     * @param username 用户名称
     * @return 路由
     */
    @GetMapping(value = "/menu/findUserRouters/{username}", headers = Security.HEADER_FROM_IN)
    List<VueRouter<MenuVo>> findUserRouters(@PathVariable String username);

}
