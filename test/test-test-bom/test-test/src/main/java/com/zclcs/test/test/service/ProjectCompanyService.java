package com.zclcs.test.test.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.ProjectCompanyAo;
import com.zclcs.test.test.api.bean.entity.ProjectCompany;
import com.zclcs.test.test.api.bean.vo.ProjectCompanyVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 项目参建单位信息数据 Service接口
 *
 * @author zclcs
 * @since 2023-09-08 16:48:43.853
 */
public interface ProjectCompanyService extends IService<ProjectCompany> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param projectCompanyVo {@link ProjectCompanyVo}
     * @return {@link ProjectCompanyVo}
     */
    BasePage<ProjectCompanyVo> findProjectCompanyPage(BasePageAo basePageAo, ProjectCompanyVo projectCompanyVo);

    /**
     * 查询（所有）
     *
     * @param projectCompanyVo {@link ProjectCompanyVo}
     * @return {@link ProjectCompanyVo}
     */
    List<ProjectCompanyVo> findProjectCompanyList(ProjectCompanyVo projectCompanyVo);

    /**
     * 查询（单个）
     *
     * @param projectCompanyVo {@link ProjectCompanyVo}
     * @return {@link ProjectCompanyVo}
     */
    ProjectCompanyVo findProjectCompany(ProjectCompanyVo projectCompanyVo);

    /**
     * 统计
     *
     * @param projectCompanyVo {@link ProjectCompanyVo}
     * @return 统计值
     */
    Long countProjectCompany(ProjectCompanyVo projectCompanyVo);

    /**
     * 新增
     *
     * @param projectCompanyAo {@link ProjectCompanyAo}
     * @return {@link ProjectCompany}
     */
    ProjectCompany createProjectCompany(ProjectCompanyAo projectCompanyAo);

    /**
     * 修改
     *
     * @param projectCompanyAo {@link ProjectCompanyAo}
     * @return {@link ProjectCompany}
     */
    ProjectCompany updateProjectCompany(ProjectCompanyAo projectCompanyAo);

    /**
     * 新增或修改
     *
     * @param projectCompanyAo {@link ProjectCompanyAo}
     * @return {@link ProjectCompany}
     */
    ProjectCompany createOrUpdateProjectCompany(ProjectCompanyAo projectCompanyAo);

    /**
     * 批量新增
     *
     * @param projectCompanyAos {@link ProjectCompanyAo}
     * @return {@link ProjectCompany}
     */
    List<ProjectCompany> createProjectCompanyBatch(List<ProjectCompanyAo> projectCompanyAos);

    /**
     * 批量修改
     *
     * @param projectCompanyAos {@link ProjectCompanyAo}
     * @return {@link ProjectCompany}
     */
    List<ProjectCompany> updateProjectCompanyBatch(List<ProjectCompanyAo> projectCompanyAos);

    /**
     * 批量新增或修改
     * id为空则新增，不为空则修改
     * 可以自行重写
     *
     * @param projectCompanyAos {@link ProjectCompanyAo}
     * @return {@link ProjectCompany}
     */
    List<ProjectCompany> createOrUpdateProjectCompanyBatch(List<ProjectCompanyAo> projectCompanyAos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteProjectCompany(List<Long> ids);

    /**
     * excel导出
     *
     * @param projectCompanyVo {@link ProjectCompanyVo}
     */
    void exportExcel(ProjectCompanyVo projectCompanyVo);

    /**
     * excel导入
     *
     * @param multipartFile excel文件
     */
    void importExcel(MultipartFile multipartFile);

}
