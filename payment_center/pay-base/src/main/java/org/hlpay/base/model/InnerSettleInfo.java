 package org.hlpay.base.model;

 import java.util.List;


 public class InnerSettleInfo
 {
   public void setSaCardList(List<SaCard> saCardList) {
     this.saCardList = saCardList; } public void setSaDealRecord(SaDealRecord saDealRecord) { this.saDealRecord = saDealRecord; } public void setSaScoreFlowRealList(List<SaScoreFlow> saScoreFlowRealList) { this.saScoreFlowRealList = saScoreFlowRealList; } public void setSaScoreFlowList(List<SaScoreFlow> saScoreFlowList) { this.saScoreFlowList = saScoreFlowList; } public void setStatus(Byte status) { this.status = status; } public void setResult(Byte result) { this.result = result; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof InnerSettleInfo)) return false;  InnerSettleInfo other = (InnerSettleInfo)o; if (!other.canEqual(this)) return false;  Object<SaCard> this$saCardList = (Object<SaCard>)getSaCardList(), other$saCardList = (Object<SaCard>)other.getSaCardList(); if ((this$saCardList == null) ? (other$saCardList != null) : !this$saCardList.equals(other$saCardList)) return false;  Object this$saDealRecord = getSaDealRecord(), other$saDealRecord = other.getSaDealRecord(); if ((this$saDealRecord == null) ? (other$saDealRecord != null) : !this$saDealRecord.equals(other$saDealRecord)) return false;  Object<SaScoreFlow> this$saScoreFlowRealList = (Object<SaScoreFlow>)getSaScoreFlowRealList(), other$saScoreFlowRealList = (Object<SaScoreFlow>)other.getSaScoreFlowRealList(); if ((this$saScoreFlowRealList == null) ? (other$saScoreFlowRealList != null) : !this$saScoreFlowRealList.equals(other$saScoreFlowRealList)) return false;  Object<SaScoreFlow> this$saScoreFlowList = (Object<SaScoreFlow>)getSaScoreFlowList(), other$saScoreFlowList = (Object<SaScoreFlow>)other.getSaScoreFlowList(); if ((this$saScoreFlowList == null) ? (other$saScoreFlowList != null) : !this$saScoreFlowList.equals(other$saScoreFlowList)) return false;  Object this$status = getStatus(), other$status = other.getStatus(); if ((this$status == null) ? (other$status != null) : !this$status.equals(other$status)) return false;  Object this$result = getResult(), other$result = other.getResult(); return !((this$result == null) ? (other$result != null) : !this$result.equals(other$result)); } protected boolean canEqual(Object other) { return other instanceof InnerSettleInfo; } public int hashCode() { int PRIME = 59; result = 1; Object<SaCard> $saCardList = (Object<SaCard>)getSaCardList(); result = result * 59 + (($saCardList == null) ? 43 : $saCardList.hashCode()); Object $saDealRecord = getSaDealRecord(); result = result * 59 + (($saDealRecord == null) ? 43 : $saDealRecord.hashCode()); Object<SaScoreFlow> $saScoreFlowRealList = (Object<SaScoreFlow>)getSaScoreFlowRealList(); result = result * 59 + (($saScoreFlowRealList == null) ? 43 : $saScoreFlowRealList.hashCode()); Object<SaScoreFlow> $saScoreFlowList = (Object<SaScoreFlow>)getSaScoreFlowList(); result = result * 59 + (($saScoreFlowList == null) ? 43 : $saScoreFlowList.hashCode()); Object $status = getStatus(); result = result * 59 + (($status == null) ? 43 : $status.hashCode()); Object $result = getResult(); return result * 59 + (($result == null) ? 43 : $result.hashCode()); } public String toString() { return "InnerSettleInfo(saCardList=" + getSaCardList() + ", saDealRecord=" + getSaDealRecord() + ", saScoreFlowRealList=" + getSaScoreFlowRealList() + ", saScoreFlowList=" + getSaScoreFlowList() + ", status=" + getStatus() + ", result=" + getResult() + ")"; }
    public List<SaCard> getSaCardList() {
     return this.saCardList;
   }

   public SaDealRecord getSaDealRecord() {
     return this.saDealRecord;
   }

   public List<SaScoreFlow> getSaScoreFlowRealList() {
     return this.saScoreFlowRealList;
   } public List<SaScoreFlow> getSaScoreFlowList() {
     return this.saScoreFlowList;
   }

   public Byte getStatus() {
     return this.status;
   }




   public Byte getResult() {
     return this.result;
   }
   private static final Object $LOCK = new Object[0]; private List<SaCard> saCardList; public static InnerSettleInfo create(SaDealRecord saDealRecord, List<SaCard> saCardList, List<SaScoreFlow> scoreFlowList, List<SaScoreFlow> scoreFlowRealList) { synchronized ($LOCK) {

       InnerSettleInfo innerSettleInfo = new InnerSettleInfo();
       saDealRecord.setStatus(Character.valueOf('1'));
       saDealRecord.setDealStatus(Character.valueOf('1'));
       innerSettleInfo.setSaDealRecord(saDealRecord);
       innerSettleInfo.setSaCardList(saCardList);
       innerSettleInfo.setSaScoreFlowList(scoreFlowList);
       innerSettleInfo.setSaScoreFlowRealList(scoreFlowRealList);
       innerSettleInfo.setResult(Byte.valueOf((byte)2));
       innerSettleInfo.setStatus(Byte.valueOf((byte)2));

       return innerSettleInfo;
     }  }


   private SaDealRecord saDealRecord;
   private List<SaScoreFlow> saScoreFlowRealList;
   private List<SaScoreFlow> saScoreFlowList;
   private Byte status;
   private Byte result;
 }

