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
     * @param basePageAo    {@link BasePageAo}
     * @param minioBucketVo {@link MinioBucketVo}
     * @return {@link MinioBucketVo}
     */
    BasePage<MinioBucketVo> findMinioBucketPage(BasePageAo basePageAo, MinioBucketVo minioBucketVo);

    /**
     * 查询（所有）
     *
     * @param minioBucketVo {@link MinioBucketVo}
     * @return {@link MinioBucketVo}
     */
    List<MinioBucketVo> findMinioBucketList(MinioBucketVo minioBucketVo);

    /**
     * 查询（单个）
     *
     * @param minioBucketVo {@link MinioBucketVo}
     * @return {@link MinioBucketVo}
     */
    MinioBucketVo findMinioBucket(MinioBucketVo minioBucketVo);

    /**
     * 新增
     *
     * @param minioBucketAo {@link MinioBucketAo}
     * @return {@link MinioBucket}
     */
    MinioBucket createMinioBucket(MinioBucketAo minioBucketAo);

    /**
     * 修改
     *
     * @param minioBucketAo {@link MinioBucketAo}
     * @return {@link MinioBucket}
     */
    MinioBucket updateMinioBucket(MinioBucketAo minioBucketAo);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteMinioBucket(List<Long> ids);

    /**
     * 校验桶名称
     *
     * @param bucketName 桶名称
     * @param bucketId   桶id
     */
    void validateBucketName(String bucketName, Long bucketId);

    /**
     * 查询（分页）
     *
     * @param basePageAo  {@link BasePageAo}
     * @param minioFileVo {@link MinioFileVo}
     * @return {@link MinioFileVo}
     */
    BasePage<MinioFileVo> findMinioFilePage(BasePageAo basePageAo, MinioFileVo minioFileVo);

    /**
     * 查询（所有）
     *
     * @param minioFileVo {@link MinioFileVo}
     * @return {@link MinioFileVo}
     */
    List<MinioFileVo> findMinioFileList(MinioFileVo minioFileVo);

    /**
     * 查询（单个）
     *
     * @param minioFileVo {@link MinioFileVo}
     * @return {@link MinioFileVo}
     */
    MinioFileVo findMinioFile(MinioFileVo minioFileVo);

    /**
     * 新增
     *
     * @param bucketName 桶名称
     * @return {@link MinioFile}
     */
    MinioFile createMinioFile(MultipartFile multipartFile, String bucketName) throws IOException;

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteMinioFile(List<String> ids);
}
