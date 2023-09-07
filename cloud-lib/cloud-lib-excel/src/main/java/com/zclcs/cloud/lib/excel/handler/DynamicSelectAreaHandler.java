package com.zclcs.cloud.lib.excel.handler;

import com.zclcs.cloud.lib.core.bean.Tree;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemTreeVo;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.common.export.excel.starter.handler.ColumnDynamicSelectDataHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zclcs 2023/1/5
 */
@Component
public class DynamicSelectAreaHandler implements ColumnDynamicSelectDataHandler<String, Map<String, List<String>>> {

    @Override
    public Function<String, Map<String, List<String>>> source(String dictName) {
        List<DictItemTreeVo> dictTree = DictCacheUtil.getDictTree(dictName);
        Map<String, List<String>> data = new HashMap<>(20);
        for (DictItemTreeVo dictItemTreeVo : dictTree) {
            if (dictItemTreeVo.isHasChildren()) {
                List<Tree<DictItemVo>> children = dictItemTreeVo.getChildren();
                for (Tree<DictItemVo> child : children) {
                    if (child.isHasChildren()) {
                        data.put(child.getLabel(), child.getChildren().stream().map(Tree::getLabel).toList());
                    }
                }
            }
        }
        return param -> data;
    }
}
