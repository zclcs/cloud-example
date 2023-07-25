package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.GeneratorConfigAo;
import com.zclcs.platform.system.api.bean.entity.GeneratorConfig;
import com.zclcs.platform.system.api.bean.vo.GeneratorConfigVo;
import com.zclcs.platform.system.service.GeneratorConfigService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码生成配置
 *
 * @author zclcs
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/generatorConfig")
public class GeneratorConfigController {

    private final GeneratorConfigService generatorConfigService;

    /**
     * 代码生成配置查询（分页）
     * 权限: generatorConfig:view
     *
     * @see GeneratorConfigService#findGeneratorConfigPage(BasePageAo, GeneratorConfigVo)
     */
    @GetMapping
    @SaCheckPermission("generatorConfig:view")
    public BaseRsp<BasePage<GeneratorConfigVo>> findGeneratorConfigPage(@Validated BasePageAo basePageAo, @Validated GeneratorConfigVo generatorConfigVo) {
        BasePage<GeneratorConfigVo> page = this.generatorConfigService.findGeneratorConfigPage(basePageAo, generatorConfigVo);
        return RspUtil.data(page);
    }

    /**
     * 代码生成配置查询（集合）
     * 权限: generatorConfig:view
     *
     * @see GeneratorConfigService#findGeneratorConfigList(GeneratorConfigVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("generatorConfig:view")
    public BaseRsp<List<GeneratorConfigVo>> findGeneratorConfigList(@Validated GeneratorConfigVo generatorConfigVo) {
        List<GeneratorConfigVo> list = this.generatorConfigService.findGeneratorConfigList(generatorConfigVo);
        return RspUtil.data(list);
    }

    /**
     * 代码生成配置查询（单个）
     * 权限: generatorConfig:view
     *
     * @see GeneratorConfigService#findGeneratorConfig(GeneratorConfigVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("generatorConfig:view")
    public BaseRsp<GeneratorConfigVo> findGeneratorConfig(@Validated GeneratorConfigVo generatorConfigVo) {
        GeneratorConfigVo generatorConfig = this.generatorConfigService.findGeneratorConfig(generatorConfigVo);
        return RspUtil.data(generatorConfig);
    }

    /**
     * 检查服务名是否重复
     * 权限: generatorConfig:add
     *
     * @param id         id
     * @param serverName 服务名
     * @return 是否重复
     */
    @GetMapping("/checkServerName")
    @SaCheckPermission("generatorConfig:add")
    public BaseRsp<Object> checkServerName(@RequestParam(required = false) Long id,
                                           @NotBlank(message = "{required}") @RequestParam String serverName) {
        generatorConfigService.validateServerName(serverName, id);
        return RspUtil.message();
    }

    /**
     * 新增代码生成配置
     * 权限: generatorConfig:add
     *
     * @see GeneratorConfigService#createGeneratorConfig(GeneratorConfigAo)
     */
    @PostMapping
    @SaCheckPermission("generatorConfig:add")
    @ControllerEndpoint(operation = "新增代码生成配置")
    public BaseRsp<GeneratorConfig> addGeneratorConfig(@RequestBody @Validated GeneratorConfigAo generatorConfigAo) {
        return RspUtil.data(this.generatorConfigService.createGeneratorConfig(generatorConfigAo));
    }

    /**
     * 删除代码生成配置
     * 权限: generatorConfig:delete
     *
     * @param ids 代码生成配置id集合(,分隔)
     * @see GeneratorConfigService#deleteGeneratorConfig(List)
     */
    @DeleteMapping("/{ids}")
    @SaCheckPermission("generatorConfig:delete")
    @ControllerEndpoint(operation = "删除代码生成配置")
    public BaseRsp<String> deleteGeneratorConfig(@NotBlank(message = "{required}") @PathVariable String ids) {
        List<Long> idList = Arrays.stream(ids.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.generatorConfigService.deleteGeneratorConfig(idList);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改代码生成配置
     * 权限: generatorConfig:update
     *
     * @see GeneratorConfigService#updateGeneratorConfig(GeneratorConfigAo)
     */
    @PutMapping
    @SaCheckPermission("generatorConfig:update")
    @ControllerEndpoint(operation = "修改代码生成配置")
    public BaseRsp<GeneratorConfig> updateGeneratorConfig(@RequestBody @Validated(UpdateStrategy.class) GeneratorConfigAo generatorConfigAo) {
        return RspUtil.data(this.generatorConfigService.updateGeneratorConfig(generatorConfigAo));
    }
}
