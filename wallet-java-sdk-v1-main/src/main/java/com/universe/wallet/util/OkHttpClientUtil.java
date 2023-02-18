package com.universe.wallet.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.File;
import java.util.Map;

/**
 * okhttp3的优势还是非常明显的
 * @author Answer.AI.L
 * @date 2019-04-09
 */
@Slf4j
public class OkHttpClientUtil {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    private static OkHttpClient okHttpClient = new OkHttpClient();
    /**
     * get 请求
     * @param url  请求url地址
     * @return string
     * */
    public static String get(String url) {
        return get(url, null, null);
    }
    /**
     * get 请求
     * @param url  请求url地址
     * @param params 请求参数 map
     * @return string
     * */
    public static String get(String url, Map<String, String> params) {
        return get(url, null, params);
    }
    /**
     * get 请求
     * @param url  请求url地址
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public static String getWithHeaders(String url, Map<String, String> headers) {
        return get(url, headers, null);
    }
    /**
     * get 请求
     * @param url  请求url地址
     * @param params 请求参数 map
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public static String get(String url, Map<String, String> headers, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }
        Request.Builder builder = new Request.Builder();
        if(headers != null) {
            Request.Builder finalRequestBuilder = builder;
            headers.forEach((k, v)->{
                finalRequestBuilder.header(k, v);
            });
        }
        Request request = builder.url(sb.toString()).build();
        log.info("do get request and url[{}]", sb.toString());
        return execute(request);
    }
    /**
     * post 请求
     * @param url  请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public static String postForm(String url, Map<String, String> headers, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        if(headers != null && headers.size() > 0) {
            Request.Builder finalRequestBuilder = requestBuilder;
            headers.forEach((k, v)->{
                finalRequestBuilder.header(k, v);
            });
        }
        Request request = requestBuilder
                .post(builder.build())
                .build();
        log.info("do post request and url[{}]", url);
        return execute(request);
    }
    /**
     * post 请求, 请求数据为 json 的字符串
     * @param url  请求url地址
     * @param json  请求数据, json 字符串
     * @return string
     */
    public static String postJson(String url, Map<String, String> headers, String json) {
        log.info("do post request and url[{}]", url);
        return executePost(url, headers, json, JSON);
    }
    /**
     * post 请求, 请求数据为 xml 的字符串
     * @param url  请求url地址
     * @param xml  请求数据, xml 字符串
     * @return string
     */
    public static String postXml(String url, Map<String, String> headers, String xml) {
        log.info("do post request and url[{}]", url);
        return executePost(url, headers, xml, XML);
    }
    private static String executePost(String url, Map<String, String> headers, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        if(headers != null && headers.size() > 0) {
            Request.Builder finalRequestBuilder = requestBuilder;
            headers.forEach((k, v)->{
                finalRequestBuilder.header(k, v);
            });
        }
        Request request = requestBuilder.post(requestBody).build();
        return execute(request);
    }

    /**
     * post 上传文件
     * @param url
     * @param params
     * @param fileType
     * @return
     */
    public static String postFile(String url, Map<String, Object> params, String fileType) {
        String responseBody = "";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                if (params.get(key) instanceof File) {
                    File file = (File) params.get(key);
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(fileType), file));
                    continue;
                }
                builder.addFormDataPart(key, params.get(key).toString());
            }
        }
        Request request = new Request
                .Builder()
                .url(url)
                .post(builder.build())
                .build();
        return execute(request);
    }

    private static String execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }
}

