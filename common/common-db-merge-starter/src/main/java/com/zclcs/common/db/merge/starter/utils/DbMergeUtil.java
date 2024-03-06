package com.zclcs.common.db.merge.starter.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UtilityClass
@Slf4j
public class DbMergeUtil {

    public static String NACOS_NAMESPACE = "{{NACOS_NAMESPACE}}";

    /**
     * 创建数据库
     *
     * @param dataSourceProperties 配置
     * @param character            数据库字符集
     * @param collate              字符集排序方式
     */
    public void createDataBase(DataSourceProperties dataSourceProperties, String character, String collate) throws ClassNotFoundException, SQLException {
        String url = dataSourceProperties.getUrl();
        String url01 = url.substring(0, url.indexOf("?"));
        String url02 = url01.substring(0, url01.lastIndexOf("/"));
        String datasourceName = url01.substring(url01.lastIndexOf("/") + 1);
        Class.forName(dataSourceProperties.getDriverClassName());
        Connection connection = DriverManager.getConnection(url02, dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        Statement statement = connection.createStatement();
        String databaseSql = """
                CREATE DATABASE if NOT EXISTS `%s` DEFAULT CHARACTER SET %s COLLATE %s
                """;
        // 创建数据库
        statement.execute(databaseSql.formatted(datasourceName, character, collate));
        statement.close();
        connection.close();
    }

    /**
     * 创建存储过程
     *
     * @param dataSourceProperties 配置
     */
    public void createProcedure(DataSourceProperties dataSourceProperties) throws ClassNotFoundException, SQLException {
        String url = dataSourceProperties.getUrl();
        Class.forName(dataSourceProperties.getDriverClassName());
        Connection connection = DriverManager.getConnection(url, dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        Statement statement = connection.createStatement();
        statement.execute("""
                drop procedure if exists add_column_if_not_exists
                """);
        // 添加表字段名称，例如给 system_user 添加字段 code_name，字段类型为int，
        // call add_column_if_not_exists(database(), 'system_user', 'code_name', 'int not null COMMENT "中国"');
        statement.execute("""
                create procedure add_column_if_not_exists(
                    IN dbName tinytext,
                    IN tableName tinytext,
                    IN fieldName tinytext,
                    IN fieldDef text)
                BEGIN
                    IF
                        (NOT EXISTS(
                                SELECT *
                                FROM information_schema.COLUMNS
                                WHERE column_name = fieldName
                                  and table_name = tableName
                                  and table_schema = dbName
                            )) THEN
                        set @ddl = CONCAT('ALTER TABLE ', dbName, '.', tableName, ' ADD COLUMN ', fieldName, ' ', fieldDef);
                        prepare stmt from @ddl;
                        execute stmt;
                    END IF;
                END                
                """);
        statement.execute("""
                drop procedure if exists change_column_if_exists                
                """);
        // 修改表字段，例如给 system_user 修改字段 code4为code_name，字段类型为int，
        // call change_column_if_exists(database(), 'system_user', 'code4','code_name', 'int not null COMMENT "中国"');
        statement.execute("""
                create procedure change_column_if_exists(IN dbName tinytext,
                                                         IN tableName tinytext,
                                                         IN oldFieldName tinytext,
                                                         IN fieldName tinytext,
                                                         IN fieldDef text)
                begin
                    IF
                        EXISTS(
                                SELECT *
                                FROM information_schema.COLUMNS
                                WHERE column_name = oldFieldName
                                  and table_name = tableName
                                  and table_schema = dbName
                            )
                    THEN
                        set @ddl = CONCAT('ALTER TABLE ', dbName, '.', tableName, ' CHANGE COLUMN ', oldFieldName, ' ', fieldName, ' ',
                                          fieldDef);
                        prepare stmt from @ddl;
                        execute stmt;
                    END IF;
                END                
                """);
        statement.execute("""
                drop procedure if exists drop_column_if_exists
                """);
        // 删除表字段 例如给 system_user 删除字段 code_name
        // call drop_column_if_exists(database(), 'system_user', 'code_name');
        statement.execute("""
                create procedure drop_column_if_exists(IN dbName tinytext,
                                                       IN tableName tinytext,
                                                       IN fieldName tinytext)
                begin
                    IF
                        EXISTS(
                                SELECT *
                                FROM information_schema.COLUMNS
                                WHERE column_name = fieldName
                                  and table_name = tableName
                                  and table_schema = dbName
                            )
                    THEN
                        set @ddl = CONCAT('ALTER TABLE ', dbName, '.', tableName, ' DROP ', fieldName);
                        prepare stmt from @ddl;
                        execute stmt;
                    END IF;
                END                
                """);
        statement.execute("""
                drop procedure if exists add_index_if_not_exists                
                """);
        //  添加表的索引 举例：比如想要在数据库system_user这张表的字段name创建普通索引 nk_system_user_name
        //  call add_index_if_not_exists(database(), 'system_user', 1, 'nk_system_user_name', 'name');
        //  indexType 1 普通索引 nk 2 唯一索引 uk 3 全文索引 fk 4 空间索引 sk（创建空间索引的列，必须将其声明为NOT NULL，空间索引只能在存储引擎为MYISAM的表）
        statement.execute("""
                CREATE PROCEDURE add_index_if_not_exists(IN dbName tinytext,
                                                         IN tableName tinytext,
                                                         IN indexType int,
                                                         IN indexName tinytext,
                                                         IN indexField text)
                BEGIN
                    SET @index_type = ' INDEX ';
                    IF (indexType = 2) THEN
                        SET @index_type = ' UNIQUE ';
                    END IF;
                    IF (indexType = 3) THEN
                        SET @index_type = ' FULLTEXT ';
                    END IF;
                    IF (indexType = 4) THEN
                        SET @index_type = ' SPATIAL ';
                    END IF;
                    SET @str = concat(
                            ' ALTER TABLE ',
                            dbName,
                            '.',
                            tableName,
                            ' ADD ',
                            @index_type,
                            indexName,
                            ' ( ',
                            indexField,
                            ' ) '
                        );
                    SELECT count(*)
                    INTO @cnt
                    FROM information_schema.statistics
                    WHERE TABLE_SCHEMA = dbName
                      AND table_name = tableName
                      AND index_name = indexName;
                    IF (@cnt <= 0) THEN
                        PREPARE stmt FROM @str;
                        EXECUTE stmt;
                    END IF;
                END
                """);
        statement.execute("""
                DROP PROCEDURE IF EXISTS change_index_if_exists                
                """);
        //  添加表的索引 举例：比如想要在数据库system_user这张表的字段name的普通索引 nk_system_user_name 修改为字段 name2的普通索引 nk_system_user_name2
        //  call change_index_if_exists(database(), 'system_user', 1, 'nk_system_user_name2', 'name', 'nk_system_user_name');
        //  indexType 1 普通索引 nk 2 唯一索引 uk 3 全文索引 fk 4 空间索引 sk（创建空间索引的列，必须将其声明为NOT NULL，空间索引只能在存储引擎为MYISAM的表）
        statement.execute("""
                CREATE PROCEDURE change_index_if_exists(IN dbName tinytext,
                                                        IN tableName tinytext,
                                                        IN indexType tinytext,
                                                        IN indexName tinytext,
                                                        IN indexField tinytext,
                                                        IN oldIndexName text)
                BEGIN
                    SET @str = concat(
                            ' DROP INDEX ',
                            oldIndexName,
                            ' ON ',
                            dbName,
                            '.',
                            tableName
                        );
                    SELECT count(*)
                    INTO @cnt
                    FROM information_schema.statistics
                    WHERE TABLE_SCHEMA = dbName
                      AND table_name = tableName
                      AND index_name = oldIndexName;
                    IF (@cnt > 0) THEN
                        PREPARE stmt FROM @str;
                        EXECUTE stmt;
                    END IF;
                    SET @index_type = ' INDEX ';
                    IF (indexType = 2) THEN
                        SET @index_type = ' UNIQUE ';
                    END IF;
                    IF (indexType = 3) THEN
                        SET @index_type = ' FULLTEXT ';
                    END IF;
                    IF (indexType = 4) THEN
                        SET @index_type = ' SPATIAL ';
                    END IF;
                    SET @str = concat(
                            ' ALTER TABLE ',
                            dbName,
                            '.',
                            tableName,
                            ' ADD ',
                            @index_type,
                            indexName,
                            ' ( ',
                            indexField,
                            ' ) '
                        );
                    SELECT count(*)
                    INTO @cnt
                    FROM information_schema.statistics
                    WHERE TABLE_SCHEMA = dbName
                      AND table_name = tableName
                      AND index_name = indexName;
                    IF (@cnt <= 0) THEN
                        PREPARE stmt FROM @str;
                        EXECUTE stmt;
                    END IF;
                END                
                """);
        statement.execute("""
                DROP PROCEDURE IF EXISTS drop_index_if_exists
                """);
        //  删除表的索引 举例：比如想要在数据库system_user这张表的字段name删除普通索引 nk_system_user_name
        //  call drop_index_if_exists(database(), 'system_user', 'nk_system_user_name');
        statement.execute("""
                CREATE PROCEDURE drop_index_if_exists(IN dbName tinytext,
                                                      IN tableName tinytext,
                                                      IN indexName tinytext)
                BEGIN
                    SET @str = concat(
                            ' DROP INDEX ',
                            indexName,
                            ' ON ',
                            dbName,
                            '.',
                            tableName
                        );
                    SELECT count(*)
                    INTO @cnt
                    FROM information_schema.statistics
                    WHERE TABLE_SCHEMA = dbName
                      AND table_name = tableName
                      AND index_name = indexName;
                    IF (@cnt > 0) THEN
                        PREPARE stmt FROM @str;
                        EXECUTE stmt;
                    END IF;
                END
                """);
        statement.execute("""
                DROP PROCEDURE IF EXISTS insert_if_not_exists
                """);
        // 如果数据不存在则新增数据，例如给 system_user 新增数据username为admin
        // call insert_if_not_exists(database(), 'system_user', 'username', 'admin', 'username="admin"');
        statement.execute("""
                CREATE PROCEDURE insert_if_not_exists(IN dbName tinytext,
                                                      IN tableName tinytext,
                                                      IN columnName text,
                                                      IN columnData text,
                                                      IN unique_sql text)
                BEGIN
                    SET @str = concat(
                            ' insert into ',
                            dbName,
                            '.',
                            tableName,
                            ' ( ',
                            columnName,
                            ' ) ',
                            ' select ',
                            columnData,
                            ' from dual where not exists (select * from ',
                            dbName,
                            '.',
                            tableName,
                            ' where ',
                            unique_sql,
                            ' ) '
                        );
                    PREPARE stmt FROM @str;
                    EXECUTE stmt;
                END
                """);
//        statement.execute("""
//                DROP PROCEDURE IF EXISTS insert_or_update
//                """);
//        // 如果数据不存在则新增数据，例如给 system_user 新增或数据username为admin 表中要有主键或者唯一索引
//        // call insert_or_update(database(), 'system_user', 'username', '(admin)', 'username=values(username)');
//        statement.execute("""
//                CREATE PROCEDURE insert_or_update(IN dbName tinytext,
//                                                  IN tableName tinytext,
//                                                  IN insertSql varchar(10000),
//                                                  IN updateSql varchar(10000))
//                BEGIN
//                    set @updateSql = concat(
//                            ' update ',
//                            dbName,
//                            '.',
//                            tableName,
//                            ' ',
//                            updateSql
//                        );
//                    prepare stmt from @updateSql;
//                    EXECUTE stmt;
//
//                    IF ROW_COUNT() = 0 THEN
//                        set @insertSql = concat(
//                            ' insert into ',
//                            dbName,
//                            '.',
//                            tableName,
//                            ' ',
//                            insertSql
//                            );
//                        prepare stmt from @insertSql;
//                        EXECUTE stmt;
//                    END IF;
//                    deallocate prepare stmt;
//                END
//                """);
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
                log.debug("Execute Sql : {}", s);
                statement.execute(s);
            }
        }
        connection.close();
        statement.close();
    }

    public void merge(DataSourceProperties dataSourceProperties, ResourcePatternResolver resourcePatternResolver, String sqlLocation, String nacosNamespace, boolean replace) throws ClassNotFoundException, SQLException, IOException {
        createDataBase(dataSourceProperties, "utf8mb4", "utf8mb4_unicode_ci");
        createProcedure(dataSourceProperties);
        List<Resource> resourceList = new ArrayList<>(List.of(resourcePatternResolver.getResources(sqlLocation)));
        if (CollectionUtil.isNotEmpty(resourceList)) {
            resourceList.sort(Comparator.comparing(Resource::getFilename));
        }
        executeSql(dataSourceProperties, resourceList, nacosNamespace, replace);
    }
}
