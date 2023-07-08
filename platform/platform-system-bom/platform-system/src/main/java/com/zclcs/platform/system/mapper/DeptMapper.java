package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.platform.system.api.bean.entity.Dept;
import com.zclcs.platform.system.api.bean.vo.DeptVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<DeptVo> findPageVo(BasePage<DeptVo> basePage, @Param(Constants.WRAPPER) Wrapper<DeptVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<DeptVo> findListVo(@Param(Constants.WRAPPER) Wrapper<DeptVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    DeptVo findOneVo(@Param(Constants.WRAPPER) Wrapper<DeptVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<DeptVo> ew);

}