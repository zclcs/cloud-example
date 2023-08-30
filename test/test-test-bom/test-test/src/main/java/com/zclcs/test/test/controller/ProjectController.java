package com.zclcs.test.test.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.test.test.api.bean.entity.Project;
import com.zclcs.test.test.api.bean.ao.ProjectAo;
import com.zclcs.test.test.api.bean.vo.ProjectVo;
import com.zclcs.test.test.service.ProjectService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目信息
 *
 * @author zclcs
 * @since 2023-08-16 14:53:10.430
 */
@Slf4j
@RestController
@RequestMapping("project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 项目信息查询（分页）
     * 权限: project:view
     *
     * @see ProjectService#findProjectPage(BasePageAo, ProjectVo)
     */
    @GetMapping
    @SaCheckPermission("project:view")
    public BaseRsp<BasePage<ProjectVo>> findProjectPage(@Validated BasePageAo basePageAo, @Validated ProjectVo projectVo) {
        BasePage<ProjectVo> page = this.projectService.findProjectPage(basePageAo, projectVo);
        return RspUtil.data(page);
    }

    /**
     * 项目信息查询（集合）
     * 权限: project:view
     *
     * @see ProjectService#findProjectList(ProjectVo)
     */
    @GetMapping("list")
    @SaCheckPermission("project:view")
    public BaseRsp<List<ProjectVo>> findProjectList(@Validated ProjectVo projectVo) {
        List<ProjectVo> list = this.projectService.findProjectList(projectVo);
        return RspUtil.data(list);
    }

    /**
     * 项目信息查询（单个）
     * 权限: project:view
     *
     * @see ProjectService#findProject(ProjectVo)
     */
    @GetMapping("one")
    @SaCheckPermission("project:view")
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
    public BaseRsp<Project> addProject(@RequestBody @Validated ProjectAo projectAo) {
        return RspUtil.data(this.projectService.createProject(projectAo));
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
     * 修改项目信息
     * 权限: project:update
     *
     * @see ProjectService#updateProject(ProjectAo)
     */
    @PutMapping
    @SaCheckPermission("project:update")
    @ControllerEndpoint(operation = "修改项目信息")
    public BaseRsp<Project> updateProject(@RequestBody @Validated(UpdateStrategy.class) ProjectAo projectAo) {
        return RspUtil.data(this.projectService.updateProject(projectAo));
    }
}