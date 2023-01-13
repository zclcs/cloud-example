package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserVo对象", description = "用户")
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "联系电话")
    private String mobile;

    @Schema(description = "状态 0锁定 1有效")
    private String status;

    @Schema(description = "最近访问时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "性别 0男 1女 2保密")
    private String gender;

    @Schema(description = "是否开启tab，0关闭 1开启")
    private String isTab;

    @Schema(description = "主题")
    private String theme;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "描述")
    private String description;


}