package com.zclcs.cloud.lib.core.constant;

import java.util.List;

/**
 * @author zclcs
 */
public interface CommonCore {

    String UNKNOWN = "unknown";
    /**
     * Java默认临时目录
     */
    String JAVA_TEMP_DIR = "java.io.tmpdir";

    /**
     * 顶级父节点id
     */
    Long TOP_PARENT_ID = 0L;

    /**
     * 顶级父节点编码
     */
    String TOP_PARENT_CODE = "0";

    /**
     * utf-8
     */
    String UTF8 = "utf-8";

    /**
     * 刷新令牌有效期默认 30 天
     */
    int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

    /**
     * 请求令牌有效期默认 12 小时
     */
    int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;

    /**
     * 排序规则：降序
     */
    String ORDER_DESC = "desc";

    /**
     * 排序规则：升序
     */
    String ORDER_ASC = "asc";

    String LOCALHOST = "localhost";

    String LOCALHOST_IP = "127.0.0.1";

    String START_TIME = "startTime";

    String SELECT = "SELECT";

    String COUNT = "COUNT";

    String LIMIT = "LIMIT";

    /**
     * 默认头像
     */
    String DEFAULT_AVATAR = "default.jpg";
    /**
     * 目录 component 值
     */
    String LAYOUT = "Layout";

    /**
     * 异步线程池名称
     */
    String ASYNC_POOL = "MyAsyncThreadPool";

    /**
     * 异步线程池名称
     */
    String NACOS_CONFIG = "MyNacosConfigThreadPool";

    /**
     * 注释中的字典信息
     */
    String DICT_REMARK = "@@";

    /**
     * 字典是否是array
     */
    String DICT_ARRAY = "array";

    /**
     * 字典是否是array
     */
    String DICT_TREE = "tree";

    /**
     * 调用成功消息
     */
    String SUCCESS_MSG = "调用成功";

    String HTTP = "http://";

    String BOOLEAN_TRUE = "true";

    /**
     * 允许上传的文件类型
     */
    List<String> ALLOW_FILE_TYPE = List.of("jpg", "png", "pdf", "xls");
}
