package com.zclcs.platform.system.biz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.entity.vo.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单 Mapper
 *
 * @author zclcs
 * @date 2023-01-10 10:39:18.238
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 分页
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @return 分页对象
     */
    BasePage<MenuVo> findPageVo(BasePage<MenuVo> basePage, @Param(Constants.WRAPPER) Wrapper<MenuVo> ew);

    /**
     * 查找集合
     *
     * @param ew 查询条件
     * @return 集合对象
     */
    List<MenuVo> findListVo(@Param(Constants.WRAPPER) Wrapper<MenuVo> ew);

    /**
     * 查找单个
     *
     * @param ew 查询条件
     * @return 对象
     */
    MenuVo findOneVo(@Param(Constants.WRAPPER) Wrapper<MenuVo> ew);

    /**
     * 统计
     *
     * @param ew 查询条件
     * @return 对象
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<MenuVo> ew);

    /**
     * 分页-用户菜单
     *
     * @param basePage 分页对象
     * @param ew       查询条件
     * @param username 用户名
     * @return 分页对象
     */
    BasePage<MenuVo> findUserMenuPageVo(BasePage<MenuVo> basePage, @Param(Constants.WRAPPER) Wrapper<MenuVo> ew, @Param("username") String username);

    /**
     * 查找集合-用户菜单
     *
     * @param ew       查询条件
     * @param username 用户名
     * @return 分页对象
     */
    List<MenuVo> findUserMenuListVo(@Param(Constants.WRAPPER) Wrapper<MenuVo> ew, @Param("username") String username);

    /**
     * 查找单个-用户菜单
     *
     * @param ew       查询条件
     * @param username 用户名
     * @return 分页对象
     */
    MenuVo findUserMenuOneVo(@Param(Constants.WRAPPER) Wrapper<MenuVo> ew, @Param("username") String username);

    /**
     * 查找-用户权限
     *
     * @param username 用户名
     * @return 权限列表
     */
    List<String> findUserPermissions(@Param("username") String username);

}