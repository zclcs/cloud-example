package com.zclcs.platform.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.exception.ValidateCodeException;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.properties.GatewayConfigProperties;
import com.zclcs.platform.gateway.utils.GatewayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * The type Validate code gateway filter.
 *
 * @author zclcs
 */
@Slf4j
@RequiredArgsConstructor
public class ValidateCodeGatewayFilter extends AbstractGatewayFilterFactory<Object> {

    private final GatewayConfigProperties gatewayConfigProperties;

    private final RedisService redisService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!gatewayConfigProperties.getIsCheckValidCode()) {
                return chain.filter(exchange);
            }

            URI originUri = GatewayUtil.getGatewayOriginalRequestUrl(exchange);

            if (CollectionUtil.isNotEmpty(gatewayConfigProperties.getNeedCheckValidCodeUrls()) && originUri != null) {
                for (String needCheckValidCodeUrl : gatewayConfigProperties.getNeedCheckValidCodeUrls()) {
                    // 不是需要验证的请求，直接向下执行
                    if (!pathMatcher.match(needCheckValidCodeUrl, originUri.getPath())) {
                        return chain.filter(exchange);
                    }
                }
            }

            try {
                checkCode(request);
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                final String errMsg = e.getMessage();
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return response.writeWith(Mono.create(monoSink -> {
                    byte[] bytes = JsonUtil.toJsonAsBytes(RspUtil.message(errMsg));
                    DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
                    monoSink.success(dataBuffer);
                }));
            }

            return chain.filter(exchange);
        };
    }

    private void checkCode(ServerHttpRequest request) {
        String code = request.getQueryParams().getFirst("code");

        if (StrUtil.isBlank(code)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        String randomStr = request.getQueryParams().getFirst("randomStr");

        if (StrUtil.isBlank(randomStr)) {
            randomStr = request.getQueryParams().getFirst(Security.SMS_PARAMETER_NAME);
        }

        String key = RedisCachePrefix.CODE_PREFIX + randomStr;

        Object codeObj = redisService.get(key);

        if (ObjectUtil.isEmpty(codeObj)) {
            throw new ValidateCodeException("验证码已过期");
        }

        if (!code.equals(codeObj)) {
            throw new ValidateCodeException("验证码不合法");
        }

        redisService.del(key);
    }

}
