package com.zclcs.platform.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.platform.system.api.bean.cache.MenuCacheBean;
import com.zclcs.platform.system.api.bean.entity.Menu;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
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
     * 查询用户菜单
     *
     * @param username 用户名称
     * @return {@link MenuCacheBean}
     */
    List<MenuCacheBean> findMenuCacheBeanByUsername(@Param("username") String username);

}