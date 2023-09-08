package com.zclcs.test.test.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.ProjectAo;
import com.zclcs.test.test.api.bean.entity.Project;
import com.zclcs.test.test.api.bean.vo.ProjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 项目信息 Service接口
 *
 * @author zclcs
 * @since 2023-09-08 16:48:48.873
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
    Long countProject(ProjectVo projectVo);

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
     * 新增或修改
     *
     * @param projectAo {@link ProjectAo}
     * @return {@link Project}
     */
    Project createOrUpdateProject(ProjectAo projectAo);

    /**
     * 批量新增
     *
     * @param projectAos {@link ProjectAo}
     * @return {@link Project}
     */
    List<Project> createProjectBatch(List<ProjectAo> projectAos);

    /**
     * 批量修改
     *
     * @param projectAos {@link ProjectAo}
     * @return {@link Project}
     */
    List<Project> updateProjectBatch(List<ProjectAo> projectAos);

    /**
     * 批量新增或修改
     * id为空则新增，不为空则修改
     * 可以自行重写
     *
     * @param projectAos {@link ProjectAo}
     * @return {@link Project}
     */
    List<Project> createOrUpdateProjectBatch(List<ProjectAo> projectAos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteProject(List<Long> ids);

    /**
     * excel导出
     *
     * @param projectVo {@link ProjectVo}
     */
    void exportExcel(ProjectVo projectVo);

    /**
     * excel导入
     *
     * @param multipartFile excel文件
     */
    void importExcel(MultipartFile multipartFile);

}
