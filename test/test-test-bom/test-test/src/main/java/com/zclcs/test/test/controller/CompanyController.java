package com.zclcs.test.test.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.test.test.api.entity.Company;
import com.zclcs.test.test.api.entity.ao.CompanyAo;
import com.zclcs.test.test.api.entity.vo.CompanyVo;
import com.zclcs.test.test.service.CompanyService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 企业信息 Controller
 *
 * @author zclcs
 * @date 2023-04-12 15:19:44.653
 */
@Slf4j
@RestController
@RequestMapping("company")
@RequiredArgsConstructor
@Tag(name = "企业信息")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @PreAuthorize("hasAuthority('company:view')")
    @Operation(summary = "企业信息查询（分页）")
    public BaseRsp<BasePage<CompanyVo>> findCompanyPage(@Validated BasePageAo basePageAo, @Validated CompanyVo companyVo) {
        BasePage<CompanyVo> page = this.companyService.findCompanyPage(basePageAo, companyVo);
        return RspUtil.data(page);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('company:view')")
    @Operation(summary = "企业信息查询（集合）")
    public BaseRsp<List<CompanyVo>> findCompanyList(@Validated CompanyVo companyVo) {
        List<CompanyVo> list = this.companyService.findCompanyList(companyVo);
        return RspUtil.data(list);
    }

    @GetMapping("one")
    @PreAuthorize("hasAuthority('company:view')")
    @Operation(summary = "企业信息查询（单个）")
    public BaseRsp<CompanyVo> findCompany(@Validated CompanyVo companyVo) {
        CompanyVo company = this.companyService.findCompany(companyVo);
        return RspUtil.data(company);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('company:add')")
    @ControllerEndpoint(operation = "新增企业信息")
    @Operation(summary = "新增企业信息")
    public BaseRsp<Company> addCompany(@RequestBody @Validated CompanyAo companyAo) {
        return RspUtil.data(this.companyService.createCompany(companyAo));
    }

    @DeleteMapping("/{companyIds}")
    @PreAuthorize("hasAuthority('company:delete')")
    @ControllerEndpoint(operation = "删除企业信息")
    @Operation(summary = "删除企业信息")
    @Parameters({
        @Parameter(name = "companyIds", description = "企业信息id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteCompany(@NotBlank(message = "{required}") @PathVariable String companyIds) {
        List<Long> ids = Arrays.stream(companyIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.companyService.deleteCompany(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('company:update')")
    @ControllerEndpoint(operation = "修改企业信息")
    @Operation(summary = "修改企业信息")
    public BaseRsp<Company> updateCompany(@RequestBody @Validated(UpdateStrategy.class) CompanyAo companyAo) {
        return RspUtil.data(this.companyService.updateCompany(companyAo));
    }
}