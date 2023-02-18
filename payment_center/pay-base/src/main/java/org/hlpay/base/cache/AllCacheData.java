 package org.hlpay.base.cache;

 import java.util.Map;

 public class AllCacheData<V> {
   Map<String, V> dataMap;
   Map<String, CacheData<V>> cacheDataMap;

   public AllCacheData<V> setDataMap(Map<String, V> dataMap) {
     this.dataMap = dataMap; return this; } public AllCacheData<V> setCacheDataMap(Map<String, CacheData<V>> cacheDataMap) { this.cacheDataMap = cacheDataMap; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof AllCacheData)) return false;  AllCacheData<?> other = (AllCacheData)o; if (!other.canEqual(this)) return false;  Object<String, V> this$dataMap = (Object<String, V>)getDataMap(); Object<String, ?> other$dataMap = (Object<String, ?>)other.getDataMap(); if ((this$dataMap == null) ? (other$dataMap != null) : !this$dataMap.equals(other$dataMap)) return false;  Object<String, CacheData<V>> this$cacheDataMap = (Object<String, CacheData<V>>)getCacheDataMap(); Object<String, CacheData<?>> other$cacheDataMap = (Object<String, CacheData<?>>)other.getCacheDataMap(); return !((this$cacheDataMap == null) ? (other$cacheDataMap != null) : !this$cacheDataMap.equals(other$cacheDataMap)); } protected boolean canEqual(Object other) { return other instanceof AllCacheData; } public int hashCode() { int PRIME = 59; result = 1; Object<String, V> $dataMap = (Object<String, V>)getDataMap(); result = result * 59 + (($dataMap == null) ? 43 : $dataMap.hashCode()); Object<String, CacheData<V>> $cacheDataMap = (Object<String, CacheData<V>>)getCacheDataMap(); return result * 59 + (($cacheDataMap == null) ? 43 : $cacheDataMap.hashCode()); } public String toString() { return "AllCacheData(dataMap=" + getDataMap() + ", cacheDataMap=" + getCacheDataMap() + ")"; }




   public Map<String, V> getDataMap() {
     return this.dataMap;
   }


   public Map<String, CacheData<V>> getCacheDataMap() {
     return this.cacheDataMap;
   }
 }
