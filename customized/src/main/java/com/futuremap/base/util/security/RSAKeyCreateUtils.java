package com.futuremap.base.util.security;

import lombok.SneakyThrows;

import java.io.File;
import java.util.Properties;

/**
 * @author Pullwind
 */
public class RSAKeyCreateUtils {
    // 公钥文件名
    public static final String pubFileName = "rsa_key.pub";
    // 私钥文件名
    public static final String priFileName = "rsa_key";

    /**
     * 当yml文件中没有指定自己的rsa公钥与私钥时就会自动生成并且放在c盘中
     * @return 返回生成文件存放的路径
     */
    @SneakyThrows
    public static String[] createDefaultRsaKeyFile(){
        String fileRoot = getFileRoot();
        if (fileRoot == null) {
            throw new RuntimeException("系统发生异常，导致无法获取当前系统信息");
        }
        //创建目录
        File file = new File(fileRoot + File.separator + "rsa");
        if (!file.exists()){
            file.mkdirs();
        }
        // 生成公钥与私钥
        RsaUtils.generateKey(file + File.separator + pubFileName
                , file + File.separator + priFileName, "jwt_token", 2048);
        return new String[]{file.toString()+File.separator + pubFileName, file.toString()+File.separator + priFileName};
    }

    /**
     * 根据不同系统创建不同目录
     * @return
     */
    public static String getFileRoot() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (null == os || "".equals(os)) {
            throw new RuntimeException("系统发生异常，导致无法获取当前系统信息");
        }
        os = os.toLowerCase();
        if (os.contains("windows")) {
            return prop.getProperty("user.home");
        }
        if (os.contains("mac")) {
            return "/Users";
        }
        if (os.contains("linux")) {
            return "/home";
        }
        return null;
    }
}
