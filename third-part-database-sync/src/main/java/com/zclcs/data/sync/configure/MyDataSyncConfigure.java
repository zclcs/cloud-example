package com.zclcs.data.sync.configure;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.properties.GlobalProperties;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.data.sync.properties.MyDataSyncProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import java.nio.charset.StandardCharsets;
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
@EnableConfigurationProperties({MyDataSyncProperties.class, GlobalProperties.class})
public class MyDataSyncConfigure {

    private final MyDataSyncProperties myDataSyncProperties;

    private final MyNacosProperties myNacosProperties;

    public MyDataSyncConfigure(MyDataSyncProperties myDataSyncProperties, MyNacosProperties myNacosProperties) {
        this.myDataSyncProperties = myDataSyncProperties;
        this.myNacosProperties = myNacosProperties;
    }

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
    public DataSourceInitializer nacosDatabaseInitializer(@Qualifier("nacosDataSource") DataSource dataSource, @Qualifier("nacosDataSourceProperties") DataSourceProperties dataSourceProperties) {
        createDataBase(dataSourceProperties, "utf8", "utf8_bin");
        final DataSourceInitializer initializer = new DataSourceInitializer();
        // 设置数据源
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator("nacos"));
        return initializer;
    }

    @Bean
    public DataSourceInitializer xxlJobDatabaseInitializer(@Qualifier("xxlJobDataSource") DataSource dataSource, @Qualifier("xxlJobDataSourceProperties") DataSourceProperties dataSourceProperties) {
        createDataBase(dataSourceProperties, "utf8mb4", "utf8mb4_unicode_ci");
        final DataSourceInitializer initializer = new DataSourceInitializer();
        // 设置数据源
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator("xxl-job"));
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
     *
     * @param type nacos xxl-job
     */
    @SneakyThrows
    private DatabasePopulator databasePopulator(String type) {
        final ResourceDatabasePopulator popular = new ResourceDatabasePopulator();
        List<Resource> resources = invokeSqlScript(type);
        List<Resource> resourceList = resources.stream().distinct().toList();
        for (Resource re : resourceList) {
            popular.addScript(re);
            log.info("Execute Sql Location : {}", re.getURL().getPath());
        }
        popular.setSeparator(myDataSyncProperties.getDelimiter());
        return popular;
    }

    /**
     * 执行SQL脚本
     *
     * @param type nacos xxl-job
     */
    private List<Resource> invokeSqlScript(String type) {
        List<Resource> resources = new ArrayList<>(1000);
        try {
            if ("nacos".equals(type)) {
                log.info("Invoke Nacos Sql Script");
                setNacosSql(resources);
            } else {
                log.info("Invoke Xxl Job Sql Script");
                setXxlJobSql(resources);
            }
            if (!resources.isEmpty()) {
                resources.sort(Comparator.comparing(Resource::getFilename));
            }
        } catch (Exception e) {
            log.error("执行SQL脚本发生错误:" + e.getMessage(), e);
        }
        return resources;
    }

    /**
     * 获取nacos脚本
     */
    private void setNacosSql(List<Resource> resources) {
        try {
            List<Resource> resourceForPath = getResourceForPath(myDataSyncProperties.getNacosSql());
            replaceSql(resources, resourceForPath, myDataSyncProperties.getNacosSql());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取xxlJob脚本
     */
    private void setXxlJobSql(List<Resource> resources) {
        try {
            List<Resource> resourceForPath = getResourceForPath(myDataSyncProperties.getXxlJobSql());
            replaceSql(resources, resourceForPath, myDataSyncProperties.getXxlJobSql());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void replaceSql(List<Resource> resources, List<Resource> resourceForPath, String location) throws IOException {
        for (Resource resource : resourceForPath) {
            String sql = FileUtil.readString(resource.getFile(), StandardCharsets.UTF_8);
            String nacosNamespaceReplace = StrUtil.replace(sql, CommonCore.NACOS_NAMESPACE, myNacosProperties.getNamespace());
            String path = resource.getURI().getPath();
            String parentPath = path.substring(0, path.lastIndexOf(location));
            String child = location + CommonCore.SQL_TEMP_PATH + resource.getFilename();
            File newFile = new File(parentPath, child);
            FileUtil.touch(newFile);
            FileUtil.writeString(nacosNamespaceReplace, newFile, StandardCharsets.UTF_8);
            ClassPathResource classPathResource = new ClassPathResource(child);
            resources.add(classPathResource);
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
