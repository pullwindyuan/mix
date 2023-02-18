 package com.futuremap.base.util;

 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.redis.connection.DataType;
 import org.springframework.data.redis.connection.ReturnType;
 import org.springframework.data.redis.connection.SortParameters;
 import org.springframework.data.redis.core.*;
 import org.springframework.data.redis.core.query.SortQuery;
 import org.springframework.data.redis.core.query.SortQueryBuilder;
 import org.springframework.data.redis.serializer.RedisSerializer;
 import org.springframework.data.redis.serializer.StringRedisSerializer;
 import org.springframework.stereotype.Component;

 import javax.annotation.Resource;
 import java.math.BigDecimal;
 import java.nio.ByteBuffer;
 import java.nio.CharBuffer;
 import java.nio.charset.Charset;
 import java.util.*;
 import java.util.concurrent.TimeUnit;

 @Component
 public class RedisProUtil<V> {
   protected final Logger logger = LoggerFactory.getLogger(getClass());
   public static Long DEFAULTE_COUNT = Long.valueOf(10000L);

   @Autowired
   private StringRedisTemplate stringRedisTemplate;

   @Resource
   private RedisTemplate<String, V> redisTemplate;

   @Resource
   private RedisTemplate<String, String> redisTemplateForSet;
   HashOperations<String, String, V> hashOperations;

   public HashOperations<String, String, V> getHashOperations() {
     return this.hashOperations;
   }

   public void init() {
     this.hashOperations = this.redisTemplate.opsForHash();
   }

   public void setRedisTemplate(StringRedisTemplate stringRedisTemplate) {
     StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
     stringRedisTemplate.setKeySerializer((RedisSerializer)stringRedisSerializer);
     stringRedisTemplate.setValueSerializer((RedisSerializer)stringRedisSerializer);
     stringRedisTemplate.setHashKeySerializer((RedisSerializer)stringRedisSerializer);
     stringRedisTemplate.setHashValueSerializer((RedisSerializer)stringRedisSerializer);
     this.stringRedisTemplate = stringRedisTemplate;
   }

   public void setRedisTemplateSerializer(RedisSerializer stringSerializer) {
     this.stringRedisTemplate.setKeySerializer(stringSerializer);
     this.stringRedisTemplate.setValueSerializer(stringSerializer);
     this.stringRedisTemplate.setHashKeySerializer(stringSerializer);
     this.stringRedisTemplate.setHashValueSerializer(stringSerializer);

     this.redisTemplate.setKeySerializer(stringSerializer);
     this.redisTemplate.setValueSerializer(stringSerializer);
     this.redisTemplate.setHashKeySerializer(stringSerializer);
     this.redisTemplate.setHashValueSerializer(stringSerializer);

     this.redisTemplateForSet.setKeySerializer(stringSerializer);
     this.redisTemplateForSet.setValueSerializer(stringSerializer);
     this.redisTemplateForSet.setHashKeySerializer(stringSerializer);
     this.redisTemplateForSet.setHashValueSerializer(stringSerializer);
   }
   public StringRedisTemplate getRedisTemplate() {
     return this.stringRedisTemplate;
   }

   public RedisTemplate getCommonRedisTemplate() {
     return this.redisTemplate;
   }

   public RedisTemplate getZSetRedisTemplate() {
     return this.redisTemplateForSet;
   }

   public String randomKey() {
     return (String)this.stringRedisTemplate.randomKey();
   }

   public void delete(String key) {
     this.redisTemplate.delete(key);
   }

   public void delete(Collection<String> keys) {
     this.redisTemplate.delete(keys);
   }

   public byte[] dump(String key) {
     return this.redisTemplate.dump(key);
   }

   public Boolean hasKey(String key) {
     return this.redisTemplate.hasKey(key);
   }

   public Boolean expire(String key, long timeout, TimeUnit unit) {
     return this.redisTemplate.expire(key, timeout, unit);
   }

   public Boolean expireAt(String key, Date date) {
     return this.redisTemplate.expireAt(key, date);
   }

   public Set<String> keys(String pattern) {
     return this.redisTemplate.keys(pattern);
   }

   public Boolean move(String key, int dbIndex) {
     return this.redisTemplate.move(key, dbIndex);
   }

   public Boolean persist(String key) {
     return this.redisTemplate.persist(key);
   }

   public Long getExpire(String key, TimeUnit unit) {
     return this.redisTemplate.getExpire(key, unit);
   }

   public Long getExpire(String key) {
     return this.redisTemplate.getExpire(key);
   }

   public void rename(String oldKey, String newKey) {
     this.redisTemplate.rename(oldKey, newKey);
   }

   public Boolean renameIfAbsent(String oldKey, String newKey) {
     return this.redisTemplate.renameIfAbsent(oldKey, newKey);
   }

   public DataType type(String key) {
     return this.redisTemplate.type(key);
   }

   public void set(String key, V contentValue) {
     this.redisTemplate.opsForValue().set(key, contentValue);
   }

   public void set(String key, V contentValue, long timeout, TimeUnit unit) {
     this.redisTemplate.opsForValue().set(key, contentValue, timeout, unit);
   }

   public V get(String key) {
     return (V)this.redisTemplate.opsForValue().get(key);
   }

   public String getRange(String key, long start, long end) {
     return this.redisTemplate.opsForValue().get(key, start, end);
   }

   public V getAndSet(String key, V contentValue) {
     return (V)this.redisTemplate.opsForValue().getAndSet(key, contentValue);
   }

   public Boolean getBit(String key, long offset) {
     return this.redisTemplate.opsForValue().getBit(key, offset);
   }

   public List<V> multiGet(Collection<String> keys) {
     return this.redisTemplate.opsForValue().multiGet(keys);
   }

   public boolean setBit(String key, long offset, boolean contentValue) {
     return this.redisTemplate.opsForValue().setBit(key, offset, contentValue).booleanValue();
   }

   public void setEx(String key, V contentValue, long timeout, TimeUnit unit) {
     this.redisTemplate.opsForValue().set(key, contentValue, timeout, unit);
   }

   public boolean setIfAbsent(String key, V contentValue) {
     return this.redisTemplate.opsForValue().setIfAbsent(key, contentValue).booleanValue();
   }

   public void setRange(String key, V contentValue, long offset) {
     this.redisTemplate.opsForValue().set(key, contentValue, offset);
   }

   public Long size(String key) {
     return this.redisTemplate.opsForValue().size(key);
   }

   public void multiSet(Map<String, V> maps) {
     this.redisTemplate.opsForValue().multiSet(maps);
   }

   public boolean multiSetIfAbsent(Map<String, V> maps) {
     return this.redisTemplate.opsForValue().multiSetIfAbsent(maps).booleanValue();
   }

   public Long incrBy(String key, long increment) {
     return this.redisTemplate.opsForValue().increment(key, increment);
   }

   public Double incrByFloat(String key, double increment) {
     return this.redisTemplate.opsForValue().increment(key, increment);
   }

   public V hGet(String key, String field) {
     return (V)this.hashOperations.get(key, field);
   }

   public Map<String, V> hGetAll(String key) {
     return this.hashOperations.entries(key);
   }

   public List<Object> hMultiGet(String key, Collection<Object> fields) {
     return this.redisTemplate.opsForHash().multiGet(key, fields);
   }

   public void hPut(String key, String hashKey, V contentValue) {
     this.redisTemplate.opsForHash().put(key, hashKey, contentValue);
   }

   public void hPutAll(String key, Map<String, V> maps) {
     this.redisTemplate.opsForHash().putAll(key, maps);
   }

   public Boolean hPutIfAbsent(String key, String hashKey, V contentValue) {
     return this.redisTemplate.opsForHash().putIfAbsent(key, hashKey, contentValue);
   }

   public Long hDelete(String key, Object... fields) {
     return this.redisTemplate.opsForHash().delete(key, fields);
   }

   public boolean hExists(String key, String field) {
     return this.redisTemplate.opsForHash().hasKey(key, field).booleanValue();
   }

   public Long hIncrBy(String key, String field, long increment) {
     return this.redisTemplate.opsForHash().increment(key, field, increment);
   }

   public Double hIncrByFloat(String key, String field, double delta) {
     return this.redisTemplate.opsForHash().increment(key, field, delta);
   }

   public Set<Object> hKeys(String key) {
     return this.redisTemplate.opsForHash().keys(key);
   }

   public Long hSize(String key) {
     return this.redisTemplate.opsForHash().size(key);
   }

   public List<Object> hValues(String key) {
     return this.redisTemplate.opsForHash().values(key);
   }

   public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
     return this.redisTemplate.opsForHash().scan(key, options);
   }

   public V lIndex(String key, long index) {
     return (V)this.redisTemplate.opsForList().index(key, index);
   }

   public List<V> lRange(String key, long start, long end) {
     return this.redisTemplate.opsForList().range(key, start, end);
   }

   public Long lLeftPush(String key, V contentValue) {
     return this.redisTemplate.opsForList().leftPush(key, contentValue);
   }

   public Long lLeftPushAll(String key, V... contentValue) {
     return this.redisTemplate.opsForList().leftPushAll(key, contentValue);
   }

   public Long lLeftPushAll(String key, Collection<V> contentValue) {
     return this.redisTemplate.opsForList().leftPushAll(key, contentValue);
   }

   public Long lLeftPushIfPresent(String key, V contentValue) {
     return this.redisTemplate.opsForList().leftPushIfPresent(key, contentValue);
   }

   public Long lLeftPush(String key, V pivot, V contentValue) {
     return this.redisTemplate.opsForList().leftPush(key, pivot, contentValue);
   }

   public Long lRightPush(String key, V contentValue) {
     return this.redisTemplate.opsForList().rightPush(key, contentValue);
   }

   public Long lRightPushAll(String key, V... contentValue) {
     return this.redisTemplate.opsForList().rightPushAll(key, contentValue);
   }

   public Long lRightPushAll(String key, Collection<V> contentValue) {
     return this.redisTemplate.opsForList().rightPushAll(key, contentValue);
   }

   public Long lRightPushIfPresent(String key, V contentValue) {
     return this.redisTemplate.opsForList().rightPushIfPresent(key, contentValue);
   }

   public Long lRightPush(String key, V pivot, V contentValue) {
     return this.redisTemplate.opsForList().rightPush(key, pivot, contentValue);
   }

   public void lSet(String key, long index, V contentValue) {
     this.redisTemplate.opsForList().set(key, index, contentValue);
   }

   public V lLeftPop(String key) {
     return (V)this.redisTemplate.opsForList().leftPop(key);
   }

   public V lBLeftPop(String key, long timeout, TimeUnit unit) {
     return (V)this.redisTemplate.opsForList().leftPop(key, timeout, unit);
   }

   public V lRightPop(String key) {
     return (V)this.redisTemplate.opsForList().rightPop(key);
   }

   public V lBRightPop(String key, long timeout, TimeUnit unit) {
     return (V)this.redisTemplate.opsForList().rightPop(key, timeout, unit);
   }

   public V lRightPopAndLeftPush(String sourceKey, String destinationKey) {
     return (V)this.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
   }

   public V lBRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
     return (V)this.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
   }

   public Long lRemove(String key, long index, V contentValue) {
     return this.redisTemplate.opsForList().remove(key, index, contentValue);
   }

   public void lTrim(String key, long start, long end) {
     this.redisTemplate.opsForList().trim(key, start, end);
   }

   public Long lLen(String key) {
     return this.redisTemplate.opsForList().size(key);
   }

   public Long sAdd(String key, String... values) {
     return this.redisTemplateForSet.opsForSet().add(key, values);
   }

   public Long sRemove(String key, Object... values) {
     return this.redisTemplateForSet.opsForSet().remove(key, values);
   }

   public String sPop(String key) {
     return (String)this.redisTemplateForSet.opsForSet().pop(key);
   }

   public Boolean sMove(String key, String contentValue, String destKey) {
     return this.redisTemplateForSet.opsForSet().move(key, contentValue, destKey);
   }

   public Long sSize(String key) {
     return this.redisTemplateForSet.opsForSet().size(key);
   }

   public Boolean sIsMember(String key, Object contentValue) {
     return this.redisTemplateForSet.opsForSet().isMember(key, contentValue);
   }

   public Set<String> sIntersect(String key, String otherKey) {
     return this.redisTemplateForSet.opsForSet().intersect(key, otherKey);
   }

   public Set<String> sIntersect(String key, Collection<String> otherKeys) {
     return this.redisTemplateForSet.opsForSet().intersect(key, otherKeys);
   }

   public Long sIntersectAndStore(String key, String otherKey, String destKey) {
     return this.redisTemplateForSet.opsForSet().intersectAndStore(key, otherKey, destKey);
   }

   public Long sIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
     return this.redisTemplateForSet.opsForSet().intersectAndStore(key, otherKeys, destKey);
   }

   public Set<String> sUnion(String key, String otherKeys) {
     return this.redisTemplateForSet.opsForSet().union(key, otherKeys);
   }

   public Set<String> sUnion(String key, Collection<String> otherKeys) {
     return this.redisTemplateForSet.opsForSet().union(key, otherKeys);
   }

   public Long sUnionAndStore(String key, String otherKey, String destKey) {
     return this.redisTemplateForSet.opsForSet().unionAndStore(key, otherKey, destKey);
   }

   public Long sUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
     return this.redisTemplateForSet.opsForSet().unionAndStore(key, otherKeys, destKey);
   }

   public Set<String> sDifference(String key, String otherKey) {
     return this.redisTemplateForSet.opsForSet().difference(key, otherKey);
   }

   public Set<String> sDifference(String key, Collection<String> otherKeys) {
     return this.redisTemplateForSet.opsForSet().difference(key, otherKeys);
   }

   public Long sDifference(String key, String otherKey, String destKey) {
     return this.redisTemplateForSet.opsForSet().differenceAndStore(key, otherKey, destKey);
   }

   public Long sDifference(String key, Collection<String> otherKeys, String destKey) {
     return this.redisTemplateForSet.opsForSet().differenceAndStore(key, otherKeys, destKey);
   }

   public Set<String> setMembers(String key) {
     return this.redisTemplateForSet.opsForSet().members(key);
   }

   public String sRandomMember(String key) {
     return (String)this.redisTemplateForSet.opsForSet().randomMember(key);
   }

   public List<String> sRandomMembers(String key, long count) {
     return this.redisTemplateForSet.opsForSet().randomMembers(key, count);
   }

   public Set<String> sDistinctRandomMembers(String key, long count) {
     return this.redisTemplateForSet.opsForSet().distinctRandomMembers(key, count);
   }

   public Cursor<String> sScan(String key, ScanOptions options) {
     return this.redisTemplateForSet.opsForSet().scan(key, options);
   }

   public Boolean zAdd(String key, String contentValue, double score) {
     return this.redisTemplateForSet.opsForZSet().add(key, contentValue, score);
   }

   public Long zAdd(String key, Set<ZSetOperations.TypedTuple<String>> values) {
     return this.redisTemplateForSet.opsForZSet().add(key, values);
   }

   public Long zRemove(String key, Object... values) {
     return this.redisTemplate.opsForZSet().remove(key, values);
   }

   public Double zIncrementScore(String key, String contentValue, double delta) {
     return this.redisTemplateForSet.opsForZSet().incrementScore(key, contentValue, delta);
   }

   public Long zRank(String key, Object contentValue) {
     return this.redisTemplateForSet.opsForZSet().rank(key, contentValue);
   }

   public Long zReverseRank(String key, Object contentValue) {
     return this.redisTemplateForSet.opsForZSet().reverseRank(key, contentValue);
   }

   public Set<String> zRange(String key, long start, long end) {
     return this.redisTemplateForSet.opsForZSet().range(key, start, end);
   }

   public Set<ZSetOperations.TypedTuple<String>> zRangeWithScores(String key, long start, long end) {
     return this.redisTemplateForSet.opsForZSet().rangeWithScores(key, start, end);
   }

   public Set<String> zRangeByScore(String key, double min, double max) {
     return this.redisTemplateForSet.opsForZSet().rangeByScore(key, min, max);
   }

   public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max) {
     return this.redisTemplateForSet.opsForZSet().rangeByScoreWithScores(key, min, max);
   }

   public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
     return this.redisTemplateForSet.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
   }

   public Set<String> zReverseRange(String key, long start, long end) {
     return this.redisTemplateForSet.opsForZSet().reverseRange(key, start, end);
   }

   public Set<ZSetOperations.TypedTuple<String>> zReverseRangeWithScores(String key, long start, long end) {
     return this.redisTemplateForSet.opsForZSet().reverseRangeWithScores(key, start, end);
   }

   public Set<String> zReverseRangeByScore(String key, double min, double max) {
     return this.redisTemplateForSet.opsForZSet().reverseRangeByScore(key, min, max);
   }

   public Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(String key, double min, double max) {
     return this.redisTemplateForSet.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
   }

   public Set<String> zReverseRangeByScore(String key, double min, double max, long start, long end) {
     return this.redisTemplateForSet.opsForZSet().reverseRangeByScore(key, min, max, start, end);
   }

   public Long zCount(String key, double min, double max) {
     return this.redisTemplateForSet.opsForZSet().count(key, min, max);
   }

   public Long zSize(String key) {
     return this.redisTemplateForSet.opsForZSet().size(key);
   }

   public Long zZCard(String key) {
     return this.redisTemplateForSet.opsForZSet().zCard(key);
   }

   public Double zScore(String key, Object contentValue) {
     return this.redisTemplateForSet.opsForZSet().score(key, contentValue);
   }

   public Long zRemoveRange(String key, long start, long end) {
     return this.redisTemplateForSet.opsForZSet().removeRange(key, start, end);
   }

   public Long zRemoveRangeByScore(String key, double min, double max) {
     return this.redisTemplateForSet.opsForZSet().removeRangeByScore(key, min, max);
   }

   public Long zUnionAndStore(String key, String otherKey, String destKey) {
     return this.redisTemplateForSet.opsForZSet().unionAndStore(key, otherKey, destKey);
   }

   public Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
     return this.redisTemplateForSet.opsForZSet()
       .unionAndStore(key, otherKeys, destKey);
   }

   public Long zIntersectAndStore(String key, String otherKey, String destKey) {
     return this.redisTemplateForSet.opsForZSet().intersectAndStore(key, otherKey, destKey);
   }

   public Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
     return this.redisTemplateForSet.opsForZSet().intersectAndStore(key, otherKeys, destKey);
   }

   public Cursor<ZSetOperations.TypedTuple<String>> zScan(String key, ScanOptions options) {
     return this.redisTemplateForSet.opsForZSet().scan(key, options);
   }

   public void setToPage(String key, String hkey, double score, V contentValue) {
     try {
       this.redisTemplateForSet.opsForZSet().add(key + ":primartPage", hkey, score);
       hPut(key, hkey, contentValue);
       this.logger.debug("setPage {}", key);
     } catch (Exception e) {
       this.logger.warn("setPage {}", key, e);
     }
   }

   public void setToPrimaryPage(String key, String hkey, double score, V contentValue) {
     setToPage(key + ":primary", hkey, score, contentValue);
   }

   public List<V> getPage(String key, String hashKeyMatch, int offset, int count) {
     List<V> result = null;
     try {
       Collection<String> keysResult = this.redisTemplateForSet.opsForZSet().rangeByScore(key + ":page", 1.0D, 100000.0D, ((offset - 1) * count), count);
       this.hashOperations = this.redisTemplate.opsForHash();
       result = this.hashOperations.multiGet(key, keysResult);
       this.logger.debug("getPage {}", key);
     } catch (Exception e) {
       this.logger.warn("getPage {}", key, e);
     }
     return result;
   }

   public List<V> getPageFromPrimary(String key, int offset, int count) {
     return getPage(key + ":primary", null, offset, count);
   }

   public Integer getSize(String key) {
     Integer num = Integer.valueOf(0);
     try {
       Long size = this.redisTemplateForSet.opsForZSet().zCard(key + ":page");
       this.logger.debug("getSize {}", key);
       return Integer.valueOf(size.intValue());
     } catch (Exception e) {
       this.logger.warn("getSize {}", key, e);

       return num;
     }
   }

   public boolean setD(String key, String value, Long d2, Long d3) {
     long start = System.currentTimeMillis();
     while (true) {
       if (System.currentTimeMillis() - start > 2000L) {
         return false;
       }
       long d1 = 0L;
       byte[] originBytes = zScoreBytes(key, value);
       Long oldScore = Long.valueOf(convertZscore(originBytes));
       if (originBytes != null)
       {
         d1 = getD1ByScore(oldScore.longValue());
       }
       if (d2 == null)
       {
         d2 = Long.valueOf(getD2ByScore(oldScore.longValue()));
       }

       if (d3 == null)
       {
         d3 = Long.valueOf(getD3ByScore(oldScore.longValue()));
       }

       long score = genScore(d1, d2.longValue(), d3.longValue());
       if (compareAndSetDimension(key, value, originBytes, score)) {
         return true;
       }
     }
   } Long SUCCESS = Long.valueOf(1L);
   String script = "if (((not (redis.call('zscore', KEYS[1], ARGV[1]))) and (ARGV[2] == '')) or redis.call('zscore', KEYS[1], ARGV[1]) == ARGV[2])  then redis.call('zadd',KEYS[1],ARGV[3],ARGV[1]) return 1 else return 0 end";

   private boolean compareAndSetDimension(String setKey, String key, byte[] oldScore, long newScore) {
     Long result = (Long)this.redisTemplate.execute((RedisCallback<Long>) connection -> {
           try {
             ObjectMapper om = new ObjectMapper();
             return (Long)connection.eval(this.script.getBytes(), ReturnType.fromJavaType(Long.class), 1, new byte[][] { setKey.getBytes(), key.getBytes(), oldScore, om.writeValueAsBytes(Long.valueOf(newScore)) });
           } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
           }
         });
     return this.SUCCESS.equals(result);
   }

   public char[] getChars(byte[] bytes) {
     Charset cs = Charset.forName("UTF-8");
     ByteBuffer bb = ByteBuffer.allocate(bytes.length);
     bb.put(bytes);
     bb.flip();
     CharBuffer cb = cs.decode(bb);
     return cb.array();
   }


   public long convertZscore(byte[] bytes) {
     char[] chars = getChars(bytes);
     long value = (new BigDecimal(String.valueOf(chars))).longValue();
     return value;
   }

   public byte[] zScoreBytes(String key, String value) {
     String script = "return redis.call('zscore', KEYS[1], ARGV[1])";
     return (byte[])this.redisTemplate.execute((RedisCallback<byte[]>) connection -> (byte[])connection.eval(script.getBytes(), ReturnType.fromJavaType(String.class), 1, new byte[][] { key.getBytes(), value.getBytes() }));
   }

   int[][] BITS = new int[][] { { 30, 33 }, { 29, 1 }, { 0, 29 } };

   int size1 = 33;
   int size2 = 1;
   int size3 = 29;
   long d1Max = maxBySize(this.size1);
   long d2Max = maxBySize(this.size2);
   long d3Max = maxBySize(this.size3);

   public long genScore(long d1, long d2, long d3) {
     return (d1 & this.d1Max) << this.size2 + this.size3 | (d2 & this.d2Max) << this.size3 | d3 & this.d3Max;
   }

   public long getD1ByScore(long score) {
     return score >> this.size2 + this.size3 & this.d1Max;
   }

   public long getD2ByScore(long score) {
     return score >> this.size3 & this.d2Max;
   }

   public long getD3ByScore(long score) {
     return score & this.d3Max;
   }

   public long incValue(long d1) {
     return (d1 & this.d1Max) << this.size2 + this.size3 | (0x0L & this.d2Max) << this.size3 | 0x0L & this.d3Max;
   }

   private long maxBySize(int len) {
     long r = 0L;
     while (len-- > 0)
     {
       r = r << 1L | 0x1L;
     }
     return r;
   }

   public String getRandomStr() {
     return String.valueOf((new Random()).nextInt(100));
   }

   public void sort(RedisTemplate redisTemplate) {
     String sortKey = "sortKey";

     StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

     redisTemplate.setKeySerializer((RedisSerializer)stringRedisSerializer);
     redisTemplate.setValueSerializer((RedisSerializer)stringRedisSerializer);
     redisTemplate.setHashKeySerializer((RedisSerializer)stringRedisSerializer);
     redisTemplate.setHashValueSerializer((RedisSerializer)stringRedisSerializer);

     redisTemplate.delete(sortKey);
     if (!redisTemplate.hasKey(sortKey).booleanValue()) {
       for (int j = 0; j < 10; j++) {
         redisTemplate.boundSetOps(sortKey).add(new Object[] { String.valueOf(j) });
         String hashKey = "hash" + j;
         String strId = String.valueOf(j);
         String strName = getRandomStr();
         String strSite = getRandomStr();
         redisTemplate.boundHashOps(hashKey).put("_id", strId);
         redisTemplate.boundHashOps(hashKey).put("Name", strName);
         redisTemplate.boundHashOps(hashKey).put("Site", strSite);

         System.out.printf("%s : {\"_id\": %s, \"Name\": %s, \"Site\", %s}\n", new Object[] { hashKey, strId, strName, strSite });
       }
     }
     SortQuery<String> sortQuery = SortQueryBuilder.sort(sortKey).by("hash*->Name").get("hash*->_id").get("hash*->Name").get("hash*->Site").order(SortParameters.Order.DESC).build();
     List<String> sortRslt = redisTemplate.sort(sortQuery);

     for (int i = 0; i < sortRslt.size(); ) {
       System.out.printf("{\"_id\": %s, \"Name\": %s, \"Site\", %s}\n", new Object[] { sortRslt.get(i + 2), sortRslt.get(i + 1), sortRslt.get(i) });
       i += 3;
     }
   }
 }

