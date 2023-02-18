package org.hlpay.base.payFeign;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("payment-service")
public interface Notify4WxPayAPI {
  @RequestMapping({"/notify/pay/wxPayNotifyRes.htm"})
  @ResponseBody
  String wxPayNotifyRes(HttpServletRequest paramHttpServletRequest) throws ServletException, IOException;
}

