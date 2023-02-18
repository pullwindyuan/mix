package com.futuremap.custom.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.*;

/**
 * @author 
 * @title UserExecutors
 * @description 用户线程池
 * @date 
 */
@Slf4j
public class UserExecutors {

    /**
     * 默认功能名
     */
    private static final String FEATURE_NAME = "";
    /**
     * 阻塞队列默认初始化容量
     */
    private static final int BLOCKING_QUEUE_CAPACITY = 64;
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = 20;
    /**
     * 线程活跃时间（分钟）
     */
    private static final long KEEP_ALIVE_TIME = 6L;

    /**
     * 获取线程池
     *
     * @param featureName           功能名
     * @param blockingQueueCapacity 阻塞队列初始化大小
     * @return ExecutorService
     */
    public static ExecutorService getThreadPool(String featureName, int blockingQueueCapacity) {
        // 线程工厂
        ThreadFactory namedThreadFactory = buildThreadFactory(featureName);
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(blockingQueueCapacity),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 获取线程池
     *
     * @param featureName 功能名
     * @return ExecutorService
     */
    public static ExecutorService getThreadPool(String featureName) {
        // 线程工厂
        ThreadFactory namedThreadFactory = buildThreadFactory(featureName);
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(BLOCKING_QUEUE_CAPACITY),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }


    /**
     * 创建线程工厂
     *
     * @param featureName 功能名
     * @return ThreadFactory
     */
    public static ThreadFactory buildThreadFactory(String featureName) {
        if (featureName == null){
            featureName = FEATURE_NAME;
        }
        return new UserThreadFactory(featureName);
    }

//  public static void main(String[] args) {
//      ExecutorService pool = getThreadPool("test");
//      CountDownLatch downLatch = new CountDownLatch(2);
//      pool.execute(() -> {
//          try {
//              Thread.sleep(2000);
//          } catch (Exception e) {
//
//          }
//          log.info("doCul4");
//          downLatch.countDown();
//      });
//      pool.execute(() -> {
//          try {
//              Thread.sleep(1000);
//          } catch (Exception e) {
//
//          }
//          log.info("doCul2");
//          downLatch.countDown();
//      });
//
//      try {
//          downLatch.await();
//      } catch (Exception e) {
//
//      }
//  }
}
