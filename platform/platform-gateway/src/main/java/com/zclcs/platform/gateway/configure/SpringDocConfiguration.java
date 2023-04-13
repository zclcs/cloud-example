package com.zclcs.platform.gateway.configure;

import com.github.xiaoymin.knife4j.spring.gateway.Knife4jGatewayAutoConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * @author zclcs
 * <p>
 * swagger 3.0 展示
 */
@Profile({"dev", "test"})
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@Import(Knife4jGatewayAutoConfiguration.class)
public class SpringDocConfiguration {

}
