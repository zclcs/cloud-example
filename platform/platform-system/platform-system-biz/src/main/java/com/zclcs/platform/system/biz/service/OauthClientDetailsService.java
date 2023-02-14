package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.entity.ao.OauthClientDetailsAo;
import com.zclcs.platform.system.api.entity.vo.OauthClientDetailsVo;

import java.util.List;

/**
 * 终端信息 Service接口
 *
 * @author zclcs
 * @date 2023-01-30 16:48:03.522
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

    /**
     * 查询（分页）
     *
     * @param basePageAo           basePageAo
     * @param oauthClientDetailsVo oauthClientDetailsVo
     * @return BasePage<OauthClientDetailsVo>
     */
    BasePage<OauthClientDetailsVo> findOauthClientDetailsPage(BasePageAo basePageAo, OauthClientDetailsVo oauthClientDetailsVo);

    /**
     * 查询（所有）
     *
     * @param oauthClientDetailsVo oauthClientDetailsVo
     * @return List<OauthClientDetailsVo>
     */
    List<OauthClientDetailsVo> findOauthClientDetailsList(OauthClientDetailsVo oauthClientDetailsVo);

    /**
     * 查询（单个）
     *
     * @param oauthClientDetailsVo oauthClientDetailsVo
     * @return OauthClientDetailsVo
     */
    OauthClientDetailsVo findOauthClientDetails(OauthClientDetailsVo oauthClientDetailsVo);

    /**
     * 统计
     *
     * @param oauthClientDetailsVo oauthClientDetailsVo
     * @return OauthClientDetailsVo
     */
    Integer countOauthClientDetails(OauthClientDetailsVo oauthClientDetailsVo);

    /**
     * 通过id获取终端信息
     *
     * @param clientId 终端id
     * @return 终端信息
     */
    OauthClientDetailsVo findById(String clientId);

    /**
     * 通过id缓存终端信息
     *
     * @param clientId 终端id
     * @return 终端信息
     */
    OauthClientDetailsVo cacheAndGetById(String clientId);

    /**
     * 通过id删除终端信息缓存
     *
     * @param clientId 终端id
     */
    void deleteCacheById(String clientId);

    /**
     * 新增
     *
     * @param oauthClientDetailsAo oauthClientDetailsAo
     * @return OauthClientDetails
     */
    OauthClientDetails createOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo);

    /**
     * 修改
     *
     * @param oauthClientDetailsAo oauthClientDetailsAo
     * @return OauthClientDetails
     */
    OauthClientDetails updateOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteOauthClientDetails(List<String> ids);

}
