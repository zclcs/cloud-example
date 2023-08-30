package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.ao.OauthClientDetailsAo;
import com.zclcs.platform.system.api.bean.cache.OauthClientDetailsCacheBean;
import com.zclcs.platform.system.api.bean.entity.OauthClientDetails;
import com.zclcs.platform.system.api.bean.vo.OauthClientDetailsVo;
import com.zclcs.platform.system.service.OauthClientDetailsService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 终端信息
 *
 * @author zclcs
 * @since 2023-01-30 16:48:03.522
 */
@Slf4j
@RestController
@RequestMapping("/oauthClientDetails")
@RequiredArgsConstructor
public class OauthClientDetailsController {

    private final OauthClientDetailsService oauthClientDetailsService;

    /**
     * 终端信息查询（分页）
     * 权限: oauthClientDetails:view
     *
     * @see OauthClientDetailsService#findOauthClientDetailsPage(BasePageAo, OauthClientDetailsVo)
     */
    @GetMapping
    @SaCheckPermission("oauthClientDetails:view")
    public BaseRsp<BasePage<OauthClientDetailsVo>> findOauthClientDetailsPage(@Validated BasePageAo basePageAo, @Validated OauthClientDetailsVo oauthClientDetailsVo) {
        BasePage<OauthClientDetailsVo> page = this.oauthClientDetailsService.findOauthClientDetailsPage(basePageAo, oauthClientDetailsVo);
        return RspUtil.data(page);
    }

    /**
     * 终端信息查询（集合）
     * 权限: oauthClientDetails:view
     *
     * @see OauthClientDetailsService#findOauthClientDetailsList(OauthClientDetailsVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("oauthClientDetails:view")
    public BaseRsp<List<OauthClientDetailsVo>> findOauthClientDetailsList(@Validated OauthClientDetailsVo oauthClientDetailsVo) {
        List<OauthClientDetailsVo> list = this.oauthClientDetailsService.findOauthClientDetailsList(oauthClientDetailsVo);
        return RspUtil.data(list);
    }

    /**
     * 终端信息查询（单个）
     * 权限: oauthClientDetails:view
     *
     * @see OauthClientDetailsService#findOauthClientDetails(OauthClientDetailsVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("oauthClientDetails:view")
    public BaseRsp<OauthClientDetailsVo> findOauthClientDetails(@Validated OauthClientDetailsVo oauthClientDetailsVo) {
        OauthClientDetailsVo oauthClientDetails = this.oauthClientDetailsService.findOauthClientDetails(oauthClientDetailsVo);
        return RspUtil.data(oauthClientDetails);
    }

    /**
     * 终端信息查询
     * 权限: 仅限内部调用
     *
     * @param clientId 终端信息id
     * @return 终端信息
     */
    @GetMapping(value = "/findByClientId/{clientId}")
    @Inner
    public OauthClientDetailsCacheBean findByClientId(@PathVariable String clientId) {
        return OauthClientDetailsCacheBean.convertToOauthClientDetailsCacheBean(oauthClientDetailsService.lambdaQuery().eq(OauthClientDetails::getClientId, clientId).one());
    }

    /**
     * 检查客户端编号
     * 权限: oauthClientDetails:add 或者 oauthClientDetails:update
     *
     * @param clientId 终端信息id
     * @return 是否重复
     */
    @GetMapping("/checkClientId")
    @SaCheckPermission(value = {"oauthClientDetails:add", "oauthClientDetails:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkClientId(@NotBlank(message = "{required}") @RequestParam String clientId) {
        oauthClientDetailsService.validateClientId(clientId);
        return RspUtil.message();
    }

    /**
     * 新增终端信息
     * 权限: oauthClientDetails:add
     *
     * @see OauthClientDetailsService#countOauthClientDetails(OauthClientDetailsVo)
     */
    @PostMapping
    @SaCheckPermission("oauthClientDetails:add")
    @ControllerEndpoint(operation = "新增终端信息")
    public BaseRsp<OauthClientDetails> addOauthClientDetails(@RequestBody @Validated OauthClientDetailsAo oauthClientDetailsAo) {
        return RspUtil.data(this.oauthClientDetailsService.createOauthClientDetails(oauthClientDetailsAo));
    }

    /**
     * 删除终端信息
     * 权限: authClientDetails:delete
     *
     * @see OauthClientDetailsService#deleteOauthClientDetails(List)
     */
    @DeleteMapping("/{oauthClientDetailsIds}")
    @SaCheckPermission("oauthClientDetails:delete")
    @ControllerEndpoint(operation = "删除终端信息")
    public BaseRsp<String> deleteOauthClientDetails(@PathVariable String oauthClientDetailsIds) {
        List<String> ids = Arrays.stream(oauthClientDetailsIds.split(Strings.COMMA)).collect(Collectors.toList());
        this.oauthClientDetailsService.deleteOauthClientDetails(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改终端信息
     * 权限: oauthClientDetails:update
     *
     * @see OauthClientDetailsService#updateOauthClientDetails(OauthClientDetailsAo)
     */
    @PutMapping
    @SaCheckPermission("oauthClientDetails:update")
    @ControllerEndpoint(operation = "修改终端信息")
    public BaseRsp<OauthClientDetails> updateOauthClientDetails(@RequestBody @Validated(UpdateStrategy.class) OauthClientDetailsAo oauthClientDetailsAo) {
        return RspUtil.data(this.oauthClientDetailsService.updateOauthClientDetails(oauthClientDetailsAo));
    }
}