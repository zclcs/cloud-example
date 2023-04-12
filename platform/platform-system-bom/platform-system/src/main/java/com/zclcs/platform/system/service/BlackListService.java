package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.BlackList;
import com.zclcs.platform.system.api.entity.ao.BlackListAo;
import com.zclcs.platform.system.api.entity.vo.BlackListVo;

import java.util.List;

/**
 * 黑名单 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:40:14.628
 */
public interface BlackListService extends IService<BlackList> {

    /**
     * 查询（分页）
     *
     * @param basePageAo  basePageAo
     * @param blackListVo blackListVo
     * @return BasePage<BlackListVo>
     */
    BasePage<BlackListVo> findBlackListPage(BasePageAo basePageAo, BlackListVo blackListVo);

    /**
     * 查询（所有）
     *
     * @param blackListVo blackListVo
     * @return List<BlackListVo>
     */
    List<BlackListVo> findBlackListList(BlackListVo blackListVo);

    /**
     * 查询（单个）
     *
     * @param blackListVo blackListVo
     * @return BlackListVo
     */
    BlackListVo findBlackList(BlackListVo blackListVo);

    /**
     * 统计
     *
     * @param blackListVo blackListVo
     * @return BlackListVo
     */
    Integer countBlackList(BlackListVo blackListVo);

    /**
     * 缓存所有黑名单规则
     */
    void cacheAllBlackList();

    /**
     * 新增
     *
     * @param blackListAo blackListAo
     * @return BlackList
     */
    BlackList createBlackList(BlackListAo blackListAo);

    /**
     * 修改
     *
     * @param blackListAo blackListAo
     * @return BlackList
     */
    BlackList updateBlackList(BlackListAo blackListAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteBlackList(List<Long> ids);

}
