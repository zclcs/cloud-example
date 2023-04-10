drop procedure if exists add_column_if_not_exists;
//
-- 添加表字段名称，例如给 system_user 添加字段 code_name，字段类型为int，
-- call add_column_if_not_exists(database(), 'system_user', 'code_name', 'int not null COMMENT "中国"');//
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
END;//

drop procedure if exists change_column_if_exists;
//
-- 修改表字段，例如给 system_user 修改字段 code4为code_name，字段类型为int，
-- call change_column_if_exists(database(), 'system_user', 'code4','code_name', 'int not null COMMENT "中国"');//
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
END;//

drop procedure if exists drop_column_if_exists;
//
-- 删除表字段 例如给 system_user 删除字段 code_name
-- call drop_column_if_exists(database(), 'system_user', 'code_name');//
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
END;//

DROP PROCEDURE IF EXISTS add_index_if_not_exists;
//
--  添加表的索引 举例：比如想要在数据库system_user这张表的字段name创建普通索引 nk_system_user_name
--  call add_index_if_not_exists(database(), 'system_user', 1, 'nk_system_user_name', 'name')
--  indexType 1 普通索引 nk 2 唯一索引 uk 3 全文索引 fk 4 空间索引 sk（创建空间索引的列，必须将其声明为NOT NULL，空间索引只能在存储引擎为MYISAM的表）
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
END;//

DROP PROCEDURE IF EXISTS change_index_if_exists;
//
--  添加表的索引 举例：比如想要在数据库system_user这张表的字段name的普通索引 nk_system_user_name 修改为字段 name2的普通索引 nk_system_user_name2
--  call change_index_if_exists(database(), 'system_user', 1, 'nk_system_user_name2', 'name', 'nk_system_user_name')
--  indexType 1 普通索引 nk 2 唯一索引 uk 3 全文索引 fk 4 空间索引 sk（创建空间索引的列，必须将其声明为NOT NULL，空间索引只能在存储引擎为MYISAM的表）
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
END;//

DROP PROCEDURE IF EXISTS drop_index_if_exists;
//
--  删除表的索引 举例：比如想要在数据库system_user这张表的字段name删除普通索引 nk_system_user_name
--  call drop_index_if_exists(database(), 'system_user', 'nk_system_user_name')
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
END;//


DROP PROCEDURE IF EXISTS insert_if_not_exists;//
-- 如果数据不存在则新增数据，例如给 system_user 新增数据username为admin
-- call insert_if_not_exists(database(), 'system_user', 'username', 'admin', 'username="admin"');//
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
END;//
