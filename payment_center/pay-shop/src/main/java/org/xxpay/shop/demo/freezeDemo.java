package org.xxpay.shop.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.xxpay.common.constant.PayConstant;
import org.xxpay.common.util.PayDigestUtil;
import org.xxpay.common.util.XXPayUtil;
import org.xxpay.shop.util.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingzhiwei on 17/10/30.
 */
public class freezeDemo {

//    // 商户ID
//    static final String mchId = "10000001";//20001223,20001245
//    // 加签key
//    static final String reqKey = "klsrkhkjjk54785khgdkjr589klmhoktjlkml";
//    // 验签key
//    static final String repKey = "jkki6of89505600e9y0e9i609u804050675p";
////static final String baseUrl = "http://api.xxpay.org/api";
//static final String baseUrl = "http://luosheng.bibi321.com/api";
//static final String notifyUrl = "http://localhost.com:8081/goods/notify_test?rt=success"; // 本地环境测试,可到ngrok.cc网站注册

//    @Value("${pay.config.mchId}")
//    private  static String mchId = "10000002";
//
//    @Value("${pay.config.reqKey}")
//    private  static String reqKey = "lserjoi859kskUTYOKLJ76j87jhuihjh";
//
//    @Value("${pay.config.resKey}")
//    private  static String resKey = "klnmhburk7c7fkmrji587fjkhjuuuuyy";
//
//    @Value("${pay.config.baseUrl}")
//    private  static String baseUrl = "http://pay.bibitrip.com/api/open";
//
//    @Value("${pay.config.notifyUrl}")
//    private  static String notifyUrl = "http://shop.bibi321.com/payNotify";

    @Value("${pay.config.mchId}")
    private  static String mchId = "10000000";

    @Value("${pay.config.reqKey}")
    private  static String reqKey = "kfywnsjuw474651189kjsujsng42547852kuf2r745de14w4x4rt4de87w52";

    @Value("${pay.config.resKey}")
    private  static String resKey = "fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764";

    @Value("${pay.config.baseUrl}")
    private  static String baseUrl = "http://ms.bibi321.com/api/payment/api";

    @Value("${pay.config.notifyUrl}")
    private  static String notifyUrl = "http://shop.bibi321.com/notify_test";

    //static final String notifyUrl = "http://shop.bibi321.com/payNotify"; // 本地环境测试,可到ngrok.cc网站注册

    public static void main(String[] args) {
        freezeTest();
        //refundOrderTest();
        //quryPayOrderTest("1494774484058", "P0020170910211048000001");
        //quryRefundOrderTest("20180529164846575", "20180529164846575");
    }

    // 异步退款创建订单
    static String freezeTest() {
        JSONObject paramMap = new JSONObject();
        String mchFreezeNo = "FREEZE" + System.currentTimeMillis();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchFreezeNo", mchFreezeNo);     // 商户退款订单号

        paramMap.put("amount", 3);  // 冻结金额
        paramMap.put("currency", "htl");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");
        paramMap.put("UserName", "袁志刚");
        paramMap.put("remarkInfo", "冻结测试");

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心冻结接口,请求数据:" + reqData);
        String url = baseUrl + "/freeze";
        //String url = baseUrl + "/refund/queue?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心冻结接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            unfreezeTest(mchFreezeNo);
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心冻结验签成功=========");
            }else {
                System.err.println("=========支付中心冻结验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }


    // 异步退款
    static String unfreezeTest(String mchFreezeNo) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchFreezeNo", mchFreezeNo);     // 商户退款订单号

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心解冻接口,请求数据:" + reqData);
        String url = baseUrl + "/unfreeze?";
        //String url = baseUrl + "/refund/queue?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心解冻接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心解冻验签成功=========");
            }else {
                System.err.println("=========支付中心解冻验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }
}
