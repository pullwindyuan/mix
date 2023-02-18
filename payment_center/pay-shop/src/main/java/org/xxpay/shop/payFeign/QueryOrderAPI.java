package org.xxpay.shop.payFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 支付订单查询
 * @author dingzhiwei jmdhappy@126.com
 * @date 2017-08-31
 * @version V1.0
 * @Copyright: www.xxpay.org
 */
@FeignClient(value = "payment-service",configuration = {})
public interface QueryOrderAPI {
    /**
     * 查询支付订单接口:
     * 1)先验证接口参数以及签名信息
     * 2)根据参数查询订单
     * 3)返回订单数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/pay/query_order")
    String queryPayOrder(@RequestParam("params") String params);


    /**
     * 查询退款订单接口:
     * 1)先验证接口参数以及签名信息
     * 2)根据参数查询订单
     * 3)返回订单数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/pay/query_refund_order")
    String queryRefundOrder(@RequestParam("params") String params);
}
