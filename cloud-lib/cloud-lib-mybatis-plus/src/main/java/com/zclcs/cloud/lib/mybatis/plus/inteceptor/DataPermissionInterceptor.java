package com.zclcs.cloud.lib.mybatis.plus.inteceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.zclcs.cloud.lib.mybatis.plus.annotation.DataPermission;
import com.zclcs.cloud.lib.security.entity.SecurityUser;
import com.zclcs.cloud.lib.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.StringReader;
import java.util.stream.Collectors;

/**
 * @author zclcs
 */
@Slf4j
public class DataPermissionInterceptor implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
                            ResultHandler resultHandler, BoundSql boundSql) {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        // 数据权限只针对查询语句
        if (SqlCommandType.SELECT == ms.getSqlCommandType()) {
            DataPermission dataPermission = getDataPermission(ms);
            if (shouldFilter(ms, dataPermission)) {
                String id = ms.getId();
                log.info("\n 数据权限过滤 method -> {}", id);
                String originSql = boundSql.getSql();
                String dataPermissionSql = dataPermissionSql(originSql, dataPermission);
                mpBs.sql(dataPermissionSql);
                log.info("\n originSql -> {} \n dataPermissionSql: {}", originSql, dataPermissionSql);
            }
        }
    }

    private String dataPermissionSql(String originSql, DataPermission dataPermission) {
        try {
            if (StrUtil.isBlank(dataPermission.field())) {
                return originSql;
            }
            SecurityUser user = SecurityUtil.getUser();
            if (user == null) {
                return originSql;
            }
            CCJSqlParserManager parserManager = new CCJSqlParserManager();
            Select select = (Select) parserManager.parse(new StringReader(originSql));
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            Table fromItem = (Table) plainSelect.getFromItem();

            String selectTableName = fromItem.getAlias() == null ? fromItem.getName() : fromItem.getAlias().getName();
            String deptIdString = user.getDeptIds().stream().map(String::valueOf).collect(Collectors.joining(StrUtil.COMMA));
            String dataPermissionSql = String.format("%s.%s in (%s)", selectTableName, dataPermission.field(), StrUtil.blankToDefault(deptIdString, "'WITHOUT PERMISSIONS'"));

            if (plainSelect.getWhere() == null) {
                plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression(dataPermissionSql));
            } else {
                plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), CCJSqlParserUtil.parseCondExpression(dataPermissionSql)));
            }
            return select.toString();
        } catch (Exception e) {
            log.warn("get data permission sql fail: {}", e.getMessage());
            return originSql;
        }
    }

    private DataPermission getDataPermission(MappedStatement mappedStatement) {
        String mappedStatementId = mappedStatement.getId();
        DataPermission dataPermission = null;
        try {
            String className = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
            final Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(DataPermission.class)) {
                dataPermission = clazz.getAnnotation(DataPermission.class);
            }
        } catch (Exception ignore) {
        }
        return dataPermission;
    }

    private Boolean shouldFilter(MappedStatement mappedStatement, DataPermission dataPermission) {
        if (dataPermission != null) {
            String methodName = StrUtil.subAfter(mappedStatement.getId(), ".", true);
            String methodPrefix = dataPermission.methodPrefix();
            if (StrUtil.isNotBlank(methodPrefix) && StrUtil.startWith(methodName, methodPrefix)) {
                return Boolean.TRUE;
            }
            String[] methods = dataPermission.methods();
            for (String method : methods) {
                if (StrUtil.equals(method, methodName)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }
}