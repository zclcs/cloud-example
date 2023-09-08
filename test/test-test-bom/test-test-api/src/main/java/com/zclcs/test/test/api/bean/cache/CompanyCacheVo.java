package com.zclcs.test.test.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 企业信息 CacheVo
 *
 * @author zclcs
 * @since 2023-09-08 16:49:03.555
 */
@Data
public class CompanyCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 企业id
     */
    private Long companyId;

    /**
     * 统一社会信用代码或组织机构代码
     */
    private String companyCode;

    /**
     * 营业执照扫描件
     */
    private String companyAttachment;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业登记注册类型@@company_type
     */
    private String companyType;

    /**
     * 企业登记注册类型@@company_type
     */
    private String companyTypeText;

    public String getCompanyTypeText() {
        return DictCacheUtil.getDictTitle("company_type", this.companyType);
    }

    /**
     * 工商营业执照注册号
     */
    private String licenseNum;

    /**
     * 注册地区编码@@area_code@@tree
     */
    private String areaCode;

    /**
     * 注册地区编码@@area_code@@tree
     */
    private String areaCodeText;

    public String getAreaCodeText() {
        return DictCacheUtil.getDictTitle("area_code", this.areaCode);
    }

    /**
     * 企业营业地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 法定代表人姓名
     */
    private String legalMan;

    /**
     * 法定代表人电话
     */
    private String legalManPhone;

    /**
     * 法定代表人职务
     */
    private String legalManDuty;

    /**
     * 法定代表人职称
     */
    private String legalManProTitle;

    /**
     * 法定代表人证件类型@@id_card_type
     */
    private String legalManIdCardType;

    /**
     * 法定代表人证件类型@@id_card_type
     */
    private String legalManIdCardTypeText;

    public String getLegalManIdCardTypeText() {
        return DictCacheUtil.getDictTitle("id_card_type", this.legalManIdCardType);
    }

    /**
     * 法定代表人证件号码
     */
    private String legalManIdCardNumber;

    /**
     * 注册资本（单位：分）
     */
    private String regCapital;

    /**
     * 实收资本（单位：分）
     */
    private String factRegCapital;

    /**
     * 资本币种@@currency_type
     */
    private String capitalCurrencyType;

    /**
     * 资本币种@@currency_type
     */
    private String capitalCurrencyTypeText;

    public String getCapitalCurrencyTypeText() {
        return DictCacheUtil.getDictTitle("currency_type", this.capitalCurrencyType);
    }

    /**
     * 注册日期
     */
    private LocalDate registerDate;

    /**
     * 成立日期
     */
    private LocalDate establishDate;

    /**
     * 办公电话
     */
    private String officePhone;

    /**
     * 传真号码
     */
    private String faxNumber;

    /**
     * 联系人姓名
     */
    private String linkMan;

    /**
     * 联系人职务
     */
    private String linkDuty;

    /**
     * 联系人电话
     */
    private String linkPhone;

    /**
     * 企业邮箱
     */
    private String email;

    /**
     * 企业网址
     */
    private String webSite;

    /**
     * 企业备注
     */
    private String remark;


}