package com.zclcs.third.part.database.sync.runner;

import cn.hutool.core.io.FileUtil;
import com.zclcs.cloud.lib.core.enums.ServerName;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.third.part.database.sync.entity.JobInfo;
import com.zclcs.third.part.database.sync.mapper.PowerJobInfoMapper;
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
    private final PowerJobInfoMapper jobInfoMapper;
    private final ThirdPartDatabaseSyncProperties thirdPartDatabaseSyncProperties;
    private final MyNacosProperties myNacosProperties;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setXxlJobDataSource(DataSource powerJobDataSource) {
        jdbcTemplate = new JdbcTemplate(powerJobDataSource);
    }

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive() && thirdPartDatabaseSyncProperties.getGenerateSql()) {
            StringBuilder sql = new StringBuilder();
            for (ServerName value : ServerName.values()) {
                String serverName = value.getServerName();
                sql.append(getPowerAppInfoSql(serverName));
                sql.append(getPowerJobInfoSql(serverName));
            }
            writeSql(sql.toString(), "1.0.2-data.sql");
        }
    }

    private String getPowerJobInfoSql(String serverName) {
        String serverNameFill = "{{NACOS_NAMESPACE}}-" + serverName;
        serverName = myNacosProperties.getNamespace() + "-" + serverName;
        StringBuilder sqlBuilder = new StringBuilder();
        List<JobInfo> jobInfos = jdbcTemplate.query("select * from job_info where app_id = (select id from app_info where app_name = ?)", jobInfoMapper, serverName);
        for (JobInfo jobInfo : jobInfos) {
            String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                    "'%s'," +
                    "'%s'," +
                    "'%s');";
            String valuesFill = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";
            String jobGroupIdSql = "(select id from app_info where app_name = " + getValue(serverNameFill) + ")";
            String values = String.format(valuesFill, jobGroupIdSql
                    , getValue(jobInfo.getConcurrency()), getValue(jobInfo.getDesignatedWorkers())
                    , getValue(jobInfo.getDispatchStrategy()), getValue(jobInfo.getExecuteType())
                    , getValue(jobInfo.getExtra()), "now()", "now()", getValue(jobInfo.getInstanceRetryNum())
                    , getValue(jobInfo.getInstanceTimeLimit()), getValue(jobInfo.getJobDescription())
                    , getValue(jobInfo.getJobName()), getValue(jobInfo.getJobParams())
                    , getValue(jobInfo.getMaxInstanceNum()), getValue(jobInfo.getMaxWorkerCount())
                    , getValue(jobInfo.getMinCpuCores()), getValue(jobInfo.getMinDiskSpace())
                    , getValue(jobInfo.getMinMemorySpace()), "null"
                    , getValue(jobInfo.getNotifyUserIds()), getValue(jobInfo.getProcessorInfo())
                    , getValue(jobInfo.getProcessorType()), getValue(jobInfo.getStatus())
                    , getValue(jobInfo.getTag()), getValue(jobInfo.getTaskRetryNum())
                    , getValue(jobInfo.getTimeExpression()), getValue(jobInfo.getTimeExpressionType()));
            String uniqueSqlFill = "app_id=%s and job_name=%s";
            String uniqueSql = String.format(uniqueSqlFill, jobGroupIdSql, getValue(jobInfo.getJobName()));
            String sql = String.format(sqlFill, "job_info",
                    """
                            app_id, concurrency, designated_workers, dispatch_strategy, execute_type, extra,
                            gmt_create, gmt_modified, instance_retry_num, instance_time_limit, job_description, job_name,
                            job_params, max_instance_num, max_worker_count, min_cpu_cores, min_disk_space,
                            min_memory_space, next_trigger_time, notify_user_ids, processor_info, processor_type,
                            status, tag, task_retry_num, time_expression, time_expression_type
                            """, values, uniqueSql);
            sqlBuilder.append(sql).append("\n");
        }
        return sqlBuilder.toString();
    }

    private String getPowerAppInfoSql(String serverName) {
        serverName = "{{NACOS_NAMESPACE}}-" + serverName;
        StringBuilder sqlBuilder = new StringBuilder();
        String sqlFill = "call insert_if_not_exists(database(), '%s', " +
                "'%s'," +
                "'%s'," +
                "'%s');";
        String valuesFill = "%s,%s,%s,%s";
        String values = String.format(valuesFill, getValue(serverName)
                , "now()", "now()"
                , "123456");
        String uniqueSqlFill = "app_name=%s";
        String uniqueSql = String.format(uniqueSqlFill, getValue(serverName));
        String sql = String.format(sqlFill, "app_info",
                "app_name,gmt_create,gmt_modified,password", values, uniqueSql);
        sqlBuilder.append(sql).append("\n");
        return sqlBuilder.toString();

    }

    private void writeSql(String sql, String fileName) {
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String finalPath = applicationHome.getDir().getParentFile().getParentFile()
                .getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\"
                + "sql\\power-job\\" + fileName;
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
