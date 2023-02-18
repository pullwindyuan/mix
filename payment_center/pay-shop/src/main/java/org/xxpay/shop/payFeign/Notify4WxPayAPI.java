package org.xxpay.shop.payFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description: 接收处理微信通知
 * @author dingzhiwei jmdhappy@126.com
 * @date 2017-07-05
 * @version V1.0
 * @Copyright: www.xxpay.org
 */
@FeignClient(value = "payment-service",configuration = {})
public interface Notify4WxPayAPI {
	/**
	 * 微信支付(统一下单接口)后台通知响应
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
     */
	@RequestMapping("/notify/pay/wxPayNotifyRes.htm")
	@ResponseBody
	String wxPayNotifyRes(HttpServletRequest request) throws ServletException, IOException;

}
