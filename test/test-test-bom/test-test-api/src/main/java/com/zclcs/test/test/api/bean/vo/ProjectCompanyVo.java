package com.zclcs.test.test.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 项目参建单位信息 Vo
 *
 * @author zclcs
 * @date 2023-08-16 14:53:21.059
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectCompanyVo extends BaseEntity implements Serializable {

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
     * 参建类型 @@company_role
     */
    @DictText(value = "company_role")
    private String companyRole;

    /**
     * 项目经理姓名
     */
    private String managerName;

    /**
     * 项目经理证件类型 @@id_card_type
     */
    @DictText(value = "id_card_type")
    private String managerIdCardType;

    /**
     * 项目经理证件号码
     */
    private String managerIdCardNumber;

    /**
     * 项目经理联系电话
     */
    private String managerPhone;


}