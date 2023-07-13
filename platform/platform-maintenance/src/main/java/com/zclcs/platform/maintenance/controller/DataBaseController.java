package com.zclcs.platform.maintenance.controller;

import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Generator;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.maintenance.service.DataBaseService;
import com.zclcs.platform.system.api.bean.vo.DataBaseDataVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/dataBase")
@Tag(name = "数据库控制台")
public class DataBaseController {

    private final DataBaseService dataBaseService;

    @GetMapping("/schema")
    @PreAuthorize("hasAuthority('dataBase:view')")
    @Operation(summary = "查询schema")
    public BaseRsp<Map<String, List<String>>> datasource() {
        Map<String, List<String>> schema = dataBaseService.getSchema(Generator.DATABASE_TYPE);
        return RspUtil.data(schema);
    }

    @GetMapping("/select")
    @PreAuthorize("hasAuthority('dataBase:view')")
    @Operation(summary = "sql查询")
    @Parameters({
            @Parameter(name = "sql", description = "sql", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<DataBaseDataVo> datasource(@NotBlank(message = "{required}") @RequestParam String sql) {
        return RspUtil.data(dataBaseService.getData(Generator.DATABASE_TYPE, sql));
    }
}
