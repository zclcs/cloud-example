package com.zclcs.common.mybatis.plus.configure;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * 自定义 p6spy sql输出格式
 *
 * @author zclcs
 */
public class P6spySqlFormatConfigure implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {
        return StringUtils.isNotBlank(sql) ? " 耗时：" + elapsed + " ms " + now +
                "\n SQL 语句：" + sql.replaceAll("[\\s]+", " ") + "\n" : "";
    }
}
