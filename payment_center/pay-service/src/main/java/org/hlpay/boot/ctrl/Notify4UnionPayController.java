 package org.hlpay.boot.ctrl


 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.Api;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.hlpay.base.channel.unionpay.sdk.AcpService;
 import org.hlpay.base.service.impl.NotifyPayServiceImpl;
 import org.hlpay.boot.ctrl.Notify4WxPayController;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.RedisUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;



 @Api(tags = {"银联支付"})
 @Controller
 public class Notify4UnionPayController
 {
   private static final MyLog _log = MyLog.getLog(Notify4WxPayController.class);



   @Autowired
   private RedisUtil redisUtil;


   @Autowired
   private NotifyPayServiceImpl notifyPayService;



   @RequestMapping({"/notify/pay/unionPayNotifyRes/{key}"})
   @ResponseBody
   public String unionPayNotifyRes(HttpServletRequest request, HttpServletResponse response, @PathVariable String key) throws ServletException, IOException {
     _log.info("====== 开始接收银联支付回调通知 ======", new Object[0]);
     _log.info("from:" + request.getRequestURL(), new Object[0]);

     if (!this.redisUtil.hasKey(key).booleanValue()) {
       return "fail";
     }
     String notifyRes = null;
     try {
       notifyRes = doUnionPayRes(request, response);
     } catch (Exception e) {
       e.printStackTrace();
     }
     if (notifyRes.equals("Success!")) {
       this.redisUtil.delete(key);
     }
     _log.info("响应给银联:{}", new Object[] { notifyRes });
     _log.info("====== 完成接收银联支付回调通知 ======", new Object[0]);
     return notifyRes;
   }

   public String doUnionPayRes(HttpServletRequest request, HttpServletResponse response) throws Exception {
     String logPrefix = "【银联支付回调通知】";

     _log.info("银联接收后台通知开始", new Object[0]);
     String encoding = request.getParameter("encoding");

     Map<String, String> reqParam = getAllRequestParam(request);

     _log.info("{}银联回调数据:", new Object[] { reqParam.toString() });
     Map<String, String> valideData = null;
     if (null != reqParam && !reqParam.isEmpty()) {
       Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
       valideData = new HashMap<>(reqParam.size());
       while (it.hasNext()) {
         Map.Entry<String, String> e = it.next();
         String key = e.getKey();
         String value = e.getValue();
         value = new String(value.getBytes(encoding), encoding);
         valideData.put(key, value);
       }
     }

     if (!AcpService.validate(valideData, encoding)) {
       _log.info("银联验证签名结果[失败].", new Object[0]);
     }
     JSONObject data = new JSONObject();
     data.putAll(valideData);
     return this.notifyPayService.handleUnionPayNotify(data, response);
   }


   public static Map<String, String> getAllRequestParam(HttpServletRequest request) {
     Map<String, String> params = new HashMap<>();
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
     _log.info(JSON.toJSONString(params), new Object[0]);
     return params;
   }
 }

