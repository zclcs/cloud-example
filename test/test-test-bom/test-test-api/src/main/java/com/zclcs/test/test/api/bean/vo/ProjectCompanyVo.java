package com.zclcs.test.test.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 项目参建单位信息数据 Vo
 *
 * @author zclcs
 * @since 2023-09-08 16:48:43.853
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectCompanyVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参建单位id
     * 默认值：
     */
    private Long projectCompanyId;

    /**
     * 项目id
     * 默认值：0
     */
    private Long projectId;

    /**
     * 企业id
     * 默认值：0
     */
    private Long companyId;

    /**
     * 参建类型@@company_role
     * 默认值：
     */
    private String companyRole;

    /**
     * 参建类型@@company_role
     */
    private String companyRoleText;

    public String getCompanyRoleText() {
        return DictCacheUtil.getDictTitle("company_role", this.companyRole);
    }

    /**
     * 项目经理姓名
     * 默认值：
     */
    private String managerName;

    /**
     * 项目经理证件类型@@id_card_type
     * 默认值：
     */
    private String managerIdCardType;

    /**
     * 项目经理证件类型@@id_card_type
     */
    private String managerIdCardTypeText;

    public String getManagerIdCardTypeText() {
        return DictCacheUtil.getDictTitle("id_card_type", this.managerIdCardType);
    }

    /**
     * 项目经理证件号码
     * 默认值：
     */
    private String managerIdCardNumber;

    /**
     * 项目经理联系电话
     * 默认值：
     */
    private String managerPhone;


}