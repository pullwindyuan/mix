 package org.hlpay.common.enumm;

 public enum CurrencyTypeEnum {
   CNY("00", "00", "元"),

   COIN("01", "01", "金币"),

   POINTS("02", "02", "积分"),

   USD("03", "03", "美元"),

   OTHERS("99", "99", "其他");

   private String type;
   private String code;
   private String desc;

   CurrencyTypeEnum(String type, String code, String desc) {
     this.type = type;
     this.code = code;
     this.desc = desc;
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





