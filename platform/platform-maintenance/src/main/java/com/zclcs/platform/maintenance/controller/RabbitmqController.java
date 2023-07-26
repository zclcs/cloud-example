package com.zclcs.platform.maintenance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import com.zclcs.platform.maintenance.bean.ao.RabbitmqDetailAo;
import com.zclcs.platform.maintenance.bean.ao.RabbitmqExchangeAo;
import com.zclcs.platform.maintenance.bean.ao.RabbitmqQueueAo;
import com.zclcs.platform.maintenance.bean.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * rabbitmq控制台
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/rabbitmq")
@RequiredArgsConstructor
public class RabbitmqController {

    private final RabbitmqApiInfoProperties rabbitmqApiInfoProperties;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 交换机查询
     * 权限: rabbitmq:view
     *
     * @param basePageAo         {@link BasePageAo}
     * @param rabbitmqExchangeAo {@link RabbitmqExchangeAo}
     * @return {@link RabbitmqExchangeVo}
     */
    @GetMapping("/exchanges")
    @SaCheckPermission("rabbitmq:view")
    public BaseRsp<BasePage<RabbitmqExchangeVo>> findRabbitmqExchangePage(@Validated BasePageAo basePageAo, @Validated RabbitmqExchangeAo rabbitmqExchangeAo) {
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

    /**
     * 交换机详情查询
     * 权限: rabbitmq:view
     *
     * @param rabbitmqDetailAo {@link RabbitmqDetailAo}
     * @return 交换机详情
     */
    @GetMapping("/exchangeDetail")
    @SaCheckPermission("rabbitmq:view")
    public BaseRsp<Map<String, String>> findRabbitmqExchangeDetail(@Validated RabbitmqDetailAo rabbitmqDetailAo) {
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

    /**
     * 删除交换机
     * 权限: rabbitmq:view
     *
     * @param rabbitmqDetailAo {@link RabbitmqDetailAo}
     * @return 是否成功
     */
    @GetMapping("/delete/exchange")
    @SaCheckPermission("rabbitmq:view")
    public BaseRsp<String> deleteRabbitmqExchange(@Validated RabbitmqDetailAo rabbitmqDetailAo) {
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

    /**
     * 队列查询
     * 权限: rabbitmq:view
     *
     * @param basePageAo      {@link BasePageAo}
     * @param rabbitmqQueueAo {@link RabbitmqQueueAo}
     * @return {@link RabbitmqQueueVo}
     */
    @GetMapping("/queues")
    @SaCheckPermission("rabbitmq:view")
    public BaseRsp<BasePage<RabbitmqQueueVo>> findRabbitmqQueuePage(@Validated BasePageAo basePageAo, @Validated RabbitmqQueueAo rabbitmqQueueAo) {
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

    /**
     * 队列详情查询
     * 权限: rabbitmq:view
     *
     * @param rabbitmqDetailAo {@link RabbitmqDetailAo}
     * @return 队列详情
     */
    @GetMapping("/queueDetail")
    @SaCheckPermission("rabbitmq:view")
    public BaseRsp<Map<String, String>> findRabbitmqQueueDetail(@Validated RabbitmqDetailAo rabbitmqDetailAo) {
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

    /**
     * 删除队列
     * 权限: rabbitmq:view
     *
     * @param rabbitmqDetailAo {@link RabbitmqDetailAo}
     * @return 是否成功
     */
    @GetMapping("/delete/queue")
    @SaCheckPermission("rabbitmq:view")
    public BaseRsp<String> deleteRabbitmqQueue(@Validated RabbitmqDetailAo rabbitmqDetailAo) {
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

    /**
     * 清空队列
     * 权限: rabbitmq:view
     *
     * @param rabbitmqDetailAo {@link RabbitmqDetailAo}
     * @return 是否成功
     */
    @GetMapping("/purge/queue")
    @SaCheckPermission("rabbitmq:view")
    public BaseRsp<String> purgeRabbitmqQueue(@Validated RabbitmqDetailAo rabbitmqDetailAo) {
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
