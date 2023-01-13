package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.Dept;
import com.zclcs.platform.system.api.entity.ao.DeptAo;
import com.zclcs.platform.system.api.entity.vo.DeptVo;
import com.zclcs.platform.system.biz.mapper.DeptMapper;
import com.zclcs.platform.system.biz.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public BasePage<DeptVo> findDeptPage(BasePageAo basePageAo, DeptVo deptVo) {
        BasePage<DeptVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<DeptVo> queryWrapper = getQueryWrapper(deptVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<DeptVo> findDeptList(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = getQueryWrapper(deptVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public DeptVo findDept(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = getQueryWrapper(deptVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countDept(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = getQueryWrapper(deptVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<DeptVo> getQueryWrapper(DeptVo deptVo) {
        QueryWrapper<DeptVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
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
        this.removeByIds(ids);
    }
}
