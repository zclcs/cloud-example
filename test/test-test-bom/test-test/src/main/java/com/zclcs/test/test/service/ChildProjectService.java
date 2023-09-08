package com.zclcs.test.test.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.ChildProjectAo;
import com.zclcs.test.test.api.bean.entity.ChildProject;
import com.zclcs.test.test.api.bean.vo.ChildProjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 工程信息 Service接口
 *
 * @author zclcs
 * @since 2023-09-08 16:48:53.770
 */
public interface ChildProjectService extends IService<ChildProject> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param childProjectVo {@link ChildProjectVo}
     * @return {@link ChildProjectVo}
     */
    BasePage<ChildProjectVo> findChildProjectPage(BasePageAo basePageAo, ChildProjectVo childProjectVo);

    /**
     * 查询（所有）
     *
     * @param childProjectVo {@link ChildProjectVo}
     * @return {@link ChildProjectVo}
     */
    List<ChildProjectVo> findChildProjectList(ChildProjectVo childProjectVo);

    /**
     * 查询（单个）
     *
     * @param childProjectVo {@link ChildProjectVo}
     * @return {@link ChildProjectVo}
     */
    ChildProjectVo findChildProject(ChildProjectVo childProjectVo);

    /**
     * 统计
     *
     * @param childProjectVo {@link ChildProjectVo}
     * @return 统计值
     */
    Long countChildProject(ChildProjectVo childProjectVo);

    /**
     * 新增
     *
     * @param childProjectAo {@link ChildProjectAo}
     * @return {@link ChildProject}
     */
    ChildProject createChildProject(ChildProjectAo childProjectAo);

    /**
     * 修改
     *
     * @param childProjectAo {@link ChildProjectAo}
     * @return {@link ChildProject}
     */
    ChildProject updateChildProject(ChildProjectAo childProjectAo);

    /**
     * 新增或修改
     *
     * @param childProjectAo {@link ChildProjectAo}
     * @return {@link ChildProject}
     */
    ChildProject createOrUpdateChildProject(ChildProjectAo childProjectAo);

    /**
     * 批量新增
     *
     * @param childProjectAos {@link ChildProjectAo}
     * @return {@link ChildProject}
     */
    List<ChildProject> createChildProjectBatch(List<ChildProjectAo> childProjectAos);

    /**
     * 批量修改
     *
     * @param childProjectAos {@link ChildProjectAo}
     * @return {@link ChildProject}
     */
    List<ChildProject> updateChildProjectBatch(List<ChildProjectAo> childProjectAos);

    /**
     * 批量新增或修改
     * id为空则新增，不为空则修改
     * 可以自行重写
     *
     * @param childProjectAos {@link ChildProjectAo}
     * @return {@link ChildProject}
     */
    List<ChildProject> createOrUpdateChildProjectBatch(List<ChildProjectAo> childProjectAos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteChildProject(List<Long> ids);

    /**
     * excel导出
     *
     * @param childProjectVo {@link ChildProjectVo}
     */
    void exportExcel(ChildProjectVo childProjectVo);

    /**
     * excel导入
     *
     * @param multipartFile excel文件
     */
    void importExcel(MultipartFile multipartFile);

}
