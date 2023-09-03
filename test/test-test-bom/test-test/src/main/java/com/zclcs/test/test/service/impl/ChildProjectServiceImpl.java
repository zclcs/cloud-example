package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工程信息 Service实现
 *
 * @author zclcs
 * @since 2023-09-02 17:12:22.752
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChildProjectServiceImpl extends ServiceImpl<ChildProjectMapper, ChildProject> implements ChildProjectService {

    @Override
    public BasePage<ChildProjectVo> findChildProjectPage(BasePageAo basePageAo, ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
        Page<ChildProjectVo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, ChildProjectVo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<ChildProjectVo> findChildProjectList(ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.selectListByQueryAs(queryWrapper, ChildProjectVo.class);
    }

    @Override
    public ChildProjectVo findChildProject(ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, ChildProjectVo.class);
    }

    @Override
    public Long countChildProject(ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
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
