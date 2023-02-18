 package com.futuremap.base.util;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.core.type.TypeReference;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 import java.io.IOException;
 import java.util.List;
 import java.util.Map;


 public class JsonUtil
 {
   public static final ObjectMapper mapper = new ObjectMapper();

   private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
   static {
     System.setProperty("fastjson.compatibleWithJavaBean", "true");
   }

   public static String object2Json(Object object) {
     if (object == null) {
       return null;
     }
     return JSONObject.toJSONString(object);
   }

   public static <T> T getObjectFromJson(String json, Class<T> clazz) {
     if (json == null) {
       return null;
     }
     return (T)JSON.parseObject(json, clazz);
   }

   public static <T> List<T> getObjectListFromJson(String json, Class<T> clazz) {
     if (json == null) {
       return null;
     }
     return JSON.parseArray(json, clazz);
   }

   public static JSONObject getJSONObjectFromJson(String json) {
     if (json == null) {
       return null;
     }
     return JSONObject.parseObject(json);
   }

   public static JSONObject getJSONObjectFromObj(Object object) {
     if (object == null) {
       return null;
     }
     return (JSONObject)JSONObject.toJSON(object);
   }

   public static String toString(Object obj) {
     if (obj == null) {
       return null;
     }
     if (obj.getClass() == String.class) {
       return (String) obj;
     }
     try {
       return mapper.writeValueAsString(obj);
     } catch (JsonProcessingException e) {
       logger.error("json序列化出错：" + obj, e);
       return null;
     }
   }

   public static <T> T toBean(String json, Class<T> tClass) {
     try {
       return mapper.readValue(json, tClass);
     } catch (IOException e) {
       logger.error("json解析出错：" + json, e);
       return null;
     }
   }

   public static <E> List<E> toList(String json, Class<E> eClass) {
     try {
       return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
     } catch (IOException e) {
       logger.error("json解析出错：" + json, e);
       return null;
     }
   }

   public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
     try {
       return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
     } catch (IOException e) {
       logger.error("json解析出错：" + json, e);
       return null;
     }
   }

   public static <T> T nativeRead(String json, TypeReference<T> type) {
     try {
       return mapper.readValue(json, type);
     } catch (IOException e) {
       logger.error("json解析出错：" + json, e);
       return null;
     }
   }
 }
