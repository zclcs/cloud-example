package com.zclcs.platform.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.properties.GlobalProperties;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.bean.vo.RedisVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * redis Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
@Tag(name = "redis控制台")
public class RedisController {

    private final static String BLOOM_FILTER = "bloom_filter";
    private final static String TOKEN = "token";
    private final static String ROUTE = "route";
    private final RedisService redisService;
    private final RedisTemplate<String, Object> redisTemplateJava;
    private final GlobalProperties globalProperties;
    private final ObjectMapper objectMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('redis:view')")
    @Operation(summary = "redis key 查询（分页）")
    public BaseRsp<BasePage<RedisVo>> findRedisPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated RedisVo redisVo) {
        String key = globalProperties.getRedisCachePrefix() + Optional.ofNullable(redisVo.getKey()).filter(StrUtil::isNotBlank).orElse("*");
        int pageSize = basePageAo.getPageSize();
        int pageNum = basePageAo.getPageNum();
        Set<String> keys = redisService.keys(key);
        List<String> pages = null;
        if (CollectionUtil.isNotEmpty(keys)) {
            pages = new ArrayList<>(keys);
            pages = pages.stream().sorted().skip((long) (pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        }
        BasePage<RedisVo> basePage = new BasePage<>(pageNum, pageSize);
        List<RedisVo> redisVos = null;
        if (CollectionUtil.isNotEmpty(pages)) {
            redisVos = new ArrayList<>();
            for (String page : pages) {
                RedisVo vo = new RedisVo();
                vo.setKey(page);
                redisVos.add(vo);
            }
        }
        basePage.setRecords(redisVos);
        if (keys != null) {
            basePage.setTotal(keys.size());
        } else {
            basePage.setTotal(0);
        }
        return RspUtil.data(basePage);
    }

    @GetMapping("/one/{key}")
    @PreAuthorize("hasAuthority('redis:view')")
    @Operation(summary = "redis key 查询（单个）")
    @Parameters({
            @Parameter(name = "key", description = "redis key", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<RedisVo> findRedis(@NotBlank(message = "{required}") @PathVariable String key) {
        key = StrUtil.removePrefix(key, globalProperties.getRedisCachePrefix());
        if (redisService.hasKey(key)) {
            RedisVo redisVo = new RedisVo();
            try {
                Object value = null;
                // 序列化方法采用的是java
                if (key.contains(TOKEN)) {
                    value = redisTemplateJava.opsForValue().get(key);
                    //数据格式是HashTable
                } else if (key.contains(BLOOM_FILTER)) {
                    value = redisService.hmget(key);
                    //数据格式是Set
                } else if (key.contains(ROUTE)) {
                    value = redisService.sGet(key);
                } else {
                    value = redisService.get(key);
                }
                Long tll = redisService.getTll(key);
                redisVo.setKey(globalProperties.getRedisCachePrefix() + key);
                redisVo.setValue(objectMapper.writeValueAsString(value));
                redisVo.setTtl(tll);
                return RspUtil.data(redisVo);
            } catch (Exception e) {
                throw new MyException("获取key值失败");
            }
        } else {
            throw new MyException("key不存在");
        }
    }

    @DeleteMapping("/{keys}")
    @PreAuthorize("hasAuthority('redis:delete')")
    @ControllerEndpoint(operation = "删除redis值")
    @Operation(summary = "删除redis值")
    @Parameters({
            @Parameter(name = "keys", description = "redis key集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRedis(@NotBlank(message = "{required}") @PathVariable String keys) {
        if (StrUtil.containsAny(keys, BLOOM_FILTER, ROUTE)) {
            throw new MyException("key不能被删除");
        }
        List<String> keyList = Arrays.stream(keys.split(Strings.COMMA)).map(s ->
                StrUtil.addPrefixIfNot(s, globalProperties.getRedisCachePrefix())).toList();
        redisService.del(keyList);
        return RspUtil.message("删除成功");
    }
}
