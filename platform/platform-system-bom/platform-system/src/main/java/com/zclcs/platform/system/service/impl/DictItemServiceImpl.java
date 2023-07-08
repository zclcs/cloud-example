package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.utils.TreeUtil;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.bean.ao.DictItemAo;
import com.zclcs.platform.system.api.bean.vo.DictItemTreeVo;
import com.zclcs.platform.system.api.bean.vo.DictItemVo;
import com.zclcs.platform.system.api.bean.vo.DictVo;
import com.zclcs.platform.system.mapper.DictItemMapper;
import com.zclcs.platform.system.service.DictItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典项 Service实现
 *
 * @author zclcs
 * @date 2023-03-06 10:56:41.301
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    @Override
    public BasePage<DictItemVo> findDictItemPage(BasePageAo basePageAo, DictItemVo dictItemVo) {
        BasePage<DictItemVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<DictItemVo> queryWrapper = getDictItemQueryWrapper(dictItemVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<DictItemVo> findDictItemList(DictItemVo dictItemVo) {
        QueryWrapper<DictItemVo> queryWrapper = getDictItemQueryWrapper(dictItemVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public DictItemVo findDictItem(DictItemVo dictItemVo) {
        QueryWrapper<DictItemVo> queryWrapper = getDictItemQueryWrapper(dictItemVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countDictItem(DictItemVo dictItemVo) {
        QueryWrapper<DictItemVo> queryWrapper = getDictItemQueryWrapper(dictItemVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    @Override
    public BasePage<DictVo> findDictPage(BasePageAo basePageAo, DictVo dictVo) {
        BasePage<DictVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<DictVo> queryWrapper = getDictQueryWrapper(dictVo);
        BasePage<DictVo> pageDictVo = this.baseMapper.findPageDictVo(basePage, queryWrapper);
        pageDictVo.getList().forEach(this::setDict);
        return pageDictVo;
    }

    @Override
    public List<DictVo> findDictList(DictVo dictVo) {
        QueryWrapper<DictVo> queryWrapper = getDictQueryWrapper(dictVo);
        List<DictVo> listDictVo = this.baseMapper.findListDictVo(queryWrapper);
        listDictVo.forEach(this::setDict);
        return listDictVo;
    }

    @Override
    public DictVo findDict(DictVo dictVo) {
        QueryWrapper<DictVo> queryWrapper = getDictQueryWrapper(dictVo);
        DictVo oneDictVo = this.baseMapper.findOneDictVo(queryWrapper);
        setDict(oneDictVo);
        return oneDictVo;
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
    public Integer countDict(DictVo dictVo) {
        QueryWrapper<DictVo> queryWrapper = getDictQueryWrapper(dictVo);
        return this.baseMapper.countDictVo(queryWrapper);
    }

    private QueryWrapper<DictItemVo> getDictItemQueryWrapper(DictItemVo dictItemVo) {
        QueryWrapper<DictItemVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "tb.dict_name", dictItemVo.getDictName());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "tb.value", dictItemVo.getValue());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "tb.parent_value", dictItemVo.getParentValue());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "tb.title", dictItemVo.getTitle());
        QueryWrapperUtil.eqNotNull(queryWrapper, "tb.id", dictItemVo.getId());
        return queryWrapper;
    }

    private QueryWrapper<DictVo> getDictQueryWrapper(DictVo dictVo) {
        QueryWrapper<DictVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "tb.dictName", dictVo.getDictName());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictItem createDictItem(DictItemAo dictItemAo) {
        String dictName = dictItemAo.getDictName();
        String value = dictItemAo.getValue();
        validateDictNameAndValueAndType(dictName, value, dictItemAo.getId());
        DictItem dictItem = new DictItem();
        BeanUtil.copyProperties(dictItemAo, dictItem);
        setDictItem(dictItem);
        this.save(dictItem);
        String parentValue = dictItem.getParentValue();
        DictCacheUtil.deleteDictByDictName(dictName);
        DictCacheUtil.deleteDictItemByDictNameAndValue(dictName, value);
        DictCacheUtil.deleteDictByDictNameAndParentValue(dictName, parentValue);
        return dictItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictItem updateDictItem(DictItemAo dictItemAo) {
        String dictName = dictItemAo.getDictName();
        String value = dictItemAo.getValue();
        validateDictNameAndValueAndType(dictName, value, dictItemAo.getId());
        DictItem dictItem = new DictItem();
        BeanUtil.copyProperties(dictItemAo, dictItem);
        setDictItem(dictItem);
        this.updateById(dictItem);
        String parentValue = dictItem.getParentValue();
        DictCacheUtil.deleteDictByDictName(dictName);
        DictCacheUtil.deleteDictItemByDictNameAndValue(dictName, value);
        DictCacheUtil.deleteDictByDictNameAndParentValue(dictName, parentValue);
        return dictItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictItem(List<Long> ids) {
        List<DictItem> list = this.lambdaQuery().in(DictItem::getId, ids).list();
        Object[] objects = list.stream().map(DictItem::getDictName).distinct().toArray();
        List<List<Object>> dictItems = new ArrayList<>();
        List<List<Object>> dictParents = new ArrayList<>();
        for (DictItem dictItem : list) {
            dictItems.add(CollectionUtil.newArrayList(dictItem.getDictName(), dictItem.getValue()));
            dictParents.add(CollectionUtil.newArrayList(dictItem.getDictName(), dictItem.getParentValue()));
        }
        this.removeByIds(ids);
        DictCacheUtil.deleteDictByDictNames(objects);
        DictCacheUtil.deleteDictItemByDictNamesAndValues(dictItems);
        DictCacheUtil.deleteDictByDictNamesAndParentValues(dictParents);
    }

    @Override
    public void validateDictNameAndValueAndType(String dictName, String value, Long id) {
        DictItem one = this.lambdaQuery()
                .eq(DictItem::getDictName, dictName)
                .eq(DictItem::getValue, value).one();
        if (one != null && !one.getId().equals(id)) {
            throw new MyException("字典项重复");
        }
    }

    private void buildTrees(List<DictItemTreeVo> trees, List<DictItemVo> dictItemVos) {
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

    private void setDict(DictVo dictVo) {
        dictVo.setDictName(dictVo.getName());
    }
}
