package org.xxpay.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.xxpay.common.constant.PayConstant;
import org.xxpay.common.util.AmountUtil;
import org.xxpay.common.util.DateUtil;
import org.xxpay.common.util.PayDigestUtil;
import org.xxpay.common.util.XXPayUtil;
import org.xxpay.shop.dao.model.GoodsOrder;
import org.xxpay.shop.service.GoodsOrderService;
import org.xxpay.shop.service.WXOAuthManager;
import org.xxpay.shop.util.Constant;
import org.xxpay.shop.util.HttpClient;
import org.xxpay.shop.util.OAuth2RequestParamHelper;
import org.xxpay.shop.util.vx.WxApi;
import org.xxpay.shop.util.vx.WxApiClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("")
public class WxApiController {

    private final static MyLog _log = MyLog.getLog(WxApiController.class);

    @Autowired
    private GoodsOrderService goodsOrderService;

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

    private final static String WX_ENTRY_URL = "http://uc.bibi321.com/wxEntry.html";//???????????????????????????????????????????????????????????????

    static final String AppID = "wxf13d13b2ab2a3dd9";//???????????????AppID???

    static final String AppSecret = "0a45c4c081153a1f249e4bb438df8d90";//?????????????????????????????????App??????,??????????????????????????????????????????????????????????????????????????????openid???code???access_token??????????????????

    private final static String GetOpenIdURL = "http://uc.bibi321.com/getOpenId";//????????????openid???????????????

    private final static String GetUserInfoURL = "http://uc.bibi321.com/getUserInfo";//????????????userInfo???????????????

    @Resource(name="stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;
    /**
     *???????????????????????????
     **/

    @RequestMapping("/wxEntry.html")
    public String WxEntry(ModelMap model, HttpServletRequest request) {
        String logPrefix = "??????????????????";
        String view = "wxEntry";
        _log.info("====== ???????????? ======");
        String ua = request.getHeader("User-Agent");
        String client = null;
        JSONObject userInfo = null;
        if(StringUtils.isBlank(ua)) {
            String errorMessage = "User-Agent?????????";
            _log.info("{}?????????{}", logPrefix, errorMessage);
            model.put("result", "failed");
            model.put("resMsg", errorMessage);
            return view;
        }else if(ua.contains("MicroMessenger")) {
            client = "wx";
        }
        if(client == null) {
            String errorMessage = "?????????????????????";
            _log.info("{}?????????{}", logPrefix, errorMessage);
            model.put("result", "failed");
            model.put("resMsg", errorMessage);
            return view;
        }
        if("wx".equals(client)){
            // ??????????????????openid???????????????????????????
            userInfo = JSON.parseObject(request.getParameter("userInfo"));
            if (userInfo != null) {
                _log.info("{}userInfo???{}", logPrefix, userInfo.toJSONString());
            }else {
                String url = GetUserInfoURL + "?redirectUrl=" + WX_ENTRY_URL;
                _log.info("??????URL={}", url);
                return "redirect:" + url;
            }
        }
        model.put("title", "?????????????????????");
        model.put("userInfo", userInfo);
        model.put("client", client);
        return view;
    }

    /**
     * ??????code
     * @return
     */
    @RequestMapping("/getOpenId")
    public void getOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException {
            _log.info("??????????????????openID??????");
            String redirectUrl = request.getParameter("redirectUrl");
            String code = request.getParameter("code");
            String openId = "";
            if(!StringUtils.isBlank(code)){//??????request?????????code?????????????????????
                try {
                    openId = WxApiClient.getOAuthOpenId(AppID, AppSecret, code);
                    _log.info("??????????????????openId={}", openId);
                } catch (Exception e) {
                    _log.error(e, "??????????????????openId??????");
                }
                if(redirectUrl.indexOf("?") > 0) {
                    redirectUrl += "&openId=" + openId;
                }else {
                    redirectUrl += "?openId=" + openId;
                }
                response.sendRedirect(redirectUrl);
            }else{//oauth??????code
            String redirectUrl4Vx = GetOpenIdURL + "?redirectUrl=" + redirectUrl;
            String state = OAuth2RequestParamHelper.prepareState(request);
            String url = WxApi.getOAuthCodeUrl(AppID, redirectUrl4Vx, "snsapi_base", state);
            _log.info("??????URL={}", url);
            response.sendRedirect(url);
        }
    }

    /**
     * ??????????????????
     * @return
     */
    @RequestMapping("/getUserInfo")
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        _log.info("??????????????????userInfo??????");
        String redirectUrl = request.getParameter("redirectUrl");
        String code = request.getParameter("code");
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String userInfo = "";
        if(!StringUtils.isBlank(code)){//??????request?????????code?????????????????????
            try {
                userInfo = WXOAuthManager.getOAuthUserInfo(AppID, AppSecret, code, stringRedisTemplate);
                _log.info("??????????????????userInfo={}", userInfo);
            } catch (Exception e) {
                _log.error(e, "??????????????????userInfo??????");
            }
            if(redirectUrl.indexOf("?") > 0) {
                redirectUrl += "&userInfo=" + URLEncoder.encode(userInfo, "UTF-8");
            }else {
                redirectUrl += "?userInfo=" + URLEncoder.encode(userInfo, "UTF-8");
            }
            _log.info("??????redirectUrl={}", redirectUrl);
            response.sendRedirect(redirectUrl);
        }else{//oauth??????code
            String redirectUrl4Vx = GetUserInfoURL + "?redirectUrl=" + redirectUrl;
            String state = OAuth2RequestParamHelper.prepareState(request);
            String url = WxApi.getOAuthCodeUrl(AppID, redirectUrl4Vx, "snsapi_userinfo", state);
            _log.info("??????URL={}", url);
            response.sendRedirect(url);
        }
    }

    @RequestMapping("/wx/notify")
    public void notifyTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        outResult(response, "success");
    }
    public Map<String, Object> request2ResponseMap(HttpServletRequest request, String[] paramArray) {
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
    public boolean verifyResponse(Map<String,Object> map) {
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

        // ????????????
        if (!verifySign(map)) {
            _log.warn("verify params sign failed. payOrderId={}", payOrderId);
            return false;
        }

        // ??????payOrderId??????????????????,????????????????????????
        GoodsOrder goodsOrder = goodsOrderService.getGoodsOrder(mchOrderNo);
        if(goodsOrder == null) {
            _log.warn("?????????????????????,payOrderId={},mchOrderNo={}", payOrderId, mchOrderNo);
            return false;
        }
        // ????????????
        if(goodsOrder.getAmount() != Long.parseLong(amount)) {
            _log.warn("?????????????????????,dbPayPrice={},payPrice={}", goodsOrder.getAmount(), amount);
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
        //???????????????????????????null??????????????????????????????
        String temp = "";
        if(StringUtils.isNotBlank(url)) {
            temp = url;
        }
        return temp;                         // ???????????????
    }
}