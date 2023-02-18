 package org.hlpay.common.enumm;

 public enum ResultEnum
 {
   SUCCESS(Integer.valueOf(200), "成功"),

   SETTLE_SUB_MCH_HAVE_SETTLED(Integer.valueOf(40000), "下级商户存在待确认账单"),

   SETTLE_ING(Integer.valueOf(40001), "当前处于记账周期中，不可以确认"),

   SETTLE_REPEAT(Integer.valueOf(40002), "重复记账"),


   ERROR(Integer.valueOf(999), "错误");
   private Integer code;
   private String desc;

   ResultEnum(Integer code, String message) {
     this.code = code;
     this.desc = message;
   }


   public Integer getCode() {
     return this.code;
   }

   public String getDesc() {
     return this.desc;
   }
 }

