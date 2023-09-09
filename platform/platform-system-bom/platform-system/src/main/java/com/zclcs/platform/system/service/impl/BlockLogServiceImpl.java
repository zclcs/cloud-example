package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.mybatis.flex.utils.PredicateUtil;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.platform.system.api.bean.ao.BlockLogAo;
import com.zclcs.platform.system.api.bean.entity.BlockLog;
import com.zclcs.platform.system.api.bean.vo.BlockLogVo;
import com.zclcs.platform.system.mapper.BlockLogMapper;
import com.zclcs.platform.system.service.BlockLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.BlockLogTableDef.BLOCK_LOG;
import static com.zclcs.platform.system.api.bean.entity.table.RouteLogTableDef.ROUTE_LOG;

/**
 * 黑名单拦截日志 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:40:05.798
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BlockLogServiceImpl extends ServiceImpl<BlockLogMapper, BlockLog> implements BlockLogService {

    private final Ip2regionSearcher ip2regionSearcher;

    @Override
    public BasePage<BlockLogVo> findBlockLogPage(BasePageAo basePageAo, BlockLogVo blockLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(blockLogVo);
        Page<BlockLogVo> blockLogVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, BlockLogVo.class);
        return new BasePage<>(blockLogVoPage);
    }

    @Override
    public List<BlockLogVo> findBlockLogList(BlockLogVo blockLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(blockLogVo);
        return this.mapper.selectListByQueryAs(queryWrapper, BlockLogVo.class);
    }

    @Override
    public BlockLogVo findBlockLog(BlockLogVo blockLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(blockLogVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, BlockLogVo.class);
    }

    @Override
    public Long countBlockLog(BlockLogVo blockLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(blockLogVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(BlockLogVo blockLogVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        BLOCK_LOG.BLOCK_ID,
                        BLOCK_LOG.BLOCK_IP,
                        BLOCK_LOG.REQUEST_URI,
                        BLOCK_LOG.REQUEST_METHOD,
                        BLOCK_LOG.REQUEST_TIME,
                        BLOCK_LOG.LOCATION,
                        BLOCK_LOG.CREATE_AT,
                        BLOCK_LOG.UPDATE_BY
                )
                .where(BLOCK_LOG.BLOCK_IP.like(blockLogVo.getBlockIp(), If::hasText))
                .and(BLOCK_LOG.REQUEST_URI.like(blockLogVo.getRequestUri(), If::hasText))
                .and(BLOCK_LOG.REQUEST_METHOD.eq(blockLogVo.getRequestMethod(), If::hasText))
                .and(ROUTE_LOG.REQUEST_TIME.between(
                        blockLogVo.getRequestTimeFrom(),
                        blockLogVo.getRequestTimeTo(),
                        PredicateUtil.localDateBothNotNull
                ))
                .orderBy(ROUTE_LOG.REQUEST_TIME.desc())
        ;
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
    public void createBlockLogBatch(List<BlockLogAo> blockLogAos) {
        List<BlockLog> blockLogs = new ArrayList<>();
        for (BlockLogAo blockLogAo : blockLogAos) {
            BlockLog blockLog = new BlockLog();
            BeanUtil.copyProperties(blockLogAo, blockLog);
            setBlockLog(blockLog);
            blockLogs.add(blockLog);
        }
        this.saveBatch(blockLogs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlockLog(List<Long> ids) {
        this.removeByIds(ids);
    }

    private void setBlockLog(BlockLog blockLog) {
        if (StrUtil.isNotBlank(blockLog.getBlockIp())) {
            blockLog.setLocation(ip2regionSearcher.getAddress(blockLog.getBlockIp()));
        }
    }
}
