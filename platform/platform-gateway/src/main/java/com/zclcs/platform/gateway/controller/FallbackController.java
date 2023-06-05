package com.zclcs.platform.gateway.controller;

import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author zclcs
 */
@Slf4j
@RestController
public class FallbackController {

    @RequestMapping("/fallback/{name}")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<BaseRsp<String>> systemFallback(@PathVariable String name) {
        String response = "当前服务器繁忙，请稍后重试";
        log.error("{}，目标微服务：{}", response, name);
        return Mono.just(RspUtil.message(response));
    }

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BaseRsp<String>> ping() {
        return Mono.just(RspUtil.message("pong"));
    }

}
