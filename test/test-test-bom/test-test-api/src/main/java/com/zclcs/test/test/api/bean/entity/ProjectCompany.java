package com.zclcs.test.test.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 项目参建单位信息 Entity
 *
 * @author zclcs
 * @since 2023-08-16 14:53:21.059
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("test_project_company")
public class ProjectCompany extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参建单位id
     */
    @Id(value = "project_company_id", keyType = KeyType.Auto)
    private Long projectCompanyId;

    /**
     * 项目id
     */
    @Column("project_id")
    private Long projectId;

    /**
     * 企业id
     */
    @Column("company_id")
    private Long companyId;

    /**
     * 参建类型 @@company_role
     */
    @Column("company_role")
    private String companyRole;

    /**
     * 项目经理姓名
     */
    @Column("manager_name")
    private String managerName;

    /**
     * 项目经理证件类型 @@id_card_type
     */
    @Column("manager_id_card_type")
    private String managerIdCardType;

    /**
     * 项目经理证件号码
     */
    @Column("manager_id_card_number")
    private String managerIdCardNumber;

    /**
     * 项目经理联系电话
     */
    @Column("manager_phone")
    private String managerPhone;


}