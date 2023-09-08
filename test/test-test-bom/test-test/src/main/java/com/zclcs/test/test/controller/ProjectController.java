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
import com.zclcs.test.test.api.bean.ao.ProjectAo;
import com.zclcs.test.test.api.bean.entity.Project;
import com.zclcs.test.test.api.bean.vo.ProjectVo;
import com.zclcs.test.test.service.ProjectService;
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
 * 项目信息
 *
 * @author zclcs
 * @since 2023-09-08 16:48:48.873
 */
@Slf4j
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 项目信息查询（分页）
     * 权限: project:page
     *
     * @see ProjectService#findProjectPage(BasePageAo, ProjectVo)
     */
    @GetMapping
    @SaCheckPermission("project:page")
    public BaseRsp<BasePage<ProjectVo>> findProjectPage(@Validated BasePageAo basePageAo, @Validated ProjectVo projectVo) {
        BasePage<ProjectVo> page = this.projectService.findProjectPage(basePageAo, projectVo);
        return RspUtil.data(page);
    }

    /**
     * 项目信息查询（集合）
     * 权限: project:list
     *
     * @see ProjectService#findProjectList(ProjectVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("project:list")
    public BaseRsp<List<ProjectVo>> findProjectList(@Validated ProjectVo projectVo) {
        List<ProjectVo> list = this.projectService.findProjectList(projectVo);
        return RspUtil.data(list);
    }

    /**
     * 项目信息查询（单个）
     * 权限: project:one
     *
     * @see ProjectService#findProject(ProjectVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("project:one")
    public BaseRsp<ProjectVo> findProject(@Validated ProjectVo projectVo) {
        ProjectVo project = this.projectService.findProject(projectVo);
        return RspUtil.data(project);
    }

    /**
     * 新增项目信息
     * 权限: project:add
     *
     * @see ProjectService#createProject(ProjectAo)
     */
    @PostMapping
    @SaCheckPermission("project:add")
    @ControllerEndpoint(operation = "新增项目信息")
    public BaseRsp<Project> addProject(@Validated @RequestBody ProjectAo projectAo) {
        return RspUtil.data(this.projectService.createProject(projectAo));
    }

    /**
     * 修改项目信息
     * 权限: project:update
     *
     * @see ProjectService#updateProject(ProjectAo)
     */
    @PutMapping
    @SaCheckPermission("project:update")
    @ControllerEndpoint(operation = "修改项目信息")
    public BaseRsp<Project> updateProject(@Validated({ValidGroups.Crud.Update.class}) @RequestBody ProjectAo projectAo) {
        return RspUtil.data(this.projectService.updateProject(projectAo));
    }

    /**
     * 新增或修改项目信息
     * 权限: project:createOrUpdate
     *
     * @see ProjectService#createOrUpdateProject(ProjectAo)
     */
    @PostMapping("/createOrUpdate")
    @SaCheckPermission("project:createOrUpdate")
    @ControllerEndpoint(operation = "新增或修改项目信息")
    public BaseRsp<Project> createOrUpdateProject(@RequestBody @Validated ProjectAo projectAo) {
        return RspUtil.data(this.projectService.createOrUpdateProject(projectAo));
    }

    /**
     * 删除项目信息
     * 权限: project:delete
     *
     * @param projectIds 项目信息id集合(,分隔)
     * @see ProjectService#deleteProject(List)
     */
    @DeleteMapping("/{projectIds}")
    @SaCheckPermission("project:delete")
    @ControllerEndpoint(operation = "删除项目信息")
    public BaseRsp<String> deleteProject(@NotBlank(message = "{required}") @PathVariable String projectIds) {
        List<Long> ids = Arrays.stream(projectIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.projectService.deleteProject(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 批量新增项目信息
     * 权限: project:add:batch
     *
     * @see ProjectService#createProjectBatch(List)
     */
    @PostMapping("/batch")
    @SaCheckPermission("project:add:batch")
    @ControllerEndpoint(operation = "批量新增项目信息")
    public BaseRsp<List<Project>> createProjectBatch(@RequestBody @Validated ValidatedList<ProjectAo> projectAos) {
        return RspUtil.data(this.projectService.createProjectBatch(projectAos));
    }

    /**
     * 批量修改项目信息
     * 权限: project:update:batch
     *
     * @see ProjectService#createOrUpdateProjectBatch(List)
     */
    @PutMapping("/batch")
    @SaCheckPermission("project:update:batch")
    @ControllerEndpoint(operation = "批量修改项目信息")
    public BaseRsp<List<Project>> updateProjectBatch(@RequestBody @Validated({ValidGroups.Crud.Update.class}) ValidatedList<ProjectAo> projectAos) {
        return RspUtil.data(this.projectService.updateProjectBatch(projectAos));
    }

    /**
     * 批量新增或修改项目信息
     * id为空则新增，不为空则修改
     * 权限: project:createOrUpdate:batch
     *
     * @see ProjectService#createOrUpdateProjectBatch(List)
     */
    @PostMapping("/createOrUpdate/batch")
    @SaCheckPermission("project:createOrUpdate:batch")
    @ControllerEndpoint(operation = "批量新增或修改项目信息")
    public BaseRsp<List<Project>> createOrUpdateProjectBatch(@RequestBody @Validated ValidatedList<ProjectAo> projectAos) {
        return RspUtil.data(this.projectService.createOrUpdateProjectBatch(projectAos));
    }

    /**
     * 导出项目信息
     * 权限: project:export
     *
     * @see ProjectService#exportExcel(ProjectVo)
     */
    @GetMapping("/export/excel")
    @SaCheckPermission("project:export")
    public void exportExcel(@Validated ProjectVo projectVo) {
        projectService.exportExcel(projectVo);
    }

    /**
     * 导入项目信息
     * 权限: project:import
     *
     * @see ProjectService#importExcel(MultipartFile)
     */
    @PostMapping("/import/excel")
    @SaCheckPermission("project:import")
    public BaseRsp<String> importExcel(@NotNull(message = "{required}") MultipartFile file) {
        projectService.importExcel(file);
        return RspUtil.message("导入成功");
    }
}