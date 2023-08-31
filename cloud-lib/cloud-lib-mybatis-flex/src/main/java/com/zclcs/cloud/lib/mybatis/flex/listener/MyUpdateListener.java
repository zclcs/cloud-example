package com.zclcs.cloud.lib.mybatis.flex.listener;

import com.mybatisflex.annotation.UpdateListener;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.sa.token.api.utils.LoginHelper;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 修改做的处理
 *
 * @author zclcs
 * @since 2023-08-31
 */
public class MyUpdateListener implements UpdateListener {

    @Override
    public void onUpdate(Object entity) {
        BaseEntity baseEntity = (BaseEntity) entity;

        //设置 account 被修改时的一些默认数据
        baseEntity.setUpdateAt(LocalDateTime.now());
        String updateBy = baseEntity.getUpdateBy();
        if (updateBy == null) {
            baseEntity.setUpdateBy(Optional.ofNullable(LoginHelper.getUsernameWithNull()).orElse("system"));
        }
    }
}
