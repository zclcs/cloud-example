package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.bean.entity.GeneratorConfig;
import com.zclcs.platform.system.api.bean.ao.GeneratorConfigAo;
import com.zclcs.platform.system.api.bean.vo.GeneratorConfigVo;
import com.zclcs.platform.system.mapper.GeneratorConfigMapper;
import com.zclcs.platform.system.service.GeneratorConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zclcs
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GeneratorConfigServiceImpl extends ServiceImpl<GeneratorConfigMapper, GeneratorConfig> implements GeneratorConfigService {

    @Override
    public BasePage<GeneratorConfigVo> findGeneratorConfigPage(BasePageAo basePageAo, GeneratorConfigVo generatorConfigVo) {
        BasePage<GeneratorConfigVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<GeneratorConfigVo> queryWrapper = getQueryWrapper(generatorConfigVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<GeneratorConfigVo> findGeneratorConfigList(GeneratorConfigVo generatorConfigVo) {
        QueryWrapper<GeneratorConfigVo> queryWrapper = getQueryWrapper(generatorConfigVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public GeneratorConfigVo findGeneratorConfig(GeneratorConfigVo generatorConfigVo) {
        QueryWrapper<GeneratorConfigVo> queryWrapper = getQueryWrapper(generatorConfigVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public GeneratorConfigVo findGeneratorConfig(Long id) {
        return this.findGeneratorConfig(GeneratorConfigVo.builder().id(id).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneratorConfig createGeneratorConfig(GeneratorConfigAo generatorConfigAo) {
        GeneratorConfig generatorConfig = new GeneratorConfig();
        BeanUtil.copyProperties(generatorConfigAo, generatorConfig);
        this.save(generatorConfig);
        return generatorConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneratorConfig updateGeneratorConfig(GeneratorConfigAo generatorConfigAo) {
        GeneratorConfig generatorConfig = new GeneratorConfig();
        BeanUtil.copyProperties(generatorConfigAo, generatorConfig);
        this.updateById(generatorConfig);
        return generatorConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGeneratorConfig(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void validateServerName(String serverName, Long id) {
        GeneratorConfig one = this.lambdaQuery()
                .eq(GeneratorConfig::getServerName, serverName).one();
        if (one != null && !one.getId().equals(id)) {
            throw new MyException("服务名重复");
        }
    }

    private QueryWrapper<GeneratorConfigVo> getQueryWrapper(GeneratorConfigVo generatorConfigVo) {
        QueryWrapper<GeneratorConfigVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.eqNotNull(queryWrapper, "gc.id", generatorConfigVo.getId());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "gc.server_name", generatorConfigVo.getServerName());
        return queryWrapper;
    }
}
