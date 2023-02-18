package org.xxpay.shop.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xxpay.common.constant.PayConstant;
import org.xxpay.common.util.PayDigestUtil;
import org.xxpay.common.util.SnowflakeIdWorker;
import org.xxpay.common.util.XXPayUtil;
import org.xxpay.shop.util.HttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by dingzhiwei on 16/5/5.
 */
public class PayOrderDemo {

    // 商户ID
    static final String mchId = "10000000";//20001223,20001245
    // 加签key
    static final String reqKey = "kfywnsjuw474651189kjsujsng42547852kuf2r745de14w4x4rt4de87w52";
    // 验签key
    static final String resKey = "fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764";

    @Value("${pay.config.baseUrl}")
    private  static String baseUrl = "https://ms.bibi321.com/api/payment/api";

//    // 商户ID
//    static final String mchId = "10000002";//20001223,20001245
//    // 加签key
//    static final String reqKey = "lserjoi859kskUTYOKLJ76j87jhuihjh";
//    // 验签key
//    static final String resKey = "klnmhburk7c7fkmrji587fjkhjuuuuyy";
//
//    @Value("${pay.config.baseUrl}")
//    private  static String baseUrl = "http://pay.bibitrip.com/api/open";

    @Value("${pay.config.notifyUrl}")
    private  static String notifyUrl = "https://ms.bibi321.com/api/payment/api/notify_test";
    //static final String notifyUrl = "http://www.baidu.com"; // 本地环境测试,可到ngrok.cc网站注册
    //static final String notifyUrl = "http://pay.bibi321.com/payNotify";//该地址也就是redirect_URI，这个地址必须是微信公众号的左侧边栏的》公众号设置->功能设置下的“网页授权域名”下的页面，不能带任何参数，任何端口号。
    //static final String notifyUrl = "http://pay.bibi321.com/notify_test";
    public static void main(String[] args) {
//        while(true) {
//            payOrderTest();
//            //让处理线程等待几秒钟
//            //int sleepTime = new Random().nextInt(3000);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        //rePay(mchId, "1534473148762", "", "", "");
        //quryPayOrderTest("D2018122609203070", "D2018122609203070");
        //payOrder();
        doPayOrderTest(payOrder());
        //payOrderTest();
    }
    // 统一下单
    static void payOrderTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        SnowflakeIdWorker idWorker0 = new SnowflakeIdWorker(21, 28);
        paramMap.put("mchOrderNo", Long.toString(idWorker0.nextId()));     // 商户订单号
        //paramMap.put("mchOrderNo", System.currentTimeMillis());     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        //UNION_PC、UNION_WAP
        paramMap.put("channelId", "WX_APP");
        paramMap.put("amount", 1);                                  // 支付金额,单位分
        paramMap.put("currency", "cny");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "支付测试");
        paramMap.put("body", "支付测试");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        paramMap.put("param1", "");                                 // 扩展参数1
        paramMap.put("param2", "");                                 // 扩展参数2
        paramMap.put("extra", "{\n" +
                "  \"productId\": \"120989823\",\n" +
                "  \"openId\": \"olnubwX4ymYu0vCZacRtLShuCQy8\",\n" +//注意：测试WX_JSAPI时需要预先通过其他途径获取到用户openID，这里填写的是我自己的
                "  \"sceneInfo\": {\n" +
                "    \"h5_info\": {\n" +
                "      \"type\": \"Wap\",\n" +
                "      \"wap_url\": \"http://localhost.com\",\n" +
                "      \"wap_name\": \"支付测试\"\n" +
                "    }\n" +
                "  }\n" +
                " ,\"discountable_amount\":\"0.00\"," + //面对面支付扫码参数：可打折金额 可打折金额+不可打折金额=总金额
                "  \"undiscountable_amount\":\"0.00\"," + //面对面支付扫码参数：不可打折金额
                "}");  // 附加参数

        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}
        paramMap.put("redirectUrl", "https://m.bibi321.com");                         // 重定向地址
        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String reqData = HttpClient.getRequestParamString(paramsMap, "UTF-8");
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/create_order?";
        String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心下单接口,响应数据:" + result);

    }

    // 统一下单
    static String payOrder() {
        JSONObject paramMap = new JSONObject();
        String mchOrderNo = String.valueOf(System.currentTimeMillis());
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", mchOrderNo);     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        //UNION_PC、UNION_WAP
        paramMap.put("channelId", PayConstant.PAY_CHANNEL_GUSD_PAY);
        paramMap.put("amount", "50");                                  // 支付金额,cny单位分, htl单位个, eth单位GWEI
        paramMap.put("currency", "gusd");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "支付测试");
        paramMap.put("body", "支付测试");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        paramMap.put("param1", "{\"code\":\"O\",\"productName\":\"食品\",\"begin_time\":\"2018-07-03 18:16:05\",\"user_id\":\"2acacd2bd0c641fd974fddc5875f5910\"}");
        //分账名单
        paramMap.put("param2", "[{\"userId\":\"dcf04ab1c28a472180cb61a0a9f093f2\",\"subOrderId\":\"354365487658756898700\",\"code\":\"SP\",\"amount\":\"30\",\"remarkInfo\":\"比比购月饼店\"},{\"userId\":\"B46968147C9741C6814B77B1B904DC97\",\"subOrderId\":\"252786546289476998\",\"code\":\"FZ\",\"amount\":\"20\",\"remarkInfo\":\"比比购服装店\"}]");
        paramMap.put("extra", "{\n" +
                "  \"productId\": \"120989823\",\n" +
                "  \"openId\": \"olnubwX4ymYu0vCZacRtLShuCQy8\",\n" +//注意：测试WX_JSAPI时需要预先通过其他途径获取到用户openID，这里填写的是我自己的
                "  \"sceneInfo\": {\n" +
                "    \"h5_info\": {\n" +
                "      \"type\": \"Wap\",\n" +
                "      \"wap_url\": \"http://localhost.com\",\n" +
                "      \"wap_name\": \"支付测试\"\n" +
                "    }\n" +
                "  }\n" +
                " ,\"discountable_amount\":\"0.00\"," + //面对面支付扫码参数：可打折金额 可打折金额+不可打折金额=总金额
                "  \"undiscountable_amount\":\"0.00\"," + //面对面支付扫码参数：不可打折金额
                "}");  // 附加参数

        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}
        paramMap.put("redirectUrl", "http://m.bibi321.com");                         // 重定向地址
        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String reqData = HttpClient.getRequestParamString(paramsMap, "UTF-8");
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/create_order?";
        String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心下单接口,响应数据:" + result);
        //判断返回结果是否是html文本
        if (result.indexOf("<html>", 0) >= 0) {
            return "SUCCESS";
        } else {
            Map retMap = JSON.parseObject(result);
            //amount=50&body=支付测试&channelAccount=600200000000000000&channelId=HL_GUSD_ASYN_PAY&channelMchId=30080000&clientIp=211.94.116.218&createTime=Mon Jan 14 11:15:26 CST 2019&currency=gusd&device=WEB&isSuccess=true&mchId=10000000&mchOrderNo=1547435720645&notifyCount=0&notifyUrl=http://shop.bibi321.com/notify_test&payOrderId=1547435720645&paySuccTime=1547435721874&resCode=SUCCESS&retCode=SUCCESS&status=2&subject=支付测试&updateTime=Mon Jan 14 11:15:26 CST 2019&key=fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764
            //amount=50&body=支付测试&channelAccount=600200000000000000&channelId=HL_GUSD_ASYN_PAY&channelMchId=30080000&clientIp=211.94.116.218&createTime=1547435726000&currency=gusd&device=WEB&isSuccess=true&mchId=10000000&mchOrderNo=1547435720645&notifyCount=0&notifyUrl=http://shop.bibi321.com/notify_test&payOrderId=1547435720645&paySuccTime=1547435721874&resCode=SUCCESS&retCode=SUCCESS&status=2&subject=支付测试&updateTime=1547435726000&key=fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764
            if ("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
                // 验签
                String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
                String retSign = (String) retMap.get("sign");
                if (checkSign.equals(retSign)) {
                    System.out.println("=========下单验签成功=========");
                } else {
                    System.err.println("=========下单验签失败=========");
                    return null;
                }
            }
            return mchOrderNo;
        }

    }

    // 执行支付
    static String doPayOrderTest(String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", payOrderId);     // 商户订单号

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String reqData = HttpClient.getRequestParamString(paramsMap, "UTF-8");
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/do?";
        String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心下单接口,响应数据:" + result);
        //判断返回结果是否是html文本
        if (result.indexOf("<html>", 0) >= 0) {
            return "SUCCESS";
        } else {
            Map retMap = JSON.parseObject(result);
            if ("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
                // 验签
                String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
                String retSign = (String) retMap.get("sign");
                if (checkSign.equals(retSign)) {
                    System.out.println("=========下单验签成功=========");
                } else {
                    System.err.println("=========下单验签失败=========");
                    return null;
                }
            }
            doSettleOrderTest(payOrderId);
            return retMap.get("payOrderId") + "";
        }
    }

    // 分账
    static String doSettleOrderTest(String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", payOrderId);     // 商户订单号
        //本次分账名单
        paramMap.put("param2", "[{\"userId\":\"dcf04ab1c28a472180cb61a0a9f093f2\",\"subOrderId\":\"354365487658756898700\",\"code\":\"SP\",\"amount\":\"30\",\"remarkInfo\":\"比比购月饼店\"},{\"userId\":\"B46968147C9741C6814B77B1B904DC97\",\"subOrderId\":\"252786546289476998\",\"code\":\"FZ\",\"amount\":\"20\",\"remarkInfo\":\"比比购服装店\"}]");

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String reqData = HttpClient.getRequestParamString(paramsMap, "UTF-8");
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/settle?";
        String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心下单接口,响应数据:" + result);
        //判断返回结果是否是html文本
        if (result.indexOf("<html>", 0) >= 0) {
            return "SUCCESS";
        } else {
            Map retMap = JSON.parseObject(result);
            if ("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
                // 验签
                String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
                String retSign = (String) retMap.get("sign");
                if (checkSign.equals(retSign)) {
                    System.out.println("=========下单验签成功=========");
                } else {
                    System.err.println("=========下单验签失败=========");
                    return null;
                }
            }
            return retMap.get("payOrderId") + "";
        }

    }


    /*
* 支付失败的订单按原路径重新发起支付，亦可通过channelId来指定重新支付的渠道
* mchId：商户ID，商户在接入支付中心时由支付中心分配的一个唯一ID，必填
* mchOrderNo商户订单号（如商户自定义的商品订单号），商户自己管理，必填
* payOrderId：支付中心订单号（下单成功后返回的订单号），可选，建议都填写完整
* channelId：支付渠道选择参数，可选，如果参数为空，表示使用订单原支付渠道支付
* redirct：业务重定向地址，可选，建议都填写完整
* */
    /*
    * 支付失败的商品或者订单按原路径重新发起支付
    * payOrderId和redirct为可选项
    * */
    @RequestMapping(value = "/rePay", method = RequestMethod.GET)
    @ResponseBody
    public static String rePay(String mchId, String mchOrderNo, String payOrderId, String channelId, String redirect) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", verifyNotBlank(mchOrderNo));                     // 商户订单号
        paramMap.put("payOrderId", verifyNotBlank(payOrderId));                     // 支付订单号
        paramMap.put("channelId", channelId);                     // 支付渠道，如果该参数为空，就默认使用原支付路径支付
        paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统
        paramMap.put("redirectUrl", verifyNotBlank(redirect));//业务重定向地址

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        //String reqData = "params=" + paramMap.toJSONString();
        //System.out.println("请求支付中心查单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/repay?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心查单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if(org.xxpay.common.constant.PayConstant.PAY_CHANNEL_ALIPAY_MOBILE.equalsIgnoreCase(channelId)) {
            return (String) retMap.get("payParams");
        }
        return (String) retMap.get("payUrl");
    }
    static String quryPayOrderTest(String mchOrderNo, String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", verifyNotBlank(mchOrderNo));                     // 商户订单号
        paramMap.put("payOrderId", verifyNotBlank(payOrderId));                     // 支付订单号
        paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        //String reqData = "params=" + paramMap.toJSONString();
        //System.out.println("请求支付中心查单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/query_order?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心查单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心查单验签成功=========");
            }else {
                System.err.println("=========支付中心查单验签失败=========");
                return null;
            }
        }
        return retMap.get("payOrderId")+"";
    }

    private static String verifyNotBlank(String url) {
        //不允许传入的参数为null，否则签名会验证失败
        String temp = "";
        if(StringUtils.isNotBlank(url)) {
            temp = url;
        }
        return temp;                         // 重定向地址
    }
}
