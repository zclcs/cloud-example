package com.zclcs.cloud.lib.mybatis.flex.configure;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.dialect.DbType;
import com.mybatisflex.core.dialect.DialectFactory;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.mybatis.flex.dialect.MyPermissionDialect;
import com.zclcs.cloud.lib.mybatis.flex.listener.MyInsertListener;
import com.zclcs.cloud.lib.mybatis.flex.listener.MySetListener;
import com.zclcs.cloud.lib.mybatis.flex.listener.MyUpdateListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;

/**
 * @author zclcs
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {

    private final ApplicationContext context;

    @Override
    public void customize(FlexGlobalConfig globalConfig) {
        DialectFactory.registerDialect(DbType.MYSQL, new MyPermissionDialect());

        //我们可以在这里进行一些列的初始化配置
        MyInsertListener insertListener = new MyInsertListener();
        MyUpdateListener updateListener = new MyUpdateListener();
        MySetListener setListener = new MySetListener();

        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();
        config.registerInsertListener(insertListener, BaseEntity.class);
        config.registerUpdateListener(updateListener, BaseEntity.class);
        config.registerSetListener(setListener, BaseEntity.class);

        String activeProfile = context.getEnvironment().getActiveProfiles()[0];

        //开启审计功能
        AuditManager.setAuditEnable(!"prod".equals(activeProfile));

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                log.debug("Flex exec sql took {} ms >>> {}", auditMessage.getElapsedTime()
                        , auditMessage.getFullSql())
        );
    }

}
