package org.xxpay.shop.controller;

//package com.pay.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * java类简单作用描述
 *
 * @ProjectName: payment_center
 * @Package: org.xxpay.shop.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/3/28 14:35
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/3/28 14:35
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class payDemo {
    private final static MyLog _log = MyLog.getLog(payDemo.class);

    //深圳航旅，对应支付后台管理里中的商户ID（注意，这里不是微信支付里的商户ID，而是我们的自定义的商户，比如比比旅行需要接入支付中心，我们就需要为比比旅行创建一个商户）
    static final String mchId = "10000000";
    // 加签key，支付中心给商户创建的对应reqKey，请接入商户妥善保存。
    static final String reqKey = "kfywnsjuw474651189kjsujsng42547852kuf2r745de14w4x4rt4de87w52";//
    // 验签key，支付中心给商户创建的对应resKey，请接入商户妥善保存。
    static final String resKey = "fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764";//
    //支付中心接口的地址
    static final String baseUrl = "http://pay.bibi321.com:3020/api";
    //接入商户的异步通知地址，用于接收支付中心支付结果通知
    static final String notifyUrl = "http://pay.bibi321.com/payNotify";
    //商户业务跳转地址，请根据实际业务进行配置
    static final String shopRedirectUrl = "http://m.bibi321.com";
    //atomiclong 可以理解是加了synchronized的long，用于多线程开发中保证数据的一致性，这里其实时用来生成id的，关于ID，其实可以由一个专门的服务来全局负责，本写法只是简单演示，不作为标准
    private AtomicLong seq = new AtomicLong(0L);

    /*
    * 对通过buy接口生成的商品进行支付，传入商品ID即可
    * 支付渠道channelId如下：
    * WX_NATIVE(微信扫码),
    * WX_JSAPI(微信公众号或微信小程序),
    * WX_APP(微信APP),
    * WX_MWEB(微信H5),
    * ALIPAY_WAP(支付宝手机支付),
    * ALIPAY_PC(支付宝网站支付),
    * ALIPAY_MOBILE(支付宝移动支付)
    * UNION_PC(银联网页支付)
    * UNION_WAP(银联移动支付)
    * 请求示例：http://pay.bibi321.com/pay?goodsOrderId=095809056978096&channelId=ALIPAY_WAP&redirect=http://m.bibi321.com
    * */

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    @ResponseBody
    public String pay(String goodsOrderId, String channelId, String redirect) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                       // 商户ID
        paramMap.put("mchOrderNo", goodsOrderId);           // 商户订单号
        paramMap.put("channelId", channelId);             // 支付渠道ID, WX_NATIVE,ALIPAY_WAP，支付渠道ID配合商户ID就能决定唯一支付渠道信息
        paramMap.put("amount", 1);                          // 支付金额,单位分
        paramMap.put("currency", "cny");                    // 币种, cny-人民币
        paramMap.put("clientIp", "localhost");        // 用户地址,IP或手机号，严格意义上该参数由业务前端传入
        paramMap.put("device", "WEB");                      // 设备
        paramMap.put("subject", "建议放置商品标题信息");
        paramMap.put("body", "建议放置商品详情描述");
        paramMap.put("notifyUrl", notifyUrl);               // 回调URL，用于支付中心通知接入商户支付结果
        paramMap.put("param1", "商户自定义参数1");          // 扩展参数1,目前可以用于商户扩展信息，支付中心对该参数直接保存，不做任何处理,如果商户需要透传json等字符串，建议经过base64等转换后传入
        paramMap.put("param2", "商户自定义参数2");         // 扩展参数2,目前可以用于商户扩展信息，支付中心对该参数直接保存，不做任何处理，如果商户需要透传json等字符串，建议经过base64等转换后传入
        paramMap.put("extra", "{\"productId\":\"120989823\",\"openId\":\"olnubwX4ymYu0vCZacRtLShuCQy8\"}");  // 附加参数，productId也应该前端传入，这里应该是goodsId，这里的openId应该前端业务传入
        paramMap.put("redirectUrl", verifyNotBlank(redirect));                         // 重定向地址,为空表示使用支付中心默认页面

        String reqSign = getSign(paramMap, reqKey);//请求参数签名
        paramMap.put("sign", reqSign);   // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/create_order?";
        //http请求请
        String result = call4Post(url + reqData);//向支付中心发起支付请求，支付中心在验证签名正确并争取获取到支付渠道详细信息后向第三方支付渠道进行正式下单，这里是同步方式下单。
        System.out.println("请求支付中心下单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if ("SUCCESS".equals(retMap.get("retCode"))) {
            // 验签
            String checkSign = getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if (checkSign.equals(retSign)) {
                System.out.println("=========支付中心下单验签成功=========");
            } else {
                System.err.println("=========支付中心下单验签失败=========");
                return null;
            }
        }
        String payOrderId = retMap.get("payOrderId").toString();

        return result + ":" + payOrderId;
    }

    /*
    * 支付失败的订单按原路径重新发起支付，亦可通过channelId来指定重新支付的渠道
    * mchId：商户ID，商户在接入支付中心时由支付中心分配的一个唯一ID，必填
    * mchOrderNo商户订单号（如商户自定义的商品订单号），商户自己管理，必填
    * payOrderId：支付中心订单号（下单成功后返回的订单号），可选，建议都填写完整
    * channelId：支付渠道选择参数，可选，如果参数为空，表示使用订单原支付渠道支付
    * redirct：业务重定向地址，可选，建议都填写完整
    * */
    @RequestMapping(value = "/rePay", method = RequestMethod.GET)
    @ResponseBody
    public String rePay(String mchId, String mchOrderNo, String payOrderId, String channelId, String redirect) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", mchOrderNo);                     // 商户订单号
        paramMap.put("payOrderId", verifyNotBlank(payOrderId));                     // 支付订单号
        paramMap.put("channelId", channelId);                     // 支付渠道，如果该参数为空，就默认使用原支付路径支付
        paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统
        paramMap.put("redirectUrl", verifyNotBlank(redirect));//业务重定向地址

        String reqSign = getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心查单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/repay?";
        String result = call4Post(url + reqData);
        System.out.println("请求支付中心查单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if (PayConstant.PAY_CHANNEL_ALIPAY_MOBILE.equalsIgnoreCase(channelId)) {
            return (String) retMap.get("payParams");
        }
        return (String) retMap.get("payUrl");
    }
    //订单查询
    //商户订单号mchOrderNo, 和支付中心订单号payOrderId 两个参数不能同时为空
    @RequestMapping(value = "/query_order", method = RequestMethod.GET)
    @ResponseBody
    static String queryPayOrderTest(String mchOrderNo, String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", mchOrderNo);                     // 商户订单号
        paramMap.put("payOrderId", payOrderId);                     // 支付订单号
        paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统

        String reqSign = getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心查单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/query_order?";
        String result = call4Post(url + reqData);
        System.out.println("请求支付中心查单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = getSign(retMap, resKey, "sign", "payParams");
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

    // 退款测试
    //商户订单号mchOrderNo, 和支付中心订单号payOrderId 两个参数不能同时为空
    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    @ResponseBody
    public String refundOrderTest(String mchOrderNo, String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchRefundNo", "REFUND" + System.currentTimeMillis());     // 商户订单号
        paramMap.put("mchOrderNo", mchOrderNo);     // 商户订单号"G20180226141222000001"
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", "ALIPAY_PC");
        paramMap.put("amount", 1);  // 退款金额
        paramMap.put("currency", "cny");                            // 币种, cny-人民币
        paramMap.put("clientIp", "localhost");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "支付测试");
        paramMap.put("body", "支付测试");
        paramMap.put("notifyUrl", notifyUrl);                       // 异步回调URL
        paramMap.put("param1", "");                                 // 扩展参数1
        paramMap.put("param2", "");                                 // 扩展参数2
        paramMap.put("channelUser", "user");
        paramMap.put("payOrderId", payOrderId);


        //{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}

        String reqSign = getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心退款接口,请求数据:" + reqData);
        String url = baseUrl + "/refund/create_order?";
        String result = call4Post(url + reqData);
        System.out.println("请求支付中心退款接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========退款验签成功=========");
            }else {
                System.err.println("=========退款验签失败=========");
                return null;
            }
        }
        return retMap.get("transOrderId")+"";
    }
    //查询退款订单
    //商户订退款单号MchRefundNo, 和支付中心退款订单号refundOrderId 两个参数不能同时为空
    @RequestMapping(value = "/query_refund_order", method = RequestMethod.GET)
    @ResponseBody
    static String queryRefundOrderTest(String mchRefundNo, String refundOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("MchRefundNo", mchRefundNo);                     // 商户订单号
        paramMap.put("refundOrderId", refundOrderId);                     // 支付订单号
        paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统

        String reqSign = getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心查单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/query_refund_order?";
        String result = call4Post(url + reqData);
        System.out.println("请求支付中心查单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========查单验签成功=========");
            }else {
                System.err.println("=========查单验签失败=========");
                return null;
            }
        }
        return retMap.get("payOrderId")+"";
    }
    /**
     * 接收支付中心异步通知,支付接口使用，退款和查询不需要使用
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/payNotify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _log.info("====== 开始处理支付中心通知 ======");
        Map<String, Object> paramMap = request2payResponseMap(request, new String[]{
                "payOrderId", "mchId", "mchOrderNo", "channelId", "amount", "currency", "status", "clientIp",
                "device", "subject", "channelOrderNo", "param1",
                "param2", "paySuccTime", "backType", "sign"
        });
        _log.info("支付中心通知请求参数,paramMap={}", paramMap);
        if (!verifyPayResponse(paramMap)) {
            String errorMessage = "verify request param failed.";
            _log.warn(errorMessage);
            outResult(response, "fail");
            return;
        }
        String payOrderId = (String) paramMap.get("payOrderId");
        String mchOrderNo = (String) paramMap.get("mchOrderNo");
        String resStr;
        try {

            // 执行业务逻辑,返回支付中心成功标识
                resStr = "success";
        } catch (Exception e) {
            resStr = "fail";
            _log.error(e, "业务异常,payOrderId=%s.mchOrderNo=%s", payOrderId, mchOrderNo);
        }
        _log.info("响应支付中心通知结果:{},payOrderId={},mchOrderNo={}", resStr, payOrderId, mchOrderNo);
        outResult(response, resStr);
        _log.info("====== 通知处理完成 ======");
    }

    @RequestMapping("/notify_test")
    public void notifyTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        outResult(response, "success");
    }

    void outResult(HttpServletResponse response, String content) {
        response.setContentType("text/html");
        PrintWriter pw;
        try {
            pw = response.getWriter();
            pw.print(content);
            _log.info("response xxpay complete.");
        } catch (IOException e) {
            _log.error(e, "response xxpay write exception.");
        }
    }

    public Map<String, Object> request2payResponseMap(HttpServletRequest request, String[] paramArray) {
        Map<String, Object> responseMap = new HashMap<>();
        for (int i = 0; i < paramArray.length; i++) {
            String key = paramArray[i];
            String v = request.getParameter(key);
            if (v != null) {
                responseMap.put(key, v);
            }
        }
        return responseMap;
    }

    public boolean verifyPayResponse(Map<String, Object> map) {
        String mchId = (String) map.get("mchId");
        String payOrderId = (String) map.get("payOrderId");
        String mchOrderNo = (String) map.get("mchOrderNo");
        String amount = (String) map.get("amount");
        String sign = (String) map.get("sign");

        if (StringUtils.isEmpty(mchId)) {
            _log.warn("Params error. mchId={}", mchId);
            return false;
        }
        if (StringUtils.isEmpty(payOrderId)) {
            _log.warn("Params error. payOrderId={}", payOrderId);
            return false;
        }
        if (StringUtils.isEmpty(amount) || !NumberUtils.isNumber(amount)) {
            _log.warn("Params error. amount={}", amount);
            return false;
        }
        if (StringUtils.isEmpty(sign)) {
            _log.warn("Params error. sign={}", sign);
            return false;
        }

        // 验证签名
        if (!verifySign(map)) {
            _log.warn("verify params sign failed. payOrderId={}", payOrderId);
            return false;
        }

        // 根据payOrderId查询业务订单,验证订单是否存在
//            GoodsOrder goodsOrder = goodsOrderService.getGoodsOrder(mchOrderNo);
//            if(goodsOrder == null) {
//                _log.warn("业务订单不存在,payOrderId={},mchOrderNo={}", payOrderId, mchOrderNo);
//                return false;
//            }
        // 核对金额
//            if(goodsOrder.getAmount() != Long.parseLong(amount)) {
//                _log.warn("支付金额不一致,dbPayPrice={},payPrice={}", goodsOrder.getAmount(), amount);
//                return false;
//            }
        return true;
    }

    /**
     * @param map
     * @param key
     * @param notContains 不包含的签名字段
     * @return
     */
    public static String getSign(Map<String, Object> map, String key, String... notContains) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            boolean isContain = false;
            for (int i = 0; i < notContains.length; i++) {
                if (entry.getKey().equals(notContains[i])) {
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }
        return getSign(newMap, key);
    }

    public boolean verifySign(Map<String, Object> map) {
        String mchId = (String) map.get("mchId");
        if (!this.mchId.equals(mchId)) return false;
        String localSign = getSign(map, resKey, "sign");
        String sign = (String) map.get("sign");
        return localSign.equalsIgnoreCase(sign);
    }

    private String verifyNotBlank(String url) {
        //不允许传入的参数为null，否则签名会验证失败
        String temp = "";
        if (StringUtils.isNotBlank(url)) {
            temp = url;
        }
        return temp;                         // 重定向地址
    }

    /**
     * 发起HTTP/HTTPS请求(method=POST)
     * @param url
     * @return
     */
    public static String call4Post(String url) {
        try {
            URL url1 = new URL(url);
            if("https".equals(url1.getProtocol())) {
                return HttpClient.callHttpsPost(url);
            }else if("http".equals(url1.getProtocol())) {
                return HttpClient.callHttpPost(url);
            }else {
                return "";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

}

class PayConstant {

    public final static String PAY_CHANNEL_WX_JSAPI = "WX_JSAPI"; 				// 微信公众号支付
    public final static String PAY_CHANNEL_WX_NATIVE = "WX_NATIVE";				// 微信原生扫码支付
    public final static String PAY_CHANNEL_WX_APP = "WX_APP";					// 微信APP支付
    public final static String PAY_CHANNEL_WX_MWEB = "WX_MWEB";					// 微信H5支付

    public final static String PAY_CHANNEL_IAP = "IAP";							// 苹果应用内支付

    public final static String PAY_CHANNEL_ALIPAY_MOBILE = "ALIPAY_MOBILE";		// 支付宝移动支付
    public final static String PAY_CHANNEL_ALIPAY_PC = "ALIPAY_PC";	    		// 支付宝PC支付
    public final static String PAY_CHANNEL_ALIPAY_WAP = "ALIPAY_WAP";	    	// 支付宝WAP支付
    public final static String PAY_CHANNEL_ALIPAY_QR = "ALIPAY_QR";	    		// 支付宝当面付之扫码支付

    public final static String PAY_CHANNEL_UNION_PC = "UNION_PC";	    		// 银联PC支付
    public final static String PAY_CHANNEL_UNION_WAP = "UNION_WAP";	    	// 银联WAP支付

    public final static String CHANNEL_NAME_WX = "WX"; 				// 渠道名称:微信
    public final static String CHANNEL_NAME_ALIPAY = "ALIPAY"; 		// 渠道名称:支付宝
    public final static String CHANNEL_NAME_UNION = "UNION"; 		// 渠道名称:银联


    public final static byte PAY_STATUS_EXPIRED = -2; 	// 订单过期
    public final static byte PAY_STATUS_FAILED = -1; 	// 支付失败
    public final static byte PAY_STATUS_INIT = 0; 		// 初始态
    public final static byte PAY_STATUS_PAYING = 1; 	// 支付中
    public final static byte PAY_STATUS_SUCCESS = 2; 	// 支付成功
    public final static byte PAY_STATUS_COMPLETE = 3; 	// 业务完成
    public final static byte PAY_STATUS_CLOSED = 4; 	// 业务关闭

    public final static byte TRANS_STATUS_INIT = 0; 		// 初始态
    public final static byte TRANS_STATUS_TRANING = 1; 		// 转账中
    public final static byte TRANS_STATUS_SUCCESS = 2; 		// 成功
    public final static byte TRANS_STATUS_FAIL = 3; 		// 失败
    public final static byte TRANS_STATUS_COMPLETE = 4; 	// 业务完成

    public final static byte TRANS_RESULT_INIT = 0; 		// 不确认结果
    public final static byte TRANS_RESULT_REFUNDING = 1; 	// 等待手动处理
    public final static byte TRANS_RESULT_SUCCESS = 2; 		// 确认成功
    public final static byte TRANS_RESULT_FAIL = 3; 		// 确认失败

    public final static byte REFUND_STATUS_INIT = 0; 		// 初始态
    public final static byte REFUND_STATUS_REFUNDING = 1; 	// 转账中
    public final static byte REFUND_STATUS_SUCCESS = 2; 	// 成功
    public final static byte REFUND_STATUS_FAIL = 3; 		// 失败
    public final static byte REFUND_STATUS_COMPLETE = 4; 	// 业务完成

    public final static byte REFUND_RESULT_INIT = 0; 		// 不确认结果
    public final static byte REFUND_RESULT_REFUNDING = 1; 	// 等待手动处理
    public final static byte REFUND_RESULT_SUCCESS = 2; 	// 确认成功
    public final static byte REFUND_RESULT_FAIL = 3; 		// 确认失败

    public final static String MCH_NOTIFY_TYPE_PAY = "1";		// 商户通知类型:支付订单
    public final static String MCH_NOTIFY_TYPE_TRANS = "2";		// 商户通知类型:转账订单
    public final static String MCH_NOTIFY_TYPE_REFUND = "3";	// 商户通知类型:退款订单

    public final static byte MCH_NOTIFY_STATUS_NOTIFYING = 1;	// 通知中
    public final static byte MCH_NOTIFY_STATUS_SUCCESS = 2;		// 通知成功
    public final static byte MCH_NOTIFY_STATUS_FAIL = 3;		// 通知失败


    public final static String RESP_UTF8 = "UTF-8";			// 通知业务系统使用的编码

    public static final String RETURN_PARAM_RETCODE = "retCode";
    public static final String RETURN_PARAM_RETMSG = "retMsg";
    public static final String RESULT_PARAM_RESCODE = "resCode";
    public static final String RESULT_PARAM_ERRCODE = "errCode";
    public static final String RESULT_PARAM_ERRCODEDES = "errCodeDes";
    public static final String RESULT_PARAM_SIGN = "sign";

    public static final String RETURN_VALUE_SUCCESS = "SUCCESS";
    public static final String RETURN_VALUE_FAIL = "FAIL";

    public static final String RETURN_ALIPAY_VALUE_SUCCESS = "success";
    public static final String RETURN_ALIPAY_VALUE_FAIL = "fail";
    public static final String RETURN_ALIPAY_TRADE_SUCCESS = "TRADE_SUCCESS";

    public static final String RETURN_WXPAY_TRADE_SUCCESS = "SUCCESS";//支付成功
    public static final String RETURN_WXPAY_TRADE_TO_REFUND = "REFUND";//转入退款
    public static final String RETURN_WXPAY_TRADE_NOTPAY = "NOTPAY";//未支付
    public static final String RETURN_WXPAY_TRADE_CLOSED = "CLOSED";//已关闭
    public static final String RETURN_WXPAY_TRADE_REVOKED = "REVOKED";//已撤销（刷卡支付）
    public static final String RETURN_WXPAY_TRADE_USERPAYING = "USERPAYING";//用户支付中
    public static final String RETURN_WXPAY_TRADE_PAYERROR = "PAYERROR";//支付失败(其他原因，如银行返回失败)

    public static final String RETURN_UNION_VALUE_SUCCESS = "Success!";
    public static final String RETURN_UNION_VALUE_FAIL = "fail";
    public static final String RETURN_UNION_VALUE_SUCCESS_CODE = "00";
    public static final String RETURN_UNION_VALUE_FAIL_CODE = "01";

    public static class JdConstant {
        public final static String CONFIG_PATH = "jd" + File.separator + "jd";	// 京东支付配置文件路径
    }

    public static class WxConstant {
        public final static String TRADE_TYPE_APP = "APP";									// APP支付
        public final static String TRADE_TYPE_JSPAI = "JSAPI";								// 公众号支付或小程序支付
        public final static String TRADE_TYPE_NATIVE = "NATIVE";							// 原生扫码支付
        public final static String TRADE_TYPE_MWEB = "MWEB";								// H5支付

    }

    public static class AliConstant {
        public final static String TRADE_TYPE_APP = "MOBILE";									// APP支付
        public final static String TRADE_TYPE_PC = "PC";								// 公众号支付或小程序支付
        public final static String TRADE_TYPE_QR = "QR";							// 原生扫码支付
        public final static String TRADE_TYPE_WAP = "WAP";								// H5支付

    }

    public static class IapConstant {
        public final static String CONFIG_PATH = "iap" + File.separator + "iap";		// 苹果应用内支付
    }

    public static class AlipayConstant {
        public final static String CONFIG_PATH = "alipay" + File.separator + "alipay";	// 支付宝移动支付
        public final static String TRADE_STATUS_WAIT = "WAIT_BUYER_PAY";		// 交易创建,等待买家付款
        public final static String TRADE_STATUS_CLOSED = "TRADE_CLOSED";		// 交易关闭
        public final static String TRADE_STATUS_SUCCESS = "TRADE_SUCCESS";		// 交易成功
        public final static String TRADE_STATUS_FINISHED = "TRADE_FINISHED";	// 交易成功且结束
    }

    public static final String NOTIFY_BUSI_PAY = "NOTIFY_VV_PAY_RES";
    public static final String NOTIFY_BUSI_TRANS = "NOTIFY_VV_TRANS_RES";

}

class MyLog extends MyLogFace {

    private static final Map<String, MyLog> _pool = new HashMap<String, MyLog>();
    //----------
    public static synchronized Set<String> getLoggers() 	{
        return _pool.keySet();
    }
    public static synchronized void clearLoggers() 	{
        _pool.clear();
    }
    //----------
    public static synchronized MyLog getLog(String clz) 	{
        MyLog log = _pool.get(clz);
        if (log==null) {
            log = new MyLog();
            log.setName(clz);
            _pool.put(clz, log);
        }
        return log;
    }
    //----------
    public static MyLog getLog(Class<?> clz){
        return getLog(clz.getName());
    }

}

class MyLogFace implements MyLogInf {

    private org.slf4j.Logger 				_log = null;
    public void setName(String clz) { 		_log = org.slf4j.LoggerFactory.getLogger(clz);	}

    public boolean isDebugEnabled() { return _log.isDebugEnabled();		}
    public boolean isInfoEnabled() 	{ return _log.isInfoEnabled();   	}
    public boolean isWarnEnabled() 	{ return _log.isWarnEnabled();		}
    public boolean isErrorEnabled() { return _log.isErrorEnabled();		}
    public boolean isTraceEnabled() { return _log.isTraceEnabled();		}

    public void trace(String message, Object... args) {
        if (this.isTraceEnabled())	_log.trace(message, args);
    }

    public void debug(String message, Object... args) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        String method = ste.getMethodName();
        String file = ste.getFileName();
        int line = ste.getLineNumber();
        if (this.isDebugEnabled())	_log.debug(method + ":Line" + line + "\n                " + message, args);
    }

    public void info(String message, Object... args) {
        if (this.isInfoEnabled())	_log.info(message, args);
    }

    public void warn(String message, Object... args) {
        if (this.isWarnEnabled())	_log.warn(message, args);
    }

    public void error(String message, Object... args) {
        if (this.isErrorEnabled())	_log.error(message, args);
    }

    public void error(Throwable e, String message, Object... args) {
        if (this.isErrorEnabled())	_log.error(String.format(message, args), e);
    }
    //------------------
    public void error(Throwable e, String message) {//简化版
        if (this.isErrorEnabled()) _log.error(message+e.toString(), e);
    }

}

abstract interface MyLogInf {

    public abstract void debug(String paramString, Object[] paramArrayOfObject);

    public abstract void info(String paramString, Object[] paramArrayOfObject);

    public abstract void warn(String paramString, Object[] paramArrayOfObject);

    public abstract void error(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject);
}

class HttpClient {

    private static final String USER_AGENT_VALUE =
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";

    private static final String JKS_CA_FILENAME =
            "tenpay_cacert.jks";

    private static final String JKS_CA_ALIAS = "tenpay";

    private static final String JKS_CA_PASSWORD = "";

    private static Logger _log = LoggerFactory.getLogger(HttpClient.class);

    /**
     * ca证书文件
     */
    private File caFile;

    /**
     * 证书文件
     */
    private File certFile;

    /**
     * 证书密码
     */
    private String certPasswd;

    /**
     * 请求内容，无论post和get，都用get方式提供
     */
    private String reqContent;

    /**
     * 应答内容
     */
    private String resContent;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 错误信息
     */
    private String errInfo;

    /**
     * 超时时间,以秒为单位
     */
    private int timeOut;

    /**
     * http应答编码
     */
    private int responseCode;

    /**
     * 字符编码
     */
    private String charset;

    private InputStream inputStream;

    public HttpClient() {
        this.caFile = null;
        this.certFile = null;
        this.certPasswd = "";

        this.reqContent = "";
        this.resContent = "";
        this.method = "POST";
        this.errInfo = "";
        this.timeOut = 30;//30秒

        this.responseCode = 0;
        this.charset = "UTF-8";

        this.inputStream = null;
    }

    public HttpClient(String url, String method, int timeOut, String charset) {
        this.caFile = null;
        this.certFile = null;
        this.certPasswd = "";

        this.reqContent = url;
        this.resContent = "";
        this.method = method;
        this.errInfo = "";
        this.timeOut = timeOut;//30秒

        this.responseCode = 0;
        this.charset = charset;

        this.inputStream = null;
    }

    /**
     * 设置证书信息
     *
     * @param certFile   证书文件
     * @param certPasswd 证书密码
     */
    public void setCertInfo(File certFile, String certPasswd) {
        this.certFile = certFile;
        this.certPasswd = certPasswd;
    }

    /**
     * 设置ca
     *
     * @param caFile
     */
    public void setCaInfo(File caFile) {
        this.caFile = caFile;
    }

    /**
     * 设置请求内容
     *
     * @param reqContent 表求内容
     */
    public void setReqContent(String reqContent) {
        this.reqContent = reqContent;
    }

    /**
     * 获取结果内容
     *
     * @return String
     * @throws IOException
     */
    public String getResContent() {
        try {
            this.doResponse();
        } catch (IOException e) {
            _log.error("", e);
            this.errInfo = e.getMessage();
            //return "";
        }

        return this.resContent;
    }

    /**
     * 设置请求方法post或者get
     *
     * @param method 请求方法post/get
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取错误信息
     *
     * @return String
     */
    public String getErrInfo() {
        return this.errInfo;
    }

    /**
     * 设置超时时间,以秒为单位
     *
     * @param timeOut 超时时间,以秒为单位
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * 获取http状态码
     *
     * @return int
     */
    public int getResponseCode() {
        return this.responseCode;
    }

    /**
     * 执行http调用。true:成功 false:失败
     *
     * @return boolean
     */
    public boolean call() {

        boolean isRet = false;

        //http
        if (null == this.caFile && null == this.certFile) {
            try {
                this.callHttp();
                isRet = true;
            } catch (IOException e) {
                _log.error("", e);
                this.errInfo = e.getMessage();
            } catch (Exception e) {
                _log.error("", e);
                this.errInfo = e.getMessage();
            }
            return isRet;
        }

        //https
        return calls();

    }

    public boolean calls() {

        boolean isRet = false;

        //https
        try {
            this.callHttps();
            isRet = true;
        } catch (UnrecoverableKeyException e) {
            _log.error("", e);
            this.errInfo = e.getMessage();
        } catch (KeyManagementException e) {
            _log.error("", e);
            this.errInfo = e.getMessage();
        } catch (CertificateException e) {
            _log.error("", e);
            this.errInfo = e.getMessage();
        } catch (KeyStoreException e) {
            _log.error("", e);
            this.errInfo = e.getMessage();
        } catch (NoSuchAlgorithmException e) {
            _log.error("", e);
            this.errInfo = e.getMessage();
        } catch (IOException e) {
            _log.error("", e);
            this.errInfo = e.getMessage();
        } catch (Exception e) {
            _log.error("", e);
            this.errInfo = e.getMessage();
        }
        return isRet;

    }

    protected void callHttp() throws IOException {

        if ("POST".equals(this.method.toUpperCase())) {
            String url = HttpClientUtil.getURL(this.reqContent);
            String queryString = HttpClientUtil.getQueryString(this.reqContent);
            byte[] postData = queryString.getBytes(this.charset);
            this.httpPostMethod(url, postData);

            return;
        }

        this.httpGetMethod(this.reqContent);

    }

    protected void callHttps() throws IOException, CertificateException,
            KeyStoreException, NoSuchAlgorithmException,
            UnrecoverableKeyException, KeyManagementException {

        // ca目录
        /*String caPath = this.caFile.getParent();

        File jksCAFile = new File(caPath + "/"
                + HttpClient.JKS_CA_FILENAME);
        if (!jksCAFile.isFile()) {
            X509Certificate cert = (X509Certificate) HttpClientUtil
                    .getCertificate(this.caFile);

            FileOutputStream out = new FileOutputStream(jksCAFile);

            // store jks file
            HttpClientUtil.storeCACert(cert, HttpClient.JKS_CA_ALIAS,
                    HttpClient.JKS_CA_PASSWORD, out);

            out.close();

        }

        FileInputStream trustStream = new FileInputStream(jksCAFile);
        FileInputStream keyStream = new FileInputStream(this.certFile);*/

		/*SSLContext sslContext = HttpClientUtil.getSSLContext(trustStream,
                HttpClient.JKS_CA_PASSWORD, keyStream, this.certPasswd);*/

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{new HttpClient.TrustAnyTrustManager()},
                new java.security.SecureRandom());

        //关闭流
        //keyStream.close();
        //trustStream.close();

        if ("POST".equals(this.method.toUpperCase())) {
            String url = HttpClientUtil.getURL(this.reqContent);
            String queryString = HttpClientUtil.getQueryString(this.reqContent);
            byte[] postData = queryString.getBytes(this.charset);

            this.httpsPostMethod(url, postData, sslContext);

            return;
        }

        this.httpsGetMethod(this.reqContent, sslContext);

    }

    /**
     * 以http post方式通信
     *
     * @param url
     * @param postData
     * @throws IOException
     */
    protected void httpPostMethod(String url, byte[] postData)
            throws IOException {

        HttpURLConnection conn = HttpClientUtil.getHttpURLConnection(url);

        this.doPost(conn, postData);
    }

    /**
     * 以http get方式通信
     *
     * @param url
     * @throws IOException
     */
    protected void httpGetMethod(String url) throws IOException {

        HttpURLConnection httpConnection =
                HttpClientUtil.getHttpURLConnection(url);

        this.setHttpRequest(httpConnection);

        httpConnection.setRequestMethod("GET");

        this.responseCode = httpConnection.getResponseCode();

        this.inputStream = httpConnection.getInputStream();

    }

    /**
     * 以https get方式通信
     *
     * @param url
     * @param sslContext
     * @throws IOException
     */
    protected void httpsGetMethod(String url, SSLContext sslContext)
            throws IOException {

        SSLSocketFactory sf = sslContext.getSocketFactory();

        HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

        conn.setSSLSocketFactory(sf);

        this.doGet(conn);

    }

    protected void httpsPostMethod(String url, byte[] postData,
                                   SSLContext sslContext) throws IOException {

        SSLSocketFactory sf = sslContext.getSocketFactory();

        HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

        conn.setSSLSocketFactory(sf);

        this.doPost(conn, postData);

    }

    /**
     * 设置http请求默认属性
     *
     * @param httpConnection
     */
    protected void setHttpRequest(HttpURLConnection httpConnection) {

        //设置连接超时时间
        httpConnection.setConnectTimeout(this.timeOut * 1000);

        //User-Agent
        httpConnection.setRequestProperty("User-Agent",
                HttpClient.USER_AGENT_VALUE);

        //不使用缓存
        httpConnection.setUseCaches(false);

        //允许输入输出
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);

    }

    /**
     * 处理应答
     *
     * @throws IOException
     */
    protected void doResponse() throws IOException {

        if (null == this.inputStream) {
            return;
        }

        //获取应答内容
        this.resContent = HttpClientUtil.InputStreamTOString(this.inputStream, this.charset);

        //关闭输入流
        this.inputStream.close();

    }

    /**
     * post方式处理
     *
     * @param conn
     * @param postData
     * @throws IOException
     */
    protected void doPost(HttpURLConnection conn, byte[] postData)
            throws IOException {

        // 以post方式通信
        conn.setRequestMethod("POST");

        // 设置请求默认属性
        this.setHttpRequest(conn);

        // Content-Type
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");


        BufferedOutputStream out = new BufferedOutputStream(conn
                .getOutputStream());

        final int len = 1024; // 1KB
        HttpClientUtil.doOutput(out, postData, len);



        /*PrintWriter out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(new String(postData));
        // flush输出流的缓冲
        out.flush();*/


        // 关闭流
        out.close();

        // 获取响应返回状态码
        this.responseCode = conn.getResponseCode();

        // 获取应答输入流
        this.inputStream = conn.getInputStream();

    }

    /**
     * get方式处理
     *
     * @param conn
     * @throws IOException
     */
    protected void doGet(HttpURLConnection conn) throws IOException {

        //以GET方式通信
        conn.setRequestMethod("GET");

        //设置请求默认属性
        this.setHttpRequest(conn);

        //获取响应返回状态码
        this.responseCode = conn.getResponseCode();

        //获取应答输入流
        this.inputStream = conn.getInputStream();
    }

    public static String callHttpPost(String url) {
        return callHttpPost(url, 60); // 默认超时时间60秒
    }

    public static String callHttpPost(String url, int connect_timeout) {
        return callHttpPost(url, connect_timeout, "UTF-8"); // 默认编码 UTF-8
    }

    public static String callHttpPost(String url, int connect_timeout, String encode) {
        HttpClient client = new HttpClient(url, "POST", connect_timeout, encode);
        client.call();
        return client.getResContent();
    }

    public static String callHttpsPost(String url) {

        HttpClient client = new HttpClient(url, "POST", 60, "UTF-8");
        client.calls();
        return client.getResContent();

    }

    public static String callHttpGet(String url, int connect_timeout, String encode) {
        HttpClient client = new HttpClient(url, "GET", connect_timeout, encode);
        client.call();
        return client.getResContent();
    }

    public static String callHttpsGet(String url) {

        HttpClient client = new HttpClient(url, "GET", 60, "UTF-8");
        client.calls();
        return client.getResContent();

    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

}

class HttpClientUtil {

    public static final String SunX509 = "SunX509";
    public static final String JKS = "JKS";
    public static final String PKCS12 = "PKCS12";
    public static final String TLS = "TLS";

    private static final String encoding = "UTF-8";

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * get HttpURLConnection
     * @param strUrl url地址
     * @return HttpURLConnection
     * @throws IOException
     */
    public static HttpURLConnection getHttpURLConnection(String strUrl)
            throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        return httpURLConnection;
    }

    /**
     * get HttpsURLConnection
     * @param strUrl url地址
     * @return HttpsURLConnection
     * @throws IOException
     */
    public static HttpsURLConnection getHttpsURLConnection(String strUrl)
            throws IOException {
        URL url = new URL(strUrl);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
                .openConnection();
        return httpsURLConnection;
    }

    /**
     * 获取不带查询串的url
     * @param strUrl
     * @return String
     */
    public static String getURL(String strUrl) {

        if(null != strUrl) {
            int indexOf = strUrl.indexOf("?");
            if(-1 != indexOf) {
                return strUrl.substring(0, indexOf);
            }

            return strUrl;
        }

        return strUrl;

    }

    /**
     * 获取查询串
     * @param strUrl
     * @return String
     */
    public static String getQueryString(String strUrl) {

        if(null != strUrl) {
            int indexOf = strUrl.indexOf("?");
            if(-1 != indexOf) {
                return strUrl.substring(indexOf+1, strUrl.length());
            }
            return "";
        }

        return strUrl;
    }

    /**
     * 查询字符串转换成Map<br/>
     * name1=key1&name2=key2&...
     * @param queryString
     * @return
     */
    public static Map queryString2Map(String queryString) {
        if(null == queryString || "".equals(queryString)) {
            return null;
        }

        Map m = new HashMap();
        String[] strArray = queryString.split("&");
        for(int index = 0; index < strArray.length; index++) {
            String pair = strArray[index];
            HttpClientUtil.putMapByPair(pair, m);
        }

        return m;

    }

    /**
     * 把键值添加至Map<br/>
     * pair:name=value
     * @param pair name=value
     * @param m
     */
    public static void putMapByPair(String pair, Map m) {

        if(null == pair || "".equals(pair)) {
            return;
        }

        int indexOf = pair.indexOf("=");
        if(-1 != indexOf) {
            String k = pair.substring(0, indexOf);
            String v = pair.substring(indexOf+1, pair.length());
            if(null != k && !"".equals(k)) {
                m.put(k, v);
            }
        } else {
            m.put(pair, "");
        }
    }

    /**
     * BufferedReader转换成String<br/>
     * 注意:流关闭需要自行处理
     * @param reader
     * @return String
     * @throws IOException
     */
    public static String bufferedReader2String(BufferedReader reader) throws IOException {
        StringBuffer buf = new StringBuffer();
        String line = null;
        while( (line = reader.readLine()) != null) {
            buf.append(line);
            buf.append("\r\n");
        }

        return buf.toString();
    }

    /**
     * 处理输出<br/>
     * 注意:流关闭需要自行处理
     * @param out
     * @param data
     * @param len
     * @throws IOException
     */
    public static void doOutput(OutputStream out, byte[] data, int len)
            throws IOException {
        int dataLen = data.length;
        int off = 0;
        while (off < data.length) {
            if (len >= dataLen) {
                out.write(data, off, dataLen);
                off += dataLen;
            } else {
                out.write(data, off, len);
                off += len;
                dataLen -= len;
            }

            // 刷新缓冲区
            out.flush();
        }

    }

    /**
     * 获取SSLContext
     * @param trustPasswd
     * @param keyPasswd
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    public static SSLContext getSSLContext(
            FileInputStream trustFileInputStream, String trustPasswd,
            FileInputStream keyFileInputStream, String keyPasswd)
            throws NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException, UnrecoverableKeyException,
            KeyManagementException {

        // ca
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(HttpClientUtil.SunX509);
        KeyStore trustKeyStore = KeyStore.getInstance(HttpClientUtil.JKS);
        trustKeyStore.load(trustFileInputStream, HttpClientUtil
                .str2CharArray(trustPasswd));
        tmf.init(trustKeyStore);

        final char[] kp = HttpClientUtil.str2CharArray(keyPasswd);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(HttpClientUtil.SunX509);
        KeyStore ks = KeyStore.getInstance(HttpClientUtil.PKCS12);
        ks.load(keyFileInputStream, kp);
        kmf.init(ks, kp);

        SecureRandom rand = new SecureRandom();
        SSLContext ctx = SSLContext.getInstance(HttpClientUtil.TLS);
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

        return ctx;
    }

    /**
     * 获取CA证书信息
     * @param cafile CA证书文件
     * @return Certificate
     * @throws CertificateException
     * @throws IOException
     */
    public static java.security.cert.Certificate getCertificate(File cafile)
            throws CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(cafile);
        java.security.cert.Certificate cert = cf.generateCertificate(in);
        in.close();
        return cert;
    }

    /**
     * 字符串转换成char数组
     * @param str
     * @return char[]
     */
    public static char[] str2CharArray(String str) {
        if(null == str) return null;

        return str.toCharArray();
    }

    /**
     * 存储ca证书成JKS格式
     * @param cert
     * @param alias
     * @param password
     * @param out
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    public static void storeCACert(Certificate cert, String alias,
                                   String password, OutputStream out) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore ks = KeyStore.getInstance("JKS");

        ks.load(null, null);

        ks.setCertificateEntry(alias, cert);

        // store keystore
        ks.store(out, HttpClientUtil.str2CharArray(password));

    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    /**
     * InputStream转换成Byte
     * 注意:流关闭需要自行处理
     * @param in
     * @return byte
     * @throws Exception
     */
    public static byte[] InputStreamTOByte(InputStream in) throws IOException{

        int BUFFER_SIZE = 4096;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;

        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        byte[] outByte = outStream.toByteArray();
        outStream.close();

        return outByte;
    }

    /**
     * InputStream转换成String
     * 注意:流关闭需要自行处理
     * @param in
     * @param encoding 编码
     * @return String
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in,String encoding) throws IOException{
        return new String(InputStreamTOByte(in),encoding);
    }
}


