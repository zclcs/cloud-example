package com.zclcs.cloud.lib.core.constant;

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

    String LOCALHOST = "localhost";

    String LOCALHOST_IP = "127.0.0.1";

    String START_TIME = "startTime";

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
}
