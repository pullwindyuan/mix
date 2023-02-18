 package org.hlpay.base.model;

 import java.time.LocalDateTime;
 import java.util.ArrayList;
 import java.util.List;











 public class SaCardExample
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

     public Criteria andCardIdIsNull() {
       addCriterion("CARD_ID is null");
       return (Criteria)this;
     }

     public Criteria andCardIdIsNotNull() {
       addCriterion("CARD_ID is not null");
       return (Criteria)this;
     }

     public Criteria andCardIdEqualTo(String value) {
       addCriterion("CARD_ID =", value, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdNotEqualTo(String value) {
       addCriterion("CARD_ID <>", value, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdGreaterThan(String value) {
       addCriterion("CARD_ID >", value, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdGreaterThanOrEqualTo(String value) {
       addCriterion("CARD_ID >=", value, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdLessThan(String value) {
       addCriterion("CARD_ID <", value, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdLessThanOrEqualTo(String value) {
       addCriterion("CARD_ID <=", value, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdLike(String value) {
       addCriterion("CARD_ID like", value, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdNotLike(String value) {
       addCriterion("CARD_ID not like", value, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdIn(List<String> values) {
       addCriterion("CARD_ID in", values, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdNotIn(List<String> values) {
       addCriterion("CARD_ID not in", values, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdBetween(String value1, String value2) {
       addCriterion("CARD_ID between", value1, value2, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardIdNotBetween(String value1, String value2) {
       addCriterion("CARD_ID not between", value1, value2, "cardId");
       return (Criteria)this;
     }

     public Criteria andCardNumberIsNull() {
       addCriterion("CARD_NUMBER is null");
       return (Criteria)this;
     }

     public Criteria andCardNumberIsNotNull() {
       addCriterion("CARD_NUMBER is not null");
       return (Criteria)this;
     }

     public Criteria andCardNumberEqualTo(String value) {
       addCriterion("CARD_NUMBER =", value, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberNotEqualTo(String value) {
       addCriterion("CARD_NUMBER <>", value, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberGreaterThan(String value) {
       addCriterion("CARD_NUMBER >", value, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberGreaterThanOrEqualTo(String value) {
       addCriterion("CARD_NUMBER >=", value, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberLessThan(String value) {
       addCriterion("CARD_NUMBER <", value, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberLessThanOrEqualTo(String value) {
       addCriterion("CARD_NUMBER <=", value, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberLike(String value) {
       addCriterion("CARD_NUMBER like", value, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberNotLike(String value) {
       addCriterion("CARD_NUMBER not like", value, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberIn(List<String> values) {
       addCriterion("CARD_NUMBER in", values, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberNotIn(List<String> values) {
       addCriterion("CARD_NUMBER not in", values, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberBetween(String value1, String value2) {
       addCriterion("CARD_NUMBER between", value1, value2, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardNumberNotBetween(String value1, String value2) {
       addCriterion("CARD_NUMBER not between", value1, value2, "cardNumber");
       return (Criteria)this;
     }

     public Criteria andCardTypeIsNull() {
       addCriterion("CARD_TYPE is null");
       return (Criteria)this;
     }

     public Criteria andCardTypeIsNotNull() {
       addCriterion("CARD_TYPE is not null");
       return (Criteria)this;
     }

     public Criteria andCardTypeEqualTo(String value) {
       addCriterion("CARD_TYPE =", value, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeNotEqualTo(String value) {
       addCriterion("CARD_TYPE <>", value, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeGreaterThan(String value) {
       addCriterion("CARD_TYPE >", value, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeGreaterThanOrEqualTo(String value) {
       addCriterion("CARD_TYPE >=", value, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeLessThan(String value) {
       addCriterion("CARD_TYPE <", value, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeLessThanOrEqualTo(String value) {
       addCriterion("CARD_TYPE <=", value, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeLike(String value) {
       addCriterion("CARD_TYPE like", value, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeNotLike(String value) {
       addCriterion("CARD_TYPE not like", value, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeIn(List<String> values) {
       addCriterion("CARD_TYPE in", values, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeNotIn(List<String> values) {
       addCriterion("CARD_TYPE not in", values, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeBetween(String value1, String value2) {
       addCriterion("CARD_TYPE between", value1, value2, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardTypeNotBetween(String value1, String value2) {
       addCriterion("CARD_TYPE not between", value1, value2, "cardType");
       return (Criteria)this;
     }

     public Criteria andCardNameIsNull() {
       addCriterion("CARD_NAME is null");
       return (Criteria)this;
     }

     public Criteria andCardNameIsNotNull() {
       addCriterion("CARD_NAME is not null");
       return (Criteria)this;
     }

     public Criteria andCardNameEqualTo(String value) {
       addCriterion("CARD_NAME =", value, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameNotEqualTo(String value) {
       addCriterion("CARD_NAME <>", value, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameGreaterThan(String value) {
       addCriterion("CARD_NAME >", value, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameGreaterThanOrEqualTo(String value) {
       addCriterion("CARD_NAME >=", value, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameLessThan(String value) {
       addCriterion("CARD_NAME <", value, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameLessThanOrEqualTo(String value) {
       addCriterion("CARD_NAME <=", value, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameLike(String value) {
       addCriterion("CARD_NAME like", value, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameNotLike(String value) {
       addCriterion("CARD_NAME not like", value, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameIn(List<String> values) {
       addCriterion("CARD_NAME in", values, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameNotIn(List<String> values) {
       addCriterion("CARD_NAME not in", values, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameBetween(String value1, String value2) {
       addCriterion("CARD_NAME between", value1, value2, "cardName");
       return (Criteria)this;
     }

     public Criteria andCardNameNotBetween(String value1, String value2) {
       addCriterion("CARD_NAME not between", value1, value2, "cardName");
       return (Criteria)this;
     }

     public Criteria andConfigNumberIsNull() {
       addCriterion("CONFIG_NUMBER is null");
       return (Criteria)this;
     }

     public Criteria andConfigNumberIsNotNull() {
       addCriterion("CONFIG_NUMBER is not null");
       return (Criteria)this;
     }

     public Criteria andConfigNumberEqualTo(String value) {
       addCriterion("CONFIG_NUMBER =", value, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberNotEqualTo(String value) {
       addCriterion("CONFIG_NUMBER <>", value, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberGreaterThan(String value) {
       addCriterion("CONFIG_NUMBER >", value, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberGreaterThanOrEqualTo(String value) {
       addCriterion("CONFIG_NUMBER >=", value, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberLessThan(String value) {
       addCriterion("CONFIG_NUMBER <", value, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberLessThanOrEqualTo(String value) {
       addCriterion("CONFIG_NUMBER <=", value, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberLike(String value) {
       addCriterion("CONFIG_NUMBER like", value, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberNotLike(String value) {
       addCriterion("CONFIG_NUMBER not like", value, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberIn(List<String> values) {
       addCriterion("CONFIG_NUMBER in", values, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberNotIn(List<String> values) {
       addCriterion("CONFIG_NUMBER not in", values, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberBetween(String value1, String value2) {
       addCriterion("CONFIG_NUMBER between", value1, value2, "configNumber");
       return (Criteria)this;
     }

     public Criteria andConfigNumberNotBetween(String value1, String value2) {
       addCriterion("CONFIG_NUMBER not between", value1, value2, "configNumber");
       return (Criteria)this;
     }

     public Criteria andLoginAccountIsNull() {
       addCriterion("LOGIN_ACCOUNT is null");
       return (Criteria)this;
     }

     public Criteria andLoginAccountIsNotNull() {
       addCriterion("LOGIN_ACCOUNT is not null");
       return (Criteria)this;
     }

     public Criteria andLoginAccountEqualTo(String value) {
       addCriterion("LOGIN_ACCOUNT =", value, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountNotEqualTo(String value) {
       addCriterion("LOGIN_ACCOUNT <>", value, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountGreaterThan(String value) {
       addCriterion("LOGIN_ACCOUNT >", value, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountGreaterThanOrEqualTo(String value) {
       addCriterion("LOGIN_ACCOUNT >=", value, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountLessThan(String value) {
       addCriterion("LOGIN_ACCOUNT <", value, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountLessThanOrEqualTo(String value) {
       addCriterion("LOGIN_ACCOUNT <=", value, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountLike(String value) {
       addCriterion("LOGIN_ACCOUNT like", value, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountNotLike(String value) {
       addCriterion("LOGIN_ACCOUNT not like", value, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountIn(List<String> values) {
       addCriterion("LOGIN_ACCOUNT in", values, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountNotIn(List<String> values) {
       addCriterion("LOGIN_ACCOUNT not in", values, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountBetween(String value1, String value2) {
       addCriterion("LOGIN_ACCOUNT between", value1, value2, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andLoginAccountNotBetween(String value1, String value2) {
       addCriterion("LOGIN_ACCOUNT not between", value1, value2, "loginAccount");
       return (Criteria)this;
     }

     public Criteria andRemainPartIsNull() {
       addCriterion("REMAIN_PART is null");
       return (Criteria)this;
     }

     public Criteria andRemainPartIsNotNull() {
       addCriterion("REMAIN_PART is not null");
       return (Criteria)this;
     }

     public Criteria andRemainPartEqualTo(Long value) {
       addCriterion("REMAIN_PART =", value, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartNotEqualTo(Long value) {
       addCriterion("REMAIN_PART <>", value, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartGreaterThan(Long value) {
       addCriterion("REMAIN_PART >", value, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartGreaterThanOrEqualTo(Long value) {
       addCriterion("REMAIN_PART >=", value, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartLessThan(Long value) {
       addCriterion("REMAIN_PART <", value, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartLessThanOrEqualTo(Long value) {
       addCriterion("REMAIN_PART <=", value, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartIn(List<Long> values) {
       addCriterion("REMAIN_PART in", values, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartNotIn(List<Long> values) {
       addCriterion("REMAIN_PART not in", values, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartBetween(Long value1, Long value2) {
       addCriterion("REMAIN_PART between", value1, value2, "remainPart");
       return (Criteria)this;
     }

     public Criteria andRemainPartNotBetween(Long value1, Long value2) {
       addCriterion("REMAIN_PART not between", value1, value2, "remainPart");
       return (Criteria)this;
     }

     public Criteria andFreezePartIsNull() {
       addCriterion("FREEZE_PART is null");
       return (Criteria)this;
     }

     public Criteria andFreezePartIsNotNull() {
       addCriterion("FREEZE_PART is not null");
       return (Criteria)this;
     }

     public Criteria andFreezePartEqualTo(Long value) {
       addCriterion("FREEZE_PART =", value, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartNotEqualTo(Long value) {
       addCriterion("FREEZE_PART <>", value, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartGreaterThan(Long value) {
       addCriterion("FREEZE_PART >", value, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartGreaterThanOrEqualTo(Long value) {
       addCriterion("FREEZE_PART >=", value, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartLessThan(Long value) {
       addCriterion("FREEZE_PART <", value, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartLessThanOrEqualTo(Long value) {
       addCriterion("FREEZE_PART <=", value, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartIn(List<Long> values) {
       addCriterion("FREEZE_PART in", values, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartNotIn(List<Long> values) {
       addCriterion("FREEZE_PART not in", values, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartBetween(Long value1, Long value2) {
       addCriterion("FREEZE_PART between", value1, value2, "freezePart");
       return (Criteria)this;
     }

     public Criteria andFreezePartNotBetween(Long value1, Long value2) {
       addCriterion("FREEZE_PART not between", value1, value2, "freezePart");
       return (Criteria)this;
     }

     public Criteria andNotBillIsNull() {
       addCriterion("NOT_BILL is null");
       return (Criteria)this;
     }

     public Criteria andNotBillIsNotNull() {
       addCriterion("NOT_BILL is not null");
       return (Criteria)this;
     }

     public Criteria andNotBillEqualTo(Long value) {
       addCriterion("NOT_BILL =", value, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillNotEqualTo(Long value) {
       addCriterion("NOT_BILL <>", value, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillGreaterThan(Long value) {
       addCriterion("NOT_BILL >", value, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillGreaterThanOrEqualTo(Long value) {
       addCriterion("NOT_BILL >=", value, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillLessThan(Long value) {
       addCriterion("NOT_BILL <", value, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillLessThanOrEqualTo(Long value) {
       addCriterion("NOT_BILL <=", value, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillIn(List<Long> values) {
       addCriterion("NOT_BILL in", values, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillNotIn(List<Long> values) {
       addCriterion("NOT_BILL not in", values, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillBetween(Long value1, Long value2) {
       addCriterion("NOT_BILL between", value1, value2, "notBill");
       return (Criteria)this;
     }

     public Criteria andNotBillNotBetween(Long value1, Long value2) {
       addCriterion("NOT_BILL not between", value1, value2, "notBill");
       return (Criteria)this;
     }

     public Criteria andCardStatusIsNull() {
       addCriterion("CARD_STATUS is null");
       return (Criteria)this;
     }

     public Criteria andCardStatusIsNotNull() {
       addCriterion("CARD_STATUS is not null");
       return (Criteria)this;
     }

     public Criteria andCardStatusEqualTo(String value) {
       addCriterion("CARD_STATUS =", value, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusNotEqualTo(String value) {
       addCriterion("CARD_STATUS <>", value, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusGreaterThan(String value) {
       addCriterion("CARD_STATUS >", value, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusGreaterThanOrEqualTo(String value) {
       addCriterion("CARD_STATUS >=", value, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusLessThan(String value) {
       addCriterion("CARD_STATUS <", value, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusLessThanOrEqualTo(String value) {
       addCriterion("CARD_STATUS <=", value, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusLike(String value) {
       addCriterion("CARD_STATUS like", value, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusNotLike(String value) {
       addCriterion("CARD_STATUS not like", value, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusIn(List<String> values) {
       addCriterion("CARD_STATUS in", values, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusNotIn(List<String> values) {
       addCriterion("CARD_STATUS not in", values, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusBetween(String value1, String value2) {
       addCriterion("CARD_STATUS between", value1, value2, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardStatusNotBetween(String value1, String value2) {
       addCriterion("CARD_STATUS not between", value1, value2, "cardStatus");
       return (Criteria)this;
     }

     public Criteria andCardLimitIsNull() {
       addCriterion("CARD_LIMIT is null");
       return (Criteria)this;
     }

     public Criteria andCardLimitIsNotNull() {
       addCriterion("CARD_LIMIT is not null");
       return (Criteria)this;
     }

     public Criteria andCardLimitEqualTo(Long value) {
       addCriterion("CARD_LIMIT =", value, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitNotEqualTo(Long value) {
       addCriterion("CARD_LIMIT <>", value, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitGreaterThan(Long value) {
       addCriterion("CARD_LIMIT >", value, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitGreaterThanOrEqualTo(Long value) {
       addCriterion("CARD_LIMIT >=", value, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitLessThan(Long value) {
       addCriterion("CARD_LIMIT <", value, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitLessThanOrEqualTo(Long value) {
       addCriterion("CARD_LIMIT <=", value, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitIn(List<Long> values) {
       addCriterion("CARD_LIMIT in", values, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitNotIn(List<Long> values) {
       addCriterion("CARD_LIMIT not in", values, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitBetween(Long value1, Long value2) {
       addCriterion("CARD_LIMIT between", value1, value2, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCardLimitNotBetween(Long value1, Long value2) {
       addCriterion("CARD_LIMIT not between", value1, value2, "cardLimit");
       return (Criteria)this;
     }

     public Criteria andCreateTimeIsNull() {
       addCriterion("CREATE_TIME is null");
       return (Criteria)this;
     }

     public Criteria andCreateTimeIsNotNull() {
       addCriterion("CREATE_TIME is not null");
       return (Criteria)this;
     }

     public Criteria andCreateTimeEqualTo(LocalDateTime value) {
       addCriterion("CREATE_TIME =", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
       addCriterion("CREATE_TIME <>", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
       addCriterion("CREATE_TIME >", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
       addCriterion("CREATE_TIME >=", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeLessThan(LocalDateTime value) {
       addCriterion("CREATE_TIME <", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
       addCriterion("CREATE_TIME <=", value, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeIn(List<LocalDateTime> values) {
       addCriterion("CREATE_TIME in", values, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
       addCriterion("CREATE_TIME not in", values, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
       addCriterion("CREATE_TIME between", value1, value2, "createTime");
       return (Criteria)this;
     }

     public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
       addCriterion("CREATE_TIME not between", value1, value2, "createTime");
       return (Criteria)this;
     }

     public Criteria andUserNameIsNull() {
       addCriterion("USER_NAME is null");
       return (Criteria)this;
     }

     public Criteria andUserNameIsNotNull() {
       addCriterion("USER_NAME is not null");
       return (Criteria)this;
     }

     public Criteria andUserNameEqualTo(String value) {
       addCriterion("USER_NAME =", value, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameNotEqualTo(String value) {
       addCriterion("USER_NAME <>", value, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameGreaterThan(String value) {
       addCriterion("USER_NAME >", value, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameGreaterThanOrEqualTo(String value) {
       addCriterion("USER_NAME >=", value, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameLessThan(String value) {
       addCriterion("USER_NAME <", value, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameLessThanOrEqualTo(String value) {
       addCriterion("USER_NAME <=", value, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameLike(String value) {
       addCriterion("USER_NAME like", "%" + value + "%", "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameNotLike(String value) {
       addCriterion("USER_NAME not like", value, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameIn(List<String> values) {
       addCriterion("USER_NAME in", values, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameNotIn(List<String> values) {
       addCriterion("USER_NAME not in", values, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameBetween(String value1, String value2) {
       addCriterion("USER_NAME between", value1, value2, "userName");
       return (Criteria)this;
     }

     public Criteria andUserNameNotBetween(String value1, String value2) {
       addCriterion("USER_NAME not between", value1, value2, "userName");
       return (Criteria)this;
     }

     public Criteria andUserIdIsNull() {
       addCriterion("USER_ID is null");
       return (Criteria)this;
    }

    public Criteria andUserIdIsNotNull() {
      addCriterion("USER_ID is not null");
      return (Criteria)this;
    }

    public Criteria andUserIdEqualTo(String value) {
      addCriterion("USER_ID =", value, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdNotEqualTo(String value) {
      addCriterion("USER_ID <>", value, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdGreaterThan(String value) {
      addCriterion("USER_ID >", value, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdGreaterThanOrEqualTo(String value) {
      addCriterion("USER_ID >=", value, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdLessThan(String value) {
      addCriterion("USER_ID <", value, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdLessThanOrEqualTo(String value) {
      addCriterion("USER_ID <=", value, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdLike(String value) {
      addCriterion("USER_ID like", value + "%", "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdNotLike(String value) {
      addCriterion("USER_ID not like", value + "%", "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdIn(List<String> values) {
      addCriterion("USER_ID in", values, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdNotIn(List<String> values) {
      addCriterion("USER_ID not in", values, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdBetween(String value1, String value2) {
      addCriterion("USER_ID between", value1, value2, "userId");
      return (Criteria)this;
    }

    public Criteria andUserIdNotBetween(String value1, String value2) {
      addCriterion("USER_ID not between", value1, value2, "userId");
      return (Criteria)this;
    }

    public Criteria andIsDeleteIsNull() {
      addCriterion("IS_DELETE is null");
      return (Criteria)this;
    }

    public Criteria andIsDeleteIsNotNull() {
      addCriterion("IS_DELETE is not null");
      return (Criteria)this;
    }

    public Criteria andIsDeleteEqualTo(String value) {
      addCriterion("IS_DELETE =", value, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteNotEqualTo(String value) {
      addCriterion("IS_DELETE <>", value, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteGreaterThan(String value) {
      addCriterion("IS_DELETE >", value, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteGreaterThanOrEqualTo(String value) {
      addCriterion("IS_DELETE >=", value, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteLessThan(String value) {
      addCriterion("IS_DELETE <", value, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteLessThanOrEqualTo(String value) {
      addCriterion("IS_DELETE <=", value, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteLike(String value) {
      addCriterion("IS_DELETE like", value, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteNotLike(String value) {
      addCriterion("IS_DELETE not like", value, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteIn(List<String> values) {
      addCriterion("IS_DELETE in", values, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteNotIn(List<String> values) {
      addCriterion("IS_DELETE not in", values, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteBetween(String value1, String value2) {
      addCriterion("IS_DELETE between", value1, value2, "isDelete");
      return (Criteria)this;
    }

    public Criteria andIsDeleteNotBetween(String value1, String value2) {
      addCriterion("IS_DELETE not between", value1, value2, "isDelete");
      return (Criteria)this;
    }

    public Criteria andFlagIsNull() {
      addCriterion("FLAG is null");
      return (Criteria)this;
    }

    public Criteria andFlagIsNotNull() {
      addCriterion("FLAG is not null");
      return (Criteria)this;
    }

    public Criteria andFlagEqualTo(Integer value) {
      addCriterion("FLAG =", value, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagNotEqualTo(Integer value) {
      addCriterion("FLAG <>", value, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagGreaterThan(Integer value) {
      addCriterion("FLAG >", value, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagGreaterThanOrEqualTo(Integer value) {
      addCriterion("FLAG >=", value, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagLessThan(Integer value) {
      addCriterion("FLAG <", value, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagLessThanOrEqualTo(Integer value) {
      addCriterion("FLAG <=", value, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagIn(List<Integer> values) {
      addCriterion("FLAG in", values, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagNotIn(List<Integer> values) {
      addCriterion("FLAG not in", values, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagBetween(Integer value1, Integer value2) {
      addCriterion("FLAG between", value1, value2, "flag");
      return (Criteria)this;
    }

    public Criteria andFlagNotBetween(Integer value1, Integer value2) {
      addCriterion("FLAG not between", value1, value2, "flag");
      return (Criteria)this;
    }

    public Criteria andExpSumIsNull() {
      addCriterion("EXP_SUM is null");
      return (Criteria)this;
    }

    public Criteria andExpSumIsNotNull() {
      addCriterion("EXP_SUM is not null");
      return (Criteria)this;
    }

    public Criteria andExpSumEqualTo(Long value) {
      addCriterion("EXP_SUM =", value, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumNotEqualTo(Long value) {
      addCriterion("EXP_SUM <>", value, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumGreaterThan(Long value) {
      addCriterion("EXP_SUM >", value, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumGreaterThanOrEqualTo(Long value) {
      addCriterion("EXP_SUM >=", value, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumLessThan(Long value) {
      addCriterion("EXP_SUM <", value, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumLessThanOrEqualTo(Long value) {
      addCriterion("EXP_SUM <=", value, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumIn(List<Long> values) {
      addCriterion("EXP_SUM in", values, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumNotIn(List<Long> values) {
      addCriterion("EXP_SUM not in", values, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumBetween(Long value1, Long value2) {
      addCriterion("EXP_SUM between", value1, value2, "expSum");
      return (Criteria)this;
    }

    public Criteria andExpSumNotBetween(Long value1, Long value2) {
      addCriterion("EXP_SUM not between", value1, value2, "expSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumIsNull() {
      addCriterion("RECHARGE_SUM is null");
      return (Criteria)this;
    }

    public Criteria andRechargeSumIsNotNull() {
      addCriterion("RECHARGE_SUM is not null");
      return (Criteria)this;
    }

    public Criteria andRechargeSumEqualTo(Long value) {
      addCriterion("RECHARGE_SUM =", value, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumNotEqualTo(Long value) {
      addCriterion("RECHARGE_SUM <>", value, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumGreaterThan(Long value) {
      addCriterion("RECHARGE_SUM >", value, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumGreaterThanOrEqualTo(Long value) {
      addCriterion("RECHARGE_SUM >=", value, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumLessThan(Long value) {
      addCriterion("RECHARGE_SUM <", value, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumLessThanOrEqualTo(Long value) {
      addCriterion("RECHARGE_SUM <=", value, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumIn(List<Long> values) {
      addCriterion("RECHARGE_SUM in", values, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumNotIn(List<Long> values) {
      addCriterion("RECHARGE_SUM not in", values, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumBetween(Long value1, Long value2) {
      addCriterion("RECHARGE_SUM between", value1, value2, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andRechargeSumNotBetween(Long value1, Long value2) {
      addCriterion("RECHARGE_SUM not between", value1, value2, "rechargeSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumIsNull() {
      addCriterion("AWARD_SUM is null");
      return (Criteria)this;
    }

    public Criteria andAwardSumIsNotNull() {
      addCriterion("AWARD_SUM is not null");
      return (Criteria)this;
    }

    public Criteria andAwardSumEqualTo(Long value) {
      addCriterion("AWARD_SUM =", value, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumNotEqualTo(Long value) {
      addCriterion("AWARD_SUM <>", value, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumGreaterThan(Long value) {
      addCriterion("AWARD_SUM >", value, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumGreaterThanOrEqualTo(Long value) {
      addCriterion("AWARD_SUM >=", value, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumLessThan(Long value) {
      addCriterion("AWARD_SUM <", value, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumLessThanOrEqualTo(Long value) {
      addCriterion("AWARD_SUM <=", value, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumIn(List<Long> values) {
      addCriterion("AWARD_SUM in", values, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumNotIn(List<Long> values) {
      addCriterion("AWARD_SUM not in", values, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumBetween(Long value1, Long value2) {
      addCriterion("AWARD_SUM between", value1, value2, "awardSum");
      return (Criteria)this;
    }

    public Criteria andAwardSumNotBetween(Long value1, Long value2) {
      addCriterion("AWARD_SUM not between", value1, value2, "awardSum");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeIsNull() {
      addCriterion("UPDATE_TIME is null");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeIsNotNull() {
      addCriterion("UPDATE_TIME is not null");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
      addCriterion("UPDATE_TIME =", value, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
      addCriterion("UPDATE_TIME <>", value, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
      addCriterion("UPDATE_TIME >", value, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
      addCriterion("UPDATE_TIME >=", value, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeLessThan(LocalDateTime value) {
      addCriterion("UPDATE_TIME <", value, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
      addCriterion("UPDATE_TIME <=", value, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
      addCriterion("UPDATE_TIME in", values, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
      addCriterion("UPDATE_TIME not in", values, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
      addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
      return (Criteria)this;
    }

    public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
      addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
      return (Criteria)this;
    }

    public Criteria andCurrencyIsNull() {
      addCriterion("CURRENCY is null");
      return (Criteria)this;
    }

    public Criteria andCurrencyIsNotNull() {
      addCriterion("CURRENCY is not null");
      return (Criteria)this;
    }

    public Criteria andCurrencyEqualTo(String value) {
      addCriterion("CURRENCY =", value, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyNotEqualTo(String value) {
      addCriterion("CURRENCY <>", value, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyGreaterThan(String value) {
      addCriterion("CURRENCY >", value, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyGreaterThanOrEqualTo(String value) {
      addCriterion("CURRENCY >=", value, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyLessThan(String value) {
      addCriterion("CURRENCY <", value, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyLessThanOrEqualTo(String value) {
      addCriterion("CURRENCY <=", value, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyLike(String value) {
      addCriterion("CURRENCY like", value, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyNotLike(String value) {
      addCriterion("CURRENCY not like", value, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyIn(List<String> values) {
      addCriterion("CURRENCY in", values, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyNotIn(List<String> values) {
      addCriterion("CURRENCY not in", values, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyBetween(String value1, String value2) {
      addCriterion("CURRENCY between", value1, value2, "currency");
      return (Criteria)this;
    }

    public Criteria andCurrencyNotBetween(String value1, String value2) {
      addCriterion("CURRENCY not between", value1, value2, "currency");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeIsNull() {
      addCriterion("VALID_START_TIME is null");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeIsNotNull() {
      addCriterion("VALID_START_TIME is not null");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeEqualTo(LocalDateTime value) {
      addCriterion("VALID_START_TIME =", value, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeNotEqualTo(LocalDateTime value) {
      addCriterion("VALID_START_TIME <>", value, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeGreaterThan(LocalDateTime value) {
      addCriterion("VALID_START_TIME >", value, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeGreaterThanOrEqualTo(LocalDateTime value) {
      addCriterion("VALID_START_TIME >=", value, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeLessThan(LocalDateTime value) {
      addCriterion("VALID_START_TIME <", value, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeLessThanOrEqualTo(LocalDateTime value) {
      addCriterion("VALID_START_TIME <=", value, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeIn(List<LocalDateTime> values) {
      addCriterion("VALID_START_TIME in", values, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeNotIn(List<LocalDateTime> values) {
      addCriterion("VALID_START_TIME not in", values, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeBetween(LocalDateTime value1, LocalDateTime value2) {
      addCriterion("VALID_START_TIME between", value1, value2, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidStartTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
      addCriterion("VALID_START_TIME not between", value1, value2, "validStartTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeIsNull() {
      addCriterion("VALID_END_TIME is null");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeIsNotNull() {
      addCriterion("VALID_END_TIME is not null");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeEqualTo(LocalDateTime value) {
      addCriterion("VALID_END_TIME =", value, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeNotEqualTo(LocalDateTime value) {
      addCriterion("VALID_END_TIME <>", value, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeGreaterThan(LocalDateTime value) {
      addCriterion("VALID_END_TIME >", value, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeGreaterThanOrEqualTo(LocalDateTime value) {
      addCriterion("VALID_END_TIME >=", value, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeLessThan(LocalDateTime value) {
      addCriterion("VALID_END_TIME <", value, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeLessThanOrEqualTo(LocalDateTime value) {
      addCriterion("VALID_END_TIME <=", value, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeIn(List<LocalDateTime> values) {
      addCriterion("VALID_END_TIME in", values, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeNotIn(List<LocalDateTime> values) {
      addCriterion("VALID_END_TIME not in", values, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeBetween(LocalDateTime value1, LocalDateTime value2) {
      addCriterion("VALID_END_TIME between", value1, value2, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andValidEndTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
      addCriterion("VALID_END_TIME not between", value1, value2, "validEndTime");
      return (Criteria)this;
    }

    public Criteria andBetweenValidStartTimeAndValidEndTime(LocalDateTime value) {
      addCriterion("VALID_START_TIME <=", value, "validStartTime");
      addCriterion("VALID_END_TIME >", value, "validEndTime");
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


  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = 31 * result + ((getOredCriteria() == null) ? 0 : getOredCriteria().hashCode());
    return result;
  }
}
