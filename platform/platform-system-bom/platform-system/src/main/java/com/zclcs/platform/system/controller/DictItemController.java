package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheBean;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.ao.DictItemAo;
import com.zclcs.platform.system.api.bean.vo.DictItemTreeVo;
import com.zclcs.platform.system.api.bean.vo.DictItemVo;
import com.zclcs.platform.system.api.bean.vo.DictVo;
import com.zclcs.platform.system.service.DictItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典项 Controller
 *
 * @author zclcs
 * @date 2023-03-06 10:56:41.301
 */
@Slf4j
@RestController
@RequestMapping("/dictItem")
@RequiredArgsConstructor
@Tag(name = "字典项")
public class DictItemController {

    private final DictItemService dictItemService;

    @GetMapping
    @SaCheckPermission("dictItem:view")
    @Operation(summary = "字典项查询（分页）")
    public BaseRsp<BasePage<DictItemVo>> findDictItemPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated DictItemVo dictItemVo) {
        BasePage<DictItemVo> page = this.dictItemService.findDictItemPage(basePageAo, dictItemVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @SaCheckPermission("dictItem:view")
    @Operation(summary = "字典项查询（集合）")
    public BaseRsp<List<DictItemVo>> findDictItemList(@ParameterObject @Validated DictItemVo dictItemVo) {
        List<DictItemVo> list = this.dictItemService.findDictItemList(dictItemVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @SaCheckPermission("dictItem:view")
    @Operation(summary = "字典项查询（单个）")
    public BaseRsp<DictItemVo> findDictItem(@ParameterObject @Validated DictItemVo dictItemVo) {
        DictItemVo dictItem = this.dictItemService.findDictItem(dictItemVo);
        return RspUtil.data(dictItem);
    }

    @GetMapping("/findByDictName/{dictName}")
    @Operation(summary = "根据字典唯一值查询所有字典项")
    @Inner
    public List<DictItemCacheBean> findByDictName(@PathVariable String dictName) {
        return DictItemCacheBean.convertToDictItemCacheBeanList(this.dictItemService.lambdaQuery()
                .eq(DictItem::getDictName, dictName).list());
    }

    @GetMapping("/findByDictNameAndValue/{dictName}/{value}")
    @Operation(summary = "根据字典唯一值、字典项唯一值查询字典项")
    @Inner
    public DictItemCacheBean findByDictNameAndValue(@PathVariable String dictName, @PathVariable String value) {
        return DictItemCacheBean.convertToDictItemCacheBean(this.dictItemService.lambdaQuery()
                .eq(DictItem::getDictName, dictName)
                .eq(DictItem::getValue, value)
                .orderByAsc(DictItem::getSorted).one());
    }

    @GetMapping("/findByDictNameAndParentValue/{dictName}/{parentValue}")
    @Operation(summary = "根据字典唯一值、父级字典项唯一值查询所有子级字典项")
    @Inner
    public List<DictItemCacheBean> findByDictNameAndParentValue(@PathVariable String dictName, @PathVariable String parentValue) {
        return DictItemCacheBean.convertToDictItemCacheBeanList(this.dictItemService.lambdaQuery()
                .eq(DictItem::getDictName, dictName)
                .eq(DictItem::getParentValue, parentValue)
                .orderByAsc(DictItem::getSorted).list());
    }

    @GetMapping("/tree")
    @SaCheckPermission("dictItem:view")
    @Operation(summary = "字典树查询")
    public BaseRsp<List<DictItemTreeVo>> findDictItemTree(@Validated DictItemVo dictItemVo) {
        List<DictItemTreeVo> dictItemTree = this.dictItemService.findDictItemTree(dictItemVo);
        return RspUtil.data(dictItemTree);
    }

    @GetMapping("/dictName")
    @SaCheckPermission("dictItem:view")
    @Operation(summary = "字典项查询（分页）")
    public BaseRsp<BasePage<DictVo>> findDictPage(@Validated BasePageAo basePageAo, @Validated DictVo dictVo) {
        BasePage<DictVo> page = this.dictItemService.findDictPage(basePageAo, dictVo);
        return RspUtil.data(page);
    }

    @GetMapping("/dictName/list")
    @SaCheckPermission("dictItem:view")
    @Operation(summary = "字典项查询（集合）")
    public BaseRsp<List<DictVo>> findDictItemList(@Validated DictVo dictVo) {
        List<DictVo> list = this.dictItemService.findDictList(dictVo);
        return RspUtil.data(list);
    }

    @GetMapping("/dictName/one")
    @SaCheckPermission("dictItem:view")
    @Operation(summary = "字典项查询（单个）")
    public BaseRsp<DictVo> findDictItem(@Validated DictVo dictVo) {
        DictVo dictItem = this.dictItemService.findDict(dictVo);
        return RspUtil.data(dictItem);
    }

    @GetMapping("/checkValue")
    @SaCheckPermission(value = {"dictItem:add", "dictItem:update"}, mode = SaMode.OR)
    @Operation(summary = "检查字典项是否重复")
    @Parameters({
            @Parameter(name = "id", description = "字典项id", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "dictName", description = "字典项表名", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "value", description = "字典项唯一值", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<Object> checkValue(@RequestParam(required = false) Long id,
                                      @NotBlank(message = "{required}") @RequestParam String dictName,
                                      @NotBlank(message = "{required}") @RequestParam String value) {
        dictItemService.validateDictNameAndValueAndType(dictName, value, id);
        return RspUtil.message();
    }

    @PostMapping
    @SaCheckPermission("dictItem:add")
    @ControllerEndpoint(operation = "新增字典项")
    @Operation(summary = "新增字典项")
    public BaseRsp<DictItem> addDictItem(@RequestBody @Validated DictItemAo dictItemAo) {
        return RspUtil.data(this.dictItemService.createDictItem(dictItemAo));
    }

    @DeleteMapping("/{dictItemIds}")
    @SaCheckPermission("dictItem:delete")
    @ControllerEndpoint(operation = "删除字典项")
    @Operation(summary = "删除字典项")
    @Parameters({
            @Parameter(name = "dictItemIds", description = "字典项id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteDictItem(@NotBlank(message = "{required}") @PathVariable String dictItemIds) {
        List<Long> ids = Arrays.stream(dictItemIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.dictItemService.deleteDictItem(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @SaCheckPermission("dictItem:update")
    @ControllerEndpoint(operation = "修改字典项")
    @Operation(summary = "修改字典项")
    public BaseRsp<DictItem> updateDictItem(@RequestBody @Validated(UpdateStrategy.class) DictItemAo dictItemAo) {
        return RspUtil.data(this.dictItemService.updateDictItem(dictItemAo));
    }
}