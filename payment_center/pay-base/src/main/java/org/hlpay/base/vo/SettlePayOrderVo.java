 package org.hlpay.base.vo;

 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.ApiModelProperty;
 import org.hlpay.common.annotation.Excel;

 import java.io.Serializable;
 import java.util.Date;

 public class SettlePayOrderVo implements Serializable {
     @Excel(name = "订单日期")
     @ApiModelProperty("更新时间")
     private String updateTimeStr;
     @ApiModelProperty("更新时间")
     private Date updateTime;
     @Excel(name = "订单编号")
     @ApiModelProperty("商户订单号")
     private String mchOrderNo;
     @Excel(name = "用户账号")
   @ApiModelProperty("用户编号")
   private String userNo; @Excel(name = "商品金额(¥)")
   @ApiModelProperty("应收金额")
   private String via; @Excel(name = "优惠金额(¥)")
   @ApiModelProperty("优惠金额")
   private String cfa; @Excel(name = "支付金额(¥)")
   @ApiModelProperty("实际入账金额")
   private String tia; @ApiModelProperty("支付订单号")
   private String payOrderId; @ApiModelProperty("商户ID")
   private String mchId; @ApiModelProperty("支付渠道ID")
   private String channelId; public SettlePayOrderVo setUpdateTimeStr(String updateTimeStr) { this.updateTimeStr = updateTimeStr; return this; }
     @ApiModelProperty("支付金额,单位分")
     private Long amount;
   @ApiModelProperty("三位货币代码,人民币:cny")
   private String currency;
   @ApiModelProperty("客户端IP")
   private String clientIp;
   @ApiModelProperty("设备")
   private String device;
   @ApiModelProperty("商品标题")
   private String subject;
   @ApiModelProperty("商品描述信息")
   private String body;
   @ApiModelProperty("渠道订单号")
   private String channelOrderNo;
   @ApiModelProperty("订单支付成功时间")
   private Long paySuccTime;
   @ApiModelProperty("创建时间")
   private Date createTime;
   @ApiModelProperty("扩展参数2")
   private String param2;
   @ApiModelProperty("商户名称")
   private String mchName;
   public SettlePayOrderVo setUpdateTime(Date updateTime) {
       this.updateTime = updateTime;
       return this;
   }
   public SettlePayOrderVo setMchOrderNo(String mchOrderNo) {
       this.mchOrderNo = mchOrderNo;
       return this;
   }
   public SettlePayOrderVo setUserNo(String userNo) {
       this.userNo = userNo;
       return this;
   }
   public SettlePayOrderVo setVia(String via) {
       this.via = via;
       return this;
   }
   public SettlePayOrderVo setCfa(String cfa) {
       this.cfa = cfa;
       return this;
   } public SettlePayOrderVo setTia(String tia) {
       this.tia = tia;
       return this;
   } public SettlePayOrderVo setPayOrderId(String payOrderId) {
       this.payOrderId = payOrderId;
       return this;
   }
   public SettlePayOrderVo setMchId(String mchId) {
       this.mchId = mchId;
       return this;
   }
   public SettlePayOrderVo setChannelId(String channelId) {
       this.channelId = channelId;
       return this;
   }
   public SettlePayOrderVo setAmount(Long amount) {
       this.amount = amount;
       return this;
   }
   public SettlePayOrderVo setCurrency(String currency) {
       this.currency = currency;
       return this;
   }
   public SettlePayOrderVo setClientIp(String clientIp) {
       this.clientIp = clientIp;
       return this;
   }
   public SettlePayOrderVo setDevice(String device) {
       this.device = device;
       return this;
   }
   public SettlePayOrderVo setSubject(String subject) {
       this.subject = subject;
       return this;
   }
   public SettlePayOrderVo setBody(String body) {
       this.body = body;
       return this;
   }
   public SettlePayOrderVo setChannelOrderNo(String channelOrderNo) {
       this.channelOrderNo = channelOrderNo;
       return this;
   }
   public SettlePayOrderVo setPaySuccTime(Long paySuccTime) {
       this.paySuccTime = paySuccTime;
       return this;
   } public SettlePayOrderVo setCreateTime(Date createTime) {
       this.createTime = createTime;
       return this;
   } public SettlePayOrderVo setParam2(String param2) {
       this.param2 = param2;
       return this;
   } public SettlePayOrderVo setMchName(String mchName) {
       this.mchName = mchName;
       return this;
   }

   public String getUpdateTimeStr() {
     return this.updateTimeStr;
   }

   public Date getUpdateTime() {
     return this.updateTime;
   }

   public String getMchOrderNo() {
     return this.mchOrderNo;
   }

   public String getPayOrderId() {
     return this.payOrderId;
   }

   public String getMchId() {
     return this.mchId;
   }

   public String getChannelId() {
     return this.channelId;
   }

   public Long getAmount() {
     return this.amount;
   }

   public String getCurrency() {
     return this.currency;
   }

   public String getClientIp() {
     return this.clientIp;
   }

   public String getDevice() {
     return this.device;
   }

   public String getSubject() {
     return this.subject;
   }

   public String getBody() {
     return this.body;
   }

   public String getChannelOrderNo() {
     return this.channelOrderNo;
   }

   public Long getPaySuccTime() {
     return this.paySuccTime;
   }

   public Date getCreateTime() {
     return this.createTime;
   }

   public String getTia() {
     return String.valueOf((float)getAmount().longValue() / 100.0F);
   }

   public String getVia() {
     try {
       JSONObject param2 = JSONObject.parseObject(innerGetParam2());
       return String.valueOf((float)param2.getLong("price").longValue() / 100.0F);
     } catch (Exception e) {
       e.printStackTrace();
       return this.via;
     }
   }

   public String getCfa() {
     try {
       JSONObject param2 = JSONObject.parseObject(innerGetParam2());
       return String.valueOf((float)param2.getLong("cutOff").longValue() / 100.0F);
     } catch (Exception e) {
       e.printStackTrace();
       return this.cfa;
     }
   }

   public String getMchName() {
     try {
       JSONObject param2 = JSONObject.parseObject(innerGetParam2());
       return param2.getString("mchName");
     } catch (Exception e) {
       e.printStackTrace();
       return this.mchName;
     }
   }

   public String getUserNo() {
     try {
       JSONObject param2 = JSONObject.parseObject(innerGetParam2());
       return param2.getString("userAccount");
     } catch (Exception e) {
       e.printStackTrace();
       return this.userNo;
     }
   }

   private String innerGetParam2() {
     return this.param2;
   }

   public String getParam2() {
     return null;
   } }

