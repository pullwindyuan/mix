package org.xxpay.shop.payFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * java类简单作用描述
 *
 * @ProjectName: payment_center_cloud
 * @Package: com.pay.client.api
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/3/14 14:36
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/3/14 14:36
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@FeignClient(value = "payment-service",configuration = {})
public interface NotifyAPI {
    /**
     * 支付宝移动支付后台通知响应
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/notify/pay/aliPayNotifyRes.htm")
    @ResponseBody
    String aliPayNotifyRes(HttpServletRequest request) throws ServletException, IOException;
    /**
     * 银联支付（统一下单接口）后台通知响应
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/notify/pay/unionPayNotifyRes")
    @ResponseBody
    String unionPayNotifyRes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
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
