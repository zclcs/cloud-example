package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.platform.system.api.bean.ao.GeneratorConfigAo;
import com.zclcs.platform.system.api.bean.entity.GeneratorConfig;
import com.zclcs.platform.system.api.bean.vo.GeneratorConfigVo;
import com.zclcs.platform.system.mapper.GeneratorConfigMapper;
import com.zclcs.platform.system.service.GeneratorConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.GeneratorConfigTableDef.GENERATOR_CONFIG;

/**
 * @author zclcs
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorConfigServiceImpl extends ServiceImpl<GeneratorConfigMapper, GeneratorConfig> implements GeneratorConfigService {

    @Override
    public BasePage<GeneratorConfigVo> findGeneratorConfigPage(BasePageAo basePageAo, GeneratorConfigVo generatorConfigVo) {
        QueryWrapper queryWrapper = getQueryWrapper(generatorConfigVo);
        Page<GeneratorConfigVo> generatorConfigVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, GeneratorConfigVo.class);
        return new BasePage<>(generatorConfigVoPage);
    }

    @Override
    public List<GeneratorConfigVo> findGeneratorConfigList(GeneratorConfigVo generatorConfigVo) {
        QueryWrapper queryWrapper = getQueryWrapper(generatorConfigVo);
        return this.mapper.selectListByQueryAs(queryWrapper, GeneratorConfigVo.class);
    }

    @Override
    public GeneratorConfigVo findGeneratorConfig(GeneratorConfigVo generatorConfigVo) {
        QueryWrapper queryWrapper = getQueryWrapper(generatorConfigVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, GeneratorConfigVo.class);
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
        GeneratorConfig one = this.queryChain().where(GENERATOR_CONFIG.SERVER_NAME.eq(serverName)).one();
        if (one != null && !one.getId().equals(id)) {
            throw new MyException("服务名重复");
        }
    }

    private QueryWrapper getQueryWrapper(GeneratorConfigVo generatorConfigVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        GENERATOR_CONFIG.ID,
                        GENERATOR_CONFIG.SERVER_NAME,
                        GENERATOR_CONFIG.AUTHOR,
                        GENERATOR_CONFIG.GEN_VERSION,
                        GENERATOR_CONFIG.BASE_PACKAGE,
                        GENERATOR_CONFIG.ENTITY_PACKAGE,
                        GENERATOR_CONFIG.AO_PACKAGE,
                        GENERATOR_CONFIG.VO_PACKAGE,
                        GENERATOR_CONFIG.CACHE_VO_PACKAGE,
                        GENERATOR_CONFIG.EXCEL_VO_PACKAGE,
                        GENERATOR_CONFIG.MAPPER_PACKAGE,
                        GENERATOR_CONFIG.MAPPER_XML_PACKAGE,
                        GENERATOR_CONFIG.SERVICE_PACKAGE,
                        GENERATOR_CONFIG.SERVICE_IMPL_PACKAGE,
                        GENERATOR_CONFIG.CONTROLLER_PACKAGE,
                        GENERATOR_CONFIG.IS_TRIM,
                        GENERATOR_CONFIG.TRIM_VALUE,
                        GENERATOR_CONFIG.EXCLUDE_COLUMNS,
                        GENERATOR_CONFIG.CREATE_AT
                )
                .where(GENERATOR_CONFIG.ID.eq(generatorConfigVo.getId()))
                .and(GENERATOR_CONFIG.SERVER_NAME.likeRight(generatorConfigVo.getServerName(), If::hasText))
        ;
        return queryWrapper;
    }
}
