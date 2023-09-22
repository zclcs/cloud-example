package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.Minio;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.core.exception.FileUploadException;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.properties.GlobalProperties;
import com.zclcs.common.minio.starter.bean.vo.FileUploadVo;
import com.zclcs.common.minio.starter.utils.MinioUtil;
import com.zclcs.platform.system.api.bean.ao.MinioBucketAo;
import com.zclcs.platform.system.api.bean.entity.MinioBucket;
import com.zclcs.platform.system.api.bean.entity.MinioFile;
import com.zclcs.platform.system.api.bean.vo.MinioBucketVo;
import com.zclcs.platform.system.api.bean.vo.MinioFileVo;
import com.zclcs.platform.system.service.MinioBucketService;
import com.zclcs.platform.system.service.MinioFileService;
import com.zclcs.platform.system.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.zclcs.platform.system.api.bean.entity.table.MinioBucketTableDef.MINIO_BUCKET;
import static com.zclcs.platform.system.api.bean.entity.table.MinioFileTableDef.MINIO_FILE;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioUtil minioUtil;
    private final MinioBucketService minioBucketService;
    private final MinioFileService minioFileService;
    private final GlobalProperties globalProperties;

    @Override
    public BasePage<MinioBucketVo> findMinioBucketPage(BasePageAo basePageAo, MinioBucketVo minioBucketVo) {
        QueryWrapper queryWrapper = getQueryWrapper(minioBucketVo);
        Page<MinioBucketVo> minioBucketPage = minioBucketService.getMapper().paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, MinioBucketVo.class);
        return new BasePage<>(minioBucketPage);
    }

    @Override
    public List<MinioBucketVo> findMinioBucketList(MinioBucketVo minioBucketVo) {
        QueryWrapper queryWrapper = getQueryWrapper(minioBucketVo);
        return minioBucketService.getMapper().selectListByQueryAs(queryWrapper, MinioBucketVo.class);
    }

    @Override
    public MinioBucketVo findMinioBucket(MinioBucketVo minioBucketVo) {
        QueryWrapper queryWrapper = getQueryWrapper(minioBucketVo);
        return minioBucketService.getMapper().selectOneByQueryAs(queryWrapper, MinioBucketVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MinioBucket createMinioBucket(MinioBucketAo minioBucketAo) {
        validateBucketName(minioBucketAo.getBucketName(), minioBucketAo.getId());
        String policy = Optional.ofNullable(minioBucketAo.getBucketPolicy()).filter(StrUtil::isNotBlank).orElse(Minio.READ_ONLY);
        MinioBucket minioBucket = new MinioBucket();
        BeanUtil.copyProperties(minioBucketAo, minioBucket);
        minioBucket.setBucketPolicy(policy);
        try {
            minioUtil.createBucket(minioBucket.getBucketName());
            minioUtil.setBucketPolicy(minioBucket.getBucketName(), policy);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("调用minio失败，" + e.getMessage());
        }
        minioBucketService.save(minioBucket);
        return minioBucket;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MinioBucket updateMinioBucket(MinioBucketAo minioBucketAo) {
        MinioBucket one = minioBucketService.getOne(new QueryWrapper().where(MINIO_BUCKET.ID.eq(minioBucketAo.getId())));
        if (one == null) {
            throw new MyException("桶不存在");
        }
        MinioBucket minioBucket = new MinioBucket();
        BeanUtil.copyProperties(minioBucketAo, minioBucket);
        try {
            minioUtil.setBucketPolicy(minioBucket.getBucketName(), minioBucketAo.getBucketPolicy());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("调用minio失败，" + e.getMessage());
        }
        minioBucket.setBucketName(null);
        minioBucketService.updateById(minioBucket);
        return minioBucket;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMinioBucket(List<Long> ids) {
        if (minioFileService.count(new QueryWrapper().where(MINIO_FILE.BUCKET_ID.in(ids))) != 0) {
            throw new MyException("请先删除文件，再删除桶");
        }
        List<MinioBucket> list = minioBucketService.list(new QueryWrapper().where(MINIO_BUCKET.ID.in(ids)));
        for (MinioBucket minioBucket : list) {
            try {
                minioUtil.removeBucket(minioBucket.getBucketName());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MyException("调用minio失败，" + e.getMessage());
            }
            minioBucketService.removeById(minioBucket.getId());
        }
    }

    @Override
    public void validateBucketName(String bucketName, Long bucketId) {
        MinioBucket one = minioBucketService.getOne(new QueryWrapper().where(MINIO_BUCKET.BUCKET_NAME.eq(bucketName)));
        if (one != null && !one.getId().equals(bucketId)) {
            throw new FieldException("桶名称重复");
        }
    }

    private QueryWrapper getQueryWrapper(MinioBucketVo minioBucketVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        MINIO_BUCKET.ID,
                        MINIO_BUCKET.BUCKET_NAME,
                        MINIO_BUCKET.BUCKET_POLICY,
                        MINIO_BUCKET.CREATE_AT,
                        MINIO_BUCKET.UPDATE_AT
                )
                .where(MINIO_BUCKET.BUCKET_NAME.eq(minioBucketVo.getBucketName()))
        ;
        return queryWrapper;
    }

    @Override
    public BasePage<MinioFileVo> findMinioFilePage(BasePageAo basePageAo, MinioFileVo minioFileVo) {
        QueryWrapper queryWrapper = getQueryWrapper(minioFileVo);
        Page<MinioFileVo> minioFileVoPage = minioFileService.getMapper().paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, MinioFileVo.class);
        return new BasePage<>(minioFileVoPage);
    }

    @Override
    public List<MinioFileVo> findMinioFileList(MinioFileVo minioFileVo) {
        QueryWrapper queryWrapper = getQueryWrapper(minioFileVo);
        return minioFileService.getMapper().selectListByQueryAs(queryWrapper, MinioFileVo.class);
    }

    @Override
    public MinioFileVo findMinioFile(MinioFileVo minioFileVo) {
        QueryWrapper queryWrapper = getQueryWrapper(minioFileVo);
        return minioFileService.getMapper().selectOneByQueryAs(queryWrapper, MinioFileVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MinioFile createMinioFile(MultipartFile multipartFile, String bucketName) throws IOException {
        String type = FileTypeUtil.getType(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        if (!globalProperties.getAllowFileType().contains(type)) {
            throw new FileUploadException("不支持上传该类型文件");
        }
        String defaultBucket = Optional.ofNullable(bucketName).filter(StrUtil::isNotBlank).orElse("default");
        Long bucketId;
        FileUploadVo fileUploadVo;
        if (minioBucketService.count(new QueryWrapper().where(MINIO_BUCKET.BUCKET_NAME.eq(defaultBucket))) == 0L) {
            try {
                bucketId = this.createMinioBucket(MinioBucketAo.builder().bucketName(defaultBucket).build()).getId();
                fileUploadVo = minioUtil.uploadFile(multipartFile, defaultBucket);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new FileUploadException("调用minio失败，" + e.getMessage());
            }
        } else {
            MinioBucket one = minioBucketService.getOne(new QueryWrapper().where(MINIO_BUCKET.BUCKET_NAME.eq(defaultBucket)));
            try {
                bucketId = one.getId();
                fileUploadVo = minioUtil.uploadFile(multipartFile, defaultBucket);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new FileUploadException("调用minio失败，" + e.getMessage());
            }
        }
        MinioFile minioFile = new MinioFile();
        BeanUtil.copyProperties(fileUploadVo, minioFile);
        minioFile.setContentType(multipartFile.getContentType());
        minioFile.setBucketId(bucketId);
        minioFileService.save(minioFile);
        return minioFile;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMinioFile(List<String> ids) {
        for (String id : ids) {
            MinioFileVo minioFile = this.findMinioFile(MinioFileVo.builder().id(id).build());
            if (minioFile == null) {
                return;
            }
            try {
                minioUtil.removeObject(minioFile.getBucketName(), minioFile.getFileName());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new FileUploadException("调用minio失败，" + e.getMessage());
            }
            minioFileService.removeById(id);
        }
    }

    private QueryWrapper getQueryWrapper(MinioFileVo minioFileVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        MINIO_FILE.ID,
                        MINIO_FILE.BUCKET_ID,
                        MINIO_BUCKET.BUCKET_NAME,
                        MINIO_FILE.FILE_NAME,
                        MINIO_FILE.ORIGINAL_FILE_NAME,
                        MINIO_FILE.CONTENT_TYPE,
                        MINIO_FILE.FILE_PATH,
                        MINIO_FILE.CREATE_AT,
                        MINIO_FILE.UPDATE_AT
                )
                .from(MINIO_FILE)
                .innerJoin(MINIO_BUCKET).on(MINIO_FILE.BUCKET_ID.eq(MINIO_BUCKET.ID))
                .where(MINIO_FILE.ID.eq(minioFileVo.getId()))
                .and(MINIO_FILE.ORIGINAL_FILE_NAME.like(minioFileVo.getOriginalFileName(), If::hasText))
                .orderBy(MINIO_FILE.CREATE_AT.desc())
        ;
        return queryWrapper;
    }
}
