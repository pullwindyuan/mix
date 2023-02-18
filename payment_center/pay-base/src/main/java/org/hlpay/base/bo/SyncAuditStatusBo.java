package org.hlpay.base.bo;

import com.alibaba.fastjson.JSONObject;
import java.util.Date;

public class SyncAuditStatusBo {
  private String contentId;
  private Integer auditStatus;
  private Date auditTime;
  private String syncAuditDataType;
  private JSONObject updateContentObject;
  private Object innerContent;
  private String remark;
  private String bizId;

  public void setContentId(String contentId) { this.contentId = contentId; } public void setAuditStatus(Integer auditStatus) { this.auditStatus = auditStatus; } public void setAuditTime(Date auditTime) { this.auditTime = auditTime; } public void setSyncAuditDataType(String syncAuditDataType) { this.syncAuditDataType = syncAuditDataType; } public void setUpdateContentObject(JSONObject updateContentObject) { this.updateContentObject = updateContentObject; } public void setInnerContent(Object innerContent) { this.innerContent = innerContent; } public void setRemark(String remark) { this.remark = remark; } public void setBizId(String bizId) { this.bizId = bizId; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SyncAuditStatusBo)) return false;  SyncAuditStatusBo other = (SyncAuditStatusBo)o; if (!other.canEqual(this)) return false;  Object this$contentId = getContentId(), other$contentId = other.getContentId(); if ((this$contentId == null) ? (other$contentId != null) : !this$contentId.equals(other$contentId)) return false;  Object this$auditStatus = getAuditStatus(), other$auditStatus = other.getAuditStatus(); if ((this$auditStatus == null) ? (other$auditStatus != null) : !this$auditStatus.equals(other$auditStatus)) return false;  Object this$auditTime = getAuditTime(), other$auditTime = other.getAuditTime(); if ((this$auditTime == null) ? (other$auditTime != null) : !this$auditTime.equals(other$auditTime)) return false;  Object this$syncAuditDataType = getSyncAuditDataType(), other$syncAuditDataType = other.getSyncAuditDataType(); if ((this$syncAuditDataType == null) ? (other$syncAuditDataType != null) : !this$syncAuditDataType.equals(other$syncAuditDataType)) return false;  Object this$updateContentObject = getUpdateContentObject(), other$updateContentObject = other.getUpdateContentObject(); if ((this$updateContentObject == null) ? (other$updateContentObject != null) : !this$updateContentObject.equals(other$updateContentObject)) return false;  Object this$innerContent = getInnerContent(), other$innerContent = other.getInnerContent(); if ((this$innerContent == null) ? (other$innerContent != null) : !this$innerContent.equals(other$innerContent)) return false;  Object this$remark = getRemark(), other$remark = other.getRemark(); if ((this$remark == null) ? (other$remark != null) : !this$remark.equals(other$remark)) return false;  Object this$bizId = getBizId(), other$bizId = other.getBizId(); return !((this$bizId == null) ? (other$bizId != null) : !this$bizId.equals(other$bizId)); } protected boolean canEqual(Object other) { return other instanceof SyncAuditStatusBo; } public int hashCode() { int PRIME = 59; result = 1; Object $contentId = getContentId(); result = result * 59 + (($contentId == null) ? 43 : $contentId.hashCode()); Object $auditStatus = getAuditStatus(); result = result * 59 + (($auditStatus == null) ? 43 : $auditStatus.hashCode()); Object $auditTime = getAuditTime(); result = result * 59 + (($auditTime == null) ? 43 : $auditTime.hashCode()); Object $syncAuditDataType = getSyncAuditDataType(); result = result * 59 + (($syncAuditDataType == null) ? 43 : $syncAuditDataType.hashCode()); Object $updateContentObject = getUpdateContentObject(); result = result * 59 + (($updateContentObject == null) ? 43 : $updateContentObject.hashCode()); Object $innerContent = getInnerContent(); result = result * 59 + (($innerContent == null) ? 43 : $innerContent.hashCode()); Object $remark = getRemark(); result = result * 59 + (($remark == null) ? 43 : $remark.hashCode()); Object $bizId = getBizId(); return result * 59 + (($bizId == null) ? 43 : $bizId.hashCode()); } public String toString() { return "SyncAuditStatusBo(contentId=" + getContentId() + ", auditStatus=" + getAuditStatus() + ", auditTime=" + getAuditTime() + ", syncAuditDataType=" + getSyncAuditDataType() + ", updateContentObject=" + getUpdateContentObject() + ", innerContent=" + getInnerContent() + ", remark=" + getRemark() + ", bizId=" + getBizId() + ")"; }






























  public SyncAuditStatusBo()
  {
    this.innerContent = null; } public String getContentId() { return this.contentId; } public SyncAuditStatusBo(String contentId, Integer auditStatus, Date auditTime, String syncAuditDataType, JSONObject updateContentObject, Object innerContent, String remark, String bizId) { this.innerContent = null; this.contentId = contentId; this.auditStatus = auditStatus; this.auditTime = auditTime; this.syncAuditDataType = syncAuditDataType; this.updateContentObject = updateContentObject; this.innerContent = innerContent; this.remark = remark; this.bizId = bizId; } public Integer getAuditStatus() { return this.auditStatus; } public Object getInnerContent() { return this.innerContent; } public Date getAuditTime() { return this.auditTime; } public String getSyncAuditDataType() { return this.syncAuditDataType; }
  public JSONObject getUpdateContentObject() {
    return this.updateContentObject;
  }
  public String getRemark() {
    return this.remark;
  }


  public String getBizId() {
    return this.bizId;
  }
}
