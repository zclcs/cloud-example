package com.zclcs.platform.gateway.handler;

import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.exception.ValidateCodeException;
import com.zclcs.cloud.lib.core.utils.RspUtil;
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
     *
     * @param viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable error) {
        /**
         * 按照异常类型进行处理
         */
        HttpStatus httpStatus = null;
        ServerHttpRequest request = exchange.getRequest();
        log.error(
                "请求发生异常，请求URI：{}，请求方法：{}，异常信息：{}",
                request.getPath(), request.getMethod(), error.getMessage()
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
        }
//        else if (error instanceof FlowException) {
//            errorMessage = "接口限流";
//        } else if (error instanceof DegradeException) {
//            errorMessage = "服务降级";
//        } else if (error instanceof ParamFlowException) {
//            errorMessage = "热点参数限流";
//        } else if (error instanceof SystemBlockException) {
//            errorMessage = "触发系统保护规则";
//        } else if (error instanceof AuthorityException) {
//            errorMessage = "授权规则不通过";
//        }
        else {
            errorMessage = "网关转发异常";
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
     *
     * @param request
     * @return
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> result = exceptionHandlerResult.get();
        return ServerResponse.status((HttpStatus) result.get("httpStatus"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(result.get("body")));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param exchange
     * @param response
     * @return
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
        public List<HttpMessageWriter<?>> messageWriters() {
            return MyGatewayExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return MyGatewayExceptionHandler.this.viewResolvers;
        }

    }

//    /**
//     * 异常处理，定义返回报文格式
//     */
//    @Override
//    protected Map<String, Object> getErrorAttributes(ServerRequest webRequest, ErrorAttributeOptions options) {
//        Throwable error = super.getError(webRequest);
//        log.error(
//                "请求发生异常，请求URI：{}，请求方法：{}，异常信息：{}",
//                webRequest.path(), webRequest.method(), error.getMessage()
//        );
//        String errorMessage;
//        if (error instanceof NotFoundException) {
//            String serverId = StrUtil.subAfter(error.getMessage(), "Unable to find instance for ", true);
//            serverId = StrUtil.replace(serverId, "\"", StrUtil.EMPTY);
//            errorMessage = String.format("无法找到%s服务", serverId);
//        } else if (StrUtil.containsIgnoreCase(error.getMessage(), "connection refused")) {
//            errorMessage = "目标服务拒绝连接";
//        } else if (error instanceof TimeoutException) {
//            errorMessage = "访问服务超时";
//        } else if (error instanceof ResponseStatusException
//                && StrUtil.containsIgnoreCase(error.getMessage(), HttpStatus.NOT_FOUND.toString())) {
//            errorMessage = "未找到该资源";
//        } else if (error instanceof ValidateCodeException) {
//            errorMessage = error.getMessage();
//        }
////        else if (error instanceof FlowException) {
////            errorMessage = "接口限流";
////        } else if (error instanceof DegradeException) {
////            errorMessage = "服务降级";
////        } else if (error instanceof ParamFlowException) {
////            errorMessage = "热点参数限流";
////        } else if (error instanceof SystemBlockException) {
////            errorMessage = "触发系统保护规则";
////        } else if (error instanceof AuthorityException) {
////            errorMessage = "授权规则不通过";
////        }
//        else {
//            errorMessage = "网关转发异常";
//        }
//        Map<String, Object> errorAttributes = new HashMap<>(3);
//        errorAttributes.put("msg", errorMessage);
//        return errorAttributes;
//    }
//
//    @Override
//    @SuppressWarnings("all")
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
//    }
//
//    @Override
//    protected int getHttpStatus(Map<String, Object> errorAttributes) {
//        return HttpStatus.INTERNAL_SERVER_ERROR.value();
//    }
}
