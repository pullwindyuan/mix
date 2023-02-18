package org.xxpay.shop.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xxpay.common.util.PayDigestUtil;
import org.xxpay.common.util.XXPayUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RestController
public class RefundOrderController {
	// 商户ID
    static final String mchId = "10000000";//20001223,20001245
    // 加签key
    static final String reqKey = "kfywnsjuw474651189kjsujsng42547852kuf2r745de14w4x4rt4de87w52";
    // 验签key
    static final String repKey = "fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764";

    //static final String baseUrl = "http://api.xxpay.org/api";
    static final String baseUrl = "http://luosheng.bibi321.com";
    static final String notifyUrl = "http://localhost.com:8081/goods/notify_test?rt=success"; // 本地环境测试,可到ngrok.cc网站注册
    
    @RequestMapping(value = "/api/refund/createOrder", method = RequestMethod.GET)
    public String refundOrderTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchRefundNo", "REFUND" + System.currentTimeMillis());     // 商户订单号
        paramMap.put("mchOrderNo", "G20180508154333000000");     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", "ALIPAY_WAP");
        paramMap.put("amount", 1);  // 退款金额
        paramMap.put("currency", "cny");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "refund");
        paramMap.put("body", "refund");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        paramMap.put("param1", "");                                 // 扩展参数1
        paramMap.put("param2", "");                                 // 扩展参数2
        paramMap.put("channelUser", "pullwind");
        paramMap.put("payOrderId", "443437813594062848");


        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心退款接口,请求数据:" + reqData);
        String url = baseUrl + "/refund/create_order?";
        String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心退款接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, repKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心退款验签成功=========");
            }else {
                System.err.println("=========支付中心退款验签失败=========");
                return null;
            }
        }
       // return retMap.get("transOrderId")+"";
        return "success";
    }
	
}
