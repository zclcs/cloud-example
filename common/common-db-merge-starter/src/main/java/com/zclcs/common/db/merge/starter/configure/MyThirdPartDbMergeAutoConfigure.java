package com.zclcs.common.db.merge.starter.configure;

import com.zclcs.common.db.merge.starter.properties.MyThirdPartDbMergeProperties;
import com.zclcs.common.db.merge.starter.utils.DbMergeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author zclcs
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "my.third.part.db.merge.enable", havingValue = "true", matchIfMissing = false)
public class MyThirdPartDbMergeAutoConfigure {

    private final MyThirdPartDbMergeProperties myThirdPartDbMergeProperties;
    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Bean(name = "nacosDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.nacos")
    public DataSourceProperties nacosDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "nacosDataSource")
    public DataSource nacosDataSource(@Qualifier("nacosDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "xxlJobDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.xxl-job")
    public DataSourceProperties xxlJobDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "xxlJobDataSource")
    public DataSource xxlJobDataSource(@Qualifier("xxlJobDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSourceInitializer nacosDatabaseInitializer(@Qualifier("nacosDataSource") DataSource dataSource, @Qualifier("nacosDataSourceProperties") DataSourceProperties dataSourceProperties) throws SQLException, ClassNotFoundException, IOException {
        DbMergeUtil.createDataBase(dataSourceProperties, "utf8", "utf8_bin");
        DbMergeUtil.createProcedure(dataSourceProperties);
        final DataSourceInitializer initializer = new DataSourceInitializer();
        // 设置数据源
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(DbMergeUtil.databasePopulator(myThirdPartDbMergeProperties.getNacosSql(), namespace, true));
        return initializer;
    }

    @Bean
    public DataSourceInitializer xxlJobDatabaseInitializer(@Qualifier("xxlJobDataSource") DataSource dataSource, @Qualifier("xxlJobDataSourceProperties") DataSourceProperties dataSourceProperties) throws SQLException, ClassNotFoundException, IOException {
        DbMergeUtil.createDataBase(dataSourceProperties, "utf8mb4", "utf8mb4_unicode_ci");
        DbMergeUtil.createProcedure(dataSourceProperties);
        final DataSourceInitializer initializer = new DataSourceInitializer();
        // 设置数据源
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(DbMergeUtil.databasePopulator(myThirdPartDbMergeProperties.getXxlJobSql(), namespace, true));
        return initializer;
    }
}
