package org.hlpay.common.util;

public interface MyLogInf {
  void debug(String paramString, Object[] paramArrayOfObject);
  
  void info(String paramString, Object[] paramArrayOfObject);
  
  void warn(String paramString, Object[] paramArrayOfObject);
  
  void error(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject);
}
