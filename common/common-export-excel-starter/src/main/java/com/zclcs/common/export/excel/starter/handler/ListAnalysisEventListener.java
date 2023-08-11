package com.zclcs.common.export.excel.starter.handler;

import com.alibaba.excel.event.AnalysisEventListener;
import com.zclcs.common.export.excel.starter.vo.ErrorMessageVo;

import java.util.List;

/**
 * list analysis EventListener
 *
 * @author zclcs
 */
public abstract class ListAnalysisEventListener<T> extends AnalysisEventListener<T> {

    /**
     * 获取 excel 解析的对象列表
     *
     * @return 集合
     */
    public abstract List<T> getList();

    /**
     * 获取异常校验结果
     *
     * @return 集合
     */
    public abstract List<ErrorMessageVo> getErrors();

}
