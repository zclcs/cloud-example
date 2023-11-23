package com.zclcs.platform.maintenance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.bean.CacheKey;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.maintenance.bean.ao.NacosConfigAo;
import com.zclcs.platform.maintenance.bean.ao.NacosServiceAo;
import com.zclcs.platform.maintenance.bean.vo.NacosConfigVo;
import com.zclcs.platform.maintenance.bean.vo.NacosServiceVo;
import com.zclcs.platform.maintenance.bean.vo.NacosTokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * nacos控制台
 *
 * @author zclcs
 * @since 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/nacos")
@RequiredArgsConstructor
public class NacosController {

    private final RedisService redisService;

    private final MyNacosProperties myNacosProperties;

    /**
     * 配置查询
     * 权限: nacos:view
     *
     * @param basePageAo    {@link BasePageAo}
     * @param nacosConfigAo {@link NacosConfigAo}
     * @return {@link NacosConfigVo.ConfigVo}
     */
    @GetMapping("/configs")
    @SaCheckPermission("nacos:view")
    public BaseRsp<BasePage<NacosConfigVo.ConfigVo>> findNacosConfigPage(@Validated BasePageAo basePageAo, @Validated NacosConfigAo nacosConfigAo) {
        String nacosToken = getNacosToken();
        Map<String, Object> params = new HashMap<>(10);
        params.put("dataId", StrUtil.isNotBlank(nacosConfigAo.getDataId()) ? Strings.ASTERISK + nacosConfigAo.getDataId() + Strings.ASTERISK : "");
        params.put("group", myNacosProperties.getGroup());
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
            NacosConfigVo nacosConfigVo = JsonUtil.readValue(body, NacosConfigVo.class);
            Objects.requireNonNull(nacosConfigVo);
            BasePage<NacosConfigVo.ConfigVo> nacosConfigVoBasePage = new BasePage<>();
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

    /**
     * 配置详情查询
     * 权限: nacos:view
     *
     * @param nacosConfigAo {@link NacosConfigAo}
     * @return {@link NacosConfigVo.ConfigDetailVo}
     */
    @GetMapping("/config/detail")
    @SaCheckPermission("nacos:view")
    public BaseRsp<NacosConfigVo.ConfigDetailVo> findNacosConfigDetail(@Validated NacosConfigAo nacosConfigAo) {
        String nacosToken = getNacosToken();
        Map<String, Object> params = new HashMap<>(10);
        params.put("dataId", nacosConfigAo.getDataId());
        params.put("group", myNacosProperties.getGroup());
        params.put("namespaceId", myNacosProperties.getNamespace());
        params.put("tenant", myNacosProperties.getNamespace());
        params.put("show", "all");
        params.put("accessToken", nacosToken);
        params.put("username", myNacosProperties.getUsername());
        try (HttpResponse execute = HttpUtil.createGet(getNacosEndPoint("/nacos/v1/cs/configs")).form(params).execute()) {
            String body = execute.body();
            return RspUtil.data(JsonUtil.readValue(body, NacosConfigVo.ConfigDetailVo.class));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求nacos config detail失败");
        }
    }

    /**
     * 编辑
     *
     * @param configDetailVo {@link NacosConfigVo.ConfigDetailVo}
     * @return 是否成功
     */
    @PutMapping("/config")
    @SaCheckPermission("nacos:view")
    public BaseRsp<String> findNacosConfigDetail(@RequestBody @Validated({ValidGroups.Crud.Update.class}) NacosConfigVo.ConfigDetailVo configDetailVo) {
        String nacosToken = getNacosToken();
        Map<String, Object> params = BeanUtil.beanToMap(configDetailVo, false, false);
        params.put("modifyTime", System.currentTimeMillis());
        try (HttpResponse execute = HttpUtil.createPost(getNacosEndPoint(String.format("/nacos/v1/cs/configs?accessToken=%s&username=%s", nacosToken, myNacosProperties.getUsername()))).form(params).execute()) {
            String body = execute.body();
            if (body.equals(CommonCore.BOOLEAN_TRUE)) {
                return RspUtil.message("编辑成功");
            } else {
                throw new MyException(body);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("编辑 nacos 配置失败，result：" + e.getMessage());
        }
    }

    /**
     * 服务列表查询
     *
     * @param basePageAo     {@link BasePageAo}
     * @param nacosServiceAo {@link NacosServiceAo}
     * @return {@link NacosServiceVo.Service}
     */
    @GetMapping("/services")
    @SaCheckPermission("nacos:view")
    public BaseRsp<BasePage<NacosServiceVo.Service>> findNacosServicePage(@Validated BasePageAo basePageAo, @Validated NacosServiceAo nacosServiceAo) {
        String nacosToken = getNacosToken();
        Map<String, Object> params = new HashMap<>(10);
        params.put("hasIpCount", "true");
        params.put("withInstances", "false");
        params.put("pageNo", basePageAo.getPageNum());
        params.put("pageSize", basePageAo.getPageSize());
        params.put("serviceNameParam", nacosServiceAo.getServiceNameParam());
        params.put("groupNameParam", myNacosProperties.getGroup());
        params.put("accessToken", nacosToken);
        params.put("namespaceId", myNacosProperties.getNamespace());
        try (HttpResponse execute = HttpUtil.createGet(getNacosEndPoint("/nacos/v1/ns/catalog/services")).form(params).execute()) {
            String body = execute.body();
            NacosServiceVo nacosServiceVo = JsonUtil.readValue(body, NacosServiceVo.class);
            Objects.requireNonNull(nacosServiceVo);
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
        String token = (String) redisService.get(RedisCachePrefix.NACOS_TOKEN_PREFIX);
        if (token == null) {
            Map<String, Object> params = new HashMap<>(2);
            params.put("username", myNacosProperties.getUsername());
            params.put("password", myNacosProperties.getPassword());
            try (HttpResponse execute = HttpUtil.createPost(getNacosEndPoint("/nacos/v1/auth/users/login")).form(params).execute()) {
                String body = execute.body();
                NacosTokenVo nacosTokenVo = JsonUtil.readValue(body, NacosTokenVo.class);
                Objects.requireNonNull(nacosTokenVo);
                redisService.set(new CacheKey(RedisCachePrefix.NACOS_TOKEN_PREFIX, Duration.ofSeconds(nacosTokenVo.getTokenTtl() - 30L * 60L)), nacosTokenVo.getAccessToken());
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
