package com.zclcs.test.test.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.test.test.api.bean.entity.Project;
import com.zclcs.test.test.api.bean.vo.ProjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目信息 Mapper
 *
 * @author zclcs
 * @date 2023-08-16 14:53:10.430
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 分页
     *
     * @param basePage {@link BasePage}
     * @param ew       {@link ProjectVo}
     * @return {@link ProjectVo}
     */
    BasePage<ProjectVo> findPageVo(BasePage<ProjectVo> basePage, @Param(Constants.WRAPPER) Wrapper<ProjectVo> ew);

    /**
     * 查找集合
     *
     * @param ew {@link ProjectVo}
     * @return {@link ProjectVo}
     */
    List<ProjectVo> findListVo(@Param(Constants.WRAPPER) Wrapper<ProjectVo> ew);

    /**
     * 查找单个
     *
     * @param ew {@link ProjectVo}
     * @return {@link ProjectVo}
     */
    ProjectVo findOneVo(@Param(Constants.WRAPPER) Wrapper<ProjectVo> ew);

    /**
     * 统计
     *
     * @param ew {@link ProjectVo}
     * @return 统计值
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<ProjectVo> ew);

}