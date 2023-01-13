package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.utils.BaseTreeUtil;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.common.datasource.starter.utils.BaseQueryWrapperUtil;
import com.zclcs.platform.system.api.entity.Dept;
import com.zclcs.platform.system.api.entity.ao.DeptAo;
import com.zclcs.platform.system.api.entity.vo.DeptTreeVo;
import com.zclcs.platform.system.api.entity.vo.DeptVo;
import com.zclcs.platform.system.biz.mapper.DeptMapper;
import com.zclcs.platform.system.biz.service.DeptService;
import com.zclcs.platform.system.biz.service.UserDataPermissionService;
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
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
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
            return (List<DeptTreeVo>) BaseTreeUtil.build(trees);
        }
    }

    @Override
    public List<Long> getChildDeptId(Long deptId) {
        List<Long> deptIds = new ArrayList<>();
        deptIds.add(deptId);
        getChild(deptIds, this.lambdaQuery().eq(Dept::getDeptId, deptId).one());
        return deptIds;
    }

    private QueryWrapper<DeptVo> getQueryWrapper(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sd.order_num");
        BaseQueryWrapperUtil.likeNotBlank(queryWrapper, "sd.dept_name", deptVo.getDeptName());
        BaseQueryWrapperUtil.betweenDateAddTimeNotBlank(queryWrapper, "sd.create_at", deptVo.getCreateTimeFrom(), deptVo.getCreateTimeTo());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dept createDept(DeptAo deptAo) {
        Dept dept = new Dept();
        BeanUtil.copyProperties(deptAo, dept);
        this.save(dept);
        return dept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dept updateDept(DeptAo deptAo) {
        Dept dept = new Dept();
        BeanUtil.copyProperties(deptAo, dept);
        this.updateById(dept);
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
        ArrayList<Long> distinct = CollectionUtil.distinct(allDeptIds);
        removeByIds(distinct);
//        userDataPermissionService.deleteByDeptIds(deptIds);

//        QueryWrapper<DeptVo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.in("sd.parent_id", distinct);
//        List<DeptVo> deptVos = this.baseMapper.findListVo(queryWrapper);
//        if (CollectionUtils.isNotEmpty(deptVos)) {
//            List<Long> deptIdList = deptVos.stream().map(SystemDeptVo::getDeptId).collect(Collectors.toList());
//            this.delete(deptIdList);
//        }
        this.removeByIds(ids);
    }

    private void buildTrees(List<DeptTreeVo> trees, List<DeptVo> deptVos) {
        deptVos.forEach(deptVo -> {
            DeptTreeVo tree = new DeptTreeVo();
            tree.setId(deptVo.getDeptId());
            tree.setParentId(deptVo.getParentId());
            tree.setLabel(deptVo.getDeptName());
            tree.setOrderNum(deptVo.getOrderNum());
            tree.setDeptName(deptVo.getDeptName());
            trees.add(tree);
        });
    }

    private void getChild(List<Long> allDeptId, Dept systemDept) {
        List<Dept> one = this.lambdaQuery().eq(Dept::getParentId, systemDept.getDeptId()).list();
        if (CollUtil.isNotEmpty(one)) {
            for (Dept dept : one) {
                allDeptId.add(dept.getDeptId());
                getChild(allDeptId, dept);
            }
        }
    }
}
