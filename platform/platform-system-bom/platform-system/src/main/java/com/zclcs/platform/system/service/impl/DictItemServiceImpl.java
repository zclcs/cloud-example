package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.core.utils.TreeUtil;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemTreeVo;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.platform.system.api.bean.ao.DictItemAo;
import com.zclcs.platform.system.api.bean.vo.DictVo;
import com.zclcs.platform.system.mapper.DictItemMapper;
import com.zclcs.platform.system.service.DictItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.max;
import static com.zclcs.cloud.lib.dict.bean.entity.table.DictItemTableDef.DICT_ITEM;

/**
 * 字典项 Service实现
 *
 * @author zclcs
 * @since 2023-03-06 10:56:41.301
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    @Override
    public BasePage<DictItemVo> findDictItemPage(BasePageAo basePageAo, DictItemVo dictItemVo) {
        QueryWrapper queryWrapper = getDictItemQueryWrapper(dictItemVo);
        Page<DictItemVo> dictItemVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, DictItemVo.class);
        return new BasePage<>(dictItemVoPage);
    }

    @Override
    public List<DictItemVo> findDictItemList(DictItemVo dictItemVo) {
        QueryWrapper queryWrapper = getDictItemQueryWrapper(dictItemVo);
        return this.mapper.selectListByQueryAs(queryWrapper, DictItemVo.class);
    }

    @Override
    public DictItemVo findDictItem(DictItemVo dictItemVo) {
        QueryWrapper queryWrapper = getDictItemQueryWrapper(dictItemVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, DictItemVo.class);
    }

    @Override
    public Long countDictItem(DictItemVo dictItemVo) {
        QueryWrapper queryWrapper = getDictItemQueryWrapper(dictItemVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    @Override
    public BasePage<DictVo> findDictPage(BasePageAo basePageAo, DictVo dictVo) {
        QueryWrapper queryWrapper = getDictQueryWrapper(dictVo);
        Page<DictVo> dictVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, DictVo.class);
        return new BasePage<>(dictVoPage);
    }

    @Override
    public List<DictVo> findDictList(DictVo dictVo) {
        QueryWrapper queryWrapper = getDictQueryWrapper(dictVo);
        return this.mapper.selectListByQueryAs(queryWrapper, DictVo.class);
    }

    @Override
    public DictVo findDict(DictVo dictVo) {
        QueryWrapper queryWrapper = getDictQueryWrapper(dictVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, DictVo.class);
    }

    @Override
    public List<DictItemTreeVo> tree(String dictName) {
        List<DictItemVo> dictItemList = findDictItemList(DictItemVo.builder().dictName(dictName).build());
        List<DictItemTreeVo> trees = new ArrayList<>();
        buildTrees(trees, dictItemList);
        return (List<DictItemTreeVo>) TreeUtil.build(trees);
    }

    @Override
    public List<DictItemTreeVo> findDictItemTree(DictItemVo dictItemVo) {
        List<DictItemVo> dictItemList = findDictItemList(dictItemVo);
        List<DictItemTreeVo> trees = new ArrayList<>();
        buildTrees(trees, dictItemList);
        if (StrUtil.isNotBlank(dictItemVo.getTitle())) {
            return trees;
        } else {
            return (List<DictItemTreeVo>) TreeUtil.build(trees);
        }
    }

    @Override
    public Long countDict(DictVo dictVo) {
        QueryWrapper queryWrapper = getDictQueryWrapper(dictVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getDictItemQueryWrapper(DictItemVo dictItemVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        DICT_ITEM.ID,
                        DICT_ITEM.DICT_NAME,
                        DICT_ITEM.PARENT_VALUE,
                        DICT_ITEM.VALUE,
                        DICT_ITEM.TITLE,
                        DICT_ITEM.TYPE,
                        DICT_ITEM.WHETHER_SYSTEM_DICT,
                        DICT_ITEM.DESCRIPTION,
                        DICT_ITEM.SORTED,
                        DICT_ITEM.IS_DISABLED
                )
                .where(DICT_ITEM.DICT_NAME.likeRight(dictItemVo.getDictName(), If::hasText))
                .and(DICT_ITEM.VALUE.likeRight(dictItemVo.getValue(), If::hasText))
                .and(DICT_ITEM.PARENT_VALUE.likeRight(dictItemVo.getParentValue(), If::hasText))
                .and(DICT_ITEM.TITLE.likeRight(dictItemVo.getTitle(), If::hasText))
                .and(DICT_ITEM.ID.eq(dictItemVo.getId()))
                .orderBy(DICT_ITEM.SORTED.asc())
        ;
        return queryWrapper;
    }

    private QueryWrapper getDictQueryWrapper(DictVo dictVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        max(DICT_ITEM.ID).as("id"),
                        DICT_ITEM.DICT_NAME.as("name"),
                        DICT_ITEM.DICT_NAME.as("dictName")
                ).groupBy(DICT_ITEM.DICT_NAME)
                .where(DICT_ITEM.DICT_NAME.likeRight(dictVo.getDictName(), If::hasText))
                .orderBy(DICT_ITEM.SORTED.asc())
        ;
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictItem createDictItem(DictItemAo dictItemAo) {
        String dictName = dictItemAo.getDictName();
        String value = dictItemAo.getValue();
        String title = dictItemAo.getTitle();
        validateDictNameAndValueAndType(dictName, value, dictItemAo.getId());
        DictItem dictItem = new DictItem();
        BeanUtil.copyProperties(dictItemAo, dictItem);
        setDictItem(dictItem);
        this.save(dictItem);
        String parentValue = dictItem.getParentValue();
        DictCacheUtil.deleteDictByDictName(dictName);
        DictCacheUtil.deleteDictByDictNameAndParentValue(dictName, parentValue);
        DictCacheUtil.deleteDictItemByDictNameAndParentValueAndTitle(dictName, parentValue, title);
        DictCacheUtil.deleteDictTree(dictName);
        DictCacheUtil.deleteDictItemByDictNameAndValue(dictName, value);
        return dictItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictItem updateDictItem(DictItemAo dictItemAo) {
        String dictName = dictItemAo.getDictName();
        String value = dictItemAo.getValue();
        String title = dictItemAo.getTitle();
        validateDictNameAndValueAndType(dictName, value, dictItemAo.getId());
        DictItem dictItem = new DictItem();
        BeanUtil.copyProperties(dictItemAo, dictItem);
        setDictItem(dictItem);
        this.updateById(dictItem);
        String parentValue = dictItem.getParentValue();
        DictCacheUtil.deleteDictByDictName(dictName);
        DictCacheUtil.deleteDictByDictNameAndParentValue(dictName, parentValue);
        DictCacheUtil.deleteDictItemByDictNameAndParentValueAndTitle(dictName, parentValue, title);
        DictCacheUtil.deleteDictTree(dictName);
        DictCacheUtil.deleteDictItemByDictNameAndValue(dictName, value);
        return dictItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictItem(List<Long> ids) {
        List<DictItem> list = this.listByIds(ids);
        Object[] objects = list.stream().map(DictItem::getDictName).distinct().toArray();
        List<List<Object>> dictParents = new ArrayList<>();
        List<List<Object>> dictTitles = new ArrayList<>();
        List<List<Object>> dictValues = new ArrayList<>();
        for (DictItem dictItem : list) {
            dictParents.add(CollectionUtil.newArrayList(dictItem.getDictName(), dictItem.getParentValue()));
            dictTitles.add(CollectionUtil.newArrayList(dictItem.getDictName(), dictItem.getParentValue(), dictItem.getTitle()));
            dictValues.add(CollectionUtil.newArrayList(dictItem.getDictName(), dictItem.getValue()));
        }
        this.removeByIds(ids);
        DictCacheUtil.deleteDictByDictNames(objects);
        DictCacheUtil.deleteDictByDictNamesAndParentValues(dictParents);
        DictCacheUtil.deleteDictItemByDictNameAndParentValueAndTitles(dictTitles);
        DictCacheUtil.deleteDictItemByDictNamesAndValues(dictValues);
        DictCacheUtil.deleteDictTrees(objects);
    }

    @Override
    public void validateDictNameAndValueAndType(String dictName, String value, Long id) {
        DictItem one = this.queryChain()
                .where(DICT_ITEM.DICT_NAME.eq(dictName))
                .and(DICT_ITEM.VALUE.eq(value)).one();
        if (one != null && !one.getId().equals(id)) {
            throw new FieldException("字典项重复");
        }
    }

    public void buildTrees(List<DictItemTreeVo> trees, List<DictItemVo> dictItemVos) {
        dictItemVos.forEach(dictItemVo -> {
            DictItemTreeVo tree = new DictItemTreeVo();
            tree.setId(dictItemVo.getId());
            tree.setCode(dictItemVo.getValue());
            tree.setParentCode(dictItemVo.getParentValue());
            tree.setLabel(dictItemVo.getTitle());
            tree.setType(dictItemVo.getType());
            tree.setWhetherSystemDict(dictItemVo.getWhetherSystemDict());
            tree.setDescription(dictItemVo.getDescription());
            tree.setSorted(dictItemVo.getSorted());
            trees.add(tree);
        });
    }

    private void setDictItem(DictItem dictItem) {
        if (dictItem.getType().equals(Dict.DICT_TYPE_0)) {
            dictItem.setParentValue(CommonCore.TOP_PARENT_CODE);
        }
    }
}
