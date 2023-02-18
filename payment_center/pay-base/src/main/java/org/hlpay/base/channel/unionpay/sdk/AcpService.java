 package org.hlpay.base.channel.unionpay.sdk;

 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.UnsupportedEncodingException;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;















 public class AcpService
 {
   public static Map<String, String> sign(Map<String, String> reqData, String encoding) {
     reqData = SDKUtil.filterBlank(reqData);
     SDKUtil.sign(reqData, encoding);
     return reqData;
   }











   public static Map<String, String> sign(Map<String, String> reqData, String certPath, String certPwd, String encoding) {
     reqData = SDKUtil.filterBlank(reqData);
     SDKUtil.signByCertInfo(reqData, certPath, certPwd, encoding);
     return reqData;
   }











   public static Map<String, String> signByCertInfo(Map<String, String> reqData, String certPath, String certPwd, String encoding) {
     reqData = SDKUtil.filterBlank(reqData);
     SDKUtil.signByCertInfo(reqData, certPath, certPwd, encoding);
     return reqData;
   }









   public static Map<String, String> signBySecureKey(Map<String, String> reqData, String secureKey, String encoding) {
     reqData = SDKUtil.filterBlank(reqData);
     SDKUtil.signBySecureKey(reqData, secureKey, encoding);
     return reqData;
   }







   public static boolean validate(Map<String, String> rspData, String encoding) {
     return SDKUtil.validate(rspData, encoding);
   }







   public static boolean validateBySecureKey(Map<String, String> rspData, String secureKey, String encoding) {
     return SDKUtil.validateBySecureKey(rspData, secureKey, encoding);
   }








   public static boolean validateAppResponse(String jsonData, String encoding) {
     LogUtil.writeLog("控件应答信息验签处理开始：[" + jsonData + "]");
     if (SDKUtil.isEmpty(encoding)) {
       encoding = "UTF-8";
     }

     Pattern p = Pattern.compile("\\s*\"sign\"\\s*:\\s*\"([^\"]*)\"\\s*");
     Matcher m = p.matcher(jsonData);
     if (!m.find()) return false;
     String sign = m.group(1);

     p = Pattern.compile("\\s*\"data\"\\s*:\\s*\"([^\"]*)\"\\s*");
     m = p.matcher(jsonData);
     if (!m.find()) return false;
     String data = m.group(1);

     p = Pattern.compile("cert_id=(\\d*)");
     m = p.matcher(jsonData);
     if (!m.find()) return false;
     String certId = m.group(1);


     try {
       return SecureUtil.validateSignBySoft(
           CertUtil.getValidatePublicKey(certId), SecureUtil.base64Decode(sign
             .getBytes(encoding)), SecureUtil.sha1X16(data, encoding));
     }
     catch (UnsupportedEncodingException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     } catch (Exception e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     }
     return false;
   }










   public static Map<String, String> post(Map<String, String> reqData, String reqUrl, String encoding) {
     Map<String, String> rspData = new HashMap<>();
     LogUtil.writeLog("请求银联地址:" + reqUrl);

     HttpClient hc = new HttpClient(reqUrl, 30000, 30000);
     try {
       int status = hc.send(reqData, encoding);
       if (200 == status) {
         String resultString = hc.getResult();
         if (null != resultString && !"".equals(resultString)) {

           Map<String, String> tmpRspData = SDKUtil.convertResultStringToMap(resultString);
           rspData.putAll(tmpRspData);
         }
       } else {
         LogUtil.writeLog("返回http状态码[" + status + "]，请检查请求报文或者请求地址是否正确");
       }
     } catch (Exception e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     }
     return rspData;
   }








   public static String get(String reqUrl, String encoding) {
     LogUtil.writeLog("请求银联地址:" + reqUrl);

     HttpClient hc = new HttpClient(reqUrl, 30000, 30000);
     try {
       int status = hc.sendGet(encoding);
       if (200 == status) {
         String resultString = hc.getResult();
         if (null != resultString && !"".equals(resultString)) {
           return resultString;
         }
       } else {
         LogUtil.writeLog("返回http状态码[" + status + "]，请检查请求报文或者请求地址是否正确");
       }
     } catch (Exception e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     }
     return null;
   }









   public static String createAutoFormHtml(String reqUrl, Map<String, String> hiddens, String encoding) {
     StringBuffer sf = new StringBuffer();
     sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encoding + "\"/></head><body>");
     sf.append("<form id = \"pay_form\" action=\"" + reqUrl + "\" method=\"post\">");

     if (null != hiddens && 0 != hiddens.size()) {
       Set<Map.Entry<String, String>> set = hiddens.entrySet();
       Iterator<Map.Entry<String, String>> it = set.iterator();
       while (it.hasNext()) {
         Map.Entry<String, String> ey = it.next();
         String key = ey.getKey();
         String value = ey.getValue();
         sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + value + "\"/>");
       }
     }

     sf.append("</form>");
     sf.append("</body>");
     sf.append("<script type=\"text/javascript\">");
     sf.append("document.all.pay_form.submit();");
     sf.append("</script>");
     sf.append("</html>");
     return sf.toString();
   }








   public static String enCodeFileContent(String filePath, String encoding) {
     String baseFileContent = "";

     File file = new File(filePath);
     if (!file.exists()) {
       try {
         file.createNewFile();
       } catch (IOException e) {
         LogUtil.writeErrorLog(e.getMessage(), e);
       }
     }
     InputStream in = null;
     try {
       in = new FileInputStream(file);
       int fl = in.available();
       if (null != in) {
         byte[] s = new byte[fl];
         in.read(s, 0, fl);

         baseFileContent = new String(SecureUtil.base64Encode(SDKUtil.deflater(s)), encoding);
       }
     } catch (Exception e) {
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
     return baseFileContent;
   }









   public static String deCodeFileContent(Map<String, String> resData, String fileDirectory, String encoding) {
     String filePath = null;
     String fileContent = resData.get("fileContent");
     if (null != fileContent && !"".equals(fileContent)) {
       FileOutputStream out = null;
       try {
         byte[] fileArray = SDKUtil.inflater(
             SecureUtil.base64Decode(fileContent.getBytes(encoding)));
         if (SDKUtil.isEmpty(resData.get("fileName"))) {


           filePath = fileDirectory + File.separator + (String)resData.get("merId") + "_" + (String)resData.get("batchNo") + "_" + (String)resData.get("txnTime") + ".txt";
         } else {
           filePath = fileDirectory + File.separator + (String)resData.get("fileName");
         }
         File file = new File(filePath);
         if (file.exists()) {
           file.delete();
         }
         file.createNewFile();
         out = new FileOutputStream(file);
         out.write(fileArray, 0, fileArray.length);
         out.flush();
       } catch (UnsupportedEncodingException e) {
         LogUtil.writeErrorLog(e.getMessage(), e);
       } catch (IOException e) {
         LogUtil.writeErrorLog(e.getMessage(), e);
       } finally {
         try {
           out.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       }
     }
     return filePath;
   }







   public static String getFileContent(String fileContent, String encoding) {
     String fc = "";
     try {
       fc = new String(SDKUtil.inflater(SecureUtil.base64Decode(fileContent.getBytes())), encoding);
     } catch (UnsupportedEncodingException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     } catch (IOException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     }
     return fc;
   }



















   public static String getCustomerInfo(Map<String, String> customerInfoMap, String accNo, String encoding) {
     if (customerInfoMap.isEmpty())
       return "{}";
     StringBuffer sf = new StringBuffer("{");
     for (Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext(); ) {
       String key = it.next();
       String value = customerInfoMap.get(key);
       if (key.equals("pin")) {
         if (null == accNo || "".equals(accNo.trim())) {
           LogUtil.writeLog("送了密码（PIN），必须在getCustomerInfo参数中上传卡号");
           throw new RuntimeException("加密PIN没送卡号无法后续处理");
         }
         value = encryptPin(accNo, value, encoding);
       }

       sf.append(key).append("=").append(value);
       if (it.hasNext())
         sf.append("&");
     }
     String customerInfo = sf.append("}").toString();
     LogUtil.writeLog("组装的customerInfo明文：" + customerInfo);
     try {
       return new String(SecureUtil.base64Encode(sf.toString().getBytes(encoding)), encoding);
     }
     catch (UnsupportedEncodingException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     } catch (IOException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     }
     return customerInfo;
   }

















   public static String getCustomerInfoWithEncrypt(Map<String, String> customerInfoMap, String accNo, String encoding) {
     if (customerInfoMap.isEmpty())
       return "{}";
     StringBuffer sf = new StringBuffer("{");

     StringBuffer encryptedInfoSb = new StringBuffer("");

     for (Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext(); ) {
       String key = it.next();
       String value = customerInfoMap.get(key);
       if (key.equals("phoneNo") || key.equals("cvn2") || key.equals("expired")) {
         encryptedInfoSb.append(key).append("=").append(value).append("&"); continue;
       }
       if (key.equals("pin")) {
         if (null == accNo || "".equals(accNo.trim())) {
           LogUtil.writeLog("送了密码（PIN），必须在getCustomerInfoWithEncrypt参数中上传卡号");
           throw new RuntimeException("加密PIN没送卡号无法后续处理");
         }
         value = encryptPin(accNo, value, encoding);
       }

       sf.append(key).append("=").append(value).append("&");
     }


     if (!encryptedInfoSb.toString().equals("")) {
       encryptedInfoSb.setLength(encryptedInfoSb.length() - 1);
       LogUtil.writeLog("组装的customerInfo encryptedInfo明文：" + encryptedInfoSb.toString());
       sf.append("encryptedInfo").append("=").append(encryptData(encryptedInfoSb.toString(), encoding));
     } else {
       sf.setLength(sf.length() - 1);
     }

     String customerInfo = sf.append("}").toString();
     LogUtil.writeLog("组装的customerInfo明文：" + customerInfo);
     try {
       return new String(SecureUtil.base64Encode(sf.toString().getBytes(encoding)), encoding);
     } catch (UnsupportedEncodingException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     } catch (IOException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     }
     return customerInfo;
   }








   public static Map<String, String> parseCustomerInfo(String customerInfo, String encoding) {
     Map<String, String> customerInfoMap = null;
     try {
       byte[] b = SecureUtil.base64Decode(customerInfo.getBytes(encoding));
       String customerInfoNoBase64 = new String(b, encoding);
       LogUtil.writeLog("解base64后===>" + customerInfoNoBase64);

       customerInfoNoBase64 = customerInfoNoBase64.substring(1, customerInfoNoBase64.length() - 1);
       customerInfoMap = SDKUtil.parseQString(customerInfoNoBase64);
       if (customerInfoMap.containsKey("encryptedInfo")) {
         String encInfoStr = customerInfoMap.get("encryptedInfo");
         customerInfoMap.remove("encryptedInfo");
         String encryptedInfoStr = decryptData(encInfoStr, encoding);
         Map<String, String> encryptedInfoMap = SDKUtil.parseQString(encryptedInfoStr);
         customerInfoMap.putAll(encryptedInfoMap);
       }
     } catch (UnsupportedEncodingException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     } catch (IOException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     }
     return customerInfoMap;
   }









   public static Map<String, String> parseCustomerInfo(String customerInfo, String certPath, String certPwd, String encoding) {
     Map<String, String> customerInfoMap = null;
     try {
       byte[] b = SecureUtil.base64Decode(customerInfo.getBytes(encoding));
       String customerInfoNoBase64 = new String(b, encoding);
       LogUtil.writeLog("解base64后===>" + customerInfoNoBase64);

       customerInfoNoBase64 = customerInfoNoBase64.substring(1, customerInfoNoBase64.length() - 1);
       customerInfoMap = SDKUtil.parseQString(customerInfoNoBase64);
       if (customerInfoMap.containsKey("encryptedInfo")) {
         String encInfoStr = customerInfoMap.get("encryptedInfo");
         customerInfoMap.remove("encryptedInfo");
         String encryptedInfoStr = decryptData(encInfoStr, certPath, certPwd, encoding);
         Map<String, String> encryptedInfoMap = SDKUtil.parseQString(encryptedInfoStr);
         customerInfoMap.putAll(encryptedInfoMap);
       }
     } catch (UnsupportedEncodingException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     } catch (IOException e) {
       LogUtil.writeErrorLog(e.getMessage(), e);
     }
     return customerInfoMap;
   }








   public static String encryptPin(String accNo, String pin, String encoding) {
     return SecureUtil.encryptPin(accNo, pin, encoding,
         CertUtil.getEncryptCertPublicKey());
   }







   public static String encryptData(String data, String encoding) {
     return SecureUtil.encryptData(data, encoding,
         CertUtil.getEncryptCertPublicKey());
   }







   public static String decryptData(String base64EncryptedInfo, String encoding) {
     return SecureUtil.decryptData(base64EncryptedInfo, encoding,
         CertUtil.getSignCertPrivateKey());
   }










   public static String decryptData(String base64EncryptedInfo, String certPath, String certPwd, String encoding) {
     return SecureUtil.decryptData(base64EncryptedInfo, encoding,
         CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd));
   }








   public static String encryptTrack(String trackData, String encoding) {
     return SecureUtil.encryptData(trackData, encoding,
         CertUtil.getEncryptTrackPublicKey());
   }





   public static String getEncryptCertId() {
     return CertUtil.getEncryptCertId();
   }








   public static String base64Encode(String rawStr, String encoding) throws IOException {
     byte[] rawByte = rawStr.getBytes(encoding);
     return new String(SecureUtil.base64Encode(rawByte), encoding);
   }







   public static String base64Decode(String base64Str, String encoding) throws IOException {
     byte[] rawByte = base64Str.getBytes(encoding);
     return new String(SecureUtil.base64Decode(rawByte), encoding);
   }
















   public static String getCardTransData(Map<String, String> cardTransDataMap, Map<String, String> requestData, String encoding) {
     StringBuffer cardTransDataBuffer = new StringBuffer();

     if (cardTransDataMap.containsKey("track2Data")) {
       StringBuffer track2Buffer = new StringBuffer();
       track2Buffer.append(requestData.get("merId"))
         .append("|").append(requestData.get("orderId"))
         .append("|").append(requestData.get("txnTime"))
         .append("|").append((requestData.get("txnAmt") == null) ? Integer.valueOf(0) : requestData.get("txnAmt"))
         .append("|").append(cardTransDataMap.get("track2Data"));
       cardTransDataMap.put("track2Data",
           encryptData(track2Buffer.toString(), encoding));
     }

     if (cardTransDataMap.containsKey("track3Data")) {
       StringBuffer track3Buffer = new StringBuffer();
       track3Buffer.append(requestData.get("merId"))
         .append("|").append(requestData.get("orderId"))
         .append("|").append(requestData.get("txnTime"))
         .append("|").append((requestData.get("txnAmt") == null) ? Integer.valueOf(0) : requestData.get("txnAmt"))
         .append("|").append(cardTransDataMap.get("track3Data"));
       cardTransDataMap.put("track3Data",
           encryptData(track3Buffer.toString(), encoding));
     }

     return cardTransDataBuffer.append("{")
       .append(SDKUtil.coverMap2String(cardTransDataMap))
       .append("}").toString();
   }











   public static int updateEncryptCert(Map<String, String> resData, String encoding) {
     return SDKUtil.getEncryptCert(resData, encoding);
   }






   public static int genLuhn(String number) {
     return SecureUtil.genLuhn(number);
   }
 }





