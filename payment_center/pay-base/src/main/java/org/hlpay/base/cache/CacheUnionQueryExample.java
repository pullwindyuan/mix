 package org.hlpay.base.cache;

 import java.util.TreeMap;

 public class CacheUnionQueryExample extends CacheQueryExample {
   private TreeMap<String, String> unionQueryMap;

   public String toString() {
     return "CacheUnionQueryExample(unionQueryMap=" + getUnionQueryMap() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object<String, String> $unionQueryMap = (Object<String, String>)getUnionQueryMap(); return result * 59 + (($unionQueryMap == null) ? 43 : $unionQueryMap.hashCode()); } protected boolean canEqual(Object other) { return other instanceof CacheUnionQueryExample; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CacheUnionQueryExample)) return false;  CacheUnionQueryExample other = (CacheUnionQueryExample)o; if (!other.canEqual(this)) return false;  Object<String, String> this$unionQueryMap = (Object<String, String>)getUnionQueryMap(), other$unionQueryMap = (Object<String, String>)other.getUnionQueryMap(); return !((this$unionQueryMap == null) ? (other$unionQueryMap != null) : !this$unionQueryMap.equals(other$unionQueryMap)); } public CacheUnionQueryExample setUnionQueryMap(TreeMap<String, String> unionQueryMap) { this.unionQueryMap = unionQueryMap; return this; }





   public TreeMap<String, String> getUnionQueryMap() {
     return this.unionQueryMap;
   }
   public CacheUnionQueryExample addQuery(String name, String value) {
     this.unionQueryMap.put(name, value);
     return this;
   }
 }

