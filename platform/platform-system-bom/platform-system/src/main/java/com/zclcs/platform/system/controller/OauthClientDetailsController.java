package com.zclcs.platform.system.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.common.security.starter.annotation.Inner;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.entity.ao.OauthClientDetailsAo;
import com.zclcs.platform.system.api.entity.vo.OauthClientDetailsVo;
import com.zclcs.platform.system.service.OauthClientDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 终端信息 Controller
 *
 * @author zclcs
 * @date 2023-01-30 16:48:03.522
 */
@Slf4j
@RestController
@RequestMapping("/oauthClientDetails")
@RequiredArgsConstructor
@Tag(name = "终端信息")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OauthClientDetailsController {

    private final OauthClientDetailsService oauthClientDetailsService;

    @GetMapping
    @PreAuthorize("hasAuthority('oauthClientDetails:view')")
    @Operation(summary = "终端信息查询（分页）")
    public BaseRsp<BasePage<OauthClientDetailsVo>> findOauthClientDetailsPage(@Validated BasePageAo basePageAo, @Validated OauthClientDetailsVo oauthClientDetailsVo) {
        BasePage<OauthClientDetailsVo> page = this.oauthClientDetailsService.findOauthClientDetailsPage(basePageAo, oauthClientDetailsVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oauthClientDetails:view')")
    @Operation(summary = "终端信息查询（集合）")
    public BaseRsp<List<OauthClientDetailsVo>> findOauthClientDetailsList(@Validated OauthClientDetailsVo oauthClientDetailsVo) {
        List<OauthClientDetailsVo> list = this.oauthClientDetailsService.findOauthClientDetailsList(oauthClientDetailsVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @PreAuthorize("hasAuthority('oauthClientDetails:view')")
    @Operation(summary = "终端信息查询（单个）")
    public BaseRsp<OauthClientDetailsVo> findOauthClientDetails(@Validated OauthClientDetailsVo oauthClientDetailsVo) {
        OauthClientDetailsVo oauthClientDetails = this.oauthClientDetailsService.findOauthClientDetails(oauthClientDetailsVo);
        return RspUtil.data(oauthClientDetails);
    }

    @GetMapping("/findByClientId/{clientId}")
    @Operation(summary = "终端信息查询")
    @Inner
    public OauthClientDetails findByClientId(@PathVariable String clientId) {
        return oauthClientDetailsService.lambdaQuery().eq(OauthClientDetails::getClientId, clientId).one();
    }

    @GetMapping("/checkClientId")
    @PreAuthorize("hasAnyAuthority('oauthClientDetails:add', 'oauthClientDetails:update')")
    @Operation(summary = "检查客户端编号")
    @Parameters({
            @Parameter(name = "clientId", description = "客户端编号", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<Object> checkClientId(@NotBlank(message = "{required}") @RequestParam String clientId) {
        oauthClientDetailsService.validateClientId(clientId);
        return RspUtil.message();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('oauthClientDetails:add')")
    @ControllerEndpoint(operation = "新增终端信息")
    @Operation(summary = "新增终端信息")
    public BaseRsp<OauthClientDetails> addOauthClientDetails(@RequestBody @Validated OauthClientDetailsAo oauthClientDetailsAo) {
        return RspUtil.data(this.oauthClientDetailsService.createOauthClientDetails(oauthClientDetailsAo));
    }

    @DeleteMapping("/{oauthClientDetailsIds}")
    @PreAuthorize("hasAuthority('oauthClientDetails:delete')")
    @ControllerEndpoint(operation = "删除终端信息")
    @Operation(summary = "删除终端信息")
    @Parameters({
            @Parameter(name = "oauthClientDetailsIds", description = "终端信息id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteOauthClientDetails(@PathVariable String oauthClientDetailsIds) {
        List<String> ids = Arrays.stream(oauthClientDetailsIds.split(StringConstant.COMMA)).collect(Collectors.toList());
        this.oauthClientDetailsService.deleteOauthClientDetails(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('oauthClientDetails:update')")
    @ControllerEndpoint(operation = "修改终端信息")
    @Operation(summary = "修改终端信息")
    public BaseRsp<OauthClientDetails> updateOauthClientDetails(@RequestBody @Validated(UpdateStrategy.class) OauthClientDetailsAo oauthClientDetailsAo) {
        return RspUtil.data(this.oauthClientDetailsService.updateOauthClientDetails(oauthClientDetailsAo));
    }
}