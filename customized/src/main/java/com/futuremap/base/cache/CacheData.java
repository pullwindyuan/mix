 package com.futuremap.base.cache;

 import org.springframework.data.redis.core.ZSetOperations;

 import java.util.Map;

 public class CacheData<V> {
   private String key;
   private V data;
   Map<String, ZSetOperations.TypedTuple<String>> scoreMap;
   Map<String, String> groupMap;
   Map<String, String> unionKeyMap;

   public CacheData<V> setKey(String key) { this.key = key; return this;    } 
   public CacheData<V> setData(V data) { this.data = data; return this;    } 
   public CacheData<V> setScoreMap(Map<String, ZSetOperations.TypedTuple<String>> scoreMap) { this.scoreMap = scoreMap; return this;    } 
   public CacheData<V> setGroupMap(Map<String, String> groupMap) { this.groupMap = groupMap; return this;    } 
   public CacheData<V> setUnionKeyMap(Map<String, String> unionKeyMap) { this.unionKeyMap = unionKeyMap; return this;    }


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
