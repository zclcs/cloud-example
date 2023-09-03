package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.ProjectAo;
import com.zclcs.test.test.api.bean.entity.Project;
import com.zclcs.test.test.api.bean.vo.ProjectVo;
import com.zclcs.test.test.mapper.ProjectMapper;
import com.zclcs.test.test.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目信息 Service实现
 *
 * @author zclcs
 * @since 2023-09-02 17:12:14.267
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public BasePage<ProjectVo> findProjectPage(BasePageAo basePageAo, ProjectVo projectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectVo);
        Page<ProjectVo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, ProjectVo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<ProjectVo> findProjectList(ProjectVo projectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectVo);
        return this.mapper.selectListByQueryAs(queryWrapper, ProjectVo.class);
    }

    @Override
    public ProjectVo findProject(ProjectVo projectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, ProjectVo.class);
    }

    @Override
    public Long countProject(ProjectVo projectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(ProjectVo projectVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Project createProject(ProjectAo projectAo) {
        Project project = new Project();
        BeanUtil.copyProperties(projectAo, project);
        this.save(project);
        return project;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Project updateProject(ProjectAo projectAo) {
        Project project = new Project();
        BeanUtil.copyProperties(projectAo, project);
        this.updateById(project);
        return project;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProject(List<Long> ids) {
        this.removeByIds(ids);
    }
}
