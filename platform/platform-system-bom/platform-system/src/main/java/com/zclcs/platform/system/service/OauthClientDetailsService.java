package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.OauthClientDetailsAo;
import com.zclcs.platform.system.api.bean.entity.OauthClientDetails;
import com.zclcs.platform.system.api.bean.vo.OauthClientDetailsVo;

import java.util.List;

/**
 * 终端信息 Service接口
 *
 * @author zclcs
 * @since 2023-09-01 19:54:03.427
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

    /**
     * 查询（分页）
     *
     * @param basePageAo           {@link BasePageAo}
     * @param oauthClientDetailsVo {@link OauthClientDetailsVo}
     * @return {@link OauthClientDetailsVo}
     */
    BasePage<OauthClientDetailsVo> findOauthClientDetailsPage(BasePageAo basePageAo, OauthClientDetailsVo oauthClientDetailsVo);

    /**
     * 查询（所有）
     *
     * @param oauthClientDetailsVo {@link OauthClientDetailsVo}
     * @return {@link OauthClientDetailsVo}
     */
    List<OauthClientDetailsVo> findOauthClientDetailsList(OauthClientDetailsVo oauthClientDetailsVo);

    /**
     * 查询（单个）
     *
     * @param oauthClientDetailsVo {@link OauthClientDetailsVo}
     * @return {@link OauthClientDetailsVo}
     */
    OauthClientDetailsVo findOauthClientDetails(OauthClientDetailsVo oauthClientDetailsVo);

    /**
     * 统计
     *
     * @param oauthClientDetailsVo {@link OauthClientDetailsVo}
     * @return 统计值
     */
    Long countOauthClientDetails(OauthClientDetailsVo oauthClientDetailsVo);

    /**
     * 新增
     *
     * @param oauthClientDetailsAo {@link OauthClientDetailsAo}
     * @return {@link OauthClientDetails}
     */
    OauthClientDetails createOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo);

    /**
     * 修改
     *
     * @param oauthClientDetailsAo {@link OauthClientDetailsAo}
     * @return {@link OauthClientDetails}
     */
    OauthClientDetails updateOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteOauthClientDetails(List<String> ids);

    /**
     * 校验客户端id
     *
     * @param clientId 客户端id
     */
    void validateClientId(String clientId);
}
