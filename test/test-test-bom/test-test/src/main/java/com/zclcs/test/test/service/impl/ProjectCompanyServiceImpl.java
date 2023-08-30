package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.ProjectCompanyAo;
import com.zclcs.test.test.api.bean.entity.ProjectCompany;
import com.zclcs.test.test.api.bean.vo.ProjectCompanyVo;
import com.zclcs.test.test.mapper.ProjectCompanyMapper;
import com.zclcs.test.test.service.ProjectCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目参建单位信息 Service实现
 *
 * @author zclcs
 * @since 2023-08-16 14:53:21.059
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProjectCompanyServiceImpl extends ServiceImpl<ProjectCompanyMapper, ProjectCompany> implements ProjectCompanyService {

    @Override
    public BasePage<ProjectCompanyVo> findProjectCompanyPage(BasePageAo basePageAo, ProjectCompanyVo projectCompanyVo) {
        BasePage<ProjectCompanyVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<ProjectCompanyVo> queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<ProjectCompanyVo> findProjectCompanyList(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper<ProjectCompanyVo> queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.findListVo(queryWrapper);
    }

    @Override
    public ProjectCompanyVo findProjectCompany(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper<ProjectCompanyVo> queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countProjectCompany(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper<ProjectCompanyVo> queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.countVo(queryWrapper);
    }

    private QueryWrapper<ProjectCompanyVo> getQueryWrapper(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper<ProjectCompanyVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProjectCompany createProjectCompany(ProjectCompanyAo projectCompanyAo) {
        ProjectCompany projectCompany = new ProjectCompany();
        BeanUtil.copyProperties(projectCompanyAo, projectCompany);
        this.save(projectCompany);
        return projectCompany;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProjectCompany updateProjectCompany(ProjectCompanyAo projectCompanyAo) {
        ProjectCompany projectCompany = new ProjectCompany();
        BeanUtil.copyProperties(projectCompanyAo, projectCompany);
        this.updateById(projectCompany);
        return projectCompany;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProjectCompany(List<Long> ids) {
        this.removeByIds(ids);
    }
}
