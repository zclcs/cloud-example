package com.zclcs.cloud.lib.mybatis.flex.dialect;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import com.mybatisflex.core.query.CPI;
import com.mybatisflex.core.query.QueryTable;
import com.mybatisflex.core.query.QueryWrapper;
import com.zclcs.cloud.lib.mybatis.flex.holder.DataPermissionHolder;
import com.zclcs.cloud.lib.sa.token.api.utils.LoginHelper;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 数据权限处理
 *
 * @author zclcs
 * @since 2023-08-31
 */
@Slf4j
public class MyPermissionDialect extends CommonsDialectImpl {

    @Override
    public String forSelectByQuery(QueryWrapper queryWrapper) {
        String permissionColumn = DataPermissionHolder.getInstance().getPermissionColumn();
        if (permissionColumn == null) {
            log.debug("Data Permission Get PermissionColumn is null");
            return super.buildSelectSql(queryWrapper);
        }
        String username = LoginHelper.getUsernameWithNull();
        if (username == null) {
            log.debug("Data Permission Get User is null");
            return super.buildSelectSql(queryWrapper);
        }
        List<QueryTable> queryTables = CPI.getQueryTables(queryWrapper);
        if (queryTables.size() != 1) {
            log.warn("Data Permission Get QueryTable More Than One");
            return super.buildSelectSql(queryWrapper);
        }
        QueryTable queryTable = queryTables.get(0);
        String deptIdString = StrUtil.blankToDefault(SystemCacheUtil.getDeptIdStringByUsername(username), "'WITHOUT PERMISSIONS'");
        String dataPermissionSql;
        if (queryTable.getAlias() == null) {
            dataPermissionSql = String.format("`%s` in (%s)", permissionColumn,
                    deptIdString);
        } else {
            dataPermissionSql = String.format("`%s`.`%s` in (%s)", queryTable.getAlias(), permissionColumn,
                    deptIdString);
        }

        //获取当前用户信息，为 queryWrapper 添加额外的条件
        queryWrapper.and(dataPermissionSql);

        return super.buildSelectSql(queryWrapper);
    }
}
