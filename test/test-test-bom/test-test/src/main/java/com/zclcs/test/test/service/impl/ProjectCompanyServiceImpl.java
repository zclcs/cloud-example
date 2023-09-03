package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目参建单位信息数据 Service实现
 *
 * @author zclcs
 * @since 2023-09-02 17:12:10.514
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectCompanyServiceImpl extends ServiceImpl<ProjectCompanyMapper, ProjectCompany> implements ProjectCompanyService {

    @Override
    public BasePage<ProjectCompanyVo> findProjectCompanyPage(BasePageAo basePageAo, ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
        Page<ProjectCompanyVo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, ProjectCompanyVo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<ProjectCompanyVo> findProjectCompanyList(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.selectListByQueryAs(queryWrapper, ProjectCompanyVo.class);
    }

    @Override
    public ProjectCompanyVo findProjectCompany(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, ProjectCompanyVo.class);
    }

    @Override
    public Long countProjectCompany(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
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
