package com.universe.wallet.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.universe.wallet.dto.request.EncryptBody;
import com.universe.wallet.dto.response.WalletResponse;
import com.universe.wallet.configuration.WalletApiProperties;
import com.universe.wallet.encryptor.AesUtil;
import com.universe.wallet.enums.TradeType;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供sha-256摘要签名的实现工具类
 * @author Pullwind
 */
@Slf4j
public class WalletRequestUtil {

    private WalletRequestUtil() {
        // TODO document why this constructor is empty
    }

    /**
     * 钱包接口请求工具方法
     * @param url 请求的地址
     * @param params 请求的业务参数
     * @param walletApiProperties 钱包配置信息
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public static WalletResponse walletRequest(String url,
                                               JSONObject params,
                                               WalletApiProperties walletApiProperties,
                                               TradeType tradeType,
                                               boolean withNotifyUrl) {
        String appId;
        String appKey;

        if (tradeType.getCode() == TradeType.WITHDRAW.getCode()) {
            appId = walletApiProperties.getWithdraw().getAppId();
            appKey = walletApiProperties.getWithdraw().getAppKey();
            if(withNotifyUrl) {
                params.put("notifyUrl", walletApiProperties.getWithdraw().getNotifyUrl());
            }
        } else {
            appId = walletApiProperties.getDeposit().getAppId();
            appKey = walletApiProperties.getDeposit().getAppKey();
            if(withNotifyUrl) {
                params.put("notifyUrl", walletApiProperties.getDeposit().getNotifyUrl());
            }
        }
        //处理公共参数，merchantMemberNo参数除审核提现外，其他接口都有该参数
        params.put("merchantMemberNo", walletApiProperties.getMerchantMemberNo());
        log.info("请求URL:{}参数:{}", url, params.toJSONString());
        params = formatRequestParams(params, appKey);
        Map<String, String> headers = new HashMap<>(2);
        headers.put("version", walletApiProperties.getVersion());
        headers.put("appid", appId);
        String result = OkHttpClientUtil.postJson(url, headers, params.toJSONString());
        log.warn("{}：{}", url, result);
        EncryptBody encryptBody = JSON.parseObject(result, EncryptBody.class);
        try {
            return getRawData(encryptBody, appKey, WalletResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSON.parseObject(result, WalletResponse.class);
    }

    /**
     * 钱包接口请求工具方法
     * @param url 请求的地址
     * @param params 请求的业务参数
     * @param walletApiProperties 钱包配置信息
     * @param outputClazz 输出的返回数据实体类
     * @param <T> 泛型，与outputClazz对应
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public static <T> WalletResponse<T> walletRequest(String url,
                                                      JSONObject params,
                                                      WalletApiProperties walletApiProperties,
                                                      Class<T> outputClazz,
                                                      TradeType tradeType,
                                                      boolean withNotifyUrl) {
        WalletResponse walletResponse = walletRequest(url, params, walletApiProperties, tradeType, withNotifyUrl);
        JSONObject data = (JSONObject) walletResponse.getData();
        walletResponse.setData(JSON.toJavaObject(data, outputClazz));
        return walletResponse;
    }

    /**
     * 钱包接口请求工具方法
     * @param url 请求的地址
     * @param params 请求的业务参数
     * @param walletApiProperties 钱包配置信息
     * @param outputClazz 输出的返回数据实体类
     * @param <T> 泛型，与outputClazz对应
     * @return 返回结构化数据，包含了状态码code, 说明信息msg, 实际泛型业务数据data
     */
    public static <T> WalletResponse<List<T>> walletRequestReturnList(String url,
                                                                      JSONObject params,
                                                                      WalletApiProperties walletApiProperties,
                                                                      Class<T> outputClazz,
                                                                      TradeType tradeType,
                                                                      boolean withNotifyUrl) {
        WalletResponse walletResponse = walletRequest(url, params, walletApiProperties, tradeType, withNotifyUrl);
        try {
            JSONArray data = (JSONArray) walletResponse.getData();
            walletResponse.setData(JSON.parseArray(JSON.toJSONString(data), outputClazz));
            return walletResponse;
        } catch (Exception e) {
            walletResponse.setData(null);
            return walletResponse;
        }
    }

    /**
     * 对请求参数进行格式化
     * @param rawJson 需要格式化的原始参数， 会对输入的参数进行直接操作，所以经过本方法处理后是格式化后的
     * @param appKey 本次请求的appKey
     */
    private static JSONObject formatRequestParams(JSONObject rawJson, String appKey) {
        JSONObject encryptJson = new JSONObject();
        long timestamp = System.currentTimeMillis()/1000;
        rawJson.put("timestamp", timestamp);
        encryptJson.put("encryptString", encode(rawJson, appKey));
        return encryptJson;
    }

    /**
     * 对输入数据进行AES GCM加密，输出URL BASE64
     * @param rawJson 需要AES加密的原始数据
     * @param appKey 本次请求的appKey
     * @return 加密后的base64 urlEncode
     */
    private static String encode(JSONObject rawJson, String appKey) {
        String content = rawJson.toJSONString();
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        return AesUtil.staticWeakKeyEncryptToBase64(appKey, contentBytes);
    }

    /**
     *
     * @param encryptBody 加密后的数据
     * @param appKey 解密key，也就是appid对应的appkey
     * @param dataClassType 泛型实际对应的类
     * @param <T> 泛型
     * @return 解密后的业务数据
     */
    public static <T> T getRawData(EncryptBody encryptBody, String appKey, Class<T> dataClassType) {
        String encryptString = encryptBody.getEncryptString();
        String decryptString = AesUtil.staticWeakKeyDecryptFromBase64ToString(appKey, encryptString);
        return JSON.parseObject(decryptString, dataClassType);
    }
}
