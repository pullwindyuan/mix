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

/**
 * Created by dingzhiwei on 16/5/5.
 */
public class RedEnvelopeDemo {

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

    // 红包统一下单
    static String payOrder() {
        JSONObject paramMap = new JSONObject();
        String mchOrderNo = String.valueOf(System.currentTimeMillis());
        paramMap.put("mchId", mchId);                               // 商户ID
        paramMap.put("mchOrderNo", mchOrderNo);     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        //UNION_PC、UNION_WAP
        paramMap.put("amount", "50");                                  // 支付金额,cny单位分, htl单位个, eth单位GWEI
        paramMap.put("currency", "gusd");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "红包测试");
        paramMap.put("body", "发红包");
        paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        /*
        * param1参数说明
        * {"code":"TRED","productName":"通证红包","begin_time":1545877791,"user_id":"0e95944f89b54fdd925904f5d35d4e2b","msg":"恭喜发财","type":"random","count":"5"}
        * code:产品类型
        * productName:产品名称
        * begin_time:过期时间
        * user_id:发送者ID
        * msg:显示信息
        * type:红包类型（random:随机拆分；average:平均）
        * count:红包个数
        * */
        paramMap.put(
        "param1",
        "{\"code\":\"TRED\",\"productName\":\"通证红包\",\"begin_time\":1545877791,\"user_id\":\"0e95944f89b54fdd925904f5d35d4e2b\",\"msg\":\"恭喜发财\",\"type\":\"random\",\"count\":\"5\"}"
        );
        //分账名单，这里有参数表示是定额、定向红包。通过该参数来定义不同的红包，列表中有多少个数据就有多少个红包
        //1、userId为空，amount不为空表示非定向定额红包，也就是每个拆分的红包等额
        //2、没有该参数表示为随机红包
        //3、目前只支持定向定额红包

        paramMap.put("redirectUrl", "https://m.bibi321.com");                         // 重定向地址
        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String reqData = HttpClient.getRequestParamString(paramsMap, "UTF-8");
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/redEnvelope/create_order?";
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
        String url = baseUrl + "/redEnvelope/do?";
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
        //由于红包的分账无法预先知晓金额，所以不需要这个参数
        //paramMap.put("param2", "[{\"userId\":\"dcf04ab1c28a472180cb61a0a9f093f2\",\"subOrderId\":\"354365487658756898700\",\"code\":\"SP\",\"amount\":\"30\",\"remarkInfo\":\"比比购月饼店\"},{\"userId\":\"B46968147C9741C6814B77B1B904DC97\",\"subOrderId\":\"252786546289476998\",\"code\":\"FZ\",\"amount\":\"20\",\"remarkInfo\":\"比比购服装店\"}]");

        String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
        paramMap.put("sign", reqSign);                              // 签名
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("params", paramMap.toJSONString());
        String reqData = HttpClient.getRequestParamString(paramsMap, "UTF-8");
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        String url = baseUrl + "/redEnvelope/settle?";
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
}
