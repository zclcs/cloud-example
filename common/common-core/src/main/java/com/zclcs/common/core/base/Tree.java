package com.zclcs.common.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zclcs
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Tree", description = "级联树对象")
public class Tree<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "值")
    private String label;

    @Schema(description = "子级")
    private List<Tree<T>> children;

    @Schema(description = "父id")
    private Long parentId;

    @Schema(description = "是否有父级")
    private boolean hasParent = false;

    @Schema(description = "是否有子级")
    private boolean hasChildren = false;

    public void initChildren() {
        this.children = new ArrayList<>();
    }

}
