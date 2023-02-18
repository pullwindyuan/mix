 package org.hlpay.common.enumm;

 public enum RunModeEnum {
   PUBLIC("public", Integer.valueOf(0), "公域模式"),

   PRIVATE("private", Integer.valueOf(1), "私域模式-代收款"),

   PRIVATE_INDEPENDENT("private_independent", Integer.valueOf(2), "私域模式-独立收款");

   private String type;
   private Integer code;
   private String desc;

   RunModeEnum(String type, Integer code, String message) {
     this.type = type;
     this.code = code;
     this.desc = message;
   }


   public String getType() {
     return this.type;
   }


   public Integer getCode() {
     return this.code;
   }

   public String getDesc() {
     return this.desc;
   }
 }





