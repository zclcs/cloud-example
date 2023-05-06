package com.zclcs.cloud.lib.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.Regexp;
import com.zclcs.cloud.lib.core.constant.ServiceName;
import com.zclcs.cloud.lib.core.constant.Strings;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author zclcs
 */
@UtilityClass
@Slf4j
public class BaseUtil {

    private static final String UNKNOWN = "unknown";

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
        String gatewayHostAddress = environment.getProperty("swagger.gateway-endpoint");
        String production = environment.getProperty("knife4j.production");
        String hostAddress = "http://" + NetUtil.getLocalhost().getHostAddress() + ":" + serverPort;
        String banner = "-----------------------------------------\n" +
                "服务启动成功，时间：" + DateUtil.date() + "\n" +
                "服务名称：" + applicationName + "\n" +
                "端口号：" + serverPort + "\n";
        if (production == null) {
            if (ServiceName.PLATFORM_GATEWAY_SERVICE.equals(applicationName)) {
                banner += "knife4jUI：" + hostAddress + "/doc.html" + "\n";
            } else {
                banner += "apiDocs：" + gatewayHostAddress + "/" + applicationName + "/v3/api-docs" + "\n";
            }
        }
        banner += "-----------------------------------------";
        System.out.println(banner);
    }
}
