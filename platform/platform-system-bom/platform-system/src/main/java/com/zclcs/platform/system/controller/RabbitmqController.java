package com.zclcs.platform.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.properties.RabbitmqApiInfoProperties;
import com.zclcs.cloud.lib.core.utils.BaseUtil;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.entity.ao.RabbitmqDetailAo;
import com.zclcs.platform.system.api.entity.ao.RabbitmqExchangeAo;
import com.zclcs.platform.system.api.entity.ao.RabbitmqQueueAo;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/rabbitmq")
@RequiredArgsConstructor
@Tag(name = "rabbitmq控制台")
public class RabbitmqController {

    private final RabbitmqApiInfoProperties rabbitmqApiInfoProperties;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/exchanges")
    @PreAuthorize("hasAuthority('rabbitmq:view')")
    @Operation(summary = "rabbitmq 交换机查询")
    public BaseRsp<BasePage<RabbitmqExchangeVo>> findRabbitmqExchangePage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated RabbitmqExchangeAo rabbitmqExchangeAo) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", Optional.ofNullable(rabbitmqExchangeAo.getName()).orElse(""));
        params.put("page", basePageAo.getPageNum());
        params.put("page_size", basePageAo.getPageSize());
        params.put("use_regex", "false");
        params.put("pagination", "true");
        try (HttpResponse execute = HttpUtil.createGet(getRabbitmqEndpoint("/api/exchanges"))
                .basicAuth(rabbitmqApiInfoProperties.getUsername(), rabbitmqApiInfoProperties.getPassword())
                .form(params).execute()) {
            String body = execute.body();
            TypeReference<RabbitmqBasePageResultVo<RabbitmqExchangeVo>> typeReference = new TypeReference<>() {
            };
            RabbitmqBasePageResultVo<RabbitmqExchangeVo> pageResultVo = objectMapper.readValue(body, typeReference);
            for (RabbitmqExchangeVo item : pageResultVo.getItems()) {
                if (StrUtil.isBlank(item.getName())) {
                    item.setName("(AMQP default)");
                }
            }
            BasePage<RabbitmqExchangeVo> basePage = new BasePage<>();
            basePage.setPages(pageResultVo.getPageCount());
            basePage.setTotal(pageResultVo.getFilteredCount());
            basePage.setPageNum(pageResultVo.getPage());
            basePage.setList(pageResultVo.getItems());
            return RspUtil.data(basePage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求rabbitmq exchange失败");
        }
    }

    @GetMapping("/exchangeDetail")
    @PreAuthorize("hasAuthority('rabbitmq:view')")
    @Operation(summary = "rabbitmq 交换机详情查询")
    public BaseRsp<Map<String, String>> findRabbitmqExchangeDetail(@ParameterObject @Validated RabbitmqDetailAo rabbitmqDetailAo) {
        String url = "/api/exchanges/" + URLEncoder.encode(rabbitmqDetailAo.getVhost(), StandardCharsets.UTF_8) + "/" +
                URLEncoder.encode(rabbitmqDetailAo.getName(), StandardCharsets.UTF_8) + "/bindings/source";
        try (HttpResponse execute = HttpUtil.createGet(getRabbitmqEndpoint(url))
                .basicAuth(rabbitmqApiInfoProperties.getUsername(), rabbitmqApiInfoProperties.getPassword()).execute()) {
            String body = execute.body();
            TypeReference<List<RabbitmqExchangeDetailVo>> typeReference = new TypeReference<>() {
            };
            List<RabbitmqExchangeDetailVo> rabbitmqExchangeDetailVos = objectMapper.readValue(body, typeReference);
            StringBuilder stringBuilder = new StringBuilder();
            for (RabbitmqExchangeDetailVo rabbitmqExchangeDetailVo : rabbitmqExchangeDetailVos) {
                stringBuilder.append("队列名称：").append(rabbitmqExchangeDetailVo.getDestination()).append("\n")
                        .append("路由key：").append(rabbitmqExchangeDetailVo.getRoutingKey()).append("\n");
            }
            Map<String, String> messageStats = MapUtil.builder("exchangeDetail", stringBuilder.toString()).build();
            return RspUtil.data(messageStats);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求rabbitmq exchange detail失败");
        }
    }

    @GetMapping("/delete/exchange")
    @PreAuthorize("hasAuthority('rabbitmq:view')")
    @Operation(summary = "rabbitmq 删除交换机")
    public BaseRsp<String> deleteRabbitmqExchange(@ParameterObject @Validated RabbitmqDetailAo rabbitmqDetailAo) {
        String url = "/api/exchanges/" + URLEncoder.encode(rabbitmqDetailAo.getVhost(), StandardCharsets.UTF_8) + "/" +
                URLEncoder.encode(rabbitmqDetailAo.getName(), StandardCharsets.UTF_8);
        try (HttpResponse execute = HttpUtil.createRequest(Method.DELETE, getRabbitmqEndpoint(url))
                .basicAuth(rabbitmqApiInfoProperties.getUsername(), rabbitmqApiInfoProperties.getPassword()).execute()) {
            String body = execute.body();
            if (execute.getStatus() != HttpStatus.HTTP_OK) {
                throw new MyException("删除rabbitmq exchange失败，result：" + body);
            } else {
                return RspUtil.message("删除成功");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("删除rabbitmq exchange失败，result：" + e.getMessage());
        }
    }

    @GetMapping("/queues")
    @PreAuthorize("hasAuthority('rabbitmq:view')")
    @Operation(summary = "rabbitmq 队列查询")
    public BaseRsp<BasePage<RabbitmqQueueVo>> findRabbitmqQueuePage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated RabbitmqQueueAo rabbitmqQueueAo) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", Optional.ofNullable(rabbitmqQueueAo.getName()).orElse(""));
        params.put("page", basePageAo.getPageNum());
        params.put("page_size", basePageAo.getPageSize());
        params.put("use_regex", "false");
        params.put("pagination", "true");
        try (HttpResponse execute = HttpUtil.createGet(getRabbitmqEndpoint("/api/queues"))
                .basicAuth(rabbitmqApiInfoProperties.getUsername(), rabbitmqApiInfoProperties.getPassword())
                .form(params).execute()) {
            String body = execute.body();
            TypeReference<RabbitmqBasePageResultVo<RabbitmqQueueVo>> typeReference = new TypeReference<>() {
            };
            RabbitmqBasePageResultVo<RabbitmqQueueVo> pageResultVo = objectMapper.readValue(body, typeReference);
            for (RabbitmqQueueVo item : pageResultVo.getItems()) {
                if (CollectionUtil.isNotEmpty(item.getArguments())) {
                    Map<String, String> map = new HashMap<>();
                    for (Map.Entry<String, String> stringStringEntry : item.getArguments().entrySet()) {
                        map.put(BaseUtil.underscoreToCamel(stringStringEntry.getKey().replace(Strings.DASH, Strings.UNDER_LINE)), stringStringEntry.getValue());
                    }
                    item.setArguments(map);
                }
            }
            BasePage<RabbitmqQueueVo> basePage = new BasePage<>();
            basePage.setPages(pageResultVo.getPageCount());
            basePage.setTotal(pageResultVo.getFilteredCount());
            basePage.setPageNum(pageResultVo.getPage());
            basePage.setList(pageResultVo.getItems());
            return RspUtil.data(basePage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求rabbitmq queue失败");
        }
    }

    @GetMapping("/queueDetail")
    @PreAuthorize("hasAuthority('rabbitmq:view')")
    @Operation(summary = "rabbitmq 队列详情查询")
    public BaseRsp<Map<String, String>> findRabbitmqQueueDetail(@ParameterObject @Validated RabbitmqDetailAo rabbitmqDetailAo) {
        String url = "/api/queues/" + URLEncoder.encode(rabbitmqDetailAo.getVhost(), StandardCharsets.UTF_8) + "/" +
                URLEncoder.encode(rabbitmqDetailAo.getName(), StandardCharsets.UTF_8) + "/bindings";
        try (HttpResponse execute = HttpUtil.createGet(getRabbitmqEndpoint(url))
                .basicAuth(rabbitmqApiInfoProperties.getUsername(), rabbitmqApiInfoProperties.getPassword()).execute()) {
            String body = execute.body();
            TypeReference<List<RabbitmqQueueDetailVo>> typeReference = new TypeReference<>() {
            };
            List<RabbitmqQueueDetailVo> rabbitmqExchangeDetailVos = objectMapper.readValue(body, typeReference);
            StringBuilder stringBuilder = new StringBuilder();
            for (RabbitmqQueueDetailVo rabbitmqQueueDetailVo : rabbitmqExchangeDetailVos) {
                if (StrUtil.isNotBlank(rabbitmqQueueDetailVo.getSource())) {
                    stringBuilder.append("交换机名称：").append(rabbitmqQueueDetailVo.getSource()).append("\n")
                            .append("路由key：").append(rabbitmqQueueDetailVo.getRoutingKey()).append("\n");
                }
            }
            Map<String, String> messageStats = MapUtil.builder("queueDetail", stringBuilder.toString()).build();
            return RspUtil.data(messageStats);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求rabbitmq queue detail失败");
        }
    }

    @GetMapping("/delete/queue")
    @PreAuthorize("hasAuthority('rabbitmq:view')")
    @Operation(summary = "rabbitmq 删除队列")
    public BaseRsp<String> deleteRabbitmqQueue(@ParameterObject @Validated RabbitmqDetailAo rabbitmqDetailAo) {
        String url = "/api/queues/" + URLEncoder.encode(rabbitmqDetailAo.getVhost(), StandardCharsets.UTF_8) + "/" +
                URLEncoder.encode(rabbitmqDetailAo.getName(), StandardCharsets.UTF_8);
        Map<String, Object> params = new HashMap<>();
        params.put("vhost", rabbitmqDetailAo.getVhost());
        params.put("name", rabbitmqDetailAo.getName());
        params.put("mode", "delete");
        try (HttpResponse execute = HttpUtil.createRequest(Method.DELETE, getRabbitmqEndpoint(url))
                .body(objectMapper.writeValueAsString(params), ContentType.JSON.getValue())
                .basicAuth(rabbitmqApiInfoProperties.getUsername(), rabbitmqApiInfoProperties.getPassword()).execute()) {
            String body = execute.body();
            if (execute.getStatus() != HttpStatus.HTTP_NO_CONTENT) {
                throw new MyException("删除rabbitmq queue失败，result：" + body);
            } else {
                return RspUtil.message("删除成功");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("删除rabbitmq queue失败，result：" + e.getMessage());
        }
    }

    @GetMapping("/purge/queue")
    @PreAuthorize("hasAuthority('rabbitmq:view')")
    @Operation(summary = "rabbitmq 清空队列")
    public BaseRsp<String> purgeRabbitmqQueue(@ParameterObject @Validated RabbitmqDetailAo rabbitmqDetailAo) {
        String url = "/api/queues/" + URLEncoder.encode(rabbitmqDetailAo.getVhost(), StandardCharsets.UTF_8) + "/" +
                URLEncoder.encode(rabbitmqDetailAo.getName(), StandardCharsets.UTF_8) + "/contents";
        try (HttpResponse execute = HttpUtil.createRequest(Method.DELETE, getRabbitmqEndpoint(url))
                .basicAuth(rabbitmqApiInfoProperties.getUsername(), rabbitmqApiInfoProperties.getPassword()).execute()) {
            String body = execute.body();
            if (execute.getStatus() != HttpStatus.HTTP_NO_CONTENT) {
                throw new MyException("清空rabbitmq queue失败，result：" + body);
            } else {
                return RspUtil.message("清空成功");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("清空rabbitmq queue失败，result：" + e.getMessage());
        }
    }

    private String getRabbitmqEndpoint(String resourcePath) {
        return CommonCore.HTTP + rabbitmqApiInfoProperties.getHost() + Strings.COLON + rabbitmqApiInfoProperties.getApiPort() + resourcePath;
    }

}
