package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户数据权限关联 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserDataPermissionVo对象", description = "用户数据权限关联")
public class UserDataPermissionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "部门编号")
    private Long deptId;

    @Schema(description = "部门名称")
    private String deptName;


}