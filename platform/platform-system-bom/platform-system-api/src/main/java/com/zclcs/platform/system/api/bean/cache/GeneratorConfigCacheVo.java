package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代码生成配置 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 20:04:43.904
 */
@Data
public class GeneratorConfigCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 服务名
     */
    private String serverName;

    /**
     * 作者
     */
    private String author;

    /**
     * 基础包名
     */
    private String basePackage;

    /**
     * entity文件存放路径
     */
    private String entityPackage;

    /**
     * 入参
     */
    private String aoPackage;

    /**
     * 出参
     */
    private String voPackage;

    /**
     * mapper文件存放路径
     */
    private String mapperPackage;

    /**
     * mapper xml文件存放路径
     */
    private String mapperXmlPackage;

    /**
     * service文件存放路径
     */
    private String servicePackage;

    /**
     * serviceImpl文件存放路径
     */
    private String serviceImplPackage;

    /**
     * controller文件存放路径
     */
    private String controllerPackage;

    /**
     * 是否去除前缀 @@yes_no
     */
    private String isTrim;

    /**
     * 是否去除前缀 @@yes_no
     */
    private String isTrimText;

    public String getIsTrimText() {
        return DictCacheUtil.getDictTitle("yes_no", this.isTrim);
    }

    /**
     * 前缀内容
     */
    private String trimValue;

    /**
     * 需要排除的字段
     */
    private String excludeColumns;

    /**
     * 版本 @@generate.version
     */
    private String genVersion;

    /**
     * 版本 @@generate.version
     */
    private String genVersionText;

    public String getGenVersionText() {
        return DictCacheUtil.getDictTitle("generate.version", this.genVersion);
    }

    /**
     * 缓存实体
     */
    private String cacheVoPackage;

    /**
     * excel实体
     */
    private String excelVoPackage;


}