package com.github.ifuyo.utils.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ifuyo.error.http.HttpsRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Https {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(Https.class);

    /**
     * 发送 GET 请求并返回指定类型的对象（支持无参请求）
     *
     * @param url     请求 URL（需要包含协议，如 "https://"）
     * @param typeRef 类型引用（用于显式指定泛型类型）
     * @return 返回指定类型的对象，如果发生异常则返回 null
     */
    public static <T> T get(final String url, TypeReference<T> typeRef) {
        try {
            // 校验 URL 合法性
            validateUrl(url);

            // 创建 HTTP 连接
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 秒连接超时
            connection.setReadTimeout(10000);   // 10 秒读取超时

            // 发送请求并获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // 将 JSON 响应解析为指定类型的对象
                    return objectMapper.readValue(response.toString(), typeRef);
                }
            } else {
                // 处理非 200 响应码
                String errorMessage = "HTTP Request Failed. URL: " + url + ", Response Code: " + responseCode;
                logger.error(errorMessage);
                throw new HttpsRequestException(errorMessage, responseCode, HttpsRequestException.ErrorType.CONNECTION_ERROR);
            }
        } catch (MalformedURLException e) {
            String errorMessage = "Invalid URL: " + url;
            logger.error(errorMessage, e);
            throw new HttpsRequestException(errorMessage, 0, HttpsRequestException.ErrorType.OTHER_ERROR, e);
        } catch (Exception e) {
            String errorMessage = "HTTP Request Failed. URL: " + url;
            logger.error(errorMessage, e);
            throw new HttpsRequestException(errorMessage, 0, HttpsRequestException.ErrorType.OTHER_ERROR, e);
        }
    }

    /**
     * 发送 GET 请求并返回指定类型的对象（支持有参请求）
     *
     * @param url     请求 URL（需要包含协议，如 "https://"）
     * @param params  查询参数（可选，格式为 {"key1": "value1", "key2": "value2"}）
     * @param headers 请求头 (可选，格式为 {"Header1: Value1", "Header2: Value2"})
     * @param typeRef 类型引用（用于显式指定泛型类型）
     * @return 返回指定类型的对象，如果发生异常则返回 null
     */
    public static <T> T get(final String url, Map<String, String> params, Map<String, String> headers, TypeReference<T> typeRef) {
        try {
            // 校验 URL 合法性
            validateUrl(url);

            // 最终发起请求的 url
            String requestUrl = url;

            // 拼接查询参数到 URL
            if (params != null && !params.isEmpty()) {
                requestUrl = buildUrlWithParams(url, params);
            }

            // 创建 HTTP 连接
            HttpURLConnection connection = (HttpURLConnection) new URL(requestUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 秒连接超时
            connection.setReadTimeout(10000);   // 10 秒读取超时

            // 设置请求头
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 发送请求并获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // 将 JSON 响应解析为指定类型的对象
                    return objectMapper.readValue(response.toString(), typeRef);
                }
            } else {
                // 处理非 200 响应码
                String errorMessage = "HTTP Request Failed. URL: " + requestUrl + ", Response Code: " + responseCode;
                logger.error(errorMessage);
                throw new HttpsRequestException(errorMessage, responseCode, HttpsRequestException.ErrorType.CONNECTION_ERROR);
            }
        } catch (MalformedURLException e) {
            String errorMessage = "Invalid URL: " + url;
            logger.error(errorMessage, e);
            throw new HttpsRequestException(errorMessage, 0, HttpsRequestException.ErrorType.OTHER_ERROR, e);
        } catch (Exception e) {
            String errorMessage = "HTTP Request Failed. URL: " + url;
            logger.error(errorMessage, e);
            throw new HttpsRequestException(errorMessage, 0, HttpsRequestException.ErrorType.OTHER_ERROR, e);
        }
    }

    /**
     * 将查询参数拼接到 URL 中
     *
     * @param url    原始 URL
     * @param params 查询参数
     * @return 拼接后的 URL
     */
    private static String buildUrlWithParams(String url, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?"); // 添加查询参数前缀

        // 遍历参数并拼接到 URL
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8); // 使用 StandardCharsets.UTF_8
            String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
            urlBuilder.append(key).append("=").append(value).append("&");
        }

        // 删除最后一个多余的 "&"
        if (urlBuilder.charAt(urlBuilder.length() - 1) == '&') {
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }

        return urlBuilder.toString();
    }

    /**
     * 检查 URL 的合法性
     *
     * @param url 请求 URL
     * @throws MalformedURLException 如果 URL 不合法
     */
    private static void validateUrl(String url) throws MalformedURLException {
        new URL(url); // 如果 URL 不合法，会抛出 MalformedURLException
    }
}