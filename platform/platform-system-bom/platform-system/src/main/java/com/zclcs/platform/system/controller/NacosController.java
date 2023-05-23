package com.zclcs.platform.system.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.ao.NacosConfigAo;
import com.zclcs.platform.system.api.entity.ao.NacosServiceAo;
import com.zclcs.platform.system.api.entity.vo.NacosConfigVo;
import com.zclcs.platform.system.api.entity.vo.NacosServiceVo;
import com.zclcs.platform.system.api.entity.vo.NacosTokenVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * nacos Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/nacos")
@RequiredArgsConstructor
@Tag(name = "nacos控制台")
public class NacosController {

    private final RedisService redisService;

    private final MyNacosProperties myNacosProperties;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/configs")
    @PreAuthorize("hasAuthority('nacos:view')")
    @Operation(summary = "nacos 配置查询")
    public BaseRsp<BasePage<NacosConfigVo.Config>> findNacosConfigPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated NacosConfigAo nacosConfigAo) {
        String nacosToken = getNacosToken();
        Map<String, Object> params = new HashMap<>();
        params.put("dataId", StrUtil.isNotBlank(nacosConfigAo.getDataId()) ? Strings.ASTERISK + nacosConfigAo.getDataId() + Strings.ASTERISK : "");
        params.put("group", "");
        params.put("appName", "");
        params.put("config_tags", "");
        params.put("pageNo", basePageAo.getPageNum());
        params.put("pageSize", basePageAo.getPageSize());
        params.put("tenant", myNacosProperties.getNamespace());
        params.put("search", "blur");
        params.put("accessToken", nacosToken);
        params.put("username", myNacosProperties.getUsername());
        try (HttpResponse execute = HttpUtil.createGet(getNacosEndPoint("/nacos/v1/cs/configs")).form(params).execute()) {
            String body = execute.body();
            NacosConfigVo nacosConfigVo = objectMapper.readValue(body, NacosConfigVo.class);
            BasePage<NacosConfigVo.Config> nacosConfigVoBasePage = new BasePage<>();
            nacosConfigVoBasePage.setPages(nacosConfigVo.getPagesAvailable());
            nacosConfigVoBasePage.setTotal(nacosConfigVo.getTotalCount());
            nacosConfigVoBasePage.setPageNum(nacosConfigVo.getPageNumber());
            nacosConfigVoBasePage.setList(nacosConfigVo.getPageItems());
            return RspUtil.data(nacosConfigVoBasePage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求nacos config失败");
        }
    }

    @GetMapping("/services")
    @PreAuthorize("hasAuthority('nacos:view')")
    @Operation(summary = "nacos 服务列表查询")
    public BaseRsp<BasePage<NacosServiceVo.Service>> findNacosServicePage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated NacosServiceAo nacosServiceAo) {
        String nacosToken = getNacosToken();
        Map<String, Object> params = new HashMap<>();
        params.put("hasIpCount", "true");
        params.put("withInstances", "false");
        params.put("pageNo", basePageAo.getPageNum());
        params.put("pageSize", basePageAo.getPageSize());
        params.put("serviceNameParam", nacosServiceAo.getServiceNameParam());
        params.put("groupNameParam", "");
        params.put("accessToken", nacosToken);
        params.put("namespaceId", myNacosProperties.getNamespace());
        try (HttpResponse execute = HttpUtil.createGet(getNacosEndPoint("/nacos/v1/ns/catalog/services")).form(params).execute()) {
            String body = execute.body();
            NacosServiceVo nacosServiceVo = objectMapper.readValue(body, NacosServiceVo.class);
            BasePage<NacosServiceVo.Service> nacosServiceBasePage = new BasePage<>();
            nacosServiceBasePage.setTotal(nacosServiceVo.getCount());
            nacosServiceBasePage.setList(nacosServiceVo.getServiceList());
            return RspUtil.data(nacosServiceBasePage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求nacos service失败");
        }
    }

    private String getNacosToken() {
        String token = (String) redisService.get(RedisCachePrefix.NACOS_TOKEN);
        if (token == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("username", myNacosProperties.getUsername());
            params.put("password", myNacosProperties.getPassword());
            try (HttpResponse execute = HttpUtil.createPost(getNacosEndPoint("/nacos/v1/auth/users/login")).form(params).execute()) {
                String body = execute.body();
                NacosTokenVo nacosTokenVo = objectMapper.readValue(body, NacosTokenVo.class);
                redisService.set(RedisCachePrefix.NACOS_TOKEN, nacosTokenVo.getAccessToken(), nacosTokenVo.getTokenTtl() - 30L * 60L);
                return nacosTokenVo.getAccessToken();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MyException("请求nacos token失败");
            }
        }
        return token;
    }

    private String getNacosEndPoint(String resourcePath) {
        return CommonCore.HTTP + myNacosProperties.getServerAddr() + resourcePath;
    }

}