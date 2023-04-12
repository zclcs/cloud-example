package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.platform.system.api.entity.RateLimitLog;
import com.zclcs.platform.system.api.entity.vo.RateLimitLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 限流拦截日志 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:53.040
 */
public interface RateLimitLogMapper extends BaseMapper<RateLimitLog> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<RateLimitLogVo> findPageVo(BasePage<RateLimitLogVo> basePage, @Param(Constants.WRAPPER) Wrapper<RateLimitLogVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<RateLimitLogVo> findListVo(@Param(Constants.WRAPPER) Wrapper<RateLimitLogVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    RateLimitLogVo findOneVo(@Param(Constants.WRAPPER) Wrapper<RateLimitLogVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<RateLimitLogVo> ew);

}