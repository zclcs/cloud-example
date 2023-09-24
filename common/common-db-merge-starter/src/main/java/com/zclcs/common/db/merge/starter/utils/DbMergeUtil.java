package com.zclcs.common.db.merge.starter.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
@Slf4j
public class DbMergeUtil {

    public static String NACOS_NAMESPACE = "{{NACOS_NAMESPACE}}";

    public static String SQL_TEMP_PATH = "sql_tmp/";

    public void createDataBase(DataSourceProperties dataSourceProperties, String character, String collate) throws ClassNotFoundException, SQLException {
        String url = dataSourceProperties.getUrl();
        String url01 = url.substring(0, url.indexOf("?"));
        String url02 = url01.substring(0, url01.lastIndexOf("/"));
        String datasourceName = url01.substring(url01.lastIndexOf("/") + 1);
        Class.forName(dataSourceProperties.getDriverClassName());
        Connection connection = DriverManager.getConnection(url02, dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        Statement statement = connection.createStatement();
        // 创建数据库
        statement.execute("CREATE DATABASE if NOT EXISTS `" + datasourceName + "` " +
                "DEFAULT CHARACTER SET " + character + " COLLATE " + collate);
        statement.close();
        connection.close();
    }

    public void createProcedure(DataSourceProperties dataSourceProperties) throws ClassNotFoundException, SQLException {
        String url = dataSourceProperties.getUrl();
        Class.forName(dataSourceProperties.getDriverClassName());
        Connection connection = DriverManager.getConnection(url, dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        Statement statement = connection.createStatement();
        // 创建存储过程
        statement.execute("drop procedure if exists add_column_if_not_exists");
        statement.execute("create procedure add_column_if_not_exists(" +
                "    IN dbName tinytext," +
                "    IN tableName tinytext," +
                "    IN fieldName tinytext," +
                "    IN fieldDef text)" +
                "BEGIN" +
                "    IF" +
                "        (NOT EXISTS(" +
                "                SELECT *" +
                "                FROM information_schema.COLUMNS" +
                "                WHERE column_name = fieldName" +
                "                  and table_name = tableName" +
                "                  and table_schema = dbName" +
                "            )) THEN" +
                "        set @ddl = CONCAT('ALTER TABLE ', dbName, '.', tableName, ' ADD COLUMN ', fieldName, ' ', fieldDef);" +
                "        prepare stmt from @ddl;" +
                "        execute stmt;" +
                "    END IF;" +
                "END");
        statement.execute("drop procedure if exists change_column_if_exists");
        statement.execute("create procedure change_column_if_exists(IN dbName tinytext," +
                "                                         IN tableName tinytext," +
                "                                         IN oldFieldName tinytext," +
                "                                         IN fieldName tinytext," +
                "                                         IN fieldDef text)" +
                "begin" +
                "    IF" +
                "        EXISTS(" +
                "                SELECT *" +
                "                FROM information_schema.COLUMNS" +
                "                WHERE column_name = oldFieldName" +
                "                  and table_name = tableName" +
                "                  and table_schema = dbName" +
                "            )" +
                "    THEN" +
                "        set @ddl = CONCAT('ALTER TABLE ', dbName, '.', tableName, ' CHANGE COLUMN ', oldFieldName, ' ', fieldName, ' '," +
                "                          fieldDef);" +
                "        prepare stmt from @ddl;" +
                "        execute stmt;" +
                "    END IF;" +
                "END");
        statement.execute("drop procedure if exists drop_column_if_exists");
        statement.execute("create procedure drop_column_if_exists(IN dbName tinytext," +
                "                                       IN tableName tinytext," +
                "                                       IN fieldName tinytext)" +
                "begin" +
                "    IF" +
                "        EXISTS(" +
                "                SELECT *" +
                "                FROM information_schema.COLUMNS" +
                "                WHERE column_name = fieldName" +
                "                  and table_name = tableName" +
                "                  and table_schema = dbName" +
                "            )" +
                "    THEN" +
                "        set @ddl = CONCAT('ALTER TABLE ', dbName, '.', tableName, ' DROP ', fieldName);" +
                "        prepare stmt from @ddl;" +
                "        execute stmt;" +
                "    END IF;" +
                "END");
        statement.execute("drop procedure if exists add_index_if_not_exists");
        statement.execute("CREATE PROCEDURE add_index_if_not_exists(IN dbName tinytext," +
                "                                         IN tableName tinytext," +
                "                                         IN indexType int," +
                "                                         IN indexName tinytext," +
                "                                         IN indexField text)" +
                "BEGIN" +
                "    SET @index_type = ' INDEX ';" +
                "    IF (indexType = 2) THEN" +
                "        SET @index_type = ' UNIQUE ';" +
                "    END IF;" +
                "    IF (indexType = 3) THEN" +
                "        SET @index_type = ' FULLTEXT ';" +
                "    END IF;" +
                "    IF (indexType = 4) THEN" +
                "        SET @index_type = ' SPATIAL ';" +
                "    END IF;" +
                "    SET @str = concat(" +
                "            ' ALTER TABLE '," +
                "            dbName," +
                "            '.'," +
                "            tableName," +
                "            ' ADD '," +
                "            @index_type," +
                "            indexName," +
                "            ' ( '," +
                "            indexField," +
                "            ' ) '" +
                "        );" +
                "    SELECT count(*)" +
                "    INTO @cnt" +
                "    FROM information_schema.statistics" +
                "    WHERE TABLE_SCHEMA = dbName" +
                "      AND table_name = tableName" +
                "      AND index_name = indexName;" +
                "    IF (@cnt <= 0) THEN" +
                "        PREPARE stmt FROM @str;" +
                "        EXECUTE stmt;" +
                "    END IF;" +
                "END");
        statement.execute("DROP PROCEDURE IF EXISTS change_index_if_exists");
        statement.execute("CREATE PROCEDURE change_index_if_exists(IN dbName tinytext," +
                "                                        IN tableName tinytext," +
                "                                        IN indexType tinytext," +
                "                                        IN indexName tinytext," +
                "                                        IN indexField tinytext," +
                "                                        IN oldIndexName text)" +
                "BEGIN" +
                "    SET @str = concat(" +
                "            ' DROP INDEX '," +
                "            oldIndexName," +
                "            ' ON '," +
                "            dbName," +
                "            '.'," +
                "            tableName" +
                "        );" +
                "    SELECT count(*)" +
                "    INTO @cnt" +
                "    FROM information_schema.statistics" +
                "    WHERE TABLE_SCHEMA = dbName" +
                "      AND table_name = tableName" +
                "      AND index_name = oldIndexName;" +
                "    IF (@cnt > 0) THEN" +
                "        PREPARE stmt FROM @str;" +
                "        EXECUTE stmt;" +
                "    END IF;" +
                "    SET @index_type = ' INDEX ';" +
                "    IF (indexType = 2) THEN" +
                "        SET @index_type = ' UNIQUE ';" +
                "    END IF;" +
                "    IF (indexType = 3) THEN" +
                "        SET @index_type = ' FULLTEXT ';" +
                "    END IF;" +
                "    IF (indexType = 4) THEN" +
                "        SET @index_type = ' SPATIAL ';" +
                "    END IF;" +
                "    SET @str = concat(" +
                "            ' ALTER TABLE '," +
                "            dbName," +
                "            '.'," +
                "            tableName," +
                "            ' ADD '," +
                "            @index_type," +
                "            indexName," +
                "            ' ( '," +
                "            indexField," +
                "            ' ) '" +
                "        );" +
                "    SELECT count(*)" +
                "    INTO @cnt" +
                "    FROM information_schema.statistics" +
                "    WHERE TABLE_SCHEMA = dbName" +
                "      AND table_name = tableName" +
                "      AND index_name = indexName;" +
                "    IF (@cnt <= 0) THEN" +
                "        PREPARE stmt FROM @str;" +
                "        EXECUTE stmt;" +
                "    END IF;" +
                "END");
        statement.execute("DROP PROCEDURE IF EXISTS drop_index_if_exists");
        statement.execute("CREATE PROCEDURE drop_index_if_exists(IN dbName tinytext," +
                "                                      IN tableName tinytext," +
                "                                      IN indexName tinytext)" +
                "BEGIN" +
                "    SET @str = concat(" +
                "            ' DROP INDEX '," +
                "            indexName," +
                "            ' ON '," +
                "            dbName," +
                "            '.'," +
                "            tableName" +
                "        );" +
                "    SELECT count(*)" +
                "    INTO @cnt" +
                "    FROM information_schema.statistics" +
                "    WHERE TABLE_SCHEMA = dbName" +
                "      AND table_name = tableName" +
                "      AND index_name = indexName;" +
                "    IF (@cnt > 0) THEN" +
                "        PREPARE stmt FROM @str;" +
                "        EXECUTE stmt;" +
                "    END IF;" +
                "END");
        statement.execute("DROP PROCEDURE IF EXISTS insert_if_not_exists");
        statement.execute("CREATE PROCEDURE insert_if_not_exists(IN dbName tinytext," +
                "                                      IN tableName tinytext," +
                "                                      IN columnName text," +
                "                                      IN columnData text," +
                "                                      IN unique_sql text)" +
                "BEGIN" +
                "    SET @str = concat(" +
                "            ' insert into '," +
                "            dbName," +
                "            '.'," +
                "            tableName," +
                "            ' ( '," +
                "            columnName," +
                "            ' ) '," +
                "            ' select '," +
                "            columnData," +
                "            ' from dual where not exists (select * from '," +
                "            dbName," +
                "            '.'," +
                "            tableName," +
                "            ' where '," +
                "            unique_sql," +
                "            ' ) '" +
                "        );" +
                "    PREPARE stmt FROM @str;" +
                "    EXECUTE stmt;" +
                "END");
        statement.execute("DROP PROCEDURE IF EXISTS insert_or_update");
        statement.execute("CREATE PROCEDURE insert_or_update(IN dbName tinytext," +
                "                                  IN tableName tinytext," +
                "                                  IN insertSql varchar(10000)," +
                "                                  IN updateSql varchar(10000))" +
                "BEGIN" +
                "    set @updateSql = concat(" +
                "            ' update '," +
                "            dbName," +
                "            '.'," +
                "            tableName," +
                "            ' '," +
                "            updateSql" +
                "        );" +
                "    prepare stmt from @updateSql;" +
                "    EXECUTE stmt;" +
                "" +
                "    IF ROW_COUNT() = 0 THEN" +
                "        set @insertSql = concat(" +
                "            ' insert into '," +
                "            dbName," +
                "            '.'," +
                "            tableName," +
                "            ' '," +
                "            insertSql" +
                "            );" +
                "        prepare stmt from @insertSql;" +
                "        EXECUTE stmt;" +
                "    END IF;" +
                "    deallocate prepare stmt;" +
                "END");
        connection.close();
        statement.close();
    }

    public void executeSql(DataSourceProperties dataSourceProperties, List<Resource> resourceList, String nacosNamespace, boolean replace) throws ClassNotFoundException, SQLException, IOException {
        String url = dataSourceProperties.getUrl();
        Class.forName(dataSourceProperties.getDriverClassName());
        Connection connection = DriverManager.getConnection(url, dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        Statement statement = connection.createStatement();
        for (Resource resource : resourceList) {
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String sql = new String(bytes, StandardCharsets.UTF_8);
            if (replace) {
                sql = StrUtil.replace(sql, NACOS_NAMESPACE, nacosNamespace);
            }
            List<String> split = StrUtil.split(sql, ";").stream().filter(StrUtil::isNotBlank).toList();
            for (String s : split) {
                log.debug("Sql : {}", s);
                statement.execute(s);
            }
        }
        connection.close();
        statement.close();
    }

    public DatabasePopulator databasePopulator(List<Resource> resourceForPath) throws IOException {
        final ResourceDatabasePopulator popular = new ResourceDatabasePopulator();
        for (Resource re : resourceForPath) {
            popular.addScript(re);
//            log.info("Execute Sql Location : {}", re.getFile().getPath());
        }
        return popular;
    }

    /**
     * 执行SQL脚本
     */
    public void replaceSqlScript(List<Resource> resourceForPath, String nacosNamespace) throws IOException {
        log.debug("invoke Sql Script");
        replaceSql(resourceForPath, nacosNamespace);
    }

    private void replaceSql(List<Resource> resourceForPath, String nacosNamespace) throws IOException {
        for (Resource resource : resourceForPath) {
            File out = FileUtil.file(FileUtil.getTmpDirPath() + "/tmp.sql");
            FileCopyUtils.copy(FileCopyUtils.copyToByteArray(resource.getInputStream()), out);
            String sql = FileUtil.readString(out, StandardCharsets.UTF_8);
            log.info(sql);
            String nacosNamespaceReplace = StrUtil.replace(sql, NACOS_NAMESPACE, nacosNamespace);
            String path = resource.getURI().getPath();
            String filename = resource.getFilename();
            String parentPath = path.substring(0, path.lastIndexOf(Objects.requireNonNull(filename)));
            String child = parentPath + SQL_TEMP_PATH;
            File newFile = new File(child, filename);
            FileUtil.touch(newFile);
            FileUtil.writeString(nacosNamespaceReplace, newFile, StandardCharsets.UTF_8);
        }
    }

    /**
     * 根据路径加载SQL资源文件
     *
     * @param basePath 相对目录
     * @return resources 集合
     */
    private List<Resource> getResourceForPath(String basePath) throws IOException {
        List<Resource> resources = new ArrayList<>();
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
