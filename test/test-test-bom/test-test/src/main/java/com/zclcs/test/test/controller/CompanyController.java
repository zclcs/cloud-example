package com.zclcs.test.test.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.test.test.api.bean.entity.Company;
import com.zclcs.test.test.api.bean.ao.CompanyAo;
import com.zclcs.test.test.api.bean.vo.CompanyVo;
import com.zclcs.test.test.service.CompanyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 企业信息
 *
 * @author zclcs
 * @date 2023-07-26 22:30:41.396
 */
@Slf4j
@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /**
     * 企业信息查询（分页）
     * 权限: company:view
     *
     * @see CompanyService#findCompanyPage(BasePageAo, CompanyVo)
     */
    @GetMapping
    @SaCheckPermission("company:view")
    public BaseRsp<BasePage<CompanyVo>> findCompanyPage(@Validated BasePageAo basePageAo, @Validated CompanyVo companyVo) {
        BasePage<CompanyVo> page = this.companyService.findCompanyPage(basePageAo, companyVo);
        return RspUtil.data(page);
    }

    /**
     * 企业信息查询（集合）
     * 权限: company:view
     *
     * @see CompanyService#findCompanyList(CompanyVo)
     */
    @GetMapping("list")
    @SaCheckPermission("company:view")
    public BaseRsp<List<CompanyVo>> findCompanyList(@Validated CompanyVo companyVo) {
        List<CompanyVo> list = this.companyService.findCompanyList(companyVo);
        return RspUtil.data(list);
    }

    /**
     * 企业信息查询（单个）
     * 权限: company:view
     *
     * @see CompanyService#findCompany(CompanyVo)
     */
    @GetMapping("one")
    @SaCheckPermission("company:view")
    public BaseRsp<CompanyVo> findCompany(@Validated CompanyVo companyVo) {
        CompanyVo company = this.companyService.findCompany(companyVo);
        return RspUtil.data(company);
    }

    /**
     * 新增企业信息
     * 权限: company:add
     *
     * @see CompanyService#createCompany(CompanyAo)
     */
    @PostMapping
    @SaCheckPermission("company:add")
    @ControllerEndpoint(operation = "新增企业信息")
    public BaseRsp<Company> addCompany(@RequestBody @Validated CompanyAo companyAo) {
        return RspUtil.data(this.companyService.createCompany(companyAo));
    }

    /**
     * 删除企业信息
     * 权限: company:delete
     *
     * @param companyIds 企业信息id集合(,分隔)
     * @see CompanyService#deleteCompany(List)
     */
    @DeleteMapping("/{companyIds}")
    @SaCheckPermission("company:delete")
    @ControllerEndpoint(operation = "删除企业信息")
    public BaseRsp<String> deleteCompany(@NotBlank(message = "{required}") @PathVariable String companyIds) {
        List<Long> ids = Arrays.stream(companyIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.companyService.deleteCompany(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改企业信息
     * 权限: company:update
     *
     * @see CompanyService#updateCompany(CompanyAo)
     */
    @PutMapping
    @SaCheckPermission("company:update")
    @ControllerEndpoint(operation = "修改企业信息")
    public BaseRsp<Company> updateCompany(@RequestBody @Validated(UpdateStrategy.class) CompanyAo companyAo) {
        return RspUtil.data(this.companyService.updateCompany(companyAo));
    }
}