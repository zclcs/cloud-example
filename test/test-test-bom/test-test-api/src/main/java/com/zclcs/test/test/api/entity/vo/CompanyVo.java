package com.zclcs.test.test.api.entity.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.Array;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 企业信息 Vo
 *
 * @author zclcs
 * @date 2023-04-12 15:15:56.349
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "CompanyVo对象", description = "企业信息")
public class CompanyVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "企业id")
    private Long companyId;

    @Schema(title = "统一社会信用代码或组织机构代码")
    private String companyCode;

    @Schema(title = "营业执照扫描件")
    private String companyAttachment;

    @Schema(title = "企业名称")
    private String companyName;

    @Schema(title = "企业登记注册类型 @@company_type")
    @DictText(value = "company_type")
    private String companyType;

    @Schema(title = "工商营业执照注册号")
    private String licenseNum;

    @Schema(title = "注册地区编码 array @@area_code")
    @DictText(value = "area_code", array = @Array)
    private String areaCode;

    @Schema(title = "企业营业地址")
    private String address;

    @Schema(title = "邮政编码")
    private String zipCode;

    @Schema(title = "法定代表人姓名")
    private String legalMan;

    @Schema(title = "法定代表人电话")
    private String legalManPhone;

    @Schema(title = "法定代表人职务")
    private String legalManDuty;

    @Schema(title = "法定代表人职称")
    private String legalManProTitle;

    @Schema(title = "法定代表人证件类型 @@id_card_type")
    @DictText(value = "id_card_type")
    private String legalManIdCardType;

    @Schema(title = "法定代表人证件号码")
    private String legalManIdCardNumber;

    @Schema(title = "注册资本（单位：分）")
    private String regCapital;

    @Schema(title = "实收资本（单位：分）")
    private String factRegCapital;

    @Schema(title = "资本币种 @@currency_type")
    @DictText(value = "currency_type")
    private String capitalCurrencyType;

    @Schema(title = "注册日期")
    private LocalDate registerDate;

    @Schema(title = "成立日期")
    private LocalDate establishDate;

    @Schema(title = "办公电话")
    private String officePhone;

    @Schema(title = "传真号码")
    private String faxNumber;

    @Schema(title = "联系人姓名")
    private String linkMan;

    @Schema(title = "联系人职务")
    private String linkDuty;

    @Schema(title = "联系人电话")
    private String linkPhone;

    @Schema(title = "企业邮箱")
    private String email;

    @Schema(title = "企业网址")
    private String webSite;

    @Schema(title = "企业备注")
    private String remark;


}