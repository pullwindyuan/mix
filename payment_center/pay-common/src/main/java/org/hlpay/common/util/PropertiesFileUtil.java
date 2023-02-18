 package org.hlpay.common.util;

 import java.util.ResourceBundle;








 public class PropertiesFileUtil
 {
   private ResourceBundle rb = null;

   public PropertiesFileUtil(String bundleFile) {
     this.rb = ResourceBundle.getBundle(bundleFile);
   }

   public String getValue(String key) {
     return this.rb.getString(key);
   }

   public static void main(String[] args) {}
 }

