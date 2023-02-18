package com.futuremap.base.cache;

import com.futuremap.base.plugin.PageModel;
import com.futuremap.base.util.BeanConvertUtils;
import com.futuremap.base.util.RedisProUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class CacheService<V>
{
  private static final String HASHMAP = ":HM:";
  private static final String ZSET = ":ZS:";
  private static final String SET = ":S:";
  private static final String LIST = ":L:";
  private static final String PRIMARY = ":PRIMARY:";
  private static final String SORT = ":SORT:";
  private static final String GROUP = ":GROUP:";
  private static final String UNION = ":UNION:";
  @Autowired
  private RedisProUtil<V> redisProUtil;
  @Autowired
  private RedisProUtil<Object> redisUtil;
  private String nameSpace;
  private DefaultRedisScript<V> defaultRedisScript = new DefaultRedisScript();

  private Class<V> type;

  private static final String LUA_GET = "local key = KEYS[1]\nlocal val = KEYS[2]\nlocal expire = ARGV[1]\nif redis.call(\"get\", key) == false then\n    if redis.call(\"set\", key, val) then\n        if tonumber(expire) > 0 then\n            redis.call(\"expire\", key, expire)\n        end\n        return true\n    end\n    return false\nelse\n    return false\nend\n";


  @PostConstruct
  public void init() {
    this.type = getType();
    this.nameSpace = getNameSpace();
    this.redisProUtil.init();
    if (StringUtils.isBlank(this.nameSpace)) {
      this.nameSpace = this.type.getTypeName();
    }
  }

  private Map<String, V> getPrimaryKeyDataMap(List<V> list) {
    if (list != null && list.size() > 0) {
      Map<String, V> dataMap = new HashMap<>();

      for (V t : list) {
        String primaryKey = getPrimaryKey(t);
        dataMap.put(primaryKey, t);
      }
      return dataMap;
    }
    return null;
  }

  private String getPrimaryKey(V data) {
    List<String> keys = getPrimaryKeyList(data);
    StringBuffer stringBuffer = new StringBuffer();
    for (String key : keys) {
      stringBuffer.append(":");
      stringBuffer.append(key);
      stringBuffer.append(":");
    }
    return stringBuffer.toString();
  }

  private Map<String, String> getUnionKeyMap(V data) {
    List<TreeMap<String, String>> unionKeyTreeList = getUnionKeyList(data);
    final StringBuffer key = new StringBuffer();
    final StringBuffer value = new StringBuffer();
    Map<String, String> map = new HashMap<>();
    for (TreeMap<String, String> t : unionKeyTreeList) {
      t.forEach((s, s2)->
          {
              key.append(":");
              key.append(s);
              key.append(":");

              value.append(":");
              value.append(s2);
              value.append(":");
          });
      map.put(key.toString(), value.toString());
    }

    return map;
  }

  private Map<String, ZSetOperations.TypedTuple<String>> getScoreMap(final String key, V data) {
    Map<String, Double> scoreMap = getScoreMap(data);
    final Map<String, ZSetOperations.TypedTuple<String>> map = new HashMap<>();
    scoreMap.forEach((s, aDouble)->
        {
            map.put(s, new DefaultTypedTuple(key, aDouble));
        });
    return map;
  }

  private Map<String, V> getDataMap(List<V> list) {
    if (list != null && list.size() > 0) {
      Map<String, V> dataMap = new HashMap<>();

      for (V t : list) {
        String primaryKey = getPrimaryKey(t);
        dataMap.put(primaryKey, t);
      }
      return dataMap;
    }
    return null;
  }

  private AllCacheData getCacheDataMap(List<V> list) {
    if (list != null && list.size() > 0) {
      AllCacheData<V> allCacheData = new AllCacheData();
      Map<String, CacheData<V>> cacheMap = new HashMap<>();

      Map<String, V> dataMap = new HashMap<>();

      for (V t : list) {
        String primaryKey = getPrimaryKey(t);
        Map<String, ZSetOperations.TypedTuple<String>> scoreMap = getScoreMap(primaryKey, t);
        Map<String, String> unionKeyMap = getUnionKeyMap(t);
        Map<String, String> groupMap = getGroupMap(t);
        dataMap.put(primaryKey, t);

        CacheData<V> cacheData = (CacheData<V>) (new CacheData<>()).setData(t).setKey(primaryKey).setScoreMap(scoreMap).setGroupMap(groupMap).setUnionKeyMap(unionKeyMap);
        cacheMap.put(primaryKey, cacheData);
      }
      allCacheData.setDataMap(dataMap);
      allCacheData.setCacheDataMap(cacheMap);
      return allCacheData;
    }
    return null;
  }

  private CacheData<V> getCacheData(V data) {
    if (data != null) {
      String primaryKey = getPrimaryKey(data);
      Map<String, ZSetOperations.TypedTuple<String>> scoreMap = getScoreMap(primaryKey, data);
      Map<String, String> unionKeyMap = getUnionKeyMap(data);
      Map<String, String> groupMap = getGroupMap(data);
      CacheData<V> cacheData = (CacheData<V>) (new CacheData<>()).setData(data).setKey(primaryKey).setScoreMap(scoreMap).setGroupMap(groupMap).setUnionKeyMap(unionKeyMap);
      return cacheData;
    }
    return null;
  }

  public PageModel<V> getListByGroup(CacheGroupQueryExample cacheGroupQueryExample) {
    return null;
  }

  public Map<String, V> getDataMap() {
    return this.redisProUtil.hGetAll(this.nameSpace + ":HM:");
  }

  public V getData(String key) {
    return (V)this.redisProUtil.hGet(this.nameSpace + ":HM:", key);
  }

  public void putAllToIndependentMap(List<V> dataList) {
    for (V v : dataList) {
      this.redisUtil.hPutAll(getPrimaryKey(v), (Map<String, Object>) BeanConvertUtils.bean2Map(v));
    }
  }

  public void putAllToListLeft(List<V> dataList) {
    this.redisProUtil.lLeftPushAll(this.nameSpace, dataList);
  }

  public void putAllToListRight(List<V> dataList) {
    this.redisProUtil.lRightPushAll(this.nameSpace, dataList);
  }

  public void putToListLeft(V data) {
    this.redisProUtil.lLeftPush(this.nameSpace, data);
  }

  public void putToListRight(V data) {
    this.redisProUtil.lRightPush(this.nameSpace, data);
  }

  public void putToIndependentMap(V data) {
    String key = getCacheSimplePrimaryKey(data);
    this.redisUtil.hPutAll(key,
        BeanConvertUtils.bean2MapWithOutNull(data));
    this.redisUtil.set(getCacheSimpleUnionKey(data), key);
  }

  public void updateSimpleCache(V data) {
    String key = getCacheSimplePrimaryKey(data);
    this.redisUtil.hPutAll(key,
        BeanConvertUtils.bean2MapWithOutNull(data));
  }

  public void putToIndependentMapExpireAt(V data, Date expireAt) {
    String key = getCacheSimplePrimaryKey(data);
    this.redisUtil.hPutAll(key, BeanConvertUtils.bean2MapWithOutNull(data));
    this.redisUtil.expireAt(key, expireAt);
    String unionKey = getCacheSimpleUnionKey(data);
    this.redisUtil.set(unionKey, key);
    this.redisUtil.expireAt(unionKey, expireAt);
  }

  public void putToIndependentMapExpire(V data, long expire, TimeUnit timeUnit) {
    String key = getCacheSimplePrimaryKey(data);
    this.redisUtil.hPutAll(key, BeanConvertUtils.bean2MapWithOutNull(data));
    this.redisUtil.expire(key, expire, timeUnit);
    String unionKey = getCacheSimpleUnionKey(data);
    this.redisUtil.set(unionKey, key);
    this.redisUtil.expire(unionKey, expire, timeUnit);
  }

  public void putToIndependentMapExpireAt(V data, Long timeOut, TimeUnit timeUnit) {
    String key = getCacheSimplePrimaryKey(data);
    this.redisUtil.hPutAll(key, BeanConvertUtils.bean2MapWithOutNull(data));
    this.redisUtil.expire(key, timeOut.longValue(), timeUnit);
    String unionKey = getCacheSimpleUnionKey(data);
    this.redisUtil.set(unionKey, key);
    this.redisUtil.expire(unionKey, timeOut.longValue(), timeUnit);
  }

  public void putToSimpleCache(V data) {
    putToIndependentMap(data);
  }

  public void deleteSimpleCache(V data) {
    this.redisUtil.hDelete(getCacheSimplePrimaryKey(data), new Object[0]);
    this.redisUtil.delete(getCacheSimpleUnionKey(data));
  }

  public void deleteSimpleCache(String primaryKey) {
    V data = getSimpleDataByPrimaryKey(primaryKey);
    if (data != null) {
      this.redisUtil.hDelete(primaryKey, new Object[0]);
      this.redisUtil.delete(getCacheSimpleUnionKey(data));
    }
  }

  public void putToSimpleCacheExpireAt(V data, Date expireAt) {
    putToIndependentMapExpireAt(data, expireAt);
  }

  public void putToSimpleCacheExpire(V data, long expire, TimeUnit timeUnit) {
    putToIndependentMapExpire(data, expire, timeUnit);
  }

  public void putToSimpleCacheExpireAt(V data, Long timeOut, TimeUnit timeUnit) {
    putToIndependentMapExpireAt(data, timeOut, timeUnit);
  }

  public V getSimpleDataByPrimaryKey(String primaryKey) {
    Map<String, Object> map = this.redisUtil.hGetAll(primaryKey);
    if (map.size() > 0) {
      return (V)BeanConvertUtils.map2Bean(map, this.type);
    }
    return null;
  }

  public V getSimpleDataByPrimaryKey(V data) {
    Map<String, Object> map = this.redisUtil.hGetAll(getCacheSimplePrimaryKey(data));
    if (map.size() > 0) {
      return (V)BeanConvertUtils.map2Bean(map, this.type);
    }
    return null;
  }

  public V getSimpleDataByUnionKey(String unionKey) {
    String key = (String)this.redisProUtil.get(":UNION:" + unionKey);
    if (key == null) {
      return null;
    }
    Map<String, Object> map = this.redisUtil.hGetAll(key);
    if (map.size() > 0) {
      return (V)BeanConvertUtils.map2Bean(map, this.type);
    }
    return null;
  }

  public V getSimpleDataByUnionKey(V data) {
    String key = (String)this.redisProUtil.get(getCacheSimpleUnionKey(data));
    if (key == null) {
      return null;
    }
    Map<String, Object> map = this.redisUtil.hGetAll(key);
    if (map.size() > 0) {
      return (V)BeanConvertUtils.map2Bean(map, this.type);
    }
    return null;
  }

  public void put(V data) {
    if (data == null) {
      return;
    }
    CacheData<V> cacheData = getCacheData(data);
    putToCache(cacheData);
  }

  public void putAll(List<V> dataList) {
    if (dataList == null && dataList.size() == 0) {
      return;
    }
    AllCacheData<V> allCacheData = getCacheDataMap(dataList);
    putAllToCache(allCacheData);
  }

  private void putToCache(final CacheData<V> cacheData) {
    this.redisProUtil.hPut(this.nameSpace + ":HM:", cacheData.getKey(), cacheData.getData());


    if (cacheData.getScoreMap() == null || cacheData.getScoreMap().size() == 0) {
      return;
    }
    cacheData.getScoreMap().forEach((scoreKey, stringTypedTuple)->
        {
            cacheData.getGroupMap().forEach((groupKey, s2)->
                {
                    CacheService.this.redisProUtil.zAdd(CacheService.this.getGroupName(groupKey, s2, scoreKey), (String)stringTypedTuple
                        .getValue(), stringTypedTuple
                        .getScore().doubleValue());
                });
            cacheData.getUnionKeyMap().forEach((unionKey, s2)->
                {

                    CacheService.this.redisProUtil.zAdd(CacheService.this.getUnionName(unionKey, s2, scoreKey), (String)stringTypedTuple
                        .getValue(), stringTypedTuple
                        .getScore().doubleValue());
                });
        });
  }

  private void putAllToCache(Map<String, V> dataMap, Map<String, Map<String, ZSetOperations.TypedTuple<String>>> scoreMap) {
    this.redisProUtil.hPutAll(this.nameSpace + ":HM:", dataMap);

    if (scoreMap == null || scoreMap.size() == 0) {
      return;
    }
    scoreMap.forEach((s, stringTypedTupleMap)->
        {
            stringTypedTupleMap.forEach((key, stringTypedTuple)->
                {
                    CacheService.this.redisProUtil.zAdd(CacheService.this.nameSpace + "_" + s + ":ZS:", (String)stringTypedTuple.getValue(), stringTypedTuple.getScore().doubleValue());
                });
        });
  }

  private void putAllToCache(AllCacheData<V> allCacheData) {
    this.redisProUtil.hPutAll(this.nameSpace + ":HM:", allCacheData.getDataMap());

    Map<String, CacheData<V>> cacheDataMap = allCacheData.getCacheDataMap();
    cacheDataMap.forEach((s, vCacheData)->
        {
            CacheService.this.putToCache(vCacheData);
        });
  }

  private String getGroupName(String groupKey, String keyValue, String sortName) {
    return this.nameSpace + ":GROUP:" + groupKey + ":" + keyValue + ":" + sortName;
  }

  private String getUnionName(String unionKey, String keyValue, String sortName) {
    return this.nameSpace + ":UNION:" + unionKey + ":" + keyValue + ":" + sortName;
  }

  public String getCacheSimplePrimaryKey(V data) {
    return this.nameSpace + ":PRIMARY:" + getSimplePrimaryKey(data);
  }

  public String getCacheSimpleUnionKey(V data) {
    return this.nameSpace + ":UNION:" + getSimpleUnionKey(data);
  }

  public abstract String getNameSpace();

  public abstract String getSimplePrimaryKey(V paramV);

  public abstract List<String> getPrimaryKeyList(V paramV);

  public abstract String getSimpleUnionKey(V paramV);

  public abstract List<TreeMap<String, String>> getUnionKeyList(V paramV);

  public abstract Map<String, Double> getScoreMap(V paramV);

  public abstract Map<String, String> getGroupMap(V paramV);

  public abstract Map<String, SortTypeEnum> getDefaultSortMap();

  public abstract Class<V> getType();

  public abstract String getQueryExpFromExample(V paramV);
}
