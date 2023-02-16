package com.zclcs.common.security.starter.service;

import cn.hutool.core.util.StrUtil;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.security.starter.entity.SecurityUser;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author zclcs
 */
public interface MyUserDetailsService extends UserDetailsService, Ordered {

    /**
     * 是否支持此客户端校验
     *
     * @param clientId  目标客户端
     * @param grantType 类型
     * @return true/false
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }

    /**
     * 排序值 默认取最大的
     *
     * @return 排序值
     */
    @Override
    default int getOrder() {
        return 0;
    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return UserDetails
     */
    default UserDetails getUserDetails(BaseRsp<UserVo> result) {
        UserVo userVo = Optional.ofNullable(result.getData()).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        Set<String> dbAuthsSet = new HashSet<>();

        if (StrUtil.isNotBlank(userVo.getRoleIdString())) {
            StrUtil.split(userVo.getRoleIdString(), StrUtil.COMMA).forEach(roleId -> {
                dbAuthsSet.add(SecurityConstant.ROLE + roleId);
            });
            // 获取资源
            dbAuthsSet.addAll(userVo.getPermissions());
        }

        Collection<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        userVo.setRoleNames(StrUtil.split(userVo.getRoleNameString(), StrUtil.COMMA));
        // 构造security用户
        return new SecurityUser(userVo.getUserId(), userVo.getDeptId(), userVo.getDeptIdString(), userVo.getUsername(),
                SecurityConstant.BCRYPT + userVo.getPassword(), userVo.getMobile(), true, true, true,
                StrUtil.equals(userVo.getStatus(), MyConstant.STATUS_VALID), authorities, userVo.getRoleNames());
    }

    /**
     * 通过用户实体查询
     *
     * @param securityUser user
     * @return
     */
    default UserDetails loadUserByUser(SecurityUser securityUser) {
        return this.loadUserByUsername(securityUser.getUsername());
    }

}
