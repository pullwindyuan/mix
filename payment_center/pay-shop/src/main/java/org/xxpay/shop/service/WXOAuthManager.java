package org.xxpay.shop.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xxpay.common.util.JsonUtil;
import org.xxpay.common.util.MyLog;
import org.xxpay.shop.controller.WxApiController;
import org.xxpay.shop.util.vx.HttpMethod;
import org.xxpay.shop.util.vx.OAuthAccessToken;
import org.xxpay.shop.util.vx.WxApi;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * java类简单作用描述
 *
 * @ProjectName: payment_center
 * @Package: org.xxpay.shop.schedule
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/5/23 16:01
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/5/23 16:01
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Configuration
@Component
@EnableScheduling
public class WXOAuthManager {
    private final static MyLog _log = MyLog.getLog(WxApiController.class);
    @Resource(name="stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    static final String AppID = "wxf13d13b2ab2a3dd9";//微信公众号AppID。

    static final String AppSecret = "0a45c4c081153a1f249e4bb438df8d90";//微信服务号（公众号）的App密匙,用于商户和公众号后台进行数据交互的重要密匙，比如获取openid和code（access_token）时需要使用

    private final static String GetOpenIdURL = "http://uc.bibi321.com/getOpenId";//商户获取openid的接口方法

    private final static String GetUserInfoURL = "http://uc.bibi321.com/getUserInfo";//商户获取userInfo的接口方法

    private static WXOAuthManager instance = null;
    public static WXOAuthManager getInstance() {
        if(instance == null) {
            instance = new WXOAuthManager();
        }
        return instance;
    }

    @Scheduled(fixedRate = 120*60*1000)
    public void getAccessToken() {
        _log.info("刷新token");
        this.refreshtOAuthAccessToken(stringRedisTemplate);
    }

    public static String getOAuthUserInfo(String appid, String secret, String code, StringRedisTemplate stringRedisTemplate) {
        OAuthAccessToken token = getOAuthAccessToken(appid, secret, code, stringRedisTemplate);
        if(token != null){
            if(token.getErrcode() != null){//获取失败
                System.out.println("## getOAuthAccessToken Error = " + token.getErrmsg());
            }else{
                String userInfo = WxApi.getOAuthUserInfo(token.getAccessToken(), token.getOpenid());
                return userInfo;
            }
        }
        return null;
    }

    //获取OAuth2.0 Token
    public static OAuthAccessToken getOAuthAccessToken(String appId, String appSecret, String code, StringRedisTemplate stringRedisTemplate) {
        OAuthAccessToken token = null;
        String accessToken = stringRedisTemplate.opsForValue().get("kfjroiuwlksgblkj");
        if(accessToken != null) {
            _log.info("从缓存获取token");
            token = JsonUtil.getObjectFromJson(accessToken, OAuthAccessToken.class);
            return token;
        }
        JSONObject jsonObject = null;
        String tockenUrl;
        String refreshToken = stringRedisTemplate.opsForValue().get("itjhit8959gfksjgo");
        if(refreshToken != null) {
            tockenUrl = WxApi.getOAuthRefreshTokenUrl(appId, refreshToken);
            jsonObject = WxApi.httpsRequest(tockenUrl, HttpMethod.GET, null);
        }else {
            tockenUrl = WxApi.getOAuthTokenUrl(appId, appSecret, code);
            jsonObject = WxApi.httpsRequest(tockenUrl, HttpMethod.GET, null);
        }


        if (null != jsonObject && !jsonObject.containsKey("errcode")) {
            try {
                token = new OAuthAccessToken();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInteger("expires_in"));
                token.setOpenid(jsonObject.getString("openid"));
                token.setScope(jsonObject.getString("scope"));
                token.setRefreshToken(jsonObject.getString("refresh_token"));
                stringRedisTemplate.opsForValue().set("itjhit8959gfksjgo", token.getRefreshToken(),30, TimeUnit.DAYS);//向redis里存入数据和设置缓存时间
            } catch (JSONException e) {
                token = null;//获取token失败
            }
        }else if(null != jsonObject){
            token = new OAuthAccessToken();
            token.setErrcode(jsonObject.getInteger("errcode"));
        }
        stringRedisTemplate.opsForValue().set("kfjroiuwlksgblkj", jsonObject.toJSONString(),2, TimeUnit.HOURS);//向redis里存入数据和设置缓存时间
        return token;
    }

    //获取OAuth2.0 Token
    public static OAuthAccessToken refreshtOAuthAccessToken(StringRedisTemplate stringRedisTemplate) {
        OAuthAccessToken token = null;
        JSONObject jsonObject = null;
        String tockenUrl;
        String refreshToken = stringRedisTemplate.opsForValue().get("itjhit8959gfksjgo");
        if(refreshToken != null) {
            tockenUrl = WxApi.getOAuthRefreshTokenUrl(AppID, refreshToken);
            jsonObject = WxApi.httpsRequest(tockenUrl, HttpMethod.GET, null);
        }else {
            return null;
        }
        if (null != jsonObject && !jsonObject.containsKey("errcode")) {
            try {
                token = new OAuthAccessToken();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInteger("expires_in"));
                token.setOpenid(jsonObject.getString("openid"));
                token.setScope(jsonObject.getString("scope"));
                token.setRefreshToken(jsonObject.getString("refresh_token"));
                stringRedisTemplate.opsForValue().set("itjhit8959gfksjgo", token.getRefreshToken(),30, TimeUnit.DAYS);//向redis里存入数据和设置缓存时间
            } catch (JSONException e) {
                token = null;//获取token失败
            }
        }else if(null != jsonObject){
            token = new OAuthAccessToken();
            token.setErrcode(jsonObject.getInteger("errcode"));
        }
        stringRedisTemplate.opsForValue().set("kfjroiuwlksgblkj", jsonObject.toJSONString(),2, TimeUnit.HOURS);//向redis里存入数据和设置缓存时间
        return token;
    }
}
