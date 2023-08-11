package com.zclcs.cloud.lib.dict.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 系统数据字典配置文件配置信息对象
 *
 * @author zclcs
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties("my.dict")
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
     * 选择所使用字节码技术。默认会使用 JAVASSIST 字节码技术，单元测试时发现使用 JAVASSIST 时执行完所有单元测试好像比 ASM 耗时更短。
     */
    private BytecodeType bytecode = BytecodeType.JAVASSIST;
}
