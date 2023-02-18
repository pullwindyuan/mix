 package org.hlpay.common.util;

 import java.lang.management.ManagementFactory;
 import java.math.BigInteger;
 import java.net.NetworkInterface;
 import java.nio.BufferUnderflowException;
 import java.nio.ByteBuffer;
 import java.security.SecureRandom;
 import java.util.Enumeration;
 import java.util.concurrent.atomic.AtomicInteger;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;








 public class GlobalIdWorker
 {
   private static final Logger LOGGER = LoggerFactory.getLogger(GlobalIdWorker.class);

   private static final int TIME_TRUNCATE = 6;

   private static final String MAC_PROC_BIT;

   private static final AtomicInteger COUNTER = new AtomicInteger((new SecureRandom()).nextInt());




   private static final int COUNTER_MASK = 8388607;




   private static final int COUNTER_FLAG = 8388608;





   static {
     int machineId = createMachineIdentifier() & 0xFFFFFF | 0x1000000;
     int processId = createProcessIdentifier() & 0xFFFF | 0x10000;
     String machineIdBit = Integer.toBinaryString(machineId).substring(1);
     String processIdBit = Integer.toBinaryString(processId).substring(1);
     MAC_PROC_BIT = machineIdBit + processIdBit;
   }




   public static BigInteger nextBigInteger() {
     long timestamp = System.currentTimeMillis() >>> 6L;

     int count = COUNTER.getAndIncrement() & 0x7FFFFF;



     String idBit = Long.toBinaryString(timestamp) + MAC_PROC_BIT + Integer.toBinaryString(count | 0x800000);

     return new BigInteger(idBit, 2);
   }




   public static String nextString() {
     return nextBigInteger().toString(10);
   }





   private static int createMachineIdentifier() {
     int machineHash;
     try {
       StringBuilder sb = new StringBuilder();
       Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
       while (e.hasMoreElements()) {
         NetworkInterface ni = e.nextElement();
         sb.append(ni.toString());
         byte[] mac = ni.getHardwareAddress();
         if (mac != null) {
           ByteBuffer bb = ByteBuffer.wrap(mac);
           try {
             sb.append(bb.getChar());
             sb.append(bb.getChar());
             sb.append(bb.getChar());
           } catch (BufferUnderflowException bufferUnderflowException) {}
         }
       }


       machineHash = sb.toString().hashCode();
     } catch (Throwable throwable) {
       machineHash = (new SecureRandom()).nextInt();
       LOGGER.warn("Use random number instead mac address!", throwable);
     }
     return machineHash;
   }




   private static short createProcessIdentifier() {
     short processId;
     try {
       String processName = ManagementFactory.getRuntimeMXBean().getName();
       if (processName.contains("@")) {
         processId = (short)Integer.parseInt(processName.substring(0, processName.indexOf('@')));
       } else {
         processId = (short)ManagementFactory.getRuntimeMXBean().getName().hashCode();
       }
     } catch (Throwable throwable) {
       processId = (short)(new SecureRandom()).nextInt();
       LOGGER.warn("Use random number instead process id!", throwable);
     }
     return processId;
   }
 }





