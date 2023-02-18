 package org.hlpay.common.util;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.LinkedBlockingQueue;
 import java.util.concurrent.SynchronousQueue;
 import java.util.concurrent.ThreadPoolExecutor;
 import java.util.concurrent.TimeUnit;

 public class ThreadExecutorUtil {
   private static ExecutorService cachedThreadPool;
   private static final Object $LOCK = new Object[0]; private static ExecutorService fixedThreadPool; public static ExecutorService newCachedThreadPool() { synchronized ($LOCK) {

       if (cachedThreadPool == null) {
         cachedThreadPool = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
       }


       return cachedThreadPool;
     }  } public static ExecutorService newFixedThreadPool(int nThreads) {
     synchronized ($LOCK) {

       if (fixedThreadPool == null) {
         fixedThreadPool = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
       }


       return fixedThreadPool;
     }
   }
 }

