package com.zclcs.cloud.lib.mybatis.plus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zclcs.cloud.lib.sa.token.api.utils.LoginHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 填充器
 *
 * @author zclcs
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createAt", LocalDateTime.class, LocalDateTime.now());
        Object createBy = metaObject.getValue("createBy");
        if (createBy == null) {
            this.strictInsertFill(metaObject, "createBy", String.class, Optional.ofNullable(LoginHelper.getUsernameWithNull()).orElse("system"));
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateAt", LocalDateTime.class, LocalDateTime.now());
        Object updateBy = metaObject.getValue("updateBy");
        if (updateBy == null) {
            this.strictUpdateFill(metaObject, "updateBy", String.class, Optional.ofNullable(LoginHelper.getUsernameWithNull()).orElse("system"));
        }
    }
}
