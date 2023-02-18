/*    */ package org.hlpay.common.util;
/*    */ 
/*    */ public class Debug
/*    */ {
/*  5 */   private static StackTraceElement ste = (new Throwable()).getStackTrace()[1];
/*  6 */   private static final MyLog _log = MyLog.getLog(Debug.class);
/*    */   public static void log(String tag, String msg) {
/*  8 */     String method = ste.getMethodName();
/*  9 */     String file = ste.getFileName();
/* 10 */     int Line = ste.getLineNumber();
/*    */     
/* 12 */     _log.debug(tag + ":" + method + ": Line " + ste
/* 13 */         .getLineNumber(), new Object[] { msg });
/*    */   }
/*    */ }
