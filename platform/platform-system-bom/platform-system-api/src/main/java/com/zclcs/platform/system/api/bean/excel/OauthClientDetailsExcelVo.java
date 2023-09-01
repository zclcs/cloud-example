package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 终端信息 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:03.427
 */
@Data
public class OauthClientDetailsExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户端ID
     */
    @ExcelProperty(value = "客户端ID")
    private String clientId;

    /**
     * 资源列表
     */
    @ExcelProperty(value = "资源列表")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @ExcelProperty(value = "客户端密钥")
    private String clientSecret;

    /**
     * 域
     */
    @ExcelProperty(value = "域")
    private String scope;

    /**
     * 认证类型
     */
    @ExcelProperty(value = "认证类型")
    private String authorizedGrantTypes;

    /**
     * 重定向地址
     */
    @ExcelProperty(value = "重定向地址")
    private String webServerRedirectUri;

    /**
     * 角色列表
     */
    @ExcelProperty(value = "角色列表")
    private String authorities;

    /**
     * token 有效期
     */
    @ExcelProperty(value = "token 有效期")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    @ExcelProperty(value = "刷新令牌有效期")
    private Integer refreshTokenValidity;

    /**
     * 令牌扩展字段JSON
     */
    @ExcelProperty(value = "令牌扩展字段JSON")
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    @ExcelProperty(value = "是否自动放行")
    private String autoapprove;


}