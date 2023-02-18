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

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    private OkHttpClientUtil() {
    }

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
        if (params != null && !params.keySet().isEmpty()) {
            boolean firstFlag = true;
            for (Map.Entry<String,String> entry : params.entrySet()) {
                if (firstFlag) {
                    sb.append("?").append(entry.getKey()).append("=").append(entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
        }
        Request.Builder builder = new Request.Builder();
        if(headers != null) {
            headers.forEach(builder::header);
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
        if (params != null && !params.keySet().isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        if(headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::header);
        }
        Request request = requestBuilder
                .post(builder.build())
                .build();
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
            headers.forEach(requestBuilder::header);
        }
        Request request = requestBuilder.post(requestBody).build();
        return execute(request);
    }

    /**
     * post 上传文件
     * @param url 请求地址
     * @param params 请求body参数
     * @param fileType 文件类型
     * @return 返回的请求结果
     */
    public static String postFile(String url, Map<String, Object> params, String fileType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //添加参数
        if (params != null && !params.keySet().isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse(fileType), file));
                    continue;
                }
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
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
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "";
    }
}

