package com.zclcs.cloud.lib.mybatis.flex.configure;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.dialect.DbType;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.mybatis.flex.listener.MyInsertListener;
import com.zclcs.cloud.lib.mybatis.flex.listener.MyUpdateListener;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * @author zclcs
 */
@AutoConfiguration
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {

    @Override
    public void customize(FlexGlobalConfig globalConfig) {
        //我们可以在这里进行一些列的初始化配置
        globalConfig.setDbType(DbType.MYSQL);
        MyInsertListener insertListener = new MyInsertListener();
        MyUpdateListener updateListener = new MyUpdateListener();

        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();

        //为 Entity1 和 Entity2 注册 insertListner
        config.registerInsertListener(insertListener, BaseEntity.class);

        //为 Entity1 和 Entity2 注册 updateListener
        config.registerUpdateListener(updateListener, BaseEntity.class);
    }

}
