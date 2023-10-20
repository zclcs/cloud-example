package com.zclcs.platform.maintenance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.maintenance.bean.ao.PowerJobQueryInstanceAo;
import com.zclcs.platform.maintenance.bean.ao.PowerJobQueryJobInfoAo;
import com.zclcs.platform.maintenance.bean.ao.PowerJobSaveJobInfoAo;
import com.zclcs.platform.maintenance.bean.vo.*;
import com.zclcs.platform.maintenance.properties.PlatformMaintenanceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * powerJob 控制台
 *
 * @author zclcs
 * @since 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/power/job")
@RequiredArgsConstructor
public class PowerJobController {

    private final RedisService redisService;

    private PlatformMaintenanceProperties platformMaintenanceProperties;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setPlatformMaintenanceProperties(PlatformMaintenanceProperties platformMaintenanceProperties) {
        this.platformMaintenanceProperties = platformMaintenanceProperties;
    }

    /**
     * 应用信息
     * 权限: powerJob:view
     *
     * @param appName 应用名称
     * @return {@link PowerJobAppInfoVo}
     */
    @GetMapping("/app/info")
    @SaCheckPermission("powerJob:view")
    public BaseRsp<List<PowerJobAppInfoVo>> findAppInfoList(@RequestParam(required = false) String appName) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("condition", appName);
        try (HttpResponse execute = HttpUtil.createGet(getPowerJobEndPoint("/appInfo/list")).form(params).execute()) {
            String body = execute.body();
            TypeReference<PowerJobResultVo<List<PowerJobAppInfoVo>>> typeReference = new TypeReference<>() {
            };
            PowerJobResultVo<List<PowerJobAppInfoVo>> resultVo = objectMapper.readValue(body, typeReference);
            return RspUtil.data(resultVo.getData());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求power job失败");
        }
    }

    /**
     * 任务信息
     * 权限: powerJob:view
     *
     * @param basePageAo             {@link PowerJobAppInfoVo}
     * @param powerJobQueryJobInfoAo {@link PowerJobQueryJobInfoAo}
     * @return {@link PowerJobAppInfoVo}
     */
    @GetMapping("/job/info")
    @SaCheckPermission("powerJob:view")
    public BaseRsp<BasePage<PowerJobJobInfoVo>> findJobInfoPage(@Validated BasePageAo basePageAo, @Validated PowerJobQueryJobInfoAo powerJobQueryJobInfoAo) {
        Map<String, Object> params = new HashMap<>(5);
        params.put("index", basePageAo.getPageNum() - 1L);
        params.put("pageSize", basePageAo.getPageSize());
        params.put("appId", powerJobQueryJobInfoAo.getAppId());
        params.put("jobId", powerJobQueryJobInfoAo.getJobId());
        params.put("keyword", powerJobQueryJobInfoAo.getKeyword());
        try (HttpResponse execute = HttpUtil.createPost(getPowerJobEndPoint("/job/list")).body(objectMapper.writeValueAsString(params)).execute()) {
            String body = execute.body();
            TypeReference<PowerJobResultVo<PowerJobPageResultVo<PowerJobJobInfoVo>>> typeReference = new TypeReference<>() {
            };
            PowerJobResultVo<PowerJobPageResultVo<PowerJobJobInfoVo>> resultVo = objectMapper.readValue(body, typeReference);
            BasePage<PowerJobJobInfoVo> basePage = new BasePage<>();
            PowerJobPageResultVo<PowerJobJobInfoVo> data = resultVo.getData();
            basePage.setTotal(data.getTotalItems());
            basePage.setList(data.getData());
            return RspUtil.data(basePage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求power job失败");
        }
    }

    /**
     * 任务实例信息
     * 权限: powerJob:view
     *
     * @param basePageAo              {@link PowerJobAppInfoVo}
     * @param powerJobQueryInstanceAo {@link PowerJobQueryInstanceAo}
     * @return {@link PowerJobAppInfoVo}
     */
    @GetMapping("/instance")
    @SaCheckPermission("powerJob:view")
    public BaseRsp<BasePage<PowerJobInstanceInfoVo>> findInstancePage(@Validated BasePageAo basePageAo, @Validated PowerJobQueryInstanceAo powerJobQueryInstanceAo) {
        if ("-1".equals(powerJobQueryInstanceAo.getStatus())) {
            powerJobQueryInstanceAo.setStatus("");
        }
        Map<String, Object> params = new HashMap<>(8);
        params.put("index", basePageAo.getPageNum() - 1L);
        params.put("pageSize", basePageAo.getPageSize());
        params.put("appId", powerJobQueryInstanceAo.getAppId());
        params.put("type", "NORMAL");
        params.put("instanceId", powerJobQueryInstanceAo.getInstanceId());
        params.put("jobId", powerJobQueryInstanceAo.getJobId());
        params.put("wfInstanceId", powerJobQueryInstanceAo.getWfInstanceId());
        params.put("status", powerJobQueryInstanceAo.getStatus());
        try (HttpResponse execute = HttpUtil.createPost(getPowerJobEndPoint("/instance/list")).body(objectMapper.writeValueAsString(params)).execute()) {
            String body = execute.body();
            TypeReference<PowerJobResultVo<PowerJobPageResultVo<PowerJobInstanceInfoVo>>> typeReference = new TypeReference<>() {
            };
            PowerJobResultVo<PowerJobPageResultVo<PowerJobInstanceInfoVo>> resultVo = objectMapper.readValue(body, typeReference);
            BasePage<PowerJobInstanceInfoVo> basePage = new BasePage<>();
            PowerJobPageResultVo<PowerJobInstanceInfoVo> data = resultVo.getData();
            basePage.setTotal(data.getTotalItems());
            basePage.setList(data.getData());
            return RspUtil.data(basePage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求power job失败");
        }
    }

    /**
     * 任务实例日志
     * 权限: powerJob:view
     *
     * @param appId      应用id
     * @param instanceId 任务实例id
     * @param index      日志下标
     * @return {@link PowerJobAppInfoVo}
     */
    @GetMapping("/log")
    @SaCheckPermission("powerJob:view")
    public BaseRsp<PowerJobStringPageVo> findLogPage(@RequestParam Long appId, @RequestParam Long instanceId, @RequestParam(required = false) Long index) {
        Map<String, Object> params = new HashMap<>(5);
        params.put("appId", appId);
        params.put("instanceId", instanceId);
        params.put("index", Optional.ofNullable(index).orElse(0L));
        try (HttpResponse execute = HttpUtil.createGet(getPowerJobEndPoint("/instance/log")).form(params).execute()) {
            String body = execute.body();
            TypeReference<PowerJobResultVo<PowerJobStringPageVo>> typeReference = new TypeReference<>() {
            };
            PowerJobResultVo<PowerJobStringPageVo> resultVo = objectMapper.readValue(body, typeReference);
            return RspUtil.data(resultVo.getData());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求power job失败");
        }
    }

    /**
     * 保存或修改任务
     * 权限: powerJob:view
     *
     * @param powerJobSaveJobInfoAo {@link PowerJobSaveJobInfoAo}
     * @return {@link PowerJobAppInfoVo}
     */
    @PostMapping("/job/info/saveOrUpdate")
    @SaCheckPermission("powerJob:view")
    public BaseRsp<String> saveOrUpdateJobInfo(@RequestBody @Validated PowerJobSaveJobInfoAo powerJobSaveJobInfoAo) {
        try (HttpResponse execute = HttpUtil.createPost(getPowerJobEndPoint("/job/save")).body(objectMapper.writeValueAsString(powerJobSaveJobInfoAo)).execute()) {
            int status = execute.getStatus();
            return RspUtil.message();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求power job失败");
        }
    }

    /**
     * 运行任务
     * 权限: powerJob:view
     *
     * @param appId          应用id
     * @param jobId          任务id
     * @param instanceParams 运行参数
     * @return {@link PowerJobAppInfoVo}
     */
    @GetMapping("/job/info/run")
    @SaCheckPermission("powerJob:view")
    public BaseRsp<Long> jobRun(@RequestParam String appId, @RequestParam String jobId, @RequestParam(required = false) String instanceParams) {
        Map<String, Object> params = new HashMap<>(5);
        params.put("appId", appId);
        params.put("jobId", jobId);
        params.put("instanceParams", instanceParams);
        try (HttpResponse execute = HttpUtil.createGet(getPowerJobEndPoint("/job/run")).form(params).execute()) {
            String body = execute.body();
            TypeReference<PowerJobResultVo<Long>> typeReference = new TypeReference<>() {
            };
            PowerJobResultVo<Long> resultVo = objectMapper.readValue(body, typeReference);
            return RspUtil.data(resultVo.getData());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求power job失败");
        }
    }

    private String getPowerJobEndPoint(String resourcePath) {
        return platformMaintenanceProperties.getPowerJobHost() + Strings.COLON + platformMaintenanceProperties.getPowerJobPort() + resourcePath;
    }

}
