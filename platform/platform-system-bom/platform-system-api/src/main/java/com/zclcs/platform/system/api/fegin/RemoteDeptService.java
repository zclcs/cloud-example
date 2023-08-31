package com.zclcs.platform.system.api.fegin;

import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.constant.ServiceName;
import com.zclcs.platform.system.api.bean.cache.DeptCacheVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteDeptService", value = ServiceName.PLATFORM_SYSTEM_SERVICE)
public interface RemoteDeptService {

    /**
     * 通过部门id查询部门
     *
     * @param deptId 部门id
     * @return {@link DeptCacheVo}
     */
    @GetMapping(value = "/dept/findByDeptId/{deptId}", headers = Security.HEADER_FROM_IN)
    DeptCacheVo findByDeptId(@PathVariable("deptId") Long deptId);

}
