 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;
 import java.time.LocalDate;




















 public class RestDateBo
   extends AccessBo
   implements Serializable
 {
   @ApiModelProperty(value = "主键ID", required = false)
   private Long id;
   @ApiModelProperty(value = "起始日期", required = true)
   private LocalDate startDate;
   @ApiModelProperty(value = "结束日期", required = true)
   private LocalDate endDate;
   @ApiModelProperty(value = "显示名称", required = false)
   private String name;
   @ApiModelProperty(value = "创建者", required = false)
   private String creator;
   private static final long serialVersionUID = 1L;

   public Long getId() {
     return this.id;
   }

   public void setId(Long id) {
     this.id = id;
   }

   public LocalDate getStartDate() {
     return this.startDate;
   }

   public void setStartDate(LocalDate startDate) {
     this.startDate = startDate;
   }

   public LocalDate getEndDate() {
     return this.endDate;
   }

   public void setEndDate(LocalDate endDate) {
     this.endDate = endDate;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getCreator() {
     return this.creator;
   }

   public void setCreator(String creator) {
     this.creator = creator;
   }


   public boolean equals(Object that) {
     if (this == that) {
       return true;
     }
     if (that == null) {
       return false;
     }
     if (getClass() != that.getClass()) {
       return false;
     }
     RestDateBo other = (RestDateBo)that;
     return (((getId() == null) ? (other.getId() == null) : getId().equals(other.getId())) && (
       (getStartDate() == null) ? (other.getStartDate() == null) : getStartDate().equals(other.getStartDate())) && (
       (getEndDate() == null) ? (other.getEndDate() == null) : getEndDate().equals(other.getEndDate())) && (
       (getName() == null) ? (other.getName() == null) : getName().equals(other.getName())) && (
       (getCreator() == null) ? (other.getCreator() == null) : getCreator().equals(other.getCreator())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getId() == null) ? 0 : getId().hashCode());
     result = 31 * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
     result = 31 * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
     result = 31 * result + ((getName() == null) ? 0 : getName().hashCode());
     result = 31 * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
     return result;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", id=").append(this.id);
     sb.append(", startDate=").append(this.startDate);
     sb.append(", endDate=").append(this.endDate);
     sb.append(", name=").append(this.name);
     sb.append(", creator=").append(this.creator);
     sb.append(", serialVersionUID=").append(1L);
     sb.append("]");
     return sb.toString();
   }
 }
