package com.zclcs.test.test.api.entity.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * @date 2023-04-12 15:15:56.349
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "CompanyAo对象", description = "企业信息")
public class CompanyAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(title = "企业id")
    private Long companyId;

    @Size(max = 18, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(title = "统一社会信用代码或组织机构代码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String companyCode;

    @Size(max = 64, message = "{noMoreThan}")
    @Schema(title = "营业执照扫描件")
    private String companyAttachment;

    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(title = "企业名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String companyName;

    @DictValid(value = "company_type", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    @Schema(title = "企业登记注册类型 @@company_type")
    private String companyType;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(title = "工商营业执照注册号")
    private String licenseNum;

    @DictValid(value = "area_code", message = "{dict}")
    @Size(max = 150, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(title = "注册地区编码 array @@area_code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String areaCode;

    @Size(max = 200, message = "{noMoreThan}")
    @Schema(title = "企业营业地址")
    private String address;

    @Size(max = 6, message = "{noMoreThan}")
    @Schema(title = "邮政编码")
    private String zipCode;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(title = "法定代表人姓名")
    private String legalMan;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(title = "法定代表人电话")
    private String legalManPhone;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(title = "法定代表人职务")
    private String legalManDuty;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(title = "法定代表人职称")
    private String legalManProTitle;

    @DictValid(value = "id_card_type", message = "{dict}")
    @Size(max = 2, message = "{noMoreThan}")
    @Schema(title = "法定代表人证件类型 @@id_card_type")
    private String legalManIdCardType;

    @Size(max = 30, message = "{noMoreThan}")
    @Schema(title = "法定代表人证件号码")
    private String legalManIdCardNumber;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(title = "注册资本（单位：分）")
    private String regCapital;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(title = "实收资本（单位：分）")
    private String factRegCapital;

    @DictValid(value = "currency_type", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    @Schema(title = "资本币种 @@currency_type")
    private String capitalCurrencyType;

    @Schema(title = "注册日期")
    private LocalDate registerDate;

    @Schema(title = "成立日期")
    private LocalDate establishDate;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(title = "办公电话")
    private String officePhone;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(title = "传真号码")
    private String faxNumber;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(title = "联系人姓名")
    private String linkMan;

    @Size(max = 255, message = "{noMoreThan}")
    @Schema(title = "联系人职务")
    private String linkDuty;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(title = "联系人电话")
    private String linkPhone;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(title = "企业邮箱")
    private String email;

    @Size(max = 200, message = "{noMoreThan}")
    @Schema(title = "企业网址")
    private String webSite;

    @Size(max = 200, message = "{noMoreThan}")
    @Schema(title = "企业备注")
    private String remark;


}