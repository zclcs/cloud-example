package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.platform.system.api.bean.ao.OauthClientDetailsAo;
import com.zclcs.platform.system.api.bean.cache.MenuCacheVo;
import com.zclcs.platform.system.api.bean.entity.OauthClientDetails;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.api.bean.vo.OauthClientDetailsVo;
import com.zclcs.platform.system.mapper.OauthClientDetailsMapper;
import com.zclcs.platform.system.service.MenuService;
import com.zclcs.platform.system.service.OauthClientDetailsService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.zclcs.platform.system.api.bean.entity.table.OauthClientDetailsTableDef.OAUTH_CLIENT_DETAILS;

/**
 * 终端信息 Service实现
 *
 * @author zclcs
 * @since 2023-01-30 16:48:03.522
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

    private final MenuService menuService;

    @Override
    public BasePage<OauthClientDetailsVo> findOauthClientDetailsPage(BasePageAo basePageAo, OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper queryWrapper = getQueryWrapper(oauthClientDetailsVo);
        Page<OauthClientDetailsVo> oauthClientDetailsVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(),
                queryWrapper, OauthClientDetailsVo.class);
        List<MenuVo> allPermissions = menuService.findMenuList(MenuVo.builder().build());
        oauthClientDetailsVoPage.getRecords().forEach(clientDetailsVo -> {
            Optional.ofNullable(clientDetailsVo.getAuthorities()).filter(StrUtil::isNotBlank).ifPresent(s ->
                    setMenuIds(s, allPermissions, clientDetailsVo));
            clientDetailsVo.setClientSecret(null);
        });
        return new BasePage<>(oauthClientDetailsVoPage);
    }

    @Override
    public List<OauthClientDetailsVo> findOauthClientDetailsList(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper queryWrapper = getQueryWrapper(oauthClientDetailsVo);
        return this.mapper.selectListByQueryAs(queryWrapper, OauthClientDetailsVo.class);
    }

    @Override
    public OauthClientDetailsVo findOauthClientDetails(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper queryWrapper = getQueryWrapper(oauthClientDetailsVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, OauthClientDetailsVo.class);
    }

    @Override
    public Long countOauthClientDetails(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper queryWrapper = getQueryWrapper(oauthClientDetailsVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        OAUTH_CLIENT_DETAILS.CLIENT_ID,
                        OAUTH_CLIENT_DETAILS.RESOURCE_IDS,
                        OAUTH_CLIENT_DETAILS.CLIENT_SECRET,
                        OAUTH_CLIENT_DETAILS.SCOPE,
                        OAUTH_CLIENT_DETAILS.AUTHORIZED_GRANT_TYPES,
                        OAUTH_CLIENT_DETAILS.WEB_SERVER_REDIRECT_URI,
                        OAUTH_CLIENT_DETAILS.AUTHORITIES,
                        OAUTH_CLIENT_DETAILS.ACCESS_TOKEN_VALIDITY,
                        OAUTH_CLIENT_DETAILS.REFRESH_TOKEN_VALIDITY,
                        OAUTH_CLIENT_DETAILS.ADDITIONAL_INFORMATION,
                        OAUTH_CLIENT_DETAILS.AUTOAPPROVE
                )
                .where(OAUTH_CLIENT_DETAILS.CLIENT_ID.eq(oauthClientDetailsVo.getClientId()))
        ;
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthClientDetails createOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo) {
        validateClientId(oauthClientDetailsAo.getClientId());
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        BeanUtil.copyProperties(oauthClientDetailsAo, oauthClientDetails);
        setAuthorities(oauthClientDetailsAo.getMenuIds(), oauthClientDetails);
        oauthClientDetails.setClientSecret(BCrypt.hashpw(oauthClientDetails.getClientSecret()));
        this.save(oauthClientDetails);
        return oauthClientDetails;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthClientDetails updateOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo) {
        long count = this.count(new QueryWrapper().where(OAUTH_CLIENT_DETAILS.CLIENT_ID.eq(oauthClientDetailsAo.getClientId())));
        if (count == 0L) {
            throw new MyException("该Client不存在");
        }
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        BeanUtil.copyProperties(oauthClientDetailsAo, oauthClientDetails);
        setAuthorities(oauthClientDetailsAo.getMenuIds(), oauthClientDetails);
        oauthClientDetails.setClientId(null);
        oauthClientDetails.setClientSecret(null);
        String clientId = oauthClientDetails.getClientId();
        this.update(oauthClientDetails, new QueryWrapper().where(OAUTH_CLIENT_DETAILS.CLIENT_ID.eq(clientId)));
        SystemCacheUtil.deleteOauthClientDetailsCache(clientId);
        return oauthClientDetails;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOauthClientDetails(List<String> ids) {
        this.removeByIds(ids);
        SystemCacheUtil.deleteOauthClientDetailsCache(ids.toArray());
    }

    @Override
    public void validateClientId(String clientId) {
        if (this.count(new QueryWrapper().where(OAUTH_CLIENT_DETAILS.CLIENT_ID.eq(clientId))) > 0L) {
            throw new FieldException("客户端id重复");
        }
    }

    private void setAuthorities(List<Long> menuIds, OauthClientDetails oauthClientDetails) {
        String permissions = SystemCacheUtil.getMenusByMenuIds(menuIds).stream().filter(Objects::nonNull).map(MenuCacheVo::getPerms).collect(Collectors.joining(StrUtil.COMMA));
        oauthClientDetails.setAuthorities(permissions);
    }

    private void setMenuIds(String authorities, List<MenuVo> systemMenuVos, OauthClientDetailsVo oauthClientDetailsVo) {
        List<String> collect = Arrays.stream(authorities.split(StrUtil.COMMA)).toList();
        List<Long> menuIds = new ArrayList<>();
        for (String a : collect) {
            MenuVo perms = CollectionUtil.findOneByField(systemMenuVos, "perms", a);
            menuIds.add(perms.getMenuId());
        }
        oauthClientDetailsVo.setMenuIds(menuIds);
    }
}
