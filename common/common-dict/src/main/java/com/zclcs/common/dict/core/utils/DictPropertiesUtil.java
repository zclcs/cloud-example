package com.zclcs.common.dict.core.utils;

import com.zclcs.common.dict.core.properties.DictProperties;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author zhouc
 */
@Getter
@Configuration(proxyBeanMethods = false)
@ComponentScan
public class DictPropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(DictPropertiesUtil.class);
    private static final String WARNING_MESSAGE = "DictProperties 未找到，请在启动类添加 @SystemDictScan 注解启用相关服务";
    private static DictProperties properties;
    private static ApplicationContext applicationContext;

    public DictPropertiesUtil(@Lazy final DictProperties properties, @Lazy final ApplicationContext applicationContext) {
        DictPropertiesUtil.properties = properties;
        DictPropertiesUtil.applicationContext = applicationContext;
    }

    public static boolean isRawValue() {
        if (properties == null) {
            logger.warn(WARNING_MESSAGE);
            return false;
        }
        return properties.isRawValue();
    }

    public static boolean isTextValueDefaultNull() {
        if (properties == null) {
            logger.warn(WARNING_MESSAGE);
            return false;
        }
        return properties.isTextValueDefaultNull();
    }

    public static boolean isMapValue() {
        if (properties == null) {
            logger.warn(WARNING_MESSAGE);
            return false;
        }
        return properties.isMapValue();
    }

    public static boolean isReplaceValue() {
        if (properties == null) {
            logger.warn(WARNING_MESSAGE);
            return false;
        }
        return properties.isReplaceValue();
    }

    public static Optional<DictProperties> properties() {
        return Optional.ofNullable(properties);
    }

    public static <U> Optional<U> get(Function<DictProperties, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (properties == null) {
            logger.warn(WARNING_MESSAGE);
            return Optional.empty();
        }
        return Optional.ofNullable(mapper.apply(properties));
    }

    public static <T> T getBean(final Class<T> clazz) {
        final String[] beanNamesForType = applicationContext.getBeanNamesForType(clazz);
        if (beanNamesForType.length == 0) {
            return null;
        }
        return applicationContext.getBean(beanNamesForType[0], clazz);
    }
}
