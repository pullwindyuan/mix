package com.futuremap.custom.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.futuremap.custom.dto.TreeAble;
import com.futuremap.custom.exception.BadRequestException;


public class TreeUtil {
	
	private final static int MAX_LEVEL = 30;

	
	/**
	 *  父类多个子类下，数据有问题, 有时间再改
	 * @param <T>
	 * @param list
	 * @return
	 */
	@Deprecated
	public static <T extends TreeAble> Map<String, T> generateTree1(List<T> list) {
        Map<String, T> inputMap = list.stream().collect(Collectors.toMap(T::getId, Function.identity()));
        Map<String, List<String>> parentMap = list.stream().collect(Collectors.groupingBy(T::getParentId,
                Collectors.mapping(T::getId, Collectors.toList())));
        //父节点
        Map<String, T> outputMap = new HashMap<>();
        list.stream().filter(entry -> !parentMap.containsKey(entry.getId()))
                .forEach(entry -> outputMap.put(entry.getId(), entry));
        // 拿到叶节点
        List<T> currentLevel = new ArrayList<>(outputMap.values());
        int i = 1;
        // 每次循环拿到当前层的上一层，放入到结果map中
        while (!currentLevel.isEmpty()) {
            currentLevel = currentLevel.stream().map(T::getParentId).filter(id -> !outputMap.containsKey(id))
                    .distinct().filter(inputMap::containsKey).map(inputMap::get)
                    // 从结果map中拿到孩子节点集合
                    .peek(entry -> entry.setChildren(
                            parentMap.get(entry.getId()).stream()
                                    .filter(outputMap::containsKey).map(outputMap::get).collect(Collectors.toList())))
                    .collect(Collectors.toList());
            currentLevel.forEach(entry -> outputMap.put(entry.getId(), entry));
            if (++i > MAX_LEVEL) {
                throw new RuntimeException("超过最大层级");
            }
        }
        return outputMap;
    }
	
	
	public static <T extends TreeAble> List<T> getTreeList(List<T> list) {
		Map<String, String>  idMap = list.stream().collect(Collectors.toMap(T::getId, T::getParentId));
		idMap.forEach((a,b)->{
			if(a.equals(idMap.get(b))) {
				throw new BadRequestException("树形数据异常");
			}
		});
		
		Map<String, List<T>> map = list.stream().collect(Collectors.groupingBy(T::getParentId));
		list.forEach(e -> {
			e.setChildren(map.getOrDefault(e.getId(), new ArrayList<>()));
		});
		return list;
	}

	public static <T extends TreeAble> Map<String, T> getTreeMap(List<T> list) {
		return getTreeList(list).stream().collect(Collectors.toMap(T::getId, e -> e));
	}
	
	
	public static <T extends TreeAble> List<T> getZeroParentTree(List<T> list) {
		List<T> tree = getTreeList(list).stream().filter(e -> "0".equals(e.getParentId()))
				.collect(Collectors.toList());
		return tree;
	}
}
