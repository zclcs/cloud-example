package com.zclcs.common.db.merge.starter.configure;

import com.zclcs.common.db.merge.starter.properties.MyDbMergeProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author zclcs
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({MyDbMergeProperties.class})
@ConditionalOnProperty(value = "my.db.merge.enable", havingValue = "true", matchIfMissing = true)
public class MyDbMergeConfigure {

    private final MyDbMergeProperties myDbMergeProperties;

    public MyDbMergeConfigure(MyDbMergeProperties myDbMergeProperties) {
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
    public DataSourceInitializer dataSourceInitializer(@Qualifier("myDataSource") DataSource dataSource, @Qualifier("myDataSourceProperties") DataSourceProperties dataSourceProperties) {
        createDataBase(dataSourceProperties, "utf8mb4", "utf8mb4_unicode_ci");
        final DataSourceInitializer initializer = new DataSourceInitializer();
        // 设置数据源
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private void createDataBase(DataSourceProperties dataSourceProperties, String character, String collate) {
        String url = dataSourceProperties.getUrl();
        try {
            Class.forName(dataSourceProperties.getDriverClassName());
            String url01 = url.substring(0, url.indexOf("?"));
            String url02 = url01.substring(0, url01.lastIndexOf("/"));
            String datasourceName = url01.substring(url01.lastIndexOf("/") + 1);
            Connection connection = DriverManager.getConnection(url02, dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
            Statement statement = connection.createStatement();
            // 创建数据库
            statement.executeUpdate("CREATE DATABASE if NOT EXISTS `" + datasourceName + "` " +
                    "DEFAULT CHARACTER SET " + character + " COLLATE " + collate);

            statement.close();
            connection.close();
        } catch (Exception e) {
            log.error("创建数据库失败 ：{} url ： {}", e.getMessage(), url, e);
        }
    }

    /**
     * 需要初始的SQL脚本
     */
    @SneakyThrows
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator popular = new ResourceDatabasePopulator();
        List<Resource> resources = invokeSqlScript();
        List<Resource> resourceList = resources.stream().distinct().toList();
        for (Resource re : resourceList) {
            popular.addScript(re);
            log.info("Execute Sql Location : {}", re.getURL().getPath());
        }
        popular.setSeparator(myDbMergeProperties.getDelimiter());
        return popular;
    }

    /**
     * 执行SQL脚本
     */
    private List<Resource> invokeSqlScript() {
        log.info("invoke Sql Script");
        List<Resource> resources = new ArrayList<>(1000);
        try {
            setSql(resources);
            if (!resources.isEmpty()) {
                resources.sort(Comparator.comparing(Resource::getFilename));
            }
        } catch (Exception e) {
            log.error("获取sql脚本出错:" + e.getMessage(), e);
        }
        return resources;
    }

    /**
     * 获取脚本
     */
    private void setSql(List<Resource> resources) {
        try {
            List<Resource> resourceForPath = getResourceForPath(myDbMergeProperties.getSql());
            resources.addAll(resourceForPath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 根据路径加载SQL资源文件
     *
     * @param basePath 相对目录
     * @return resources 集合
     */
    private List<Resource> getResourceForPath(String basePath) {
        List<Resource> resources = new ArrayList<>();
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
        return resources;
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
