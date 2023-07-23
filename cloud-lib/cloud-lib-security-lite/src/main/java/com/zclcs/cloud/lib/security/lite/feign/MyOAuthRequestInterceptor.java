package com.zclcs.cloud.lib.security.lite.feign;

import cn.dev33.satoken.same.SaSameUtil;
import cn.hutool.core.collection.CollUtil;
import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.common.web.starter.utils.WebUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * oauth2 feign token传递
 * <p>
 * 重写 OAuth2FeignRequestInterceptor ，官方实现部分常见不适用
 *
 * @author zclcs
 */
@Slf4j
@RequiredArgsConstructor
public class MyOAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Collection<String> fromHeader = requestTemplate.headers().get(Security.FROM);
        // 带from 请求直接跳过
        if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(Security.FROM_IN)) {
            return;
        }

        // 非web 请求直接跳过
        if (WebUtil.getRequest().isEmpty()) {
            return;
        }
        requestTemplate.header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken());
    }

}
