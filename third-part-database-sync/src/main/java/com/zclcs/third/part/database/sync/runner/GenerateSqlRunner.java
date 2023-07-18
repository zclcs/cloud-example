package com.zclcs.third.part.database.sync.runner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.common.db.merge.starter.properties.MyThirdPartDbMergeProperties;
import com.zclcs.third.part.database.sync.entity.XxlJobInfo;
import com.zclcs.third.part.database.sync.enums.Servers;
import com.zclcs.third.part.database.sync.mapper.XxlJobInfoMapper;
import com.zclcs.third.part.database.sync.properties.ThirdPartDatabaseSyncProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zclcs
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class GenerateSqlRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final XxlJobInfoMapper xxlJobInfoMapper;
    private final MyThirdPartDbMergeProperties myThirdPartDbMergeProperties;
    private final ThirdPartDatabaseSyncProperties thirdPartDatabaseSyncProperties;
    private final MyNacosProperties myNacosProperties;
    private DataSource xxlJobDataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setXxlJobDataSource(DataSource xxlJobDataSource) {
        this.xxlJobDataSource = xxlJobDataSource;
        jdbcTemplate = new JdbcTemplate(xxlJobDataSource);
    }

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive() && thirdPartDatabaseSyncProperties.getGenerateSql()) {
            StringBuilder sql = new StringBuilder();
            for (Servers value : Servers.values()) {
                String serverName = value.getServerName();
                String desc = value.getDesc();
                sql.append(getXxlJobGroupSql(serverName, desc));
                sql.append(getXxlJobInfoSql(serverName));
            }
            writeSql(sql.toString(), "1.0.3-data.sql");
        }
    }

    private String getXxlJobInfoSql(String serverName) {
        String serverNameFill = "{{NACOS_NAMESPACE}}-" + serverName;
        serverName = myNacosProperties.getNamespace() + "-" + serverName;
        StringBuilder sqlBuilder = new StringBuilder();
        List<XxlJobInfo> xxlJobInfos = jdbcTemplate.query("select * from xxl_job_info where job_group = (select id from xxl_job_group where app_name = ?)", xxlJobInfoMapper, serverName);
        for (XxlJobInfo xxlJobInfo : xxlJobInfos) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
            String jobGroupIdSql = "(select id from xxl_job_group where app_name = " + getValue(serverNameFill) + ")";
            String values = String.format(valuesFill, jobGroupIdSql
                    , getValue(xxlJobInfo.getJobDesc()), "now()", "now()", getValue(xxlJobInfo.getAuthor())
                    , getValue(xxlJobInfo.getAlarmEmail()), getValue(xxlJobInfo.getScheduleType())
                    , getValue(xxlJobInfo.getScheduleConf()), getValue(xxlJobInfo.getMisfireStrategy())
                    , getValue(xxlJobInfo.getExecutorRouteStrategy()), getValue(xxlJobInfo.getExecutorHandler())
                    , getValue(xxlJobInfo.getExecutorParam()), getValue(xxlJobInfo.getExecutorBlockStrategy())
                    , xxlJobInfo.getExecutorTimeout(), xxlJobInfo.getExecutorFailRetryCount()
                    , getValue(xxlJobInfo.getGlueType()), getValue(xxlJobInfo.getGlueSource())
                    , getValue(xxlJobInfo.getGlueRemark()), "now()"
                    , getValue(xxlJobInfo.getChildJobId()), "1", "0", "0");
            String uniqueSqlFill = "job_group=%s and executor_handler=%s";
            String uniqueSql = String.format(uniqueSqlFill, jobGroupIdSql, getValue(xxlJobInfo.getExecutorHandler()));
            String sql = String.format(sqlFill, "xxl_job_info",
                    """
                            job_group,job_desc,add_time,update_time,author,alarm_email,schedule_type,schedule_conf,misfire_strategy,
                            executor_route_strategy,executor_handler,executor_param,executor_block_strategy,executor_timeout,executor_fail_retry_count,
                            glue_type,glue_source,glue_remark,glue_updatetime,child_jobid,trigger_status,trigger_last_time,trigger_next_time
                            """, values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getXxlJobGroupSql(String serverName, String desc) {
        serverName = "{{NACOS_NAMESPACE}}-" + serverName;
        StringBuilder sqlBuilder = new StringBuilder();
        String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                "'%s'," +
                "'%s'," +
                "'%s');";
        String valuesFill = "%s,%s,%s,%s,%s";
        String values = String.format(valuesFill, getValue(serverName)
                , getValue(desc), "0"
                , "NULL", "now()");
        String uniqueSqlFill = "app_name=%s";
        String uniqueSql = String.format(uniqueSqlFill, getValue(serverName));
        String sql = String.format(sqlFill, "xxl_job_group",
                "app_name,title,address_type,address_list,update_time", values, uniqueSql);
        sqlBuilder.append(sql).append("\n");
        return sqlBuilder.toString();

    }

    private void writeSql(String sql, String fileName) {
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String finalPath = applicationHome.getDir().getParentFile().getParentFile()
                .getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\"
                + StrUtil.replace(myThirdPartDbMergeProperties.getXxlJobSql(), "/", "\\") + fileName;
        FileUtil.touch(finalPath);
        FileUtil.writeString(sql, finalPath, StandardCharsets.UTF_8);
    }

    private Object getValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String s) {
            return "\"" + s + "\"";
        } else {
            return value;
        }
    }
}
