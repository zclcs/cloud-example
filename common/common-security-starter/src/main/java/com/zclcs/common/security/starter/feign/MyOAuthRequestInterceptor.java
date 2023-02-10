package com.zclcs.common.security.starter.feign;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.web.starter.utils.WebUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

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

    private final BearerTokenResolver tokenResolver;

    /**
     * Create a template with the header of provided name and extracted extract </br>
     * <p>
     * 1. 如果使用 非web 请求，header 区别 </br>
     * <p>
     * 2. 根据authentication 还原请求token
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        Collection<String> fromHeader = template.headers().get(SecurityConstant.FROM);
        // 带from 请求直接跳过
        if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(SecurityConstant.FROM_IN)) {
            return;
        }

        // 非web 请求直接跳过
        if (WebUtil.getRequest().isEmpty()) {
            return;
        }
        HttpServletRequest request = WebUtil.getHttpServletRequest();
        // 避免请求参数的 query token 无法传递
        String token = tokenResolver.resolve(request);
        if (StrUtil.isBlank(token)) {
            return;
        }
        template.header(HttpHeaders.AUTHORIZATION,
                String.format("%s %s", OAuth2AccessToken.TokenType.BEARER.getValue(), token));

    }

}
