package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.MinioBucket;
import com.zclcs.platform.system.api.entity.ao.MinioBucketAo;
import com.zclcs.platform.system.api.entity.vo.MinioBucketVo;

import java.util.List;

/**
 * 桶 Service接口
 *
 * @author zclcs
 * @date 2021-10-18 10:37:09.922
 */
public interface MinioBucketService extends IService<MinioBucket> {

    /**
     * 查询（分页）
     *
     * @param basePageAo    basePageAo
     * @param minioBucketVo minioBucketVo
     * @return BasePage<MinioBucketVo>
     */
    BasePage<MinioBucketVo> findMinioBucketPage(BasePageAo basePageAo, MinioBucketVo minioBucketVo);

    /**
     * 查询（所有）
     *
     * @param minioBucketVo minioBucketVo
     * @return List<MinioBucketVo>
     */
    List<MinioBucketVo> findMinioBucketList(MinioBucketVo minioBucketVo);

    /**
     * 查询（单个）
     *
     * @param minioBucketVo minioBucketVo
     * @return MinioBucketVo
     */
    MinioBucketVo findMinioBucket(MinioBucketVo minioBucketVo);

    /**
     * 新增
     *
     * @param minioBucketAo MinioBucketAo
     * @return 桶
     */
    MinioBucket createMinioBucket(MinioBucketAo minioBucketAo);

    /**
     * 修改
     *
     * @param minioBucketAo MinioBucketAo
     * @return 桶
     */
    MinioBucket updateMinioBucket(MinioBucketAo minioBucketAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteMinioBucket(List<Long> ids);

    void validateBucketName(String bucketName, Long bucketId);
}
