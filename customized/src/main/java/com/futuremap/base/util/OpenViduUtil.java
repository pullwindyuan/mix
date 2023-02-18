package com.futuremap.base.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OpenViduUtil {
	private static final Logger log = LoggerFactory.getLogger(OpenViduUtil.class);

	@Value("${openvidu.url}")
	private static String openviduUrl;

	@Value("${openvidu.secret}")
	private String openviduSecret;

	private static OpenViduUtil instance;

	public static OpenViduUtil getInstance() {
		if(instance == null) {
			instance = new OpenViduUtil();
		}
		return instance;
	}
	public static HttpResponse httpsGetVideo(String url, String user, String pass, ArrayList<Header> headers, Map<String, String> requestParam) {
		return httpsGet(url, user, pass, headers, requestParam);
	}

	public static HttpResponse httpsGetVideo(String url, ArrayList<Header> headers, Map<String, String> requestParam) {
		return httpsGet(url, headers, requestParam);
	}

	public HttpResponse httpsGetVideo(String url, Map<String, String> requestParam) {
		Header[] headers = {new BasicHeader("Content-type", "video/mp4"),
								new BasicHeader("Range", "0-")};
		return httpsGet(url, "OPENVIDUAPP", openviduSecret, null, requestParam);
	}
	public HttpResponse httpsGetWithEntity(String url, Map<String, String> requestParam, JSONObject body) {
		String userPass = "OPENVIDUAPP" + ":" + openviduSecret;
		Header[] headers = {new BasicHeader("Content-type", "application/json"),
				new BasicHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes()))};

//		Authorization: 'Basic ' + btoa('OPENVIDUAPP:' + openviduSecret),
		return httpsGetWithEntity(url, headers, requestParam, body);
	}
	private static HttpResponse httpsGet(String url, String user, String pass, ArrayList<Header> headers, Map<String, String> requestParam) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		String queryParam = getRequestParamString(requestParam);
		Header[] headerArray = new Header[headers.size()];
		int i = 0;
		for(Header h:headers) {
			headerArray[i] = h;
			i ++;
		}
		HttpGet httpGet = new HttpGet(url + queryParam);
		if(headers != null) {
			httpGet.setHeaders(headerArray);
		}

		headerArray = httpGet.getAllHeaders();
		for(Header h:headerArray) {
			log.info(h.getName() + " : " + h.getValue());
		}
//		String userPass = "OPENVIDUAPP" + ":" + openviduSecret;
//		//Header[] AuthHeaders = {new BasicHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes()))};
//		httpGet.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes()));

		TrustStrategy trustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		CredentialsProvider provider = new BasicCredentialsProvider();
		//UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("OPENVIDUAPP", openviduSecret);
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, pass);
		provider.setCredentials(AuthScope.ANY, credentials);

		SSLContext sslContext;
		try {
			sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, trustStrategy).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException var8) {
			throw new RuntimeException(var8);
		}

		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(30000);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(30000);
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build()).setConnectionTimeToLive(30L, TimeUnit.SECONDS).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslContext).setDefaultCredentialsProvider(provider).build();

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (IOException var14) {
			throw new RuntimeException(var14);
		}
		return httpResponse;
	}

	private static HttpResponse httpsGet(String url, ArrayList<Header> headers, Map<String, String> requestParam) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		String queryParam = getRequestParamString(requestParam);
		Header[] headerArray = new Header[headers.size()];
		int i = 0;
		for(Header h:headers) {
			headerArray[i] = h;
			i ++;
		}
		HttpGet httpGet = new HttpGet(url + queryParam);
		if(headers != null) {
			httpGet.setHeaders(headerArray);
		}

		headerArray = httpGet.getAllHeaders();
		for(Header h:headerArray) {
			log.info(h.getName() + " : " + h.getValue());
		}
//		String userPass = "OPENVIDUAPP" + ":" + openviduSecret;
//		//Header[] AuthHeaders = {new BasicHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes()))};
//		httpGet.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes()));

		TrustStrategy trustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};

		SSLContext sslContext;
		try {
			sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, trustStrategy).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException var8) {
			throw new RuntimeException(var8);
		}

		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(30000);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(30000);
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build()).setConnectionTimeToLive(30L, TimeUnit.SECONDS).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslContext).build();

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (IOException var14) {
			throw new RuntimeException(var14);
		}
		return httpResponse;
	}

	private HttpResponse httpsGetWithEntity(String url, Header[] headers, Map<String, String> requestParam, JSONObject body) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		String queryParam = getRequestParamString(requestParam);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(url + queryParam);

		if(headers != null) {
			httpGet.setHeaders(headers);
		}
		StringEntity params = null;
		if(body != null) {
			try {
				params = new StringEntity(body.toString());
			} catch (UnsupportedEncodingException var15) {
				return null;
			}
		}
		httpGet.setEntity(params);
		TrustStrategy trustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("OPENVIDUAPP", openviduSecret);
		provider.setCredentials(AuthScope.ANY, credentials);

		SSLContext sslContext;
		try {
			sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, trustStrategy).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException var8) {
			throw new RuntimeException(var8);
		}

		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(30000);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(30000);
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build()).setConnectionTimeToLive(30L, TimeUnit.SECONDS).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslContext).setDefaultCredentialsProvider(provider).build();

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (IOException var14) {
			throw new RuntimeException(var14);
		}
		return httpResponse;
	}

	public static HttpResponse httpsPost(String url, String user, String pass, JSONObject body) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}

		HttpPost request = new HttpPost(url);

		StringEntity params;
		try {
			log.info("body json:" + body.toJSONString());
			log.info("body:" + body.toString());
			params = new StringEntity(body.toJSONString());
		} catch (UnsupportedEncodingException var15) {
			return null;
		}
		String userPass = user + ":" + pass;
		request.setEntity(params);
		Header[] headers = {new BasicHeader("Content-type", "application/json"),
				new BasicHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes()))};
		if(headers != null) {
			request.setHeaders(headers);
		}
		TrustStrategy trustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, pass);
		provider.setCredentials(AuthScope.ANY, credentials);

		SSLContext sslContext;
		try {
			sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, trustStrategy).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException var8) {
			throw new RuntimeException(var8);
		}

		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(30000);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(30000);
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build()).setConnectionTimeToLive(30L, TimeUnit.SECONDS).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslContext).setDefaultCredentialsProvider(provider).build();

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(request);
		} catch (IOException var14) {
			throw new RuntimeException(var14);
		}
		return httpResponse;
	}

	public HttpResponse httpsPost(String url, JSONObject body) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}

		HttpPost request = new HttpPost(url);

		StringEntity params;
		try {
			log.info("body json:" + body.toJSONString());
			log.info("body:" + body.toString());
			params = new StringEntity(body.toJSONString());
		} catch (UnsupportedEncodingException var15) {
			return null;
		}
		String userPass = "OPENVIDUAPP" + ":" + openviduSecret;
		request.setEntity(params);
		Header[] headers = {new BasicHeader("Content-type", "application/json"),
				new BasicHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes()))};
		if(headers != null) {
			request.setHeaders(headers);
		}
		TrustStrategy trustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("OPENVIDUAPP", openviduSecret);
		provider.setCredentials(AuthScope.ANY, credentials);

		SSLContext sslContext;
		try {
			sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, trustStrategy).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException var8) {
			throw new RuntimeException(var8);
		}

		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(30000);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(30000);
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build()).setConnectionTimeToLive(30L, TimeUnit.SECONDS).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslContext).setDefaultCredentialsProvider(provider).build();

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(request);
		} catch (IOException var14) {
			throw new RuntimeException(var14);
		}
		return httpResponse;
	}

	public static HttpResponse sendSignal(String url, String user, String pass, JSONObject signalBody) {
		return httpsPost(url, user, pass, signalBody);
	}

	public HttpResponse sendSignal(String url, JSONObject signalBody) {
		return httpsPost(url, signalBody);
	}

	static class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {

		private final static String METHOD_NAME = "GET";

		@Override
		public String getMethod() {
			return METHOD_NAME;
		}

		public HttpGetWithEntity() {
			super();
		}

		public HttpGetWithEntity(final URI uri) {
			super();
			setURI(uri);
		}

		HttpGetWithEntity(final String uri) {
			super();
			setURI(URI.create(uri));
		}
	}

	/**
	 * 将Map存储的对象，转换为key=value&key=value的字符
	 *
	 * @param requestParam
	 * @return
	 */
	public static String getRequestParamString(Map<String, String> requestParam) {
		String coder = "UTF-8";
		StringBuffer sf = new StringBuffer("");
		String reqstr = "";
		if (null != requestParam && 0 != requestParam.size()) {
			for (Map.Entry<String, String> en : requestParam.entrySet()) {
				try {
					sf.append(en.getKey()
							+ "="
							+ (null == en.getValue() || "".equals(en.getValue()) ? "" : URLEncoder
							.encode(en.getValue(), coder)) + "&");
				} catch (UnsupportedEncodingException e) {
					log.error(e.getMessage(), e);
					return "";
				}
			}
			reqstr = sf.substring(0, sf.length() - 1);
		}
		log.info("请求报文(已做过URLEncode编码):[" + reqstr + "]");
		return reqstr;
	}

	/**
	 * 将字符串的首字母转大写
	 * @param str 需要转换的字符串
	 * @return
	 */
	public static String captureName(String str) {
		// 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
		char[] cs=str.toCharArray();
		cs[0]-=32;
		return String.valueOf(cs);
	}
}


