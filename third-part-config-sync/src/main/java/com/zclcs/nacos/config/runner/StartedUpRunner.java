package com.zclcs.nacos.config.runner;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.nacos.config.vo.NacosBaseDataVo;
import com.zclcs.nacos.config.vo.NacosImportOverwriteVo;
import com.zclcs.nacos.config.vo.NacosTokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
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

    private final MyNacosProperties myNacosProperties;
    private final ResourcePatternResolver resourcePatternResolver;
    private final ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) {
        try {
            Resource[] overwrite = resourcePatternResolver.getResources("classpath:zip/skip/**.zip");
            Resource[] skip = resourcePatternResolver.getResources("classpath:zip/overwrite/**.zip");
            log.info("nacos配置导入开始 总配置数 {} ", overwrite.length + skip.length);
            for (Resource resource : overwrite) {
                importNacos("OVERWRITE", resource);
            }
            for (Resource resource : skip) {
                importNacos("SKIP", resource);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void importNacos(String policy, Resource resource) throws IOException {
        String nacosToken = getNacosToken();
        Map<String, Object> params = new HashMap<>();
        File out = new File("tmp.zip");
        FileCopyUtils.copy(FileCopyUtils.copyToByteArray(resource.getInputStream()), out);
        params.put("policy", policy);
        params.put("file", out);
        try (HttpResponse execute = HttpUtil.createPost(getNacosEndPoint(
                        String.format("/nacos/v1/cs/configs?import=%s&namespace=%s&accessToken=%s&username=%s&tenant=%s",
                                "true", myNacosProperties.getNamespace(), nacosToken, myNacosProperties.getUsername(), myNacosProperties.getNamespace())))
                .form(params).execute()) {
            String body = execute.body();
            TypeReference<NacosBaseDataVo<NacosImportOverwriteVo>> typeReference = new TypeReference<>() {
            };
            NacosBaseDataVo<NacosImportOverwriteVo> bean = objectMapper.readValue(body, typeReference);
            if (bean.success()) {
                log.info("nacos配置导入成功 配置 {} 导入策略 {} 返回 {}", resource.getFilename(), policy, body);
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
            NacosTokenVo nacosTokenVo = objectMapper.readValue(body, NacosTokenVo.class);
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
