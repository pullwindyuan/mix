 package org.hlpay.base.vo;

 import com.alibaba.fastjson.annotation.JSONField;
 import com.alibaba.fastjson.serializer.ToStringSerializer;
 import io.swagger.annotations.ApiModelProperty;
 import org.hlpay.common.annotation.Excel;
 import org.hlpay.common.enumm.CurrencyTypeEnum;
 import org.hlpay.common.enumm.RunModeEnum;

 import java.io.Serializable;
 import java.math.BigDecimal;
 import java.math.RoundingMode;
 import java.time.LocalDateTime;

 public class SettleCardVo implements Serializable { 
     @Excel(name = "订单日期")
     @ApiModelProperty("有效起始时间（闭）")
   private String validStartTimeStr; 
     @ApiModelProperty("有效起始时间（闭）")
   private LocalDateTime validStartTime; 
     @Excel(name = "企业名称")
   @ApiModelProperty("持有者名称")
   private String userName; 
     @Excel(name = "商品金额(¥)")
   @ApiModelProperty("应收金额")
   private String via; @Excel(name = "优惠金额(¥)")
   @ApiModelProperty("优惠金额")
   private String cfa; @Excel(name = "支付金额(¥)")
   @ApiModelProperty("实际入账金额")
   private String tia; @Excel(name = "服务费(¥)")
   @ApiModelProperty("服务费")
   private String pda; @Excel(name = "结算金额(¥)")
   @ApiModelProperty("实际提现金额")
   private String wda; 
   @ApiModelProperty("卡ID")
   private String cardId; 
   @ApiModelProperty("卡号")
   private String cardNumber; 
   @ApiModelProperty("对应的商户类型：PLATFORM（平台），PLATFORM-AGENCY（平台代理），MCH（商户）， MCH_BRANCH（门店）")
   private String mchType; 
   @ApiModelProperty("卡类型")
   private String cardType; private Integer flag; 
   @ApiModelProperty("卡名称")
   private String cardName; 
   @ApiModelProperty("出账配置")
   private String configNumber; 
   @ApiModelProperty("结算状态")
   private String cardStatus; 
   public SettleCardVo setValidStartTimeStr(String validStartTimeStr) { 
       this.validStartTimeStr = validStartTimeStr; 
       return this; 
   } 
   @ApiModelProperty("出账金额") 
   private String epa; 
   @ApiModelProperty("持有者ID") 
   private String userId; 
   @ApiModelProperty("币种") 
   private String currency; 
   @ApiModelProperty("结算状态:0-记账中，1-记账完成待确认，2-已确认，3-已完成， 4-完成现金结算 ") 
   private String settleStatus; 
   @ApiModelProperty("创建时间") 
   private LocalDateTime createTime; 
   @ApiModelProperty("有效结束时间（开）") 
   private LocalDateTime validEndTime; 
   @ApiModelProperty("现金结算日期") 
   private LocalDateTime cashSettleDate; 
   private Long remainPart; 
   private Long freezePart; 
   private Long notBill; 
   private Long expSum; 
   private Long rechargeSum; 
   private Long awardSum; 
   @ApiModelProperty("所在年月") 
   @JSONField(serializeUsing = ToStringSerializer.class) 
   private Long yearMonthGroup; 
   @ApiModelProperty("所在年") 
   @JSONField(serializeUsing = ToStringSerializer.class) 
   private Long yearGroup; 
   private static final long serialVersionUID = 1L;
   public SettleCardVo setValidStartTime(LocalDateTime validStartTime) { 
       this.validStartTime = validStartTime; 
       return this; 
   } 
   public SettleCardVo setUserName(String userName) {
       this.userName = userName;
       return this; 
      } 
   public SettleCardVo setVia(String via) { 
       this.via = via; 
       return this; 
      } 
   public SettleCardVo setCfa(String cfa) { 
       this.cfa = cfa; return this; 
   }
   public SettleCardVo setTia(String tia) { 
       this.tia = tia; 
       return this; 
   } 
   public SettleCardVo setPda(String pda) { 
       this.pda = pda; 
       return this; 
   } 
   public SettleCardVo setWda(String wda) { 
       this.wda = wda;
       return this; 
   } 
   public SettleCardVo setCardId(String cardId) { this.cardId = cardId; return this;    } 
   public SettleCardVo setCardNumber(String cardNumber) { this.cardNumber = cardNumber; return this;    } 
   public SettleCardVo setMchType(String mchType) { this.mchType = mchType; return this;    } 
   public SettleCardVo setCardType(String cardType) { this.cardType = cardType; return this;    } 
   public SettleCardVo setFlag(Integer flag) { this.flag = flag; return this;    } 
   public SettleCardVo setCardName(String cardName) { this.cardName = cardName; return this;    } 
   public SettleCardVo setConfigNumber(String configNumber) { this.configNumber = configNumber; return this;    } 
   public SettleCardVo setCardStatus(String cardStatus) { this.cardStatus = cardStatus; return this;    } 
   public SettleCardVo setEpa(String epa) { this.epa = epa; return this;    } 
   public SettleCardVo setUserId(String userId) { this.userId = userId; return this;    } 
   public SettleCardVo setCurrency(String currency) { this.currency = currency; return this;    } 
   public SettleCardVo setSettleStatus(String settleStatus) { this.settleStatus = settleStatus; return this;    } 
   public SettleCardVo setCreateTime(LocalDateTime createTime) { this.createTime = createTime; return this;    } 
   public SettleCardVo setValidEndTime(LocalDateTime validEndTime) { this.validEndTime = validEndTime; return this;    } 
   public SettleCardVo setCashSettleDate(LocalDateTime cashSettleDate) { this.cashSettleDate = cashSettleDate; return this;    } 
   public SettleCardVo setRemainPart(Long remainPart) { this.remainPart = remainPart; return this;    } 
   public SettleCardVo setFreezePart(Long freezePart) { this.freezePart = freezePart; return this;    } 
   public SettleCardVo setNotBill(Long notBill) { this.notBill = notBill; return this;    } 
   public SettleCardVo setExpSum(Long expSum) { this.expSum = expSum; return this;    } 
   public SettleCardVo setRechargeSum(Long rechargeSum) { this.rechargeSum = rechargeSum; return this;    } 
   public SettleCardVo setAwardSum(Long awardSum) { this.awardSum = awardSum; return this;    } 
   public SettleCardVo setYearMonthGroup(Long yearMonthGroup) { this.yearMonthGroup = yearMonthGroup; return this;    } 
   public SettleCardVo setYearGroup(Long yearGroup) { this.yearGroup = yearGroup; return this;    } 

   public String getValidStartTimeStr() {
     return this.validStartTimeStr;
   }

   public LocalDateTime getValidStartTime() {
     return this.validStartTime;
   }

   public String getUserName() {
     return this.userName;
   }

   public String getCardId() {
     return this.cardId;
   }



   public String getCardNumber() {
     return this.cardNumber;
   }

   public String getMchType() {
     return this.mchType;
   }

   public String getCardType() {
     return this.cardType;
   }

   public Integer getFlag() {
     return this.flag;
   }

   public String getCardName() {
     return this.cardName;
   }

   public String getConfigNumber() {
     return this.configNumber;
   }

   public String getCardStatus() {
     return this.cardStatus;
   }

   public String getUserId() {
     return this.userId;
   }

   public String getCurrency() {
     return this.currency;
   }

   public String getSettleStatus() {
     return this.settleStatus;
   }

   public LocalDateTime getCreateTime() {
     return this.createTime;
   }

   public LocalDateTime getValidEndTime() {
     return this.validEndTime;
   }

   public LocalDateTime getCashSettleDate() {
     return this.cashSettleDate;
   }

   public Long getRemainPart() {
     return this.remainPart;
   }

   public Long getFreezePart() {
     return this.freezePart;
   }

   public Long getNotBill() {
     return this.notBill;
   }

   public Long getExpSum() {
     return this.expSum;
   }

   public Long getRechargeSum() {
     return this.rechargeSum;
   }

   public Long getAwardSum() {
     return this.awardSum;
   }

   public Long getYearMonthGroup() {
     return this.yearMonthGroup;
   }

   public Long getYearGroup() {
     return this.yearGroup;
   }
   private String getAmountVOString(String amountStr) {
     String header, tail = CurrencyTypeEnum.valueOf(getCurrency().toUpperCase()).getDesc();

     if (this.flag.intValue() == RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue()) {
       header = "自收：";
     } else {
       header = "代收：";
     }
     return header + amountStr + tail;
   }

   private String getAmountVOStringWithOutHeader(String amountStr) {
     String tail = CurrencyTypeEnum.valueOf(getCurrency().toUpperCase()).getDesc();
     return amountStr + tail;
   }


   public String getTia() {
     if (this.tia == null) {
       if (this.remainPart == null) {
         return "0";
       }
       this.tia = BigDecimal.valueOf(this.remainPart.longValue()).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     if (!this.tia.contains(".")) {
       this.tia = (new BigDecimal(this.tia)).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     return getAmountVOString(this.tia);
   }

   public String getVia() {
     if (this.via == null) {
       if (this.freezePart == null) {
         return "0";
       }
       this.via = BigDecimal.valueOf(this.freezePart.longValue()).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     if (!this.via.contains(".")) {
       this.via = (new BigDecimal(this.via)).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     return getAmountVOStringWithOutHeader(this.via);
   }

   public String getCfa() {
     if (this.cfa == null) {
       if (this.notBill == null) {
         return "0";
       }
       this.cfa = BigDecimal.valueOf(this.notBill.longValue()).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     if (!this.cfa.contains(".")) {
       this.cfa = (new BigDecimal(this.cfa)).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     return getAmountVOStringWithOutHeader(this.cfa);
   }

   public String getEpa() {
     if (this.epa == null) {
       if (this.expSum == null) {
         return "0";
       }
       return BigDecimal.valueOf(this.expSum.longValue()).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     if (!this.epa.contains(".")) {
       this.epa = (new BigDecimal(this.epa)).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     return getAmountVOString(this.epa);
   }

   public String getWda() {
     if (this.wda == null) {
       if (this.rechargeSum == null) {
         return "0";
       }
       this.wda = BigDecimal.valueOf(this.rechargeSum.longValue()).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     if (!this.wda.contains(".")) {
       this.wda = (new BigDecimal(this.wda)).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     return getAmountVOStringWithOutHeader(this.wda);
   }

   public String getPda() {
     if (this.pda == null) {
       if (this.awardSum == null) {
         return "0";
       }
       this.pda = BigDecimal.valueOf(this.awardSum.longValue()).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     if (!this.pda.contains(".")) {
       this.pda = (new BigDecimal(this.pda)).divide(new BigDecimal("100"), 2, RoundingMode.UNNECESSARY).toPlainString();
     }
     return getAmountVOStringWithOutHeader(this.pda);
   } }





