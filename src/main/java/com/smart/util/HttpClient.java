package com.smart.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.contant.enums.ContentTypeEnum;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author LiaoQinZhou
 * @date: 2021/4/20 14:29
 */
@Component
public class HttpClient {

    /*@Value("${customs-server.host}")
    private String host;
    @Value("${customs-server.timeout}")
    private Integer timeout;*/


    private static String host = "127.0.0.1";

    private static Integer timeout = 5000;

    @Async
    public String get(String uri) {
        HttpGet httpGet = new HttpGet(StrUtil.format("{}{}", host, uri));
        return doGet(httpGet);
    }

    @Async
    public String get(String uri, Map<String, String> param) {
        URI url = null;
        try {
            URIBuilder builder = new URIBuilder(StrUtil.format("{}{}", host, uri));
            if (CollUtil.isNotEmpty(param)) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            url = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(url);
        return doGet(httpGet);
    }

    @Async
    public String get(String uri, String json) {
        URI url = null;
        try {
            URIBuilder builder = new URIBuilder(StrUtil.format("{}{}", host, uri));
            if (StrUtil.isNotEmpty(json)) {
                Map<String, Object> param = JSON.parseObject(json);
                param.forEach((k, v) -> {
                    builder.addParameter(k, JSONObject.toJSONString(v));
                });
            }
            url = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(url);
        return doGet(httpGet);
    }

    @Async
    public String post(String uri, String json) {
        HttpPost httpPost = new HttpPost(StrUtil.format("{}{}", host, uri));
        StringEntity entity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(entity);
        httpPost.setHeader(Header.CONTENT_TYPE.getValue(), ContentTypeEnum.APPLICATION_JSON.getValue());
        return doPost(httpPost);
    }

    @Async
    public String post(String uri, Map<String, String> paramMap) {
        HttpPost httpPost = new HttpPost(StrUtil.format("{}{}", host, uri));
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        if (CollUtil.isNotEmpty(paramMap)) {
            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
            paramMap.forEach((k, v) -> {
                multipartEntityBuilder.addTextBody(k, v, contentType);
            });
        }
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        return doPost(httpPost);
    }


    @Async
    public String post(String uri, Map<String, String> paramMap, File... files) {
        HttpPost httpPost = new HttpPost(StrUtil.format("{}{}", host, uri));
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        if (CollUtil.isNotEmpty(paramMap)) {
            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
            paramMap.forEach((k, v) -> {
                multipartEntityBuilder.addTextBody(k, v, contentType);
            });
        }
        if (ArrayUtil.isNotEmpty(files)) {
            String key = "file";
            for (File file : files) {
                try {
                    multipartEntityBuilder.addBinaryBody(key, file, ContentType.DEFAULT_BINARY, URLEncoder.encode(file.getName(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        return doPost(httpPost);
    }

    @Async
    public String put(String uri, String json) {
        HttpPut httpPut = new HttpPut(StrUtil.format("{}{}", host, uri));
        StringEntity entity = new StringEntity(json, "UTF-8");
        httpPut.setEntity(entity);
        httpPut.setHeader(Header.CONTENT_TYPE.getValue(), ContentTypeEnum.APPLICATION_JSON.getValue());
        return doPut(httpPut);
    }

    @Async
    public String delete(String uri, String json) {
        URI url = null;
        try {
            URIBuilder builder = new URIBuilder(StrUtil.format("{}{}", host, uri));
            if (StrUtil.isNotEmpty(json)) {
                Map<String, Object> param = JSON.parseObject(json);
                param.forEach((k, v) -> {
                    builder.addParameter(k, JSONObject.toJSONString(v));
                });
            }
            url = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpDelete httpDelete = new HttpDelete(url);
        return doDelete(httpDelete);
    }

    @Async
    public String delete(String uri, Map<String, String> param) {
        URI url = null;
        try {
            URIBuilder builder = new URIBuilder(StrUtil.format("{}{}", host, uri));
            if (CollUtil.isNotEmpty(param)) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            url = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpDelete httpDelete = new HttpDelete(url);
        return doDelete(httpDelete);
    }

    private String doGet(HttpGet httpGet) {
        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse response = httpClient.execute(httpGet);
        ) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setSocketTimeout(timeout)
                    .setRedirectsEnabled(true).build();
            httpGet.setConfig(requestConfig);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String doPost(HttpPost httpPost) {
        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse response = httpClient.execute(httpPost);
        ) {
            HttpEntity responseEntity = response.getEntity();
            if (Objects.nonNull(responseEntity)) {
                return EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String doPut(HttpPut httpPut) {
        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse response = httpClient.execute(httpPut);
        ) {
            HttpEntity responseEntity = response.getEntity();
            if (Objects.nonNull(responseEntity)) {
                return EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String doDelete(HttpDelete httpDelete) {
        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse response = httpClient.execute(httpDelete);
        ) {
            HttpEntity responseEntity = response.getEntity();
            if (Objects.nonNull(responseEntity)) {
                return EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 请求头
    @Async
    public String get(String uri, Map<String, Object> param, Map<String, String> headerMap) {
        URI url = null;
        try {
            URIBuilder builder = new URIBuilder(StrUtil.format("{}{}", host, uri));
            if (CollUtil.isNotEmpty(param)) {
                for (String key : param.keySet()) {
                    if (StrUtil.isEmpty(key)) {
                        // 为空
                        builder.addParameter(key, "\'\'");
                    }
                    builder.addParameter(key, String.valueOf(param.get(key)));
                }
            }
            url = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(url);
        if (CollUtil.isNotEmpty(headerMap)) {
            for (String key : headerMap.keySet()) {
                httpGet.addHeader(key, headerMap.get(key));
            }
        }
        return doGet(httpGet);
    }


    // 请求头
    @Async
    public String post(String uri, Map<String, String> paramMap, Map<String, String> headerMap) {
        HttpPost httpPost = new HttpPost(StrUtil.format("{}{}", host, uri));
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        if (CollUtil.isNotEmpty(paramMap)) {
            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
            paramMap.forEach((k, v) -> {
                multipartEntityBuilder.addTextBody(k, v, contentType);
            });
        }
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        if (CollUtil.isNotEmpty(headerMap)) {
            for (String key : headerMap.keySet()) {
                httpPost.addHeader(key, headerMap.get(key));
            }
        }
        return doPost(httpPost);
    }
}
