 package org.hlpay.base.cache;

 import java.util.TreeMap;

 public class CacheGroupQueryExample extends CacheQueryExample {
   private TreeMap<String, String> groupQueryMap;

   public String toString() {
     return "CacheGroupQueryExample(groupQueryMap=" + getGroupQueryMap() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object<String, String> $groupQueryMap = (Object<String, String>)getGroupQueryMap(); return result * 59 + (($groupQueryMap == null) ? 43 : $groupQueryMap.hashCode()); } protected boolean canEqual(Object other) { return other instanceof CacheGroupQueryExample; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CacheGroupQueryExample)) return false;  CacheGroupQueryExample other = (CacheGroupQueryExample)o; if (!other.canEqual(this)) return false;  Object<String, String> this$groupQueryMap = (Object<String, String>)getGroupQueryMap(), other$groupQueryMap = (Object<String, String>)other.getGroupQueryMap(); return !((this$groupQueryMap == null) ? (other$groupQueryMap != null) : !this$groupQueryMap.equals(other$groupQueryMap)); } public CacheGroupQueryExample setGroupQueryMap(TreeMap<String, String> groupQueryMap) { this.groupQueryMap = groupQueryMap; return this; }




   public TreeMap<String, String> getGroupQueryMap() {
     return this.groupQueryMap;
   }
   public CacheGroupQueryExample addQuery(String name, String value) {
     this.groupQueryMap.put(name, value);
     return this;
   }
 }
