package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.bean.entity.MinioBucket;
import com.zclcs.platform.system.api.bean.entity.MinioFile;
import com.zclcs.platform.system.api.bean.ao.MinioBucketAo;
import com.zclcs.platform.system.api.bean.vo.FileUploadVo;
import com.zclcs.platform.system.api.bean.vo.MinioFileVo;
import com.zclcs.platform.system.mapper.MinioFileMapper;
import com.zclcs.platform.system.service.MinioBucketService;
import com.zclcs.platform.system.service.MinioFileService;
import com.zclcs.platform.system.utils.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * 文件 Service实现
 *
 * @author zclcs
 * @date 2021-10-18 10:37:21.262
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MinioFileServiceImpl extends ServiceImpl<MinioFileMapper, MinioFile> implements MinioFileService {

    private final MinioUtil minioUtil;
    private final MinioBucketService minioBucketService;

    @Override
    public BasePage<MinioFileVo> findMinioFilePage(BasePageAo basePageAo, MinioFileVo minioFileVo) {
        BasePage<MinioFileVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<MinioFileVo> queryWrapper = getQueryWrapper(minioFileVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<MinioFileVo> findMinioFileList(MinioFileVo minioFileVo) {
        QueryWrapper<MinioFileVo> queryWrapper = getQueryWrapper(minioFileVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public MinioFileVo findMinioFile(MinioFileVo minioFileVo) {
        QueryWrapper<MinioFileVo> queryWrapper = getQueryWrapper(minioFileVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MinioFile createMinioFile(MultipartFile multipartFile, String bucketName) {
        String defaultBucket = Optional.ofNullable(bucketName).filter(StrUtil::isNotBlank).orElse("default");
        Long bucketId;
        FileUploadVo fileUploadVo;
        if (minioBucketService.lambdaQuery().eq(MinioBucket::getBucketName, defaultBucket).count() == 0L) {
            try {
                bucketId = minioBucketService.createMinioBucket(MinioBucketAo.builder().bucketName(defaultBucket).build()).getId();
                fileUploadVo = minioUtil.uploadFile(multipartFile, defaultBucket);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MyException("调用minio失败，" + e.getMessage());
            }
        } else {
            MinioBucket one = minioBucketService.lambdaQuery().eq(MinioBucket::getBucketName, defaultBucket).one();
            try {
                bucketId = one.getId();
                fileUploadVo = minioUtil.uploadFile(multipartFile, defaultBucket);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MyException("调用minio失败，" + e.getMessage());
            }
        }
        MinioFile minioFile = new MinioFile();
        BeanUtil.copyProperties(fileUploadVo, minioFile);
        minioFile.setContentType(multipartFile.getContentType());
        minioFile.setBucketId(bucketId);
        this.save(minioFile);
        return minioFile;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMinioFile(List<String> ids) {
        for (String id : ids) {
            MinioFileVo minioFile = this.findMinioFile(MinioFileVo.builder().id(id).build());
            try {
                minioUtil.removeObject(minioFile.getBucketName(), minioFile.getFileName());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MyException("调用minio失败，" + e.getMessage());
            }
            this.removeById(id);
        }
    }

    private QueryWrapper<MinioFileVo> getQueryWrapper(MinioFileVo minioFileVo) {
        QueryWrapper<MinioFileVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.eqNotBlank(queryWrapper, "mf.id", minioFileVo.getId());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "mf.original_file_name", minioFileVo.getOriginalFileName());
        queryWrapper.orderByDesc("mf.create_at");
        return queryWrapper;
    }
}
