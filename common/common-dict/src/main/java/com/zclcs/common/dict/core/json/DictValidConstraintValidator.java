package com.zclcs.common.dict.core.json;

import cn.hutool.core.util.StrUtil;
import com.zclcs.common.dict.core.json.annotation.DictValid;
import com.zclcs.common.dict.core.utils.DictCacheUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 校验字典信息是否在字典列表中。校验字典信息的填写是否符合需求
 *
 * @author zclcs
 */
public class DictValidConstraintValidator implements ConstraintValidator<DictValid, Object> {

    private String name;

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (name == null || StrUtil.isBlankIfStr(value)) {
            return true;
        }
        return DictCacheUtil.getDictItemByDictNameAndValue(name, String.valueOf(value)) != null;
    }

    @Override
    public void initialize(final DictValid constraintAnnotation) {
        this.name = constraintAnnotation.value();
    }

}
