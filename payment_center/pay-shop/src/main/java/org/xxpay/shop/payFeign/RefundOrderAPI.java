package org.xxpay.shop.payFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * @Description: 转账订单
 * @author dingzhiwei jmdhappy@126.com
 * @date 2017-10-30
 * @version V1.0
 * @Copyright: www.xxpay.org
 */
@FeignClient(value = "payment-service",configuration = {})
public interface RefundOrderAPI {
    /**
     * 统一异步退款接口:
     * 1)先验证接口参数以及签名信息
     * 2)验证通过创建支付订单
     * 3)根据商户选择渠道,调用支付服务进行下单
     * 4)返回下单数据
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/refund/queue")
    public String payOrder(@RequestParam String params);
    /**
     * 统一退款接口:
     * 1)先验证接口参数以及签名信息
     * 2)验证通过创建支付订单
     * 3)根据商户选择渠道,调用支付服务进行下单
     * 4)返回下单数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/refund/create_order")
    public String payOrderWork(@RequestParam String params);
}
