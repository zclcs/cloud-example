package com.zclcs.platform.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.exception.ValidateCodeException;
import com.zclcs.platform.gateway.properties.GatewayConfigProperties;
import com.zclcs.platform.system.api.bean.ao.LoginByUsernameAo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.function.Function;

/**
 * @author zclcs
 */
@Slf4j
@RequiredArgsConstructor
public class PasswordDecoderFilter extends AbstractGatewayFilterFactory<Object> {

    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();
    private static final String KEY_ALGORITHM = "AES";
    private final GatewayConfigProperties gatewayConfig;
    private final ObjectMapper objectMapper;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 1. 检查需不需要解密密码
            if (!gatewayConfig.getIsDecodePassword()) {
                return chain.filter(exchange);
            }

            // 2. 不是登录请求，直接向下执行
            if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), Security.TOKEN_URL)) {
                return chain.filter(exchange);
            }

            // 3. 前端加密密文解密逻辑
            Class inClass = String.class;
            Class outClass = String.class;
            ServerRequest serverRequest = ServerRequest.create(exchange, MESSAGE_READERS);
            // 4. 解密生成新的报文
            Mono<?> modifiedBody = serverRequest.bodyToMono(inClass).flatMap(decryptAES());

            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove(HttpHeaders.CONTENT_LENGTH);

            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
                return chain.filter(exchange.mutate().request(decorator).build());
            }));
        };
    }

    /**
     * 原文解密
     *
     * @return
     */
    private Function decryptAES() {
        return s -> {
            // 构建前端对应解密AES 因子
            AES aes = new AES(Mode.CFB, Padding.NoPadding,
                    new SecretKeySpec(gatewayConfig.getEncodeKey().getBytes(), KEY_ALGORITHM),
                    new IvParameterSpec(gatewayConfig.getEncodeKey().getBytes()));

            // 获取请求密码并解密
            LoginByUsernameAo loginByUsernameAo = null;
            try {
                loginByUsernameAo = objectMapper.readValue((String) s, LoginByUsernameAo.class);
            } catch (JsonProcessingException e) {
                throw new ValidateCodeException("参数解析异常");
            }
            if (loginByUsernameAo != null && StrUtil.isNotBlank(loginByUsernameAo.getPassword())) {
                loginByUsernameAo.setPassword(aes.decryptStr(loginByUsernameAo.getPassword()));
            } else {
                throw new ValidateCodeException("参数解析异常");
            }
            try {
                return Mono.just(objectMapper.writeValueAsString(loginByUsernameAo));
            } catch (JsonProcessingException e) {
                throw new ValidateCodeException("参数解析异常");
            }
        };
    }

    /**
     * 报文转换
     *
     * @return
     */
    private ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                                CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

}
