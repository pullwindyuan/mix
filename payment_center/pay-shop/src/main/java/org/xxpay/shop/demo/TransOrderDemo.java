package org.xxpay.shop.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.xxpay.common.constant.PayConstant;
import org.xxpay.common.util.PayDigestUtil;
import org.xxpay.common.util.XXPayUtil;
import org.xxpay.shop.payFeign.TransOrderMgr;
import org.xxpay.shop.util.HttpClient;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingzhiwei on 17/10/30.
 */
public class TransOrderDemo {

    // 商户ID
    static final String mchId = "10000000";//20001223,20001245
    // 加签key
    static final String reqKey = "kfywnsjuw474651189kjsujsng42547852kuf2r745de14w4x4rt4de87w52";
    // 验签key
    static final String repKey = "fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764";

    //static final String baseUrl = "http://api.xxpay.org/api";
    static final String baseUrl = "http://192.168.1.222:3020/";
    static final String notifyUrl = "http://localhost.com:8081/goods/notify_test?rt=success"; // 本地环境测试,可到ngrok.cc网站注册

    //================正式配置======================
//    @Value("${pay.config.mchId}")
//    private  static String mchId = "10000003";
//
//    @Value("${pay.config.reqKey}")
//    private  static String reqKey = "oisfjgou589080qu490jOPjgOPIUJHT8Jiopajegklsjhwpoui-09u-079";
//
//    @Value("${pay.config.resKey}")
//    private  static String repKey = "kljdfghkojht895985056jifuUI4950505656IPOUOIupoirut289hioyhp";
//
//    @Value("${pay.config.baseUrl}")
//    private  static String baseUrl = "https://gate.bibi321.com/api/open";
//
//    @Value("${pay.config.notifyUrl}")
//    private  static String notifyUrl = "https://shop.bibi321.com/payNotify";

    public static void main(String[] args) {
    	awardByTrans("2acacd2bd0c641fd974fddc5875f5910","2019-01-19 16:20:00");
    	//transOrderExchangeTest();
        //transOrderRechargeTest();
        //doTransOrderTest();
        //doTransOrderDemo();
        //transOrderWithdrawTest();
       // doTransOrderWithdrawTest(transOrderWithdrawTest());
        //transOrderAllTest();

        //transOrderAllDemo();
        //transOrderTest();//测试
        //quryPayOrderTest("1494774484058", "P0020170910211048000001");
    }

    // 统一下单
    static String transOrderTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", System.currentTimeMillis());     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", PayConstant.PAY_CHANNEL_GUSD_RECARGE);
        paramMap.put("amount", "57394867239067");                                  // 转行金额,单位分
        paramMap.put("currency", "gusd");                            // 币种, cny-人民币 bd-比豆
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "DH");
        paramMap.put("body", "比豆兑换HTL");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        //扩展参数1 在兑换HTL的时候用于表示比豆消耗数量
        paramMap.put("param1", "100");                                 // 扩展参数1
        // 扩展参数2 在奖励发放，异步分账支付的时候作为参与分账的用户信息
        paramMap.put("param2", "");                                 // 扩展参数2
        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");
        paramMap.put("UserName", "袁志刚");
        //平时作为备注，充值和提取时作为用户的token钱包地址
        paramMap.put("remarkInfo", "比豆奖励转账");

        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

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
                return   "success";
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return "fail";
    }

    static String awardByTrans(String userId,String beginTime) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", System.currentTimeMillis());     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", PayConstant.PAY_CHANNEL_AWARD_HTL);
        paramMap.put("amount", "5");                                  // 转行金额,单位个
        paramMap.put("currency", "htl");                            // 币种, cny-人民币 bd-比豆 htl
        paramMap.put("clientIp", "127.0.0.1");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "商城购物奖励测试");
        paramMap.put("body", "商城购物奖励测试");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL

        JSONObject productInfo = new JSONObject();
        productInfo.put("code", "GO");
        productInfo.put("productName", "商城购物");
        productInfo.put("begin_time", beginTime);
        paramMap.put("param1", productInfo.toJSONString());                                 // 扩展参数1, 记录了比豆分配参数                                 // 扩展参数1, 记录了比豆分配参数

        JSONArray userList = new JSONArray();

        JSONObject user = new JSONObject();
        user.put("userId", userId);
        user.put("phone", userId);
        user.put("amount", "3");
        user.put("comment", "商城购物" + ":" + "GO");
        userList.add(user);

        user = new JSONObject();
        user.put("userId", "af365ddad6094d36af40a08e6487ee6a");
        user.put("phone", "af365ddad6094d36af40a08e6487ee6a");
        user.put("amount", "2");
        user.put("comment", "商城购物推荐" + ":" + "GOT");
        userList.add(user);

        paramMap.put("param2", userList.toJSONString());                                 // 扩展参数2, 其他扩展信息
        paramMap.put("channelUser", userId);
        paramMap.put("UserName", "谁谁谁");
        paramMap.put("remarkInfo", "商城购物");

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
                return   "success";
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return "fail";
    }

    // 统一下单,测试临时提供给前端的调用接口
    static String transOrderAllDemo() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("amount", "10");                                  // 转行金额
        paramMap.put("currency", "htl"); // currency取值范围：bd-比豆； htl-乐旅币； eth-以太坊
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
//        paramMap.put("subject", "DH");
//        paramMap.put("body", "比豆兑换HTL");
        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");//用户userId
        paramMap.put("remarkInfo", "2986720ui34896uy9ghaijghijkbjkbh");//钱包地址,提取和充值时为必填,其他情况作为备足信息存在
        paramMap.put("action", "EXCHANGE");//action取值范围：RECHARGE:充值； WITHDRAW:提取； EXCHANGE:兑换；
        paramMap.put("param1", "1000");//EXCHANGE时用于传递需要扣除的比都数量,其他情况内容自定

        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心转账接口,请求数据:" + reqData);
        String url = baseUrl + "/trans?";
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
                return   "success";
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return "fail";
    }

    // 统一下单,测试正式开放给卡包的接口
    static String transOrderAllTest() {
        JSONObject paramMap = new JSONObject();
        String mchTransNo = String.valueOf(System.currentTimeMillis());
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("amount", "10");                                  // 转行金额
        paramMap.put("currency", "htl"); // currency取值范围：bd-比豆； htl-乐旅币； eth-以太坊
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("mchTransNo", mchTransNo);     // 商户订单号
        paramMap.put("subject", "DH");
        paramMap.put("body", "比豆兑换HTL");
        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");//用户userId
        paramMap.put("remarkInfo", "2986720ui34896uy9ghaijghijkbjkbh");//钱包地址,提取和充值时为必填,其他情况作为备足信息存在
        paramMap.put("action", "EXCHANGE");//action取值范围：RECHARGE:充值； WITHDRAW:提取； EXCHANGE:兑换； AWARD:奖励
        paramMap.put("param1", "1000");//EXCHANGE时用于传递需要扣除的比都数量,其他情况内容自定
        //paramMap.put("param1", productInfo.toJSONString());                                 // AWARD时传递奖励的产品信息
        //paramMap.put("param2", userList.toJSONString());                                 // AWARD时传递奖励的数量和奖励用户信息,和分账的信息格式一致,支持多用户同时奖励
        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心转账接口,请求数据:" + reqData);
        String url = baseUrl + "/trans.do?";
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
                return   mchTransNo;
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return mchTransNo;
    }

    // 统一下单,测试正式开放给卡包的接口
    static String transOrderWithdrawTest() {
        JSONObject paramMap = new JSONObject();
        String mchTransNo = String.valueOf(System.currentTimeMillis());
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("amount", "10");                                  // 转行金额
        paramMap.put("currency", "htl"); // currency取值范围：bd-比豆； htl-乐旅币； eth-以太坊
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("mchTransNo", mchTransNo);     // 商户订单号
        paramMap.put("subject", "提现HTL");
        paramMap.put("body", "提现HTL");
        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");//用户userId
        paramMap.put("remarkInfo", "2986720ui34896uy9ghaijghijkbjkbh");//钱包地址,提取和充值时为必填,其他情况作为备足信息存在
        paramMap.put("action", "WITHDRAW");//action取值范围：RECHARGE:充值； WITHDRAW:提取； EXCHANGE:兑换；
        paramMap.put("param1", "5");//EXCHANGE时用于传递需要扣除的比都数量,提币时用于标准手续费

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心转账接口,请求数据:" + reqData);
        String url = baseUrl + "/trans.do?";
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
                System.out.println("=========支付中心提现验签成功=========");
                return   mchTransNo;
            }else {
                System.err.println("=========支付中心提现验签失败=========");
                return "fail";
            }
        }
        return mchTransNo;
    }

    // 执行提现转账,测试正式开放给卡包的接口
    static String doTransOrderWithdrawTest(String mchTransNo) {
        JSONObject paramMap = new JSONObject();
        //String mchTransNo = String.valueOf(System.currentTimeMillis());
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", mchTransNo);     // 商户订单号

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心执行提现接口,请求数据:" + reqData);
        String url = baseUrl + "/trans/do?";
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
                System.out.println("=========支付中心执行提现验签成功=========");
                return   mchTransNo;
            }else {
                System.err.println("=========支付中心执行提现验签失败=========");
                return "fail";
            }
        }
        return mchTransNo;
    }

    // 统一下单
    static String transOrderRechargeTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", System.currentTimeMillis());     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("amount", "1000");                                  // 转行金额,单位分
        paramMap.put("currency", "htl");                            // 币种, cny-人民币 bd-比豆
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");
        paramMap.put("UserName", "袁志刚");
        paramMap.put("remarkInfo", "2986720ui34896uy9ghaijghijkbjkbh");

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心转账接口,请求数据:" + reqData);
        String url = baseUrl + "/recharge/create?";
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
                return   "success";
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return "fail";
    }

    // 统一下单
    static String transOrderWithdrawDemoTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", System.currentTimeMillis());     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("amount", "1000");                                  // 转行金额,单位分
        paramMap.put("currency", "htl");                            // 币种, cny-人民币 bd-比豆
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");
        paramMap.put("UserName", "袁志刚");
        paramMap.put("remarkInfo", "2986720ui34896uy9ghaijghijkbjkbh");

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心转账接口,请求数据:" + reqData);
        String url = baseUrl + "/withdraw/create?";
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
                return   "success";
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

    // 执行转账操作
    static String doTransOrderDemo() {
        JSONObject paramMap = new JSONObject();
        String url = baseUrl + "/trans/doDemo?";
        Map paramsMap = new HashMap();
        paramMap.put("mchTransNo", "RECHARGET442232842417498576572357818338");     // 商户订单号
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


    // 统一下单
    static String transOrderExchangeTest() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchTransNo", System.currentTimeMillis());     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("amount", "1000");                                  // 转行金额,单位分
        paramMap.put("currency", "htl");                            // 币种, cny-人民币 bd-比豆
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("channelUser", "2acacd2bd0c641fd974fddc5875f5910");
        paramMap.put("UserName", "袁志刚");
        paramMap.put("remarkInfo", "2986720ui34896uy9ghaijghijkbjkbh");

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心转账接口,请求数据:" + reqData);
        String url = baseUrl + "/exchange?";
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
                return   "success";
            }else {
                System.err.println("=========支付中心转账验签失败=========");
                return "fail";
            }
        }
        return "fail";
    }

    static String quryPayOrderTest(String mchOrderNo, String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", mchOrderNo);                     // 商户订单号
        paramMap.put("payOrderId", payOrderId);                     // 支付订单号
        paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心查单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/query_order?";
        String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心查单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, repKey, "sign", "payParams");
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
