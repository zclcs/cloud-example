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
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.ao.DictItemAo;
import com.zclcs.platform.system.api.bean.vo.DictItemTreeVo;
import com.zclcs.platform.system.api.bean.vo.DictItemVo;
import com.zclcs.platform.system.api.bean.vo.DictVo;
import com.zclcs.platform.system.service.DictItemService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.zclcs.cloud.lib.dict.bean.entity.table.DictItemTableDef.DICT_ITEM;

/**
 * 字典项
 *
 * @author zclcs
 * @since 2023-03-06 10:56:41.301
 */
@Slf4j
@RestController
@RequestMapping("/dictItem")
@RequiredArgsConstructor
public class DictItemController {

    private final DictItemService dictItemService;

    /**
     * 字典项查询（分页）
     * 权限: dictItem:view
     *
     * @see DictItemService#findDictItemPage(BasePageAo, DictItemVo)
     */
    @GetMapping
    @SaCheckPermission("dictItem:view")
    public BaseRsp<BasePage<DictItemVo>> findDictItemPage(@Validated BasePageAo basePageAo, @Validated DictItemVo dictItemVo) {
        BasePage<DictItemVo> page = this.dictItemService.findDictItemPage(basePageAo, dictItemVo);
        return RspUtil.data(page);
    }

    /**
     * 字典项查询（集合）
     * 权限: dictItem:view
     *
     * @see DictItemService#findDictItemList(DictItemVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("dictItem:view")
    public BaseRsp<List<DictItemVo>> findDictItemList(@Validated DictItemVo dictItemVo) {
        List<DictItemVo> list = this.dictItemService.findDictItemList(dictItemVo);
        return RspUtil.data(list);
    }

    /**
     * 字典项查询（单个）
     * 权限: dictItem:view
     *
     * @see DictItemService#findDictItem(DictItemVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("dictItem:view")
    public BaseRsp<DictItemVo> findDictItem(@Validated DictItemVo dictItemVo) {
        DictItemVo dictItem = this.dictItemService.findDictItem(dictItemVo);
        return RspUtil.data(dictItem);
    }

    /**
     * 根据字典唯一值查询所有字典项
     *
     * @param dictName 字典唯一值
     * @return 所有字典项
     */
    @GetMapping("/findByDictName")
    @Inner
    public List<DictItemCacheVo> findByDictName(@RequestParam @NotBlank(message = "{required}") String dictName) {
        return this.dictItemService.queryChain().where(DICT_ITEM.DICT_NAME.eq(dictName)).listAs(DictItemCacheVo.class);
    }

    /**
     * 根据字典唯一值、字典项唯一值查询字典项
     *
     * @param dictName 字典唯一值
     * @param value    字典项唯一值
     * @return 字典项
     */
    @GetMapping("/findByDictNameAndValue")
    @Inner
    public DictItemCacheVo findByDictNameAndValue(@RequestParam @NotBlank(message = "{required}") String dictName,
                                                  @RequestParam @NotBlank(message = "{required}") String value) {
        return this.dictItemService.queryChain().where(DICT_ITEM.DICT_NAME.eq(dictName))
                .and(DICT_ITEM.VALUE.eq(value))
                .orderBy(DICT_ITEM.SORTED.asc())
                .oneAs(DictItemCacheVo.class);
    }

    /**
     * 根据字典唯一值、父级字典项唯一值查询所有子级字典项
     *
     * @param dictName    字典唯一值
     * @param parentValue 父级字典项唯一值
     * @return 字典项
     */
    @GetMapping("/findByDictNameAndParentValue")
    @Inner
    public List<DictItemCacheVo> findByDictNameAndParentValue(@RequestParam @NotBlank(message = "{required}") String dictName,
                                                              @RequestParam @NotBlank(message = "{required}") String parentValue) {
        return this.dictItemService.queryChain().where(DICT_ITEM.DICT_NAME.eq(dictName))
                .and(DICT_ITEM.PARENT_VALUE.eq(parentValue))
                .orderBy(DICT_ITEM.SORTED.asc())
                .listAs(DictItemCacheVo.class);
    }

    /**
     * 字典树查询
     * 权限: dictItem:view
     *
     * @see DictItemService#findDictItemTree(DictItemVo)
     */
    @GetMapping("/tree")
    @SaCheckPermission("dictItem:view")
    public BaseRsp<List<DictItemTreeVo>> findDictItemTree(@Validated DictItemVo dictItemVo) {
        List<DictItemTreeVo> dictItemTree = this.dictItemService.findDictItemTree(dictItemVo);
        return RspUtil.data(dictItemTree);
    }

    /**
     * 字典项查询（分页）
     * 权限: dictItem:view
     *
     * @see DictItemService#findDictPage(BasePageAo, DictVo)
     */
    @GetMapping("/dictName")
    @SaCheckPermission("dictItem:view")
    public BaseRsp<BasePage<DictVo>> findDictPage(@Validated BasePageAo basePageAo, @Validated DictVo dictVo) {
        BasePage<DictVo> page = this.dictItemService.findDictPage(basePageAo, dictVo);
        return RspUtil.data(page);
    }

    /**
     * 字典项查询（集合）
     * 权限: dictItem:view
     *
     * @see DictItemService#findDictList(DictVo)
     */
    @GetMapping("/dictName/list")
    @SaCheckPermission("dictItem:view")
    public BaseRsp<List<DictVo>> findDictItemList(@Validated DictVo dictVo) {
        List<DictVo> list = this.dictItemService.findDictList(dictVo);
        return RspUtil.data(list);
    }

    /**
     * 字典项查询（单个）
     * 权限: dictItem:view
     *
     * @see DictItemService#findDictList(DictVo)
     */
    @GetMapping("/dictName/one")
    @SaCheckPermission("dictItem:view")
    public BaseRsp<DictVo> findDictItem(@Validated DictVo dictVo) {
        DictVo dictItem = this.dictItemService.findDict(dictVo);
        return RspUtil.data(dictItem);
    }

    /**
     * 检查字典项是否重复
     * 权限: dictItem:add 或者 dictItem:update
     *
     * @param id       字典项id
     * @param dictName 字典项表名
     * @param value    字典项唯一值
     * @return 是否重复
     */
    @GetMapping("/checkValue")
    @SaCheckPermission(value = {"dictItem:add", "dictItem:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkValue(@RequestParam(required = false) Long id,
                                      @NotBlank(message = "{required}") @RequestParam String dictName,
                                      @NotBlank(message = "{required}") @RequestParam String value) {
        dictItemService.validateDictNameAndValueAndType(dictName, value, id);
        return RspUtil.message();
    }

    /**
     * 新增字典项
     * 权限: dictItem:add
     *
     * @see DictItemService#createDictItem(DictItemAo)
     */
    @PostMapping
    @SaCheckPermission("dictItem:add")
    @ControllerEndpoint(operation = "新增字典项")
    public BaseRsp<DictItem> addDictItem(@RequestBody @Validated DictItemAo dictItemAo) {
        return RspUtil.data(this.dictItemService.createDictItem(dictItemAo));
    }

    /**
     * 删除字典项
     * 权限: dictItem:delete
     *
     * @param dictItemIds 字典项id集合(,分隔)
     * @see DictItemService#createDictItem(DictItemAo)
     */
    @DeleteMapping("/{dictItemIds}")
    @SaCheckPermission("dictItem:delete")
    @ControllerEndpoint(operation = "删除字典项")
    public BaseRsp<String> deleteDictItem(@NotBlank(message = "{required}") @PathVariable String dictItemIds) {
        List<Long> ids = Arrays.stream(dictItemIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.dictItemService.deleteDictItem(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改字典项
     * 权限: dictItem:update
     *
     * @see DictItemService#createDictItem(DictItemAo)
     */
    @PutMapping
    @SaCheckPermission("dictItem:update")
    @ControllerEndpoint(operation = "修改字典项")
    public BaseRsp<DictItem> updateDictItem(@RequestBody @Validated(UpdateStrategy.class) DictItemAo dictItemAo) {
        return RspUtil.data(this.dictItemService.updateDictItem(dictItemAo));
    }
}