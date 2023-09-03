package com.zclcs.test.test.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 企业信息 Entity
 *
 * @author zclcs
 * @since 2023-09-02 17:12:18.866
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("test_company")
public class Company extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 企业id
     */
    @Id(keyType = KeyType.Auto)
    private Long companyId;

    /**
     * 统一社会信用代码或组织机构代码
     */
    @Column("company_code")
    private String companyCode;

    /**
     * 营业执照扫描件
     */
    @Column("company_attachment")
    private String companyAttachment;

    /**
     * 企业名称
     */
    @Column("company_name")
    private String companyName;

    /**
     * 企业登记注册类型 @@company_type
     */
    @Column("company_type")
    private String companyType;

    /**
     * 工商营业执照注册号
     */
    @Column("license_num")
    private String licenseNum;

    /**
     * 注册地区编码 array @@area_code
     */
    @Column("area_code")
    private String areaCode;

    /**
     * 企业营业地址
     */
    @Column("address")
    private String address;

    /**
     * 邮政编码
     */
    @Column("zip_code")
    private String zipCode;

    /**
     * 法定代表人姓名
     */
    @Column("legal_man")
    private String legalMan;

    /**
     * 法定代表人电话
     */
    @Column("legal_man_phone")
    private String legalManPhone;

    /**
     * 法定代表人职务
     */
    @Column("legal_man_duty")
    private String legalManDuty;

    /**
     * 法定代表人职称
     */
    @Column("legal_man_pro_title")
    private String legalManProTitle;

    /**
     * 法定代表人证件类型 @@id_card_type
     */
    @Column("legal_man_id_card_type")
    private String legalManIdCardType;

    /**
     * 法定代表人证件号码
     */
    @Column("legal_man_id_card_number")
    private String legalManIdCardNumber;

    /**
     * 注册资本（单位：分）
     */
    @Column("reg_capital")
    private String regCapital;

    /**
     * 实收资本（单位：分）
     */
    @Column("fact_reg_capital")
    private String factRegCapital;

    /**
     * 资本币种 @@currency_type
     */
    @Column("capital_currency_type")
    private String capitalCurrencyType;

    /**
     * 注册日期
     */
    @Column("register_date")
    private LocalDate registerDate;

    /**
     * 成立日期
     */
    @Column("establish_date")
    private LocalDate establishDate;

    /**
     * 办公电话
     */
    @Column("office_phone")
    private String officePhone;

    /**
     * 传真号码
     */
    @Column("fax_number")
    private String faxNumber;

    /**
     * 联系人姓名
     */
    @Column("link_man")
    private String linkMan;

    /**
     * 联系人职务
     */
    @Column("link_duty")
    private String linkDuty;

    /**
     * 联系人电话
     */
    @Column("link_phone")
    private String linkPhone;

    /**
     * 企业邮箱
     */
    @Column("email")
    private String email;

    /**
     * 企业网址
     */
    @Column("web_site")
    private String webSite;

    /**
     * 企业备注
     */
    @Column("remark")
    private String remark;


}