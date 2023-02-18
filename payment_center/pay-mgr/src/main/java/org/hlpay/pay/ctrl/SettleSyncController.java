 package org.hlpay.pay.ctrl


 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import java.util.Map;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.security.SecurityAccessManager;
 import org.hlpay.base.service.IRefundOrderService;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.UnionIdUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;


















 @Api(value = "私域同步到公域异步记账接口", tags = {"异步记账接口"})
 @RestController
 @RequestMapping({"/api"})
 public class SettleSyncController
 {
   private final MyLog _log = MyLog.getLog(org.hlpay.pay.ctrl.SettleSyncController.class);


   @Resource
   private MchInfoService mchInfoService;


   @Resource
   private MchInfoService mchInfoServiceMgr;


   @Resource
   private IRefundOrderService refundOrderService;


   @Resource
   private Mq4MchNotify mq4MchNotify;


   @Autowired
   private SecurityAccessManager securityAccessManager;



   @ApiOperation(value = "私有云专用:同步记账", notes = "同步记账")
   @RequestMapping({"/settle/syncPay"})
   public CommonResult syncPayOrder(@RequestBody PayOrder payOrder, HttpServletRequest request) throws Exception {
     this._log.info("###### 开始接收同步记账支付订单请求 ######", new Object[0]);
     this.securityAccessManager.checkAccessIPWhiteList(request);
     validateSettleSyncParams((JSONObject)JSONObject.toJSON(payOrder), new JSONObject());
     this.mq4MchNotify.forceSend("queue.notify.mch.settle.sync", JSONObject.toJSONString(payOrder));
     return CommonResult.success("同步请求成功");
   }









   private boolean validateSettleSyncParams(JSONObject params, JSONObject payContext) throws Exception {
     String mchId = UnionIdUtil.getIdInfoFromUnionId(params.getString("mchId"))[0][0];
     String mchOrderNo = params.getString("mchOrderNo");
     String payOrderId = params.getString("payOrderId");
     String mchName = params.getString("mchName");

     String sign = params.getString("sign");


     if (StringUtils.isBlank(mchId)) {
       String errorMessage = "request params[mchId] error.";
       throw new Exception(errorMessage);
     }
     if (StringUtils.isBlank(mchOrderNo) && StringUtils.isBlank(payOrderId)) {
       String errorMessage = "request params[mchOrderNo or payOrderId] error.";
       throw new Exception(errorMessage);
     }


     if (StringUtils.isBlank(sign)) {
       String errorMessage = "request params[sign] error.";
       throw new Exception(errorMessage);
     }


     MchInfo platform = this.mchInfoServiceMgr.getRootMchInfo(mchId);
     if (platform == null) {
       String errorMessage = "Can't found mchInfo[mchId=" + mchId + "] record in db.";
       throw new Exception(errorMessage);
     }





     String reqKey = platform.getReqKey();
     if (StringUtils.isBlank(reqKey)) {
       String errorMessage = "reqKey is null[mchId=" + mchId + "] record in db.";
       throw new Exception(errorMessage);
     }
     payContext.put("resKey", platform.getResKey());
     payContext.put("reqKey", platform.getReqKey());


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey, new String[] { "clientIp", "device", "subject", "body", "extra", "errCode", "errMsg", "param1", "param2", "notifyCount", "lastNotifyTime", "expireTime", "createTime", "updateTime" });














     if (!verifyFlag) {
       String errorMessage = "Verify XX pay sign failed.";
       throw new Exception(errorMessage);
     }

     return true;
   }
 }





