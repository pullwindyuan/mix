 package org.hlpay.base.vo;

 import io.swagger.annotations.ApiModelProperty;

 public class RestDateVo implements Serializable {
   @ApiModelProperty(value = "主键ID", required = true)
   private Long id;
   @ApiModelProperty(value = "起始日期", required = true)
   private LocalDate startDate;
   @ApiModelProperty(value = "结束日期", required = true)
   private LocalDate endDate;
   @ApiModelProperty(value = "名称", required = true)
   private String name;

   public RestDateVo setId(Long id) { this.id = id; return this; } @ApiModelProperty(value = "审核状态：0：审核总 1：审核通过 2：审核不通过", required = true) private Integer state; @ApiModelProperty(value = "创建时间", required = true) private LocalDateTime createTime; @ApiModelProperty(value = "更新时间", required = true) private LocalDateTime updateTime; @ApiModelProperty(value = "创建者", required = true) private String creator; @ApiModelProperty(value = "审核者", required = true) private String auditor; public RestDateVo setStartDate(LocalDate startDate) { this.startDate = startDate; return this; } public RestDateVo setEndDate(LocalDate endDate) { this.endDate = endDate; return this; } public RestDateVo setName(String name) { this.name = name; return this; } public RestDateVo setState(Integer state) { this.state = state; return this; } public RestDateVo setCreateTime(LocalDateTime createTime) { this.createTime = createTime; return this; } public RestDateVo setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; return this; } public RestDateVo setCreator(String creator) { this.creator = creator; return this; } public RestDateVo setAuditor(String auditor) { this.auditor = auditor; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof RestDateVo)) return false;  RestDateVo other = (RestDateVo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$startDate = getStartDate(), other$startDate = other.getStartDate(); if ((this$startDate == null) ? (other$startDate != null) : !this$startDate.equals(other$startDate)) return false;  Object this$endDate = getEndDate(), other$endDate = other.getEndDate(); if ((this$endDate == null) ? (other$endDate != null) : !this$endDate.equals(other$endDate)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$state = getState(), other$state = other.getState(); if ((this$state == null) ? (other$state != null) : !this$state.equals(other$state)) return false;  Object this$createTime = getCreateTime(), other$createTime = other.getCreateTime(); if ((this$createTime == null) ? (other$createTime != null) : !this$createTime.equals(other$createTime)) return false;  Object this$updateTime = getUpdateTime(), other$updateTime = other.getUpdateTime(); if ((this$updateTime == null) ? (other$updateTime != null) : !this$updateTime.equals(other$updateTime)) return false;  Object this$creator = getCreator(), other$creator = other.getCreator(); if ((this$creator == null) ? (other$creator != null) : !this$creator.equals(other$creator)) return false;  Object this$auditor = getAuditor(), other$auditor = other.getAuditor(); return !((this$auditor == null) ? (other$auditor != null) : !this$auditor.equals(other$auditor)); } protected boolean canEqual(Object other) { return other instanceof RestDateVo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $startDate = getStartDate(); result = result * 59 + (($startDate == null) ? 43 : $startDate.hashCode()); Object $endDate = getEndDate(); result = result * 59 + (($endDate == null) ? 43 : $endDate.hashCode()); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $state = getState(); result = result * 59 + (($state == null) ? 43 : $state.hashCode()); Object $createTime = getCreateTime(); result = result * 59 + (($createTime == null) ? 43 : $createTime.hashCode()); Object $updateTime = getUpdateTime(); result = result * 59 + (($updateTime == null) ? 43 : $updateTime.hashCode()); Object $creator = getCreator(); result = result * 59 + (($creator == null) ? 43 : $creator.hashCode()); Object $auditor = getAuditor(); return result * 59 + (($auditor == null) ? 43 : $auditor.hashCode()); } public String toString() { return "RestDateVo(id=" + getId() + ", startDate=" + getStartDate() + ", endDate=" + getEndDate() + ", name=" + getName() + ", state=" + getState() + ", createTime=" + getCreateTime() + ", updateTime=" + getUpdateTime() + ", creator=" + getCreator() + ", auditor=" + getAuditor() + ")"; }


   public Long getId() {
     return this.id;
   }

   public LocalDate getStartDate() {
     return this.startDate;
   }

   public LocalDate getEndDate() {
     return this.endDate;
   }

   public String getName() {
     return this.name;
   }

   public Integer getState() {
     return this.state;
   }
   public LocalDateTime getCreateTime() {
     return this.createTime;
   }
   public String getCreator() {
     return this.creator;
   }



   public String getAuditor() {
     return this.auditor;
   }
   public LocalDateTime getUpdateTime() {
     if (this.updateTime == null) {
       this.updateTime = this.createTime;
     }
     return this.updateTime;
   }
 }





