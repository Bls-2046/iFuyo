package com.github.ifuyo.apps;

import com.github.ifuyo.utils.requests.Https;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.IOException;

public class Api {
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
}
