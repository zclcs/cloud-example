package com.zclcs.platform.system.controller;

import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.security.annotation.Inner;
import com.zclcs.platform.system.api.entity.Dept;
import com.zclcs.platform.system.api.entity.ao.DeptAo;
import com.zclcs.platform.system.api.entity.vo.DeptTreeVo;
import com.zclcs.platform.system.api.entity.vo.DeptVo;
import com.zclcs.platform.system.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
@Tag(name = "部门")
public class DeptController {

    private final DeptService deptService;

    @GetMapping
    @PreAuthorize("hasAuthority('dept:view')")
    @Operation(summary = "部门查询（分页）")
    public BaseRsp<BasePage<DeptVo>> findDeptPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated DeptVo deptVo) {
        BasePage<DeptVo> page = this.deptService.findDeptPage(basePageAo, deptVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('dept:view')")
    @Operation(summary = "部门查询（集合）")
    public BaseRsp<List<DeptVo>> findDeptList(@ParameterObject @Validated DeptVo deptVo) {
        List<DeptVo> list = this.deptService.findDeptList(deptVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @PreAuthorize("hasAuthority('dept:view')")
    @Operation(summary = "部门查询（单个）")
    public BaseRsp<DeptVo> findDept(@ParameterObject @Validated DeptVo deptVo) {
        DeptVo dept = this.deptService.findDept(deptVo);
        return RspUtil.data(dept);
    }

    @GetMapping("/tree")
    @PreAuthorize("hasAnyAuthority('user:view', 'dept:view')")
    @Operation(summary = "部门树")
    public BaseRsp<List<DeptTreeVo>> deptTree(@Valid DeptVo deptVo) {
        List<DeptTreeVo> list = this.deptService.findDeptTree(deptVo);
        return RspUtil.data(list);
    }

    @GetMapping("/options")
    @PreAuthorize("hasAnyAuthority('user:view', 'dept:view')")
    @Operation(summary = "部门前端下拉框")
    public BaseRsp<List<DeptVo>> deptOptions(@Valid DeptVo deptVo) {
        List<DeptVo> list = this.deptService.findDeptList(deptVo);
        return RspUtil.data(list);
    }

    @GetMapping(value = "/findByDeptId/{deptId}")
    @Operation(summary = "根据部门id查询部门")
    @Inner
    public Dept findByDeptId(@PathVariable Long deptId) {
        return this.deptService.lambdaQuery().eq(Dept::getDeptId, deptId).one();
    }

    @GetMapping("/checkDeptName")
    @PreAuthorize("hasAnyAuthority('dept:add', 'dept:update')")
    @Operation(summary = "检查部门名称")
    @Parameters({
            @Parameter(name = "deptId", description = "部门id", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "deptName", description = "部门名称", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<Object> checkDeptName(@RequestParam(required = false) Long deptId,
                                         @NotBlank(message = "{required}") @RequestParam String deptName) {
        deptService.validateDeptName(deptName, deptId);
        return RspUtil.message();
    }

    @GetMapping("/checkDeptCode")
    @PreAuthorize("hasAnyAuthority('dept:add', 'dept:update')")
    @Operation(summary = "检查部门编码")
    @Parameters({
            @Parameter(name = "deptId", description = "部门id", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "deptCode", description = "部门编码", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<Object> checkDeptCode(@RequestParam(required = false) Long deptId,
                                         @NotBlank(message = "{required}") @RequestParam String deptCode) {
        deptService.validateDeptCode(deptCode, deptId);
        return RspUtil.message();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('dept:add')")
    @ControllerEndpoint(operation = "新增部门")
    @Operation(summary = "新增部门")
    public BaseRsp<Dept> addDept(@RequestBody @Validated DeptAo deptAo) {
        return RspUtil.data(this.deptService.createDept(deptAo));
    }

    @DeleteMapping("/{deptIds}")
    @PreAuthorize("hasAuthority('dept:delete')")
    @ControllerEndpoint(operation = "删除部门")
    @Operation(summary = "删除部门")
    @Parameters({
            @Parameter(name = "deptIds", description = "部门id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteDept(@NotBlank(message = "{required}") @PathVariable String deptIds) {
        List<Long> ids = Arrays.stream(deptIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.deptService.deleteDept(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('dept:update')")
    @ControllerEndpoint(operation = "修改部门")
    @Operation(summary = "修改部门")
    public BaseRsp<Dept> updateDept(@RequestBody @Validated(UpdateStrategy.class) DeptAo deptAo) {
        return RspUtil.data(this.deptService.updateDept(deptAo));
    }
}