package com.zclcs.common.doc.starter.configure;

import cn.hutool.core.util.RandomUtil;
import com.zclcs.common.doc.starter.properties.MyDocProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zclcs
 */
@AutoConfiguration
@EnableConfigurationProperties(MyDocProperties.class)
@ConditionalOnProperty(value = "my.doc.enable", havingValue = "true", matchIfMissing = true)
public class MyDocAutoConfigure {

    private final MyDocProperties properties;

    public MyDocAutoConfigure(MyDocProperties properties) {
        this.properties = properties;
    }

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags() != null) {
                openApi.getTags().forEach(tag -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0, 100));
                    tag.setExtensions(map);
                });
            }
            if (openApi.getPaths() != null) {
                openApi.addExtension("x-test123", "333");
                openApi.getPaths().addExtension("x-abb", RandomUtil.randomInt(1, 100));
            }

        };
    }

    @Bean
    @Order(-1)
    public OpenAPI groupRestApi() {
        String description = String.format("<div style='font-size:%spx;color:%s;'>%s</div>",
                properties.getDescriptionFontSize(), properties.getDescriptionColor(), properties.getDescription());
        return new OpenAPI()
                .info(new Info()
                        .title(properties.getTitle())
                        .version(properties.getVersion())

                        .description(description)
                        .termsOfService(properties.getTermsOfServiceUrl())
                        .license(new License().name(properties.getLicense())
                                .url(properties.getLicenseUrl())));
    }


}
