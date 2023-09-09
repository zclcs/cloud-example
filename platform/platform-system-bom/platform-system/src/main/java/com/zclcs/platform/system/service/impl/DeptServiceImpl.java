package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.core.utils.TreeUtil;
import com.zclcs.cloud.lib.mybatis.flex.utils.PredicateUtil;
import com.zclcs.platform.system.api.bean.ao.DeptAo;
import com.zclcs.platform.system.api.bean.entity.Dept;
import com.zclcs.platform.system.api.bean.entity.UserDataPermission;
import com.zclcs.platform.system.api.bean.vo.DeptTreeVo;
import com.zclcs.platform.system.api.bean.vo.DeptVo;
import com.zclcs.platform.system.mapper.DeptMapper;
import com.zclcs.platform.system.service.DeptService;
import com.zclcs.platform.system.service.UserDataPermissionService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.DeptTableDef.DEPT;
import static com.zclcs.platform.system.api.bean.entity.table.UserDataPermissionTableDef.USER_DATA_PERMISSION;

/**
 * 部门 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:10.151
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    private final UserDataPermissionService userDataPermissionService;

    @Override
    public BasePage<DeptVo> findDeptPage(BasePageAo basePageAo, DeptVo deptVo) {
        QueryWrapper queryWrapper = getQueryWrapper(deptVo);
        Page<DeptVo> deptVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, DeptVo.class);
        return new BasePage<>(deptVoPage);
    }

    @Override
    public List<DeptVo> findDeptList(DeptVo deptVo) {
        QueryWrapper queryWrapper = getQueryWrapper(deptVo);
        return this.mapper.selectListByQueryAs(queryWrapper, DeptVo.class);
    }

    @Override
    public DeptVo findDept(DeptVo deptVo) {
        QueryWrapper queryWrapper = getQueryWrapper(deptVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, DeptVo.class);
    }

    @Override
    public Long countDept(DeptVo deptVo) {
        QueryWrapper queryWrapper = getQueryWrapper(deptVo);
        return this.mapper.selectCountByQuery(queryWrapper);
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
        Dept one = this.getById(deptId);
        if (one == null) {
            return deptIds;
        }
        deptIds.add(deptId);
        getChild(deptIds, one);
        return deptIds;
    }

    private QueryWrapper getQueryWrapper(DeptVo deptVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        DEPT.DEPT_ID,
                        DEPT.DEPT_CODE,
                        DEPT.PARENT_CODE,
                        DEPT.DEPT_NAME,
                        DEPT.ORDER_NUM,
                        DEPT.CREATE_AT,
                        DEPT.UPDATE_AT
                )
                .where(DEPT.DEPT_NAME.like(deptVo.getDeptName(), If::hasText))
                .and(DEPT.CREATE_AT.between(
                        deptVo.getCreateTimeFrom(),
                        deptVo.getCreateTimeTo(),
                        PredicateUtil.localDateBothNotNull
                ))
                .orderBy(DEPT.ORDER_NUM.asc())
        ;
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
        Object[] userIds = userDataPermissionService.queryChain().where(USER_DATA_PERMISSION.DEPT_ID.in(ids)).list().stream()
                .map(UserDataPermission::getUserId).toList().toArray();
        removeByIds(deptIdsDistinct);
        SystemCacheUtil.deleteDeptByDeptIds(deptIds);
        userDataPermissionService.updateChain().where(USER_DATA_PERMISSION.DEPT_ID.in(ids)).remove();
        SystemCacheUtil.deleteDeptIdsByUserIds(userIds);
    }

    private void buildTrees(List<DeptTreeVo> trees, List<DeptVo> deptVos) {
        deptVos.forEach(deptVo -> {
            DeptTreeVo tree = new DeptTreeVo();
            tree.setId(deptVo.getDeptId());
            tree.setCode(deptVo.getDeptCode());
            tree.setParentCode(deptVo.getParentCode());
            tree.setHarPar(!deptVo.getParentCode().equals(CommonCore.TOP_PARENT_CODE));
            tree.setLabel(deptVo.getDeptName());
            tree.setOrderNum(deptVo.getOrderNum());
            tree.setDeptName(deptVo.getDeptName());
            trees.add(tree);
        });
    }

    private void getChild(List<Long> allDeptId, Dept systemDept) {
        List<Dept> list = this.queryChain().where(DEPT.PARENT_CODE.eq(systemDept.getDeptCode())).list();
        if (CollUtil.isNotEmpty(list)) {
            for (Dept dept : list) {
                allDeptId.add(dept.getDeptId());
                getChild(allDeptId, dept);
            }
        }
    }

    @Override
    public void validateDeptCode(String deptCode, Long deptId) {
        if (CommonCore.TOP_PARENT_CODE.equals(deptCode)) {
            throw new FieldException("部门编码输入非法值");
        }
        Dept one = this.queryChain().where(DEPT.DEPT_CODE.eq(deptCode)).one();
        if (one != null && !one.getDeptId().equals(deptId)) {
            throw new FieldException("部门编码重复");
        }
    }

    @Override
    public void validateDeptName(String deptName, Long deptId) {
        Dept one = this.queryChain().where(DEPT.DEPT_NAME.eq(deptName)).one();
        if (one != null && !one.getDeptId().equals(deptId)) {
            throw new FieldException("部门名称重复");
        }
    }
}
