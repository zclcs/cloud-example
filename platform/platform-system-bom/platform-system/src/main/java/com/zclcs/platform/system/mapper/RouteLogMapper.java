package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.platform.system.api.entity.RouteLog;
import com.zclcs.platform.system.api.entity.vo.RouteLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 网关转发日志 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:40:09.958
 */
public interface RouteLogMapper extends BaseMapper<RouteLog> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<RouteLogVo> findPageVo(BasePage<RouteLogVo> basePage, @Param(Constants.WRAPPER) Wrapper<RouteLogVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<RouteLogVo> findListVo(@Param(Constants.WRAPPER) Wrapper<RouteLogVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    RouteLogVo findOneVo(@Param(Constants.WRAPPER) Wrapper<RouteLogVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<RouteLogVo> ew);

}