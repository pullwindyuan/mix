package org.xxpay.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.xxpay.common.constant.PayConstant;
import org.xxpay.common.util.*;
import org.xxpay.shop.dao.model.GoodsOrder;
import org.xxpay.shop.payFeign.PayOrderAPI;
import org.xxpay.shop.payFeign.TransOrderMgr;
import org.xxpay.shop.service.GoodsOrderService;
import org.xxpay.shop.util.Constant;
import org.xxpay.shop.util.HttpClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("")
public class GoodsOrderController {

    private final static MyLog _log = MyLog.getLog(GoodsOrderController.class);
    @Autowired
    private PayOrderAPI payOrderAPI;

    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private TransOrderMgr transOrderMgr;

    @Value("${pay.config.mchId}")
    private String mchId;

    @Value("${pay.config.reqKey}")
    private String reqKey;

    @Value("${pay.config.resKey}")
    private String resKey;

    @Value("${pay.config.baseUrl}")
    private String baseUrl;

    @Value("${pay.config.notifyUrl}")
    private String notifyUrl;
//http://gate.bibitrip.com:8765/api/admin/user/front/info
//    static final String mchId = "10000000";//深圳航旅，对应支付后台管理里中的商户ID（注意，这里不是微信支付里的商户ID，而是我们的自定义的商户，比如比比旅行需要接入支付中心，我们就需要为比比旅行创建一个商户）
//    // 加签key
//    static final String reqKey = "kfywnsjuw474651189kjsujsng42547852kuf2r745de14w4x4rt4de87w52";//后台管理中创建商户时创建的对应key，请接入商户妥善保存。
//    // 验签key
//    static final String resKey = "fywk6jsikh37672ks734kgklsl78qkgkjrt86wgsg64hsa74hrwj6hsabru764";//后台管理中创建商户时创建的对应key，请接入商户妥善保存。
//      static final String baseUrl = "http://gate.bibitrip.com:3020";//http://gate.bibitrip.com:8765/api/payment/api";//"http://pay.bibitrip.com:88/api/open";//"http://luosheng.bibi321.com/api";//"http://pay.bibi321.com:3020/api";//支付中心服务的地址//注意，这里baseUrl没有带3020的端口，是因为测试环境使用frp内网穿透，pay.bibi321.com域名已经绑定了3020端口

//    static final String mchId = "10000002";//深圳航旅，对应支付后台管理里中的商户ID（注意，这里不是微信支付里的商户ID，而是我们的自定义的商户，比如比比旅行需要接入支付中心，我们就需要为比比旅行创建一个商户）
//    // 加签key
//    static final String reqKey = "lserjoi859kskUTYOKLJ76j87jhuihjh";//后台管理中创建商户时创建的对应key，请接入商户妥善保存。
//    // 验签key
//    static final String resKey = "klnmhburk7c7fkmrji587fjkhjuuuuyy";//后台管理中创建商户时创建的对应key，请接入商户妥善保存。
//
//    static final String baseUrl = "http://pay.bibitrip.com:88/api/open";//"http://pay.bibitrip.com:88/api/open";//"http://luosheng.bibi321.com/api";//"http://pay.bibi321.com:3020/api";//支付中心服务的地址//注意，这里baseUrl没有带3020的端口，是因为测试环境使用frp内网穿透，pay.bibi321.com域名已经绑定了3020端口

//    static final String notifyUrl = "http://shop.bibi321.com/payNotify";//接入商户的自定义通知地址，改地址是本支付中心用于将支付结果通知到商户的地址，不是支付渠道和支付中心的通知回调地址
    static final String shopRedirectUrl = "http://m.bibi321.com";//商户业务跳转地址
    private AtomicLong seq = new AtomicLong(0L);//atomiclong 可以理解是加了synchronized的long，用于多线程开发中保证数据的一致性，这里其实时用来生成id的，关于ID，其实可以由一个专门的服务来全局负责
    private final static String QR_PAY_URL = "http://shop.bibi321.com/qrPay.html";//商户业务地址，和支付中心无关，由商户维护；
    static final String AppID = "wxf13d13b2ab2a3dd9";//微信公众号AppID。
    static final String AppSecret = "0a45c4c081153a1f249e4bb438df8d90";//微信服务号（公众号）的App密匙,用于商户和公众号后台进行数据交互的重要密匙，比如获取openid和code（access_token）时需要使用
    private final static String GetOpenIdURL = "http://shop.bibi321.com/getOpenId";//商户获取openid的接口方法

    /*
    * 该接口用于用户发起购买的操作，生成商品订单，注意，这里不是支付订单
    * */
    @RequestMapping(value = "/buy/{goodsId}", method = RequestMethod.GET)
    @ResponseBody
    public String buy(@PathVariable("goodsId") String goodsId) {
        if(!"G_0001".equals(goodsId)) {
            return "fail";
        }
        String goodsOrderId = String.format("%s%s%06d", "G", DateUtil.getSeqString(), (int) seq.getAndIncrement() % 1000000);
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setGoodsOrderId(goodsOrderId);
        goodsOrder.setGoodsId(goodsId);
        goodsOrder.setGoodsName("商品G_0001");
        goodsOrder.setAmount(1l);
        goodsOrder.setUserId("000001");
        goodsOrder.setStatus(Constant.GOODS_ORDER_STATUS_INIT);
        int result = goodsOrderService.addGoodsOrder(goodsOrder);
        _log.info("插入商品订单,返回:{}", result);
        return result+"";
    }

    /*
    * 对通过buy接口生成的商品进行支付，传入商品ID即可
    * */
    @RequestMapping(value = "/pay/{goodsOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public String pay(@PathVariable("goodsOrderId") String goodsOrderId, String redirct) {
        GoodsOrder goodsOrder = goodsOrderService.getGoodsOrder(goodsOrderId);
        if(goodsOrder == null) return "fail";
        int status = goodsOrder.getStatus();
        if(status != Constant.GOODS_ORDER_STATUS_INIT) {
            return "fail_001";
        }
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                       // 商户ID
        paramMap.put("mchOrderNo", goodsOrderId);           // 商户订单号
        paramMap.put("channelId", "ALIPAY_WAP");             // 支付渠道ID, WX_NATIVE,ALIPAY_WAP，支付渠道ID配合商户ID就能决定唯一支付渠道信息
        paramMap.put("amount", goodsOrder.getAmount());                          // 支付金额,单位分
        paramMap.put("currency", "cny");                    // 币种, cny-人民币
        paramMap.put("clientIp", "114.112.124.236");        // 用户地址,IP或手机号，严格意义上该参数由业务前端传入
        paramMap.put("device", "WEB");                      // 设备
        paramMap.put("subject", goodsOrder.getGoodsName());
        paramMap.put("body", goodsOrder.getGoodsName());
        paramMap.put("notifyUrl", notifyUrl);               // 回调URL，用于支付中心通知接入商户支付结果
        paramMap.put("param1", "");                         // 扩展参数1
        paramMap.put("param2", "");                         // 扩展参数2
        paramMap.put("extra", "{\"productId\":\"120989823\",\"openId\":\"olnubwX4ymYu0vCZacRtLShuCQy8\"}");  // 附加参数，productId也应该前端传入，这里应该是goodsId，这里的openId应该前端业务传入
        paramMap.put("redirectUrl", verifyNotBlank(redirct));                         // 重定向地址,为空表示使用支付中心默认页面

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);//请求参数签名
        paramMap.put("sign", reqSign);   // 签名
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/create_order?";
        String result = XXPayUtil.call4Post(url + reqData);//向支付中心发起支付请求，支付中心在验证签名正确并争取获取到支付渠道详细信息后向第三方支付渠道进行正式下单，这里是同步方式下单。
        System.out.println("请求支付中心下单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode"))) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心下单验签成功=========");
            }else {
                System.err.println("=========支付中心下单验签失败=========");
                return null;
            }
        }
        String payOrderId = retMap.get("payOrderId").toString();

        goodsOrder = new GoodsOrder();
        goodsOrder.setGoodsOrderId(goodsOrderId);
        goodsOrder.setPayOrderId(payOrderId);
        goodsOrder.setChannelId("ALIPAY_WAP");
        int ret = goodsOrderService.update(goodsOrder);
        _log.info("修改商品订单,返回:{}", ret);
        return result+"";
    }

    /*
    * 支付失败的商品或者订单按原路径重新发起支付
    * payOrderId和redirct为可选项
    * */
    @RequestMapping(value = "/rePay", method = RequestMethod.GET)
    @ResponseBody
    public String rePay(String mchId, String mchOrderNo, String payOrderId, String channelId, String redirect) {
        _log.info("重新支付,参数:mchOrderNo={};payOrderId={}", mchOrderNo,payOrderId);
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
        if(PayConstant.PAY_CHANNEL_ALIPAY_MOBILE.equalsIgnoreCase(channelId)) {
            return (String) retMap.get("payParams");
        }
        return (String) retMap.get("payUrl");
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public String queryPayOrderTest(String mchOrderNo, String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", verifyNotBlank(mchOrderNo));                     // 商户订单号
        paramMap.put("payOrderId", verifyNotBlank(payOrderId));                     // 支付订单号,可选项
        //paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统

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

/**
* pay接口的内部方法，如果是使用HTTP调用时，需要使用pay接口
* */
    private Object createPayOrder(GoodsOrder goodsOrder, Map<String, Object> params) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchId);                       // 商户ID
        paramMap.put("mchOrderNo", goodsOrder.getGoodsOrderId());           //商户订单号
        paramMap.put("channelId", params.get("channelId"));             // 支付渠道ID, WX_NATIVE,ALIPAY_WAP
        paramMap.put("amount", goodsOrder.getAmount());                          // 支付金额,单位分
        paramMap.put("currency", "cny");                    // 币种, cny-人民币
        paramMap.put("clientIp", params.get("clientIp"));        // 用户地址,IP或手机号
        paramMap.put("openId", params.get("openId"));        // 用户地址,IP或手机号
        paramMap.put("device", "WEB");                      // 设备
        paramMap.put("subject", goodsOrder.getGoodsName());
        paramMap.put("body", goodsOrder.getGoodsName());
        paramMap.put("notifyUrl", notifyUrl);         // 回调URL
        paramMap.put("param1", "{\"code\":\"O\",\"begin_time\":\"2018-09-03 18:16:05\",\"user_id\":\"2acacd2bd0c641fd974fddc5875f5910\"}");                         // 扩展参数1

        paramMap.put("param2", "");                         // 扩展参数2
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
        paramMap.put("redirectUrl", verifyNotBlank((String) params.get("redirectUrl")));                         // 重定向地址

        JSONObject extra = new JSONObject();
        extra.put("openId", params.get("openId"));
        //paramMap.put("extra", extra.toJSONString());  // 附加参数

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);   // 签名
        //String reqData = "params=" + paramMap.toJSONString();
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String reqData = HttpClient.getRequestParamString(paramsMap, "UTF-8");
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/pay/create_order?";
        //Map<String, String> paramsMap = new HashMap();
        //paramsMap.put("params", paramMap.toJSONString());
        //String result = HttpClient.post(paramsMap,url);
        //String result = payOrderAPI.payOrder(paramMap.toJSONString());
        String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心下单接口,响应数据:" + result);
        //判断返回结果是否是html文本
        if (result.indexOf("<html>", 0) >= 0) {
            return result;
        }
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode"))) {
            // 验签
            String checkSign = PayDigestUtil.getSign(retMap, resKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                System.out.println("=========支付中心下单验签成功=========");
            }else {
                System.err.println("=========支付中心下单验签失败=========");
                return null;
            }
        }
        return retMap;
    }

    @RequestMapping("/openQrPay.html")
    public String openQrPay(ModelMap model) {
        return "openQrPay";
    }
/**
 *公众号支付二维码支付页面
 **/

    @RequestMapping("/qrPay.html")
    public String qrPay(ModelMap model, HttpServletRequest request, Long amount, String redirect) {
        String logPrefix = "【二维码扫码支付】";
        String view = "qrPay";
        _log.info("====== 开始接收二维码扫码支付请求 ======");
        _log.info("重定向地址：{}", redirect);
        String ua = request.getHeader("User-Agent");
        String goodsId = "G_0001";
        _log.info("{}接收参数:goodsId={},amount={},ua={}", logPrefix, goodsId, amount, ua);
        String client = "wx";
        String channelId = "ALIPAY_WAP";//默认支付方式为支付宝
        if(StringUtils.isBlank(ua)) {
            String errorMessage = "User-Agent为空！";
            _log.info("{}信息：{}", logPrefix, errorMessage);
            model.put("result", "failed");
            model.put("resMsg", errorMessage);
            return view;
        }else {
            if(ua.contains("Alipay")) {
                client = "alipay";
                channelId = "ALIPAY_WAP";
            }else if(ua.contains("MicroMessenger")) {
                client = "wx";
                channelId = "WX_JSAPI";
            }
        }
        if(client == null) {
            String errorMessage = "请用微信或支付宝扫码";
            _log.info("{}信息：{}", logPrefix, errorMessage);
            model.put("result", "failed");
            model.put("resMsg", errorMessage);
            return view;
        }
        // 先插入订单数据
        GoodsOrder goodsOrder = null;
        Map<String, String> orderMap = null;
        if ("alipay".equals(client)) {
            _log.info("{}{}扫码下单", logPrefix, "支付宝");
            Map params = new HashMap<>();
            params.put("channelId", channelId);
            params.put("redirectUrl", verifyNotBlank(redirect));//业务重定向地址
            params.put("clientIp", "127.0.0.1");
            // 下单
            goodsOrder = createGoodsOrder(goodsId, amount);
            orderMap = (Map<String, String>) createPayOrder(goodsOrder, params);
        }else if("wx".equals(client)){
            _log.info("{}{}扫码", logPrefix, "微信");
            // 判断是否拿到openid，如果没有则去获取
            String openId = request.getParameter("openId");
            if (StringUtils.isNotBlank(openId)) {
                _log.info("{}openId：{}", logPrefix, openId);
                Map params = new HashMap<>();
                params.put("channelId", channelId);
                params.put("openId", openId);
                params.put("redirectUrl", verifyNotBlank(redirect));//业务重定向地址
                params.put("clientIp", "127.0.0.1");
                goodsOrder = createGoodsOrder(goodsId, amount);
                // 下单
                orderMap = (Map<String, String>) createPayOrder(goodsOrder, params);
            }else {
                String redirectUrl = QR_PAY_URL + "?amount=" + amount;
                String url = GetOpenIdURL + "?redirectUrl=" + redirectUrl;
                _log.info("跳转URL={}", url);
                return "redirect:" + url;
            }
        }
        model.put("goodsOrder", goodsOrder);
        model.put("amount", AmountUtil.convertCent2Dollar(goodsOrder.getAmount()+""));
        if(orderMap != null) {
            model.put("orderMap", orderMap);
            String payOrderId = orderMap.get("payOrderId");
            GoodsOrder go = new GoodsOrder();
            go.setGoodsOrderId(goodsOrder.getGoodsOrderId());
            go.setPayOrderId(payOrderId);
            go.setChannelId(channelId);
            int ret = goodsOrderService.update(go);
            _log.info("修改商品订单,返回:{}", ret);
        }
        model.put("client", client);
        return view;
    }
    //buy接口的内部方法，如果是使用HTTP调用时，需要使用buy接口
    GoodsOrder createGoodsOrder(String goodsId, Long amount) {
        // 先插入订单数据
        String goodsOrderId =  String.format("%s%s%06d", "G", DateUtil.getSeqString(), (int) seq.getAndIncrement() % 1000000);
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setGoodsOrderId(goodsOrderId);
        goodsOrder.setGoodsId(goodsId);
        goodsOrder.setGoodsName("商品G_0001");
        goodsOrder.setAmount(amount);
        goodsOrder.setUserId("000001");
        goodsOrder.setStatus(Constant.GOODS_ORDER_STATUS_INIT);
        int result = goodsOrderService.addGoodsOrder(goodsOrder);
        _log.info("插入商品订单,返回:{}", result);
        return goodsOrder;
    }

    /**
     * 接收支付中心通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/payNotify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _log.info("====== 开始处理支付中心通知 ======");
        Map<String,Object> paramMap = request2payResponseMap(request, new String[]{
                "payOrderId",// 支付订单号
                "mchId",	// 商户ID
                "mchOrderNo",// 商户订单号
                "channelId",// 渠道ID
                "amount",// 支付金额
                "currency",// 货币类型
                "status", // 支付状态
                "clientIp",// 客户端IP
                "device",  // 设备
                "subject", // 商品标题
                "channelOrderNo", // 渠道订单号
                "param1",// 扩展参数1
                "param2",// 扩展参数2
                "paySuccTime",// 支付成功时间
                "backType",//保留，暂无值
                "sign"//签名信息
        });
        _log.info("支付中心通知请求参数,paramMap={}", paramMap);
        if (!verifyPayResponse(paramMap)) {
            String errorMessage = "verify request param failed.";
            _log.warn(errorMessage);
            outResult(response, "success");
            return;
        }
        String payOrderId = (String) paramMap.get("payOrderId");
        String mchOrderNo = (String) paramMap.get("mchOrderNo");
        String resStr;
        try {
            GoodsOrder goodsOrder = goodsOrderService.getGoodsOrder(mchOrderNo);
            if(goodsOrder != null && goodsOrder.getStatus() == Constant.GOODS_ORDER_STATUS_COMPLETE) {
                outResult(response, "success");
                return;
            }
            // 执行业务逻辑
            int ret = goodsOrderService.updateStatus4Success(mchOrderNo);
            // ret返回结果
            // 等于1表示处理成功,返回支付中心success
            // 其他值,返回支付中心fail,让稍后再通知
            if(ret == 1) {
                ret = goodsOrderService.updateStatus4Complete(mchOrderNo);
                if(ret == 1) {
                    resStr = "success";
                }else {
                    resStr = "fail";
                }
            }else {
                resStr = "fail";
            }
        }catch (Exception e) {
            resStr = "fail";
            _log.error(e, "执行业务异常,payOrderId=%s.mchOrderNo=%s", payOrderId, mchOrderNo);
        }
        _log.info("响应支付中心通知结果:{},payOrderId={},mchOrderNo={}", resStr, payOrderId, mchOrderNo);
        outResult(response, resStr);
        _log.info("====== 支付中心通知处理完成 ======");
    }

    @RequestMapping("/notify_test")
    public void notifyTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _log.info("接收异步通知成功");
        outResult(response, "success");
    }

    @RequestMapping("/toAliPay.html")
    @ResponseBody
    public String toAliPay(Long amount, String channelId, String redirect) {
        String logPrefix = "【支付宝支付】";
        _log.info("====== 开始接收支付宝支付请求 ======");
        String goodsId = "G_0001";
        _log.info("{}接收参数:goodsId={},amount={},channelId={}", logPrefix, goodsId, amount, channelId);
        // 先插入订单数据
        Map params = new HashMap<>();
        params.put("channelId", channelId);

        params.put("redirectUrl", verifyNotBlank(redirect));                         // 重定向地址
        params.put("clientIp", "127.0.0.1");
        params.put("param1", "2acacd2bd0c641fd974fddc5875f5910");

        // 下单
        GoodsOrder goodsOrder = createGoodsOrder(goodsId, amount);
        Map<String, String> orderMap = (Map<String, String>) createPayOrder(goodsOrder, params);
        if(orderMap != null && "success".equalsIgnoreCase(orderMap.get("resCode"))) {
            String payOrderId = orderMap.get("payOrderId");
            GoodsOrder go = new GoodsOrder();
            go.setGoodsOrderId(goodsOrder.getGoodsOrderId());
            go.setPayOrderId(payOrderId);
            go.setChannelId(channelId);
            //int ret = goodsOrderService.update(go);
            //_log.info("修改商品订单,返回:{}", ret);
        }
        if(PayConstant.PAY_CHANNEL_ALIPAY_MOBILE.equalsIgnoreCase(channelId)) {
            return orderMap.get("payParams");
        }
        return orderMap.get("payUrl");
    }

    @RequestMapping("/getopenid.html")
    public String GetOpenId(ModelMap model) {
        return "getopenid";
    }

    @RequestMapping("/wxprepay.html")
    public String openWxPrePay(ModelMap model) {
        return "wxprepay";
    }

    @RequestMapping("/toWxPay.html")
    public String toWxPay(ModelMap model, HttpServletRequest request, @RequestParam Long amount, String redirect, String clientIp, String result) throws IOException {
        String logPrefix = "【微信H5支付】";
        _log.info("====== 开始接收微信支付请求 ======");
        String goodsId = "G_0001";
        String ua = request.getHeader("User-Agent");
        _log.info("{}接收参数:amount={},clientIp={}, result={}", logPrefix, amount, clientIp, result);
        String client = "h5";
        if(StringUtils.isBlank(ua)) {
            String errorMessage = "User-Agent为空！";
            _log.info("{}信息：{}", logPrefix, errorMessage);
        }else {
            if(ua.contains("MicroMessenger")) {
                client = "wx";
            }
        }
        model.put("client", client);
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf13d13b2ab2a3dd9%s&redirect_uri=http%3a%2f%2fshop.bibi321.com%2fwxprepay.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect
        String channelId;
        Map params = new HashMap<>();
        JSONObject resultObj = null;
        if(StringUtils.isNotBlank(result)) {
            resultObj = JSON.parseObject(URLDecoder.decode(result,"UTF-8"));
        }
        if(resultObj != null) {
            channelId = "WX_JSAPI";
            params.put("openId", resultObj.getString("openId"));
        }else {
            channelId = "WX_MWEB";
        }
        // 先插入订单数据

        params.put("clientIp", clientIp);
        params.put("channelId", channelId);
        params.put("redirectUrl", redirect);                         // 重定向地址


        // 下单
        GoodsOrder goodsOrder = createGoodsOrder(goodsId, amount);
        Map<String, String> orderMap = (Map<String, String>) createPayOrder(goodsOrder, params);
        if(orderMap != null && "success".equalsIgnoreCase(orderMap.get("resCode"))) {
            String payOrderId = orderMap.get("payOrderId");
            GoodsOrder go = new GoodsOrder();
            go.setGoodsOrderId(goodsOrder.getGoodsOrderId());
            go.setPayOrderId(payOrderId);
            go.setChannelId(channelId);
            int ret = goodsOrderService.update(go);
            _log.info("修改商品订单,返回:{}", ret);
        }
        if(PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId)) {
            _log.info("微信公众号支付参数:{}", orderMap.get("payParams"));
        }else {
            _log.info("微信H5支付链接,返回:{}", orderMap.get("payUrl"));
            model.put("payUrl", orderMap.get("payUrl"));
        }
        model.put("orderMap", orderMap);
        return "toWxPay";
    }

    @ApiOperation(value="银联支付体验主页")
    @RequestMapping(value="unionPay.html",method= RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping("/unionPay/pay.html")
    @ResponseBody
    public String toUnionPay(HttpServletRequest request, Long amount, String channelId, String redirect) {
        String logPrefix = "【银联支付】";
        _log.info("====== 开始接收银联支付请求 ======");
        String goodsId = "G_0001";
        _log.info("{}接收参数:goodsId={},amount={},channelId={}", logPrefix, goodsId, amount, channelId);
        // 先插入订单数据
        Map params = new HashMap<>();
        params.put("channelId", channelId);
        params.put("redirectUrl", verifyNotBlank(redirect));                         // 重定向地址
        // 下单
        GoodsOrder goodsOrder = createGoodsOrder(goodsId, amount);//创建商品订单，由商户维护，业务相关性极大
        String result = (String) createPayOrder(goodsOrder, params);//统一下单，业务无关
//        if(orderMap != null && "success".equalsIgnoreCase(orderMap.get("resCode"))) {
//            String payOrderId = orderMap.get("payOrderId");
//            GoodsOrder go = new GoodsOrder();
//            go.setGoodsOrderId(goodsOrder.getGoodsOrderId());
//            go.setPayOrderId(payOrderId);
//            go.setChannelId(channelId);
//            int ret = goodsOrderService.update(go);
//            _log.info("修改商品订单,返回:{}", ret);
//
//    }
        return result;
    }

    @RequestMapping("/transOrder")
    public String openQrPay() {
        String test = transOrderMgr.getList("{\"channelId\":\"HL_AWARD\"}", 1, 100);
        System.out.println("请求支付中心转账订单列表,响应数据:" + test);
        return "openQrPay";
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

    // 卡包支付页面测试
    @RequestMapping("/cardPayTest")
    public String payOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        JSONObject paramMap = new JSONObject();
        String mchOrderNo = String.valueOf(System.currentTimeMillis());
        paramMap.put("mchId", "10000000");                               // 商户ID
        paramMap.put("mchOrderNo", mchOrderNo);     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        //UNION_PC、UNION_WAP
        paramMap.put("channelId", PayConstant.PAY_CHANNEL_HTL_ASYN_PAY);
        paramMap.put("amount", 5);                                  // 支付金额,单位分
        paramMap.put("currency", "cny");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "支付测试");
        paramMap.put("body", "支付测试");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        paramMap.put("param1", "{\"code\":\"O\",\"productName\":\"食品\",\"begin_time\":\"2018-07-03 18:16:05\",\"user_id\":\"2acacd2bd0c641fd974fddc5875f5910\"}");
        //分账名单
        paramMap.put("param2", "[{\"userId\":\"dcf04ab1c28a472180cb61a0a9f093f2\",\"subOrderId\":\"89024657623578966754\",\"code\":\"SP\",\"amount\":\"3\",\"remarkInfo\":\"比比购月饼店\"},{\"userId\":\"B46968147C9741C6814B77B1B904DC97\",\"subOrderId\":\"252786546289476998\",\"code\":\"FZ\",\"amount\":\"2\",\"remarkInfo\":\"比比购服装店\"}]");
        paramMap.put("openId", "olnubwXHaCVYR7QYAuwBr-GXFcRE");
        paramMap.put("userId", "2acacd2bd0c641fd974fddc5875f5910");
        paramMap.put("extra", "{\n" +
                "  \"productId\": \"120989823\",\n" +
                "  \"openId\": \"olnubwXHaCVYR7QYAuwBr-GXFcRE\",\n" +//注意：测试WX_JSAPI时需要预先通过其他途径获取到用户openID，这里填写的是我自己的
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
        paramMap.put("redirectUrl", "https://m.bibi321.com");                         // 重定向地址
        //String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        //paramMap.put("sign", reqSign);                              // 签名
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String reqData = HttpClient.getRequestParamString(paramsMap, "UTF-8");
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = "https://ms.bibi321.com/api/cp/pay" + "/requestSelectPay?";
        String result = XXPayUtil.call4Post(url + reqData);
        System.out.println("请求支付中心下单接口,响应数据:" + result);
        //判断返回结果是否是html文本
        if (result.indexOf("<html>", 0) >= 0) {
            return result;
        } else {
            JSONObject retMap = JSON.parseObject(result);
            if ("0001".equals(retMap.get("status"))) {
                model.put("redirect", retMap.getString("data"));
            }
            return "cardPayTestEntry";
        }
    }

    public Map<String, Object> request2payResponseMap(HttpServletRequest request, String[] paramArray) {
        Map<String, Object> responseMap = new HashMap<>();
        for (int i = 0;i < paramArray.length; i++) {
            String key = paramArray[i];
            String v = request.getParameter(key);
            if (v != null) {
                responseMap.put(key, v);
            }
        }
        return responseMap;
    }

    public boolean verifyPayResponse(Map<String,Object> map) {
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
        GoodsOrder goodsOrder = goodsOrderService.getGoodsOrder(mchOrderNo);
        if(goodsOrder == null) {
            _log.warn("业务订单不存在,payOrderId={},mchOrderNo={}", payOrderId, mchOrderNo);
            return false;
        }
        // 核对金额
        if(goodsOrder.getAmount() != Long.parseLong(amount)) {
            _log.warn("支付金额不一致,dbPayPrice={},payPrice={}", goodsOrder.getAmount(), amount);
            return false;
        }
        return true;
    }

    public boolean verifySign(Map<String, Object> map) {
        String mchId = (String) map.get("mchId");
        if(!this.mchId.equals(mchId)) return false;
        String localSign = PayDigestUtil.getSign(map, resKey, "sign");
        String sign = (String) map.get("sign");
        return localSign.equalsIgnoreCase(sign);
    }

    private String verifyNotBlank(String url) {
        //不允许传入的参数为null，否则签名会验证失败
        String temp = "";
        if(StringUtils.isNotBlank(url)) {
            temp = url;
        }
        return temp;                         // 重定向地址
    }
}