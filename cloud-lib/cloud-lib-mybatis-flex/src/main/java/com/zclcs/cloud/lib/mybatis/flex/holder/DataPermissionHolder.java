package com.zclcs.cloud.lib.mybatis.flex.holder;

public class DataPermissionHolder {

    private final ThreadLocal<String> threadLocal;

    private DataPermissionHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    public static DataPermissionHolder getInstance() {
        return SingletonHolder.S_INSTANCE;
    }

    /**
     * 静态内部类单例模式
     * 单例初使化
     */
    private static class SingletonHolder {
        private static final DataPermissionHolder S_INSTANCE = new DataPermissionHolder();
    }

    public void setPermissionColumn(String column) {
        threadLocal.set(column);
    }

    public String getPermissionColumn() {
        return threadLocal.get();
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }
}
