package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.bean.Tree;
import com.zclcs.platform.system.api.bean.ao.DeptAo;
import com.zclcs.platform.system.api.bean.entity.Dept;
import com.zclcs.platform.system.api.bean.vo.DeptTreeVo;
import com.zclcs.platform.system.api.bean.vo.DeptVo;

import java.util.List;

/**
 * 部门 Service接口
 *
 * @author zclcs
 * @since 2023-09-01 19:53:38.826
 */
public interface DeptService extends IService<Dept> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param deptVo     {@link DeptVo}
     * @return {@link DeptVo}
     */
    BasePage<DeptVo> findDeptPage(BasePageAo basePageAo, DeptVo deptVo);

    /**
     * 查询（所有）
     *
     * @param deptVo {@link DeptVo}
     * @return {@link DeptVo}
     */
    List<DeptVo> findDeptList(DeptVo deptVo);

    /**
     * 查询（单个）
     *
     * @param deptVo {@link DeptVo}
     * @return {@link DeptVo}
     */
    DeptVo findDept(DeptVo deptVo);

    /**
     * 统计
     *
     * @param deptVo {@link DeptVo}
     * @return 统计值
     */
    Long countDept(DeptVo deptVo);

    /**
     * 获取部门列表树
     *
     * @param deptVo deptTreeVo
     * @return 部门列表
     */
    List<Tree<DeptTreeVo>> findDeptTree(DeptVo deptVo);

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
     * @param deptAo {@link DeptAo}
     * @return {@link Dept}
     */
    Dept createDept(DeptAo deptAo);

    /**
     * 修改
     *
     * @param deptAo {@link DeptAo}
     * @return {@link Dept}
     */
    Dept updateDept(DeptAo deptAo);

    /**
     * 删除
     *
     * @param ids 表id集合
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
