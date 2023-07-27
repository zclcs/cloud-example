package com.zclcs.test.test.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 企业信息 Ao
 *
 * @author zclcs
 * @date 2023-07-26 22:21:40.994
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CompanyAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 企业id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long companyId;

    /**
     * 统一社会信用代码或组织机构代码
     */
    @Size(max = 18, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String companyCode;

    /**
     * 营业执照扫描件
     */
    @Size(max = 64, message = "{noMoreThan}")
    private String companyAttachment;

    /**
     * 企业名称
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String companyName;

    /**
     * 企业登记注册类型 @@company_type
     */
    @DictValid(value = "company_type", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    private String companyType;

    /**
     * 工商营业执照注册号
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String licenseNum;

    /**
     * 注册地区编码 array @@area_code
     */
    @DictValid(value = "area_code", message = "{dict}")
    @Size(max = 150, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String areaCode;

    /**
     * 企业营业地址
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String address;

    /**
     * 邮政编码
     */
    @Size(max = 6, message = "{noMoreThan}")
    private String zipCode;

    /**
     * 法定代表人姓名
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String legalMan;

    /**
     * 法定代表人电话
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String legalManPhone;

    /**
     * 法定代表人职务
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String legalManDuty;

    /**
     * 法定代表人职称
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String legalManProTitle;

    /**
     * 法定代表人证件类型 @@id_card_type
     */
    @DictValid(value = "id_card_type", message = "{dict}")
    @Size(max = 2, message = "{noMoreThan}")
    private String legalManIdCardType;

    /**
     * 法定代表人证件号码
     */
    @Size(max = 30, message = "{noMoreThan}")
    private String legalManIdCardNumber;

    /**
     * 注册资本（单位：分）
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String regCapital;

    /**
     * 实收资本（单位：分）
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String factRegCapital;

    /**
     * 资本币种 @@currency_type
     */
    @DictValid(value = "currency_type", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String capitalCurrencyType;

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
    @Size(max = 20, message = "{noMoreThan}")
    private String officePhone;

    /**
     * 传真号码
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String faxNumber;

    /**
     * 联系人姓名
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String linkMan;

    /**
     * 联系人职务
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String linkDuty;

    /**
     * 联系人电话
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String linkPhone;

    /**
     * 企业邮箱
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String email;

    /**
     * 企业网址
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String webSite;

    /**
     * 企业备注
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String remark;


}