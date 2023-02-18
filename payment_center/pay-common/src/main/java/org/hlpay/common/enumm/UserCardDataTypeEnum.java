 package org.hlpay.common.enumm;

 public enum UserCardDataTypeEnum {
   PAYMENT(CardDataTypeEnum.PAYMENT),

   SETTLE(CardDataTypeEnum.SETTLE);

   private String type;
   private String code;
   private String desc;

   UserCardDataTypeEnum(String type, String code, String message) {
     this.type = type;
     this.code = code;
     this.desc = message;
   }

   UserCardDataTypeEnum(CardDataTypeEnum cardDataTypeEnum) {
     this.type = cardDataTypeEnum.getType();
     this.code = cardDataTypeEnum.getCode();
     this.desc = cardDataTypeEnum.getDesc();
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





