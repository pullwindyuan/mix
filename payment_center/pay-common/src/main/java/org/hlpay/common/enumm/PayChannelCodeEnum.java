 package org.hlpay.common.enumm;


 public enum PayChannelCodeEnum
 {
   WX(Integer.valueOf(0), "WX", "微信支付"),

   ALIPAY(Integer.valueOf(1), "ALIPAY", "支付宝支付"),

   UNION(Integer.valueOf(2), "UNION", "银联支付"),

   INNER(Integer.valueOf(3), "INNER", "内部支付"),

   OTHERS(Integer.valueOf(99), "OTHERS", "其他");

   private Integer code;

   private String type;

   private String desc;

   PayChannelCodeEnum(Integer code, String type, String message) {
     this.code = code;
     this.type = type;
     this.desc = message;
   }

   public Integer getCode() {
     return this.code;
   }

   public String getType() {
     return this.type;
   }

   public String getDesc() {
     return this.desc;
   }

   public static PayChannelCodeEnum getEnumByCode(Integer code) {
     if (code == WX.code)
       return WX;
     if (code == ALIPAY.code)
       return ALIPAY;
     if (code == UNION.code)
       return UNION;
     if (code == INNER.code)
       return INNER;
     if (code == OTHERS.code) {
       return OTHERS;
     }
     return null;
   }

   public static String getTypeByCode(Integer code) {
     if (code == WX.code)
       return WX.type;
     if (code == ALIPAY.code)
       return ALIPAY.type;
     if (code == UNION.code)
       return UNION.type;
     if (code == INNER.code)
       return INNER.type;
     if (code == OTHERS.code) {
       return OTHERS.type;
     }
     return null;
   }
 }

