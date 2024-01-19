package com.zclcs.cloud.lib.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 级联树对象
 *
 * @author zclcs
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 值
     */
    private String label;

    /**
     * 额外信息
     */
    private T extra;

    /**
     * 子级
     */
    private List<Tree<T>> children;

    /**
     * 是否有父级
     */
    private boolean hasParent = false;

    /**
     * 是否有子级
     */
    private boolean hasChildren = false;

    public void initChildren() {
        this.children = new ArrayList<>();
    }

}
