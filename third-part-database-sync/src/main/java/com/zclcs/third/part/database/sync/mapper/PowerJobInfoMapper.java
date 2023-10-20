package com.zclcs.third.part.database.sync.mapper;

import com.zclcs.third.part.database.sync.entity.JobInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * xxl-job info
 *
 * @author xuxueli  2016-1-12 18:25:49
 */
@Component
public class PowerJobInfoMapper implements RowMapper<JobInfo> {
    @Override
    public JobInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return JobInfo.builder()
                .id(rs.getLong("id"))
                .jobName(rs.getString("job_name"))
                .jobDescription(rs.getString("job_description"))
                .appId(rs.getLong("app_id"))
                .jobDescription(rs.getString("job_description"))
                .jobParams(rs.getString("job_params"))
                .timeExpressionType(rs.getInt("time_expression_type"))
                .timeExpression(rs.getString("time_expression"))
                .executeType(rs.getInt("execute_type"))
                .processorType(rs.getInt("processor_type"))
                .processorInfo(rs.getString("processor_info"))
                .maxInstanceNum(rs.getInt("max_instance_num"))
                .concurrency(rs.getInt("concurrency"))
                .instanceTimeLimit(rs.getLong("instance_time_limit"))
                .instanceRetryNum(rs.getInt("instance_retry_num"))
                .taskRetryNum(rs.getInt("task_retry_num"))
                .status(rs.getInt("status"))
                .nextTriggerTime(rs.getLong("next_trigger_time"))
                .minCpuCores(rs.getDouble("min_cpu_cores"))
                .minMemorySpace(rs.getDouble("min_memory_space"))
                .minDiskSpace(rs.getDouble("min_disk_space"))
                .designatedWorkers(rs.getString("designated_workers"))
                .maxWorkerCount(rs.getInt("max_worker_count"))
                .notifyUserIds(rs.getString("notify_user_ids"))
                .gmtCreate(rs.getDate("gmt_create"))
                .gmtModified(rs.getDate("gmt_modified"))
                .extra(rs.getString("extra"))
                .dispatchStrategy(rs.getInt("dispatch_strategy"))
                .lifecycle(rs.getString("lifecycle"))
                .alarmConfig(rs.getString("alarm_config"))
                .tag(rs.getString("tag"))
                .logConfig(rs.getString("log_config"))
                .build();
    }
}
