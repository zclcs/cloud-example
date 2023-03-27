package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.security.starter.annotation.Inner;
import com.zclcs.platform.system.api.entity.UserRole;
import com.zclcs.platform.system.biz.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
@Slf4j
@RestController
@RequestMapping("/user/role")
@RequiredArgsConstructor
@Tag(name = "用户角色关联")
public class UserRoleController {

    private final UserRoleService userRoleService;


    @GetMapping("/findByUserId/{userId}")
    @Operation(summary = "根据用户id查询角色id")
    @Inner
    public BaseRsp<List<Long>> findByUserId(@PathVariable Long userId) {
        List<Long> ids = userRoleService.lambdaQuery().eq(UserRole::getUserId, userId).list().stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return RspUtil.data(ids);
    }
}