package com.zclcs.nacos.config.runner;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
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
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    private final ResourcePatternResolver resourcePatternResolver;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        Resource[] overwrite = resourcePatternResolver.getResources("classpath:zip/overwrite/**.zip");
        Resource[] skip = resourcePatternResolver.getResources("classpath:zip/skip/**.zip");
        log.info("nacos配置导入开始 总配置数 {} ", overwrite.length + skip.length);
        for (Resource resource : overwrite) {
            importNacos("OVERWRITE", resource);
        }
        for (Resource resource : skip) {
            importNacos("SKIP", resource);
        }
    }

    private void importNacos(String policy, Resource resource) throws IOException {
        String nacosToken = getNacosToken();
        Map<String, Object> params = new HashMap<>();
        params.put("policy", policy);
        params.put("file", resource.getFile());
        try (HttpResponse execute = HttpUtil.createPost(getNacosEndPoint(
                        String.format("/nacos/v1/cs/configs?import=%s&namespace=%s&accessToken=%s&username=%s&tenant=%s",
                                "true", myNacosProperties.getNamespace(), nacosToken, myNacosProperties.getUsername(), myNacosProperties.getNamespace())))
                .form(params).execute()) {
            String body = execute.body();
            TypeReference<NacosBaseDataVo<NacosImportOverwriteVo>> typeReference = new TypeReference<>() {
            };
            NacosBaseDataVo<NacosImportOverwriteVo> bean = JSONUtil.toBean(body, typeReference, true);
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

    /**
     * 根据路径加载SQL资源文件
     *
     * @param basePath 相对目录
     * @return resources 集合
     */
    private List<Resource> getResourceForPath(String basePath) throws IOException {
        List<Resource> resources = new ArrayList<>();
        Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(basePath);
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            String protocol = url.getProtocol();
            if ("jar".equalsIgnoreCase(protocol)) {
                getResourceForJar(basePath, url, resources);
            } else if ("file".equalsIgnoreCase(protocol)) {
                getResourceForFile(basePath, resources);
            }
        }
        return resources;
    }

    /**
     * 解析jar包中的资源文件
     *
     * @param basePath  资源目录相关路径
     * @param url       jar的URL
     * @param resources 集合
     * @throws IOException io错误
     */
    private void getResourceForJar(String basePath, URL url, List<Resource> resources) throws IOException {
        JarURLConnection connection = (JarURLConnection) url.openConnection();
        if (connection == null) {
            return;
        }
        JarFile jarFile = connection.getJarFile();
        if (jarFile == null) {
            return;
        }
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        while (jarEntryEnumeration.hasMoreElements()) {
            JarEntry entry = jarEntryEnumeration.nextElement();
            String jarEntryName = entry.getName();
            if (jarEntryName.startsWith(basePath) && jarEntryName.endsWith("zip")) {
                ClassPathResource classPathResource = new ClassPathResource(jarEntryName);
                Resource rs = new UrlResource(classPathResource.getURL());
                resources.add(rs);
            }
        }
    }

    /**
     * 解析文件系统中的资源文件
     *
     * @param basePath  资源目录相关路径
     * @param resources 集合
     * @throws FileNotFoundException 文件未找到
     */
    private void getResourceForFile(String basePath, List<Resource> resources) throws FileNotFoundException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + basePath);
        if (file.exists() && file.isDirectory()) {
            addResourceLevel1(file, resources);
        }
    }

    private void addResourceLevel1(File file, List<Resource> resources) {
        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            if (listFile.isDirectory()) {
                for (File file1 : Objects.requireNonNull(listFile.listFiles())) {
                    addResourceLevel2(file1, resources);
                }
            } else {
                addResourceLevel2(listFile, resources);
            }
        }
    }


    private void addResourceLevel2(File file, List<Resource> resources) {
        if (file.isFile() && file.getName().endsWith("zip")) {
            Resource rs = new FileSystemResource(file);
            resources.add(rs);
        }
    }
}
