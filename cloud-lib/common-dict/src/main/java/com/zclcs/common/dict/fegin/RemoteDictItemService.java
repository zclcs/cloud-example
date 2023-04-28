package com.zclcs.common.dict.fegin;

import com.zclcs.common.core.constant.Security;
import com.zclcs.common.core.constant.ServiceName;
import com.zclcs.common.dict.entity.DictItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @GetMapping(value = "/dictItem/findByDictName/{dictName}", headers = Security.HEADER_FROM_IN)
    List<DictItem> findByDictName(@PathVariable String dictName);

    /**
     * 根据字典唯一值、字典项唯一值查询字典项
     *
     * @param dictName 字典唯一值
     * @param value    字典项唯一值
     * @return 字典项
     */
    @GetMapping(value = "/dictItem/findByDictNameAndValue/{dictName}/{value}", headers = Security.HEADER_FROM_IN)
    DictItem findByDictNameAndValue(@PathVariable String dictName, @PathVariable String value);

    /**
     * 根据字典唯一值、父级字典项唯一值查询所有子级字典项
     *
     * @param dictName    字典唯一值
     * @param parentValue 父级字典项唯一值
     * @return 所有子级字典项
     */
    @GetMapping(value = "/dictItem/findByDictNameAndParentValue/{dictName}/{parentValue}", headers = Security.HEADER_FROM_IN)
    List<DictItem> findByDictNameAndParentValue(@PathVariable String dictName, @PathVariable String parentValue);
}
