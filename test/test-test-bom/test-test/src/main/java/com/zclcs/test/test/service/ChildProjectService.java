package com.zclcs.test.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.ChildProjectAo;
import com.zclcs.test.test.api.bean.entity.ChildProject;
import com.zclcs.test.test.api.bean.vo.ChildProjectVo;

import java.util.List;

/**
 * 工程信息 Service接口
 *
 * @author zclcs
 * @since 2023-08-16 14:53:25.234
 */
public interface ChildProjectService extends IService<ChildProject> {

    /**
     * 查询（分页）
     *
     * @param basePageAo     {@link BasePageAo}
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
    Integer countChildProject(ChildProjectVo childProjectVo);

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
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteChildProject(List<Long> ids);

}
