package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.BlackListAo;
import com.zclcs.platform.system.api.bean.entity.BlackList;
import com.zclcs.platform.system.api.bean.vo.BlackListVo;

import java.util.List;

/**
 * 黑名单 Service接口
 *
 * @author zclcs
 * @since 2023-09-01 19:53:59.035
 */
public interface BlackListService extends IService<BlackList> {

    /**
     * 查询（分页）
     *
     * @param basePageAo  {@link BasePageAo}
     * @param blackListVo {@link BlackListVo}
     * @return {@link BlackListVo}
     */
    BasePage<BlackListVo> findBlackListPage(BasePageAo basePageAo, BlackListVo blackListVo);

    /**
     * 查询（所有）
     *
     * @param blackListVo {@link BlackListVo}
     * @return {@link BlackListVo}
     */
    List<BlackListVo> findBlackListList(BlackListVo blackListVo);

    /**
     * 查询（单个）
     *
     * @param blackListVo {@link BlackListVo}
     * @return {@link BlackListVo}
     */
    BlackListVo findBlackList(BlackListVo blackListVo);

    /**
     * 统计
     *
     * @param blackListVo {@link BlackListVo}
     * @return 统计值
     */
    Long countBlackList(BlackListVo blackListVo);

    /**
     * 缓存所有黑名单规则
     */
    void cacheAllBlackList();

    /**
     * 新增
     *
     * @param blackListAo {@link BlackListAo}
     * @return {@link BlackList}
     */
    BlackList createBlackList(BlackListAo blackListAo);

    /**
     * 修改
     *
     * @param blackListAo {@link BlackListAo}
     * @return {@link BlackList}
     */
    BlackList updateBlackList(BlackListAo blackListAo);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteBlackList(List<Long> ids);

}
