package com.zclcs.cloud.lib.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.Regexp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.enums.AreaType;
import lombok.experimental.UtilityClass;
import org.springframework.core.env.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author zclcs
 */
@UtilityClass
public class BaseUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * 根据行政区划代码返回 省 市 县
     *
     * @param areaCode 行政区划代码
     * @return {@link AreaType}
     */
    public AreaType getAreaTypeByAreaCode(String areaCode) {
        if (StrUtil.endWith(areaCode, AreaType.PROVINCE.getCode())) {
            return AreaType.PROVINCE;
        }
        if (StrUtil.endWith(areaCode, AreaType.CITY.getCode())) {
            return AreaType.CITY;
        }
        return AreaType.COUNTY;
    }

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public String camelToUnderscore(String value) {
        if (StrUtil.isBlank(value)) {
            return value;
        }
        return StrUtil.toSymbolCase(value, StrUtil.C_UNDERLINE);
    }

    /**
     * 下划线转驼峰
     *
     * @param value 待转换值
     * @return 结果
     */
    public String underscoreToCamel(String value) {
        StringBuilder result = new StringBuilder();
        String[] arr = value.split(Strings.UNDER_LINE);
        for (String s : arr) {
            if (StrUtil.isNotBlank(s)) {
                result.append((String.valueOf(s.charAt(0))).toUpperCase()).append(s.substring(1));
            }
        }
        return result.toString();
    }

    /**
     * 正则校验
     *
     * @param regex 正则表达式字符串
     * @param value 要匹配的字符串
     * @return 正则校验结果
     */
    public boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 判断是否包含中文
     *
     * @param value 内容
     * @return 结果
     */
    public boolean containChinese(String value) {
        if (StrUtil.isBlank(value)) {
            return Boolean.FALSE;
        }
        Matcher matcher = Regexp.CHINESE.matcher(value);
        return matcher.find();
    }

    public void printSystemUpBanner(Environment environment) {
        String applicationName = environment.getProperty("spring.application.name");
        String serverPort = environment.getProperty("server.port");
        String banner = "-----------------------------------------\n" +
                "服务启动成功，时间：" + DateUtil.date() + "\n" +
                "服务名称：" + applicationName + "\n" +
                "端口号：" + serverPort + "\n";
        banner += "-----------------------------------------";
        System.out.println(banner);
    }
}
