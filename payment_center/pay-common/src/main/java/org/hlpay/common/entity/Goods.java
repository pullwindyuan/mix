 package org.hlpay.common.entity;

 public class Goods
 {
   private Integer id;
   private String goodsName;
   private String goodsCode;
   private String addtime;
   private String updatetime;
   private String series;
   private String nowtype;

   public String getNowtype() {
     return this.nowtype;
   }

   public void setNowtype(String nowtype) {
     this.nowtype = nowtype;
   }

   public String getSeries() {
     return this.series;
   }

   public void setSeries(String series) {
     this.series = series;
   }

   public String getAddtime() {
     return this.addtime;
   }

   public void setAddtime(String addtime) {
     this.addtime = addtime;
   }

   public String getUpdatetime() {
     return this.updatetime;
   }

   public void setUpdatetime(String updatetime) {
     this.updatetime = updatetime;
   }

   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public String getGoodsName() {
     return this.goodsName;
   }

   public void setGoodsName(String goodsName) {
     this.goodsName = goodsName;
   }

   public String getGoodsCode() {
     return this.goodsCode;
   }

   public void setGoodsCode(String goodsCode) {
     this.goodsCode = goodsCode;
   }

   public String toString() {
     return "{id=" + this.id + ", goodsName=" + this.goodsName + ", goodsCode=" + this.goodsCode + ", addtime=" + this.addtime + ", updatetime=" + this.updatetime + "}";
   }
 }
