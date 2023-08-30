package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.ChildProjectAo;
import com.zclcs.test.test.api.bean.entity.ChildProject;
import com.zclcs.test.test.api.bean.vo.ChildProjectVo;
import com.zclcs.test.test.mapper.ChildProjectMapper;
import com.zclcs.test.test.service.ChildProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工程信息 Service实现
 *
 * @author zclcs
 * @since 2023-08-16 14:53:25.234
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ChildProjectServiceImpl extends ServiceImpl<ChildProjectMapper, ChildProject> implements ChildProjectService {

    @Override
    public BasePage<ChildProjectVo> findChildProjectPage(BasePageAo basePageAo, ChildProjectVo childProjectVo) {
        BasePage<ChildProjectVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<ChildProjectVo> queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<ChildProjectVo> findChildProjectList(ChildProjectVo childProjectVo) {
        QueryWrapper<ChildProjectVo> queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.findListVo(queryWrapper);
    }

    @Override
    public ChildProjectVo findChildProject(ChildProjectVo childProjectVo) {
        QueryWrapper<ChildProjectVo> queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countChildProject(ChildProjectVo childProjectVo) {
        QueryWrapper<ChildProjectVo> queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.countVo(queryWrapper);
    }

    private QueryWrapper<ChildProjectVo> getQueryWrapper(ChildProjectVo childProjectVo) {
        QueryWrapper<ChildProjectVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChildProject createChildProject(ChildProjectAo childProjectAo) {
        ChildProject childProject = new ChildProject();
        BeanUtil.copyProperties(childProjectAo, childProject);
        this.save(childProject);
        return childProject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChildProject updateChildProject(ChildProjectAo childProjectAo) {
        ChildProject childProject = new ChildProject();
        BeanUtil.copyProperties(childProjectAo, childProject);
        this.updateById(childProject);
        return childProject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChildProject(List<Long> ids) {
        this.removeByIds(ids);
    }
}
