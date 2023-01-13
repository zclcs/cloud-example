package com.zclcs.common.datasource.starter.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zclcs.common.core.base.BaseFieldOrder;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.utils.BaseUtil;

import java.util.List;

/**
 * 处理排序工具类
 *
 * @author zclcs
 */
public abstract class BaseFieldSortUtil {

    /**
     * 处理排序 for mybatis-plus
     *
     * @param basePageAo        BasePageAo
     * @param wrapper           wrapper
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(BasePageAo basePageAo, QueryWrapper<?> wrapper, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
        List<BaseFieldOrder> fieldOrders = basePageAo.getFieldOrders();
        if (CollectionUtil.isNotEmpty(basePageAo.getFieldOrders())) {
            for (BaseFieldOrder fieldOrder : fieldOrders) {
                if (!StrUtil.isAllBlank(fieldOrder.getField(), fieldOrder.getOrder())) {
                    String sortField = camelToUnderscore ? BaseUtil.camelToUnderscore(fieldOrder.getField()) : fieldOrder.getField();
                    if (StrUtil.equals(fieldOrder.getOrder(), MyConstant.ORDER_DESC)) {
                        wrapper.orderByDesc(sortField);
                    } else {
                        wrapper.orderByAsc(sortField);
                    }
                }
            }
        } else {
            if (StrUtil.isNotBlank(defaultSort)) {
                defaultSort = camelToUnderscore ? BaseUtil.camelToUnderscore(defaultSort) : defaultSort;
                if (StrUtil.equals(defaultOrder, MyConstant.ORDER_DESC)) {
                    wrapper.orderByDesc(defaultSort);
                } else {
                    wrapper.orderByAsc(defaultSort);
                }
            }
        }
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param basePageAo BasePageAo
     * @param wrapper    wrapper
     */
    public static void handleWrapperSort(BasePageAo basePageAo, QueryWrapper<?> wrapper) {
        handleWrapperSort(basePageAo, wrapper, null, null, false);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param basePageAo        BasePageAo
     * @param wrapper           wrapper
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(BasePageAo basePageAo, QueryWrapper<?> wrapper, boolean camelToUnderscore) {
        handleWrapperSort(basePageAo, wrapper, null, null, camelToUnderscore);
    }
}
