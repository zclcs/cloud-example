package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.BlockLog;
import com.zclcs.platform.system.api.entity.ao.BlockLogAo;
import com.zclcs.platform.system.api.entity.vo.BlockLogVo;

import java.util.List;

/**
 * 黑名单拦截日志 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:40:05.798
 */
public interface BlockLogService extends IService<BlockLog> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param blockLogVo blockLogVo
     * @return BasePage<BlockLogVo>
     */
    BasePage<BlockLogVo> findBlockLogPage(BasePageAo basePageAo, BlockLogVo blockLogVo);

    /**
     * 查询（所有）
     *
     * @param blockLogVo blockLogVo
     * @return List<BlockLogVo>
     */
    List<BlockLogVo> findBlockLogList(BlockLogVo blockLogVo);

    /**
     * 查询（单个）
     *
     * @param blockLogVo blockLogVo
     * @return BlockLogVo
     */
    BlockLogVo findBlockLog(BlockLogVo blockLogVo);

    /**
     * 统计
     *
     * @param blockLogVo blockLogVo
     * @return BlockLogVo
     */
    Integer countBlockLog(BlockLogVo blockLogVo);

    /**
     * 新增
     *
     * @param blockLogAo blockLogAo
     * @return BlockLog
     */
//    @Async(MyConstant.ASYNC_POOL)
    BlockLog createBlockLog(BlockLogAo blockLogAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteBlockLog(List<Long> ids);

}
