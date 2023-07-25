package com.zclcs.platform.system.api.bean.ao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码生成参数 Ao
 *
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GenerateAo {

    /**
     * 服务名
     */
    @NotNull(message = "{required}")
    private Long generatorConfigId;

    /**
     * 表名
     */
    @NotBlank(message = "{required}")
    private String name;

    /**
     * 库名
     */
    @NotBlank(message = "{required}")
    private String datasource;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否需要新建菜单 yes_no 默认 否
     */
    private String isCreateMenu;

    /**
     * 菜单名称 不填默认表注释
     */
    private String menuName;

    /**
     * 菜单路径 不填默认类名
     */
    private String menuPath;

    /**
     * 路由组件
     */
    private String menuComponent;

    /**
     * 上级菜单code
     */
    private String menuCode;

    /**
     * 是否创建目录 yes_no 默认 否 默认菜单在该目录下
     */
    private String isCreateDir;

    /**
     * 目录名称
     */
    private String dirName;

    /**
     * 目录路由地址
     */
    private String dirPath;

}
