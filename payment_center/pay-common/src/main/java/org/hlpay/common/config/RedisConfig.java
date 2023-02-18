 package org.hlpay.common.config;

 import com.fasterxml.jackson.annotation.JsonAutoDetect;
 import com.fasterxml.jackson.annotation.JsonTypeInfo;
 import com.fasterxml.jackson.annotation.PropertyAccessor;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
 import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
 import org.hlpay.common.util.FastJsonRedisSerializer;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.context.annotation.Primary;
 import org.springframework.data.redis.connection.RedisConnectionFactory;
 import org.springframework.data.redis.core.RedisTemplate;
 import org.springframework.data.redis.serializer.RedisSerializer;
 import org.springframework.data.redis.serializer.StringRedisSerializer;

 @Configuration
 public class RedisConfig
 {
   @Bean
   public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisFactory) {
     RedisTemplate<String, Object> template = new RedisTemplate();
     template.setConnectionFactory(redisFactory);
     FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
     ObjectMapper om = new ObjectMapper();
     om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
     om.activateDefaultTyping((PolymorphicTypeValidator)LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

     fastJsonRedisSerializer.setObjectMapper(om);
     StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

     template.setKeySerializer((RedisSerializer)stringRedisSerializer);

     template.setHashKeySerializer((RedisSerializer)stringRedisSerializer);

     template.setValueSerializer((RedisSerializer)fastJsonRedisSerializer);

     template.setHashValueSerializer((RedisSerializer)fastJsonRedisSerializer);
     template.afterPropertiesSet();
     return template;
   }


   @Bean
   @Primary
   public RedisTemplate<String, String> StringRedisTemplate(RedisConnectionFactory redisFactory) {
     RedisTemplate<String, String> template = new RedisTemplate();
     template.setConnectionFactory(redisFactory);
     FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
     ObjectMapper om = new ObjectMapper();
     om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
     om.activateDefaultTyping((PolymorphicTypeValidator)LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

     fastJsonRedisSerializer.setObjectMapper(om);
     StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

     template.setKeySerializer((RedisSerializer)stringRedisSerializer);

     template.setHashKeySerializer((RedisSerializer)stringRedisSerializer);

     template.setValueSerializer((RedisSerializer)fastJsonRedisSerializer);

     template.setHashValueSerializer((RedisSerializer)fastJsonRedisSerializer);
     template.afterPropertiesSet();
     return template;
   }
 }





