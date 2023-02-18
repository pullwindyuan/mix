//package com.futuremap.erp.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * @author ken
// * @title BaseRedisConfig
// * @description Redis基础配置
// * @date 2020/9/30 18:01
// */
//@EnableCaching
//@Configuration
//public class BaseRedisConfig extends CachingConfigurerSupport {
//
//    /**
//     * 获取redis模板对象
//     * @param redisConnectionFactory redis连接工程对象
//     * @return
//     */
//    @Primary
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        return createRedisTemplate(redisConnectionFactory);
//    }
//
//    /**
//     * 创建redis模板对象
//     * @param redisConnectionFactory
//     * @return
//     */
//    public RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        // Jackson序列化号处理对象
//        Jackson2JsonRedisSerializer<Object> jacksonSerializer = redisSerializer();
//        // String序列化号处理对象
//        StringRedisSerializer stringSerializer = new StringRedisSerializer();
//        // 初始化Redis模板对象
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        // 连接工程设置
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        // key采用String的序列化方式
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        // hash的Key采用String的序列化方式
//        redisTemplate.setKeySerializer(stringSerializer);
//        // value序列化采用Jackson
//        redisTemplate.setValueSerializer(jacksonSerializer);
//        // Hash的value序列化采用Jackson
//        redisTemplate.setHashValueSerializer(jacksonSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    /**
//     * redis序列号处理
//     * @return
//     */
//    public Jackson2JsonRedisSerializer<Object> redisSerializer() {
//        // 创建JSON序列化器
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 必须设置，否则无法将JSON转化为对象，会转化成Map类型
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(objectMapper);
//        return serializer;
//    }
//
//
////    @Bean
////    public RedisService redisService(){
////        return new RedisServiceImpl();
////    }
//}
