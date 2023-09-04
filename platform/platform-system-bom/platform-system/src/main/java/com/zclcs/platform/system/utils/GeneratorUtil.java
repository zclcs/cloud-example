package com.zclcs.platform.system.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.io.Files;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.FieldType;
import com.zclcs.cloud.lib.core.constant.Generator;
import com.zclcs.cloud.lib.core.utils.BaseUtil;
import com.zclcs.platform.system.api.bean.entity.ColumnInfo;
import com.zclcs.platform.system.api.bean.vo.GeneratorConfigVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * 代码生成器工具类
 *
 * @author zclcs
 */
@Slf4j
@UtilityClass
public class GeneratorUtil {

    private String getFilePath(GeneratorConfigVo configure, String packagePath, String suffix, boolean serviceInterface) {
        String filePath = Generator.TEMP_PATH + configure.getJavaPath() +
                packageConvertPath(configure.getBasePackage() + "." + packagePath);
        filePath += configure.getClassName() + suffix;
        return filePath;
    }

    private String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    public void generateEntityFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.JAVA_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getEntityPackage(), suffix, false);
        String templateName = Generator.ENTITY_TEMPLATE;
        getDao(columnInfos, configure, path, templateName);
    }

    public void generateAoFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.AO_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getAoPackage(), suffix, false);
        String templateName = Generator.AO_TEMPLATE;
        getDao(columnInfos, configure, path, templateName);
    }

    public void generateVoFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.VO_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getVoPackage(), suffix, false);
        String templateName = Generator.VO_TEMPLATE;
        getDao(columnInfos, configure, path, templateName);
    }

    public void generateCacheVoFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.CACHE_VO_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getCacheVoPackage(), suffix, false);
        String templateName = Generator.CACHE_VO_TEMPLATE;
        getDao(columnInfos, configure, path, templateName);
    }

    public void generateExcelVoFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.EXCEL_VO_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getExcelVoPackage(), suffix, false);
        String templateName = Generator.EXCEL_VO_TEMPLATE;
        getDao(columnInfos, configure, path, templateName);
    }

    public void getDao(List<ColumnInfo> columnInfos, GeneratorConfigVo configure, String path, String templateName) throws Exception {
        File entityFile = new File(path);
        Map<String, Object> map = BeanUtil.beanToMap(configure);
        map.put("hasDate", false);
        map.put("hasBigDecimal", false);
        columnInfos.forEach(c -> {
            c.setField(BaseUtil.underscoreToCamel(c.getName().toLowerCase(Locale.ROOT)));
            c.setFieldUpperCase(c.getName().toUpperCase(Locale.ROOT));
            if (StrUtil.containsAny(c.getType(), FieldType.DATE, FieldType.DATETIME, FieldType.TIMESTAMP)) {
                map.put("hasDate", true);
            }
            if (StrUtil.containsAny(c.getType(), FieldType.DECIMAL, FieldType.NUMERIC)) {
                map.put("hasBigDecimal", true);
            }
        });
        map.put("columns", columnInfos);
        generateFileByTemplate(templateName, entityFile, map);
    }

    public void generateMapperFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.MAPPER_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getMapperPackage(), suffix, false);
        String templateName = Generator.MAPPER_TEMPLATE;
        File mapperFile = new File(path);
        generateFileByTemplate(templateName, mapperFile, BeanUtil.beanToMap(configure));
    }

    public void generateServiceFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.SERVICE_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getServicePackage(), suffix, true);
        String templateName = Generator.SERVICE_TEMPLATE;
        File serviceFile = new File(path);
        generateFileByTemplate(templateName, serviceFile, BeanUtil.beanToMap(configure));
    }

    public void generateServiceImplFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.SERVICE_IMPL_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getServiceImplPackage(), suffix, false);
        String templateName = Generator.SERVICE_IMPL_TEMPLATE;
        getDao(columnInfos, configure, path, templateName);
    }

    public void generateControllerFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.CONTROLLER_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getControllerPackage(), suffix, false);
        String templateName = Generator.CONTROLLER_TEMPLATE;
        File controllerFile = new File(path);
        generateFileByTemplate(templateName, controllerFile, BeanUtil.beanToMap(configure));
    }

    public void generateMapperXmlFile(List<ColumnInfo> columnInfos, GeneratorConfigVo configure) throws Exception {
        String suffix = Generator.MAPPER_XML_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getMapperXmlPackage(), suffix, false);
        String templateName = Generator.MAPPER_XML_TEMPLATE;
        File mapperXmlFile = new File(path);
        Map<String, Object> map = BeanUtil.beanToMap(configure);
        columnInfos.forEach(c -> c.setField(BaseUtil.underscoreToCamel(c.getName().toLowerCase(Locale.ROOT))));
        map.put("columns", columnInfos);
        generateFileByTemplate(templateName, mapperXmlFile, map);
    }

    @SuppressWarnings("all")
    public void generateFileByTemplate(String templateName, File file, Map<String, Object> data) throws Exception {
        String genVersion = (String) data.get("genVersion");
        String templateLocation = genVersion.equals("01") ? "generator/templates/plus/" : "generator/templates/flex/";
        Template template = getTemplate(templateLocation, templateName);
        Files.createParentDirs(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8), 10240)) {
            template.process(data, out);
        } catch (Exception e) {
            String message = "代码生成异常";
            log.error(message, e);
            throw new Exception(message);
        }
    }

    private Template getTemplate(String templateLocation, String templateName) throws Exception {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        String templatePath = Objects.requireNonNull(GeneratorUtil.class.getResource("/" + templateLocation)).getPath();
        File file = new File(templatePath);
        if (!file.exists()) {
            templatePath = System.getProperties().getProperty(CommonCore.JAVA_TEMP_DIR);
            FileUtil.copy(Objects.requireNonNull(GeneratorUtil.class.getClassLoader().getResource("classpath:" + templateLocation + templateName)).getPath(), templatePath + File.separator + templateName, true);
        }
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        configuration.setDefaultEncoding(CommonCore.UTF8);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return configuration.getTemplate(templateName);
    }
}
