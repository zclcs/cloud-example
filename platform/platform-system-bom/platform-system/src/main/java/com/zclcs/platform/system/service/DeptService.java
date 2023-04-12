package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.Dept;
import com.zclcs.platform.system.api.entity.ao.DeptAo;
import com.zclcs.platform.system.api.entity.vo.DeptTreeVo;
import com.zclcs.platform.system.api.entity.vo.DeptVo;

import java.util.List;

/**
 * 部门 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
public interface DeptService extends IService<Dept> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param deptVo     deptVo
     * @return BasePage<DeptVo>
     */
    BasePage<DeptVo> findDeptPage(BasePageAo basePageAo, DeptVo deptVo);

    /**
     * 查询（所有）
     *
     * @param deptVo deptVo
     * @return List<DeptVo>
     */
    List<DeptVo> findDeptList(DeptVo deptVo);

    /**
     * 查询（单个）
     *
     * @param deptVo deptVo
     * @return DeptVo
     */
    DeptVo findDept(DeptVo deptVo);

    /**
     * 统计
     *
     * @param deptVo deptVo
     * @return DeptVo
     */
    Integer countDept(DeptVo deptVo);

    /**
     * 获取部门列表树
     *
     * @param deptVo deptTreeVo
     * @return 部门列表
     */
    List<DeptTreeVo> findDeptTree(DeptVo deptVo);

    /**
     * 返回本级以及下级部门编号
     *
     * @param deptId 部门编号
     * @return 本级以及下级部门编号
     */
    List<Long> getChildDeptId(Long deptId);

    /**
     * 新增
     *
     * @param deptAo deptAo
     * @return Dept
     */
    Dept createDept(DeptAo deptAo);

    /**
     * 修改
     *
     * @param deptAo deptAo
     * @return Dept
     */
    Dept updateDept(DeptAo deptAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteDept(List<Long> ids);

    /**
     * 验证部门编码
     *
     * @param deptCode 部门编码
     * @param deptId   部门id
     */
    void validateDeptCode(String deptCode, Long deptId);

    /**
     * 验证部门名称
     *
     * @param deptName 部门名称
     * @param deptId   部门id
     */
    void validateDeptName(String deptName, Long deptId);

}
