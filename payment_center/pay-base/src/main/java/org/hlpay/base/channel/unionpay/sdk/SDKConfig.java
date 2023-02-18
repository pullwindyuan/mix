package org.hlpay.base.channel.unionpay.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;















































public class SDKConfig
{
  public static final String FILE_NAME = "acp_sdk.properties";
  private String frontRequestUrl;
  private String backRequestUrl;
  private String orderRequestUrl;
  private String singleQueryUrl;
  private String batchQueryUrl;
  private String batchTransUrl;
  private String fileTransUrl;
  private String signCertPath;
  private String signCertPwd;
  private String signCertType;
  private String encryptCertPath;
  private String validateCertDir;
  private String signCertDir;
  private String encryptTrackCertPath;
  private String encryptTrackKeyModulus;
  private String encryptTrackKeyExponent;
  private String cardRequestUrl;
  private String appRequestUrl;
  private String singleMode;
  private String secureKey;
  private String middleCertPath;
  private String rootCertPath;
  private boolean ifValidateCNName = true;
  private boolean ifValidateRemoteCert = false;
  private String signMethod = "01";

  private String version = "5.0.0";


  private String frontUrl;


  private String backUrl;

  private String frontFailUrl;

  private String jfFrontRequestUrl;

  private String jfBackRequestUrl;

  private String jfSingleQueryUrl;

  private String jfCardRequestUrl;

  private String jfAppRequestUrl;

  private String qrcBackTransUrl;

  private String qrcB2cIssBackTransUrl;

  private String qrcB2cMerBackTransUrl;

  private String zhrzFrontRequestUrl;

  private String zhrzBackRequestUrl;

  private String zhrzSingleQueryUrl;

  private String zhrzCardRequestUrl;

  private String zhrzAppRequestUrl;

  private String zhrzFaceRequestUrl;

  public static final String SDK_FRONT_URL = "acpsdk.frontTransUrl";

  public static final String SDK_BACK_URL = "acpsdk.backTransUrl";

  public static final String SDK_ORDER_URL = "acpsdk.orderTransUrl";

  public static final String SDK_SIGNQ_URL = "acpsdk.singleQueryUrl";

  public static final String SDK_BATQ_URL = "acpsdk.batchQueryUrl";

  public static final String SDK_BATTRANS_URL = "acpsdk.batchTransUrl";

  public static final String SDK_FILETRANS_URL = "acpsdk.fileTransUrl";

  public static final String SDK_CARD_URL = "acpsdk.cardTransUrl";

  public static final String SDK_APP_URL = "acpsdk.appTransUrl";

  public static final String JF_SDK_FRONT_TRANS_URL = "acpsdk.jfFrontTransUrl";

  public static final String JF_SDK_BACK_TRANS_URL = "acpsdk.jfBackTransUrl";

  public static final String JF_SDK_SINGLE_QUERY_URL = "acpsdk.jfSingleQueryUrl";

  public static final String JF_SDK_CARD_TRANS_URL = "acpsdk.jfCardTransUrl";

  public static final String JF_SDK_APP_TRANS_URL = "acpsdk.jfAppTransUrl";

  public static final String QRC_BACK_TRANS_URL = "acpsdk.qrcBackTransUrl";

  public static final String QRC_B2C_ISS_BACK_TRANS_URL = "acpsdk.qrcB2cIssBackTransUrl";

  public static final String QRC_B2C_MER_BACK_TRANS_URL = "acpsdk.qrcB2cMerBackTransUrl";

  public static final String ZHRZ_SDK_FRONT_TRANS_URL = "acpsdk.zhrzFrontTransUrl";

  public static final String ZHRZ_SDK_BACK_TRANS_URL = "acpsdk.zhrzBackTransUrl";

  public static final String ZHRZ_SDK_SINGLE_QUERY_URL = "acpsdk.zhrzSingleQueryUrl";

  public static final String ZHRZ_SDK_CARD_TRANS_URL = "acpsdk.zhrzCardTransUrl";

  public static final String ZHRZ_SDK_APP_TRANS_URL = "acpsdk.zhrzAppTransUrl";

  public static final String ZHRZ_SDK_FACE_TRANS_URL = "acpsdk.zhrzFaceTransUrl";

  public static final String SDK_SIGNCERT_PATH = "acpsdk.signCert.path";

  public static final String SDK_SIGNCERT_PWD = "acpsdk.signCert.pwd";

  public static final String SDK_SIGNCERT_TYPE = "acpsdk.signCert.type";

  public static final String SDK_ENCRYPTCERT_PATH = "acpsdk.encryptCert.path";

  public static final String SDK_ENCRYPTTRACKCERT_PATH = "acpsdk.encryptTrackCert.path";

  public static final String SDK_ENCRYPTTRACKKEY_MODULUS = "acpsdk.encryptTrackKey.modulus";

  public static final String SDK_ENCRYPTTRACKKEY_EXPONENT = "acpsdk.encryptTrackKey.exponent";

  public static final String SDK_VALIDATECERT_DIR = "acpsdk.validateCert.dir";

  public static final String SDK_CVN_ENC = "acpsdk.cvn2.enc";

  public static final String SDK_DATE_ENC = "acpsdk.date.enc";

  public static final String SDK_PAN_ENC = "acpsdk.pan.enc";

  public static final String SDK_SINGLEMODE = "acpsdk.singleMode";

  public static final String SDK_SECURITYKEY = "acpsdk.secureKey";

  public static final String SDK_ROOTCERT_PATH = "acpsdk.rootCert.path";

  public static final String SDK_MIDDLECERT_PATH = "acpsdk.middleCert.path";

  public static final String SDK_IF_VALIDATE_CN_NAME = "acpsdk.ifValidateCNName";

  public static final String SDK_IF_VALIDATE_REMOTE_CERT = "acpsdk.ifValidateRemoteCert";

  public static final String SDK_SIGN_METHOD = "acpsdk.signMethod";

  public static final String SDK_VERSION = "acpsdk.version";

  public static final String SDK_BACKURL = "acpsdk.backUrl";

  public static final String SDK_FRONTURL = "acpsdk.frontUrl";

  public static final String SDK_FRONT_FAIL_URL = "acpsdk.frontFailUrl";

  private static SDKConfig config = new SDKConfig();





  private Properties properties;





  public static SDKConfig getConfig() {
    return config;
  }







  public void loadPropertiesFromPath(String rootPath) {
    if (rootPath != null && !"".equals(rootPath.trim())) {
      LogUtil.writeLog("从路径读取配置文件: " + rootPath + File.separator + "acp_sdk.properties");
      File file = new File(rootPath + File.separator + "acp_sdk.properties");
      InputStream in = null;
      if (file.exists()) {
        try {
          in = new FileInputStream(file);
          this.properties = new Properties();
          this.properties.load(in);
          loadProperties(this.properties);
        } catch (FileNotFoundException e) {
          LogUtil.writeErrorLog(e.getMessage(), e);
        } catch (IOException e) {
          LogUtil.writeErrorLog(e.getMessage(), e);
        } finally {
          if (null != in) {
            try {
              in.close();
            } catch (IOException e) {
              LogUtil.writeErrorLog(e.getMessage(), e);
            }
          }
        }
      } else {

        LogUtil.writeErrorLog(rootPath + "acp_sdk.properties" + "不存在,加载参数失败");
      }
    } else {
      loadPropertiesFromSrc();
    }
  }





  public void loadPropertiesFromSrc() {
    InputStream in = null;
    try {
      LogUtil.writeLog("从classpath: " + SDKConfig.class.getClassLoader().getResource("").getPath() + " 获取属性文件" + "acp_sdk.properties");
      in = SDKConfig.class.getClassLoader().getResourceAsStream("acp_sdk.properties");
      if (null != in) {
        this.properties = new Properties();
        try {
          this.properties.load(in);
        } catch (IOException e) {
          throw e;
        }
      } else {
        LogUtil.writeErrorLog("acp_sdk.properties属性文件未能在classpath指定的目录下 " + SDKConfig.class.getClassLoader().getResource("").getPath() + " 找到!");
        return;
      }
      loadProperties(this.properties);
    } catch (IOException e) {
      LogUtil.writeErrorLog(e.getMessage(), e);
    } finally {
      if (null != in) {
        try {
          in.close();
        } catch (IOException e) {
          LogUtil.writeErrorLog(e.getMessage(), e);
        }
      }
    }
  }






  public void loadProperties(Properties pro) {
    LogUtil.writeLog("开始从属性文件中加载配置项");
    String value = null;

    value = pro.getProperty("acpsdk.signCert.path");
    if (!SDKUtil.isEmpty(value)) {
      this.signCertPath = value.trim();
      LogUtil.writeLog("配置项：私钥签名证书路径==>acpsdk.signCert.path==>" + value + " 已加载");
    }
    value = pro.getProperty("acpsdk.signCert.pwd");
    if (!SDKUtil.isEmpty(value)) {
      this.signCertPwd = value.trim();
      LogUtil.writeLog("配置项：私钥签名证书密码==>acpsdk.signCert.pwd 已加载");
    }
    value = pro.getProperty("acpsdk.signCert.type");
    if (!SDKUtil.isEmpty(value)) {
      this.signCertType = value.trim();
      LogUtil.writeLog("配置项：私钥签名证书类型==>acpsdk.signCert.type==>" + value + " 已加载");
    }
    value = pro.getProperty("acpsdk.encryptCert.path");
    if (!SDKUtil.isEmpty(value)) {
      this.encryptCertPath = value.trim();
      LogUtil.writeLog("配置项：敏感信息加密证书==>acpsdk.encryptCert.path==>" + value + " 已加载");
    }
    value = pro.getProperty("acpsdk.validateCert.dir");
    if (!SDKUtil.isEmpty(value)) {
      this.validateCertDir = value.trim();
      LogUtil.writeLog("配置项：验证签名证书路径(这里配置的是目录，不要指定到公钥文件)==>acpsdk.validateCert.dir==>" + value + " 已加载");
    }
    value = pro.getProperty("acpsdk.frontTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.frontRequestUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.backTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.backRequestUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.orderTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.orderRequestUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.batchQueryUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.batchQueryUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.batchTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.batchTransUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.fileTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.fileTransUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.singleQueryUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.singleQueryUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.cardTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.cardRequestUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.appTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.appRequestUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.encryptTrackCert.path");
    if (!SDKUtil.isEmpty(value)) {
      this.encryptTrackCertPath = value.trim();
    }

    value = pro.getProperty("acpsdk.secureKey");
    if (!SDKUtil.isEmpty(value)) {
      this.secureKey = value.trim();
    }
    value = pro.getProperty("acpsdk.rootCert.path");
    if (!SDKUtil.isEmpty(value)) {
      this.rootCertPath = value.trim();
    }
    value = pro.getProperty("acpsdk.middleCert.path");
    if (!SDKUtil.isEmpty(value)) {
      this.middleCertPath = value.trim();
    }


    value = pro.getProperty("acpsdk.jfFrontTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.jfFrontRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.jfBackTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.jfBackRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.jfSingleQueryUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.jfSingleQueryUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.jfCardTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.jfCardRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.jfAppTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.jfAppRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.qrcBackTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.qrcBackTransUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.qrcB2cIssBackTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.qrcB2cIssBackTransUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.qrcB2cMerBackTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.qrcB2cMerBackTransUrl = value.trim();
    }


    value = pro.getProperty("acpsdk.zhrzFrontTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.zhrzFrontRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.zhrzBackTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.zhrzBackRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.zhrzSingleQueryUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.zhrzSingleQueryUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.zhrzCardTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.zhrzCardRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.zhrzAppTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.zhrzAppRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.zhrzFaceTransUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.zhrzFaceRequestUrl = value.trim();
    }

    value = pro.getProperty("acpsdk.encryptTrackKey.exponent");
    if (!SDKUtil.isEmpty(value)) {
      this.encryptTrackKeyExponent = value.trim();
    }

    value = pro.getProperty("acpsdk.encryptTrackKey.modulus");
    if (!SDKUtil.isEmpty(value)) {
      this.encryptTrackKeyModulus = value.trim();
    }

    value = pro.getProperty("acpsdk.ifValidateCNName");
    if (!SDKUtil.isEmpty(value) &&
      "false".equals(value.trim())) {
      this.ifValidateCNName = false;
    }

    value = pro.getProperty("acpsdk.ifValidateRemoteCert");
    if (!SDKUtil.isEmpty(value) &&
      "true".equals(value.trim())) {
      this.ifValidateRemoteCert = true;
    }

    value = pro.getProperty("acpsdk.signMethod");
    if (!SDKUtil.isEmpty(value)) {
      this.signMethod = value.trim();
    }

    value = pro.getProperty("acpsdk.signMethod");
    if (!SDKUtil.isEmpty(value)) {
      this.signMethod = value.trim();
    }
    value = pro.getProperty("acpsdk.version");
    if (!SDKUtil.isEmpty(value)) {
      this.version = value.trim();
    }
    value = pro.getProperty("acpsdk.frontUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.frontUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.backUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.backUrl = value.trim();
    }
    value = pro.getProperty("acpsdk.frontFailUrl");
    if (!SDKUtil.isEmpty(value)) {
      this.frontFailUrl = value.trim();
    }
  }


  public String getFrontRequestUrl() {
    return this.frontRequestUrl;
  }

  public void setFrontRequestUrl(String frontRequestUrl) {
    this.frontRequestUrl = frontRequestUrl;
  }

  public String getBackRequestUrl() {
    return this.backRequestUrl;
  }

  public void setBackRequestUrl(String backRequestUrl) {
    this.backRequestUrl = backRequestUrl;
  }

  public String getOrderRequestUrl() {
    return this.orderRequestUrl;
  }

  public void setOrderRequestUrl(String orderRequestUrl) {
    this.orderRequestUrl = orderRequestUrl;
  }

  public String getSignCertPath() {
    return this.signCertPath;
  }

  public void setSignCertPath(String signCertPath) {
    this.signCertPath = signCertPath;
  }

  public String getSignCertPwd() {
    return this.signCertPwd;
  }

  public void setSignCertPwd(String signCertPwd) {
    this.signCertPwd = signCertPwd;
  }

  public String getSignCertType() {
    return this.signCertType;
  }

  public void setSignCertType(String signCertType) {
    this.signCertType = signCertType;
  }

  public String getEncryptCertPath() {
    return this.encryptCertPath;
  }

  public void setEncryptCertPath(String encryptCertPath) {
    this.encryptCertPath = encryptCertPath;
  }

  public String getValidateCertDir() {
    return this.validateCertDir;
  }

  public void setValidateCertDir(String validateCertDir) {
    this.validateCertDir = validateCertDir;
  }

  public String getSingleQueryUrl() {
    return this.singleQueryUrl;
  }

  public void setSingleQueryUrl(String singleQueryUrl) {
    this.singleQueryUrl = singleQueryUrl;
  }

  public String getBatchQueryUrl() {
    return this.batchQueryUrl;
  }

  public void setBatchQueryUrl(String batchQueryUrl) {
    this.batchQueryUrl = batchQueryUrl;
  }

  public String getBatchTransUrl() {
    return this.batchTransUrl;
  }

  public void setBatchTransUrl(String batchTransUrl) {
    this.batchTransUrl = batchTransUrl;
  }

  public String getFileTransUrl() {
    return this.fileTransUrl;
  }

  public void setFileTransUrl(String fileTransUrl) {
    this.fileTransUrl = fileTransUrl;
  }

  public String getSignCertDir() {
    return this.signCertDir;
  }

  public void setSignCertDir(String signCertDir) {
    this.signCertDir = signCertDir;
  }

  public Properties getProperties() {
    return this.properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public String getCardRequestUrl() {
    return this.cardRequestUrl;
  }

  public void setCardRequestUrl(String cardRequestUrl) {
    this.cardRequestUrl = cardRequestUrl;
  }

  public String getAppRequestUrl() {
    return this.appRequestUrl;
  }

  public void setAppRequestUrl(String appRequestUrl) {
    this.appRequestUrl = appRequestUrl;
  }

  public String getEncryptTrackCertPath() {
    return this.encryptTrackCertPath;
  }

  public void setEncryptTrackCertPath(String encryptTrackCertPath) {
    this.encryptTrackCertPath = encryptTrackCertPath;
  }

  public String getJfFrontRequestUrl() {
    return this.jfFrontRequestUrl;
  }

  public void setJfFrontRequestUrl(String jfFrontRequestUrl) {
    this.jfFrontRequestUrl = jfFrontRequestUrl;
  }

  public String getJfBackRequestUrl() {
    return this.jfBackRequestUrl;
  }

  public void setJfBackRequestUrl(String jfBackRequestUrl) {
    this.jfBackRequestUrl = jfBackRequestUrl;
  }

  public String getJfSingleQueryUrl() {
    return this.jfSingleQueryUrl;
  }

  public void setJfSingleQueryUrl(String jfSingleQueryUrl) {
    this.jfSingleQueryUrl = jfSingleQueryUrl;
  }

  public String getJfCardRequestUrl() {
    return this.jfCardRequestUrl;
  }

  public void setJfCardRequestUrl(String jfCardRequestUrl) {
    this.jfCardRequestUrl = jfCardRequestUrl;
  }

  public String getJfAppRequestUrl() {
    return this.jfAppRequestUrl;
  }

  public void setJfAppRequestUrl(String jfAppRequestUrl) {
    this.jfAppRequestUrl = jfAppRequestUrl;
  }

  public String getSingleMode() {
    return this.singleMode;
  }

  public void setSingleMode(String singleMode) {
    this.singleMode = singleMode;
  }

  public String getEncryptTrackKeyExponent() {
    return this.encryptTrackKeyExponent;
  }

  public void setEncryptTrackKeyExponent(String encryptTrackKeyExponent) {
    this.encryptTrackKeyExponent = encryptTrackKeyExponent;
  }

  public String getEncryptTrackKeyModulus() {
    return this.encryptTrackKeyModulus;
  }

  public void setEncryptTrackKeyModulus(String encryptTrackKeyModulus) {
    this.encryptTrackKeyModulus = encryptTrackKeyModulus;
  }

  public String getSecureKey() {
    return this.secureKey;
  }

  public void setSecureKey(String securityKey) {
    this.secureKey = securityKey;
  }

  public String getMiddleCertPath() {
    return this.middleCertPath;
  }

  public void setMiddleCertPath(String middleCertPath) {
    this.middleCertPath = middleCertPath;
  }

  public boolean isIfValidateCNName() {
    return this.ifValidateCNName;
  }

  public void setIfValidateCNName(boolean ifValidateCNName) {
    this.ifValidateCNName = ifValidateCNName;
  }

  public boolean isIfValidateRemoteCert() {
    return this.ifValidateRemoteCert;
  }

  public void setIfValidateRemoteCert(boolean ifValidateRemoteCert) {
    this.ifValidateRemoteCert = ifValidateRemoteCert;
  }

  public String getSignMethod() {
    return this.signMethod;
  }

  public void setSignMethod(String signMethod) {
    this.signMethod = signMethod;
  }
  public String getQrcBackTransUrl() {
    return this.qrcBackTransUrl;
  }

  public void setQrcBackTransUrl(String qrcBackTransUrl) {
    this.qrcBackTransUrl = qrcBackTransUrl;
  }

  public String getQrcB2cIssBackTransUrl() {
    return this.qrcB2cIssBackTransUrl;
  }

  public void setQrcB2cIssBackTransUrl(String qrcB2cIssBackTransUrl) {
    this.qrcB2cIssBackTransUrl = qrcB2cIssBackTransUrl;
  }

  public String getQrcB2cMerBackTransUrl() {
    return this.qrcB2cMerBackTransUrl;
  }

  public void setQrcB2cMerBackTransUrl(String qrcB2cMerBackTransUrl) {
    this.qrcB2cMerBackTransUrl = qrcB2cMerBackTransUrl;
  }

  public String getZhrzFrontRequestUrl() {
    return this.zhrzFrontRequestUrl;
  }

  public String getZhrzBackRequestUrl() {
    return this.zhrzBackRequestUrl;
  }

  public String getZhrzSingleQueryUrl() {
    return this.zhrzSingleQueryUrl;
  }

  public String getZhrzCardRequestUrl() {
    return this.zhrzCardRequestUrl;
  }

  public String getZhrzAppRequestUrl() {
    return this.zhrzAppRequestUrl;
  }

  public String getZhrzFaceRequestUrl() {
    return this.zhrzFaceRequestUrl;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getFrontUrl() {
    return this.frontUrl;
  }

  public void setFrontUrl(String frontUrl) {
    this.frontUrl = frontUrl;
  }

  public String getBackUrl() {
    return this.backUrl;
  }

  public void setBackUrl(String backUrl) {
    this.backUrl = backUrl;
  }

  public String getFrontFailUrl() {
    return this.frontFailUrl;
  }

  public String getRootCertPath() {
    return this.rootCertPath;
  }

  public void setRootCertPath(String rootCertPath) {
    this.rootCertPath = rootCertPath;
  }
}





