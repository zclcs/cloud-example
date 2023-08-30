package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.GeneratorConfigAo;
import com.zclcs.platform.system.api.bean.entity.GeneratorConfig;
import com.zclcs.platform.system.api.bean.vo.GeneratorConfigVo;

import java.util.List;

/**
 * @author zclcs
 */
public interface GeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询（分页）
     *
     * @param basePageAo        {@link BasePageAo}
     * @param generatorConfigVo {@link GeneratorConfigVo}
     * @return {@link BasePageAo}
     */
    BasePage<GeneratorConfigVo> findGeneratorConfigPage(BasePageAo basePageAo, GeneratorConfigVo generatorConfigVo);

    /**
     * 查询（所有）
     *
     * @param generatorConfigVo {@link GeneratorConfigVo}
     * @return {@link GeneratorConfigVo}
     */
    List<GeneratorConfigVo> findGeneratorConfigList(GeneratorConfigVo generatorConfigVo);

    /**
     * 查询（单个）
     *
     * @param generatorConfigVo {@link GeneratorConfigVo}
     * @return {@link GeneratorConfigVo}
     */
    GeneratorConfigVo findGeneratorConfig(GeneratorConfigVo generatorConfigVo);

    /**
     * 查询
     *
     * @param id 配置id
     * @return {@link GeneratorConfigVo}
     */
    GeneratorConfigVo findGeneratorConfig(Long id);

    /**
     * 添加
     *
     * @param generatorConfigAo {@link GeneratorConfigAo}
     * @return {@link GeneratorConfig}
     */
    GeneratorConfig createGeneratorConfig(GeneratorConfigAo generatorConfigAo);

    /**
     * 修改
     *
     * @param generatorConfigAo {@link GeneratorConfigAo}
     * @return {@link GeneratorConfig}
     */
    GeneratorConfig updateGeneratorConfig(GeneratorConfigAo generatorConfigAo);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteGeneratorConfig(List<Long> ids);

    /**
     * 验证配置是否重复
     *
     * @param serverName 服务名
     * @param id         id
     */
    void validateServerName(String serverName, Long id);

}
