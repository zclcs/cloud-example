package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.cloud.lib.security.utils.PasswordUtil;
import com.zclcs.platform.system.api.bean.ao.OauthClientDetailsAo;
import com.zclcs.platform.system.api.bean.cache.MenuCacheBean;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 终端信息 Service实现
 *
 * @author zclcs
 * @date 2023-01-30 16:48:03.522
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

    private final MenuService menuService;

    @Override
    public BasePage<OauthClientDetailsVo> findOauthClientDetailsPage(BasePageAo basePageAo, OauthClientDetailsVo oauthClientDetailsVo) {
        BasePage<OauthClientDetailsVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<OauthClientDetailsVo> queryWrapper = getQueryWrapper(oauthClientDetailsVo);
        List<MenuVo> allPermissions = menuService.findMenuList(MenuVo.builder().build());
        basePage.getList().forEach(clientDetailsVo -> {
            Optional.ofNullable(clientDetailsVo.getAuthorities()).filter(StrUtil::isNotBlank).ifPresent(s ->
                    setMenuIds(s, allPermissions, clientDetailsVo));
            clientDetailsVo.setClientSecret(null);
        });
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<OauthClientDetailsVo> findOauthClientDetailsList(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper<OauthClientDetailsVo> queryWrapper = getQueryWrapper(oauthClientDetailsVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public OauthClientDetailsVo findOauthClientDetails(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper<OauthClientDetailsVo> queryWrapper = getQueryWrapper(oauthClientDetailsVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countOauthClientDetails(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper<OauthClientDetailsVo> queryWrapper = getQueryWrapper(oauthClientDetailsVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<OauthClientDetailsVo> getQueryWrapper(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper<OauthClientDetailsVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.eqNotNull(queryWrapper, "socd.client_id", oauthClientDetailsVo.getClientId());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthClientDetails createOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo) {
        validateClientId(oauthClientDetailsAo.getClientId());
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        BeanUtil.copyProperties(oauthClientDetailsAo, oauthClientDetails);
        setAuthorities(oauthClientDetailsAo.getMenuIds(), oauthClientDetails);
        oauthClientDetails.setClientSecret(PasswordUtil.PASSWORD_ENCODER.encode(oauthClientDetails.getClientSecret()));
        this.save(oauthClientDetails);
        return oauthClientDetails;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthClientDetails updateOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo) {
        Long count = this.lambdaQuery().eq(OauthClientDetails::getClientId, oauthClientDetailsAo.getClientId()).count();
        if (count == 0L) {
            throw new MyException("该Client不存在");
        }
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        BeanUtil.copyProperties(oauthClientDetailsAo, oauthClientDetails);
        setAuthorities(oauthClientDetailsAo.getMenuIds(), oauthClientDetails);
        oauthClientDetails.setClientId(null);
        oauthClientDetails.setClientSecret(null);
        String clientId = oauthClientDetails.getClientId();
        this.lambdaUpdate().eq(OauthClientDetails::getClientId, clientId).update(oauthClientDetails);
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
        if (this.lambdaQuery().eq(OauthClientDetails::getClientId, clientId).count() > 0L) {
            throw new MyException("客户端id重复");
        }
    }

    private void setAuthorities(List<Long> menuIds, OauthClientDetails oauthClientDetails) {
        String permissions = SystemCacheUtil.getMenusByMenuIds(menuIds).stream().filter(Objects::nonNull).map(MenuCacheBean::getPerms).collect(Collectors.joining(StrUtil.COMMA));
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
