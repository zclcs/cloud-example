package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户数据权限关联 Ao
 *
 * @author zclcs
 * @since 2023-09-01 19:55:16.457
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDataPermissionAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long userId;

    /**
     * 部门id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long deptId;


}