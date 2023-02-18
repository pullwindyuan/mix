package org.hlpay.base.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


























public class RestDate
  implements Serializable
{
  private Long id;
  private LocalDate startDate;
  private LocalDate endDate;
  private String name;
  private Integer state;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
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

  public Integer getState() {
    return this.state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public LocalDateTime getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public LocalDateTime getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
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
    RestDate other = (RestDate)that;
    return (((getId() == null) ? (other.getId() == null) : getId().equals(other.getId())) && (
      (getStartDate() == null) ? (other.getStartDate() == null) : getStartDate().equals(other.getStartDate())) && (
      (getEndDate() == null) ? (other.getEndDate() == null) : getEndDate().equals(other.getEndDate())) && (
      (getName() == null) ? (other.getName() == null) : getName().equals(other.getName())) && (
      (getState() == null) ? (other.getState() == null) : getState().equals(other.getState())) && (
      (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
      (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())) && (
      (getCreator() == null) ? (other.getCreator() == null) : getCreator().equals(other.getCreator())));
  }


  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = 31 * result + ((getId() == null) ? 0 : getId().hashCode());
    result = 31 * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
    result = 31 * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
    result = 31 * result + ((getName() == null) ? 0 : getName().hashCode());
    result = 31 * result + ((getState() == null) ? 0 : getState().hashCode());
    result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
    result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
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
    sb.append(", state=").append(this.state);
    sb.append(", createTime=").append(this.createTime);
    sb.append(", updateTime=").append(this.updateTime);
    sb.append(", creator=").append(this.creator);
    sb.append(", serialVersionUID=").append(1L);
    sb.append("]");
    return sb.toString();
  }
}

