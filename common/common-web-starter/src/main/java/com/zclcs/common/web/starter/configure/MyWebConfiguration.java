package com.zclcs.common.web.starter.configure;

import com.zclcs.common.web.starter.properties.MyWebProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

/**
 * @author zclcs
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(MyWebProperties.class)
@ConditionalOnProperty(value = "my.web.enable.date.converter", havingValue = "true", matchIfMissing = true)
public class MyWebConfiguration implements WebMvcConfigurer {

    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";

    @Override
    public void addFormatters(@Nullable FormatterRegistry registry) {
        if (registry != null) {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setDateFormatter(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN));
            registrar.setTimeFormatter(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN));
            registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN));
            registrar.registerFormatters(registry);
        }
    }

    @ControllerAdvice
    public static class ControllerStringParamTrimConfig {

        /**
         * url和form表单中的参数trim
         */
        @InitBinder
        public void initBinder(WebDataBinder binder) {
            // 构造方法中 boolean 参数含义为如果是空白字符串,是否转换为null
            // 即如果为true,那么 " " 会被转换为 null,否者为 ""
            StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
            binder.registerCustomEditor(String.class, stringTrimmerEditor);
        }
    }
}
