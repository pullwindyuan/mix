 package org.hlpay.base.bo;

 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;
 import java.util.List;

 public class WorkFlowAuditAddBo implements Serializable {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "发布者ID", required = true)
   private String author;
   @ApiModelProperty(value = "发布者名称", required = true)
   private String authorName;
   @ApiModelProperty(value = "发布的资源ID", required = true)
   private String bizId;
   @ApiModelProperty(value = "发布的内容", required = true)
   private JSONObject content;

   public WorkFlowAuditAddBo setAuthor(String author) {
     this.author = author; return this; } @ApiModelProperty(value = "发布的内容列表 isMulti = 1的时候使用", required = true) private List<JSONObject> innerContent; @ApiModelProperty(value = "是否是批量", required = true) private Integer isMulti; @ApiModelProperty(value = "发布者所属组织ID", required = true) private Long orgId; @ApiModelProperty(value = "项目名称fergn调用", required = true) private String projId; @ApiModelProperty(value = "回调接口", required = true) private String servletPath; @ApiModelProperty(value = "同步操作类型,可用值:ADD,MODIFY,DELETE", required = true) private String syncAuditDataType; public WorkFlowAuditAddBo setAuthorName(String authorName) { this.authorName = authorName; return this; } public WorkFlowAuditAddBo setBizId(String bizId) { this.bizId = bizId; return this; } public WorkFlowAuditAddBo setContent(JSONObject content) { this.content = content; return this; } public WorkFlowAuditAddBo setInnerContent(List<JSONObject> innerContent) { this.innerContent = innerContent; return this; } public WorkFlowAuditAddBo setIsMulti(Integer isMulti) { this.isMulti = isMulti; return this; } public WorkFlowAuditAddBo setOrgId(Long orgId) { this.orgId = orgId; return this; } public WorkFlowAuditAddBo setProjId(String projId) { this.projId = projId; return this; } public WorkFlowAuditAddBo setServletPath(String servletPath) { this.servletPath = servletPath; return this; } public WorkFlowAuditAddBo setSyncAuditDataType(String syncAuditDataType) { this.syncAuditDataType = syncAuditDataType; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof WorkFlowAuditAddBo)) return false;  WorkFlowAuditAddBo other = (WorkFlowAuditAddBo)o; if (!other.canEqual(this)) return false;  Object this$author = getAuthor(), other$author = other.getAuthor(); if ((this$author == null) ? (other$author != null) : !this$author.equals(other$author)) return false;  Object this$authorName = getAuthorName(), other$authorName = other.getAuthorName(); if ((this$authorName == null) ? (other$authorName != null) : !this$authorName.equals(other$authorName)) return false;  Object this$bizId = getBizId(), other$bizId = other.getBizId(); if ((this$bizId == null) ? (other$bizId != null) : !this$bizId.equals(other$bizId)) return false;  Object this$content = getContent(), other$content = other.getContent(); if ((this$content == null) ? (other$content != null) : !this$content.equals(other$content)) return false;  Object<JSONObject> this$innerContent = (Object<JSONObject>)getInnerContent(), other$innerContent = (Object<JSONObject>)other.getInnerContent(); if ((this$innerContent == null) ? (other$innerContent != null) : !this$innerContent.equals(other$innerContent)) return false;  Object this$isMulti = getIsMulti(), other$isMulti = other.getIsMulti(); if ((this$isMulti == null) ? (other$isMulti != null) : !this$isMulti.equals(other$isMulti)) return false;  Object this$orgId = getOrgId(), other$orgId = other.getOrgId(); if ((this$orgId == null) ? (other$orgId != null) : !this$orgId.equals(other$orgId)) return false;  Object this$projId = getProjId(), other$projId = other.getProjId(); if ((this$projId == null) ? (other$projId != null) : !this$projId.equals(other$projId)) return false;  Object this$servletPath = getServletPath(), other$servletPath = other.getServletPath(); if ((this$servletPath == null) ? (other$servletPath != null) : !this$servletPath.equals(other$servletPath)) return false;  Object this$syncAuditDataType = getSyncAuditDataType(), other$syncAuditDataType = other.getSyncAuditDataType(); return !((this$syncAuditDataType == null) ? (other$syncAuditDataType != null) : !this$syncAuditDataType.equals(other$syncAuditDataType)); } protected boolean canEqual(Object other) { return other instanceof WorkFlowAuditAddBo; } public int hashCode() { int PRIME = 59; result = 1; Object $author = getAuthor(); result = result * 59 + (($author == null) ? 43 : $author.hashCode()); Object $authorName = getAuthorName(); result = result * 59 + (($authorName == null) ? 43 : $authorName.hashCode()); Object $bizId = getBizId(); result = result * 59 + (($bizId == null) ? 43 : $bizId.hashCode()); Object $content = getContent(); result = result * 59 + (($content == null) ? 43 : $content.hashCode()); Object<JSONObject> $innerContent = (Object<JSONObject>)getInnerContent(); result = result * 59 + (($innerContent == null) ? 43 : $innerContent.hashCode()); Object $isMulti = getIsMulti(); result = result * 59 + (($isMulti == null) ? 43 : $isMulti.hashCode()); Object $orgId = getOrgId(); result = result * 59 + (($orgId == null) ? 43 : $orgId.hashCode()); Object $projId = getProjId(); result = result * 59 + (($projId == null) ? 43 : $projId.hashCode()); Object $servletPath = getServletPath(); result = result * 59 + (($servletPath == null) ? 43 : $servletPath.hashCode()); Object $syncAuditDataType = getSyncAuditDataType(); return result * 59 + (($syncAuditDataType == null) ? 43 : $syncAuditDataType.hashCode()); } public String toString() { return "WorkFlowAuditAddBo(author=" + getAuthor() + ", authorName=" + getAuthorName() + ", bizId=" + getBizId() + ", content=" + getContent() + ", innerContent=" + getInnerContent() + ", isMulti=" + getIsMulti() + ", orgId=" + getOrgId() + ", projId=" + getProjId() + ", servletPath=" + getServletPath() + ", syncAuditDataType=" + getSyncAuditDataType() + ")"; }







   public String getAuthor() {
     return this.author;
   }



   public String getAuthorName() {
     return this.authorName;
   }



   public String getBizId() {
     return this.bizId;
   }



   public JSONObject getContent() {
     return this.content;
   }



   public List<JSONObject> getInnerContent() {
     return this.innerContent;
   }



   public Integer getIsMulti() {
     return this.isMulti;
   }



   public Long getOrgId() {
     return this.orgId;
   }



   public String getProjId() {
     return this.projId;
   }



   public String getServletPath() {
     return this.servletPath;
   }



   public String getSyncAuditDataType() {
     return this.syncAuditDataType;
   }
 }
