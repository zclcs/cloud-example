package com.zclcs.third.part.database.sync.mapper;

import com.zclcs.third.part.database.sync.entity.XxlJobInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * xxl-job info
 *
 * @author xuxueli  2016-1-12 18:25:49
 */
@Component
public class XxlJobInfoMapper implements RowMapper<XxlJobInfo> {
    @Override
    public XxlJobInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ZoneId zoneId = ZoneId.systemDefault();
        long addTime = rs.getDate("add_time").getTime();
        LocalDateTime addTimeLocal = Instant.ofEpochMilli(addTime).atZone(zoneId).toLocalDateTime();
        long updateTime = rs.getDate("update_time").getTime();
        LocalDateTime updateTimeLocal = Instant.ofEpochMilli(updateTime).atZone(zoneId).toLocalDateTime();
        long glueUpdatetime = rs.getDate("glue_updatetime").getTime();
        LocalDateTime glueUpdatetimeLocal = Instant.ofEpochMilli(glueUpdatetime).atZone(zoneId).toLocalDateTime();
        return XxlJobInfo.builder()
                .id(rs.getInt("id"))
                .jobGroup(rs.getInt("job_group"))
                .jobDesc(rs.getString("job_desc"))
                .addTime(addTimeLocal)
                .updateTime(updateTimeLocal)
                .author(rs.getString("author"))
                .alarmEmail(rs.getString("alarm_email"))
                .scheduleType(rs.getString("schedule_type"))
                .scheduleConf(rs.getString("schedule_conf"))
                .misfireStrategy(rs.getString("misfire_strategy"))
                .executorRouteStrategy(rs.getString("executor_route_strategy"))
                .executorHandler(rs.getString("executor_handler"))
                .executorParam(rs.getString("executor_param"))
                .executorBlockStrategy(rs.getString("executor_block_strategy"))
                .executorTimeout(rs.getInt("executor_timeout"))
                .executorFailRetryCount(rs.getInt("executor_fail_retry_count"))
                .glueType(rs.getString("glue_type"))
                .glueSource(rs.getString("glue_source"))
                .glueRemark(rs.getString("glue_remark"))
                .glueUpdatetime(glueUpdatetimeLocal)
                .childJobId(rs.getString("child_jobid"))
                .triggerStatus(rs.getInt("trigger_status"))
                .triggerLastTime(rs.getLong("trigger_last_time"))
                .triggerNextTime(rs.getLong("trigger_next_time"))
                .build();
    }
}
