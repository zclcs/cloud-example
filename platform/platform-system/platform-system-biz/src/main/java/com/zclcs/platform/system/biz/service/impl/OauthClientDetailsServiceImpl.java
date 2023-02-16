package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.exception.MyException;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.common.security.starter.utils.PasswordUtil;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.entity.ao.OauthClientDetailsAo;
import com.zclcs.platform.system.api.entity.vo.MenuVo;
import com.zclcs.platform.system.api.entity.vo.OauthClientDetailsVo;
import com.zclcs.platform.system.biz.mapper.OauthClientDetailsMapper;
import com.zclcs.platform.system.biz.service.MenuService;
import com.zclcs.platform.system.biz.service.OauthClientDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private final RedisService redisService;

    private final ObjectMapper objectMapper;

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

    //    @SneakyThrows(JsonProcessingException.class)
    @Override
    public OauthClientDetailsVo findById(String clientId) {
        Object obj = redisService.get(RedisCachePrefixConstant.CLIENT_DETAILS_PREFIX + clientId);
//        log.info("client_details {}", objectMapper.writeValueAsString(obj));
        if (obj == null) {
            synchronized (this) {
                // 再查一次，防止上个已经抢到锁的线程已经更新过了
                obj = redisService.get(RedisCachePrefixConstant.CLIENT_DETAILS_PREFIX + clientId);
                if (obj != null) {
                    return (OauthClientDetailsVo) obj;
                }
                return cacheAndGetById(clientId);
            }
        }
        return (OauthClientDetailsVo) obj;
    }

    @Override
    public OauthClientDetailsVo cacheAndGetById(String clientId) {
        OauthClientDetailsVo oauthClientDetailsVo = this.findOauthClientDetails(OauthClientDetailsVo.builder().clientId(clientId).build());
        if (oauthClientDetailsVo == null) {
            return null;
        }
        redisService.set(RedisCachePrefixConstant.CLIENT_DETAILS_PREFIX + clientId, oauthClientDetailsVo);
        return oauthClientDetailsVo;
    }

    @Override
    public void deleteCacheById(String clientId) {
        redisService.del(RedisCachePrefixConstant.CLIENT_DETAILS_PREFIX + clientId);
    }

    private QueryWrapper<OauthClientDetailsVo> getQueryWrapper(OauthClientDetailsVo oauthClientDetailsVo) {
        QueryWrapper<OauthClientDetailsVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.eqNotNull(queryWrapper, "socd.client_id", oauthClientDetailsVo.getClientId());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthClientDetails createOauthClientDetails(OauthClientDetailsAo oauthClientDetailsAo) {
        OauthClientDetailsVo byId = this.findById(oauthClientDetailsAo.getClientId());
        if (byId != null) {
            throw new MyException("该Client已存在");
        }
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
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        BeanUtil.copyProperties(oauthClientDetailsAo, oauthClientDetails);
        setAuthorities(oauthClientDetailsAo.getMenuIds(), oauthClientDetails);
        oauthClientDetails.setClientId(null);
        oauthClientDetails.setClientSecret(null);
        this.updateById(oauthClientDetails);
        return oauthClientDetails;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOauthClientDetails(List<String> ids) {
        this.removeByIds(ids);
    }

    private void setAuthorities(List<Long> menuIds, OauthClientDetails oauthClientDetails) {
        String permissions = menuService.findMenuList(MenuVo.builder().menuIds(menuIds).build()).stream().map(MenuVo::getPerms).collect(Collectors.joining(StrUtil.COMMA));
        oauthClientDetails.setAuthorities(permissions);
    }

    private void setMenuIds(String authorities, List<MenuVo> systemMenuVos, OauthClientDetailsVo oauthClientDetailsVo) {
        List<String> collect = Arrays.stream(authorities.split(StrUtil.COMMA)).collect(Collectors.toList());
        List<Long> menuIds = new ArrayList<>();
        for (String a : collect) {
            MenuVo perms = CollectionUtil.findOneByField(systemMenuVos, "perms", a);
            menuIds.add(perms.getMenuId());
        }
        oauthClientDetailsVo.setMenuIds(menuIds);
    }
}
