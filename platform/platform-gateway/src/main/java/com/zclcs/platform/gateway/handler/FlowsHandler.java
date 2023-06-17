package com.zclcs.platform.gateway.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.exception.MyException;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author zclcs
 */
@Slf4j
public class FlowsHandler {

    private ObjectMapper objectMapper;

    public FlowsHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 新增限流
     *
     * @param rules 限流定义
     */
    private void add(Set<GatewayFlowRule> rules) {
        try {
            GatewayRuleManager.loadRules(rules);
            for (GatewayFlowRule gatewayFlowRule : rules) {
                log.debug("网关限流配置已刷新 {}", objectMapper.writeValueAsBytes(gatewayFlowRule));
            }
        } catch (Exception exception) {
            log.error("add flow has error", exception);
        }
    }

    /**
     * 更新所有限流信息
     *
     * @param configStr 配置字符串
     */
    public void refreshAll(String configStr) {
        // 无效字符串不处理
        if (!StrUtil.hasLetter(configStr)) {
            log.error("invalid string for flow");
            return;
        }
        // 用Jackson反序列化
        Set<GatewayFlowRule> gatewayFlowRules = null;
        try {
            TypeReference<Set<GatewayFlowRule>> valueTypeRef = new TypeReference<>() {
            };
            gatewayFlowRules = objectMapper.readValue(configStr, valueTypeRef);
            for (GatewayFlowRule gatewayFlowRule : gatewayFlowRules) {
                boolean validRule = GatewayRuleManager.isValidRule(gatewayFlowRule);
                if (!validRule) {
                    throw new MyException("valid flow rule error");
                }
                log.debug("获取到的网关限流配置 {}", objectMapper.writeValueAsBytes(gatewayFlowRule));
            }
        } catch (Exception e) {
            log.error("get flow from nacos string error", e);
        }
        // 如果等于null，表示反序列化失败，立即返回
        if (null == gatewayFlowRules) {
            return;
        }
        // 添加最新路由
        add(gatewayFlowRules);
    }
}
