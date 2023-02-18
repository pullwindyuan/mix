 package org.hlpay.common.enumm;

 public enum CardDataTypeEnum {
   DUMB("1", "1", "冲销"),

   TRUSTEESHIP("2", "2", "资金托管"),

   PAYMENT("3", "3", "收支"),

   SETTLE("4", "4", "结算"),

   REGULATION("5", "5", "资金监管"),

   OTHERS("9", "9", "其他");

   private String type;
   private String code;
   private String desc;

   CardDataTypeEnum(String type, String code, String message) {
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





