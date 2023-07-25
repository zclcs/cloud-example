package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.BlackListAo;
import com.zclcs.platform.system.api.bean.entity.BlackList;
import com.zclcs.platform.system.api.bean.vo.BlackListVo;
import com.zclcs.platform.system.service.BlackListService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 黑名单
 *
 * @author zclcs
 * @date 2023-01-10 10:40:14.628
 */
@Slf4j
@RestController
@RequestMapping("/blackList")
@RequiredArgsConstructor
public class BlackListController {

    private final BlackListService blackListService;

    /**
     * 黑名单查询（分页）
     * 权限: blackList:view
     *
     * @see BlackListService#findBlackListPage(BasePageAo, BlackListVo)
     */
    @GetMapping
    @SaCheckPermission("blackList:view")
    public BaseRsp<BasePage<BlackListVo>> findBlackListPage(@Validated BasePageAo basePageAo, @Validated BlackListVo blackListVo) {
        BasePage<BlackListVo> page = this.blackListService.findBlackListPage(basePageAo, blackListVo);
        return RspUtil.data(page);
    }

    /**
     * 黑名单查询（集合）
     * 权限: blackList:view
     *
     * @see BlackListService#findBlackListList(BlackListVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("blackList:view")
    public BaseRsp<List<BlackListVo>> findBlackListList(@Validated BlackListVo blackListVo) {
        List<BlackListVo> list = this.blackListService.findBlackListList(blackListVo);
        return RspUtil.data(list);
    }

    /**
     * 黑名单查询（单个）
     * 权限: blackList:view
     *
     * @see BlackListService#findBlackList(BlackListVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("blackList:view")
    public BaseRsp<BlackListVo> findBlackList(@Validated BlackListVo blackListVo) {
        BlackListVo blackList = this.blackListService.findBlackList(blackListVo);
        return RspUtil.data(blackList);
    }

    /**
     * 新增黑名单
     * 权限: blackList:add
     *
     * @see BlackListService#createBlackList(BlackListAo)
     */
    @PostMapping
    @SaCheckPermission("blackList:add")
    @ControllerEndpoint(operation = "新增黑名单")
    public BaseRsp<BlackList> addBlackList(@RequestBody @Validated BlackListAo blackListAo) {
        return RspUtil.data(this.blackListService.createBlackList(blackListAo));
    }

    /**
     * 删除黑名单
     * 权限: blackList:delete
     *
     * @param blackListIds 黑名单id集合(,分隔)
     * @see BlackListService#deleteBlackList(List)
     */
    @DeleteMapping("/{blackListIds}")
    @SaCheckPermission("blackList:delete")
    @ControllerEndpoint(operation = "删除黑名单")
    public BaseRsp<String> deleteBlackList(@NotBlank(message = "{required}") @PathVariable String blackListIds) {
        List<Long> ids = Arrays.stream(blackListIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.blackListService.deleteBlackList(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改黑名单
     * 权限: blackList:update
     *
     * @see BlackListService#updateBlackList(BlackListAo)
     */
    @PutMapping
    @SaCheckPermission("blackList:update")
    @ControllerEndpoint(operation = "修改黑名单")
    public BaseRsp<BlackList> updateBlackList(@RequestBody @Validated(UpdateStrategy.class) BlackListAo blackListAo) {
        return RspUtil.data(this.blackListService.updateBlackList(blackListAo));
    }
}