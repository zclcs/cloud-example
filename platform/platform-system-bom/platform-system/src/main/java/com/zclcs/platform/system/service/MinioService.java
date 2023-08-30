package com.zclcs.platform.system.service;

import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.MinioBucketAo;
import com.zclcs.platform.system.api.bean.entity.MinioBucket;
import com.zclcs.platform.system.api.bean.entity.MinioFile;
import com.zclcs.platform.system.api.bean.vo.MinioBucketVo;
import com.zclcs.platform.system.api.bean.vo.MinioFileVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MinioService {

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

    /**
     * 查询（分页）
     *
     * @param basePageAo  basePageAo
     * @param minioFileVo minioFileVo
     * @return BasePage<MinioFileVo>
     */
    BasePage<MinioFileVo> findMinioFilePage(BasePageAo basePageAo, MinioFileVo minioFileVo);

    /**
     * 查询（所有）
     *
     * @param minioFileVo minioFileVo
     * @return List<MinioFileVo>
     */
    List<MinioFileVo> findMinioFileList(MinioFileVo minioFileVo);

    /**
     * 查询（单个）
     *
     * @param minioFileVo minioFileVo
     * @return MinioFileVo
     */
    MinioFileVo findMinioFile(MinioFileVo minioFileVo);

    /**
     * 新增
     *
     * @param multipartFile file
     * @param bucketName    桶名称
     * @return 文件
     */
    MinioFile createMinioFile(MultipartFile multipartFile, String bucketName) throws IOException;

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteMinioFile(List<String> ids);
}
