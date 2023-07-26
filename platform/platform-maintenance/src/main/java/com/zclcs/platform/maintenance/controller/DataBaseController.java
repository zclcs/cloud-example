package com.zclcs.platform.maintenance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Generator;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.maintenance.bean.vo.DataBaseDataVo;
import com.zclcs.platform.maintenance.service.DataBaseService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据库控制台
 *
 * @author zclcs
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/dataBase")
public class DataBaseController {

    private final DataBaseService dataBaseService;

    /**
     * 查询schema
     * 权限: dataBase:view
     *
     * @return schema
     */
    @GetMapping("/schema")
    @SaCheckPermission("dataBase:view")
    public BaseRsp<Map<String, List<String>>> datasource() {
        Map<String, List<String>> schema = dataBaseService.getSchema(Generator.DATABASE_TYPE);
        return RspUtil.data(schema);
    }

    /**
     * sql执行
     * 只能执行select
     * 权限: dataBase:view
     *
     * @param sql sql语句
     * @return 查询结果
     */
    @GetMapping("/select")
    @SaCheckPermission("dataBase:view")
    public BaseRsp<DataBaseDataVo> datasource(@NotBlank(message = "{required}") @RequestParam String sql) {
        return RspUtil.data(dataBaseService.getData(Generator.DATABASE_TYPE, sql));
    }
}
