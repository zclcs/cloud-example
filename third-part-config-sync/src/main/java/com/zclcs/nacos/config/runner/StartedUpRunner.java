package com.zclcs.nacos.config.runner;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.nacos.config.vo.NacosBaseDataVo;
import com.zclcs.nacos.config.vo.NacosImportVo;
import com.zclcs.nacos.config.vo.NacosTokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zclcs
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final Environment environment;
    private final MyNacosProperties myNacosProperties;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("zip/nacos_config.zip");
        String nacosToken = getNacosToken();
        Map<String, Object> params = new HashMap<>();
        params.put("policy", "OVERWRITE");
        params.put("file", classPathResource.getFile());
        try (HttpResponse execute = HttpUtil.createPost(getNacosEndPoint(
                        String.format("/nacos/v1/cs/configs?import=%s&namespace=%s&accessToken=%s&username=%s&tenant=%s",
                                "true", myNacosProperties.getNamespace(), nacosToken, myNacosProperties.getUsername(), myNacosProperties.getNamespace())))
                .form(params).execute()) {
            String body = execute.body();
            TypeReference<NacosBaseDataVo<NacosImportVo>> typeReference = new TypeReference<>() {
            };
            NacosBaseDataVo<NacosImportVo> bean = JSONUtil.toBean(body, typeReference, true);
            if (bean.success()) {
                log.info("nacos配置导入成功 导入配置条数 {} 跳过条数 {}", bean.getData().getSuccCount(), bean.getData().getSkipCount());
            } else {
                throw new MyException("nacos配置导入失败 返回 " + body);
            }
        }
    }

    private String getNacosToken() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", myNacosProperties.getUsername());
        params.put("password", myNacosProperties.getPassword());
        try (HttpResponse execute = HttpUtil.createPost(getNacosEndPoint("/nacos/v1/auth/users/login")).form(params).execute()) {
            String body = execute.body();
            NacosTokenVo nacosTokenVo = JSONUtil.toBean(body, NacosTokenVo.class);
            return nacosTokenVo.getAccessToken();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException("请求nacos token失败");
        }
    }

    private String getNacosEndPoint(String resourcePath) {
        return CommonCore.HTTP + myNacosProperties.getServerAddr() + resourcePath;
    }
}