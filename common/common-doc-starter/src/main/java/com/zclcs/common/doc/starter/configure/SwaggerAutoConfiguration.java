package com.zclcs.common.doc.starter.configure;

import com.zclcs.common.doc.starter.properties.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

/**
 * swagger配置
 *
 * @author zclcs
 */
@RequiredArgsConstructor
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
@ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
public class SwaggerAutoConfiguration {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(swaggerProperties.getTitle())
                        .version(swaggerProperties.getVersion())
                        .description(swaggerProperties.getDescription())
                        .termsOfService(swaggerProperties.getTermsOfService())
                        .contact(new Contact().name(swaggerProperties.getConcatName())
                                .email(swaggerProperties.getConcatEmail()))
                        .license(new License().name(swaggerProperties.getLicense())
                                .url(swaggerProperties.getLicenseUrl())))
                .schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme());
    }

    private SecurityScheme securityScheme() {
        OAuthFlow clientCredential = new OAuthFlow();
        clientCredential.setTokenUrl(swaggerProperties.getTokenUrl());
        clientCredential.setScopes(new Scopes().addString(swaggerProperties.getScope(), swaggerProperties.getScope()));
        OAuthFlows oauthFlows = new OAuthFlows();
        oauthFlows.password(clientCredential);
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        securityScheme.setFlows(oauthFlows);
        return securityScheme;
    }

}
