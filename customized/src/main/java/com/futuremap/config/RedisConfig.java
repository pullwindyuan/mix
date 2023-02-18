 package com.futuremap.config;

 import com.futuremap.base.util.FastJsonRedisSerializer;
 import com.fasterxml.jackson.annotation.JsonAutoDetect;
 import com.fasterxml.jackson.annotation.JsonTypeInfo;
 import com.fasterxml.jackson.annotation.PropertyAccessor;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
 import org.redisson.Redisson;
 import org.redisson.api.RedissonClient;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.context.annotation.Primary;
 import org.springframework.core.env.Environment;
 import org.springframework.data.redis.connection.RedisConnectionFactory;
 import org.springframework.data.redis.core.RedisTemplate;
 import org.springframework.data.redis.serializer.RedisSerializer;
 import org.springframework.data.redis.serializer.StringRedisSerializer;
 import org.redisson.config.Config;
 import org.springframework.stereotype.Service;

 import javax.annotation.Resource;
 import java.io.IOException;

 /**
  * 部署到外网开发期间不启用redis
  */
 //@Configuration
 //@Service
 public class RedisConfig
 {
     @Resource
     private Environment env;
//     @Bean
//     public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
//         RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
//         redisTemplate.setKeySerializer(new StringRedisSerializer());
//         redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//         redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//         return redisTemplate;
//     }

   @Bean
   public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisFactory) {
     RedisTemplate<String, Object> template = new RedisTemplate();
     template.setConnectionFactory(redisFactory);
     FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
     ObjectMapper om = new ObjectMapper();
     om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
     om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

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
     om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

     fastJsonRedisSerializer.setObjectMapper(om);
     StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

     template.setKeySerializer((RedisSerializer)stringRedisSerializer);

     template.setHashKeySerializer((RedisSerializer)stringRedisSerializer);

     template.setValueSerializer((RedisSerializer)fastJsonRedisSerializer);

     template.setHashValueSerializer((RedisSerializer)fastJsonRedisSerializer);
     template.afterPropertiesSet();
     return template;
   }

     @Bean(destroyMethod="shutdown")
     RedissonClient redisson() throws IOException {
         //1、创建配置
         Config config = new Config();
         String address = this.env.getProperty("spring.redis.host");
         String port = this.env.getProperty("spring.redis.port");
         config.useSingleServer().setAddress("redis://" + address + ":" + port);
         return Redisson.create(config);
     }
 }





