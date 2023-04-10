package com.zclcs.platform.system.biz.controller;

import cn.hutool.core.util.StrUtil;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.dict.core.entity.DictItem;
import com.zclcs.common.dict.core.utils.DictCacheUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统字典控制器
 *
 * @author zclcs
 * @since 1.4.1
 */
@Slf4j
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
@Tag(name = "字典缓存查询")
public class DictController {

    /**
     * 获取字典
     *
     * @param dictName 字典唯一值
     * @return 字典
     */
    @Operation(summary = "获取字典")
    @Parameters({
            @Parameter(name = "dictName", description = "字典唯一值", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "parentCode", description = "子级字典项", required = false, in = ParameterIn.QUERY),
    })
    @GetMapping(value = "/dictQuery")
    public BaseRsp<List<DictItem>> dictTypeQuery(@RequestParam(required = true) final String dictName,
                                                 @RequestParam(required = false) final String parentCode) {
        if (StrUtil.isNotBlank(parentCode)) {
            return RspUtil.data(DictCacheUtil.getDictByDictNameAndParentValue(dictName, parentCode));
        }
        return RspUtil.data(DictCacheUtil.getDictByDictName(dictName));
    }

    /**
     * 获取字典项文本
     *
     * @param dictName 字典唯一值
     * @param value    字典项唯一值
     * @return 字典项文本
     */
    @Operation(summary = "获取字典项文本")
    @Parameters({
            @Parameter(name = "dictName", description = "字典唯一值", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "value", description = "字典项唯一值", required = true, in = ParameterIn.QUERY)
    })
    @GetMapping(value = "/dictTextQuery")
    public BaseRsp<String> dictTextQuery(@RequestParam String dictName, @RequestParam String value) {
        return RspUtil.data(DictCacheUtil.getDictItemByDictNameAndValue(dictName, value).getTitle());
    }
}
