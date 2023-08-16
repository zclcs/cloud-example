package com.zclcs.test.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.entity.Project;
import com.zclcs.test.test.api.bean.ao.ProjectAo;
import com.zclcs.test.test.api.bean.vo.ProjectVo;

import java.util.List;

/**
 * 项目信息 Service接口
 *
 * @author zclcs
 * @date 2023-08-16 14:53:10.430
 */
public interface ProjectService extends IService<Project> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param projectVo {@link ProjectVo}
     * @return {@link ProjectVo}
     */
    BasePage<ProjectVo> findProjectPage(BasePageAo basePageAo, ProjectVo projectVo);

    /**
     * 查询（所有）
     *
     * @param projectVo {@link ProjectVo}
     * @return {@link ProjectVo}
     */
    List<ProjectVo> findProjectList(ProjectVo projectVo);

    /**
     * 查询（单个）
     *
     * @param projectVo {@link ProjectVo}
     * @return {@link ProjectVo}
     */
    ProjectVo findProject(ProjectVo projectVo);

    /**
     * 统计
     *
     * @param projectVo {@link ProjectVo}
     * @return 统计值
     */
    Integer countProject(ProjectVo projectVo);

    /**
     * 新增
     *
     * @param projectAo {@link ProjectAo}
     * @return {@link Project}
     */
     Project createProject(ProjectAo projectAo);

    /**
     * 修改
     *
     * @param projectAo {@link ProjectAo}
     * @return {@link Project}
     */
     Project updateProject(ProjectAo projectAo);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteProject(List<Long> ids);

}
