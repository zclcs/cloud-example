package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 黑名单 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:59.035
 */
@Data
public class BlackListExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 黑名单id
     */
    @ExcelProperty(value = "黑名单id")
    private Long blackId;

    /**
     * 黑名单ip
     */
    @ExcelProperty(value = "黑名单ip")
    private String blackIp;

    /**
     * 请求uri（支持通配符）
     */
    @ExcelProperty(value = "请求uri（支持通配符）")
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
     * ip对应地址
     */
    @ExcelProperty(value = "ip对应地址")
    private String location;

    /**
     * 黑名单状态 默认 1 @@enable_disable
     */
    @ExcelProperty(value = "黑名单状态 默认 1 @@enable_disable")
    private String blackStatus;

    public void setBlackStatus(String blackStatus) {
        this.blackStatus = DictCacheUtil.getDictTitle("enable_disable", blackStatus);
    }


}