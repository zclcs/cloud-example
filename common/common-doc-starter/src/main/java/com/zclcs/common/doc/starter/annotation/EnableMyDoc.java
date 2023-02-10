package com.zclcs.common.doc.starter.annotation;

import com.zclcs.common.doc.starter.configure.SwaggerAutoConfiguration;
import com.zclcs.common.doc.starter.properties.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 spring doc
 *
 * @author zclcs
 * @date 2022-03-26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({SwaggerAutoConfiguration.class})
public @interface EnableMyDoc {

}
