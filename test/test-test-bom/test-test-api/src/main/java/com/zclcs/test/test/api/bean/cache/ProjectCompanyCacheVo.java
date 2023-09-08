package com.zclcs.test.test.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 项目参建单位信息数据 CacheVo
 *
 * @author zclcs
 * @since 2023-09-08 16:48:43.853
 */
@Data
public class ProjectCompanyCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参建单位id
     */
    private Long projectCompanyId;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 企业id
     */
    private Long companyId;

    /**
     * 参建类型@@company_role
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
     */
    private String managerName;

    /**
     * 项目经理证件类型@@id_card_type
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
     */
    private String managerIdCardNumber;

    /**
     * 项目经理联系电话
     */
    private String managerPhone;


}