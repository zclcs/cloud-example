package com.zclcs.cloud.lib.excel.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.zclcs.cloud.lib.core.bean.Tree;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemTreeVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.common.export.excel.starter.handler.ColumnDynamicSelectDataHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zclcs 2023/7/5
 */
@Component
public class DynamicSelectCityHandler implements ColumnDynamicSelectDataHandler<String, Map<String, List<String>>> {

    @Override
    public Function<String, Map<String, List<String>>> source(String dictName) {
        return param -> {
            List<Tree<DictItemTreeVo>> dictTree = DictCacheUtil.getDictTree(dictName);
            Map<String, List<String>> data = new HashMap<>(20);
            for (Tree<DictItemTreeVo> dictItemTreeVo : dictTree) {
                if (dictItemTreeVo.isHasChildren()) {
                    data.put(dictItemTreeVo.getLabel(), dictItemTreeVo.getChildren().stream().map(Tree::getLabel).toList());
                }
            }
            if (CollectionUtil.isEmpty(data)) {
                return null;
            }
            return data;
        };
    }
}
