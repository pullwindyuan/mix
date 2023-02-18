 package com.futuremap.base.cache;

 import java.util.TreeMap;

 public class CacheUnionQueryExample extends CacheQueryExample {
   private TreeMap<String, String> unionQueryMap;

 public CacheUnionQueryExample setUnionQueryMap(TreeMap<String, String> unionQueryMap) { this.unionQueryMap = unionQueryMap; return this; }





   public TreeMap<String, String> getUnionQueryMap() {
     return this.unionQueryMap;
   }
   public CacheUnionQueryExample addQuery(String name, String value) {
     this.unionQueryMap.put(name, value);
     return this;
   }
 }

