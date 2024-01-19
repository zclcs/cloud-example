package com.zclcs.cloud.lib.dict.fegin;

import com.zclcs.cloud.lib.core.bean.Tree;
import com.zclcs.cloud.lib.core.constant.ServiceName;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemTreeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteDictItemService", value = ServiceName.PLATFORM_SYSTEM_SERVICE)
public interface RemoteDictItemService {

    /**
     * 根据字典唯一值查询所有字典项
     *
     * @param dictName 字典唯一值
     * @return 所有字典项
     */
    @GetMapping(value = "/dictItem/findByDictName")
    List<DictItemCacheVo> findByDictName(@RequestParam String dictName);

    /**
     * 根据字典唯一值、字典项唯一值查询字典项
     *
     * @param dictName 字典唯一值
     * @param value    字典项唯一值
     * @return 字典项
     */
    @GetMapping(value = "/dictItem/findByDictNameAndValue")
    DictItemCacheVo findByDictNameAndValue(@RequestParam String dictName, @RequestParam String value);

    /**
     * 根据字典唯一值、字典值查询字典项
     * 重复值根据 sorted 字典正序返回第一条
     * 权限: 内部调用
     *
     * @param dictName    字典唯一值
     * @param parentValue 父字典code
     * @param title       字典值
     * @return 字典项
     */
    @GetMapping(value = "/dictItem/findByDictNameAndParentValueAndTitle")
    DictItemCacheVo findByDictNameAndParentValueAndTitle(@RequestParam String dictName, @RequestParam String parentValue, @RequestParam String title);

    /**
     * 根据字典唯一值、父级字典项唯一值查询所有子级字典项
     *
     * @param dictName    字典唯一值
     * @param parentValue 父级字典项唯一值
     * @return 所有子级字典项
     */
    @GetMapping(value = "/dictItem/findByDictNameAndParentValue")
    List<DictItemCacheVo> findByDictNameAndParentValue(@RequestParam String dictName, @RequestParam String parentValue);

    /**
     * 字典树查询
     *
     * @param dictName 字典名称
     * @return 字典树
     */
    @GetMapping(value = "/dictItem/inner/tree")
    List<Tree<DictItemTreeVo>> tree(@RequestParam String dictName);
}
