package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.dict.core.entity.DictItem;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.common.security.starter.annotation.Inner;
import com.zclcs.platform.system.api.entity.ao.DictItemAo;
import com.zclcs.platform.system.api.entity.vo.DictItemTreeVo;
import com.zclcs.platform.system.api.entity.vo.DictItemVo;
import com.zclcs.platform.system.api.entity.vo.DictVo;
import com.zclcs.platform.system.biz.service.DictItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(summary = "字典项查询（分页）")
    @PreAuthorize("hasAuthority('dictItem:view')")
    public BaseRsp<BasePage<DictItemVo>> findDictItemPage(@Validated BasePageAo basePageAo, @Validated DictItemVo dictItemVo) {
        BasePage<DictItemVo> page = this.dictItemService.findDictItemPage(basePageAo, dictItemVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @Operation(summary = "字典项查询（集合）")
    @PreAuthorize("hasAuthority('dictItem:view')")
    public BaseRsp<List<DictItemVo>> findDictItemList(@Validated DictItemVo dictItemVo) {
        List<DictItemVo> list = this.dictItemService.findDictItemList(dictItemVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @Operation(summary = "字典项查询（单个）")
    @PreAuthorize("hasAuthority('dictItem:view')")
    public BaseRsp<DictItemVo> findDictItem(@Validated DictItemVo dictItemVo) {
        DictItemVo dictItem = this.dictItemService.findDictItem(dictItemVo);
        return RspUtil.data(dictItem);
    }

    @GetMapping("/findByDictName/{dictName}")
    @Operation(summary = "根据字典唯一值查询所有字典项")
    @Inner
    public BaseRsp<List<DictItem>> findByDictName(@PathVariable String dictName) {
        List<DictItem> dictItems = this.dictItemService.lambdaQuery().eq(DictItem::getDictName, dictName).list();
        return RspUtil.data(dictItems);
    }

    @GetMapping("/findByDictNameAndValue/{dictName}/{value}")
    @Operation(summary = "根据字典唯一值、字典项唯一值查询字典项")
    @Inner
    public BaseRsp<DictItem> findByDictNameAndValue(@PathVariable String dictName, @PathVariable String value) {
        DictItem dictItem = this.dictItemService.lambdaQuery().eq(DictItem::getDictName, dictName).eq(DictItem::getValue, value).one();
        return RspUtil.data(dictItem);
    }

    @GetMapping("/findByDictNameAndParentValue/{dictName}/{parentValue}")
    @Operation(summary = "根据字典唯一值、父级字典项唯一值查询所有子级字典项")
    @Inner
    public BaseRsp<List<DictItem>> findByDictNameAndParentValue(@PathVariable String dictName, @PathVariable String parentValue) {
        List<DictItem> dictItems = this.dictItemService.lambdaQuery().eq(DictItem::getDictName, dictName).eq(DictItem::getParentValue, parentValue).list();
        return RspUtil.data(dictItems);
    }

    @GetMapping("/tree")
    @Operation(summary = "字典树查询")
    @PreAuthorize("hasAuthority('dictItem:view')")
    public BaseRsp<List<DictItemTreeVo>> findDictItemTree(@Validated DictItemVo dictItemVo) {
        List<DictItemTreeVo> dictItemTree = this.dictItemService.findDictItemTree(dictItemVo);
        return RspUtil.data(dictItemTree);
    }

    @GetMapping("/dictName")
    @Operation(summary = "字典项查询（分页）")
    @PreAuthorize("hasAuthority('dictItem:view')")
    public BaseRsp<BasePage<DictVo>> findDictPage(@Validated BasePageAo basePageAo, @Validated DictVo dictVo) {
        BasePage<DictVo> page = this.dictItemService.findDictPage(basePageAo, dictVo);
        return RspUtil.data(page);
    }

    @GetMapping("/dictName/list")
    @Operation(summary = "字典项查询（集合）")
    @PreAuthorize("hasAuthority('dictItem:view')")
    public BaseRsp<List<DictVo>> findDictItemList(@Validated DictVo dictVo) {
        List<DictVo> list = this.dictItemService.findDictList(dictVo);
        return RspUtil.data(list);
    }

    @GetMapping("/dictName/one")
    @Operation(summary = "字典项查询（单个）")
    @PreAuthorize("hasAuthority('dictItem:view')")
    public BaseRsp<DictVo> findDictItem(@Validated DictVo dictVo) {
        DictVo dictItem = this.dictItemService.findDict(dictVo);
        return RspUtil.data(dictItem);
    }

    @GetMapping("/checkValue")
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
    @PreAuthorize("hasAuthority('dictItem:add')")
    @ControllerEndpoint(operation = "新增字典项")
    @Operation(summary = "新增字典项")
    public BaseRsp<DictItem> addDictItem(@RequestBody @Validated DictItemAo dictItemAo) {
        return RspUtil.data(this.dictItemService.createDictItem(dictItemAo));
    }

    @DeleteMapping("/{dictItemIds}")
    @PreAuthorize("hasAuthority('dictItem:delete')")
    @ControllerEndpoint(operation = "删除字典项")
    @Operation(summary = "删除字典项")
    @Parameters({
            @Parameter(name = "dictItemIds", description = "字典项id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteDictItem(@NotBlank(message = "{required}") @PathVariable String dictItemIds) {
        List<Long> ids = Arrays.stream(dictItemIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.dictItemService.deleteDictItem(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('dictItem:update')")
    @ControllerEndpoint(operation = "修改字典项")
    @Operation(summary = "修改字典项")
    public BaseRsp<DictItem> updateDictItem(@RequestBody @Validated(UpdateStrategy.class) DictItemAo dictItemAo) {
        return RspUtil.data(this.dictItemService.updateDictItem(dictItemAo));
    }
}