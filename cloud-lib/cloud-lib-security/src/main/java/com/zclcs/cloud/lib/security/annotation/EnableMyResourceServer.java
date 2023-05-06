package com.zclcs.cloud.lib.security.annotation;

import com.zclcs.cloud.lib.security.configuration.MyResourceServerAutoConfiguration;
import com.zclcs.cloud.lib.security.configuration.MyResourceServerConfiguration;
import com.zclcs.cloud.lib.security.feign.MyFeignClientConfiguration;
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
