 package org.hlpay.common.util;

 import java.text.DecimalFormat;
 import java.text.FieldPosition;
 import java.text.NumberFormat;
 import java.util.concurrent.atomic.AtomicInteger;

 public class BizSequenceUtils
 {
   private static Object lock = new Object();



   private static BizSequenceUtils instance;


   private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

   private static final NumberFormat numberFormat = new DecimalFormat("00000000");

   private static AtomicInteger seq = new AtomicInteger(1);

   private static final int MAX = 99999999;

   public static BizSequenceUtils getInstance() {
     if (instance == null) {
       synchronized (lock) {
         if (instance == null) {
           instance = new BizSequenceUtils();
         }
       }
     }
     return instance;
   }

   public String generateBizSeqNo(String bizSeqNoPrefix) {
     StringBuffer bizSeqNo = new StringBuffer();
     bizSeqNo.append(bizSeqNoPrefix)
       .append(DateUtils.getCurrentTimeStr("yyMMddHHmmss"))
       .append(getSeq());
     return bizSeqNo.toString();
   }

   private String getSeq() {
     StringBuffer sb = new StringBuffer();
     numberFormat.format(seq, sb, HELPER_POSITION);
     if (!seq.compareAndSet(99999999, 0)) {
       seq.incrementAndGet();
     }
     return sb.toString();
   }
 }

