package com.zclcs.cloud.lib.mybatis.flex.holder;

public class DataDesensitizationHolder {

    private final ThreadLocal<String[]> threadLocal;

    private DataDesensitizationHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    public static DataDesensitizationHolder getInstance() {
        return SingletonHolder.S_INSTANCE;
    }

    /**
     * 静态内部类单例模式
     * 单例初使化
     */
    private static class SingletonHolder {
        private static final DataDesensitizationHolder S_INSTANCE = new DataDesensitizationHolder();
    }

    public void setDesensitizationFields(String[] fields) {
        threadLocal.set(fields);
    }

    public String[] getDesensitizationFields() {
        return threadLocal.get();
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }
}
