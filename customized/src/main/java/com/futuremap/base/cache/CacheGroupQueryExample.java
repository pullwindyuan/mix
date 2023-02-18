 package com.futuremap.base.cache;

 import java.util.TreeMap;

 public class CacheGroupQueryExample extends CacheQueryExample {
   private TreeMap<String, String> groupQueryMap;

   public TreeMap<String, String> getGroupQueryMap() {
     return this.groupQueryMap;
   }
   public CacheGroupQueryExample addQuery(String name, String value) {
     this.groupQueryMap.put(name, value);
     return this;
   }
 }
