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
public class RefundOrderDemo {

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
//================商城正式配置======================
//    @Value("${pay.config.mchId}")
//    private  static String mchId = "10000003";
//
//    @Value("${pay.config.reqKey}")
//    private  static String reqKey = "oisfjgou589080qu490jOPjgOPIUJHT8Jiopajegklsjhwpoui-09u-079";
//
//    @Value("${pay.config.resKey}")
//    private  static String resKey = "kljdfghkojht895985056jifuUI4950505656IPOUOIupoirut289hioyhp";
//
//    @Value("${pay.config.baseUrl}")
//    private  static String baseUrl = "https://gate.bibi321.com/api/open";
//
//    @Value("${pay.config.notifyUrl}")
//    private  static String notifyUrl = "https://shop.bibi321.com/payNotify";
//================测试配置======================
    @Value("${pay.config.mchId}")
    private  static String mchId = "10000000";

    @Value("${pay.config.reqKey}")
    private  static String reqKey = "kfywnsjuw474651189kjsujsng42547852kuf2r745de14w4x4rt4de87w52";

    @Value("${pay.config.resKey}")
    private  static String resKey = "fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764";

    @Value("${pay.config.baseUrl}")
    private  static String baseUrl = "https://ms.bibi321.com/api/payment/api";

    @Value("${pay.config.notifyUrl}")
    private  static String notifyUrl = "http://shop.bibi321.com/notify_test";

    //static final String notifyUrl = "http://shop.bibi321.com/payNotify"; // 本地环境测试,可到ngrok.cc网站注册

    public static void main(String[] args) {
        //refundOrderAsynTest();
        //refundOrderTest();
        refundTransOrderTest();
        //refundOrderCnyTest();
        //quryPayOrderTest("1494774484058", "P0020170910211048000001");
        //quryRefundOrderTest("20180529164846575", "20180529164846575");
    }

    // 异步退款创建订单
    static String refundOrderAsynTest() {
        JSONObject paramMap = new JSONObject();
        //String mchRefundNo = "REFUND" + System.currentTimeMillis();
        String mchRefundNo = "REFUND" + "1547603077666";
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchRefundNo", mchRefundNo);     // 商户退款订单号
        paramMap.put("mchOrderNo", "1547603077666");     // 商户订单号
        //paramMap.put("payOrderId", "1542974507314");
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        //paramMap.put("channelId", PayConstant.PAY_CHANNEL_HTL_ASYN_PAY);//WX_JSAPI
        paramMap.put("amount", "3000000000");  // 退款金额
        //paramMap.put("currency", "eth");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "异步退款测试");
        paramMap.put("body", "异步退款测试");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        //preFreeze: 表示是否需要进行冻结操作,
        //paramMap.put("preFreeze", false);                       // 是否需要冻结,该参数如果不传，默认为冻结
        //paramMap.put("param1", "{\"code\":\"o\",\"productName\":\"\\u5546\\u54c1\",\"begin_time\":1542090034,\"user_id\":\"9f28ce4e2f1d46199b45e5f38be9130b\"}");                                 // 扩展参数1
        //param2：差分退款参数，该参数指定了原支付订单中的部分分账订单退款，结构和异步分账支付的对应分账参数一致，如果本参数传入的分账信息再原订单中没有匹配的或者金额超限的都将会下单失败
        //paramMap.put("param2", "[{\"userId\":null,\"subOrderId\":2508573000110339,\"code\":\"FZ\",\"amount\":415,\"remarkInfo\":\"\\u5929\\u6c34\\u79e6\\u5b89\\u51b0\\u7cd6\\u5fc3\\u5bcc\\u58eb\"}]");
        paramMap.put("param1", "{\"code\":\"O\",\"productName\":\"食品\",\"begin_time\":\"2018-07-03 18:16:05\",\"user_id\":\"2acacd2bd0c641fd974fddc5875f5910\"}");
        //param2：差分退款参数，该参数指定了原支付订单中的部分分账订单退款，结构和异步分账支付的对应分账参数一致，如果本参数传入的分账信息再原订单中没有匹配的或者金额超限的都将会下单失败
        //注意：异步退款之支持一次退一个分账订单，不支持批量，应为多个无法确定订单唯一状态，有的商户可能会拒绝退款.
        //paramMap.put("param2", "[{\"userId\":\"dcf04ab1c28a472180cb61a0a9f093f2\",\"subOrderId\":\"89024657623578976578\",\"code\":\"SP\",\"amount\":\"3000000000\",\"remarkInfo\":\"比比购月饼店\"},{\"userId\":\"B46968147C9741C6814B77B1B904DC97\",\"subOrderId\":\"252786546289476998\",\"code\":\"FZ\",\"amount\":\"2000000000\",\"remarkInfo\":\"比比购服装店\"}]");
        paramMap.put("param2", "[{\"userId\":\"dcf04ab1c28a472180cb61a0a9f093f2\",\"subOrderId\":\"354365487658756898700\",\"code\":\"SP\",\"amount\":\"3000000000\",\"remarkInfo\":\"比比购月饼店\"}]");
        paramMap.put("channelUser", "袁志刚");


        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心退款接口,请求数据:" + reqData);
        String url = baseUrl + "/refund/asyn_create?";
        //String url = baseUrl + "/refund/queue?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心退款接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            refundOrderAsynDoTest(mchRefundNo);
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心退款验签成功=========");
            }else {
                System.err.println("=========支付中心退款验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }

    // 异步退款创建订单
    static String refundOrderAsynCreateTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchRefundNo", "REFUND" + System.currentTimeMillis());     // 商户退款订单号
        paramMap.put("mchOrderNo", "1535505314692");     // 商户订单号
        paramMap.put("payOrderId", "1535505314692");
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", PayConstant.PAY_CHANNEL_HTL_ASYN_PAY);//WX_JSAPI
        paramMap.put("amount", 3);  // 退款金额
        paramMap.put("currency", "htl");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "退款测试");
        paramMap.put("body", "退款测试");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        //preFreeze: 表示是否需要进行冻结操作,
        paramMap.put("preFreeze", false);                       // 是否需要冻结,该参数如果不传，默认为冻结
        paramMap.put("param1", "{\"code\":\"O\",\"begin_time\":\"2018-07-19 09:02:51\",\"user_id\":\"2acacd2bd0c641fd974fddc5875f5910\"}");                                 // 扩展参数1
        paramMap.put("param2", "[{\"userId\":\"dcf04ab1c28a472180cb61a0a9f093f2\",\"subOrderId\":\"89024657623578966754\",\"code\":\"SP\",\"amount\":\"2\",\"remarkInfo\":\"比比购月饼店\"},{\"userId\":\"B46968147C9741C6814B77B1B904DC97\",\"subOrderId\":\"252786546289476998\",\"code\":\"FZ\",\"amount\":\"1\",\"remarkInfo\":\"比比购服装店\"}]");
        paramMap.put("channelUser", "pullwind");


        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心退款接口,请求数据:" + reqData);
        String url = baseUrl + "/refund/asyn_create?";
        //String url = baseUrl + "/refund/queue?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心退款接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心退款验签成功=========");
            }else {
                System.err.println("=========支付中心退款验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }

    // 异步退款
    static String refundOrderAsynDoTest(String mchRefundNo) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchRefundNo", mchRefundNo);     // 商户退款订单号

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心退款接口,请求数据:" + reqData);
        String url = baseUrl + "/refund/asyn_do?";
        //String url = baseUrl + "/refund/queue?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心退款接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心退款验签成功=========");
            }else {
                System.err.println("=========支付中心退款验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }

    // 通证退款
    static String refundOrderTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        //paramMap.put("mchRefundNo", "REFUND" + System.currentTimeMillis());     // 商户退款订单号
        paramMap.put("mchRefundNo", "REFUND" + "19011212178036428451");     // 商户退款订单号
        paramMap.put("mchOrderNo", "19011212178036428451");     // 商户订单号
        //paramMap.put("payOrderId", "C15466699649262942");
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        //paramMap.put("channelId", PayConstant.PAY_CHANNEL_HTL_PAY);//WX_JSAPI
        paramMap.put("amount", 27990);  // 退款金额
        //paramMap.put("currency", "htl");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "商城退款");
        paramMap.put("body", "商城退款");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        //同步退款不需要冻结操作，以实时信息为准
        //paramMap.put("preFreeze", false);                       // 是否需要冻结,该参数如果不传，默认为冻结
        //paramMap.put("param1", "{\"code\":\"o\",\"productName\":\"\\u5546\\u54c1\",\"begin_time\":1546796388,\"user_id\":\"b82535a080034a9394ea443aa92e5125\"}");                                 // 扩展参数1
        //param2：差分退款参数，该参数指定了原支付订单中的部分分账订单退款，结构和异步分账支付的对应分账参数一致，如果本参数传入的分账信息再原订单中没有匹配的或者金额超限的都将会下单失败
        //同步退款由于没有冻结操作，以及商户异步确认的过程，所以能够坐到订单状态统一，可以支持一次退多个分账
        paramMap.put("param2", "");
        paramMap.put("channelUser", "商城用户");


        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心退款接口,请求数据:" + reqData);
        String url = baseUrl + "/refund/create_order?";
        //String url = baseUrl + "/refund/queue?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心退款接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心退款验签成功=========");
            }else {
                System.err.println("=========支付中心退款验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }

    // 通证奖励退款
    static String refundTransOrderTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        //paramMap.put("mchRefundNo", "REFUND" + System.currentTimeMillis());     // 商户退款订单号
        paramMap.put("mchRefundNo", "REFUND" + "1547798460189");     // 商户退款订单号
        paramMap.put("mchTransNo", "1547798460189");     // 商户订单号
        //paramMap.put("transOrderId", "1547693188652");
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        //paramMap.put("channelId", PayConstant.PAY_CHANNEL_AWARD_HTL);//WX_JSAPI
        paramMap.put("amount", 2);  // 退款金额
        //paramMap.put("currency", "htl");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "商城退款");
        paramMap.put("body", "商城退款");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        //同步退款不需要冻结操作，以实时信息为准
        //paramMap.put("preFreeze", false);                       // 是否需要冻结,该参数如果不传，默认为冻结
        paramMap.put("param1", "{\"code\":\"PPRZJL\",\"productName\":\"品牌入驻奖励\"}");                                 // 扩展参数1
        //param2：差分退款参数，该参数指定了原支付订单中的部分分账订单退款，结构和异步分账支付的对应分账参数一致，如果本参数传入的分账信息再原订单中没有匹配的或者金额超限的都将会下单失败
        //同步退款由于没有冻结操作，以及商户异步确认的过程，所以能够坐到订单状态统一，可以支持一次退多个分账
        paramMap.put("param2", "[{\"amount\":\"2\",\"phone\":\"2acacd2bd0c641fd974fddc5875f5910\",\"comment\":\"商城购物:GO\",\"userId\":\"2acacd2bd0c641fd974fddc5875f5910\"}]");
        paramMap.put("channelUser", "商城用户");


        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心退款接口,请求数据:" + reqData);
        String url = baseUrl + "/transRefund/create_order?";
        //String url = baseUrl + "/refund/queue?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心退款接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心退款验签成功=========");
            }else {
                System.err.println("=========支付中心退款验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }

    // 人民币退款
    static String refundOrderCnyTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        //paramMap.put("mchRefundNo", "REFUND" + System.currentTimeMillis());     // 商户退款订单号
        paramMap.put("mchRefundNo", "20181211160418693");     // 商户退款订单号
        paramMap.put("mchOrderNo", "NH20181211151711779");     // 商户订单号
        paramMap.put("payOrderId", "NH20181211151711779");
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", PayConstant.PAY_CHANNEL_ALIPAY_WAP);//WX_JSAPI
        paramMap.put("amount", 2800);  // 退款金额
        paramMap.put("currency", "cny");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "酒店退款");
        paramMap.put("body", "酒店退款");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        //同步退款不需要冻结操作，以实时信息为准
        //paramMap.put("preFreeze", false);                       // 是否需要冻结,该参数如果不传，默认为冻结
        paramMap.put("param1", "{\"code\":\"o\",\"productName\":\"\\u5546\\u54c1\",\"begin_time\":1545990169,\"user_id\":\"d94c14b7a9b04e36852e7505131a00f7\"}");                                 // 扩展参数1
        //param2：差分退款参数，该参数指定了原支付订单中的部分分账订单退款，结构和异步分账支付的对应分账参数一致，如果本参数传入的分账信息再原订单中没有匹配的或者金额超限的都将会下单失败
        //同步退款由于没有冻结操作，以及商户异步确认的过程，所以能够坐到订单状态统一，可以支持一次退多个分账
        paramMap.put("param2", "");
        paramMap.put("channelUser", "徐子剑");


        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心退款接口,请求数据:" + reqData);
        String url = baseUrl + "/refund/create_order?";
        //String url = baseUrl + "/refund/queue?";
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String result = HttpClient.post(paramsMap,url);
        //String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心退款接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心退款验签成功=========");
            }else {
                System.err.println("=========支付中心退款验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }
    static String quryRefundOrderTest(String mchOrderNo, String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchRefundNo", mchOrderNo);                     // 商户订单号
        paramMap.put("refundOrderId", payOrderId);                     // 支付订单号
        paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心查单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/query_refund_order?";
        String result = XXPayUtil.call4Post(url + reqData);
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

}
