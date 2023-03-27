package com.zclcs.common.dict.core.properties;

import com.zclcs.common.dict.core.notice.RefreshDictEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 系统数据字典配置文件配置信息对象
 *
 * @author zclcs
 */
@Data
@ToString
@EqualsAndHashCode
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("system.dict")
public class DictProperties {

    /**
     * 是否显示原生数据字典值。true 实际类型转换，false 转换成字符串值
     */
    private boolean rawValue = false;

    /**
     * 是否把字典值转换成 Map 形式，包含字典值和文本。
     */
    private boolean mapValue = false;

    /**
     * 是否用字典文本替换字典值输出（在原字段输出字典文本）
     */
    private boolean replaceValue = false;

    /**
     * 字典文本的值是否默认为null，true 默认为null，false 默认为空字符串
     */
    private boolean textValueDefaultNull = false;

    /**
     * 通知其他协同系统刷新字典的MQ类型
     */
    private MqType mqType = MqType.AMQP;

    /**
     * 配置使用 Redis 来存储字典数据
     */
    private StoreType storeType = StoreType.REDIS;

    /**
     * 两次刷新字典事件的时间间隔；两次刷新事件时间间隔小于配置参数将不会刷新。
     * 此设置只影响 {@link RefreshDictEvent} 事件
     */
    private Duration refreshDictInterval = Duration.ofSeconds(60);

    /**
     * 缓存配置
     */
    @NestedConfigurationProperty
    private DictPropertiesCache cache = new DictPropertiesCache();

    /**
     * 选择所使用字节码技术。默认会使用 JAVASSIST 字节码技术，单元测试时发现使用 JAVASSIST 时执行完所有单元测试好像比 ASM 耗时更短。
     */
    private BytecodeType bytecode = BytecodeType.JAVASSIST;
}
