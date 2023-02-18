 package org.hlpay.common.enumm;

 public enum DealTypeEnum {
   INCOME("1", "1", "收入"),

   EXP("0", "0", "支出"),

   REFUND("2", "2", "退款"),

   OTHERS("99", "99", "其他");

   private String type;

   private String code;

   private String desc;

   DealTypeEnum(String type, String code, String message) {
     this.type = type;
     this.code = code;
     this.desc = message;
   }


   public String getType() {
     return this.type;
   }

   public String getCode() {
     return this.code;
   }

   public String getDesc() {
     return this.desc;
   }
 }

