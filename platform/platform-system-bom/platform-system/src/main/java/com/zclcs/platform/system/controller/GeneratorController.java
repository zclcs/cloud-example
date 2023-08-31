package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Generator;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.GenerateAo;
import com.zclcs.platform.system.api.bean.entity.TableInfo;
import com.zclcs.platform.system.service.GeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成
 *
 * @author zclcs
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/gen")
public class GeneratorController {

    private final GeneratorService generatorService;

    /**
     * 查询代码生成数据源
     * 权限: gen:generate
     *
     * @return 数据源
     */
    @GetMapping("/datasource")
    @SaCheckPermission("gen:generate")
    public BaseRsp<List<Map<String, String>>> datasource() {
        List<String> databases = generatorService.getDatabases(null);
        List<Map<String, String>> data = new ArrayList<>();
        databases.forEach(s -> {
            Map<String, String> map = new HashMap<>(1);
            map.put("value", s);
            data.add(map);
        });
        return RspUtil.data(data);
    }

    /**
     * 查询代码生成表
     * 权限: gen:generate
     *
     * @param name       表名
     * @param datasource 库名
     * @param basePageAo 分页参数
     * @return 表定义
     */
    @GetMapping("/tables")
    @SaCheckPermission("gen:generate")
    public BaseRsp<BasePage<TableInfo>> tablesInfo(String name,
                                                   @NotBlank(message = "{required}") String datasource,
                                                   BasePageAo basePageAo) {
        BasePage<TableInfo> tables = generatorService.getTables(name, basePageAo, Generator.DATABASE_TYPE, datasource);
        return RspUtil.data(tables);
    }

    /**
     * 生成代码
     * 权限: gen:generate:gen
     *
     * @see GeneratorService#generate(GenerateAo, HttpServletResponse)
     */
    @PostMapping
    @SaCheckPermission("gen:generate:gen")
    public void generate(@Validated @RequestBody GenerateAo generateAo,
                         HttpServletResponse response) {
        generatorService.generate(generateAo, response);
    }
}
