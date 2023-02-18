package org.hlpay.base.payFeign;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("payment-service")
public interface NotifyAPI {
  @RequestMapping({"/notify/pay/aliPayNotifyRes.htm"})
  @ResponseBody
  String aliPayNotifyRes(HttpServletRequest paramHttpServletRequest) throws ServletException, IOException;
  
  @RequestMapping({"/notify/pay/unionPayNotifyRes"})
  @ResponseBody
  String unionPayNotifyRes(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException;
  
  @RequestMapping({"/notify/pay/wxPayNotifyRes.htm"})
  @ResponseBody
  String wxPayNotifyRes(HttpServletRequest paramHttpServletRequest) throws ServletException, IOException;
}

