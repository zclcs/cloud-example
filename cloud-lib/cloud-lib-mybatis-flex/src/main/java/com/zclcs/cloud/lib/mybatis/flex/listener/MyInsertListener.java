package com.zclcs.cloud.lib.mybatis.flex.listener;

import com.mybatisflex.annotation.InsertListener;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.sa.token.api.utils.LoginHelper;

import java.time.LocalDateTime;
import java.util.Optional;

public class MyInsertListener implements InsertListener {

    @Override
    public void onInsert(Object entity) {
        BaseEntity baseEntity = (BaseEntity) entity;

        //设置 account 被新增时的一些默认数据
        baseEntity.setCreateAt(LocalDateTime.now());
        String createBy = baseEntity.getCreateBy();
        if (createBy == null) {
            baseEntity.setCreateBy(Optional.ofNullable(LoginHelper.getUsernameWithNull()).orElse("system"));
        }
    }
}
