 package com.futuremap.base.util;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.parser.ParserConfig;
 import com.alibaba.fastjson.serializer.SerializerFeature;
 import com.fasterxml.jackson.databind.JavaType;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.fasterxml.jackson.databind.type.TypeFactory;
 import org.springframework.data.redis.serializer.RedisSerializer;
 import org.springframework.data.redis.serializer.SerializationException;
 import org.springframework.util.Assert;

 import java.nio.charset.Charset;

 public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
   private ObjectMapper objectMapper = new ObjectMapper();
   public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

   private Class<T> clazz;

   static {
     ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
   }



   public FastJsonRedisSerializer(Class<T> clazz) {
     this.clazz = clazz;
   }


   @Override
   public byte[] serialize(T t) throws SerializationException {
     if (t == null) {
       return new byte[0];
     }
     return JSON.toJSONString(t, new SerializerFeature[] { SerializerFeature.WriteClassName }).getBytes(DEFAULT_CHARSET);
   }


   @Override
   public T deserialize(byte[] bytes) throws SerializationException {
     if (bytes == null || bytes.length <= 0) {
       return null;
     }
     String str = new String(bytes, DEFAULT_CHARSET);

     return (T)JSON.parseObject(str, this.clazz);
   }

   public void setObjectMapper(ObjectMapper objectMapper) {
     Assert.notNull(objectMapper, "'objectMapper' must not be null");
     this.objectMapper = objectMapper;
   }

   protected JavaType getJavaType(Class<?> clazz) {
     return TypeFactory.defaultInstance().constructType(clazz);
   }
 }





