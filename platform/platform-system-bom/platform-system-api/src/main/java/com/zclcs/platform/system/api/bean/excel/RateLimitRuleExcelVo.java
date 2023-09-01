package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 限流规则 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:43.828
 */
@Data
public class RateLimitRuleExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流规则id
     */
    @ExcelProperty(value = "限流规则id")
    private Long rateLimitRuleId;

    /**
     * 请求uri（不支持通配符）
     */
    @ExcelProperty(value = "请求uri（不支持通配符）")
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    @ExcelProperty(value = "请求方法，如果为ALL则表示对所有方法生效")
    private String requestMethod;

    /**
     * 限制时间起
     */
    @ExcelProperty(value = "限制时间起")
    private String limitFrom;

    /**
     * 限制时间止
     */
    @ExcelProperty(value = "限制时间止")
    private String limitTo;

    /**
     * 限制次数
     */
    @ExcelProperty(value = "限制次数")
    private Integer rateLimitCount;

    /**
     * 时间周期（单位秒）
     */
    @ExcelProperty(value = "时间周期（单位秒）")
    private String intervalSec;

    /**
     * 规则状态 默认 1 @@enable_disable
     */
    @ExcelProperty(value = "规则状态 默认 1 @@enable_disable")
    private String ruleStatus;

    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = DictCacheUtil.getDictTitle("enable_disable", ruleStatus);
    }


}