 package org.hlpay.common.enumm;

 public enum ExternalMchTypeEnum {
   root_org(MchTypeEnum.PLATFORM_AGENCY, "0", "组织"),

   org(MchTypeEnum.MCH, "0", "组织"),

   brand(MchTypeEnum.MCH_VIRTUAL, "1", "品牌"),

   store(MchTypeEnum.MCH_BRANCH, "2", "门店");

   private String code;
   private String desc;
   private MchTypeEnum mchTypeEnum;

   ExternalMchTypeEnum(MchTypeEnum mchTypeEnum, String code, String message) {
     this.mchTypeEnum = mchTypeEnum;
     this.code = code;
     this.desc = message;
   }


   public MchTypeEnum getMchTypeEnum() {
     return this.mchTypeEnum;
   }

   public String getCode() {
     return this.code;
   }

   public String getDesc() {
     return this.desc;
   }

   public static ExternalMchTypeEnum getExternalMchTypeEnum(String type) {
     return valueOf(type);
   }
 }





