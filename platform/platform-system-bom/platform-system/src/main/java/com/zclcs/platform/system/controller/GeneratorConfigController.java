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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zclcs
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/generatorConfig")
@Tag(name = "代码生成配置")
public class GeneratorConfigController {

    private final GeneratorConfigService generatorConfigService;

    @GetMapping
    @SaCheckPermission("generatorConfig:view")
    @Operation(summary = "代码生成配置查询（分页）")
    public BaseRsp<BasePage<GeneratorConfigVo>> findGeneratorConfigPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated GeneratorConfigVo generatorConfigVo) {
        BasePage<GeneratorConfigVo> page = this.generatorConfigService.findGeneratorConfigPage(basePageAo, generatorConfigVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @SaCheckPermission("generatorConfig:view")
    @Operation(summary = "代码生成配置查询（集合）")
    public BaseRsp<List<GeneratorConfigVo>> findGeneratorConfigList(@ParameterObject @Validated GeneratorConfigVo generatorConfigVo) {
        List<GeneratorConfigVo> list = this.generatorConfigService.findGeneratorConfigList(generatorConfigVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @SaCheckPermission("generatorConfig:view")
    @Operation(summary = "代码生成配置查询（单个）")
    public BaseRsp<GeneratorConfigVo> findGeneratorConfig(@ParameterObject @Validated GeneratorConfigVo generatorConfigVo) {
        GeneratorConfigVo generatorConfig = this.generatorConfigService.findGeneratorConfig(generatorConfigVo);
        return RspUtil.data(generatorConfig);
    }

    @GetMapping("/checkServerName")
    @SaCheckPermission("generatorConfig:add")
    @Operation(summary = "检查服务名是否重复")
    @Parameters({
            @Parameter(name = "id", description = "id", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "serverName", description = "服务名", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<Object> checkServerName(@RequestParam(required = false) Long id,
                                           @NotBlank(message = "{required}") @RequestParam String serverName) {
        generatorConfigService.validateServerName(serverName, id);
        return RspUtil.message();
    }

    @PostMapping
    @SaCheckPermission("generatorConfig:add")
    @ControllerEndpoint(operation = "新增代码生成配置")
    @Operation(summary = "新增代码生成配置")
    public BaseRsp<GeneratorConfig> addGeneratorConfig(@RequestBody @Validated GeneratorConfigAo generatorConfigAo) {
        return RspUtil.data(this.generatorConfigService.createGeneratorConfig(generatorConfigAo));
    }

    @DeleteMapping("/{ids}")
    @SaCheckPermission("generatorConfig:delete")
    @ControllerEndpoint(operation = "删除代码生成配置")
    @Operation(summary = "删除代码生成配置")
    @Parameters({
            @Parameter(name = "ids", description = "代码生成配置id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteGeneratorConfig(@NotBlank(message = "{required}") @PathVariable String ids) {
        List<Long> idList = Arrays.stream(ids.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.generatorConfigService.deleteGeneratorConfig(idList);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @SaCheckPermission("generatorConfig:update")
    @ControllerEndpoint(operation = "修改代码生成配置")
    @Operation(summary = "修改代码生成配置")
    public BaseRsp<GeneratorConfig> updateGeneratorConfig(@RequestBody @Validated(UpdateStrategy.class) GeneratorConfigAo generatorConfigAo) {
        return RspUtil.data(this.generatorConfigService.updateGeneratorConfig(generatorConfigAo));
    }
}
