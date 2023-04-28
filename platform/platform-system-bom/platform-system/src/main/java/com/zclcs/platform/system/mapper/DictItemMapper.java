package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.dict.entity.DictItem;
import com.zclcs.platform.system.api.entity.vo.DictItemVo;
import com.zclcs.platform.system.api.entity.vo.DictVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典项 Mapper
 *
 * @author zclcs
 * @date 2023-03-06 10:56:41.301
 */
public interface DictItemMapper extends BaseMapper<DictItem> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<DictItemVo> findPageVo(BasePage<DictItemVo> basePage, @Param(Constants.WRAPPER) Wrapper<DictItemVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<DictItemVo> findListVo(@Param(Constants.WRAPPER) Wrapper<DictItemVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    DictItemVo findOneVo(@Param(Constants.WRAPPER) Wrapper<DictItemVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<DictItemVo> ew);

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<DictVo> findPageDictVo(BasePage<DictVo> basePage, @Param(Constants.WRAPPER) Wrapper<DictVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<DictVo> findListDictVo(@Param(Constants.WRAPPER) Wrapper<DictVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    DictVo findOneDictVo(@Param(Constants.WRAPPER) Wrapper<DictVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countDictVo(@Param(Constants.WRAPPER) Wrapper<DictVo> ew);

}