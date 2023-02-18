 package org.hlpay.base.model;

 import java.time.LocalDate;
 import java.time.LocalDateTime;
 import java.util.ArrayList;
 import java.util.List;











 public class RestDateExample
 {
   protected String orderByClause;
   protected boolean distinct;
   protected List<Criteria> oredCriteria = new ArrayList<>();
   private Integer limit;

   public void setOrderByClause(String orderByClause) {
     this.orderByClause = orderByClause;
   }
   private Long offset; private Boolean forUpdate;
   public String getOrderByClause() {
     return this.orderByClause;
   }

   public void setDistinct(boolean distinct) {
     this.distinct = distinct;
   }

   public boolean isDistinct() {
     return this.distinct;
   }

   public List<Criteria> getOredCriteria() {
     return this.oredCriteria;
   }

   public void or(Criteria criteria) {
     this.oredCriteria.add(criteria);
   }

   public Criteria or() {
     Criteria criteria = createCriteriaInternal();
     this.oredCriteria.add(criteria);
     return criteria;
   }

   public Criteria createCriteria() {
     Criteria criteria = createCriteriaInternal();
     if (this.oredCriteria.size() == 0) {
       this.oredCriteria.add(criteria);
     }
     return criteria;
   }

   protected Criteria createCriteriaInternal() {
     Criteria criteria = new Criteria();
     return criteria;
   }

   public void clear() {
     this.oredCriteria.clear();
     this.orderByClause = null;
     this.distinct = false;
   }

   public void setLimit(Integer limit) {
     this.limit = limit;
   }

   public Integer getLimit() {
     return this.limit;
   }

   public void setOffset(Long offset) {
     this.offset = offset;
   }

   public Long getOffset() {
     return this.offset;
   }

   public void setForUpdate(Boolean forUpdate) {
     this.forUpdate = forUpdate;
   }

   public Boolean getForUpdate() {
     return this.forUpdate;
   }




   protected static abstract class GeneratedCriteria
   {
     protected List<Criterion> criteria = new ArrayList<>();


     public boolean isValid() {
       return (this.criteria.size() > 0);
     }

     public List<Criterion> getAllCriteria() {
       return this.criteria;
     }

     public List<Criterion> getCriteria() {
       return this.criteria;
     }

     protected void addCriterion(String condition) {
       if (condition == null) {
         throw new RuntimeException("Value for condition cannot be null");
       }
       this.criteria.add(new Criterion(condition));
     }

     protected void addCriterion(String condition, Object value, String property) {
       if (value == null) {
         throw new RuntimeException("Value for " + property + " cannot be null");
       }
       this.criteria.add(new Criterion(condition, value));
     }

     protected void addCriterion(String condition, Object value1, Object value2, String property) {
       if (value1 == null || value2 == null) {
         throw new RuntimeException("Between values for " + property + " cannot be null");
       }
       this.criteria.add(new Criterion(condition, value1, value2));
     }

     public Criteria andIdIsNull() {
       addCriterion("id is null");
       return (Criteria)this;
     }

     public Criteria andIdIsNotNull() {
       addCriterion("id is not null");
       return (Criteria)this;
     }

     public Criteria andIdEqualTo(Long value) {
       addCriterion("id =", value, "id");
       return (Criteria)this;
     }

     public Criteria andIdNotEqualTo(Long value) {
       addCriterion("id <>", value, "id");
       return (Criteria)this;
     }

     public Criteria andIdGreaterThan(Long value) {
       addCriterion("id >", value, "id");
       return (Criteria)this;
     }

     public Criteria andIdGreaterThanOrEqualTo(Long value) {
       addCriterion("id >=", value, "id");
       return (Criteria)this;
     }

     public Criteria andIdLessThan(Long value) {
       addCriterion("id <", value, "id");
       return (Criteria)this;
     }

     public Criteria andIdLessThanOrEqualTo(Long value) {
       addCriterion("id <=", value, "id");
       return (Criteria)this;
     }

     public Criteria andIdIn(List<Long> values) {
       addCriterion("id in", values, "id");
       return (Criteria)this;
     }

     public Criteria andIdNotIn(List<Long> values) {
       addCriterion("id not in", values, "id");
       return (Criteria)this;
     }

     public Criteria andIdBetween(Long value1, Long value2) {
       addCriterion("id between", value1, value2, "id");
       return (Criteria)this;
     }

     public Criteria andIdNotBetween(Long value1, Long value2) {
       addCriterion("id not between", value1, value2, "id");
       return (Criteria)this;
     }

     public Criteria andStartDateIsNull() {
       addCriterion("StartDate is null");
       return (Criteria)this;
     }

     public Criteria andStartDateIsNotNull() {
       addCriterion("StartDate is not null");
       return (Criteria)this;
     }

     public Criteria andStartDateEqualTo(LocalDate value) {
       addCriterion("StartDate =", value, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateNotEqualTo(LocalDate value) {
       addCriterion("StartDate <>", value, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateGreaterThan(LocalDate value) {
       addCriterion("StartDate >", value, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateGreaterThanOrEqualTo(LocalDate value) {
       addCriterion("StartDate >=", value, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateLessThan(LocalDate value) {
       addCriterion("StartDate <", value, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateLessThanOrEqualTo(LocalDate value) {
       addCriterion("StartDate <=", value, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateIn(List<LocalDate> values) {
       addCriterion("StartDate in", values, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateNotIn(List<LocalDate> values) {
       addCriterion("StartDate not in", values, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateBetween(LocalDate value1, LocalDate value2) {
       addCriterion("StartDate between", value1, value2, "startDate");
       return (Criteria)this;
     }

     public Criteria andStartDateNotBetween(LocalDate value1, LocalDate value2) {
       addCriterion("StartDate not between", value1, value2, "startDate");
       return (Criteria)this;
     }

     public Criteria andEndDateIsNull() {
       addCriterion("EndDate is null");
       return (Criteria)this;
     }

     public Criteria andEndDateIsNotNull() {
       addCriterion("EndDate is not null");
       return (Criteria)this;
     }

     public Criteria andEndDateEqualTo(LocalDate value) {
       addCriterion("EndDate =", value, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateNotEqualTo(LocalDate value) {
       addCriterion("EndDate <>", value, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateGreaterThan(LocalDate value) {
       addCriterion("EndDate >", value, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateGreaterThanOrEqualTo(LocalDate value) {
       addCriterion("EndDate >=", value, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateLessThan(LocalDate value) {
       addCriterion("EndDate <", value, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateLessThanOrEqualTo(LocalDate value) {
       addCriterion("EndDate <=", value, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateIn(List<LocalDate> values) {
       addCriterion("EndDate in", values, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateNotIn(List<LocalDate> values) {
       addCriterion("EndDate not in", values, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateBetween(LocalDate value1, LocalDate value2) {
       addCriterion("EndDate between", value1, value2, "endDate");
       return (Criteria)this;
     }

     public Criteria andEndDateNotBetween(LocalDate value1, LocalDate value2) {
       addCriterion("EndDate not between", value1, value2, "endDate");
       return (Criteria)this;
     }

     public Criteria andNameIsNull() {
       addCriterion("`Name` is null");
       return (Criteria)this;
     }

     public Criteria andNameIsNotNull() {
       addCriterion("`Name` is not null");
       return (Criteria)this;
     }

     public Criteria andNameEqualTo(String value) {
       addCriterion("`Name` =", value, "name");
       return (Criteria)this;
     }

     public Criteria andNameNotEqualTo(String value) {
       addCriterion("`Name` <>", value, "name");
       return (Criteria)this;
     }

     public Criteria andNameGreaterThan(String value) {
       addCriterion("`Name` >", value, "name");
       return (Criteria)this;
     }

     public Criteria andNameGreaterThanOrEqualTo(String value) {
       addCriterion("`Name` >=", value, "name");
       return (Criteria)this;
     }

     public Criteria andNameLessThan(String value) {
       addCriterion("`Name` <", value, "name");
       return (Criteria)this;
     }

     public Criteria andNameLessThanOrEqualTo(String value) {
       addCriterion("`Name` <=", value, "name");
       return (Criteria)this;
     }

     public Criteria andNameLike(String value) {
       addCriterion("`Name` like", value, "name");
       return (Criteria)this;
     }

     public Criteria andNameNotLike(String value) {
       addCriterion("`Name` not like", value, "name");
       return (Criteria)this;
     }

     public Criteria andNameIn(List<String> values) {
       addCriterion("`Name` in", values, "name");
       return (Criteria)this;
     }

     public Criteria andNameNotIn(List<String> values) {
       addCriterion("`Name` not in", values, "name");
       return (Criteria)this;
     }

     public Criteria andNameBetween(String value1, String value2) {
       addCriterion("`Name` between", value1, value2, "name");
       return (Criteria)this;
     }

     public Criteria andNameNotBetween(String value1, String value2) {
       addCriterion("`Name` not between", value1, value2, "name");
       return (Criteria)this;
     }

     public Criteria andStateIsNull() {
       addCriterion("`State` is null");
       return (Criteria)this;
     }

     public Criteria andStateIsNotNull() {
       addCriterion("`State` is not null");
       return (Criteria)this;
     }

     public Criteria andStateEqualTo(Integer value) {
       addCriterion("`State` =", value, "state");
       return (Criteria)this;
     }

     public Criteria andStateNotEqualTo(Integer value) {
       addCriterion("`State` <>", value, "state");
       return (Criteria)this;
     }

     public Criteria andStateGreaterThan(Integer value) {
       addCriterion("`State` >", value, "state");
       return (Criteria)this;
     }

     public Criteria andStateGreaterThanOrEqualTo(Integer value) {
       addCriterion("`State` >=", value, "state");
       return (Criteria)this;
     }

     public Criteria andStateLessThan(Integer value) {
       addCriterion("`State` <", value, "state");
       return (Criteria)this;
     }

     public Criteria andStateLessThanOrEqualTo(Integer value) {
       addCriterion("`State` <=", value, "state");
       return (Criteria)this;
     }

     public Criteria andStateIn(List<Integer> values) {
       addCriterion("`State` in", values, "state");
       return (Criteria)this;
     }

     public Criteria andStateNotIn(List<Integer> values) {
       addCriterion("`State` not in", values, "state");
       return (Criteria)this;
     }

     public Criteria andStateBetween(Integer value1, Integer value2) {
       addCriterion("`State` between", value1, value2, "state");
       return (Criteria)this;
     }

     public Criteria andStateNotBetween(Integer value1, Integer value2) {
       addCriterion("`State` not between", value1, value2, "state");
       return (Criteria)this;
     }

     public Criteria andCreateTimeIsNull() {
       addCriterion("CreateTime is null");
       return (Criteria)this;
     }

     public Criteria andCreateTimeIsNotNull() {
       addCriterion("CreateTime is not null");
       return (Criteria)this;
     }

     public Criteria andCreateTimeEqualTo(LocalDateTime value) {
       addCriterion("CreateTime =", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
       addCriterion("CreateTime <>", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
       addCriterion("CreateTime >", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
       addCriterion("CreateTime >=", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeLessThan(LocalDateTime value) {
       addCriterion("CreateTime <", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
       addCriterion("CreateTime <=", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeIn(List<LocalDateTime> values) {
       addCriterion("CreateTime in", values, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
       addCriterion("CreateTime not in", values, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
       addCriterion("CreateTime between", value1, value2, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
       addCriterion("CreateTime not between", value1, value2, "createTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeIsNull() {
       addCriterion("UpdateTime is null");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeIsNotNull() {
       addCriterion("UpdateTime is not null");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
       addCriterion("UpdateTime =", value, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
       addCriterion("UpdateTime <>", value, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
       addCriterion("UpdateTime >", value, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
       addCriterion("UpdateTime >=", value, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeLessThan(LocalDateTime value) {
       addCriterion("UpdateTime <", value, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
       addCriterion("UpdateTime <=", value, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
       addCriterion("UpdateTime in", values, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
       addCriterion("UpdateTime not in", values, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
       addCriterion("UpdateTime between", value1, value2, "updateTime");
       return (Criteria)this;
     }

     public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
       addCriterion("UpdateTime not between", value1, value2, "updateTime");
       return (Criteria)this;
     }

     public Criteria andCreatorIsNull() {
       addCriterion("Creator is null");
       return (Criteria)this;
     }

     public Criteria andCreatorIsNotNull() {
       addCriterion("Creator is not null");
       return (Criteria)this;
     }

     public Criteria andCreatorEqualTo(String value) {
       addCriterion("Creator =", value, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorNotEqualTo(String value) {
       addCriterion("Creator <>", value, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorGreaterThan(String value) {
       addCriterion("Creator >", value, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorGreaterThanOrEqualTo(String value) {
       addCriterion("Creator >=", value, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorLessThan(String value) {
       addCriterion("Creator <", value, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorLessThanOrEqualTo(String value) {
       addCriterion("Creator <=", value, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorLike(String value) {
       addCriterion("Creator like", value, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorNotLike(String value) {
       addCriterion("Creator not like", value, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorIn(List<String> values) {
       addCriterion("Creator in", values, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorNotIn(List<String> values) {
       addCriterion("Creator not in", values, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorBetween(String value1, String value2) {
       addCriterion("Creator between", value1, value2, "creator");
       return (Criteria)this;
     }

     public Criteria andCreatorNotBetween(String value1, String value2) {
       addCriterion("Creator not between", value1, value2, "creator");
       return (Criteria)this;
     }

     public Criteria andAuditorIsNull() {
       addCriterion("Auditor is null");
       return (Criteria)this;
     }

     public Criteria andAuditorIsNotNull() {
       addCriterion("Auditor is not null");
       return (Criteria)this;
     }

     public Criteria andAuditorEqualTo(String value) {
       addCriterion("Auditor =", value, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorNotEqualTo(String value) {
       addCriterion("Auditor <>", value, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorGreaterThan(String value) {
       addCriterion("Auditor >", value, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorGreaterThanOrEqualTo(String value) {
       addCriterion("Auditor >=", value, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorLessThan(String value) {
       addCriterion("Auditor <", value, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorLessThanOrEqualTo(String value) {
       addCriterion("Auditor <=", value, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorLike(String value) {
       addCriterion("Auditor like", value, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorNotLike(String value) {
       addCriterion("Auditor not like", value, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorIn(List<String> values) {
       addCriterion("Auditor in", values, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorNotIn(List<String> values) {
       addCriterion("Auditor not in", values, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorBetween(String value1, String value2) {
       addCriterion("Auditor between", value1, value2, "auditor");
       return (Criteria)this;
     }

     public Criteria andAuditorNotBetween(String value1, String value2) {
       addCriterion("Auditor not between", value1, value2, "auditor");
       return (Criteria)this;
     }
   }



   public static class Criteria
     extends GeneratedCriteria {}



   public static class Criterion
   {
     private String condition;

     private Object value;

     private Object secondValue;

     private boolean noValue;

     private boolean singleValue;

     private boolean betweenValue;

     private boolean listValue;

     private String typeHandler;


     public String getCondition() {
       return this.condition;
     }

     public Object getValue() {
       return this.value;
     }

     public Object getSecondValue() {
       return this.secondValue;
     }

     public boolean isNoValue() {
       return this.noValue;
     }

     public boolean isSingleValue() {
       return this.singleValue;
     }

     public boolean isBetweenValue() {
       return this.betweenValue;
     }

     public boolean isListValue() {
       return this.listValue;
     }

     public String getTypeHandler() {
       return this.typeHandler;
     }


     protected Criterion(String condition) {
       this.condition = condition;
       this.typeHandler = null;
       this.noValue = true;
     }


     protected Criterion(String condition, Object value, String typeHandler) {
       this.condition = condition;
       this.value = value;
       this.typeHandler = typeHandler;
       if (value instanceof List) {
         this.listValue = true;
       } else {
         this.singleValue = true;
       }
     }

     protected Criterion(String condition, Object value) {
       this(condition, value, (String)null);
     }


     protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
       this.condition = condition;
       this.value = value;
       this.secondValue = secondValue;
       this.typeHandler = typeHandler;
       this.betweenValue = true;
     }

     protected Criterion(String condition, Object value, Object secondValue) {
       this(condition, value, secondValue, null);
     }
   }
 }

