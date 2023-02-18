 package org.hlpay.base.cache;

 import java.util.Map;
 import org.springframework.data.redis.core.ZSetOperations;

 public class CacheData<V> {
   private String key;
   private V data;
   Map<String, ZSetOperations.TypedTuple<String>> scoreMap;
   Map<String, String> groupMap;
   Map<String, String> unionKeyMap;

   public CacheData<V> setKey(String key) { this.key = key; return this; } public CacheData<V> setData(V data) { this.data = data; return this; } public CacheData<V> setScoreMap(Map<String, ZSetOperations.TypedTuple<String>> scoreMap) { this.scoreMap = scoreMap; return this; } public CacheData<V> setGroupMap(Map<String, String> groupMap) { this.groupMap = groupMap; return this; } public CacheData<V> setUnionKeyMap(Map<String, String> unionKeyMap) { this.unionKeyMap = unionKeyMap; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CacheData)) return false;  CacheData<?> other = (CacheData)o; if (!other.canEqual(this)) return false;  Object this$key = getKey(), other$key = other.getKey(); if ((this$key == null) ? (other$key != null) : !this$key.equals(other$key)) return false;  Object this$data = getData(), other$data = other.getData(); if ((this$data == null) ? (other$data != null) : !this$data.equals(other$data)) return false;  Object<String, ZSetOperations.TypedTuple<String>> this$scoreMap = (Object<String, ZSetOperations.TypedTuple<String>>)getScoreMap(), other$scoreMap = (Object<String, ZSetOperations.TypedTuple<String>>)other.getScoreMap(); if ((this$scoreMap == null) ? (other$scoreMap != null) : !this$scoreMap.equals(other$scoreMap)) return false;  Object<String, String> this$groupMap = (Object<String, String>)getGroupMap(), other$groupMap = (Object<String, String>)other.getGroupMap(); if ((this$groupMap == null) ? (other$groupMap != null) : !this$groupMap.equals(other$groupMap)) return false;  Object<String, String> this$unionKeyMap = (Object<String, String>)getUnionKeyMap(), other$unionKeyMap = (Object<String, String>)other.getUnionKeyMap(); return !((this$unionKeyMap == null) ? (other$unionKeyMap != null) : !this$unionKeyMap.equals(other$unionKeyMap)); } protected boolean canEqual(Object other) { return other instanceof CacheData; } public int hashCode() { int PRIME = 59; result = 1; Object $key = getKey(); result = result * 59 + (($key == null) ? 43 : $key.hashCode()); Object $data = getData(); result = result * 59 + (($data == null) ? 43 : $data.hashCode()); Object<String, ZSetOperations.TypedTuple<String>> $scoreMap = (Object<String, ZSetOperations.TypedTuple<String>>)getScoreMap(); result = result * 59 + (($scoreMap == null) ? 43 : $scoreMap.hashCode()); Object<String, String> $groupMap = (Object<String, String>)getGroupMap(); result = result * 59 + (($groupMap == null) ? 43 : $groupMap.hashCode()); Object<String, String> $unionKeyMap = (Object<String, String>)getUnionKeyMap(); return result * 59 + (($unionKeyMap == null) ? 43 : $unionKeyMap.hashCode()); } public String toString() { return "CacheData(key=" + getKey() + ", data=" + getData() + ", scoreMap=" + getScoreMap() + ", groupMap=" + getGroupMap() + ", unionKeyMap=" + getUnionKeyMap() + ")"; }




   public String getKey() {
     return this.key;
   }


   public V getData() {
     return this.data;
   }


   public Map<String, ZSetOperations.TypedTuple<String>> getScoreMap() {
     return this.scoreMap;
   }


   public Map<String, String> getGroupMap() {
     return this.groupMap;
   }


   public Map<String, String> getUnionKeyMap() {
     return this.unionKeyMap;
   }
 }
