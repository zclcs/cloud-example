package com.zclcs.common.db.merge.starter.configure;

import com.zclcs.common.db.merge.starter.properties.MyThirdPartDbMergeProperties;
import com.zclcs.common.db.merge.starter.utils.DbMergeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author zclcs
 */
@Slf4j
@AutoConfiguration(before = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(MyThirdPartDbMergeProperties.class)
@ConditionalOnProperty(value = "my.third.part.db.merge.enable", havingValue = "true", matchIfMissing = false)
public class MyThirdPartDbMergeAutoConfigure {

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    public MyThirdPartDbMergeAutoConfigure(MyThirdPartDbMergeProperties myThirdPartDbMergeProperties, ResourcePatternResolver resourcePatternResolver) throws SQLException, IOException, ClassNotFoundException {
        DbMergeUtil.merge(myThirdPartDbMergeProperties.getNacosDataSourceProperties(), resourcePatternResolver, myThirdPartDbMergeProperties.getNacosSql(), namespace, true);
        DbMergeUtil.merge(myThirdPartDbMergeProperties.getPowerJobDataSourceProperties(), resourcePatternResolver, myThirdPartDbMergeProperties.getPowerJobSql(), namespace, true);
    }
}
