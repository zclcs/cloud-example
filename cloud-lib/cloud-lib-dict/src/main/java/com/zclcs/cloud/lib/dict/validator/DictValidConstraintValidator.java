package com.zclcs.cloud.lib.dict.validator;

import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * 校验字典信息是否在字典列表中。校验字典信息的填写是否符合需求
 *
 * @author zclcs
 */
public class DictValidConstraintValidator implements ConstraintValidator<DictValid, Object> {

    private String name;
    private boolean isArray;

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (name == null || StrUtil.isBlankIfStr(value)) {
            return true;
        }
        String dictValue = String.valueOf(value);
        if (isArray) {
            if (!StrUtil.contains(dictValue, Strings.COMMA)) {
                return false;
            } else {
                List<String> split = StrUtil.split(dictValue, Strings.COMMA);
                for (String s : split) {
                    boolean b = DictCacheUtil.getDict(name, s) == null;
                    if (b) {
                        return false;
                    }
                }
            }
        }
        return DictCacheUtil.getDict(name, dictValue) != null;
    }

    @Override
    public void initialize(final DictValid constraintAnnotation) {
        this.name = constraintAnnotation.value();
        this.isArray = constraintAnnotation.isArray();
    }

}
