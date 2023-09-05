package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单 Ao
 *
 * @author zclcs
 * @since 2023-09-01 20:05:00.313
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MenuAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 目录/菜单/按钮id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long menuId;

    /**
     * 目录/菜单/按钮编码（唯一值）
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String menuCode;

    /**
     * 上级目录/菜单编码
     * 默认值：0
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String parentCode;

    /**
     * 目录/菜单/按钮名称
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String menuName;

    /**
     * 页面缓存名称
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String keepAliveName;

    /**
     * 对应路由path
     * 默认值：
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String path;

    /**
     * 对应路由组件component
     * 默认值：
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String component;

    /**
     * 打开目录重定向的子菜单
     * 默认值：
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String redirect;

    /**
     * 权限标识
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String perms;

    /**
     * 图标
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String icon;

    /**
     * 类型 @@system_menu.type
     * 默认值：0
     */
    @DictValid(value = "system_menu.type", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String type;

    /**
     * 是否隐藏菜单 @@yes_no
     * 默认值：0
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String hideMenu;

    /**
     * 是否忽略KeepAlive缓存 @@yes_no
     * 默认值：0
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String ignoreKeepAlive;

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no
     * 默认值：0
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String hideBreadcrumb;

    /**
     * 隐藏所有子菜单 @@yes_no
     * 默认值：0
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String hideChildrenInMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     * 默认值：
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String currentActiveMenu;

    /**
     * 排序
     * 默认值：0
     */
    private Double orderNum;


}