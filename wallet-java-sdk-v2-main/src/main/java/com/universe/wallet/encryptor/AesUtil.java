package com.universe.wallet.encryptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.BytesEncryptor;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AES对称加密工具类
 * 为了让性能最高，尽可能的使用静态加解密器（静态就是一次初始化一个加解密器
 * 后重复使用，前提是密码和盐不变），如果密码或者盐会频繁变动就只能使用动态
 * 加解密器，每次加解密操作都需要新建一个加解密器
 * 使用AES/GCM/NoPadding
 *  //为什么没有使用工程中现有的Encrypt工具类：
 *  //1、没有GCM模式（测试性能的时候我增加了该模式）；
 *  //2、性能上不及本工具类的静态加解密方法（高出4倍以上的性能）
 *   //3、不支持安全密码模式，应对密码字典攻击的能力低。
 * @author Pullwind
 * @date 2022年2月26日
 *
 */
@Slf4j
public class AesUtil {
    /**
     * 使用外部直接输入的加解密密码的加解码器，是否能够应对密码字典攻击由调用者决定
     */
    private static ConcurrentHashMap<String, BytesEncryptor> extendAesBytesEncryptorMap = new ConcurrentHashMap<>(10);

    /**
     * 初始化一个全局的非安全密码加密器，用于密码不变的情况下重复使用
     * @param encryptPass 加密密码
     */
    public static BytesEncryptor initWeakKeyEncryptor(String encryptPass) {
        BytesEncryptor bytesEncryptor = ExtendAesBytesEncryptor.weakKeyStronger(encryptPass);
        extendAesBytesEncryptorMap.put(encryptPass, bytesEncryptor);
        return bytesEncryptor;
    }

    private static byte[] encrypt(BytesEncryptor encryptor, byte[] content) {
        return encryptor.encrypt(content);
    }

    private static byte[] decrypt(BytesEncryptor encryptor, byte[] content) {
        return encryptor.decrypt(content);
    }

    /**
     * 静态AES 加密操作，字符编码格式为UTF-8，密码使用的非安全随机密码
     *
     * @param content     待加密内容

     * @return 返回密文bytes
     */
    public static byte[] staticWeakKeyEncrypt(String encryptPass, byte[] content) {
        BytesEncryptor bytesEncryptor = getBytesEncryptor(encryptPass);
        return encrypt(bytesEncryptor, content);
    }

    /**
     * AES 解密操作，字符编码格式为UTF-8，密码使用的非安全随机密码
     *
     * @param content     待解密内容

     * @return 返回解密后bytes
     */
    public static byte[] staticWeakKeyDecrypt(String encryptPass, byte[] content) {
        BytesEncryptor bytesEncryptor = getBytesEncryptor(encryptPass);
        return decrypt(bytesEncryptor, content);
    }

    /**
     * 静态AES 加密操作，字符编码格式为UTF-8，密码使用的非安全随机密码
     *
     * @param content 待加密内容

     * @return 返回密文bytes
     */
    public static String staticWeakKeyEncryptToBase64(String encryptPass, byte[] content) {
        BytesEncryptor bytesEncryptor = getBytesEncryptor(encryptPass);
        return Base64.getUrlEncoder().encodeToString(encrypt(bytesEncryptor, content));
    }

    /**
     * 静态AES 加密操作，字符编码格式为UTF-8，密码使用的非安全随机密码
     *
     * @param content 待加密内容

     * @return 返回密文bytes
     */
    public static String staticWeakKeyEncryptFromStringToBase64(String encryptPass, String content) {
        BytesEncryptor bytesEncryptor = getBytesEncryptor(encryptPass);
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        return Base64.getUrlEncoder().encodeToString(encrypt(bytesEncryptor, contentBytes));
    }

    /**
     * AES 解密操作，字符编码格式为UTF-8，密码使用的非安全随机密码
     *
     * @param content     待解密内容

     * @return 返回解密后bytes
     */
    public static String staticWeakKeyDecryptFromBase64ToString(String encryptPass, String content) {
        BytesEncryptor bytesEncryptor = getBytesEncryptor(encryptPass);
        return new String(decrypt(bytesEncryptor, Base64.getUrlDecoder().decode(content)));
    }

    public static BytesEncryptor getBytesEncryptor(String encryptPass) {
        BytesEncryptor bytesEncryptor = extendAesBytesEncryptorMap.get(encryptPass);
        if(bytesEncryptor == null) {
            bytesEncryptor = initWeakKeyEncryptor(encryptPass);
        }
        return bytesEncryptor;
    }
    public static void main(String[] args) {
        //获取测试充值回调接口的参数：充值的加密key请使用配置文件中的
        // deposit:
        //      #由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对，充值的appId是以CZ开头的
        //      appId: CZGB5WHUMJXLTRFK
        //      #由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对
        //      appKey: CKtOay%jvLaMTqBT0pmNcrDyavH19bui
        String depositAppKey = "CKtOay%jvLaMTqBT0pmNcrDyavH19bui";
        String depositNotifyContent = "{\n" +
                "  \"merchantOrderNo\": \"xxx\",\n" +
                "  \"merchantMemberNo\": \"tt_123456\",\n" +
                "  \"amount\": \"190\",\n" +
                "  \"actualAmount\": \"250\",\n" +
                "  \"serviceFee\": \"9.5\",\n" +
                "  \"state\": 1,\n" +
                "  \"timestamp\": 1640146252\n" +
                "}";
        String depositEncryptString = staticWeakKeyEncryptFromStringToBase64(depositAppKey, depositNotifyContent);
        log.info("充值回调测试参数：{\"encryptString\":\"{}\"}", depositEncryptString);
        //获取测试充值回调接口的参数：充值的加密key请使用配置文件中的
        // deposit:
        //      #由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对，充值的appId是以CZ开头的
        //      appId: CZGB5WHUMJXLTRFK
        //      #由钱包分配，千万不要配置错误，充值和提现是不同的appId-appKey对
        //      appKey: CKtOay%jvLaMTqBT0pmNcrDyavH19bui
        String withdrawAppKey = "TKwBkQ0rtb$vy4L5xQQCbYUdU7SEZy7X";
        String withdrawNotifyContent = "{\n" +
                "  \"merchantOrderNo\": \"9287349k3adfsdjflsdj\",\n" +
                "  \"merchantMemberNo\": \"tt_123456\",\n" +
                "  \"amount\": \"190.5\",\n" +
                "  \"serviceFee\": \"9.5\",\n" +
                "  \"state\": 1,\n" +
                "  \"timestamp\": 1640146252\n" +
                "}";
        String withdrawEncryptString = staticWeakKeyEncryptFromStringToBase64(withdrawAppKey, withdrawNotifyContent);
        log.info("提现回调测试参数：{\"encryptString\":\"{}\"}", withdrawEncryptString);

        //API文档中给出的加密验证方法，对给出的示例原文加密
        String content = "{\"appId\":\"\",\"merchantOrderNo\":\"iizbzVQOcLiYjUgUeIZhgCOEBfjRwo\"," +
                "\"merchantMemberNo\":\"ZjzKDvufenWYLCxQNkGL\",\"paymentMethod\":1,\"accountName\":\"张三\"," +
                "\"account\":\"3234456452342346\",\"bankAccountName\":\"\",\"bankAccount\":\"\"," +
                "\"bankName\":\"建设银行\",\"bankBranch\":\"上海支行\",\"notifyUrl\":\"https://xxx.xxxx.com/order\"," +
                "\"timestamp\":1648435896,\"sign\":\"\",\"amount\":\"1.1\",\"notAudit\":false,\"qrCodeUrl\":\"\"}";

        String weakPass = "TK*K0T1(%6UCT(tlpZVB0516E&LOx#!@";
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

        String base64 = staticWeakKeyEncryptToBase64(weakPass, contentBytes);
        log.info("非安全密码加密base64结果：{}", base64);

        //API文档中给出的接密验证方法，对给出的示例密文进行解密
        String encryptString = "uA8J6YGKdWFf7svlW_ihrjlD4044WQb3M8n78RTgIMDgHE1QfzUUD_Yn6cEMWWQ3aM-RcNhO07uS0rMZl2fs7nGrz9" +
                "sH4VtmZLv03sodWOtbBwrJJpZosid3F5HiFzIbfCitDvOY7-KDHo4mKdw3qnukKxiGNpr7Sy45EEUHeQBq4tRALteWP51QOzAj" +
                "Ice-v-LQxg19pt8wkxXv8GO6yxIFNrPR29cR9PrJFjRiOjIkLDnkTW0zClyaSs_zgw3IWJfiNPsVRMUTw10aUN_Iri0sB3cVLtL" +
                "3wVz-9_hQNUhqMNZNz1NshFWe3ygYZAdQPvjxnkn1im4xUHEQvwKG5ojzpNcEXP251cxI9Y-aIxCxCcOMxCAPgemihN2-6fKRZD" +
                "ukBJWSLhLfOodh_xLZVGtUtUXsB6lFEnRZpSGl9xkOVRB-Fn8Wa_1qL7fpfGo8DRuUstNoYljQh_sA0-esGwZu_qRG_6avom9bsD" +
                "_enXPcHfrsG7oDN4LCEcHviHzfFr2tJBbROFKIHKTYNcvX8qXWXnuWFwcX3YOfSWDTHAI=";
        String decrypted = staticWeakKeyDecryptFromBase64ToString(weakPass, encryptString);
        log.info("API文档示例解密结果：{}", decrypted);

        if(content.equals(decrypted)) {
            log.info("API文档示例解密验证结果：《通过》");
        }else {
            log.info("API文档示例解密验证结果：《无法解密》");
        }
    }
}