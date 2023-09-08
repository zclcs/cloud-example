package com.zclcs.test.test.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.annotation.DictExcelValid;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectAreaHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectCityHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectDictHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectProvinceHandler;
import com.zclcs.common.export.excel.starter.annotation.ExcelSelect;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * 企业信息 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-08 15:00:09.827
 */
@Data
public class CompanyExcelVo {

    /**
     * 企业id
     */
    @NotNull(message = "{required}")
    @ExcelProperty(value = "企业id")
    private Long companyId;

    /**
     * 统一社会信用代码或组织机构代码
     */
    @Size(max = 18, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @ExcelProperty(value = "统一社会信用代码或组织机构代码")
    private String companyCode;

    /**
     * 营业执照扫描件
     */
    @Size(max = 64, message = "{noMoreThan}")
    @ExcelProperty(value = "营业执照扫描件")
    private String companyAttachment;

    /**
     * 企业名称
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @ExcelProperty(value = "企业名称")
    private String companyName;

    /**
     * 企业登记注册类型@@company_type
     */
    @DictExcelValid(value = "company_type")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "company_type")
    @ExcelProperty(value = "企业登记注册类型")
    private String companyType;

    public void setCompanyType(String companyType) {
        this.companyType = DictCacheUtil.getDictTitle("company_type", companyType);
    }

    /**
     * 工商营业执照注册号
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "工商营业执照注册号")
    private String licenseNum;

    /**
     * 省
     */
    @ExcelSelect(handler = DynamicSelectProvinceHandler.class, parameter = "area_code")
    @ExcelProperty("省")
    private String province;

    /**
     * 市
     */
    @ExcelSelect(parentColumn = "省", handler = DynamicSelectCityHandler.class, parameter = "area_code")
    @ExcelProperty("市")
    private String city;

    /**
     * 区/县
     */
    @ExcelSelect(parentColumn = "市", handler = DynamicSelectAreaHandler.class, parameter = "area_code")
    @ExcelProperty(value = "区/县")
    private String areaCode;

    public void setAreaCode(String areaCode) {
        DictItemCacheVo area = DictCacheUtil.getDict("area_code", areaCode);
        if (area != null) {
            this.areaCode = area.getTitle();
            DictItemCacheVo city = DictCacheUtil.getDict("area_code", area.getParentValue());
            if (city != null) {
                this.city = city.getTitle();
                DictItemCacheVo province = DictCacheUtil.getDict("area_code", city.getParentValue());
                if (province != null) {
                    this.province = province.getTitle();
                }
            }
        }
    }

    /**
     * 企业营业地址
     */
    @Size(max = 200, message = "{noMoreThan}")
    @ExcelProperty(value = "企业营业地址")
    private String address;

    /**
     * 邮政编码
     */
    @Size(max = 6, message = "{noMoreThan}")
    @ExcelProperty(value = "邮政编码")
    private String zipCode;

    /**
     * 法定代表人姓名
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "法定代表人姓名")
    private String legalMan;

    /**
     * 法定代表人电话
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "法定代表人电话")
    private String legalManPhone;

    /**
     * 法定代表人职务
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "法定代表人职务")
    private String legalManDuty;

    /**
     * 法定代表人职称
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "法定代表人职称")
    private String legalManProTitle;

    /**
     * 法定代表人证件类型@@id_card_type
     */
    @DictExcelValid(value = "id_card_type")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "id_card_type")
    @ExcelProperty(value = "法定代表人证件类型")
    private String legalManIdCardType;

    public void setLegalManIdCardType(String legalManIdCardType) {
        this.legalManIdCardType = DictCacheUtil.getDictTitle("id_card_type", legalManIdCardType);
    }

    /**
     * 法定代表人证件号码
     */
    @Size(max = 30, message = "{noMoreThan}")
    @ExcelProperty(value = "法定代表人证件号码")
    private String legalManIdCardNumber;

    /**
     * 注册资本（单位：分）
     */
    @Size(max = 20, message = "{noMoreThan}")
    @ExcelProperty(value = "注册资本（单位：分）")
    private String regCapital;

    /**
     * 实收资本（单位：分）
     */
    @Size(max = 20, message = "{noMoreThan}")
    @ExcelProperty(value = "实收资本（单位：分）")
    private String factRegCapital;

    /**
     * 资本币种@@currency_type
     */
    @DictExcelValid(value = "currency_type")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "currency_type")
    @ExcelProperty(value = "资本币种")
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
    @Size(max = 20, message = "{noMoreThan}")
    @ExcelProperty(value = "办公电话")
    private String officePhone;

    /**
     * 传真号码
     */
    @Size(max = 20, message = "{noMoreThan}")
    @ExcelProperty(value = "传真号码")
    private String faxNumber;

    /**
     * 联系人姓名
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "联系人姓名")
    private String linkMan;

    /**
     * 联系人职务
     */
    @Size(max = 255, message = "{noMoreThan}")
    @ExcelProperty(value = "联系人职务")
    private String linkDuty;

    /**
     * 联系人电话
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "联系人电话")
    private String linkPhone;

    /**
     * 企业邮箱
     */
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelProperty(value = "企业邮箱")
    private String email;

    /**
     * 企业网址
     */
    @Size(max = 200, message = "{noMoreThan}")
    @ExcelProperty(value = "企业网址")
    private String webSite;

    /**
     * 企业备注
     */
    @Size(max = 200, message = "{noMoreThan}")
    @ExcelProperty(value = "企业备注")
    private String remark;


}