package com.zclcs.test.test.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.test.test.api.bean.ao.ChildProjectAo;
import com.zclcs.test.test.api.bean.entity.ChildProject;
import com.zclcs.test.test.api.bean.vo.ChildProjectVo;
import com.zclcs.test.test.service.ChildProjectService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工程信息
 *
 * @author zclcs
 * @since 2023-09-04 20:04:57.706
 */
@Slf4j
@RestController
@RequestMapping("/childProject")
@RequiredArgsConstructor
public class ChildProjectController {

    private final ChildProjectService childProjectService;

    /**
     * 工程信息查询（分页）
     * 权限: childProject:view
     *
     * @see ChildProjectService#findChildProjectPage(BasePageAo, ChildProjectVo)
     */
    @GetMapping
    @SaCheckPermission("childProject:view")
    public BaseRsp<BasePage<ChildProjectVo>> findChildProjectPage(@Validated BasePageAo basePageAo, @Validated ChildProjectVo childProjectVo) {
        BasePage<ChildProjectVo> page = this.childProjectService.findChildProjectPage(basePageAo, childProjectVo);
        return RspUtil.data(page);
    }

    /**
     * 工程信息查询（集合）
     * 权限: childProject:view
     *
     * @see ChildProjectService#findChildProjectList(ChildProjectVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("childProject:view")
    public BaseRsp<List<ChildProjectVo>> findChildProjectList(@Validated ChildProjectVo childProjectVo) {
        List<ChildProjectVo> list = this.childProjectService.findChildProjectList(childProjectVo);
        return RspUtil.data(list);
    }

    /**
     * 工程信息查询（单个）
     * 权限: childProject:view
     *
     * @see ChildProjectService#findChildProject(ChildProjectVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("childProject:view")
    public BaseRsp<ChildProjectVo> findChildProject(@Validated ChildProjectVo childProjectVo) {
        ChildProjectVo childProject = this.childProjectService.findChildProject(childProjectVo);
        return RspUtil.data(childProject);
    }

    /**
     * 新增工程信息
     * 权限: childProject:add
     *
     * @see ChildProjectService#createChildProject(ChildProjectAo)
     */
    @PostMapping
    @SaCheckPermission("childProject:add")
    @ControllerEndpoint(operation = "新增工程信息")
    public BaseRsp<ChildProject> addChildProject(@RequestBody @Validated ChildProjectAo childProjectAo) {
        return RspUtil.data(this.childProjectService.createChildProject(childProjectAo));
    }

    /**
     * 删除工程信息
     * 权限: childProject:delete
     *
     * @param childProjectIds 工程信息id集合(,分隔)
     * @see ChildProjectService#deleteChildProject(List)
     */
    @DeleteMapping("/{childProjectIds}")
    @SaCheckPermission("childProject:delete")
    @ControllerEndpoint(operation = "删除工程信息")
    public BaseRsp<String> deleteChildProject(@NotBlank(message = "{required}") @PathVariable String childProjectIds) {
        List<Long> ids = Arrays.stream(childProjectIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.childProjectService.deleteChildProject(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改工程信息
     * 权限: childProject:update
     *
     * @see ChildProjectService#updateChildProject(ChildProjectAo)
     */
    @PutMapping
    @SaCheckPermission("childProject:update")
    @ControllerEndpoint(operation = "修改工程信息")
    public BaseRsp<ChildProject> updateChildProject(@RequestBody @Validated(UpdateStrategy.class) ChildProjectAo childProjectAo) {
        return RspUtil.data(this.childProjectService.updateChildProject(childProjectAo));
    }

    /**
     * 导出工程信息
     * 权限: childProject:export
     *
     * @see ChildProjectService#exportExcel(ChildProjectVo)
     */
    @GetMapping("/export/excel")
    @SaCheckPermission("childProject:export")
    public void exportExcel(@Validated ChildProjectVo childProjectVo) {
        childProjectService.exportExcel(childProjectVo);
    }

    /**
     * 导入工程信息
     * 权限: childProject:import
     *
     * @see ChildProjectService#importExcel(MultipartFile)
     */
    @PostMapping("/import/excel")
    @SaCheckPermission("childProject:import")
    public BaseRsp<String> importExcel(MultipartFile file) {
        childProjectService.importExcel(file);
        return RspUtil.message("导入成功");
    }
}