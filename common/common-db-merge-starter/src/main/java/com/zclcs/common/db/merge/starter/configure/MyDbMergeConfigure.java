package com.zclcs.common.db.merge.starter.configure;

import com.zclcs.common.db.merge.starter.properties.MyDbMergeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Copyright (C) 2020 广东腾晖信息科技开发股份有限公司
 * 版权所有
 *
 * @author zclcs
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(MyDbMergeProperties.class)
@ConditionalOnProperty(value = "my.db.merge.enable", havingValue = "true", matchIfMissing = true)
public class MyDbMergeConfigure {

    private final MyDbMergeProperties properties;

    public MyDbMergeConfigure(MyDbMergeProperties properties) {
        this.properties = properties;
    }

    /**
     * 是否需要执行初始全量脚本
     *
     * @return true-执行初始全量脚本, false-执行增量脚本
     */
    private static boolean initOrOther(final DataSource dataSource) throws SQLException {
        boolean init = false;
        final String sql = "SELECT count(*) FROM information_schema.TABLES WHERE table_schema = DATABASE()";
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                long cnt = rs.getLong(1);
                log.info("是否存在表,{}", cnt == 0 ? "否" : "是");
                init = cnt < 1;
            }
        }
        return init;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        // 设置数据源
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator(dataSource));
        return initializer;
    }

    /**
     * 需要初始的SQL脚本
     *
     * @param dataSource 数据源
     */
    private DatabasePopulator databasePopulator(DataSource dataSource) {
        final ResourceDatabasePopulator popular = new ResourceDatabasePopulator();
        List<Resource> resources = invokeSqlScript(dataSource);
        List<Resource> resourceList = resources.stream().distinct().collect(Collectors.toList());
        for (Resource re : resourceList) {
            popular.addScript(re);
            log.info("====================启动 执行SQL脚本 SQL---------{}", re.getFilename());
        }
        popular.setSeparator(properties.getDelimiter());
        return popular;
    }

    /**
     * 执行SQL脚本
     */
    private List<Resource> invokeSqlScript(final DataSource dataSource) {
        log.info("====================启动 执行SQL脚本 Start");
        List<Resource> resources = new ArrayList<>(1000);
        try {
            boolean isInvokeInitSql = initOrOther(dataSource);

            //执行SQL脚本-1-执行初始全量脚本
            if (isInvokeInitSql) {
                log.info("====================启动 执行SQL脚本-1-执行初始全量脚本 Start");
                invokeInitSql(resources);
                log.info("====================启动 执行SQL脚本-1-执行初始全量脚本 End");
            }

            //执行SQL脚本-2-执行增量脚本
            if (!isInvokeInitSql) {
                log.info("====================启动 执行SQL脚本-2-执行增量脚本 Start");
                invokeOtherSql(resources);
                log.info("====================启动 执行SQL脚本-2-执行增量脚本 End");
            }

            if (!resources.isEmpty()) {
                resources.sort(Comparator.comparing(Resource::getFilename));
            }
            log.info("====================启动 执行SQL脚本 End");
        } catch (Exception e) {
            log.error("执行SQL脚本发生错误:" + e.getMessage(), e);
        }
        return resources;
    }

    /**
     * 执行初始全量脚本
     */
    private void invokeInitSql(List<Resource> resources) {
        try {
            getResourceForPath(properties.getInitSqlLocation(), resources);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 执行增量脚本
     */
    private void invokeOtherSql(List<Resource> resources) {
        try {
            getResourceForPath(properties.getIncrementSqlLocation(), resources);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 根据路径加载SQL资源文件
     *
     * @param basePath  相对目录
     * @param resources 集合
     */
    private void getResourceForPath(String basePath, List<Resource> resources) {
        try {
            Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(basePath);
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                String protocol = url.getProtocol();
                if ("jar".equalsIgnoreCase(protocol)) {
                    getResourceForJar(basePath, url, resources);
                } else if ("file".equalsIgnoreCase(protocol)) {
                    getResourceForFile(basePath, resources);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 解析jar包中的资源文件
     *
     * @param basePath  资源目录相关路径
     * @param url       jar的URL
     * @param resources 集合
     * @throws IOException io错误
     */
    private void getResourceForJar(String basePath, URL url, List<Resource> resources) throws IOException {
        JarURLConnection connection = (JarURLConnection) url.openConnection();
        if (connection == null) {
            return;
        }
        JarFile jarFile = connection.getJarFile();
        if (jarFile == null) {
            return;
        }
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        while (jarEntryEnumeration.hasMoreElements()) {
            JarEntry entry = jarEntryEnumeration.nextElement();
            String jarEntryName = entry.getName();
            if (jarEntryName.startsWith(basePath) && jarEntryName.endsWith("sql")) {
                ClassPathResource classPathResource = new ClassPathResource(jarEntryName);
                Resource rs = new UrlResource(classPathResource.getURL());
                resources.add(rs);
            }
        }
    }

    /**
     * 解析文件系统中的资源文件
     *
     * @param basePath  资源目录相关路径
     * @param resources 集合
     * @throws FileNotFoundException 文件未找到
     */
    private void getResourceForFile(String basePath, List<Resource> resources) throws FileNotFoundException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + basePath);
        if (file.exists() && file.isDirectory()) {
            addResourceLevel1(file, resources);
        }
    }

    private void addResourceLevel1(File file, List<Resource> resources) {
        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            if (listFile.isDirectory()) {
                for (File file1 : Objects.requireNonNull(listFile.listFiles())) {
                    addResourceLevel2(file1, resources);
                }
            } else {
                addResourceLevel2(listFile, resources);
            }
        }
    }


    private void addResourceLevel2(File file, List<Resource> resources) {
        if (file.isFile() && file.getName().endsWith("sql")) {
            Resource rs = new FileSystemResource(file);
            resources.add(rs);
        }
    }
}
