package org.xxpay.shop.payFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@FeignClient(value = "payment-service",configuration = {})
public interface Notify4UnionPayAPI {
	/**
	 * 银联支付（统一下单接口）后台通知响应
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/notify/pay/unionPayNotifyRes")
	@ResponseBody
	String unionPayNotifyRes(HttpServletRequest request) throws ServletException, IOException;
}
