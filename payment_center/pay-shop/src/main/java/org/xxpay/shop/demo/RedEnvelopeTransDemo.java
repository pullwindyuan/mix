package org.xxpay.shop.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.xxpay.common.constant.PayConstant;
import org.xxpay.common.util.PayDigestUtil;
import org.xxpay.common.util.XXPayUtil;
import org.xxpay.shop.util.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingzhiwei on 17/10/30.
 */
public class RedEnvelopeTransDemo {

    // 商户ID
    static final String mchId = "10000000";//20001223,20001245
    // 加签key
    static final String reqKey = "kfywnsjuw474651189kjsujsng42547852kuf2r745de14w4x4rt4de87w52";
    // 验签key
    static final String repKey = "fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764";

    //static final String baseUrl = "http://api.xxpay.org/api";
    static final String baseUrl = "https://ms.bibi321.com/api/payment/api";
    static final String notifyUrl = "http://localhost.com:8081/goods/notify_test?rt=success"; // 本地环境测试,可到ngrok.cc网站注册

//    // 商户ID
//    static final String mchId = "10000001";//20001223,20001245
//    // 加签key
//    static final String reqKey = "klsghioergnjkfjk57879w3jhsdfkjhnji34784jihaUuiUijj";
//    // 验签key
//    static final String repKey = "dfseiopghsldngskl85956068989KHdgousi4yjklzndfgkljh";
//
//    //static final String baseUrl = "http://api.xxpay.org/api";
//    static final String baseUrl = "https://gate.bibi321.com/api/open";
//    static final String notifyUrl = "http://localhost.com:8081/goods/notify_test?rt=success"; // 本地环境测试,可到ngrok.cc网站注册

    public static void main(String[] args) {
        //transOrderExchangeTest();
        //transOrderRechargeTest();
        //doTransOrderTest();
        //doTransOrderDemo();
        //transOrderWithdrawTest();
        doTransOrderTest(redEnvelopeOrderTest());
        //transOrderTest();
        //transOrderAllTest();

        //transOrderAllDemo();
        //transOrderTest();
        //quryPayOrderTest("1494774484058", "P0020170910211048000001");
    }

    // 统一下单
    static String redEnvelopeOrderTest() {
        JSONObject paramMap = new JSONObject();
        String mchOrderNo = String.valueOf(System.currentTimeMillis());
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", mchOrderNo);     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", PayConstant.PAY_CHANNEL_TOKEN_RED_ENVELOPE);
        paramMap.put("amount", "2000");                                  // 转行金额,单位分
        paramMap.put("currency", "htl");                            // 币种, cny-人民币 bd-比豆
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "红包");
        paramMap.put("body", "恭喜发财");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL

        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");
        paramMap.put("UserName", "袁志刚");
        //平时作为备注，充值和提取时作为用户的token钱包地址
        paramMap.put("remarkInfo", "恭喜发财");
        //paramMap.put("param1", "{\"code\":\"O\",\"productName\":\"食品\",\"begin_time\":\"2018-07-03 18:16:05\",\"user_id\":\"2acacd2bd0c641fd974fddc5875f5910\"}");
        //分账名单，这里有参数表示是定额、定向红包。通过该参数来定义不同的红包，列表中有多少个数据就有多少个红包
        //1、userId为空，amount不为空表示非定向定额红包，也就是每个拆分的红包等额
        //2、没有该参数表示为随机红包
        //3、目前只支持定向定额红包
        paramMap.put("param2", "[{\"userId\":\"a6d60aa757934b85a8ee449b64276e9e\",\"subOrderId\":\"354365487658756898700\",\"phone\":\"18124023006\",\"amount\":\"2000\",\"comment\":\"恭喜发财\"}]");

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心转账接口,请求数据:" + reqData);
        String url = baseUrl + "/trans/create_order?";
        Map paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap, url);
        System.out.println("请求支付中心转账接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);

        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, repKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心转账验签成功=========");
                return   mchOrderNo;
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return "fail";
    }

    // 执行转账操作
    static String doTransOrderTest(String transOrderId) {
        System.out.println("transOrderId:" + transOrderId);
        JSONObject paramMap = new JSONObject();
        String url = baseUrl + "/trans/do?";
        Map paramsMap = new HashMap();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", transOrderId);     // 商户订单号
        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap, url);
        System.out.println("请求支付中心转账接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);

        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, repKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心转账验签成功=========");
                return   "success";
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return "fail";
    }

    // 统一下单:转账
    static String transOrderTest() {
        JSONObject paramMap = new JSONObject();
        String mchOrderNo = String.valueOf(System.currentTimeMillis());
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", mchOrderNo);     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", PayConstant.PAY_CHANNEL_TOKEN_TRANS);
        paramMap.put("amount", "1");                                  // 转行金额,单位分
        paramMap.put("currency", "htl");                            // 币种, cny-人民币 bd-比豆
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "通证转账");
        paramMap.put("body", "谢谢");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL

        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");
        paramMap.put("UserName", "袁志刚");
        //平时作为备注，充值和提取时作为用户的token钱包地址
        paramMap.put("remarkInfo", "谢谢");
        //paramMap.put("param1", "{\"code\":\"O\",\"productName\":\"食品\",\"begin_time\":\"2018-07-03 18:16:05\",\"user_id\":\"2acacd2bd0c641fd974fddc5875f5910\"}");
        //分账名单，这里有参数表示是定额、定向红包
        paramMap.put("param2", "[{\"userId\":\"3c110f2cf3c149cd84cc2dee014aa9be\",\"phone\":\"18124023006\",\"amount\":\"1\",\"comment\":\"谢谢\"}]");

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心转账接口,请求数据:" + reqData);
        String url = baseUrl + "/trans/create_order?";
        Map paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap, url);
        System.out.println("请求支付中心转账接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);

        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, repKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心转账验签成功=========");
                return   mchOrderNo;
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return "fail";
    }
}
