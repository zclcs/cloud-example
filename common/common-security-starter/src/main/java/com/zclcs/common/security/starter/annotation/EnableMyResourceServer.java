package com.zclcs.common.security.starter.annotation;

import com.zclcs.common.security.starter.configuration.MyResourceServerAutoConfiguration;
import com.zclcs.common.security.starter.configuration.MyResourceServerConfiguration;
import com.zclcs.common.security.starter.feign.MyFeignClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.lang.annotation.*;

/**
 * @author zclcs
 * <p>
 * 资源服务注解
 */
@Documented
@Inherited
@EnableMethodSecurity
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MyResourceServerAutoConfiguration.class, MyResourceServerConfiguration.class,
        MyFeignClientConfiguration.class})
public @interface EnableMyResourceServer {

}
