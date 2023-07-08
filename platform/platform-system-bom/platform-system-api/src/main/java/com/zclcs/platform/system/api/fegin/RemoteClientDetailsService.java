package com.zclcs.platform.system.api.fegin;

import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.constant.ServiceName;
import com.zclcs.platform.system.api.bean.cache.OauthClientDetailsCacheBean;
import com.zclcs.platform.system.api.bean.vo.OauthClientDetailsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceName.PLATFORM_SYSTEM_SERVICE)
public interface RemoteClientDetailsService {

    /**
     * 通过clientId 查询客户端信息单个
     *
     * @param clientId 客户端id
     * @return {@link OauthClientDetailsCacheBean}
     */
    @GetMapping(value = "/oauthClientDetails/findByClientId/{clientId}", headers = Security.HEADER_FROM_IN)
    OauthClientDetailsCacheBean findByClientId(@PathVariable("clientId") String clientId);

    /**
     * 查询全部客户端
     *
     * @return BaseRsp
     */
    @GetMapping(value = "/oauthClientDetails/list", headers = Security.HEADER_FROM_IN)
    BaseRsp<List<OauthClientDetailsVo>> listClientDetails();

}
