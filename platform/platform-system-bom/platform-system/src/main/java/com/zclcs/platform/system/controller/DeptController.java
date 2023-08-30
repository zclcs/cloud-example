package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.ao.DeptAo;
import com.zclcs.platform.system.api.bean.cache.DeptCacheBean;
import com.zclcs.platform.system.api.bean.entity.Dept;
import com.zclcs.platform.system.api.bean.vo.DeptTreeVo;
import com.zclcs.platform.system.api.bean.vo.DeptVo;
import com.zclcs.platform.system.service.DeptService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.zclcs.platform.system.api.bean.entity.table.DeptTableDef.DEPT;

/**
 * 部门
 *
 * @author zclcs
 * @since 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    /**
     * 部门查询（分页）
     * 权限: dept:view
     *
     * @see DeptService#findDeptPage(BasePageAo, DeptVo)
     */
    @GetMapping
    @SaCheckPermission("dept:view")
    public BaseRsp<BasePage<DeptVo>> findDeptPage(@Validated BasePageAo basePageAo, @Validated DeptVo deptVo) {
        BasePage<DeptVo> page = this.deptService.findDeptPage(basePageAo, deptVo);
        return RspUtil.data(page);
    }

    /**
     * 部门查询（集合）
     * 权限: dept:view
     *
     * @see DeptService#findDeptList(DeptVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("dept:view")
    public BaseRsp<List<DeptVo>> findDeptList(@Validated DeptVo deptVo) {
        List<DeptVo> list = this.deptService.findDeptList(deptVo);
        return RspUtil.data(list);
    }

    /**
     * 部门查询（单个）
     * 权限: dept:view
     *
     * @see DeptService#findDept(DeptVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("dept:view")
    public BaseRsp<DeptVo> findDept(@Validated DeptVo deptVo) {
        DeptVo dept = this.deptService.findDept(deptVo);
        return RspUtil.data(dept);
    }

    /**
     * 获取部门树
     * 权限: user:view 或者 dept:view
     *
     * @see DeptService#findDeptTree(DeptVo)
     */
    @GetMapping("/tree")
    @SaCheckPermission(value = {"user:view", "dept:view"}, mode = SaMode.OR)
    public BaseRsp<List<DeptTreeVo>> deptTree(@Valid DeptVo deptVo) {
        List<DeptTreeVo> list = this.deptService.findDeptTree(deptVo);
        return RspUtil.data(list);
    }

    /**
     * 部门前端下拉框
     * 权限: user:view 或者 dept:view
     *
     * @see DeptService#findDeptList(DeptVo)
     */
    @GetMapping("/options")
    @SaCheckPermission(value = {"user:view", "dept:view"}, mode = SaMode.OR)
    public BaseRsp<List<DeptVo>> deptOptions(@Valid DeptVo deptVo) {
        List<DeptVo> list = this.deptService.findDeptList(deptVo);
        return RspUtil.data(list);
    }

    /**
     * 根据部门id查询部门
     * 权限: 仅限内部调用
     *
     * @param deptId 部门id
     * @return 部门
     */
    @GetMapping(value = "/findByDeptId/{deptId}")
    @Inner
    public DeptCacheBean findByDeptId(@PathVariable Long deptId) {
        return this.deptService.queryChain().where(DEPT.DEPT_ID.eq(deptId)).oneAs(DeptCacheBean.class);
    }

    /**
     * 检查部门名称
     * 权限: dept:add 或者 dept:update
     *
     * @param deptId   部门id
     * @param deptName 部门名称
     * @return 是否成功
     */
    @GetMapping("/checkDeptName")
    @SaCheckPermission(value = {"dept:add", "dept:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkDeptName(@RequestParam(required = false) Long deptId,
                                         @NotBlank(message = "{required}") @RequestParam String deptName) {
        deptService.validateDeptName(deptName, deptId);
        return RspUtil.message();
    }

    /**
     * 检查部门编码
     * 权限: dept:add 或者 dept:update
     *
     * @param deptId   部门id
     * @param deptCode 部门编码
     * @return 是否成功
     */
    @GetMapping("/checkDeptCode")
    @SaCheckPermission(value = {"dept:add", "dept:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkDeptCode(@RequestParam(required = false) Long deptId,
                                         @NotBlank(message = "{required}") @RequestParam String deptCode) {
        deptService.validateDeptCode(deptCode, deptId);
        return RspUtil.message();
    }

    /**
     * 新增部门
     * 权限: dept:add
     *
     * @see DeptService#createDept(DeptAo)
     */
    @PostMapping
    @SaCheckPermission("dept:add")
    @ControllerEndpoint(operation = "新增部门")
    public BaseRsp<Dept> addDept(@RequestBody @Validated DeptAo deptAo) {
        return RspUtil.data(this.deptService.createDept(deptAo));
    }

    /**
     * 删除部门
     * 权限: dept:delete
     *
     * @param deptIds 部门id集合(,分隔)
     * @see DeptService#deleteDept(List)
     */
    @DeleteMapping("/{deptIds}")
    @SaCheckPermission("dept:delete")
    @ControllerEndpoint(operation = "删除部门")
    public BaseRsp<String> deleteDept(@NotBlank(message = "{required}") @PathVariable String deptIds) {
        List<Long> ids = Arrays.stream(deptIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.deptService.deleteDept(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改部门
     * 权限: dept:update
     *
     * @see DeptService#updateDept(DeptAo)
     */
    @PutMapping
    @SaCheckPermission("dept:update")
    @ControllerEndpoint(operation = "修改部门")
    public BaseRsp<Dept> updateDept(@RequestBody @Validated(UpdateStrategy.class) DeptAo deptAo) {
        return RspUtil.data(this.deptService.updateDept(deptAo));
    }
}