package com.zclcs.platform.system.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.common.xxl.job.starter.properties.MyXxlJobProperties;
import com.zclcs.platform.system.api.entity.ao.XxlJobJobInfoAo;
import com.zclcs.platform.system.api.entity.ao.XxlJobJobLogAo;
import com.zclcs.platform.system.api.entity.ao.XxlJobJobLogDetailAo;
import com.zclcs.platform.system.api.entity.vo.*;
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

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * nacos Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/xxl/job")
@RequiredArgsConstructor
@Tag(name = "nacos控制台")
public class XxlJobController {

    private final RedisService redisService;

    private final MyXxlJobProperties myXxlJobProperties;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/jobInfos")
    @PreAuthorize("hasAuthority('jobInfo:view')")
    @Operation(summary = "xxlJob 任务管理")
    public BaseRsp<BasePage<XxlJobJobInfoVo>> findXxlJobJobInfoPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated XxlJobJobInfoAo xxlJobJobInfoAo) {
        Map<String, Object> params = new HashMap<>();
        params.put("jobGroup", 0);
        params.put("triggerStatus", Optional.ofNullable(xxlJobJobInfoAo.getTriggerStatus()).orElse(-1));
        params.put("jobDesc", xxlJobJobInfoAo.getJobDesc());
        params.put("executorHandler", xxlJobJobInfoAo.getExecutorHandler());
        params.put("author", xxlJobJobInfoAo.getAuthor());
        params.put("start", basePageAo.getPageNum() - 1);
        params.put("length", basePageAo.getPageSize());
        try (HttpResponse execute = HttpUtil.createGet(getXxlJobEndPoint("/jobinfo/pageList")).cookie(getHttpCookie()).form(params).execute()) {
            String body = execute.body();
            TypeReference<XxlJobBasePageResultVo<XxlJobJobInfoVo>> typeReference = new TypeReference<>() {
            };
            XxlJobBasePageResultVo<XxlJobJobInfoVo> xxlJobJobInfoVo = objectMapper.readValue(body, typeReference);
            BasePage<XxlJobJobInfoVo> xxlJobInfoBasePage = new BasePage<>();
            xxlJobInfoBasePage.setTotal(xxlJobJobInfoVo.getRecordsTotal());
            xxlJobInfoBasePage.setList(xxlJobJobInfoVo.getData());
            return RspUtil.data(xxlJobInfoBasePage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求xxl job jobInfo失败");
        }
    }

    @GetMapping("/jobLogs")
    @PreAuthorize("hasAuthority('jobLog:view')")
    @Operation(summary = "xxlJob 日志管理")
    public BaseRsp<BasePage<XxlJobJobLogVo>> findXxlJobJobLogPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated XxlJobJobLogAo xxlJobJobLogAo) {
        Map<String, Object> params = new HashMap<>();
        params.put("jobGroup", Optional.ofNullable(xxlJobJobLogAo.getJobGroup()).orElse(0));
        params.put("jobId", Optional.ofNullable(xxlJobJobLogAo.getJobId()).orElse(0));
        params.put("logStatus", Optional.ofNullable(xxlJobJobLogAo.getLogStatus()).orElse(-1));
        DateTime date = DateUtil.date();
        params.put("filterTime", Optional.ofNullable(xxlJobJobLogAo.getFilterTimeFrom()).filter(StrUtil::isNotBlank)
                .orElse(DateUtil.beginOfDay(date).toString(DatePattern.NORM_DATETIME_FORMAT))
                + " - " +
                Optional.ofNullable(xxlJobJobLogAo.getFilterTimeTo()).filter(StrUtil::isNotBlank)
                        .orElse(DateUtil.endOfDay(date).toString(DatePattern.NORM_DATETIME_FORMAT)));
        params.put("start", basePageAo.getPageNum() - 1);
        params.put("length", basePageAo.getPageSize());
        try (HttpResponse execute = HttpUtil.createGet(getXxlJobEndPoint("/joblog/pageList")).cookie(getHttpCookie()).form(params).execute()) {
            String body = execute.body();
            TypeReference<XxlJobBasePageResultVo<XxlJobJobLogVo>> typeReference = new TypeReference<>() {
            };
            XxlJobBasePageResultVo<XxlJobJobLogVo> xxlJobJobLogVo = objectMapper.readValue(body, typeReference);
            for (XxlJobJobLogVo datum : xxlJobJobLogVo.getData()) {
                Optional.ofNullable(datum.getHandleTime()).filter(StrUtil::isNotBlank).ifPresent(s ->
                        datum.setHandleTime(DateUtil.parse(s, DatePattern.UTC_MS_WITH_XXX_OFFSET_PATTERN)
                                .toString(DatePattern.NORM_DATETIME_PATTERN)));
                Optional.ofNullable(datum.getTriggerTime()).filter(StrUtil::isNotBlank).ifPresent(s ->
                        datum.setTriggerTime(DateUtil.parse(s, DatePattern.UTC_MS_WITH_XXX_OFFSET_PATTERN)
                                .toString(DatePattern.NORM_DATETIME_PATTERN)));
                datum.setTriggerMsg(datum.getTriggerMsg()
                        .replace("<br>", "\n")
                        .replace("<span style=\"color:#00c0ef;\" >", "")
                        .replace("</span>", ""));
            }
            BasePage<XxlJobJobLogVo> xxlJobLogBasePage = new BasePage<>();
            xxlJobLogBasePage.setTotal(xxlJobJobLogVo.getRecordsTotal());
            xxlJobLogBasePage.setList(xxlJobJobLogVo.getData());
            return RspUtil.data(xxlJobLogBasePage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求xxl job jobLog失败");
        }
    }

    @GetMapping("/jobLogDetail")
    @PreAuthorize("hasAuthority('jobLog:view')")
    @Operation(summary = "xxlJob 查询日志")
    public BaseRsp<XxlJobJobLogDetailVo> findJobLogDetail(@ParameterObject @Validated XxlJobJobLogDetailAo xxlJobJobLogDetailAo) {
        Map<String, Object> params = new HashMap<>();
        params.put("logId", xxlJobJobLogDetailAo.getLogId());
        params.put("fromLineNum", 1);
        try (HttpResponse execute = HttpUtil.createGet(getXxlJobEndPoint("/joblog/logDetailCat")).cookie(getHttpCookie()).form(params).execute()) {
            String body = execute.body();
            TypeReference<XxlJobBaseResultVo<XxlJobJobLogDetailVo>> typeReference = new TypeReference<>() {
            };
            XxlJobBaseResultVo<XxlJobJobLogDetailVo> xxlJobBaseResultVo = objectMapper.readValue(body, typeReference);
            if (xxlJobBaseResultVo.success()) {
                xxlJobBaseResultVo.getContent().setLogContent(xxlJobBaseResultVo.getContent().getLogContent()
                        .replace("<br>", "\n"));
                return RspUtil.data(xxlJobBaseResultVo.getContent());
            } else {
                return RspUtil.data(XxlJobJobLogDetailVo.builder().logContent("").build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求xxl job jobLogDetail失败");
        }
    }

    private HttpCookie getHttpCookie() {
        return new HttpCookie("XXL_JOB_LOGIN_IDENTITY", getXxlJobCookie());
    }

    private String getXxlJobCookie() {
        String cookie = (String) redisService.get(RedisCachePrefix.XXL_JOB_COOKIE);
        if (cookie == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("userName", myXxlJobProperties.getAdminUsername());
            params.put("password", myXxlJobProperties.getAdminPassword());
            params.put("ifRemember", "on");
            try (HttpResponse execute = HttpUtil.createPost(getXxlJobEndPoint("/login")).form(params).execute()) {
                String body = execute.body();
                TypeReference<XxlJobBaseResultVo<Object>> typeReference = new TypeReference<>() {
                };
                XxlJobBaseResultVo<Object> xxlJobBaseResultVo = objectMapper.readValue(body, typeReference);
                if (xxlJobBaseResultVo.success()) {
                    HttpCookie xxlJobLoginIdentity = execute.getCookie("XXL_JOB_LOGIN_IDENTITY");
                    redisService.set(RedisCachePrefix.XXL_JOB_COOKIE, xxlJobLoginIdentity.getValue());
                    return xxlJobLoginIdentity.getValue();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MyException("请求xxl-job cookie失败");
            }
        }
        return cookie;
    }

    private String getXxlJobEndPoint(String resourcePath) {
        return myXxlJobProperties.getAdminAddresses() + resourcePath;
    }

}
