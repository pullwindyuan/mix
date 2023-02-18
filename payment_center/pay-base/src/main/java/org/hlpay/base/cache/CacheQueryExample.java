package org.hlpay.base.cache;

public class CacheQueryExample {
  private String keyScanExp;
  private Map<String, SortEnum> sortMap;
  private Integer offset;
  private Integer size;

  public CacheQueryExample setKeyScanExp(String keyScanExp) { this.keyScanExp = keyScanExp; return this; } public CacheQueryExample setSortMap(Map<String, SortEnum> sortMap) { this.sortMap = sortMap; return this; } public CacheQueryExample setOffset(Integer offset) { this.offset = offset; return this; } public CacheQueryExample setSize(Integer size) { this.size = size; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CacheQueryExample)) return false;  CacheQueryExample other = (CacheQueryExample)o; if (!other.canEqual(this)) return false;  Object this$keyScanExp = getKeyScanExp(), other$keyScanExp = other.getKeyScanExp(); if ((this$keyScanExp == null) ? (other$keyScanExp != null) : !this$keyScanExp.equals(other$keyScanExp)) return false;  Object<String, SortEnum> this$sortMap = (Object<String, SortEnum>)getSortMap(), other$sortMap = (Object<String, SortEnum>)other.getSortMap(); if ((this$sortMap == null) ? (other$sortMap != null) : !this$sortMap.equals(other$sortMap)) return false;  Object this$offset = getOffset(), other$offset = other.getOffset(); if ((this$offset == null) ? (other$offset != null) : !this$offset.equals(other$offset)) return false;  Object this$size = getSize(), other$size = other.getSize(); return !((this$size == null) ? (other$size != null) : !this$size.equals(other$size)); } protected boolean canEqual(Object other) { return other instanceof CacheQueryExample; } public int hashCode() { int PRIME = 59; result = 1; Object $keyScanExp = getKeyScanExp(); result = result * 59 + (($keyScanExp == null) ? 43 : $keyScanExp.hashCode()); Object<String, SortEnum> $sortMap = (Object<String, SortEnum>)getSortMap(); result = result * 59 + (($sortMap == null) ? 43 : $sortMap.hashCode()); Object $offset = getOffset(); result = result * 59 + (($offset == null) ? 43 : $offset.hashCode()); Object $size = getSize(); return result * 59 + (($size == null) ? 43 : $size.hashCode()); } public String toString() { return "CacheQueryExample(keyScanExp=" + getKeyScanExp() + ", sortMap=" + getSortMap() + ", offset=" + getOffset() + ", size=" + getSize() + ")"; }




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
