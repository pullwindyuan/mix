package org.hlpay.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.hlpay.base.model.RefundOrder;
import org.hlpay.base.service.BaseService4RefundOrder;
import org.hlpay.base.service.INotifyRefundService;
import org.hlpay.base.service.IPayChannel4AliService;
import org.hlpay.base.service.IPayChannel4WxService;
import org.hlpay.base.service.IRefundOrderService;
import org.hlpay.base.service.mq.Mq4MchNotify;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.entity.Result;
import org.hlpay.common.util.BeanConvertUtils;
import org.hlpay.common.util.HLPayUtil;
import org.hlpay.common.util.JsonUtil;
import org.hlpay.common.util.MyLog;
import org.hlpay.common.util.ResultUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RefundOrderServiceImpl extends BaseService4RefundOrder implements IRefundOrderService {
  private static final MyLog _log = MyLog.getLog(RefundOrderServiceImpl.class);

  @Resource
  private IPayChannel4AliService payChannel4AliService;

  @Resource
  private IPayChannel4WxService payChannel4WxService;

  @Resource
  Mq4MchNotify mq4MchNotify;
  @Resource
  INotifyRefundService notifyRefundService;

  public Map select(JSONObject jsonParam) {
    String refundOrderId = jsonParam.get("refundOrderId").toString();
    RefundOrder refundOrder = baseSelectRefundOrder(refundOrderId);
    Map<String, Object> resultMap = new HashMap<>();
    if (refundOrder == null) {
      return ResultUtil.createFailMap("根据退款订单号查询退款订单失败", null);
    }

    return ResultUtil.createSuccessMap(JsonUtil.getJSONObjectFromObj(refundOrder));
  }


  public RefundOrder selectByMchIdAndRefundOrderId(JSONObject jsonParam) {
    String mchId = jsonParam.get("mchId").toString();
    String refundOrderId = jsonParam.get("refundOrderId").toString();
    return baseSelectByMchIdAndRefundOrderId(mchId, refundOrderId);
  }


  public RefundOrder selectByMchIdAndMchRefundNo(JSONObject jsonParam) {
    String mchId = jsonParam.get("mchId").toString();
    String mchRefundNo = jsonParam.get("mchRefundNo").toString();
    return baseSelectByMchIdAndMchRefundNo(mchId, mchRefundNo);
  }


  public Map updateStatus4Ing(JSONObject jsonParam) {
    String refundOrderId = jsonParam.get("refundOrderId").toString();
    String channelOrderNo = jsonParam.get("channelOrderNo").toString();
    int result = baseUpdateStatus4Ing(refundOrderId, channelOrderNo);
    Map<String, Object> resultMap = new HashMap<>();
    if (result > 0) {
      resultMap.put("isSuccess", Boolean.valueOf(true));
    } else {
      resultMap.put("isSuccess", Boolean.valueOf(false));
    }
    resultMap.put("bizResult", Integer.valueOf(result));
    return resultMap;
  }












  public Map updateStatus4Success(JSONObject jsonParam) {
    return null;
  }


  public Map updateStatus4Complete(JSONObject jsonParam) {
    String refundOrderId = jsonParam.get("refundOrderId").toString();
    int result = baseUpdateStatus4Complete(refundOrderId);
    Map<String, Object> resultMap = new HashMap<>();
    if (result > 0) {
      resultMap.put("isSuccess", Boolean.valueOf(true));
    } else {
      resultMap.put("isSuccess", Boolean.valueOf(false));
    }
    resultMap.put("bizResult", Integer.valueOf(result));
    return resultMap;
  }


  public int create(RefundOrder refundOrder) {
    int result = baseCreateRefundOrder(refundOrder);
    return result;
  }


  public void sendRefundNotify(String refundOrderId, String channelName) {
    JSONObject object = new JSONObject();
    sendRefundNotify(object);
  }




  public Result query(JSONObject queryParam) throws NoSuchMethodException {
    RefundOrder refundOrder;
    String mchId = (String)queryParam.get("mchId");
    String refundOrderId = (String)queryParam.get("refundOrderId");
    String mchRefundNo = (String)queryParam.get("mchRefundNo");
    String queryFromChannel = (String)queryParam.get("queryFromChannel");
    String executeNotify = (String)queryParam.get("executeNotify");


    if (StringUtils.isNotBlank(refundOrderId)) {
      refundOrder = selectByMchIdAndRefundOrderId(queryParam);
    } else {
      refundOrder = selectByMchIdAndMchRefundNo(queryParam);
    }

    if (refundOrder == null) {
      return Result.createFailMap("退款查询失败", null);
    }

    Byte refundStatus = refundOrder.getStatus();

    Map<String, Object> map = new HashMap<>();
    if ((refundStatus.byteValue() != 2 && refundStatus.byteValue() != 3) ||
      Boolean.parseBoolean(queryFromChannel)) {
      Result queryResult;
      String channelId = refundOrder.getChannelId();

      Map<String, Object> bizResult = null;
      boolean updatStatus = false;
      boolean isSuccess = false;
      switch (channelId) {
        case "WX_APP":
        case "WX_JSAPI":
        case "WX_NATIVE":
        case "WX_MWEB":
          queryResult = this.payChannel4WxService.getWxRefundReq(refundOrder);
          _log.info("查询返回结果:{}", new Object[] { JSONObject.toJSONString(queryResult) });
          bizResult = (Map<String, Object>)queryResult.getBizResult();
          isSuccess = queryResult.isSuccess();
          break;
        case "ALIPAY_MOBILE":
        case "ALIPAY_PC":
        case "ALIPAY_WAP":
        case "ALIPAY_QR":
          queryResult = this.payChannel4AliService.getAliRefundReq(refundOrder);
          _log.info("查询返回结果:{}", new Object[] { JSONObject.toJSONString(queryResult) });
          bizResult = (Map<String, Object>)queryResult.getBizResult();
          isSuccess = queryResult.isSuccess();
          break;
      }






      if (isSuccess) {
        refundOrder.setStatus(Byte.valueOf((byte)2));
        updatStatus = true;
      } else {
        map.put("channelErrCode", bizResult.get("channelErrCode"));
        map.put("channelErrMsg", bizResult.get("channelErrMsg"));
      }
    }
    boolean isNotify = Boolean.parseBoolean(executeNotify);
    if (isNotify) {
      JSONObject jsonParam = new JSONObject();
      jsonParam.put("refundOrderId", refundOrderId);
      this.notifyRefundService.sendBizRefundNotify(refundOrder);
      _log.info("业务查单完成,并再次发送业务支付通知.发送结果:{}", new Object[] { Integer.valueOf(1) });
    }
    return Result.createSuccessMap(refundOrder);
  }


  public Map sendRefundNotify(JSONObject jsonParam) {
    String msg = jsonParam.get("msg").toString();
    int result = 1;

    try {
      this.mq4MchNotify.send("queue.notify.mch.refund", msg);
    } catch (Exception e) {
      _log.error(e, "");
      result = 0;
    }
    Map<String, Object> resultMap = new HashMap<>();
    if (result > 0) {
      resultMap.put("isSuccess", Boolean.valueOf(true));
    } else {
      resultMap.put("isSuccess", Boolean.valueOf(false));
    }
    resultMap.put("bizResult", Integer.valueOf(result));
    return resultMap;
  }


  public CommonResult<Result> doAliRefundReq(String tradeType, RefundOrder refundOrder, String resKey) throws NoSuchMethodException {
    Map<? extends String, ?> result = this.payChannel4AliService.doAliRefundReq(refundOrder);
    System.out.println("doAliRefundReq result =" + result);
    Boolean s = (Boolean)result.get("isSuccess");
    if (!s.booleanValue()) {
      return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "支付宝退款失败"), resKey);
    }
    this.notifyRefundService.sendBizRefundNotify(refundOrder);
    Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
    map.putAll(result);
    return HLPayUtil.makeRetData(map, resKey);
  }


  public CommonResult<Result> getAliRefundReq(String tradeType, RefundOrder refundOrder, String resKey) throws NoSuchMethodException {
    Result result = this.payChannel4AliService.getAliRefundReq(refundOrder);
    System.out.println("map=" + result);
    Boolean s = Boolean.valueOf(result.isSuccess());
    if (!s.booleanValue()) {
      return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "查询支付宝退款失败"), resKey);
    }
    this.notifyRefundService.sendBizRefundNotify(refundOrder);
    Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
    map.putAll(BeanConvertUtils.bean2Map(result));
    return HLPayUtil.makeRetData(map, resKey);
  }


  public CommonResult<Result> doWxRefundReq(String tradeType, RefundOrder refundOrder, String resKey) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("refundOrder", refundOrder);
    Result result = this.payChannel4WxService.doWxRefundReq(refundOrder);
    Boolean s = Boolean.valueOf(result.isSuccess());
    if (!s.booleanValue()) {
      return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "调用微信退款失败"), resKey);
    }
    this.notifyRefundService.sendBizRefundNotify(refundOrder);
    Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
    map.putAll(BeanConvertUtils.bean2Map(result));
    return HLPayUtil.makeRetData(map, resKey);
  }


  public CommonResult<Result> getWxRefundReq(String tradeType, RefundOrder refundOrder, String resKey) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("refundOrder", refundOrder);
    Result result = this.payChannel4WxService.getWxRefundReq(refundOrder);
    System.out.println("get WX refund result=" + result);
    Boolean s = Boolean.valueOf(result.isSuccess());
    if (!s.booleanValue()) {
      return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "查询微信退款失败"), resKey);
    }
    this.notifyRefundService.sendBizRefundNotify(refundOrder);
    Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
    map.putAll(BeanConvertUtils.bean2Map(result));
    return HLPayUtil.makeRetData(map, resKey);
  }
}

