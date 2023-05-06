package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.platform.system.api.entity.RateLimitRule;
import com.zclcs.platform.system.api.entity.vo.RateLimitRuleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 限流规则 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:49.113
 */
public interface RateLimitRuleMapper extends BaseMapper<RateLimitRule> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<RateLimitRuleVo> findPageVo(BasePage<RateLimitRuleVo> basePage, @Param(Constants.WRAPPER) Wrapper<RateLimitRuleVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<RateLimitRuleVo> findListVo(@Param(Constants.WRAPPER) Wrapper<RateLimitRuleVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    RateLimitRuleVo findOneVo(@Param(Constants.WRAPPER) Wrapper<RateLimitRuleVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<RateLimitRuleVo> ew);

}