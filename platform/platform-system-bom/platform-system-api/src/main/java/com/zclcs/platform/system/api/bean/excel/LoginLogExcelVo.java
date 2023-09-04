package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 20:07:45.093
 */
@Data
public class LoginLogExcelVo {

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 登录时间
     */
    @ExcelProperty(value = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 登录地点
     */
    @ExcelProperty(value = "登录地点")
    private String location;

    /**
     * ip地址
     */
    @ExcelProperty(value = "ip地址")
    private String ip;

    /**
     * 操作系统
     */
    @ExcelProperty(value = "操作系统")
    private String system;

    /**
     * 登录类型 @@system_login_log.type
     */
    @ExcelProperty(value = "登录类型 @@system_login_log.type")
    private String loginType;

    public void setLoginType(String loginType) {
        this.loginType = DictCacheUtil.getDictTitle("system_login_log.type", loginType);
    }

    /**
     * 浏览器
     */
    @ExcelProperty(value = "浏览器")
    private String browser;


}