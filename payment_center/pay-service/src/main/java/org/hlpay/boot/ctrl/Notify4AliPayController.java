 package org.hlpay.boot.ctrl


 import com.alibaba.fastjson.JSONObject;
 import java.util.Iterator;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.hlpay.base.service.impl.NotifyPayServiceImpl;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.bind.annotation.RestController;















 @RestController
 public class Notify4AliPayController
 {
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.ctrl.Notify4AliPayController.class);




   @Autowired
   private NotifyPayServiceImpl notifyPayService;




   @RequestMapping({"/notify/pay/aliPayNotifyRes.htm"})
   @ResponseBody
   public String aliPayNotifyRes(HttpServletRequest request) {
     _log.info("====== 开始接收支付宝支付回调通知 ======", new Object[0]);
     _log.info("from:" + request.getRequestURL(), new Object[0]);

     String notifyRes = null;
     try {
       notifyRes = doAliPayRes(request);
     } catch (Exception e) {
       e.printStackTrace();
     }
     _log.info("响应给支付宝:{}", new Object[] { notifyRes });
     _log.info("====== 完成接收支付宝支付回调通知 ======", new Object[0]);
     return notifyRes;
   }

   public String doAliPayRes(HttpServletRequest request) throws Exception {
     String logPrefix = "【支付宝支付回调通知】";

     JSONObject params = new JSONObject();
     Map requestParams = request.getParameterMap();
     for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
       String name = iter.next();
       String[] values = (String[])requestParams.get(name);
       String valueStr = "";
       for (int i = 0; i < values.length; i++)
       {
         valueStr = (i == values.length - 1) ? (valueStr + values[i]) : (valueStr + values[i] + ",");
       }


       params.put(name, valueStr);
     }
     _log.info("{}通知请求数据:reqStr={}", new Object[] { logPrefix, params.toJSONString() });
     if (params.isEmpty()) {
       _log.error("{}请求参数为空", new Object[] { logPrefix });
       return "fail";
     }
     return this.notifyPayService.handleAliPayNotify(params);
   }
 }

