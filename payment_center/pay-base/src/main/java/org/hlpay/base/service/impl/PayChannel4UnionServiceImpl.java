package org.hlpay.base.service.impl;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.hlpay.base.channel.unionpay.sdk.AcpService;
import org.hlpay.base.channel.unionpay.sdk.LogUtil;
import org.hlpay.base.channel.unionpay.sdk.SDKConfig;
import org.hlpay.base.channel.unionpay.sdk.UnionConfig;
import org.hlpay.base.model.PayOrder;
import org.hlpay.base.service.IPayChannel4UnionService;
import org.hlpay.common.entity.Result;
import org.hlpay.common.enumm.RetEnum;
import org.hlpay.common.util.Base64Util;
import org.hlpay.common.util.CommonUtil;
import org.hlpay.common.util.RandomStrUtils;
import org.hlpay.common.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "config.union")
@Primary
public class PayChannel4UnionServiceImpl implements IPayChannel4UnionService {
  private static final Logger logger = LoggerFactory.getLogger(PayChannel4UnionServiceImpl.class); @Autowired
  private RedisUtil redisUtil; @Value("${config.union.frontNotifyUrl}")
  private String frontNotifyUrl;
  @Value("${config.union.backNotifyUrl}")
  private String backNotifyUrl;
  @Autowired
  BaseService4PayOrder baseService4PayOrder;

  public String getFrontNotifyUrl() {
    return this.frontNotifyUrl;
  }

  public void setFrontNotifyUrl(String frontNotifyUrl) {
    this.frontNotifyUrl = frontNotifyUrl;
  }

  public String getBackNotifyUrl() {
    return this.backNotifyUrl;
  }

  public void setBackNotifyUrl(String backNotifyUrl) {
    this.backNotifyUrl = backNotifyUrl;
  }


  public JSONObject doUnionPayReq(PayOrder payOrder) {
    String logPrefix = "【银联订单交易状态查询】";
    String payOrderId = payOrder.getPayOrderId();
    Map<String, String> requestData = new HashMap<>();

    requestData.put("version", UnionConfig.version);
    requestData.put("encoding", UnionConfig.encoding_UTF8);
    requestData.put("signMethod", "01");
    requestData.put("txnType", "01");
    requestData.put("txnSubType", "01");
    requestData.put("bizType", "000201");

    if ("UNION_WAP".equals(payOrder.getChannelId())) {
      requestData.put("channelType", "08");
    } else {
      requestData.put("channelType", "07");
    }


    String frontUrl = getFrontNotifyUrl();
    String redirectUrl = payOrder.getRedirectUrl();
    if (redirectUrl != null &&
      redirectUrl.indexOf("http") >= 0) {
      frontUrl = redirectUrl;
    }

    logger.info("前台回调地址：{}", redirectUrl);

    requestData.put("frontUrl", frontUrl);

    requestData.put("merId", UnionConfig.merId);
    requestData.put("accessType", "0");
    requestData.put("orderId", payOrder.getPayOrderId());
    requestData.put("txnTime", UnionConfig.getCurrentTime());
    requestData.put("currencyCode", "156");
    requestData.put("txnAmt", CommonUtil.subZeroAndDot(payOrder.getAmount().toString()));

    JSONObject reqReservedObject = new JSONObject();
    reqReservedObject.put("payOrderId", payOrder.getPayOrderId());
    reqReservedObject.put("channelId", payOrder.getChannelId());
    reqReservedObject.put("amount", payOrder.getAmount().toString());
    String reqReserved = reqReservedObject.toJSONString();

    logger.info("透传字段：{}", reqReserved);
    requestData.put("reqReserved", reqReserved);











    String randomString = RandomStrUtils.getInstance().getMixRandomString(20);
    this.redisUtil.set(randomString, payOrderId);
    requestData.put("backUrl", getBackNotifyUrl() + "/" + randomString);








    Map<String, String> submitFromData = AcpService.sign(requestData, UnionConfig.encoding_UTF8);

    String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
    String form = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, UnionConfig.encoding_UTF8);

    logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据：{}", form);



    if (this.baseService4PayOrder.baseUpdateStatus4Ing(payOrderId, null) != 1);



    JSONObject map = HLPayUtil.makeRetJSONObject("SUCCESS", "", "SUCCESS", null);
    map.put("payOrderId", payOrderId);
    map.put("payUrl", form);
    return map;
  }





  public String unionPay(JSONObject payOrder) {
    String payOrderId = payOrder.getString("payOrderId");
    Map<String, String> requestData = new HashMap<>();

    requestData.put("version", UnionConfig.version);
    requestData.put("encoding", UnionConfig.encoding_UTF8);
    requestData.put("signMethod", "01");
    requestData.put("txnType", "01");
    requestData.put("txnSubType", "01");
    requestData.put("bizType", "000201");

    if ("UNION_WAP".equals(payOrder.getString("channelId"))) {
      requestData.put("channelType", "08");
    } else {
      requestData.put("channelType", "07");
    }


    String frontUrl = getFrontNotifyUrl();
    String redirectUrl = payOrder.getString("redirectUrl");
    if (redirectUrl != null &&
      redirectUrl.indexOf("http") >= 0) {
      frontUrl = redirectUrl;
    }

    logger.info("前台回调地址：{}", redirectUrl);

    requestData.put("frontUrl", frontUrl);

    requestData.put("merId", UnionConfig.merId);
    requestData.put("accessType", "0");
    requestData.put("orderId", payOrder.getString("payOrderId"));
    requestData.put("txnTime", UnionConfig.getCurrentTime());
    requestData.put("currencyCode", "156");
    requestData.put("txnAmt", CommonUtil.subZeroAndDot(payOrder.getString("amount")));

    String reqReserved = "null";
    reqReserved = Base64Util.encodeData(payOrder.toJSONString());
    logger.info("透传字段：{}", reqReserved);
    requestData.put("reqReserved", reqReserved);











    String randomString = RandomStrUtils.getInstance().getMixRandomString(20);
    this.redisUtil.set(randomString, payOrderId);
    requestData.put("backUrl", getBackNotifyUrl() + "/" + randomString);








    Map<String, String> submitFromData = AcpService.sign(requestData, UnionConfig.encoding_UTF8);

    String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
    String form = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, UnionConfig.encoding_UTF8);

    logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据：{}", form);



    if (this.baseService4PayOrder.baseUpdateStatus4Ing(payOrder.getString("payOrderId"), null) != 1);



    return form;
  }


  public Result queryReq(String payOrderId, String mchId, String channelId) throws Exception {
    String logPrefix = "【银联订单交易状态查询】";

    Map<String, String> requestData = new HashMap<>();


    requestData.put("version", UnionConfig.version);
    requestData.put("encoding", UnionConfig.encoding_UTF8);
    requestData.put("signMethod", "01");
    requestData.put("txnType", "00");
    requestData.put("txnSubType", "00");
    requestData.put("bizType", "000201");


    requestData.put("merId", UnionConfig.merId);
    requestData.put("accessType", "0");
    requestData.put("orderId", payOrderId);
    requestData.put("txnTime", UnionConfig.getCurrentTime());




    try {
      Map<String, String> reqData = AcpService.sign(requestData, UnionConfig.encoding_UTF8);


      String url = SDKConfig.getConfig().getSingleQueryUrl();


      Map<String, String> rspData = AcpService.post(reqData, url, UnionConfig.encoding_UTF8);



      if (!rspData.isEmpty()) {
        if (AcpService.validate(rspData, UnionConfig.encoding_UTF8)) {
          LogUtil.writeLog("验证签名成功");
          if ("00".equals(rspData.get("respCode"))) {

            String origRespCode = rspData.get("origRespCode");
            if ("00".equals(origRespCode)) {
              return Result.createSuccessMap(Byte.valueOf((byte)2));
            }
            return Result.createSuccessMap(Byte.valueOf((byte)1));
          }



          logger.warn("{}交易信息不存在, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_NO_BIZ_SEQUENCE_NO.getMessage(), payOrderId });
          throw new Exception("银联交易信息不存在");
        }

        LogUtil.writeErrorLog("验证签名失败");


        logger.warn("{}验证签名失败, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_REMOTE_CHECK_SIGN_FAIL.getMessage(), payOrderId });
        throw new Exception("银联订单交易状态查询验证签名失败");
      }


      LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");


      logger.warn("{}错误http返回状态（http状态码非200）, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_REMOTE_UNUSABLE.getMessage(), payOrderId });
      throw new Exception("银联交易错误http返回状态（http状态码非200）");
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }


  public Result queryReq(PayOrder payOrder) {
    String logPrefix = "【银联订单交易状态查询】";

    Map<String, String> requestData = new HashMap<>();


    requestData.put("version", UnionConfig.version);
    requestData.put("encoding", UnionConfig.encoding_UTF8);
    requestData.put("signMethod", "01");
    requestData.put("txnType", "00");
    requestData.put("txnSubType", "00");
    requestData.put("bizType", "000201");


    requestData.put("merId", UnionConfig.merId);
    requestData.put("accessType", "0");
    requestData.put("orderId", payOrder.getPayOrderId());
    requestData.put("txnTime", UnionConfig.getCurrentTime());




    Map<String, String> reqData = AcpService.sign(requestData, UnionConfig.encoding_UTF8);


    String url = SDKConfig.getConfig().getSingleQueryUrl();


    Map<String, String> rspData = AcpService.post(reqData, url, UnionConfig.encoding_UTF8);



    if (!rspData.isEmpty()) {
      if (AcpService.validate(rspData, UnionConfig.encoding_UTF8)) {
        LogUtil.writeLog("验证签名成功");
        if ("00".equals(rspData.get("respCode"))) {

          String origRespCode = rspData.get("origRespCode");
          if ("00".equals(origRespCode)) {

            payOrder.setStatus(Byte.valueOf((byte)2));


            logger.warn("{}交易成功，可更新订单状态, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_SUCCESS.getMessage(), payOrder.toString() });
            return Result.createSuccessMap(payOrder);
          }  if ("03".equals(origRespCode) || "04"
            .equals(origRespCode) || "05"
            .equals(origRespCode)) {

            payOrder.setStatus(Byte.valueOf((byte)1));


            logger.warn("{}查询成功，但需再次发起交易状态查询交易, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_SUCCESS.getMessage(), payOrder.toString() });
            return Result.createSuccessMap(payOrder);
          }



          logger.warn("{}交易查询时失败, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_REMOTE_DEAL_EXCEPTION.getMessage(), payOrder.toString() });
          return Result.createFailMap("银联订单交易状态查询失败", null);
        }




        logger.warn("{}交易信息不存在, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_NO_BIZ_SEQUENCE_NO.getMessage(), payOrder.toString() });
        return Result.createFailMap("银联订单交易状态查询失败", null);
      }

      LogUtil.writeErrorLog("验证签名失败");


      logger.warn("{}验证签名失败, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_REMOTE_CHECK_SIGN_FAIL.getMessage(), payOrder.toString() });
      return Result.createFailMap("银联订单交易状态查询失败", null);
    }


    LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");


    logger.warn("{}错误http返回状态（http状态码非200）, {}. jsonParam={}", new Object[] { logPrefix, RetEnum.RET_REMOTE_UNUSABLE.getMessage(), payOrder.toString() });
    return Result.createFailMap("银联订单交易状态查询失败", null);
  }

  public String validate(Map<String, String> valideData, String encoding) {
    String message = "Success!";
    if (!AcpService.validate(valideData, encoding)) {
      message = "fail";
    }
    return message;
  }


  public void fileTransfer() {
    Map<String, String> data = new HashMap<>();
    data.put("version", UnionConfig.version);
    data.put("encoding", UnionConfig.encoding_UTF8);
    data.put("signMethod", "01");
    data.put("txnType", "76");
    data.put("txnSubType", "01");
    data.put("bizType", "000000");
    data.put("accessType", "0");
    data.put("merId", UnionConfig.merId);
    data.put("txnTime", UnionConfig.getCurrentTime());
    data.put("fileType", "00");
    Map<String, String> reqData = AcpService.sign(data, UnionConfig.encoding_UTF8);
    String url = SDKConfig.getConfig().getFileTransUrl();

    Map<String, String> rspData = AcpService.post(reqData, url, UnionConfig.encoding_UTF8);


    if (!rspData.isEmpty()) {

      if (AcpService.validate(rspData, UnionConfig.encoding_UTF8))
      {
        logger.info("验证签名成功");

        String respCode = rspData.get("respCode");

        if ("00".equals(respCode));
      }
      else
      {
        logger.info("验证签名失败");
      }

    } else {

      logger.info("未获取到返回报文或返回http状态码非200");
    }
  }
}

