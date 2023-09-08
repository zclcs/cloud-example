package com.zclcs.cloud.lib.dict.validator;

import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.dict.annotation.DictExcelValid;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 校验字典信息是否在字典列表中。校验字典信息的填写是否符合需求
 *
 * @author zclcs
 */
public class DictExcelValidConstraintValidator implements ConstraintValidator<DictExcelValid, Object> {

    private String name;

    @Override
    public boolean isValid(final Object title, final ConstraintValidatorContext context) {
        if (name == null || StrUtil.isBlankIfStr(title)) {
            return true;
        }
        String dictTitle = String.valueOf(title);
        return DictCacheUtil.getDictValue(name, dictTitle) != null;
    }

    @Override
    public void initialize(final DictExcelValid constraintAnnotation) {
        this.name = constraintAnnotation.value();
    }

}
