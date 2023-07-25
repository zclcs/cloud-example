package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
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
 * @date 2023-01-10 10:39:18.238
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
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long menuId;

    /**
     * 目录/菜单/按钮编码
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String menuCode;

    /**
     * 上级目录/菜单编码 不填默认顶级
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String parentCode;

    /**
     * 目录/菜单/按钮名称
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String menuName;

    /**
     * 页面缓存名称
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String keepAliveName;

    /**
     * 对应路由path
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String path;

    /**
     * 对应路由组件component
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String component;

    /**
     * 打开目录重定向的子菜单
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String redirect;

    /**
     * 权限标识
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String perms;

    /**
     * 图标
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String icon;

    /**
     * 类型 @@system_menu.type
     */
    @Size(max = 40, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @DictValid(value = "system_menu.type")
    private String type;

    /**
     * 是否隐藏菜单 @@yes_no 默认否
     */
    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    private String hideMenu;

    /**
     * 是否忽略KeepAlive缓存 @@yes_no 默认否
     */
    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    private String ignoreKeepAlive;

    /**
     * 隐藏该路由在面包屑上面的显示 @@yes_no 默认否
     */
    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    private String hideBreadcrumb;

    /**
     * 隐藏所有子菜单 @@yes_no 默认否
     */
    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    private String hideChildrenInMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String currentActiveMenu;

    /**
     * 排序
     */
    private Double orderNum;


}