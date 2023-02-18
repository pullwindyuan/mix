package org.hlpay.base.cache;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hlpay.common.enumm.SortTypeEnum;

public interface ICache<T> {
  String getNameSpace();
  
  String getSimplePrimaryKey(T paramT);
  
  List<String> getPrimaryKey(T paramT);
  
  String getSimpleUnionKey(T paramT);
  
  List<TreeMap<String, String>> getUnionKeyList(T paramT);
  
  Map<String, Double> getScoreMap(T paramT);
  
  Map<String, String> getGroupMap(T paramT);
  
  Map<String, SortTypeEnum> getDefaultSortMap();
  
  String getQueryExpFromExample(T paramT);
}

