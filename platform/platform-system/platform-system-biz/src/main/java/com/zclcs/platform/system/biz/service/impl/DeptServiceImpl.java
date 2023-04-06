package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.exception.MyException;
import com.zclcs.common.core.utils.TreeUtil;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.entity.Dept;
import com.zclcs.platform.system.api.entity.UserDataPermission;
import com.zclcs.platform.system.api.entity.ao.DeptAo;
import com.zclcs.platform.system.api.entity.vo.DeptTreeVo;
import com.zclcs.platform.system.api.entity.vo.DeptVo;
import com.zclcs.platform.system.biz.mapper.DeptMapper;
import com.zclcs.platform.system.biz.service.DeptService;
import com.zclcs.platform.system.biz.service.UserDataPermissionService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    private final UserDataPermissionService userDataPermissionService;

    @Override
    public BasePage<DeptVo> findDeptPage(BasePageAo basePageAo, DeptVo deptVo) {
        BasePage<DeptVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<DeptVo> queryWrapper = getQueryWrapper(deptVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<DeptVo> findDeptList(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = getQueryWrapper(deptVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public DeptVo findDept(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = getQueryWrapper(deptVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countDept(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = getQueryWrapper(deptVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    @Override
    public List<DeptTreeVo> findDeptTree(DeptVo deptVo) {
        List<DeptVo> depts = findDeptList(deptVo);
        List<DeptTreeVo> trees = new ArrayList<>();
        buildTrees(trees, depts);
        if (StrUtil.isNotBlank(deptVo.getDeptName())) {
            return trees;
        } else {
            return (List<DeptTreeVo>) TreeUtil.build(trees);
        }
    }

    @Override
    public List<Long> getChildDeptId(Long deptId) {
        List<Long> deptIds = new ArrayList<>();
        Dept one = this.lambdaQuery().eq(Dept::getDeptId, deptId).one();
        if (one == null) {
            return deptIds;
        }
        deptIds.add(deptId);
        getChild(deptIds, one);
        return deptIds;
    }

    private QueryWrapper<DeptVo> getQueryWrapper(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sd.order_num");
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sd.dept_name", deptVo.getDeptName());
        QueryWrapperUtil.betweenDateAddTimeNotBlank(queryWrapper, "sd.create_at", deptVo.getCreateTimeFrom(), deptVo.getCreateTimeTo());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dept createDept(DeptAo deptAo) {
        validateDeptCode(deptAo.getDeptCode(), deptAo.getDeptId());
        validateDeptName(deptAo.getDeptName(), deptAo.getDeptId());
        Dept dept = new Dept();
        BeanUtil.copyProperties(deptAo, dept);
        this.save(dept);
        SystemCacheUtil.deleteDeptByDeptId(dept.getDeptId());
        return dept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dept updateDept(DeptAo deptAo) {
        validateDeptName(deptAo.getDeptName(), deptAo.getDeptId());
        Dept dept = new Dept();
        BeanUtil.copyProperties(deptAo, dept);
        dept.setDeptCode(null);
        this.updateById(dept);
        SystemCacheUtil.deleteDeptByDeptId(dept.getDeptId());
        return dept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(List<Long> ids) {
        List<Long> allDeptIds = new ArrayList<>(ids);
        for (Long id : ids) {
            List<Long> childDeptIds = getChildDeptId(id);
            allDeptIds.addAll(childDeptIds);
        }
        ArrayList<Long> deptIdsDistinct = CollectionUtil.distinct(allDeptIds);
        Object[] deptIds = deptIdsDistinct.toArray();
        Object[] userIds = userDataPermissionService.lambdaQuery()
                .in(UserDataPermission::getDeptId, ids).list().stream()
                .map(UserDataPermission::getUserId).toList().toArray();
        removeByIds(deptIdsDistinct);
        SystemCacheUtil.deleteDeptByDeptIds(deptIds);
        userDataPermissionService.lambdaUpdate().in(UserDataPermission::getDeptId, ids).remove();
        SystemCacheUtil.deleteDeptIdsByUserIds(userIds);
    }

    private void buildTrees(List<DeptTreeVo> trees, List<DeptVo> deptVos) {
        deptVos.forEach(deptVo -> {
            DeptTreeVo tree = new DeptTreeVo();
            tree.setId(deptVo.getDeptId());
            tree.setCode(deptVo.getDeptCode());
            tree.setParentCode(deptVo.getParentCode());
            tree.setHarPar(!deptVo.getParentCode().equals(MyConstant.TOP_PARENT_CODE));
            tree.setLabel(deptVo.getDeptName());
            tree.setOrderNum(deptVo.getOrderNum());
            tree.setDeptName(deptVo.getDeptName());
            trees.add(tree);
        });
    }

    private void getChild(List<Long> allDeptId, Dept systemDept) {
        List<Dept> list = this.lambdaQuery().eq(Dept::getParentCode, systemDept.getDeptCode()).list();
        if (CollUtil.isNotEmpty(list)) {
            for (Dept dept : list) {
                allDeptId.add(dept.getDeptId());
                getChild(allDeptId, dept);
            }
        }
    }

    @Override
    public void validateDeptCode(String deptCode, Long deptId) {
        if (MyConstant.TOP_PARENT_CODE.equals(deptCode)) {
            throw new MyException("部门编码输入非法值");
        }
        Dept one = this.lambdaQuery().eq(Dept::getDeptCode, deptCode).one();
        if (one != null && !one.getDeptId().equals(deptId)) {
            throw new MyException("部门编码重复");
        }
    }

    @Override
    public void validateDeptName(String deptName, Long deptId) {
        Dept one = this.lambdaQuery().eq(Dept::getDeptName, deptName).one();
        if (one != null && !one.getDeptId().equals(deptId)) {
            throw new MyException("部门名称重复");
        }
    }
}
