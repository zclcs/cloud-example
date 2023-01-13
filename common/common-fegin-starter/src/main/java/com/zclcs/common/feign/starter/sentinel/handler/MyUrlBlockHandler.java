package com.zclcs.common.feign.starter.sentinel.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.web.starter.utils.BaseWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * sentinel统一降级限流策略
 * <p>
 * {@link com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler}
 *
 * @author lengleng
 * @date 2020-06-11
 */
@Slf4j
@RequiredArgsConstructor
public class MyUrlBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.error("sentinel 降级 资源名称{}", e.getRule().getResource(), e);
        BaseWebUtil.makeResponse(response, MediaType.APPLICATION_JSON.getType(), HttpStatus.TOO_MANY_REQUESTS.value(), BaseRspUtil.message(e.getMessage()));
    }

}
