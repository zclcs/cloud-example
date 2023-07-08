package com.zclcs.platform.system.api.bean.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 构建 Vue路由
 *
 * @author zclcs
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VueRouter<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3327478146308500708L;


    private Long id;

    private String code;

    private String parentCode;

    private String path;
    private String name;
    private String component;
    private String redirect;
    private RouterMeta meta;
    private List<VueRouter<T>> children;

    @JsonIgnore
    private Boolean hasParent = false;

    @JsonIgnore
    private Boolean hasChildren = false;

    public void initChildren() {
        this.children = new ArrayList<>();
    }

}
