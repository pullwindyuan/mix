package com.futuremap.erp.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ken
 * @title BeanCopierUtils
 * @description bean工具类
 * @date 2020/10/10 18:30
 */
public class BeanCopierUtils {

    /**
     * 本地缓存
     */
    public static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    /**
     * 对象属性拷贝
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target){
        // 生成本地缓存key
        String beanKey =  generateKey(source.getClass(), target.getClass());
        // 拷贝对象初始化
        BeanCopier copier = null;
        // 判断本地缓存是否存在，存在直接复用
        if(!beanCopierMap.containsKey(beanKey)){
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);
    }

    /**
     * 生成本地缓存key
     *   源对象字符串 + 目标对象字符串
     * @param source 源对象
     * @param target 目标对象Class
     * @return 字符串
     */
    private static String generateKey(Class<?> source, Class<?> target){
        return source.toString() + target.toString();

    }

    /**
     * 单个对象属性拷贝
     * @param source 源对象
     * @param clazz 目标对象Class
     * @param <T> 目标对象类型
     * @param <M> 源对象类型
     * @return 目标对象
     */
    public static <T, M> T copyProperties(M source, Class<T> clazz){
        if (Objects.isNull(source) || Objects.isNull(clazz)) {
            throw new IllegalArgumentException();
        }
        return copyProperties(source, clazz, null);
    }

    /**
     * 列表对象拷贝
     * @param sources 源列表
     * @param clazz 源列表对象Class
     * @param <T> 目标列表对象类型
     * @param <M> 源列表对象类型
     * @return 目标列表
     */
    public static <T, M> List<T> copyObjects(List<M> sources, Class<T> clazz) {
        if(Objects.isNull(sources) || Objects.isNull(clazz) || sources.isEmpty()) {
            throw new IllegalArgumentException();
        }
        BeanCopier copier = BeanCopier.create(sources.get(0).getClass(), clazz, false);
        return Optional.of(sources)
                .orElse(new ArrayList<>())
                .stream().map(m -> copyProperties(m, clazz, copier))
                .collect(Collectors.toList());
    }

    /**
     * 单个对象属性拷贝
     * @param source 源对象
     * @param clazz 目标对象Class
     * @param copier copier
     * @param <T> 目标对象类型
     * @param <M> 源对象类型
     * @return 目标对象
     */
    private static <T, M> T copyProperties(M source, Class<T> clazz, BeanCopier copier){
        if (null == copier){
            copier = BeanCopier.create(source.getClass(), clazz, false);
        }
        T t = null;
        try {
            t = clazz.newInstance();
            copier.copy(source, t, null);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
