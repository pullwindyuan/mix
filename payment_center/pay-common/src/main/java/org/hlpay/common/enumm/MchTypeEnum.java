 package org.hlpay.common.enumm;



 public enum MchTypeEnum
 {
   PLATFORM("100", "1", "平台"),

   PLATFORM_AGENCY("200", "2", "平台代理"),

   MCH("300", "90", "普通商户，不具备结算资质"),

   MCH_VIRTUAL("400", "3", "虚拟商户"),

   MCH_BRANCH("500", "4", "商户分支机构"),
   USER("800", "8", "用户"),
   OTHER("900", "9", "其他");
   private String type;
   private String code;
   private String desc;

   MchTypeEnum(String type, String code, String message) {
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

