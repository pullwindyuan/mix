package org.xxpay.shop.payFeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 转账订单
 * @author dingzhiwei jmdhappy@126.com
 * @date 2017-10-30
 * @version V1.0
 * @Copyright: www.xxpay.org
 */
@FeignClient(value = "pay-mgr-dev")
public interface TransOrderMgr {
    /**
     * 统一转账接口:
     * 1)先验证接口参数以及签名信息
     * 2)验证通过创建支付订单
     * 3)根据商户选择渠道,调用支付服务进行下单
     * 4)返回下单数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/trans_order/")
    String getList(@RequestParam("order") String order, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize);
}
