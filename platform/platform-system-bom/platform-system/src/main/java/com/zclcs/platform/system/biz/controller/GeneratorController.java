package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.GeneratorConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.platform.system.api.entity.Table;
import com.zclcs.platform.system.api.entity.ao.GenerateAo;
import com.zclcs.platform.system.biz.service.GeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/gen")
@Tag(name = "代码生成")
public class GeneratorController {

    private final GeneratorService generatorService;

    @GetMapping("/datasource")
    @PreAuthorize("hasAuthority('gen:generate')")
    @Operation(summary = "查询代码生成数据源")
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

    @GetMapping("/tables")
    @PreAuthorize("hasAuthority('gen:generate')")
    @Operation(summary = "查询代码生成表")
    public BaseRsp<BasePage<Table>> tablesInfo(@Parameter(name = "name", description = "表名") String name,
                                               @Parameter(name = "datasource", description = "库名") String datasource,
                                               BasePageAo request) {
        BasePage<Table> tables = generatorService.getTables(name, request, GeneratorConstant.DATABASE_TYPE, datasource);
        return RspUtil.data(tables);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('gen:generate:gen')")
    @Operation(summary = "生成代码")
    public void generate(@Validated @RequestBody GenerateAo generateAo,
                         HttpServletResponse response) {
        generatorService.generate(generateAo, response);
    }
}
