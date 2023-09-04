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

/**
 * 项目参建单位信息数据 Ao
 *
 * @author zclcs
 * @since 2023-09-04 20:04:43.968
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectCompanyAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参建单位id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long projectCompanyId;

    /**
     * 项目id
     * 默认值：0
     */
    @NotNull(message = "{required}")
    private Long projectId;

    /**
     * 企业id
     * 默认值：0
     */
    @NotNull(message = "{required}")
    private Long companyId;

    /**
     * 参建类型 @@company_role
     * 默认值：
     */
    @DictValid(value = "company_role", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String companyRole;

    /**
     * 项目经理姓名
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String managerName;

    /**
     * 项目经理证件类型 @@id_card_type
     * 默认值：
     */
    @DictValid(value = "id_card_type", message = "{dict}")
    @Size(max = 2, message = "{noMoreThan}")
    private String managerIdCardType;

    /**
     * 项目经理证件号码
     * 默认值：
     */
    @Size(max = 30, message = "{noMoreThan}")
    private String managerIdCardNumber;

    /**
     * 项目经理联系电话
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String managerPhone;


}