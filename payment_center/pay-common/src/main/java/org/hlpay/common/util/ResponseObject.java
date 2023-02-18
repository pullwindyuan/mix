 package org.hlpay.common.util;

 import java.io.Serializable;

 public class ResponseObject implements Serializable {
   private Integer erroCode;

   public Integer getErroCode() {
     return this.erroCode;
   } private Object data;
   public void setErroCode(Integer erroCode) {
     this.erroCode = erroCode;
   }
   public Object getData() {
     return this.data;
   }
   public void setData(Object data) {
     this.data = data;
   }
 }

