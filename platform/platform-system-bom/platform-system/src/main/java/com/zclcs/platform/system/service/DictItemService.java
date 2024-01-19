package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.bean.Tree;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemTreeVo;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemVo;
import com.zclcs.platform.system.api.bean.ao.DictItemAo;
import com.zclcs.platform.system.api.bean.vo.DictVo;

import java.util.List;

/**
 * 字典 Service接口
 *
 * @author zclcs
 * @since 2023-09-01 20:03:54.686
 */
public interface DictItemService extends IService<DictItem> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param dictItemVo {@link DictItemVo}
     * @return {@link DictItemVo}
     */
    BasePage<DictItemVo> findDictItemPage(BasePageAo basePageAo, DictItemVo dictItemVo);

    /**
     * 查询（所有）
     *
     * @param dictItemVo {@link DictItemVo}
     * @return {@link DictItemVo}
     */
    List<DictItemVo> findDictItemList(DictItemVo dictItemVo);

    /**
     * 查询（单个）
     *
     * @param dictItemVo {@link DictItemVo}
     * @return {@link DictItemVo}
     */
    DictItemVo findDictItem(DictItemVo dictItemVo);

    /**
     * 统计
     *
     * @param dictItemVo {@link DictItemVo}
     * @return 统计值
     */
    Long countDictItem(DictItemVo dictItemVo);

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param dictVo     dictVo
     * @return BasePage<DictVo>
     */
    BasePage<DictVo> findDictPage(BasePageAo basePageAo, DictVo dictVo);

    /**
     * 查询（所有）
     *
     * @param dictVo dictVo
     * @return List<DictVo>
     */
    List<DictVo> findDictList(DictVo dictVo);

    /**
     * 查询（单个）
     *
     * @param dictVo dictVo
     * @return DictVo
     */
    DictVo findDict(DictVo dictVo);

    /**
     * 获取字典树
     *
     * @return 字典树
     */
    List<Tree<DictItemTreeVo>> tree(String dictName);

    /**
     * 获取字典树
     *
     * @param dictItemVo dictItemVo
     * @return 字典树
     */
    List<Tree<DictItemTreeVo>> findDictItemTree(DictItemVo dictItemVo);

    /**
     * 统计
     *
     * @param dictVo dictVo
     * @return Integer
     */
    Long countDict(DictVo dictVo);

    /**
     * 新增
     *
     * @param dictItemAo {@link DictItemAo}
     * @return {@link DictItem}
     */
    DictItem createDictItem(DictItemAo dictItemAo);

    /**
     * 修改
     *
     * @param dictItemAo {@link DictItemAo}
     * @return {@link DictItem}
     */
    DictItem updateDictItem(DictItemAo dictItemAo);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteDictItem(List<Long> ids);

    /**
     * 验证字典是否重复
     *
     * @param dictName 字典名称
     * @param value    字典唯一值
     * @param id       字典id
     */
    void validateDictNameAndValueAndType(String dictName, String value, Long id);

}
