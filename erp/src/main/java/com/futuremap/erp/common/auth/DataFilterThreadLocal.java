package com.futuremap.erp.common.auth;

public class DataFilterThreadLocal {
    private static final ThreadLocal<DataFilterMetaData> ThreadLocalDataFilter = new ThreadLocal<>();

    public static void clear() {
        ThreadLocalDataFilter.remove();
    }
    public static void set(DataFilterMetaData  metaData) {
        ThreadLocalDataFilter.set(metaData);
    }
    public static DataFilterMetaData  get() {
        return ThreadLocalDataFilter.get();
    }

}
