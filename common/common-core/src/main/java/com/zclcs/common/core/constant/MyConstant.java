package com.zclcs.common.core.constant;

/**
 * 系统常量类
 *
 * @author zclcs
 */
public interface MyConstant {

    /**
     * 刷新令牌有效期默认 30 天
     */
    int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

    /**
     * 请求令牌有效期默认 12 小时
     */
    int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;

    /**
     * 请求开始时间
     */
    String REQUEST_START_TIME = "REQUEST-START-TIME";

    String START_TIME = "startTime";

    /**
     * nginx需要配置
     */
    String X_REAL_IP = "X-Real-IP";

    String UNKNOWN = "unknown";

    /**
     * 用户状态：有效
     */
    String STATUS_VALID = "1";

    /**
     * 用户状态：锁定
     */
    String STATUS_LOCK = "0";

    /**
     * 默认头像
     */
    String DEFAULT_AVATAR = "default.jpg";

    /**
     * 性别男
     */
    String SEX_MALE = "0";

    /**
     * 性别女
     */
    String SEX_FEMALE = "1";

    /**
     * 性别保密
     */
    String SEX_UNKNOWN = "2";

    /**
     * 菜单
     */
    String TYPE_MENU = "0";
    /**
     * 按钮
     */
    String TYPE_BUTTON = "1";
    /**
     * 目录
     */
    String TYPE_DIR = "2";
    /**
     * 目录 component 值
     */
    String LAYOUT = "Layout";

    /**
     * 顶级父节点id
     */
    Long TOP_PARENT_ID = 0L;

    /**
     * 顶级父节点编码
     */
    String TOP_PARENT_CODE = "0";

    /**
     * 是
     */
    String YES = "1";
    /**
     * 否
     */
    String NO = "0";

    /**
     * 排序规则：降序
     */
    String ORDER_DESC = "desc";

    /**
     * 排序规则：升序
     */
    String ORDER_ASC = "asc";

    /**
     * 页码
     */
    String PAGE_SIZE = "pageSize";

    /**
     * 页数
     */
    String PAGE_NUM = "pageNum";

    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    String VALID_FILE_TYPE = "xlsx, zip";

    /**
     * 异步线程池名称
     */
    String ASYNC_POOL = "MyAsyncThreadPool";
    /**
     * Java默认临时目录
     */
    String JAVA_TEMP_DIR = "java.io.tmpdir";
    /**
     * utf-8
     */
    String UTF8 = "utf-8";

    String LOCALHOST = "localhost";
    String LOCALHOST_IP = "127.0.0.1";

    /**
     * 调用成功消息
     */
    String SUCCESS_MSG = "调用成功";

    /**
     * http contentType
     */
    String JSON_UTF8 = "application/json;charset=UTF-8";

    /**
     * 根节点id
     */
    String TOP_PARENT_VALUE = "0";

    /**
     * 根节点id
     */
    Integer TOP_PARENT_LEVEL = 1;

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
     * 行政区划表名
     */
    String AREA_CODE = "area_code";

    /**
     * 字典表缓存前缀
     */
    String DICT_CACHE_PREFIX = "dict";

    /**
     * 新增
     */
    String INSERT = "INSERT";

    /**
     * 修改
     */
    String UPDATE = "UPDATE";

    /**
     * 删除
     */
    String DELETE = "DELETE";

    /**
     * 删除
     */
    String ADMIN = "admin";

}
