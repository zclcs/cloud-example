package com.zclcs.platform.gateway.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.zclcs.cloud.lib.core.exception.ValidateCodeException;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
@Slf4j
public class MyGatewayExceptionHandler implements ErrorWebExceptionHandler {

    /**
     * 存储处理异常后的信息
     */
    private final ThreadLocal<Map<String, Object>> exceptionHandlerResult = new ThreadLocal<>();
    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    @Override
    public @NotNull Mono<Void> handle(ServerWebExchange exchange, Throwable error) {
        /**
         * 按照异常类型进行处理
         */
        HttpStatus httpStatus;
        ServerHttpRequest request = exchange.getRequest();
        log.error(
                "请求发生异常，请求URI：{}，请求方法：{}，异常信息：{}",
                request.getPath(), request.getMethod(), error.getMessage(), error
        );
        String errorMessage;
        if (error instanceof NotFoundException) {
            String serverId = StrUtil.subAfter(error.getMessage(), "Unable to find instance for ", true);
            serverId = StrUtil.replace(serverId, "\"", StrUtil.EMPTY);
            errorMessage = String.format("无法找到%s服务", serverId);
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (StrUtil.containsIgnoreCase(error.getMessage(), "connection refused")) {
            errorMessage = "目标服务拒绝连接";
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (StrUtil.containsIgnoreCase(error.getMessage(), "Response took longer than timeout")) {
            errorMessage = "接口请求超时";
            httpStatus = HttpStatus.GATEWAY_TIMEOUT;
        } else if (error instanceof TimeoutException) {
            errorMessage = "访问服务超时";
            httpStatus = HttpStatus.REQUEST_TIMEOUT;
        } else if (error instanceof ResponseStatusException
                && StrUtil.containsIgnoreCase(error.getMessage(), HttpStatus.NOT_FOUND.toString())) {
            errorMessage = "未找到该资源";
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (error instanceof ValidateCodeException) {
            errorMessage = error.getMessage();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (error instanceof FlowException) {
            errorMessage = "访问频繁";
            httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        } else if (error instanceof DegradeException) {
            errorMessage = "服务降级";
            httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        } else if (error instanceof ParamFlowException) {
            errorMessage = "访问频繁";
            httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        } else if (error instanceof SystemBlockException) {
            errorMessage = "触发系统保护规则";
            httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        } else if (error instanceof AuthorityException) {
            errorMessage = "授权规则不通过";
            httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        } else {
            errorMessage = "网关转发异常";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        // 封装响应体,此body可修改为自己的jsonBody
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("httpStatus", httpStatus);
        result.put("body", RspUtil.message(errorMessage));

        // 参考AbstractErrorWebExceptionHandler
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(error);
        }

        exceptionHandlerResult.set(result);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(error))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));

    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> result = exceptionHandlerResult.get();
        return ServerResponse.status((HttpStatus) result.get("httpStatus"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(result.get("body")));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public @NotNull List<HttpMessageWriter<?>> messageWriters() {
            return MyGatewayExceptionHandler.this.messageWriters;
        }

        @Override
        public @NotNull List<ViewResolver> viewResolvers() {
            return MyGatewayExceptionHandler.this.viewResolvers;
        }

    }
}
