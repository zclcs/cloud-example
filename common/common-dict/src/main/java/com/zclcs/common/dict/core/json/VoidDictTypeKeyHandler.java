package com.zclcs.common.dict.core.json;

import com.zclcs.common.dict.core.json.annotation.DictText;
import com.zclcs.common.dict.core.json.annotation.DictTypeKeyHandler;

/**
 * DictTypeKeyHandler 默认实现
 *
 * @author zclcs
 * @since 1.4.7
 */
public class VoidDictTypeKeyHandler implements DictTypeKeyHandler<Object> {
    @Override
    public String getDictName(final Object bean, final String fieldName, final String fieldValue, final DictText dictText) {
        return null;
    }
}
