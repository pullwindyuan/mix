 package org.hlpay.base.service.impl;

 import com.alibaba.fastjson.JSONObject;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.hlpay.base.model.TransOrder;
 import org.hlpay.base.service.BaseService4TransOrder;
 import org.hlpay.base.service.IPayChannel4AliService;
 import org.hlpay.base.service.IPayChannel4WxService;
 import org.hlpay.base.service.ITransOrderService;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.RetEnum;
 import org.hlpay.common.util.BeanConvertUtils;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.JsonUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.ResultUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Primary;
 import org.springframework.stereotype.Service;

 @Service
 @Primary
 public class TransOrderServiceImpl
   extends BaseService4TransOrder implements ITransOrderService {
   private static final MyLog _log = MyLog.getLog(TransOrderServiceImpl.class);


   @Autowired
   private ITransOrderService transOrderService;


   @Resource
   private IPayChannel4AliService payChannel4AliService;

   @Resource
   private IPayChannel4WxService payChannel4WxService;


   public Map select(JSONObject jsonParam) {
     String transOrderId = jsonParam.get("transOrderId").toString();
     TransOrder transOrder = baseSelectTransOrder(transOrderId);
     JSONObject transOrderObj = JsonUtil.getJSONObjectFromObj(transOrder);
     Map<String, Object> resultMap = new HashMap<>();
     if (transOrderObj == null) {
       return ResultUtil.createFailMap("根据转账订单号查询转账订单失败", RetEnum.RET_BIZ_DATA_NOT_EXISTS.getMessage());
     }
     return ResultUtil.createSuccessMap(transOrderObj);
   }


   public Map selectByMchIdAndTransOrderId(JSONObject jsonParam) {
     String mchId = jsonParam.get("mchId").toString();
     String transOrderId = jsonParam.get("transOrderId").toString();
     TransOrder transOrder = baseSelectByMchIdAndTransOrderId(mchId, transOrderId);
     JSONObject transOrderObj = JsonUtil.getJSONObjectFromObj(transOrder);
     Map<String, Object> resultMap = new HashMap<>();
     if (transOrderObj == null) {
       return ResultUtil.createFailMap("根据商户号和转账订单号查询转账订单失败", RetEnum.RET_BIZ_DATA_NOT_EXISTS.getMessage());
     }
     return ResultUtil.createSuccessMap(transOrderObj);
   }


   public Map selectByMchIdAndMchTransNo(JSONObject jsonParam) {
     String mchId = jsonParam.get("mchId").toString();
     String mchTransNo = jsonParam.get("mchTransNo").toString();
     TransOrder transOrder = baseSelectByMchIdAndMchTransNo(mchId, mchTransNo);
     JSONObject transOrderObj = JsonUtil.getJSONObjectFromObj(transOrder);
     Map<String, Object> resultMap = new HashMap<>();
     if (transOrderObj == null) {
       return ResultUtil.createFailMap("根据商户号和商户订单号查询支付订单失败", RetEnum.RET_BIZ_DATA_NOT_EXISTS.getMessage());
     }
     return ResultUtil.createSuccessMap(transOrderObj);
   }


   public TransOrder selectByMchIdAndTransOrderId(String mchId, String transOrderId) {
     return baseSelectByMchIdAndTransOrderId(mchId, transOrderId);
   }


   public TransOrder selectByMchIdAndMchTransNo(String mchId, String mchTransNo) {
     return baseSelectByMchIdAndMchTransNo(mchId, mchTransNo);
   }


   public Map updateStatus4Ing(JSONObject jsonParam) {
     String transOrderId = jsonParam.get("transOrderId").toString();
     String channelOrderNo = jsonParam.get("channelOrderNo").toString();
     int result = baseUpdateStatus4Ing(transOrderId, channelOrderNo);
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
     String transOrderId = jsonParam.get("transOrderId").toString();
     int result = baseUpdateStatus4Success(transOrderId);
     Map<String, Object> resultMap = new HashMap<>();
     if (result > 0) {
       resultMap.put("isSuccess", Boolean.valueOf(true));
     } else {
       resultMap.put("isSuccess", Boolean.valueOf(false));
     }
     resultMap.put("bizResult", Integer.valueOf(result));
     return resultMap;
   }


   public Map updateStatus4Complete(JSONObject jsonParam) {
     String transOrderId = jsonParam.get("transOrderId").toString();
     int result = baseUpdateStatus4Complete(transOrderId);
     Map<String, Object> resultMap = new HashMap<>();
     if (result > 0) {
       resultMap.put("isSuccess", Boolean.valueOf(true));
     } else {
       resultMap.put("isSuccess", Boolean.valueOf(false));
     }
     resultMap.put("bizResult", Integer.valueOf(result));
     return resultMap;
   }


   public int updateStatus4Ing(String transOrderId) {
     int result = baseUpdateStatus4Ing(transOrderId);
     return result;
   }


   public int updateStatus4Success(String transOrderId) {
     int result = baseUpdateStatus4Success(transOrderId);
     return result;
   }


   public int updateStatus4Complete(String transOrderId) {
     int result = baseUpdateStatus4Complete(transOrderId);
     return result;
   }


   public Map sendTransNotify(JSONObject jsonParam) {
     int result = 1;







     Map<String, Object> resultMap = new HashMap<>();
     if (result > 0) {
       resultMap.put("isSuccess", Boolean.valueOf(true));
     } else {
       resultMap.put("isSuccess", Boolean.valueOf(false));
     }
     resultMap.put("bizResult", Integer.valueOf(result));
     return resultMap;
   }

   public int create(JSONObject transOrderObj) throws NoSuchMethodException {
     TransOrder transOrder = (TransOrder)BeanConvertUtils.map2Bean((Map)transOrderObj, TransOrder.class);
     int result = baseCreateTransOrder(transOrder);
     return result;
   }

   public int create(TransOrder transOrder) {
     int result = baseCreateTransOrder(transOrder);
     return result;
   }

   public void sendTransNotify(String transOrderId, String channelName) {
     JSONObject object = new JSONObject();
     object.put("transOrderId", transOrderId);
     object.put("channelName", channelName);
     this.transOrderService.sendTransNotify(object);
   }


   public CommonResult<Result> doAliTransReq(String tradeType, TransOrder transOrder, String resKey) throws NoSuchMethodException {
     Map<? extends String, ?> result = this.payChannel4AliService.doAliTransReq(transOrder);
     System.out.println("do Ali trans result=" + result);
     Boolean s = (Boolean)result.get("isSuccess");
     if (!s.booleanValue()) {
       return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("SUCCESS", "", "FAIL", "0111", "调用支付宝转账失败"), resKey);
     }
     Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
     map.putAll(result);
     return HLPayUtil.makeRetData(map, resKey);
   }


   public CommonResult<Result> getAliTransReq(String tradeType, TransOrder transOrder, String resKey) throws NoSuchMethodException {
     Result result = this.payChannel4AliService.getAliTransReq(transOrder);
     System.out.println("get Ali trans result=" + result);
     System.out.println("do Ali trans result=" + result);
     Boolean s = Boolean.valueOf(result.isSuccess());

     if (!s.booleanValue()) {
       return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("SUCCESS", "", "FAIL", "0111", "查询支付宝转账失败"), resKey);
     }
     Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
     map.putAll(BeanConvertUtils.bean2Map(result.getBizResult()));
     return HLPayUtil.makeRetData(map, resKey);
   }


   public CommonResult<Result> doWxTransReq(String tradeType, TransOrder transOrder, String resKey) {
     Result result = this.payChannel4WxService.doWxTransReq(transOrder);
     System.out.println("do Wx trans result=" + result);
     Boolean s = Boolean.valueOf(result.isSuccess());
     if (!s.booleanValue()) {
       return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("SUCCESS", "", "FAIL", "0111", "调用微信转账失败"), resKey);
     }
     Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
     map.putAll(BeanConvertUtils.bean2Map(result.getBizResult()));
     return HLPayUtil.makeRetData(map, resKey);
   }


   public CommonResult<Result> getWxTransReq(String tradeType, TransOrder transOrder, String resKey) {
     Result result = this.payChannel4WxService.getWxTransReq(transOrder);
     System.out.println("get Wx trans result=" + result);
     Boolean s = Boolean.valueOf(result.isSuccess());
     if (!s.booleanValue()) {
       return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("SUCCESS", "", "FAIL", "0111", "查询微信转账失败"), resKey);
     }
     Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
     map.putAll(BeanConvertUtils.bean2Map(result.getBizResult()));
     return HLPayUtil.makeRetData(map, resKey);
   }
 }





