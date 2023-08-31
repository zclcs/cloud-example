package com.zclcs.cloud.lib.mybatis.flex.holder;

public class FieldPermissionHolder {

    private final ThreadLocal<String[]> threadLocal;

    private FieldPermissionHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    public static FieldPermissionHolder getInstance() {
        return SingletonHolder.S_INSTANCE;
    }

    /**
     * 静态内部类单例模式
     * 单例初使化
     */
    private static class SingletonHolder {
        private static final FieldPermissionHolder S_INSTANCE = new FieldPermissionHolder();
    }

    public void setPermissionFields(String[] fields) {
        threadLocal.set(fields);
    }

    public String[] getPermissionFields() {
        return threadLocal.get();
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }
}
