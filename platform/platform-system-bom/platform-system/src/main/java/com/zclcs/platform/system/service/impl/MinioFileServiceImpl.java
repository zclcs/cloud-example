package com.zclcs.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.bean.entity.MinioFile;
import com.zclcs.platform.system.mapper.MinioFileMapper;
import com.zclcs.platform.system.service.MinioFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文件 Service实现
 *
 * @author zclcs
 * @since 2021-10-18 10:37:21.262
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinioFileServiceImpl extends ServiceImpl<MinioFileMapper, MinioFile> implements MinioFileService {

}
