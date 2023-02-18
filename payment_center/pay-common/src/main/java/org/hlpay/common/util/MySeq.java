 package org.hlpay.common.util;

 import java.util.concurrent.atomic.AtomicLong;









 public class MySeq
 {
   private static AtomicLong pay_seq = new AtomicLong(0L);
   private static String pay_seq_prefix = "P";
   private static AtomicLong trans_seq = new AtomicLong(0L);
   private static String trans_seq_prefix = "T";
   private static AtomicLong refund_seq = new AtomicLong(0L);
   private static String refund_seq_prefix = "R";

   private static String node = "00";











   public static String getPay() {
     return GlobalIdWorker.nextString();
   }


   public static String getTrans() {
     return trans_seq_prefix + GlobalIdWorker.nextString();
   }


   public static String getRefund() {
     return refund_seq_prefix + GlobalIdWorker.nextString();
   }


   private static String getSeq(String prefix, AtomicLong seq) {
     prefix = prefix + node;
     return String.format("%s%s%06d", new Object[] { prefix, DateUtil.getSeqString(), Integer.valueOf((int)seq.getAndIncrement() % 1000000) });
   }

   public static void main(String[] args) {
     for (int i = 0; i < 100; i++) {
       System.out.println("pay=" + getPay());
       System.out.println("trans=" + getTrans());
       System.out.println("refund=" + getRefund());
     }
   }
 }





