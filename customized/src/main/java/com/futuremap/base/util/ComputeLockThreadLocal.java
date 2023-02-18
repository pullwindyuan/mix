package com.futuremap.base.util;

import org.springframework.core.NamedThreadLocal;

import java.util.List;

public class ComputeLockThreadLocal {
    private static final ThreadLocal<List<String>> threadLocal = new NamedThreadLocal<>("lockList");

    public static void set(List<String> computeLockList) {
        threadLocal.set(computeLockList);
    }

    public static List<String> get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}