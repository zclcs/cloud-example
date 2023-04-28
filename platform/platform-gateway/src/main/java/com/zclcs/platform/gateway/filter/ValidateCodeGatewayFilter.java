package com.zclcs.platform.gateway.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.common.core.constant.RedisCachePrefix;
import com.zclcs.common.core.constant.Security;
import com.zclcs.common.core.exception.ValidateCodeException;
import com.zclcs.common.core.utils.RspUtil;
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
import reactor.core.publisher.Mono;

/**
 * The type Validate code gateway filter.
 *
 * @author zclcs
 */
@Slf4j
@RequiredArgsConstructor
public class ValidateCodeGatewayFilter extends AbstractGatewayFilterFactory<Object> {

    private final GatewayConfigProperties configProperties;

    private final ObjectMapper objectMapper;

    private final RedisService redisService;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            boolean isAuthToken = CharSequenceUtil.containsAnyIgnoreCase(request.getURI().getPath(),
                    Security.OAUTH_TOKEN_URL);

            // 不是登录请求，直接向下执行
            if (!isAuthToken) {
                return chain.filter(exchange);
            }

            // 刷新token，手机号登录（也可以这里进行校验） 直接向下执行
            String grantType = request.getQueryParams().getFirst("grant_type");
            if (StrUtil.equals(Security.REFRESH_TOKEN, grantType)) {
                return chain.filter(exchange);
            }

            boolean isIgnoreClient = configProperties.getIgnoreClients().contains(GatewayUtil.getClientId(request));
            try {
                // only oauth and the request not in ignore clients need check code.
                if (!isIgnoreClient) {
                    checkCode(request);
                }
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                final String errMsg = e.getMessage();
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return response.writeWith(Mono.create(monoSink -> {
                    try {
                        byte[] bytes = objectMapper.writeValueAsBytes(RspUtil.message(errMsg));
                        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);

                        monoSink.success(dataBuffer);
                    } catch (JsonProcessingException jsonProcessingException) {
                        log.error("对象输出异常", jsonProcessingException);
                        monoSink.error(jsonProcessingException);
                    }
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
