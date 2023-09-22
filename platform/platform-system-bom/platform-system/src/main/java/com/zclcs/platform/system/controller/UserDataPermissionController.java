package com.zclcs.platform.system.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.service.UserDataPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.UserDataPermissionTableDef.USER_DATA_PERMISSION;

/**
 * 用户角色关联
 *
 * @author zclcs
 * @since 2023-01-10 10:39:28.842
 */
@Slf4j
@RestController
@RequestMapping("/user/data/permission")
@RequiredArgsConstructor
public class UserDataPermissionController {

    private final UserDataPermissionService userDataPermissionService;

    /**
     * 根据用户id查询权限编码id
     * 权限: 仅限内部调用
     *
     * @param userId 用户id
     * @return 权限编码id集合
     */
    @GetMapping("/findByUserId/{userId}")
    @Inner
    public List<Long> findByUserId(@PathVariable Long userId) {
        return userDataPermissionService.listAs(new QueryWrapper().select(USER_DATA_PERMISSION.DEPT_ID).where(USER_DATA_PERMISSION.USER_ID.eq(userId)), Long.class);
    }
}