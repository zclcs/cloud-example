package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.MinioConstant;
import com.zclcs.common.core.exception.MyException;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.entity.MinioBucket;
import com.zclcs.platform.system.api.entity.MinioFile;
import com.zclcs.platform.system.api.entity.ao.MinioBucketAo;
import com.zclcs.platform.system.api.entity.vo.MinioBucketVo;
import com.zclcs.platform.system.mapper.MinioBucketMapper;
import com.zclcs.platform.system.mapper.MinioFileMapper;
import com.zclcs.platform.system.service.MinioBucketService;
import com.zclcs.platform.system.utils.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 桶 Service实现
 *
 * @author zclcs
 * @date 2021-10-18 10:37:09.922
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MinioBucketServiceImpl extends ServiceImpl<MinioBucketMapper, MinioBucket> implements MinioBucketService {

    private final MinioUtil minioUtil;
    private final MinioFileMapper minioFileMapper;

    @Override
    public BasePage<MinioBucketVo> findMinioBucketPage(BasePageAo basePageAo, MinioBucketVo minioBucketVo) {
        BasePage<MinioBucketVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<MinioBucketVo> queryWrapper = new QueryWrapper<>();
        getQueryWrapper(minioBucketVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<MinioBucketVo> findMinioBucketList(MinioBucketVo minioBucketVo) {
        QueryWrapper<MinioBucketVo> queryWrapper = new QueryWrapper<>();
        getQueryWrapper(minioBucketVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public MinioBucketVo findMinioBucket(MinioBucketVo minioBucketVo) {
        QueryWrapper<MinioBucketVo> queryWrapper = new QueryWrapper<>();
        getQueryWrapper(minioBucketVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MinioBucket createMinioBucket(MinioBucketAo minioBucketAo) {
        validateBucketName(minioBucketAo.getBucketName(), minioBucketAo.getId());
        String policy = Optional.ofNullable(minioBucketAo.getBucketPolicy()).filter(StrUtil::isNotBlank).orElse(MinioConstant.READ_ONLY);
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
        this.save(minioBucket);
        return minioBucket;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MinioBucket updateMinioBucket(MinioBucketAo minioBucketAo) {
        MinioBucket one = this.lambdaQuery().eq(MinioBucket::getId, minioBucketAo.getId()).one();
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
        this.updateById(minioBucket);
        return minioBucket;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMinioBucket(List<Long> ids) {
        if (minioFileMapper.selectCount(new QueryWrapper<MinioFile>().lambda().in(MinioFile::getBucketId, ids)) != 0) {
            throw new MyException("请先删除文件，再删除桶");
        }
        List<MinioBucket> list = this.lambdaQuery().in(MinioBucket::getId, ids).list();
        for (MinioBucket minioBucket : list) {
            try {
                minioUtil.removeBucket(minioBucket.getBucketName());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MyException("调用minio失败，" + e.getMessage());
            }
            this.removeById(minioBucket.getId());
            minioFileMapper.delete(new QueryWrapper<MinioFile>().lambda().eq(MinioFile::getBucketId, minioBucket.getId()));
        }
    }

    @Override
    public void validateBucketName(String bucketName, Long bucketId) {
        MinioBucket one = this.lambdaQuery().eq(MinioBucket::getBucketName, bucketName).one();
        if (one != null && !one.getId().equals(bucketId)) {
            throw new MyException("桶名称重复");
        }
    }

    private QueryWrapper<MinioBucketVo> getQueryWrapper(MinioBucketVo minioBucketVo) {
        QueryWrapper<MinioBucketVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.eqNotBlank(queryWrapper, "mb.bucket_name", minioBucketVo.getBucketName());
        return queryWrapper;
    }

}
