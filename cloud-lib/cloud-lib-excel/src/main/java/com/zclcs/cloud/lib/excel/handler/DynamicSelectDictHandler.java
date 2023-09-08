package com.zclcs.cloud.lib.excel.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.common.export.excel.starter.handler.ColumnDynamicSelectDataHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zclcs 2023/1/5
 */
@Component
public class DynamicSelectDictHandler implements ColumnDynamicSelectDataHandler<String, List<String>> {

    @Override
    public Function<String, List<String>> source(String dictName) {
        return params -> {
            List<DictItemCacheVo> dictByDictName = DictCacheUtil.getDictByDictName(dictName);
            if (CollectionUtil.isEmpty(dictByDictName)) {
                return null;
            }
            return dictByDictName.stream().map(DictItemCacheVo::getTitle).collect(Collectors.toList());
        };
    }
}
