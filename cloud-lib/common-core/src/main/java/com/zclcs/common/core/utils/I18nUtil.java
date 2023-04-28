package com.zclcs.common.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * i18n 工具类
 *
 * @author zclcs
 */
@UtilityClass
public class I18nUtil {

    /**
     * 通过code 获取中文错误信息
     *
     * @param code
     * @return
     */
    public String getMessage(String code) {
        MessageSource messageSource = SpringContextHolderUtil.getBean("messageSource");
        return messageSource.getMessage(code, null, Locale.CHINA);
    }

    /**
     * 通过code 和参数获取中文错误信息
     *
     * @param code
     * @return
     */
    public String getMessage(String code, Object... objects) {
        MessageSource messageSource = SpringContextHolderUtil.getBean("messageSource");
        return messageSource.getMessage(code, objects, Locale.CHINA);
    }

    /**
     * security 通过code 和参数获取中文错误信息
     *
     * @param code
     * @return
     */
    public String getSecurityMessage(String code, Object... objects) {
        MessageSource messageSource = SpringContextHolderUtil.getBean("securityMessageSource");
        return messageSource.getMessage(code, objects, Locale.CHINA);
    }

}
