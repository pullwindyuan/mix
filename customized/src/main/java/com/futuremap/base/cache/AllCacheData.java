 package com.futuremap.base.cache;

 import java.util.Map;

 public class AllCacheData<V> {
   Map<String, V> dataMap;
   Map<String, CacheData<V>> cacheDataMap;

   public AllCacheData<V> setDataMap(Map<String, V> dataMap) {
     this.dataMap = dataMap; 
     return this; 
   } 
   public AllCacheData<V> setCacheDataMap(Map<String, CacheData<V>> cacheDataMap) { this.cacheDataMap = cacheDataMap; return this;    }

   public Map<String, V> getDataMap() {
     return this.dataMap;
   }


   public Map<String, CacheData<V>> getCacheDataMap() {
     return this.cacheDataMap;
   }
 }
