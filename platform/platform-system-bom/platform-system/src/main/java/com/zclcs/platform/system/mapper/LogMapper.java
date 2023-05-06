package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.platform.system.api.entity.Log;
import com.zclcs.platform.system.api.entity.vo.LogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户操作日志 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
 */
public interface LogMapper extends BaseMapper<Log> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<LogVo> findPageVo(BasePage<LogVo> basePage, @Param(Constants.WRAPPER) Wrapper<LogVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<LogVo> findListVo(@Param(Constants.WRAPPER) Wrapper<LogVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    LogVo findOneVo(@Param(Constants.WRAPPER) Wrapper<LogVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<LogVo> ew);

}