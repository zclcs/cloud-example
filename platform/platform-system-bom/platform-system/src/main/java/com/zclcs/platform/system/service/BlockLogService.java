package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.BlockLogAo;
import com.zclcs.platform.system.api.bean.entity.BlockLog;
import com.zclcs.platform.system.api.bean.vo.BlockLogVo;

import java.util.List;

/**
 * 黑名单拦截日志 Service接口
 *
 * @author zclcs
 * @since 2023-01-10 10:40:05.798
 */
public interface BlockLogService extends IService<BlockLog> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param blockLogVo {@link BlockLogVo}
     * @return {@link BlockLogVo}
     */
    BasePage<BlockLogVo> findBlockLogPage(BasePageAo basePageAo, BlockLogVo blockLogVo);

    /**
     * 查询（所有）
     *
     * @param blockLogVo {@link BlockLogVo}
     * @return {@link BlockLogVo}
     */
    List<BlockLogVo> findBlockLogList(BlockLogVo blockLogVo);

    /**
     * 查询（单个）
     *
     * @param blockLogVo {@link BlockLogVo}
     * @return {@link BlockLogVo}
     */
    BlockLogVo findBlockLog(BlockLogVo blockLogVo);

    /**
     * 统计
     *
     * @param blockLogVo {@link BlockLogVo}
     * @return 统计值
     */
    Long countBlockLog(BlockLogVo blockLogVo);

    /**
     * 新增
     *
     * @param blockLogAo {@link BlockLogAo}
     * @return {@link BlockLog}
     */
    BlockLog createBlockLog(BlockLogAo blockLogAo);

    /**
     * 批量添加
     *
     * @param blockLogAos {@link BlockLogAo}
     */
    void createBlockLogBatch(List<BlockLogAo> blockLogAos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteBlockLog(List<Long> ids);

}
