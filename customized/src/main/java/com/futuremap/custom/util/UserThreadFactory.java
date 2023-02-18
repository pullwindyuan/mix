package com.futuremap.custom.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ken
 * @title UserThreadFactory
 * @description 用户线程工厂类
 * @date 2020/11/5 14:37
 */
public class UserThreadFactory implements ThreadFactory {

    /**
     * 线程名前缀
     */
    private final String namePrefix;

    /**
     * 下一id
     */
    private final AtomicInteger nextId = new AtomicInteger(1);

    /**
     * 构造方法
     * 定义线程组名称，在jstack 问题排查时，非常有帮助
     *
     * @param featureName 功能名称
     */
    UserThreadFactory(String featureName) {
        namePrefix = "UserThreadFactory-" + featureName + "-Worker-";
    }

    @Override
    public Thread newThread(Runnable task) {
        String name = namePrefix + nextId.getAndIncrement();
        Thread thread = new Thread(task, name);
        return thread;
    }
}
