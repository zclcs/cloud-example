package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.entity.ao.MenuAo;
import com.zclcs.platform.system.api.entity.vo.MenuVo;
import com.zclcs.platform.system.biz.mapper.MenuMapper;
import com.zclcs.platform.system.biz.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:18.238
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public BasePage<MenuVo> findMenuPage(BasePageAo basePageAo, MenuVo menuVo) {
        BasePage<MenuVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<MenuVo> findMenuList(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public MenuVo findMenu(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countMenu(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = getQueryWrapper(menuVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<MenuVo> getQueryWrapper(MenuVo menuVo) {
        QueryWrapper<MenuVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu createMenu(MenuAo menuAo) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuAo, menu);
        this.save(menu);
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu updateMenu(MenuAo menuAo) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuAo, menu);
        this.updateById(menu);
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(List<Long> ids) {
        this.removeByIds(ids);
    }
}
