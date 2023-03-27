package com.zclcs.platform.system.api.fegin;

import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.core.constant.ServiceNameConstant;
import com.zclcs.platform.system.api.entity.Menu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteMenuService", value = ServiceNameConstant.PLATFORM_SYSTEM_SERVICE)
public interface RemoteMenuService {

    /**
     * 根据菜单id查询菜单
     *
     * @param menuId 菜单id
     * @return BaseRsp
     */
    @GetMapping(value = "/menu/findByMenuId/{menuId}", headers = SecurityConstant.HEADER_FROM_IN)
    BaseRsp<Menu> findByMenuId(@PathVariable Long menuId);

}
