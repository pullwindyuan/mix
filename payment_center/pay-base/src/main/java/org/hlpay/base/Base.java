 package org.hlpay.base;

 import java.math.BigDecimal;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import org.apache.commons.lang.builder.ReflectionToStringBuilder;
 import org.apache.commons.lang.builder.ToStringStyle;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class Base
 {
   protected static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199)\\d{8}$";
   protected final Logger logger = LoggerFactory.getLogger(getClass());

   protected static final ExecutorService callbackExecutor = Executors.newFixedThreadPool(50);

   public static final Integer FREEZE = Integer.valueOf(3);

   public static final BigDecimal LECTURER_DEFAULT_PROPORTION = BigDecimal.valueOf(0.7D);

   public static String getString(Map<String, Object> map, String key) {
     if (null != map.get(key)) {
       return map.get(key).toString();
     }
     return "";
   }

   public static Map<String, Object> getMap() {
     return new HashMap<>();
   }

   public void log(Object obj) {
     this.logger.info(ReflectionToStringBuilder.toString(obj, ToStringStyle.MULTI_LINE_STYLE));
   }
 }
