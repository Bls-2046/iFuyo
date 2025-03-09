package com.github.ifuyo.apps;

import com.github.ifuyo.apps.navigation.gothorweb.ui.GothorWeb;
import com.github.ifuyo.entity.DeepSeekEntity;
import com.github.ifuyo.utils.requests.Https;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.*;
import java.io.IOException;

/**
 *  请求返回必须为 JSON, 否则反序列化会失败
 */

public class Api {

    // ===================================一言 API, 获取古诗句 =============================================
    /**
     * v1 版本请求 url: <a href="https://v1.jinrishici.com/all.json">...</a>
     * 支持部分自定义、响应格式为 JSON
     *
     * v2 版本请求 url:<a href="https://v2.jinrishici.com/sentence">...</a>
     *  请求前需要通过 https://v2.jinrishici.com/token 获取token
     *  再对 <a href="https://v2.jinrishici.com/sentence">...</a> 发起请求
     *
     *  以上两个都为 GET 请求
     */
    // ================= v1 版本 =================
    @Setter
    @Getter
    public static class VerseV1Response {
        // Getter 和 Setter 方法
        private String content;
        private String origin;
        private String author;
        private String category;
    }

    public static VerseV1Response getVerseV1() {
        return Https.get("https://v1.jinrishici.com/all.json", new TypeReference<VerseV1Response>() {});
    }

    // ================= v2 版本 =================
    @Setter
    @Getter
    public static class VerseV2Response {
        // Getters and setters
        private String status;
        private Data data;
        private String token;
        private String ipAddress;
        private String warning;

        // 嵌套类：Data
        @Setter
        @Getter
        public static class Data {
            // Getters and setters
            private String id;
            private String content;
            private int popularity;
            private Origin origin;
            private List<String> matchTags;
            private String recommendedReason;
            private String cacheAt;
        }

        // 嵌套类：Origin
        @Setter
        @Getter
        public static class Origin {
            // Getters and setters
            private String title;
            private String dynasty;
            private String author;
            private List<String> content;
            private List<String> translate;

        }
    }

    @Setter
    @Getter
    public static class TokenResponse {
        // Getters and setters
        private String status;
        private String data;
    }

    public static TokenResponse getToken() throws IOException {
        return Https.get("https://v2.jinrishici.com/token", new TypeReference<TokenResponse>() {});
    }

    @Nullable
    public static VerseV2Response getVerseV2() {
        String token;
        try {
            token = getToken().getData();
            System.out.println("token: " + token);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("X-User-Token", token); // 添加 Token 到请求头
        return Https.get("https://v2.jinrishici.com/sentence", null, headers, new TypeReference<VerseV2Response>() {});
    }
    // ======================================= 一言 API, 获取古诗句 ===============================================

    // =================================== DeepSeek API, 获取问题回复 ( 暂时不做 ) =============================================
    /**
     * DeepSeek 对话
     * 每一次对话都会记录对话内容并存入数据库
     * 数据库存储字段如下:
     * | username | dialogue_date | title | content |
     * username 与 dialogue_date 为标识字段, 确认对话内容
     * 进入对话界面会读取历史对话信息并展示在对话框中
     * 用户在输入框中写入问题并发送, 得到回答展示在对话框中并更新数据库信息
     */
    private static String API_KEY;
    private static String API_URL;

    // 从配置文件获取 API_KEY 和 API_URL
    static {
        Properties properties = new Properties();
        try {
            InputStream inputStream = DeepSeekEntity.class.getResourceAsStream("/config.properties");
            properties.load(inputStream);
            API_KEY = properties.getProperty("DeepSeek_API_KEY");
            API_URL = properties.getProperty("DeepSeek_API_URL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowHistoryDialogue(String username) {
        // 通过 username 字段对数据库中的历史对话进行筛选
        // 模拟筛选结果
        List<Map<String, String>> historyDialogue = List.of(
                Map.of("role", "user", "content", "Hello, how are you?"),
                Map.of("role", "assistant", "content", "I'm fine, thank you!")
        );
    }

    public static void CreateRequest(String username) {

    }

    public static void DialogueDeepSeek(DeepSeekEntity.DeepSeekRequest requestBody) {
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        String requstBodyJSON = gson.toJson(requestBody);

//        return Https.post("https://api.deepseek.com/chat/completions");
    }
    // =================================== DeepSeek API, 获取问题回复 ( 暂时不做 ) =============================================

    // =================================== 日程提示, 微信 API  =============================================
}
