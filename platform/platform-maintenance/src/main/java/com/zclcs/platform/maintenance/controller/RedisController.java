package com.zclcs.platform.maintenance.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import com.zclcs.platform.maintenance.bean.vo.RedisVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * redis控制台
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Slf4j
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final static String BLOOM_FILTER = "bloom_filter";
    private final RedisService redisService;
    private final GlobalProperties globalProperties;
    private final ObjectMapper objectMapper;

    /**
     * redis key 查询（分页）
     * 权限: redis:view
     *
     * @param basePageAo {@link BasePageAo}
     * @param redisVo    {@link RedisVo}
     * @return {@link RedisVo}
     */
    @GetMapping
    @SaCheckPermission("redis:view")
    public BaseRsp<BasePage<RedisVo>> findRedisPage(@Validated BasePageAo basePageAo, @Validated RedisVo redisVo) {
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

    /**
     * redis key 查询（单个）
     * 权限: redis:view
     *
     * @param key redis Key
     * @return {@link RedisVo}
     */
    @GetMapping("/one/{key}")
    @SaCheckPermission("redis:view")
    public BaseRsp<RedisVo> findRedis(@NotBlank(message = "{required}") @PathVariable String key) {
        key = StrUtil.removePrefix(key, globalProperties.getRedisCachePrefix());
        if (redisService.hasKey(key)) {
            RedisVo redisVo = new RedisVo();
            try {
                Object value = null;
                // 序列化方法采用的是java
                if (key.contains(BLOOM_FILTER)) {
                    value = redisService.hmget(key);
                } else {
                    value = redisService.get(key);
                }
                Long tll = redisService.getTll(key);
                redisVo.setKey(globalProperties.getRedisCachePrefix() + key);
                redisVo.setValue(value);
                redisVo.setTtl(tll);
                return RspUtil.data(redisVo);
            } catch (Exception e) {
                throw new MyException("获取key值失败");
            }
        } else {
            throw new MyException("key不存在");
        }
    }

    /**
     * 删除redis值
     * 权限: redis:view
     *
     * @param keys redis key集合
     * @return 是否成功
     */
    @DeleteMapping("/{keys}")
    @SaCheckPermission("redis:delete")
    @ControllerEndpoint(operation = "删除redis值")
    public BaseRsp<String> deleteRedis(@NotBlank(message = "{required}") @PathVariable String keys) {
        if (StrUtil.containsAny(keys, BLOOM_FILTER)) {
            throw new MyException("key不能被删除");
        }
        List<String> keyList = Arrays.stream(keys.split(Strings.COMMA)).map(s ->
                StrUtil.addPrefixIfNot(s, globalProperties.getRedisCachePrefix())).toList();
        redisService.del(keyList);
        return RspUtil.message("删除成功");
    }
}
