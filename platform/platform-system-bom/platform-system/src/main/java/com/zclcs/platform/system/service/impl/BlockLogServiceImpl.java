package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.utils.AddressUtil;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.entity.BlockLog;
import com.zclcs.platform.system.api.entity.ao.BlockLogAo;
import com.zclcs.platform.system.api.entity.vo.BlockLogVo;
import com.zclcs.platform.system.mapper.BlockLogMapper;
import com.zclcs.platform.system.service.BlockLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 黑名单拦截日志 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:40:05.798
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BlockLogServiceImpl extends ServiceImpl<BlockLogMapper, BlockLog> implements BlockLogService {

    @Override
    public BasePage<BlockLogVo> findBlockLogPage(BasePageAo basePageAo, BlockLogVo blockLogVo) {
        BasePage<BlockLogVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<BlockLogVo> queryWrapper = getQueryWrapper(blockLogVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<BlockLogVo> findBlockLogList(BlockLogVo blockLogVo) {
        QueryWrapper<BlockLogVo> queryWrapper = getQueryWrapper(blockLogVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public BlockLogVo findBlockLog(BlockLogVo blockLogVo) {
        QueryWrapper<BlockLogVo> queryWrapper = getQueryWrapper(blockLogVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countBlockLog(BlockLogVo blockLogVo) {
        QueryWrapper<BlockLogVo> queryWrapper = getQueryWrapper(blockLogVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<BlockLogVo> getQueryWrapper(BlockLogVo blockLogVo) {
        QueryWrapper<BlockLogVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sbl.block_ip", blockLogVo.getBlockIp());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sbl.request_uri", blockLogVo.getRequestUri());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sbl.request_method", blockLogVo.getRequestMethod());
        QueryWrapperUtil.betweenDateAddTimeNotBlank(queryWrapper, "sbl.create_at", blockLogVo.getCreateTimeFrom(), blockLogVo.getCreateTimeTo());
        queryWrapper.orderByDesc("sbl.create_at");
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlockLog createBlockLog(BlockLogAo blockLogAo) {
        BlockLog blockLog = new BlockLog();
        BeanUtil.copyProperties(blockLogAo, blockLog);
        setBlockLog(blockLog);
        this.save(blockLog);
        return blockLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlockLog(List<Long> ids) {
        this.removeByIds(ids);
    }

    private void setBlockLog(BlockLog blockLog) {
        if (StrUtil.isNotBlank(blockLog.getBlockIp())) {
            blockLog.setLocation(AddressUtil.getCityInfo(blockLog.getBlockIp()));
        }
    }
}
