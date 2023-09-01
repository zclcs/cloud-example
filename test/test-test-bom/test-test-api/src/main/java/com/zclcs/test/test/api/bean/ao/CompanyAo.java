package com.zclcs.test.test.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 企业信息 Ao
 *
 * @author zclcs
 * @since 2023-09-01 16:25:49.782
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
     * 默认值：
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long companyId;

    /**
     * 统一社会信用代码或组织机构代码
     * 默认值：
     */
    @Size(max = 18, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String companyCode;

    /**
     * 营业执照扫描件
     * 默认值：
     */
    @Size(max = 64, message = "{noMoreThan}")
    private String companyAttachment;

    /**
     * 企业名称
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String companyName;

    /**
     * 企业登记注册类型 @@company_type
     * 默认值：
     */
    @DictValid(value = "company_type", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    private String companyType;

    /**
     * 工商营业执照注册号
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String licenseNum;

    /**
     * 注册地区编码 array @@area_code
     * 默认值：
     */
    @DictValid(value = "area_code", isArray = true, message = "{dict}")
    @Size(max = 150, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String areaCode;

    /**
     * 企业营业地址
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String address;

    /**
     * 邮政编码
     * 默认值：
     */
    @Size(max = 6, message = "{noMoreThan}")
    private String zipCode;

    /**
     * 法定代表人姓名
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String legalMan;

    /**
     * 法定代表人电话
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String legalManPhone;

    /**
     * 法定代表人职务
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String legalManDuty;

    /**
     * 法定代表人职称
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String legalManProTitle;

    /**
     * 法定代表人证件类型 @@id_card_type
     * 默认值：
     */
    @DictValid(value = "id_card_type", message = "{dict}")
    @Size(max = 2, message = "{noMoreThan}")
    private String legalManIdCardType;

    /**
     * 法定代表人证件号码
     * 默认值：
     */
    @Size(max = 30, message = "{noMoreThan}")
    private String legalManIdCardNumber;

    /**
     * 注册资本（单位：分）
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String regCapital;

    /**
     * 实收资本（单位：分）
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String factRegCapital;

    /**
     * 资本币种 @@currency_type
     * 默认值：
     */
    @DictValid(value = "currency_type", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String capitalCurrencyType;

    /**
     * 注册日期
     * 默认值：
     */
    private LocalDate registerDate;

    /**
     * 成立日期
     * 默认值：
     */
    private LocalDate establishDate;

    /**
     * 办公电话
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String officePhone;

    /**
     * 传真号码
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String faxNumber;

    /**
     * 联系人姓名
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String linkMan;

    /**
     * 联系人职务
     * 默认值：
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String linkDuty;

    /**
     * 联系人电话
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String linkPhone;

    /**
     * 企业邮箱
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String email;

    /**
     * 企业网址
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String webSite;

    /**
     * 企业备注
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String remark;


}