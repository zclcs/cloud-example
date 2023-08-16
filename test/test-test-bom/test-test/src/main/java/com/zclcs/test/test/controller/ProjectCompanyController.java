package com.zclcs.test.test.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.test.test.api.bean.entity.ProjectCompany;
import com.zclcs.test.test.api.bean.ao.ProjectCompanyAo;
import com.zclcs.test.test.api.bean.vo.ProjectCompanyVo;
import com.zclcs.test.test.service.ProjectCompanyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目参建单位信息
 *
 * @author zclcs
 * @date 2023-08-16 14:53:21.059
 */
@Slf4j
@RestController
@RequestMapping("projectCompany")
@RequiredArgsConstructor
public class ProjectCompanyController {

    private final ProjectCompanyService projectCompanyService;

    /**
     * 项目参建单位信息查询（分页）
     * 权限: projectCompany:view
     *
     * @see ProjectCompanyService#findProjectCompanyPage(BasePageAo, ProjectCompanyVo)
     */
    @GetMapping
    @SaCheckPermission("projectCompany:view")
    public BaseRsp<BasePage<ProjectCompanyVo>> findProjectCompanyPage(@Validated BasePageAo basePageAo, @Validated ProjectCompanyVo projectCompanyVo) {
        BasePage<ProjectCompanyVo> page = this.projectCompanyService.findProjectCompanyPage(basePageAo, projectCompanyVo);
        return RspUtil.data(page);
    }

    /**
     * 项目参建单位信息查询（集合）
     * 权限: projectCompany:view
     *
     * @see ProjectCompanyService#findProjectCompanyList(ProjectCompanyVo)
     */
    @GetMapping("list")
    @SaCheckPermission("projectCompany:view")
    public BaseRsp<List<ProjectCompanyVo>> findProjectCompanyList(@Validated ProjectCompanyVo projectCompanyVo) {
        List<ProjectCompanyVo> list = this.projectCompanyService.findProjectCompanyList(projectCompanyVo);
        return RspUtil.data(list);
    }

    /**
     * 项目参建单位信息查询（单个）
     * 权限: projectCompany:view
     *
     * @see ProjectCompanyService#findProjectCompany(ProjectCompanyVo)
     */
    @GetMapping("one")
    @SaCheckPermission("projectCompany:view")
    public BaseRsp<ProjectCompanyVo> findProjectCompany(@Validated ProjectCompanyVo projectCompanyVo) {
        ProjectCompanyVo projectCompany = this.projectCompanyService.findProjectCompany(projectCompanyVo);
        return RspUtil.data(projectCompany);
    }

    /**
     * 新增项目参建单位信息
     * 权限: projectCompany:add
     *
     * @see ProjectCompanyService#createProjectCompany(ProjectCompanyAo)
     */
    @PostMapping
    @SaCheckPermission("projectCompany:add")
    @ControllerEndpoint(operation = "新增项目参建单位信息")
    public BaseRsp<ProjectCompany> addProjectCompany(@RequestBody @Validated ProjectCompanyAo projectCompanyAo) {
        return RspUtil.data(this.projectCompanyService.createProjectCompany(projectCompanyAo));
    }

    /**
     * 删除项目参建单位信息
     * 权限: projectCompany:delete
     *
     * @param projectCompanyIds 项目参建单位信息id集合(,分隔)
     * @see ProjectCompanyService#deleteProjectCompany(List)
     */
    @DeleteMapping("/{projectCompanyIds}")
    @SaCheckPermission("projectCompany:delete")
    @ControllerEndpoint(operation = "删除项目参建单位信息")
    public BaseRsp<String> deleteProjectCompany(@NotBlank(message = "{required}") @PathVariable String projectCompanyIds) {
        List<Long> ids = Arrays.stream(projectCompanyIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.projectCompanyService.deleteProjectCompany(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改项目参建单位信息
     * 权限: projectCompany:update
     *
     * @see ProjectCompanyService#updateProjectCompany(ProjectCompanyAo)
     */
    @PutMapping
    @SaCheckPermission("projectCompany:update")
    @ControllerEndpoint(operation = "修改项目参建单位信息")
    public BaseRsp<ProjectCompany> updateProjectCompany(@RequestBody @Validated(UpdateStrategy.class) ProjectCompanyAo projectCompanyAo) {
        return RspUtil.data(this.projectCompanyService.updateProjectCompany(projectCompanyAo));
    }
}