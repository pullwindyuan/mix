package org.hlpay.boot.ctrl


 import java.io.IOException;
 import java.io.InputStream;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.io.IOUtils;
 import org.hlpay.base.service.impl.NotifyPayServiceImpl;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.bind.annotation.RestController;












 @RestController
 public class Notify4WxPayController
 {
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.ctrl.Notify4WxPayController.class);




   @Autowired
   private NotifyPayServiceImpl notifyPayService;




   @RequestMapping({"/notify/pay/wxPayNotifyRes.htm"})
   @ResponseBody
   public String wxPayNotifyRes(HttpServletRequest request) throws ServletException, IOException {
     _log.info("====== 开始接收微信支付回调通知 ======", new Object[0]);
     _log.info("from:" + request.getRequestURL(), new Object[0]);

     String notifyRes = doWxPayRes(request);
     _log.info("响应给微信:{}", new Object[] { notifyRes });
     _log.info("====== 完成接收微信支付回调通知 ======", new Object[0]);
     return notifyRes;
   }

   public String doWxPayRes(HttpServletRequest request) throws ServletException, IOException {
     String logPrefix = "【微信支付回调通知】";
     String xmlResult = IOUtils.toString((InputStream)request.getInputStream(), request.getCharacterEncoding());
     _log.info("{}通知请求数据:reqStr={}", new Object[] { logPrefix, xmlResult });
     return this.notifyPayService.handleWxPayNotify(xmlResult);
   }
 }





