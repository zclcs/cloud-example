package com.zclcs.platform.system.api.fegin;

import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.core.constant.ServiceNameConstant;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.entity.vo.OauthClientDetailsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstant.PLATFORM_SYSTEM_SERVICE)
public interface RemoteClientDetailsService {

    /**
     * 通过clientId 查询客户端信息单个
     *
     * @param clientId 客户端id
     * @return BaseRsp
     */
    @GetMapping(value = "/oauthClientDetails/findByClientId/{clientId}", headers = SecurityConstant.HEADER_FROM_IN)
    OauthClientDetails findByClientId(@PathVariable("clientId") String clientId);

    /**
     * 查询全部客户端
     *
     * @return BaseRsp
     */
    @GetMapping(value = "/oauthClientDetails/list", headers = SecurityConstant.HEADER_FROM_IN)
    BaseRsp<List<OauthClientDetailsVo>> listClientDetails();

}
