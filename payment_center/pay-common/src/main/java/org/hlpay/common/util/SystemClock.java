 package org.hlpay.common.util;

 import java.sql.Timestamp;
 import java.util.concurrent.Executors;
 import java.util.concurrent.ScheduledExecutorService;
 import java.util.concurrent.ThreadFactory;
 import java.util.concurrent.TimeUnit;
 import java.util.concurrent.atomic.AtomicLong;















 public class SystemClock
 {
   private final long period;
   private final AtomicLong now;

   private SystemClock(long period) {
     this.period = period;
     this.now = new AtomicLong(System.currentTimeMillis());
     scheduleClockUpdating();
   }

   private static class InstanceHolder {
     public static final SystemClock INSTANCE = new SystemClock(1L);
   }

   private static SystemClock instance() {
     return InstanceHolder.INSTANCE;
   }

   private void scheduleClockUpdating() {
     ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory()
         {
           public Thread newThread(Runnable runnable) {
             Thread thread = new Thread(runnable, "System Clock");
             thread.setDaemon(true);
             return thread;
           }
         });
     scheduler.scheduleAtFixedRate(new Runnable()
         {
           public void run() {
             SystemClock.this.now.set(System.currentTimeMillis());
           }
         },  this.period, this.period, TimeUnit.MILLISECONDS);
   }

   private long currentTimeMillis() {
     return this.now.get();
   }

   public static long now() {
     return instance().currentTimeMillis();
   }

   public static String nowDate() {
     return (new Timestamp(instance().currentTimeMillis())).toString();
   }
 }

