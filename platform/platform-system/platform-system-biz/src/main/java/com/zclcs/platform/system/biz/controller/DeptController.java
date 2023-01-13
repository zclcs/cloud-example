package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.Dept;
import com.zclcs.platform.system.api.entity.ao.DeptAo;
import com.zclcs.platform.system.api.entity.vo.DeptVo;
import com.zclcs.platform.system.biz.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("dept")
@RequiredArgsConstructor
@Tag(name = "部门")
public class DeptController {

    private final DeptService deptService;

    @GetMapping
    @Operation(summary = "部门查询（分页）")
    @PreAuthorize("hasAuthority('dept:view')")
    public BaseRsp<BasePage<DeptVo>> findDeptPage(@Validated BasePageAo basePageAo, @Validated DeptVo deptVo) {
        BasePage<DeptVo> page = this.deptService.findDeptPage(basePageAo, deptVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "部门查询（集合）")
    @PreAuthorize("hasAuthority('dept:view')")
    public BaseRsp<List<DeptVo>> findDeptList(@Validated DeptVo deptVo) {
        List<DeptVo> list = this.deptService.findDeptList(deptVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "部门查询（单个）")
    @PreAuthorize("hasAuthority('dept:view')")
    public BaseRsp<DeptVo> findDept(@Validated DeptVo deptVo) {
        DeptVo dept = this.deptService.findDept(deptVo);
        return BaseRspUtil.data(dept);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('dept:add')")
    @ControllerEndpoint(operation = "新增部门")
    @Operation(summary = "新增部门")
    public BaseRsp<Dept> addDept(@RequestBody @Validated DeptAo deptAo) {
        return BaseRspUtil.data(this.deptService.createDept(deptAo));
    }

    @DeleteMapping("/{deptIds}")
    @PreAuthorize("hasAuthority('dept:delete')")
    @ControllerEndpoint(operation = "删除部门")
    @Operation(summary = "删除部门")
    @Parameters({
            @Parameter(name = "deptIds", description = "部门id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteDept(@NotBlank(message = "{required}") @PathVariable String deptIds) {
        List<Long> ids = Arrays.stream(deptIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.deptService.deleteDept(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('dept:update')")
    @ControllerEndpoint(operation = "修改部门")
    @Operation(summary = "修改部门")
    public BaseRsp<Dept> updateDept(@RequestBody @Validated(UpdateStrategy.class) DeptAo deptAo) {
        return BaseRspUtil.data(this.deptService.updateDept(deptAo));
    }
}