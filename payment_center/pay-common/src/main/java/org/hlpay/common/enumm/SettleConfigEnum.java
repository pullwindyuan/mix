 package org.hlpay.common.enumm;

 import java.time.LocalDateTime;
 import org.hlpay.common.util.DateUtils;


 public enum SettleConfigEnum
 {
   DAY("1", "1", "日结"),

   WEEK("2", "2", "周结"),

   MONTH("3", "3", "月结"),

   YEAR("4", "4", "年结"),

   ANY("5", "5", "按需结算");

   private String type;
   private String code;
   private String desc;

   SettleConfigEnum(String type, String code, String message) {
     this.type = type;
     this.code = code;
     this.desc = message;
   }


   public String getType() {
     return this.type;
   }

   public String getDesc() {
     return this.desc;
   }

   public static LocalDateTime[] getValidSettleTime(String name, LocalDateTime paySuccessTime) {
     System.out.println("SettleConfigEnum-name" + name);
     SettleConfigEnum settleConfigEnum = null;
     settleConfigEnum = valueOf(name);
     LocalDateTime[] result = new LocalDateTime[2];
     if (DAY == settleConfigEnum) {
       result[0] = DateUtils.getDayFirstDate(paySuccessTime);
       result[1] = DateUtils.getLocalDayLastDate(paySuccessTime);
     } else if (WEEK == settleConfigEnum) {
       result[0] = DateUtils.getWeekFirstDate(paySuccessTime);
       result[1] = DateUtils.getWeekLastDate(paySuccessTime);
     } else if (MONTH == settleConfigEnum) {
       result[0] = DateUtils.getMonthFirstDate(paySuccessTime);
       result[1] = DateUtils.getMonthLastDate(paySuccessTime);
     } else if (YEAR == settleConfigEnum) {
       result[0] = DateUtils.getYearFirstDate(paySuccessTime);
       result[1] = DateUtils.getYearLastDate(paySuccessTime);
     } else if (ANY == settleConfigEnum) {
       result[0] = paySuccessTime;
       result[1] = result[0];
     } else {

       result = null;
     }
     return result;
   }

   public static LocalDateTime[] getCurrValidSettleTime(String name) {
     return getValidSettleTime(name, LocalDateTime.now());
   }
 }





