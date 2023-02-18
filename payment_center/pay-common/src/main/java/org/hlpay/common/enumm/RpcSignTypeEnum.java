 package org.hlpay.common.enumm;

 public enum RpcSignTypeEnum
 {
   NOT_SIGN(Integer.valueOf(0)),
   SHA1_SIGN(Integer.valueOf(1));

   private Integer code;

   RpcSignTypeEnum(Integer code) {
     this.code = code;
   }


   public Integer getCode() {
     return this.code;
   }

   public static RpcSignTypeEnum getRpcSignTypeEnum(Integer code) {
     if (code == null) {
       return null;
     }

     RpcSignTypeEnum[] values = values();
     for (RpcSignTypeEnum e : values) {
       if (e.getCode().equals(code)) {
         return e;
       }
     }
     return null;
   }
 }





