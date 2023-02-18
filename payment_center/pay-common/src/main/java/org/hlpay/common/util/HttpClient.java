 package org.hlpay.common.util;

 import java.io.BufferedOutputStream;
 import java.io.BufferedReader;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import java.net.HttpURLConnection;
 import java.net.MalformedURLException;
 import java.net.ProtocolException;
 import java.net.URISyntaxException;
 import java.net.URL;
 import java.net.URLConnection;
 import java.net.URLEncoder;
 import java.security.KeyManagementException;
 import java.security.KeyStoreException;
 import java.security.NoSuchAlgorithmException;
 import java.security.SecureRandom;
 import java.security.UnrecoverableKeyException;
 import java.security.cert.CertificateException;
 import java.security.cert.X509Certificate;
 import java.util.HashMap;
 import java.util.Map;
 import javax.net.ssl.HttpsURLConnection;
 import javax.net.ssl.SSLContext;
 import javax.net.ssl.SSLSocketFactory;
 import javax.net.ssl.TrustManager;
 import javax.net.ssl.X509TrustManager;
 import org.apache.http.Header;
 import org.apache.http.HttpEntity;
 import org.apache.http.client.config.RequestConfig;
 import org.apache.http.client.methods.CloseableHttpResponse;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.client.methods.HttpUriRequest;
 import org.apache.http.entity.StringEntity;
 import org.apache.http.impl.client.HttpClientBuilder;
 import org.apache.http.message.BasicHeader;
 import org.apache.http.util.EntityUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class HttpClient
 {
   private static final String APPLICATION_JSON = "application/json";
   private static Logger _log = LoggerFactory.getLogger(HttpClient.class);



   private static final String CONTENT_TYPE_TEXT_JSON = "text/json";



   private static final String CHARSET_UTF_8 = "UTF-8";



   private static final String USER_AGENT_VALUE = "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";



   private static final String JKS_CA_FILENAME = "tenpay_cacert.jks";



   private static final String JKS_CA_ALIAS = "tenpay";



   private static final String JKS_CA_PASSWORD = "";



   private File caFile;


   private File certFile;


   private String certPasswd;


   private String reqContent;


   private String resContent;


   private String method;


   private String errInfo;


   private int timeOut;


   private int responseCode;


   private String charset;


   private InputStream inputStream;


   private URL url;


   private int connectionTimeout;


   private int readTimeOut;


   private String result;



   public HttpClient() {
     this.caFile = null;
     this.certFile = null;
     this.certPasswd = "";

     this.reqContent = "";
     this.resContent = "";
     this.method = "POST";
     this.errInfo = "";
     this.timeOut = 30;

     this.responseCode = 0;
     this.charset = "UTF-8";

     this.inputStream = null;
   }

   public HttpClient(String url, String method, int timeOut, String charset) {
     this.caFile = null;
     this.certFile = null;
     this.certPasswd = "";

     this.reqContent = url;
     this.resContent = "";
     this.method = method;
     this.errInfo = "";
     this.timeOut = timeOut;

     this.responseCode = 0;
     this.charset = charset;

     this.inputStream = null;
   }







   public void setCertInfo(File certFile, String certPasswd) {
     this.certFile = certFile;
     this.certPasswd = certPasswd;
   }






   public void setCaInfo(File caFile) {
     this.caFile = caFile;
   }






   public void setReqContent(String reqContent) {
     this.reqContent = reqContent;
   }







   public String getResContent() {
     try {
       doResponse();
     } catch (IOException e) {
       _log.error("", e);
       this.errInfo = e.getMessage();
     }


     return this.resContent;
   }






   public void setMethod(String method) {
     this.method = method;
   }






   public String getErrInfo() {
     return this.errInfo;
   }






   public void setTimeOut(int timeOut) {
     this.timeOut = timeOut;
   }






   public int getResponseCode() {
     return this.responseCode;
   }







   public boolean call() {
     boolean isRet = false;


     if (null == this.caFile && null == this.certFile) {
       try {
         callHttp();
         isRet = true;
       } catch (IOException e) {
         _log.error("", e);
         this.errInfo = e.getMessage();
       } catch (Exception e) {
         _log.error("", e);
         this.errInfo = e.getMessage();
       }
       return isRet;
     }


     return calls();
   }



   public boolean calls() {
     boolean isRet = false;


     try {
       callHttps();
       isRet = true;
     } catch (UnrecoverableKeyException e) {
       _log.error("", e);
       this.errInfo = e.getMessage();
     } catch (KeyManagementException e) {
       _log.error("", e);
       this.errInfo = e.getMessage();
     } catch (CertificateException e) {
       _log.error("", e);
       this.errInfo = e.getMessage();
     } catch (KeyStoreException e) {
       _log.error("", e);
       this.errInfo = e.getMessage();
     } catch (NoSuchAlgorithmException e) {
       _log.error("", e);
       this.errInfo = e.getMessage();
     } catch (IOException e) {
       _log.error("", e);
       this.errInfo = e.getMessage();
     } catch (Exception e) {
       _log.error("", e);
       this.errInfo = e.getMessage();
     }
     return isRet;
   }



   protected void callHttp() throws IOException {
     if ("POST".equals(this.method.toUpperCase())) {
       String url = HttpClientUtil.getURL(this.reqContent);
       String queryString = HttpClientUtil.getQueryString(this.reqContent);
       byte[] postData = queryString.getBytes(this.charset);
       httpPostMethod(url, postData);

       return;
     }

     httpGetMethod(this.reqContent);
   }






























   protected void callHttps() throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
     SSLContext sslContext = SSLContext.getInstance("SSL");
     sslContext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());






     if ("POST".equals(this.method.toUpperCase())) {
       String url = HttpClientUtil.getURL(this.reqContent);
       String queryString = HttpClientUtil.getQueryString(this.reqContent);
       byte[] postData = queryString.getBytes(this.charset);

       httpsPostMethod(url, postData, sslContext);

       return;
     }

     httpsGetMethod(this.reqContent, sslContext);
   }











   protected void httpPostMethod(String url, byte[] postData) throws IOException {
     HttpURLConnection conn = HttpClientUtil.getHttpURLConnection(url);

     doPost(conn, postData);
   }









   protected void httpGetMethod(String url) throws IOException {
     HttpURLConnection httpConnection = HttpClientUtil.getHttpURLConnection(url);

     setHttpRequest(httpConnection);

     httpConnection.setRequestMethod("GET");

     this.responseCode = httpConnection.getResponseCode();

     this.inputStream = httpConnection.getInputStream();
   }











   protected void httpsGetMethod(String url, SSLContext sslContext) throws IOException {
     SSLSocketFactory sf = sslContext.getSocketFactory();

     HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

     conn.setSSLSocketFactory(sf);

     doGet(conn);
   }




   protected void httpsPostMethod(String url, byte[] postData, SSLContext sslContext) throws IOException {
     SSLSocketFactory sf = sslContext.getSocketFactory();

     HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

     conn.setSSLSocketFactory(sf);

     doPost(conn, postData);
   }









   protected void setHttpRequest(HttpURLConnection httpConnection) {
     httpConnection.setConnectTimeout(this.timeOut * 1000);


     httpConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)");



     httpConnection.setUseCaches(false);


     httpConnection.setDoInput(true);
     httpConnection.setDoOutput(true);
   }








   protected void doResponse() throws IOException {
     if (null == this.inputStream) {
       return;
     }


     this.resContent = HttpClientUtil.InputStreamTOString(this.inputStream, this.charset);


     this.inputStream.close();
   }












   protected void doPost(HttpURLConnection conn, byte[] postData) throws IOException {
     conn.setRequestMethod("POST");


     setHttpRequest(conn);


     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");




     BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());

     int len = 1024;
     HttpClientUtil.doOutput(out, postData, 1024);











     out.close();


     this.responseCode = conn.getResponseCode();


     this.inputStream = conn.getInputStream();
   }










   protected void doGet(HttpURLConnection conn) throws IOException {
     conn.setRequestMethod("GET");


     setHttpRequest(conn);


     this.responseCode = conn.getResponseCode();


     this.inputStream = conn.getInputStream();
   }

   public static String callHttpPost(String url) {
     return callHttpPost(url, 60);
   }

   public static String callHttpPost(String url, int connect_timeout) {
     return callHttpPost(url, connect_timeout, "UTF-8");
   }

   public static String callHttpPost(String url, int connect_timeout, String encode) {
     HttpClient client = new HttpClient(url, "POST", connect_timeout, encode);
     client.call();
     return client.getResContent();
   }


   public static String callHttpsPost(String url) {
     HttpClient client = new HttpClient(url, "POST", 60, "UTF-8");
     client.calls();
     return client.getResContent();
   }


   public static String callHttpGet(String url, int connect_timeout, String encode) {
     HttpClient client = new HttpClient(url, "GET", connect_timeout, encode);
     client.call();
     return client.getResContent();
   }


   public static String callHttpsGet(String url) {
     HttpClient client = new HttpClient(url, "GET", 60, "UTF-8");
     client.calls();
     return client.getResContent();
   }


   private static class TrustAnyTrustManager
     implements X509TrustManager
   {
     private TrustAnyTrustManager() {}


     public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

     public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

     public X509Certificate[] getAcceptedIssuers() {
       return new X509Certificate[0];
     }
   }





   public String getResult() {
     return this.result;
   }





   public void setResult(String result) {
     this.result = result;
   }







   public HttpClient(String url, int connectionTimeout, int readTimeOut) {
     try {
       this.url = new URL(url);
       this.connectionTimeout = connectionTimeout;
       this.readTimeOut = readTimeOut;
     } catch (MalformedURLException e) {
       _log.error(e.getMessage(), e);
     }
   }








   public int send(Map<String, String> data, String encoding) throws Exception {
     try {
       HttpURLConnection httpURLConnection = createConnection(encoding);
       if (null == httpURLConnection) {
         throw new Exception("创建联接失败");
       }
       String sendData = getRequestParamString(data, encoding);
       _log.info("请求报文:[" + sendData + "]");
       requestServer(httpURLConnection, sendData, encoding);

       this.result = response(httpURLConnection, encoding);
       _log.info("同步返回报文:[" + this.result + "]");
       return httpURLConnection.getResponseCode();
     } catch (Exception e) {
       throw e;
     }
   }








   public int sendGet(String encoding) throws Exception {
     try {
       HttpURLConnection httpURLConnection = createConnectionGet(encoding);
       if (null == httpURLConnection) {
         throw new Exception("创建联接失败");
       }
       this.result = response(httpURLConnection, encoding);
       _log.info("同步返回报文:[" + this.result + "]");
       return httpURLConnection.getResponseCode();
     } catch (Exception e) {
       throw e;
     }
   }










   private void requestServer(URLConnection connection, String message, String encoder) throws Exception {
     PrintStream out = null;
     try {
       connection.connect();
       out = new PrintStream(connection.getOutputStream(), false, encoder);
       out.print(message);
       out.flush();
     } catch (Exception e) {
       throw e;
     } finally {
       if (null != out) {
         out.close();
       }
     }
   }











   private String response(HttpURLConnection connection, String encoding) throws URISyntaxException, IOException, Exception {
     InputStream in = null;
     StringBuilder sb = new StringBuilder(1024);
     BufferedReader br = null;
     try {
       if (200 == connection.getResponseCode()) {
         in = connection.getInputStream();
         sb.append(new String(read(in), encoding));
       } else {
         in = connection.getErrorStream();
         sb.append(new String(read(in), encoding));
       }
       _log.info("HTTP Return Status-Code:[" + connection
           .getResponseCode() + "]");
       return sb.toString();
     } catch (Exception e) {
       throw e;
     } finally {
       if (null != br) {
         br.close();
       }
       if (null != in) {
         in.close();
       }
       if (null != connection) {
         connection.disconnect();
       }
     }
   }

   public static byte[] read(InputStream in) throws IOException {
     byte[] buf = new byte[1024];
     int length = 0;
     ByteArrayOutputStream bout = new ByteArrayOutputStream();
     while ((length = in.read(buf, 0, buf.length)) > 0) {
       bout.write(buf, 0, length);
     }
     bout.flush();
     return bout.toByteArray();
   }







   private HttpURLConnection createConnection(String encoding) throws ProtocolException {
     HttpURLConnection httpURLConnection = null;
     try {
       httpURLConnection = (HttpURLConnection)this.url.openConnection();
     } catch (IOException e) {
       _log.error(e.getMessage(), e);
       return null;
     }
     httpURLConnection.setConnectTimeout(this.connectionTimeout);
     httpURLConnection.setReadTimeout(this.readTimeOut);
     httpURLConnection.setDoInput(true);
     httpURLConnection.setDoOutput(true);
     httpURLConnection.setUseCaches(false);
     httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + encoding);

     httpURLConnection.setRequestMethod("POST");
     if ("https".equalsIgnoreCase(this.url.getProtocol())) {
       HttpsURLConnection husn = (HttpsURLConnection)httpURLConnection;
       husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
       husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
       return husn;
     }
     return httpURLConnection;
   }







   private HttpURLConnection createConnectionGet(String encoding) throws ProtocolException {
     HttpURLConnection httpURLConnection = null;
     try {
       httpURLConnection = (HttpURLConnection)this.url.openConnection();
     } catch (IOException e) {
       _log.error(e.getMessage(), e);
       return null;
     }
     httpURLConnection.setConnectTimeout(this.connectionTimeout);
     httpURLConnection.setReadTimeout(this.readTimeOut);
     httpURLConnection.setUseCaches(false);
     httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + encoding);

     httpURLConnection.setRequestMethod("GET");
     if ("https".equalsIgnoreCase(this.url.getProtocol())) {
       HttpsURLConnection husn = (HttpsURLConnection)httpURLConnection;
       husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
       husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
       return husn;
     }
     return httpURLConnection;
   }








   public static String getRequestParamString(Map<String, String> requestParam, String coder) {
     if (null == coder || "".equals(coder)) {
       coder = "UTF-8";
     }
     StringBuffer sf = new StringBuffer("");
     String reqstr = "";
     if (null != requestParam && 0 != requestParam.size()) {
       for (Map.Entry<String, String> en : requestParam.entrySet()) {
         try {
           sf.append((String)en.getKey() + "=" + ((

               null == en.getValue() || "".equals(en.getValue())) ? "" :
               URLEncoder.encode(en.getValue(), coder)) + "&");
         } catch (UnsupportedEncodingException e) {
           _log.error(e.getMessage(), e);
           return "";
         }
       }
       reqstr = sf.substring(0, sf.length() - 1);
     }
     _log.info("请求报文(已做过URLEncode编码):[" + reqstr + "]");
     return reqstr;
   }








   public static String post(Map<String, String> reqData, String reqUrl) {
     Map<String, String> rspData = new HashMap<>();


     HttpClient hc = new HttpClient(reqUrl, 30000, 30000);
     try {
       int status = hc.send(reqData, "UTF-8");
       if (200 == status) {
         String resultString = hc.getResult();
         if (null != resultString && !"".equals(resultString))
         {
           return resultString;
         }
       }

     }
     catch (Exception exception) {}


     return null;
   }







   public static String post(String url, Map<String, Object> param) {
     _log.info("POST 请求， url={}，map={}", url, param.toString());
     try {
       HttpPost httpPost = new HttpPost(url.trim());
       RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
       httpPost.setConfig(requestConfig);
       httpPost.addHeader("Content-Type", "application/json");
       StringEntity se = new StringEntity(param.toString(), "UTF-8");
       se.setContentType("text/json");
       se.setContentEncoding((Header)new BasicHeader("Content-Type", "application/json"));
       httpPost.setEntity((HttpEntity)se);
       CloseableHttpResponse closeableHttpResponse = HttpClientBuilder.create().build().execute((HttpUriRequest)httpPost);
       return EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
     } catch (Exception e) {
       _log.info("HTTP请求出错", e);
       e.printStackTrace();

       return "";
     }
   }






   public static String post(String url, Map<String, Object> param, Integer TimeOut) {
     _log.info("POST 请求， url={}，map={}", url, param.toString());
     try {
       HttpPost httpPost = new HttpPost(url.trim());
       RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(50000).setConnectionRequestTimeout(TimeOut.intValue()).setSocketTimeout(TimeOut.intValue()).build();
       httpPost.setConfig(requestConfig);
       httpPost.addHeader("Content-Type", "application/json");
       StringEntity se = new StringEntity(param.toString(), "UTF-8");
       se.setContentType("text/json");
       se.setContentEncoding((Header)new BasicHeader("Content-Type", "application/json"));
       httpPost.setEntity((HttpEntity)se);
       CloseableHttpResponse closeableHttpResponse = HttpClientBuilder.create().build().execute((HttpUriRequest)httpPost);
       return EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
     } catch (Exception e) {
       _log.info("HTTP请求出错", e);
       e.printStackTrace();

       return "";
     }
   }







   public static String post(String url, String body) {
     try {
       HttpPost httpPost = new HttpPost(url.trim());
       RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
       httpPost.setConfig(requestConfig);
       httpPost.addHeader("Content-Type", "application/json");
       StringEntity se = new StringEntity(body, "UTF-8");
       se.setContentType("text/json");
       se.setContentEncoding((Header)new BasicHeader("Content-Type", "application/json"));
       httpPost.setEntity((HttpEntity)se);
       CloseableHttpResponse closeableHttpResponse = HttpClientBuilder.create().build().execute((HttpUriRequest)httpPost);
       return EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
     } catch (Exception e) {
       _log.info("HTTP请求出错", e);
       e.printStackTrace();

       return "";
     }
   }
 }

