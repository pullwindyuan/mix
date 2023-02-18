 package com.futuremap.base.cache;

 public enum SortTypeEnum {
   ASC("ASC", "1", "升序"),
   DESC("DESC", "0", "降序");

   private String type;

   private String code;

   private String desc;

   SortTypeEnum(String type, String code, String message) {
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





