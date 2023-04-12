package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.entity.vo.OauthClientDetailsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 终端信息 Mapper
 *
 * @author zclcs
 * @date 2023-01-30 16:48:03.522
 */
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<OauthClientDetailsVo> findPageVo(BasePage<OauthClientDetailsVo> basePage, @Param(Constants.WRAPPER) Wrapper<OauthClientDetailsVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<OauthClientDetailsVo> findListVo(@Param(Constants.WRAPPER) Wrapper<OauthClientDetailsVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    OauthClientDetailsVo findOneVo(@Param(Constants.WRAPPER) Wrapper<OauthClientDetailsVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<OauthClientDetailsVo> ew);

}