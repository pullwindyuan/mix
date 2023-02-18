package org.hlpay.base.model;

 public class SaSettleConfig
 {
   private String configId;
   private String configNumber;
   private Character liquidateMode;
   private Integer liquidateDate;
   private String liquidateTime;
   private Character settleMode;
   private Integer settleDate;
   private Character weekendSettle;
   private Character holidaySettle;
   private String createTime;
   private String lastUpdateTime;
   private String userId;
   private Character isDelete;
   private Character flag;
   private Character status;

   public SaSettleConfig() {}

   public SaSettleConfig(String configId, String configNumber, Character liquidateMode, Integer liquidateDate, String liquidateTime, Character settleMode, Integer settleDate, Character weekendSettle, Character holidaySettle, String createTime, String lastUpdateTime, String userId, Character isDelete, Character flag, Character status) {
     this.configId = configId;
     this.configNumber = configNumber;
     this.liquidateMode = liquidateMode;
     this.liquidateDate = liquidateDate;
     this.liquidateTime = liquidateTime;
     this.settleMode = settleMode;
     this.settleDate = settleDate;
     this.weekendSettle = weekendSettle;
     this.holidaySettle = holidaySettle;
     this.createTime = createTime;
     this.lastUpdateTime = lastUpdateTime;
     this.userId = userId;
     this.isDelete = isDelete;
     this.flag = flag;
     this.status = status;
   }

   public String getConfigId() {
     return this.configId;
   }

   public void setConfigId(String configId) {
     this.configId = configId;
   }

   public String getConfigNumber() {
     return this.configNumber;
   }

   public void setConfigNumber(String configNumber) {
     this.configNumber = configNumber;
   }

   public Character getLiquidateMode() {
     return this.liquidateMode;
   }

   public void setLiquidateMode(Character liquidateMode) {
     this.liquidateMode = liquidateMode;
   }

   public Integer getLiquidateDate() {
     return this.liquidateDate;
   }

   public void setLiquidateDate(Integer liquidateDate) {
     this.liquidateDate = liquidateDate;
   }

   public String getLiquidateTime() {
     return this.liquidateTime;
   }

   public void setLiquidateTime(String liquidateTime) {
     this.liquidateTime = liquidateTime;
   }

   public Character getSettleMode() {
     return this.settleMode;
   }

   public void setSettleMode(Character settleMode) {
     this.settleMode = settleMode;
   }

   public Integer getSettleDate() {
     return this.settleDate;
   }

   public void setSettleDate(Integer settleDate) {
     this.settleDate = settleDate;
   }

   public Character getWeekendSettle() {
     return this.weekendSettle;
   }

   public void setWeekendSettle(Character weekendSettle) {
     this.weekendSettle = weekendSettle;
   }

   public Character getHolidaySettle() {
     return this.holidaySettle;
   }

   public void setHolidaySettle(Character holidaySettle) {
     this.holidaySettle = holidaySettle;
   }

   public String getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(String createTime) {
     this.createTime = createTime;
   }

   public String getLastUpdateTime() {
     return this.lastUpdateTime;
   }

   public void setLastUpdateTime(String lastUpdateTime) {
     this.lastUpdateTime = lastUpdateTime;
   }

   public String getuserId() {
     return this.userId;
   }

   public void setuserId(String userId) {
     this.userId = userId;
   }

   public Character getIsDelete() {
     return this.isDelete;
   }

   public void setIsDelete(Character isDelete) {
     this.isDelete = isDelete;
   }

   public Character getFlag() {
     return this.flag;
   }

   public void setFlag(Character flag) {
     this.flag = flag;
   }

   public Character getStatus() {
     return this.status;
   }

   public void setStatus(Character status) {
     this.status = status;
   }


   public String toString() {
     return "SaSettleConfig [configId=" + this.configId + ", configNumber=" + this.configNumber + ", liquidateMode=" + this.liquidateMode + ", liquidateDate=" + this.liquidateDate + ", liquidateTime=" + this.liquidateTime + ", settleMode=" + this.settleMode + ", settleDate=" + this.settleDate + ", weekendSettle=" + this.weekendSettle + ", holidaySettle=" + this.holidaySettle + ", createTime=" + this.createTime + ", lastUpdateTime=" + this.lastUpdateTime + ", userId=" + this.userId + ", isDelete=" + this.isDelete + ", flag=" + this.flag + ", status=" + this.status + "]";
   }
 }





