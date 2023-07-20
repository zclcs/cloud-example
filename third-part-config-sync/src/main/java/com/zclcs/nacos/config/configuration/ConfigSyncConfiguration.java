package com.zclcs.nacos.config.configuration;

import com.zclcs.nacos.config.vo.NacosBaseDataVo;
import com.zclcs.nacos.config.vo.NacosImportOverwriteVo;
import com.zclcs.nacos.config.vo.NacosImportSkipVo;
import com.zclcs.nacos.config.vo.NacosTokenVo;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * ip2region 自动化配置
 *
 * @author zclcs
 */
@Configuration
@ImportRuntimeHints(ConfigSyncRuntimeHintsRegistrar.class)
@RegisterReflectionForBinding({NacosTokenVo.class, NacosImportSkipVo.class, NacosImportSkipVo.SkipData.class, NacosImportOverwriteVo.class, NacosBaseDataVo.class})
public class ConfigSyncConfiguration {

}
