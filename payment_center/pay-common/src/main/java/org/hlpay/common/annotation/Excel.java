 package org.hlpay.common.annotation;

 import java.lang.annotation.ElementType;
 import java.lang.annotation.Retention;
 import java.lang.annotation.RetentionPolicy;
 import java.lang.annotation.Target;

 @Retention(RetentionPolicy.RUNTIME)
 @Target({ElementType.FIELD})
 public @interface Excel
 {
   String name() default "";

   String dateFormat() default "";

   String readConverterExp() default "";

   ColumnType cellType() default ColumnType.STRING;

   double height() default 14.0D;

   double width() default 16.0D;

   String suffix() default "";

   String defaultValue() default "";

   String prompt() default "";

   String[] combo() default {};

   boolean isExport() default true;

   String targetAttr() default "";

   Type type() default Type.ALL;

   public enum Type
   {
     ALL(0), EXPORT(1), IMPORT(2);

     private final int value;

     Type(int value) {
       this.value = value;
     }


     public int value() {
       return this.value;
     }
   }

   public enum ColumnType
   {
     NUMERIC(0), STRING(1);

     private final int value;

     ColumnType(int value) {
       this.value = value;
     }


     public int value() {
       return this.value;
     }
   }
 }

