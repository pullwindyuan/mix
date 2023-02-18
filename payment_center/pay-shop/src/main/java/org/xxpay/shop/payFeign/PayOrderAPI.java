package org.xxpay.shop.payFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 支付订单,包括:统一下单,订单查询,补单等接口
 * @author dingzhiwei jmdhappy@126.com
 * @date 2017-07-05
 * @version V1.0
 * @Copyright: www.xxpay.org
 */
@FeignClient(value = "payment-service",configuration = {})
public interface PayOrderAPI {
    /**
     * 统一下单接口:
     * 1)先验证接口参数以及签名信息
     * 2)验证通过创建支付订单
     * 3)根据商户选择渠道,调用支付服务进行下单
     * 4)返回下单数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/pay/create_order")
    String payOrder(@RequestParam("params") String params);

    /**
     * 查询支付订单接口:
     * 1)先验证接口参数以及签名信息
     * 2)根据参数查询订单
     * 3)返回订单数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/pay/repay")
    String queryAndPayOrder(@RequestParam("params") String params);
}
