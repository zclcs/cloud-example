package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.platform.system.api.entity.MinioFile;
import com.zclcs.platform.system.api.entity.vo.MinioFileVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件 Mapper
 *
 * @author zclcs
 * @date 2021-10-18 10:37:21.262
 */
public interface MinioFileMapper extends BaseMapper<MinioFile> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<MinioFileVo> findPageVo(BasePage<MinioFileVo> basePage, @Param(Constants.WRAPPER) Wrapper<MinioFileVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<MinioFileVo> findListVo(@Param(Constants.WRAPPER) Wrapper<MinioFileVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    MinioFileVo findOneVo(@Param(Constants.WRAPPER) Wrapper<MinioFileVo> ew);

}