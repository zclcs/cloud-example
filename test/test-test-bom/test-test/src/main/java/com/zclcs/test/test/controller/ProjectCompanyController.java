package com.zclcs.test.test.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.bean.ValidatedList;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.test.test.api.bean.ao.ProjectCompanyAo;
import com.zclcs.test.test.api.bean.entity.ProjectCompany;
import com.zclcs.test.test.api.bean.vo.ProjectCompanyVo;
import com.zclcs.test.test.service.ProjectCompanyService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目参建单位信息数据
 *
 * @author zclcs
 * @since 2023-09-08 16:48:43.853
 */
@Slf4j
@RestController
@RequestMapping("/projectCompany")
@RequiredArgsConstructor
public class ProjectCompanyController {

    private final ProjectCompanyService projectCompanyService;

    /**
     * 项目参建单位信息数据查询（分页）
     * 权限: projectCompany:page
     *
     * @see ProjectCompanyService#findProjectCompanyPage(BasePageAo, ProjectCompanyVo)
     */
    @GetMapping
    @SaCheckPermission("projectCompany:page")
    public BaseRsp<BasePage<ProjectCompanyVo>> findProjectCompanyPage(@Validated BasePageAo basePageAo, @Validated ProjectCompanyVo projectCompanyVo) {
        BasePage<ProjectCompanyVo> page = this.projectCompanyService.findProjectCompanyPage(basePageAo, projectCompanyVo);
        return RspUtil.data(page);
    }

    /**
     * 项目参建单位信息数据查询（集合）
     * 权限: projectCompany:list
     *
     * @see ProjectCompanyService#findProjectCompanyList(ProjectCompanyVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("projectCompany:list")
    public BaseRsp<List<ProjectCompanyVo>> findProjectCompanyList(@Validated ProjectCompanyVo projectCompanyVo) {
        List<ProjectCompanyVo> list = this.projectCompanyService.findProjectCompanyList(projectCompanyVo);
        return RspUtil.data(list);
    }

    /**
     * 项目参建单位信息数据查询（单个）
     * 权限: projectCompany:one
     *
     * @see ProjectCompanyService#findProjectCompany(ProjectCompanyVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("projectCompany:one")
    public BaseRsp<ProjectCompanyVo> findProjectCompany(@Validated ProjectCompanyVo projectCompanyVo) {
        ProjectCompanyVo projectCompany = this.projectCompanyService.findProjectCompany(projectCompanyVo);
        return RspUtil.data(projectCompany);
    }

    /**
     * 新增项目参建单位信息数据
     * 权限: projectCompany:add
     *
     * @see ProjectCompanyService#createProjectCompany(ProjectCompanyAo)
     */
    @PostMapping
    @SaCheckPermission("projectCompany:add")
    @ControllerEndpoint(operation = "新增项目参建单位信息数据")
    public BaseRsp<ProjectCompany> addProjectCompany(@Validated @RequestBody ProjectCompanyAo projectCompanyAo) {
        return RspUtil.data(this.projectCompanyService.createProjectCompany(projectCompanyAo));
    }

    /**
     * 修改项目参建单位信息数据
     * 权限: projectCompany:update
     *
     * @see ProjectCompanyService#updateProjectCompany(ProjectCompanyAo)
     */
    @PutMapping
    @SaCheckPermission("projectCompany:update")
    @ControllerEndpoint(operation = "修改项目参建单位信息数据")
    public BaseRsp<ProjectCompany> updateProjectCompany(@Validated({ValidGroups.Crud.Update.class}) @RequestBody ProjectCompanyAo projectCompanyAo) {
        return RspUtil.data(this.projectCompanyService.updateProjectCompany(projectCompanyAo));
    }

    /**
     * 新增或修改项目参建单位信息数据
     * 权限: projectCompany:createOrUpdate
     *
     * @see ProjectCompanyService#createOrUpdateProjectCompany(ProjectCompanyAo)
     */
    @PostMapping("/createOrUpdate")
    @SaCheckPermission("projectCompany:createOrUpdate")
    @ControllerEndpoint(operation = "新增或修改项目参建单位信息数据")
    public BaseRsp<ProjectCompany> createOrUpdateProjectCompany(@RequestBody @Validated ProjectCompanyAo projectCompanyAo) {
        return RspUtil.data(this.projectCompanyService.createOrUpdateProjectCompany(projectCompanyAo));
    }

    /**
     * 删除项目参建单位信息数据
     * 权限: projectCompany:delete
     *
     * @param projectCompanyIds 项目参建单位信息数据id集合(,分隔)
     * @see ProjectCompanyService#deleteProjectCompany(List)
     */
    @DeleteMapping("/{projectCompanyIds}")
    @SaCheckPermission("projectCompany:delete")
    @ControllerEndpoint(operation = "删除项目参建单位信息数据")
    public BaseRsp<String> deleteProjectCompany(@NotBlank(message = "{required}") @PathVariable String projectCompanyIds) {
        List<Long> ids = Arrays.stream(projectCompanyIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.projectCompanyService.deleteProjectCompany(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 批量新增项目参建单位信息数据
     * 权限: projectCompany:add:batch
     *
     * @see ProjectCompanyService#createProjectCompanyBatch(List)
     */
    @PostMapping("/batch")
    @SaCheckPermission("projectCompany:add:batch")
    @ControllerEndpoint(operation = "批量新增项目参建单位信息数据")
    public BaseRsp<List<ProjectCompany>> createProjectCompanyBatch(@RequestBody @Validated ValidatedList<ProjectCompanyAo> projectCompanyAos) {
        return RspUtil.data(this.projectCompanyService.createProjectCompanyBatch(projectCompanyAos));
    }

    /**
     * 批量修改项目参建单位信息数据
     * 权限: projectCompany:update:batch
     *
     * @see ProjectCompanyService#createOrUpdateProjectCompanyBatch(List)
     */
    @PutMapping("/batch")
    @SaCheckPermission("projectCompany:update:batch")
    @ControllerEndpoint(operation = "批量修改项目参建单位信息数据")
    public BaseRsp<List<ProjectCompany>> updateProjectCompanyBatch(@RequestBody @Validated({ValidGroups.Crud.Update.class}) ValidatedList<ProjectCompanyAo> projectCompanyAos) {
        return RspUtil.data(this.projectCompanyService.updateProjectCompanyBatch(projectCompanyAos));
    }

    /**
     * 批量新增或修改项目参建单位信息数据
     * id为空则新增，不为空则修改
     * 权限: projectCompany:createOrUpdate:batch
     *
     * @see ProjectCompanyService#createOrUpdateProjectCompanyBatch(List)
     */
    @PostMapping("/createOrUpdate/batch")
    @SaCheckPermission("projectCompany:createOrUpdate:batch")
    @ControllerEndpoint(operation = "批量新增或修改项目参建单位信息数据")
    public BaseRsp<List<ProjectCompany>> createOrUpdateProjectCompanyBatch(@RequestBody @Validated ValidatedList<ProjectCompanyAo> projectCompanyAos) {
        return RspUtil.data(this.projectCompanyService.createOrUpdateProjectCompanyBatch(projectCompanyAos));
    }

    /**
     * 导出项目参建单位信息数据
     * 权限: projectCompany:export
     *
     * @see ProjectCompanyService#exportExcel(ProjectCompanyVo)
     */
    @GetMapping("/export/excel")
    @SaCheckPermission("projectCompany:export")
    public void exportExcel(@Validated ProjectCompanyVo projectCompanyVo) {
        projectCompanyService.exportExcel(projectCompanyVo);
    }

    /**
     * 导入项目参建单位信息数据
     * 权限: projectCompany:import
     *
     * @see ProjectCompanyService#importExcel(MultipartFile)
     */
    @PostMapping("/import/excel")
    @SaCheckPermission("projectCompany:import")
    public BaseRsp<String> importExcel(@NotNull(message = "{required}") MultipartFile file) {
        projectCompanyService.importExcel(file);
        return RspUtil.message("导入成功");
    }
}