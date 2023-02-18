 package org.hlpay.common.enumm;

 public enum FreezeActionEnum {
   FREEZE("0", "冻结"),
   UNFREEZE("1", "解冻");

   private String type;
   private String desc;

   FreezeActionEnum(String type, String message) {
     this.type = type;
     this.desc = message;
   }


   public String getType() {
     return this.type;
   }

   public String getDesc() {
     return this.desc;
   }
 }





