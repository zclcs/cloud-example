package com.zclcs.common.db.merge.starter.configure;

import cn.hutool.core.collection.CollectionUtil;
import com.zclcs.common.db.merge.starter.properties.MyDbMergeProperties;
import com.zclcs.common.db.merge.starter.utils.DbMergeUtil;
import lombok.RequiredArgsConstructor;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author zclcs
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(MyDbMergeProperties.class)
@ConditionalOnProperty(value = "my.db.merge.enable", havingValue = "true", matchIfMissing = false)
public class MyDbMergeAutoConfigure {

    private final MyDbMergeProperties myDbMergeProperties;
    private final ResourcePatternResolver resourcePatternResolver;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

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
    public Boolean dataSourceInitializer(@Qualifier("myDataSourceProperties") DataSourceProperties dataSourceProperties) throws SQLException, ClassNotFoundException, IOException {
        DbMergeUtil.createDataBase(dataSourceProperties, "utf8mb4", "utf8mb4_unicode_ci");
        DbMergeUtil.createProcedure(dataSourceProperties);
        List<Resource> resourceList = new ArrayList<>(List.of(resourcePatternResolver.getResources(myDbMergeProperties.getSql())));
        if (CollectionUtil.isNotEmpty(resourceList)) {
            resourceList.sort(Comparator.comparing(Resource::getFilename));
        }
        DbMergeUtil.executeSql(dataSourceProperties, resourceList, namespace, false);
        return true;
    }
}
