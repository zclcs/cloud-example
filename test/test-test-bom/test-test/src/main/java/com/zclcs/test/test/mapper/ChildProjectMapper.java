package com.zclcs.test.test.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.test.test.api.bean.entity.ChildProject;
import com.zclcs.test.test.api.bean.vo.ChildProjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工程信息 Mapper
 *
 * @author zclcs
 * @date 2023-08-16 14:53:25.234
 */
public interface ChildProjectMapper extends BaseMapper<ChildProject> {

    /**
     * 分页
     *
     * @param basePage {@link BasePage}
     * @param ew       {@link ChildProjectVo}
     * @return {@link ChildProjectVo}
     */
    BasePage<ChildProjectVo> findPageVo(BasePage<ChildProjectVo> basePage, @Param(Constants.WRAPPER) Wrapper<ChildProjectVo> ew);

    /**
     * 查找集合
     *
     * @param ew {@link ChildProjectVo}
     * @return {@link ChildProjectVo}
     */
    List<ChildProjectVo> findListVo(@Param(Constants.WRAPPER) Wrapper<ChildProjectVo> ew);

    /**
     * 查找单个
     *
     * @param ew {@link ChildProjectVo}
     * @return {@link ChildProjectVo}
     */
    ChildProjectVo findOneVo(@Param(Constants.WRAPPER) Wrapper<ChildProjectVo> ew);

    /**
     * 统计
     *
     * @param ew {@link ChildProjectVo}
     * @return 统计值
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<ChildProjectVo> ew);

}