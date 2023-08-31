package com.zclcs.platform.system.controller;

import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典缓存查询
 *
 * @author zclcs
 * @since 1.4.1
 */
@Slf4j
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController {

    /**
     * 获取字典
     * 权限: 无权限
     *
     * @param dictName   字典唯一值
     * @param parentCode 字典上级code
     * @return 字典缓存集合
     */
    @GetMapping(value = "/dictQuery")
    public BaseRsp<List<DictItemCacheVo>> dictTypeQuery(@RequestParam(required = true) final String dictName,
                                                        @RequestParam(required = false) final String parentCode) {
        if (StrUtil.isNotBlank(parentCode)) {
            return RspUtil.data(DictCacheUtil.getDictByDictNameAndParentValue(dictName, parentCode));
        }
        return RspUtil.data(DictCacheUtil.getDictByDictName(dictName));
    }

    /**
     * 获取字典项文本
     * 权限: 无权限
     *
     * @param dictName 字典唯一值
     * @param value    字典项唯一值
     * @return 字典项文本
     */
    @GetMapping(value = "/dictTextQuery")
    public BaseRsp<String> dictTextQuery(@RequestParam String dictName, @RequestParam String value) {
        DictItemCacheVo dict = DictCacheUtil.getDict(dictName, value);
        return RspUtil.data(dict == null ? null : dict.getTitle());
    }
}
