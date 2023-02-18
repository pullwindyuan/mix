 package org.hlpay.common.util;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;




 public class MyLogFace
   implements MyLogInf
 {
   private Logger _log = null; public void setName(String clz) {
     this._log = LoggerFactory.getLogger(clz);
   }
   public boolean isDebugEnabled() { return this._log.isDebugEnabled(); }
   public boolean isInfoEnabled() { return this._log.isInfoEnabled(); }
   public boolean isWarnEnabled() { return this._log.isWarnEnabled(); }
   public boolean isErrorEnabled() { return this._log.isErrorEnabled(); } public boolean isTraceEnabled() {
     return this._log.isTraceEnabled();
   }
   public void trace(String message, Object... args) {
     if (isTraceEnabled()) this._log.trace(message, args);
   }

   public void debug(String message, Object... args) {
     StackTraceElement ste = (new Throwable()).getStackTrace()[1];
     String method = ste.getMethodName();
     String file = ste.getFileName();
     int line = ste.getLineNumber();
     if (isDebugEnabled()) this._log.debug(method + ":Line" + line + "\n                " + message, args);
   }

   public void info(String message, Object... args) {
     if (isInfoEnabled()) this._log.info(message, args);
   }

   public void warn(String message, Object... args) {
     if (isWarnEnabled()) this._log.warn(message, args);
   }

   public void error(String message, Object... args) {
     if (isErrorEnabled()) this._log.error(message, args);
   }

   public void error(Throwable e, String message, Object... args) {
     if (isErrorEnabled()) this._log.error(String.format(message, args), e);
   }

   public void error(Throwable e, String message) {
     if (isErrorEnabled()) this._log.error(message + e.toString(), e);
   }
 }





