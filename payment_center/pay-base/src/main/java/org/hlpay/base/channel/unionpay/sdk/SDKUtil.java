package org.hlpay.base.channel.unionpay.sdk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.Deflater;
import java.util.zip.Inflater;




































public class SDKUtil
{
  public static boolean sign(Map<String, String> data, String encoding) {
    if (isEmpty(encoding)) {
      encoding = "UTF-8";
    }
    String signMethod = data.get("signMethod");
    String version = data.get("version");
    if (!"1.0.0".equals(version) && !"5.0.1".equals(version) && isEmpty(signMethod)) {
      LogUtil.writeErrorLog("signMethod must Not null");
      return false;
    }

    if (isEmpty(version)) {
      LogUtil.writeErrorLog("version must Not null");
      return false;
    }
    if ("01".equals(signMethod) || "1.0.0".equals(version) || "5.0.1".equals(version))
    { if ("5.0.0".equals(version) || "1.0.0".equals(version) || "5.0.1".equals(version)) {

        data.put("certId", CertUtil.getSignCertId());

        String stringData = coverMap2String(data);
        LogUtil.writeLog("打印排序后待签名请求报文串（交易返回11验证签名失败时可以用来同正确的进行比对）:[" + stringData + "]");
        byte[] byteSign = null;
        String stringSign = null;


        try {
          byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
          LogUtil.writeLog("打印摘要（交易返回11验证签名失败可以用来同正确的进行比对）:[" + new String(signDigest) + "]");
          byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(
                CertUtil.getSignCertPrivateKey(), signDigest));
          stringSign = new String(byteSign);

          data.put("signature", stringSign);
          return true;
        } catch (Exception e) {
          LogUtil.writeErrorLog("Sign Error", e);
          return false;
        }
      }  if ("5.1.0".equals(version)) {

        data.put("certId", CertUtil.getSignCertId());

        String stringData = coverMap2String(data);
        LogUtil.writeLog("打印待签名请求报文串（交易返回11验证签名失败时可以用来同正确的进行比对）:[" + stringData + "]");
        byte[] byteSign = null;
        String stringSign = null;


        try {
          byte[] signDigest = SecureUtil.sha256X16(stringData, encoding);
          LogUtil.writeLog("打印摘要（交易返回11验证签名失败可以用来同正确的进行比对）:[" + new String(signDigest) + "]");
          byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft256(
                CertUtil.getSignCertPrivateKey(), signDigest));
          stringSign = new String(byteSign);

          data.put("signature", stringSign);
          return true;
        } catch (Exception e) {
          LogUtil.writeErrorLog("Sign Error", e);
          return false;
        }
      }  }
    else { if ("11".equals(signMethod))
        return signBySecureKey(data, SDKConfig.getConfig()
            .getSecureKey(), encoding);
      if ("12".equals(signMethod))
        return signBySecureKey(data, SDKConfig.getConfig()
            .getSecureKey(), encoding);  }

    return false;
  }
















  public static boolean signBySecureKey(Map<String, String> data, String secureKey, String encoding) {
    if (isEmpty(encoding)) {
      encoding = "UTF-8";
    }
    if (isEmpty(secureKey)) {
      LogUtil.writeErrorLog("secureKey is empty");
      return false;
    }
    String signMethod = data.get("signMethod");
    if (isEmpty(signMethod)) {
      LogUtil.writeErrorLog("signMethod must Not null");
      return false;
    }

    if ("11".equals(signMethod)) {

      String stringData = coverMap2String(data);
      LogUtil.writeLog("待签名请求报文串:[" + stringData + "]");


      String strBeforeSha256 = stringData + "&" + SecureUtil.sha256X16Str(secureKey, encoding);
      String strAfterSha256 = SecureUtil.sha256X16Str(strBeforeSha256, encoding);


      data.put("signature", strAfterSha256);
      return true;
    }  if ("12".equals(signMethod)) {
      String stringData = coverMap2String(data);
      LogUtil.writeLog("待签名请求报文串:[" + stringData + "]");


      String strBeforeSM3 = stringData + "&" + SecureUtil.sm3X16Str(secureKey, encoding);
      String strAfterSM3 = SecureUtil.sm3X16Str(strBeforeSM3, encoding);

      data.put("signature", strAfterSM3);
      return true;
    }
    return false;
  }
















  public static boolean signByCertInfo(Map<String, String> data, String certPath, String certPwd, String encoding) {
    if (isEmpty(encoding)) {
      encoding = "UTF-8";
    }
    if (isEmpty(certPath) || isEmpty(certPwd)) {
      LogUtil.writeErrorLog("CertPath or CertPwd is empty");
      return false;
    }
    String signMethod = data.get("signMethod");
    String version = data.get("version");
    if (!"1.0.0".equals(version) && !"5.0.1".equals(version) && isEmpty(signMethod)) {
      LogUtil.writeErrorLog("signMethod must Not null");
      return false;
    }
    if (isEmpty(version)) {
      LogUtil.writeErrorLog("version must Not null");
      return false;
    }

    if ("01".equals(signMethod) || "1.0.0".equals(version) || "5.0.1".equals(version)) {
      if ("5.0.0".equals(version) || "1.0.0".equals(version) || "5.0.1".equals(version)) {

        data.put("certId", CertUtil.getCertIdByKeyStoreMap(certPath, certPwd));

        String stringData = coverMap2String(data);
        LogUtil.writeLog("待签名请求报文串:[" + stringData + "]");
        byte[] byteSign = null;
        String stringSign = null;


        try {
          byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
          byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(
                CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd), signDigest));
          stringSign = new String(byteSign);

          data.put("signature", stringSign);
          return true;
        } catch (Exception e) {
          LogUtil.writeErrorLog("Sign Error", e);
          return false;
        }
      }  if ("5.1.0".equals(version)) {

        data.put("certId", CertUtil.getCertIdByKeyStoreMap(certPath, certPwd));

        String stringData = coverMap2String(data);
        LogUtil.writeLog("待签名请求报文串:[" + stringData + "]");
        byte[] byteSign = null;
        String stringSign = null;


        try {
          byte[] signDigest = SecureUtil.sha256X16(stringData, encoding);
          byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft256(
                CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd), signDigest));
          stringSign = new String(byteSign);

          data.put("signature", stringSign);
          return true;
        } catch (Exception e) {
          LogUtil.writeErrorLog("Sign Error", e);
          return false;
        }
      }
    }

    return false;
  }










  public static boolean validateBySecureKey(Map<String, String> resData, String secureKey, String encoding) {
    LogUtil.writeLog("验签处理开始");
    if (isEmpty(encoding)) {
      encoding = "UTF-8";
    }
    String signMethod = resData.get("signMethod");
    if ("11".equals(signMethod)) {

      String stringSign = resData.get("signature");
      LogUtil.writeLog("签名原文：[" + stringSign + "]");

      String stringData = coverMap2String(resData);
      LogUtil.writeLog("待验签返回报文串：[" + stringData + "]");


      String strBeforeSha256 = stringData + "&" + SecureUtil.sha256X16Str(secureKey, encoding);
      String strAfterSha256 = SecureUtil.sha256X16Str(strBeforeSha256, encoding);

      return stringSign.equals(strAfterSha256);
    }  if ("12".equals(signMethod)) {

      String stringSign = resData.get("signature");
      LogUtil.writeLog("签名原文：[" + stringSign + "]");

      String stringData = coverMap2String(resData);
      LogUtil.writeLog("待验签返回报文串：[" + stringData + "]");


      String strBeforeSM3 = stringData + "&" + SecureUtil.sm3X16Str(secureKey, encoding);

      String strAfterSM3 = SecureUtil.sm3X16Str(strBeforeSM3, encoding);
      return stringSign.equals(strAfterSM3);
    }
    return false;
  }










  public static boolean validate(Map<String, String> resData, String encoding) {
    LogUtil.writeLog("验签处理开始");
    if (isEmpty(encoding)) {
      encoding = "UTF-8";
    }
    String signMethod = resData.get("signMethod");
    String version = resData.get("version");
    if ("01".equals(signMethod) || "1.0.0".equals(version) || "5.0.1".equals(version)) {

      if ("5.0.0".equals(version) || "1.0.0".equals(version) || "5.0.1".equals(version)) {
        String stringSign = resData.get("signature");
        LogUtil.writeLog("签名原文：[" + stringSign + "]");

        String certId = resData.get("certId");
        LogUtil.writeLog("对返回报文串验签使用的验签公钥序列号：[" + certId + "]");

        String stringData = coverMap2String(resData);
        LogUtil.writeLog("待验签返回报文串：[" + stringData + "]");

        try {
          return SecureUtil.validateSignBySoft(
              CertUtil.getValidatePublicKey(certId),
              SecureUtil.base64Decode(stringSign.getBytes(encoding)),
              SecureUtil.sha1X16(stringData, encoding));
        } catch (UnsupportedEncodingException e) {
          LogUtil.writeErrorLog(e.getMessage(), e);
        } catch (Exception e) {
          LogUtil.writeErrorLog(e.getMessage(), e);
        }
      } else if ("5.1.0".equals(version)) {

        String strCert = resData.get("signPubKeyCert");

        X509Certificate x509Cert = CertUtil.genCertificateByStr(strCert);
        if (x509Cert == null) {
          LogUtil.writeErrorLog("convert signPubKeyCert failed");
          return false;
        }

        if (!CertUtil.verifyCertificate(x509Cert)) {
          LogUtil.writeErrorLog("验证公钥证书失败，证书信息：[" + strCert + "]");
          return false;
        }


        String stringSign = resData.get("signature");
        LogUtil.writeLog("签名原文：[" + stringSign + "]");

        String stringData = coverMap2String(resData);
        LogUtil.writeLog("待验签返回报文串：[" + stringData + "]");

        try {
          boolean result = SecureUtil.validateSignBySoft256(x509Cert
              .getPublicKey(), SecureUtil.base64Decode(stringSign
                .getBytes(encoding)), SecureUtil.sha256X16(stringData, encoding));

          LogUtil.writeLog("验证签名" + (result ? "成功" : "失败"));
          return result;
        } catch (UnsupportedEncodingException e) {
          LogUtil.writeErrorLog(e.getMessage(), e);
        } catch (Exception e) {
          LogUtil.writeErrorLog(e.getMessage(), e);
        }
      }
    } else {
      if ("11".equals(signMethod)) {

        String stringSign = resData.get("signature");
        LogUtil.writeLog("签名原文：[" + stringSign + "]");

        String stringData = coverMap2String(resData);
        LogUtil.writeLog("待验签返回报文串：[" + stringData + "]");


        String strBeforeSha256 = stringData + "&" + SecureUtil.sha256X16Str(SDKConfig.getConfig()
            .getSecureKey(), encoding);
        String strAfterSha256 = SecureUtil.sha256X16Str(strBeforeSha256, encoding);

        boolean result = stringSign.equals(strAfterSha256);
        LogUtil.writeLog("验证签名" + (result ? "成功" : "失败"));
        return result;
      }  if ("12".equals(signMethod)) {

        String stringSign = resData.get("signature");
        LogUtil.writeLog("签名原文：[" + stringSign + "]");

        String stringData = coverMap2String(resData);
        LogUtil.writeLog("待验签返回报文串：[" + stringData + "]");


        String strBeforeSM3 = stringData + "&" + SecureUtil.sm3X16Str(SDKConfig.getConfig()
            .getSecureKey(), encoding);

        String strAfterSM3 = SecureUtil.sm3X16Str(strBeforeSM3, encoding);
        boolean result = stringSign.equals(strAfterSM3);
        LogUtil.writeLog("验证签名" + (result ? "成功" : "失败"));
        return result;
      }
    }  return false;
  }








  public static String coverMap2String(Map<String, String> data) {
    TreeMap<String, String> tree = new TreeMap<>();
    Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> en = it.next();
      if ("signature".equals(((String)en.getKey()).trim())) {
        continue;
      }
      tree.put(en.getKey(), en.getValue());
    }
    it = tree.entrySet().iterator();
    StringBuffer sf = new StringBuffer();
    while (it.hasNext()) {
      Map.Entry<String, String> en = it.next();
      sf.append((String)en.getKey() + "=" + (String)en.getValue() + "&");
    }

    return sf.substring(0, sf.length() - 1);
  }








  public static Map<String, String> coverResultString2Map(String result) {
    return convertResultStringToMap(result);
  }







  public static Map<String, String> convertResultStringToMap(String result) {
    Map<String, String> map = null;

    if (result != null && !"".equals(result.trim())) {
      if (result.startsWith("{") && result.endsWith("}")) {
        result = result.substring(1, result.length() - 1);
      }
      map = parseQString(result);
    }

    return map;
  }











  public static Map<String, String> parseQString(String str) {
    Map<String, String> map = new HashMap<>();
    int len = str.length();
    StringBuilder temp = new StringBuilder();

    String key = null;
    boolean isKey = true;
    boolean isOpen = false;
    char openName = Character.MIN_VALUE;
    if (len > 0) {
      for (int i = 0; i < len; i++) {
        char curChar = str.charAt(i);
        if (isKey) {

          if (curChar == '=') {
            key = temp.toString();
            temp.setLength(0);
            isKey = false;
          } else {
            temp.append(curChar);
          }
        } else {
          if (isOpen) {
            if (curChar == openName) {
              isOpen = false;
            }
          } else {

            if (curChar == '{') {
              isOpen = true;
              openName = '}';
            }
            if (curChar == '[') {
              isOpen = true;
              openName = ']';
            }
          }

          if (curChar == '&' && !isOpen) {
            putKeyValueToMap(temp, isKey, key, map);
            temp.setLength(0);
            isKey = true;
          } else {
            temp.append(curChar);
          }
        }
      }

      putKeyValueToMap(temp, isKey, key, map);
    }
    return map;
  }


  private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map) {
    if (isKey) {
      key = temp.toString();
      if (key.length() == 0) {
        throw new RuntimeException("QString format illegal");
      }
      map.put(key, "");
    } else {
      if (key.length() == 0) {
        throw new RuntimeException("QString format illegal");
      }
      map.put(key, temp.toString());
    }
  }











  public static int getEncryptCert(Map<String, String> resData, String encoding) {
    String strCert = resData.get("encryptPubKeyCert");
    String certType = resData.get("certType");
    if (isEmpty(strCert) || isEmpty(certType))
      return -1;
    X509Certificate x509Cert = CertUtil.genCertificateByStr(strCert);
    if ("01".equals(certType)) {

      if (!CertUtil.getEncryptCertId().equals(x509Cert
          .getSerialNumber().toString())) {

        String localCertPath = SDKConfig.getConfig().getEncryptCertPath();
        String newLocalCertPath = genBackupName(localCertPath);

        if (!copyFile(localCertPath, newLocalCertPath)) {
          return -1;
        }
        if (!writeFile(localCertPath, strCert, encoding))
          return -1;
        LogUtil.writeLog("save new encryptPubKeyCert success");
        CertUtil.resetEncryptCertPublicKey();
        return 1;
      }
      return 0;
    }

    if ("02".equals(certType))
    {















      return 0;
    }

    LogUtil.writeLog("unknown cerType:" + certType);
    return -1;
  }












  public static boolean copyFile(String srcFile, String destFile) {
    boolean flag = false;
    FileInputStream fin = null;
    FileOutputStream fout = null;
    FileChannel fcin = null;
    FileChannel fcout = null;

    try {
      fin = new FileInputStream(srcFile);
      fout = new FileOutputStream(destFile);

      fcin = fin.getChannel();
      fcout = fout.getChannel();

      ByteBuffer buffer = ByteBuffer.allocate(1024);

      while (true) {
        buffer.clear();

        int r = fcin.read(buffer);

        if (r == -1) {
          flag = true;

          break;
        }
        buffer.flip();

        fcout.write(buffer);
      }
      fout.flush();
    } catch (IOException e) {
      LogUtil.writeErrorLog("CopyFile fail", e);
    } finally {
      try {
        if (null != fin)
          fin.close();
        if (null != fout)
          fout.close();
        if (null != fcin)
          fcin.close();
        if (null != fcout)
          fcout.close();
      } catch (IOException ex) {
        LogUtil.writeErrorLog("Releases any system resources fail", ex);
      }
    }
    return flag;
  }













  public static boolean writeFile(String filePath, String fileContent, String encoding) {
    FileOutputStream fout = null;
    FileChannel fcout = null;
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }

    try {
      fout = new FileOutputStream(filePath);

      fcout = fout.getChannel();


      ByteBuffer buffer = ByteBuffer.wrap(fileContent.getBytes(encoding));
      fcout.write(buffer);
      fout.flush();
    } catch (FileNotFoundException e) {
      LogUtil.writeErrorLog("WriteFile fail", e);
      return false;
    } catch (IOException ex) {
      LogUtil.writeErrorLog("WriteFile fail", ex);
      return false;
    } finally {
      try {
        if (null != fout)
          fout.close();
        if (null != fcout)
          fcout.close();
      } catch (IOException ex) {
        LogUtil.writeErrorLog("Releases any system resources fail", ex);
        return false;
      }
    }
    return true;
  }








  public static String genBackupName(String fileName) {
    if (isEmpty(fileName))
      return "";
    int i = fileName.lastIndexOf(".");
    String leftFileName = fileName.substring(0, i);
    String rightFileName = fileName.substring(i + 1);
    String newFileName = leftFileName + "_backup" + "." + rightFileName;
    return newFileName;
  }


  public static byte[] readFileByNIO(String filePath) {
    FileInputStream in = null;
    FileChannel fc = null;
    ByteBuffer bf = null;
    try {
      in = new FileInputStream(filePath);
      fc = in.getChannel();
      bf = ByteBuffer.allocate((int)fc.size());
      fc.read(bf);
      return bf.array();
    } catch (Exception e) {
      LogUtil.writeErrorLog(e.getMessage());
      return null;
    } finally {
      try {
        if (null != fc) {
          fc.close();
        }
        if (null != in) {
          in.close();
        }
      } catch (Exception e) {
        LogUtil.writeErrorLog(e.getMessage());
        return null;
      }
    }
  }






  public static Map<String, String> filterBlank(Map<String, String> contentData) {
    LogUtil.writeLog("打印请求报文域 :");
    Map<String, String> submitFromData = new HashMap<>();
    Set<String> keyset = contentData.keySet();

    for (String key : keyset) {
      String value = contentData.get(key);
      if (value != null && !"".equals(value.trim())) {

        submitFromData.put(key, value.trim());
        LogUtil.writeLog(key + "-->" + String.valueOf(value));
      }
    }
    return submitFromData;
  }









  public static byte[] inflater(byte[] inputByte) throws IOException {
    int compressedDataLength = 0;
    Inflater compresser = new Inflater(false);
    compresser.setInput(inputByte, 0, inputByte.length);
    ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
    byte[] result = new byte[1024];
    try {
      while (!compresser.finished()) {
        compressedDataLength = compresser.inflate(result);
        if (compressedDataLength == 0) {
          break;
        }
        o.write(result, 0, compressedDataLength);
      }
    } catch (Exception ex) {
      System.err.println("Data format error!\n");
      ex.printStackTrace();
    } finally {
      o.close();
    }
    compresser.end();
    return o.toByteArray();
  }









  public static byte[] deflater(byte[] inputByte) throws IOException {
    int compressedDataLength = 0;
    Deflater compresser = new Deflater();
    compresser.setInput(inputByte);
    compresser.finish();
    ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
    byte[] result = new byte[1024];
    try {
      while (!compresser.finished()) {
        compressedDataLength = compresser.deflate(result);
        o.write(result, 0, compressedDataLength);
      }
    } finally {
      o.close();
    }
    compresser.end();
    return o.toByteArray();
  }








  public static boolean isEmpty(String s) {
    return (null == s || "".equals(s.trim()));
  }
}





