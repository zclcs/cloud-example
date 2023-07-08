package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import com.zclcs.platform.system.api.bean.ao.DictItemAo;
import com.zclcs.platform.system.api.bean.vo.DictItemTreeVo;
import com.zclcs.platform.system.api.bean.vo.DictItemVo;
import com.zclcs.platform.system.api.bean.vo.DictVo;

import java.util.List;

/**
 * 字典项 Service接口
 *
 * @author zclcs
 * @date 2023-03-06 10:56:41.301
 */
public interface DictItemService extends IService<DictItem> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param dictItemVo dictItemVo
     * @return BasePage<DictItemVo>
     */
    BasePage<DictItemVo> findDictItemPage(BasePageAo basePageAo, DictItemVo dictItemVo);

    /**
     * 查询（所有）
     *
     * @param dictItemVo dictItemVo
     * @return List<DictItemVo>
     */
    List<DictItemVo> findDictItemList(DictItemVo dictItemVo);

    /**
     * 查询（单个）
     *
     * @param dictItemVo dictItemVo
     * @return DictItemVo
     */
    DictItemVo findDictItem(DictItemVo dictItemVo);

    /**
     * 统计
     *
     * @param dictItemVo dictItemVo
     * @return DictItemVo
     */
    Integer countDictItem(DictItemVo dictItemVo);

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
     * 获取字典列表树
     *
     * @param dictItemVo dictItemVo
     * @return 字典列表
     */
    List<DictItemTreeVo> findDictItemTree(DictItemVo dictItemVo);

    /**
     * 统计
     *
     * @param dictVo dictVo
     * @return Integer
     */
    Integer countDict(DictVo dictVo);

    /**
     * 新增
     *
     * @param dictItemAo dictItemAo
     * @return DictItem
     */
    DictItem createDictItem(DictItemAo dictItemAo);

    /**
     * 修改
     *
     * @param dictItemAo dictItemAo
     * @return DictItem
     */
    DictItem updateDictItem(DictItemAo dictItemAo);

    /**
     * 删除
     *
     * @param ids ids
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
