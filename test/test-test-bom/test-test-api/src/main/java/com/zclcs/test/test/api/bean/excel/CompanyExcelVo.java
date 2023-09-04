package com.zclcs.test.test.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 企业信息 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-04 20:04:53.080
 */
@Data
public class CompanyExcelVo {

    /**
     * 企业id
     */
    @ExcelProperty(value = "企业id")
    private Long companyId;

    /**
     * 统一社会信用代码或组织机构代码
     */
    @ExcelProperty(value = "统一社会信用代码或组织机构代码")
    private String companyCode;

    /**
     * 营业执照扫描件
     */
    @ExcelProperty(value = "营业执照扫描件")
    private String companyAttachment;

    /**
     * 企业名称
     */
    @ExcelProperty(value = "企业名称")
    private String companyName;

    /**
     * 企业登记注册类型 @@company_type
     */
    @ExcelProperty(value = "企业登记注册类型 @@company_type")
    private String companyType;

    public void setCompanyType(String companyType) {
        this.companyType = DictCacheUtil.getDictTitle("company_type", companyType);
    }

    /**
     * 工商营业执照注册号
     */
    @ExcelProperty(value = "工商营业执照注册号")
    private String licenseNum;

    /**
     * 注册地区编码 array @@area_code
     */
    @ExcelProperty(value = "注册地区编码 array @@area_code")
    private String areaCode;
    
    public void setAreaCode(String areaCode) {
        this.areaCode = DictCacheUtil.getDictTitleArray("area_code", areaCode);
    }

    /**
     * 企业营业地址
     */
    @ExcelProperty(value = "企业营业地址")
    private String address;

    /**
     * 邮政编码
     */
    @ExcelProperty(value = "邮政编码")
    private String zipCode;

    /**
     * 法定代表人姓名
     */
    @ExcelProperty(value = "法定代表人姓名")
    private String legalMan;

    /**
     * 法定代表人电话
     */
    @ExcelProperty(value = "法定代表人电话")
    private String legalManPhone;

    /**
     * 法定代表人职务
     */
    @ExcelProperty(value = "法定代表人职务")
    private String legalManDuty;

    /**
     * 法定代表人职称
     */
    @ExcelProperty(value = "法定代表人职称")
    private String legalManProTitle;

    /**
     * 法定代表人证件类型 @@id_card_type
     */
    @ExcelProperty(value = "法定代表人证件类型 @@id_card_type")
    private String legalManIdCardType;

    public void setLegalManIdCardType(String legalManIdCardType) {
        this.legalManIdCardType = DictCacheUtil.getDictTitle("id_card_type", legalManIdCardType);
    }

    /**
     * 法定代表人证件号码
     */
    @ExcelProperty(value = "法定代表人证件号码")
    private String legalManIdCardNumber;

    /**
     * 注册资本（单位：分）
     */
    @ExcelProperty(value = "注册资本（单位：分）")
    private String regCapital;

    /**
     * 实收资本（单位：分）
     */
    @ExcelProperty(value = "实收资本（单位：分）")
    private String factRegCapital;

    /**
     * 资本币种 @@currency_type
     */
    @ExcelProperty(value = "资本币种 @@currency_type")
    private String capitalCurrencyType;

    public void setCapitalCurrencyType(String capitalCurrencyType) {
        this.capitalCurrencyType = DictCacheUtil.getDictTitle("currency_type", capitalCurrencyType);
    }

    /**
     * 注册日期
     */
    @ExcelProperty(value = "注册日期")
    private LocalDate registerDate;

    /**
     * 成立日期
     */
    @ExcelProperty(value = "成立日期")
    private LocalDate establishDate;

    /**
     * 办公电话
     */
    @ExcelProperty(value = "办公电话")
    private String officePhone;

    /**
     * 传真号码
     */
    @ExcelProperty(value = "传真号码")
    private String faxNumber;

    /**
     * 联系人姓名
     */
    @ExcelProperty(value = "联系人姓名")
    private String linkMan;

    /**
     * 联系人职务
     */
    @ExcelProperty(value = "联系人职务")
    private String linkDuty;

    /**
     * 联系人电话
     */
    @ExcelProperty(value = "联系人电话")
    private String linkPhone;

    /**
     * 企业邮箱
     */
    @ExcelProperty(value = "企业邮箱")
    private String email;

    /**
     * 企业网址
     */
    @ExcelProperty(value = "企业网址")
    private String webSite;

    /**
     * 企业备注
     */
    @ExcelProperty(value = "企业备注")
    private String remark;


}