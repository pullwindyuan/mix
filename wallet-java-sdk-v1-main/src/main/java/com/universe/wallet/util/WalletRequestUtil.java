package com.universe.wallet.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.universe.wallet.DTO.response.WalletResponse;
import com.universe.wallet.configruration.WalletApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 提供sha-256摘要签名的实现工具类
 * @author Pullwind
 */
@Slf4j
public class WalletRequestUtil {
    private static MessageDigest messageDigest = createDigest("SHA-256");

    /**
     * 钱包接口请求工具方法
     * @param url 请求的地址
     * @param params 请求的业务参数
     * @param walletApiProperties 钱包配置信息
     * @param outputClazz 输出的返回数据实体类
     * @param <T> 泛型，与outputClazz对应
     * @return 返回指定的数据对象，如果返回值为空，表示接口口操作失败
     */
    public static <T> WalletResponse<T> walletRequest(String url, JSONObject params, WalletApiProperties walletApiProperties, Class<T> outputClazz) {

        if(!url.contains("approval")) {
            params.put("merchantMemberNo", walletApiProperties.getMerchantMemberNo());
        }

        boolean isWithdraw = url.contains("/withdrawOrders/");
        String appId;
        String appKey;

        if(isWithdraw) {
            appId = walletApiProperties.getOpenApi().getWithdraw().getAppId();
            appKey = walletApiProperties.getOpenApi().getWithdraw().getAppKey();
        }else {
            appId = walletApiProperties.getOpenApi().getDeposit().getAppId();
            appKey = walletApiProperties.getOpenApi().getDeposit().getAppKey();
        }
        params.put("appId", appId);

        if(isWithdraw) {
            //提现
            if (url.contains("/create")) {
                params.put("notifyUrl", walletApiProperties.getOpenApi().getWithdraw().getNotifyUrl());
            }
        }else {
            //充值
            if (url.contains("/create")) {
                params.put("notifyUrl", walletApiProperties.getOpenApi().getDeposit().getNotifyUrl());
            }
        }

        try {
            formatRequestParams(params, appKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("version", "v1");
        headers.put("appid", appId);

        log.info("request : {}: {}", url, params.toJSONString());
        String result = OkHttpClientUtil.postJson(url, headers, params.toJSONString());
        log.warn("{}：{}", url, result);
        JSONObject resultJson = JSON.parseObject(result);
        WalletResponse walletResponse = new WalletResponse();
        int code = resultJson.getIntValue("code");
        walletResponse.setCode(code);
        walletResponse.setMsg(resultJson.getString("msg"));
        if(code == 0) {
            JSONObject data = resultJson.getJSONObject("data");
            walletResponse.setData(JSON.toJavaObject(data, outputClazz));
        }else {
            walletResponse.setData(null);
        }
        return walletResponse;
    }

    /**
     * 生成sha256编码后的数据
     */
    private static MessageDigest createDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("No such hashing algorithm", ex);
        }
    }

    /**
     * 对请求参数进行格式化
     * @param rawJSON 需要格式化的原始参数， 会对输入的参数进行直接操作，所以经过本方法处理的rawJSON就是格式化后的，直接使用即可的appId
     * @param appKey 本次请求的appKey
     * @throws NoSuchAlgorithmException
     */
    private static void formatRequestParams(JSONObject rawJSON, String appKey) throws NoSuchAlgorithmException {
        long timestamp = System.currentTimeMillis()/1000;
        String sign = sign(rawJSON, appKey, timestamp);
        log.warn("timestamp：{}", timestamp);
        log.warn("sign：{}", sign);
        rawJSON.put("timestamp", timestamp);
        rawJSON.put("sign", sign);

    }

    /**
     * 对钱包回调传过来的数据进行签名
     * @param rawJSON 需要签名的原始参数
     * @param appKey 本次请求的appKey
     * @throws NoSuchAlgorithmException
     * @return 数字签名
     */
    private static String sign(JSONObject rawJSON, String appKey, long timestamp) {
        String bankBranch = rawJSON.getString("bankBranch");
        TreeMap<String, String> treeMap = new TreeMap<>();
        rawJSON.forEach((key, value)->{
            treeMap.put(key, String.valueOf(value));
        });

        if(!StringUtils.hasText(bankBranch) && treeMap.containsKey("bankBranch")) {
            treeMap.remove("bankBranch");
        }

        treeMap.put("timestamp", String.valueOf(timestamp));

        StringBuilder stringBuilder = new StringBuilder("");
        treeMap.forEach((k, v)->{
            stringBuilder.append(k);
            stringBuilder.append("=");
            stringBuilder.append(v);
            stringBuilder.append("&");
        });

        stringBuilder.append("key=");
        stringBuilder.append(appKey);
        String raw = stringBuilder.toString();

        String sign = null;
        try {
            sign = getSHA256Str(raw);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 利用Apache的工具类实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str) throws NoSuchAlgorithmException {
        String encdeStr = "";
        try {
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

    public static boolean checkSign(JSONObject data, String appKey) {
        String inputSignature = data.getString("sign");
        data.remove("sign");
        String signature = sign(data, appKey, data.getLong("timestamp"));
        log.info("local signature: {}", signature);
        log.info("inputSignature signature: {}", inputSignature);
        if(signature.equals(inputSignature)) {
            return true;
        }
        return false;
    }

    public static boolean matches(String raw, String inputSign) {
        try {
            String sign = getSHA256Str(raw);
            return sign.equals(inputSign);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
//        JSONObject rawObj = JSON.parseObject("{\n" +
//                "  \"appId\": \"TXTJ2FY9NRVMKJC3\",\n" +
//                "  \"merchantOrderNo\": \"9287349k3adfsdjflsdj\",\n" +
//                "  \"merchantMemberNo\": \"tt_123456\",\n" +
//                "  \"amount\": \"190.5\",\n" +
//                "  \"serviceFee\": \"9.5\",\n" +
//                "  \"bankAccountName\": \"唐七七\",\n" +
//                "  \"bankAccount\": \"40008384509934995\",\n" +
//                "  \"bankName\": \"中国建设银行\",\n" +
//                "  \"bankBranch\": \"深圳分行\",\n" +
//                "  \"state\": 1,\n" +
//                "}");
//        long timestamp = System.currentTimeMillis()/1000;
//        rawObj.put("timestamp", timestamp);
//        String signature = signResponseData(rawObj, "TKNi3HZbX5l#Pt6#rN0mUAa9)aO#Zy4f", timestamp);
        JSONObject rawObj = JSON.parseObject("{\n" +
                "  \"appId\": \"CZYVXIV30UF125UU\",\n" +
                "  \"merchantOrderNo\": \"65876988707978\",\n" +
                "  \"merchantMemberNo\": \"tt_123456\",\n" +
                "  \"amount\": \"190\",\n" +
                "  \"actualAmount\": \"250\",\n" +
                "  \"serviceFee\": \"9.5\",\n" +
                "  \"state\": 1,\n" +
                "}");
        long timestamp = System.currentTimeMillis()/1000;
        rawObj.put("timestamp", timestamp);
        String signature = sign(rawObj, "CK6Z*THfs)7Yhm#FmecMrWZTOtuEfs6d", timestamp);
        rawObj.put("sign", signature);
        log.info(rawObj.toJSONString());
        String rawPassword = "password";
        String encoded = null;
        try {
            encoded = getSHA256Str(rawPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        log.warn("encoded：{}", encoded);
    }
}
