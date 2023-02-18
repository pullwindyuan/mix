 package org.hlpay.base.plugin;

 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;

 public class PageModel<T>
   implements Serializable
 {
   public List<T> list = new ArrayList<>();
   public Integer count = Integer.valueOf(0);
   public String msg;
   public Boolean rel;
   public Integer code;
   public Object obj;

   public Object getObj() {
     return this.obj;
   }

   public void setObj(Object obj) {
     this.obj = obj;
   }

   public Integer getCode() {
     return this.code;
   }

   public void setCode(Integer code) {
     this.code = code;
   }

   public List<T> getList() {
     return this.list;
   }

   public void setList(List<T> list) {
     this.list = list;
   }

   public Integer getCount() {
     return this.count;
   }

   public void setCount(Integer count) {
     this.count = count;
   }

   public String getMsg() {
     return this.msg;
   }

   public void setMsg(String msg) {
     this.msg = msg;
   }

   public Boolean getRel() {
     return this.rel;
   }

   public void setRel(Boolean rel) {
     this.rel = rel;
   }
 }

