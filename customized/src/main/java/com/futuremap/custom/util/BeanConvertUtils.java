 package com.futuremap.custom.util;

 import org.apache.commons.beanutils.BeanUtils;
 import org.apache.commons.beanutils.ConvertUtils;
 import org.apache.commons.beanutils.Converter;
 import org.apache.commons.beanutils.converters.*;

 import java.lang.reflect.InvocationTargetException;
 import java.math.BigDecimal;
 import java.util.*;

 public class BeanConvertUtils {
   static {
     ConvertUtils.register(new DateTimeConverter(), Date.class);
     DateConverter dateConverter = new DateConverter();
     dateConverter.setPattern("yyyy-MM-dd HH:mm:ss");
     ConvertUtils.register((Converter)dateConverter, Date.class);
     ConvertUtils.register((Converter)new LongConverter(null), Long.class);
     ConvertUtils.register((Converter)new ShortConverter(null), Short.class);
     ConvertUtils.register((Converter)new IntegerConverter(null), Integer.class);
     ConvertUtils.register((Converter)new DoubleConverter(null), Double.class);
     ConvertUtils.register((Converter)new BigDecimalConverter(null), BigDecimal.class);
   }

   public static Map<String, Object> bean2Map(Object obj) {
     try {
       Map<String, Object> map = BeanUtils.describe(obj);
       map.remove("class");
       return map;
     } catch (IllegalAccessException e) {
       e.printStackTrace();
     } catch (InvocationTargetException e) {
       e.printStackTrace();
     } catch (NoSuchMethodException e) {
       e.printStackTrace();
     }
     return null;
   }

   public static Map<String, Object> bean2MapWithOutNull(Object obj) {
     try {
       Map<String, Object> map = BeanUtils.describe(obj);
       map.remove("class");
       Set<Map.Entry<String, Object>> entrySet = map.entrySet();
       Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();

       Map<String, Object> newMap = new HashMap<>();
       while (iterator.hasNext()) {
         Map.Entry<String, Object> temp = iterator.next();
         if (temp.getValue() != null) {
           newMap.put(temp.getKey(), temp.getValue());
         }
       }
       return newMap;
     } catch (IllegalAccessException e) {
       e.printStackTrace();
     } catch (InvocationTargetException e) {
       e.printStackTrace();
     } catch (NoSuchMethodException e) {
       e.printStackTrace();
     }
     return null;
   }

   public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz) {
     if (map == null || clazz == null) {
       return null;
     }
     T bean = null;
     try {
       bean = clazz.newInstance();
       BeanUtils.populate(bean, map);
     } catch (InstantiationException e) {
       e.printStackTrace();
     } catch (IllegalAccessException e) {
       e.printStackTrace();
     } catch (InvocationTargetException e) {
       e.printStackTrace();
     }
     return bean;
   }

   public static <T> T map2BeanCommon(Map<Object, Object> map, Class<T> clazz) {
     if (map == null || clazz == null) {
       return null;
     }
     T bean = null;
     try {
       bean = clazz.newInstance();
       BeanUtils.populate(bean, map);
     } catch (InstantiationException e) {
       e.printStackTrace();
     } catch (IllegalAccessException e) {
       e.printStackTrace();
     } catch (InvocationTargetException e) {
       e.printStackTrace();
     }
     return bean;
   }

   public static void copyProperties(Object dest, Object src) {
     if (src == null || dest == null) {
       return;
     }
     try {
       BeanUtils.copyProperties(dest, src);
     } catch (IllegalAccessException e) {
       e.printStackTrace();
     } catch (InvocationTargetException e) {
       e.printStackTrace();
     } catch (IllegalArgumentException e) {
       e.printStackTrace();
     }
   }

   public static <T> List<T> mergeList(List<T> one, List<T> tow, Map<String, T> map, List<String> keys, IMerge<T> iMerge) {
     List<T> merge = new ArrayList<>();
     merge.addAll(one);
     merge.addAll(tow);

     if (keys == null) {
       keys = new ArrayList<>();
     }
     if (map == null) {
       map = new HashMap<>();
     }

     for (T t : merge) {
       map.put(iMerge.getKey(t), t);
     }
     final List<T> outputList = new ArrayList<>();
     final List<String> finalKeys = keys;
     map.forEach((s, t)->
         {
             outputList.add(t);
             finalKeys.add(s);
         });
     return outputList;
   }

   public static interface IMerge<T> {
     String getKey(T param1T);
   }
 }
