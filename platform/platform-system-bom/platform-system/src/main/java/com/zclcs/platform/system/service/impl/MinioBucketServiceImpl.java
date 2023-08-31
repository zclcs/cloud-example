package com.zclcs.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.bean.entity.MinioBucket;
import com.zclcs.platform.system.mapper.MinioBucketMapper;
import com.zclcs.platform.system.service.MinioBucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 桶 Service实现
 *
 * @author zclcs
 * @since 2021-10-18 10:37:09.922
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinioBucketServiceImpl extends ServiceImpl<MinioBucketMapper, MinioBucket> implements MinioBucketService {

}
