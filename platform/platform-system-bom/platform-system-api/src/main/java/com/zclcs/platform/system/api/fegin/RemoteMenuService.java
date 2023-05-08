package com.zclcs.platform.system.api.fegin;

import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.constant.ServiceName;
import com.zclcs.platform.system.api.entity.Menu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteMenuService", value = ServiceName.PLATFORM_SYSTEM_SERVICE)
public interface RemoteMenuService {

    /**
     * 根据菜单id查询菜单
     *
     * @param menuId 菜单id
     * @return {@link Menu}
     */
    @GetMapping(value = "/menu/findByMenuId/{menuId}", headers = Security.HEADER_FROM_IN)
    Menu findByMenuId(@PathVariable Long menuId);

}
