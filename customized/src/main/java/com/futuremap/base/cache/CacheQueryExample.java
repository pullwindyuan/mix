package com.futuremap.base.cache;

import java.util.Map;

public class CacheQueryExample {
  private String keyScanExp;
  private Map<String, SortEnum> sortMap;
  private Integer offset;
  private Integer size;

  public CacheQueryExample setKeyScanExp(String keyScanExp) { this.keyScanExp = keyScanExp; return this;    } 
   public CacheQueryExample setSortMap(Map<String, SortEnum> sortMap) { this.sortMap = sortMap; return this;    } 
   public CacheQueryExample setOffset(Integer offset) { this.offset = offset; return this;    } 
   public CacheQueryExample setSize(Integer size) { this.size = size; return this;    }


  public String getKeyScanExp() {
    return this.keyScanExp;
  }


  public Map<String, SortEnum> getSortMap() {
    return this.sortMap;
  }


  public Integer getOffset() {
    return this.offset;
  }


  public Integer getSize() {
    return this.size;
  }
  public CacheQueryExample addSort(String name, SortEnum sortEnum) {
    this.sortMap.put(name, sortEnum);
    return this;
  }
}
