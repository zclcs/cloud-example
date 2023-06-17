package com.zclcs.common.db.merge.starter.configure;

import com.zclcs.common.db.merge.starter.properties.MyDbMergeProperties;
import com.zclcs.common.db.merge.starter.utils.DbMergeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author zclcs
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({MyDbMergeProperties.class})
@ConditionalOnProperty(value = "my.db.merge.enable", havingValue = "true", matchIfMissing = false)
public class MyDbMergeAutoConfigure {

    private final MyDbMergeProperties myDbMergeProperties;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    public MyDbMergeAutoConfigure(MyDbMergeProperties myDbMergeProperties) {
        this.myDbMergeProperties = myDbMergeProperties;
    }

    @Primary
    @Bean(name = "myDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties myDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "myDataSource")
    public DataSource myDataSource(@Qualifier("myDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier("myDataSource") DataSource dataSource, @Qualifier("myDataSourceProperties") DataSourceProperties dataSourceProperties) throws SQLException, ClassNotFoundException, IOException {
        DbMergeUtil.createDataBase(dataSourceProperties, "utf8mb4", "utf8mb4_unicode_ci");
        DbMergeUtil.createProcedure(dataSourceProperties);
        final DataSourceInitializer initializer = new DataSourceInitializer();
        // 设置数据源
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(DbMergeUtil.databasePopulator(myDbMergeProperties.getSql(), namespace, false));
        return initializer;
    }
}
