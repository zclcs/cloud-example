package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.entity.MinioFile;
import com.zclcs.platform.system.api.bean.vo.MinioFileVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件 Service接口
 *
 * @author zclcs
 * @since 2021-10-18 10:37:21.262
 */
public interface MinioFileService extends IService<MinioFile> {

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
