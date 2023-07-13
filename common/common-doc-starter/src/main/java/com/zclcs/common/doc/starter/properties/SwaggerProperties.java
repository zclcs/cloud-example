package com.zclcs.common.doc.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerProperties
 *
 * @author zclcs
 */
@Data
@Component
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    /**
     * 是否开启swagger
     */
    private Boolean enabled = true;

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";

    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();

    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();

    /**
     * 需要排除的服务
     */
    private List<String> ignoreProviders = new ArrayList<>();

    /**
     * 标题
     **/
    private String title = "";

    /**
     * 版本
     **/
    private String version = "";

    /**
     * 描述
     **/
    private String description = "";

    /**
     * 服务Url
     **/
    private String termsOfService = "";

    /**
     * 作者
     **/
    private String concatName = "";

    /**
     * 作者邮箱
     **/
    private String concatEmail = "";

    /**
     * 开源协议
     **/
    private String license = "";

    /**
     * 开源协议地址
     **/
    private String licenseUrl = "";

    /**
     * 网关
     */
    private String gatewayEndPoint;

    /**
     * 获取token
     */
    private String tokenUrl;

    /**
     * 作用域
     */
    private String scope;

}
