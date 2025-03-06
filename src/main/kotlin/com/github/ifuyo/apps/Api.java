package com.github.ifuyo.apps;

import com.github.ifuyo.utils.requests.Https;
import com.fasterxml.jackson.core.type.TypeReference;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.IOException;

public class Api {
    public static class TokenResponse {
        private String status;
        private String data;

        // Getters and setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static TokenResponse getToken() throws IOException {
        return Https.get("https://v2.jinrishici.com/token", new TypeReference<TokenResponse>() {});
    }

    public static class VerseV1Response {
        private String content;
        private String origin;
        private String author;
        private String category;

        // Getter 和 Setter 方法
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    public static VerseV1Response getVerseV1() {
        return Https.get("https://v1.jinrishici.com/all.json", new TypeReference<VerseV1Response>() {});
    }

    public static class VerseV2Response {
        private String status;
        private Data data;
        private String token;
        private String ipAddress;
        private String warning;

        // Getters and setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getWarning() {
            return warning;
        }

        public void setWarning(String warning) {
            this.warning = warning;
        }

        // 嵌套类：Data
        public static class Data {
            private String id;
            private String content;
            private int popularity;
            private Origin origin;
            private List<String> matchTags;
            private String recommendedReason;
            private String cacheAt;

            // Getters and setters
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getPopularity() {
                return popularity;
            }

            public void setPopularity(int popularity) {
                this.popularity = popularity;
            }

            public Origin getOrigin() {
                return origin;
            }

            public void setOrigin(Origin origin) {
                this.origin = origin;
            }

            public List<String> getMatchTags() {
                return matchTags;
            }

            public void setMatchTags(List<String> matchTags) {
                this.matchTags = matchTags;
            }

            public String getRecommendedReason() {
                return recommendedReason;
            }

            public void setRecommendedReason(String recommendedReason) {
                this.recommendedReason = recommendedReason;
            }

            public String getCacheAt() {
                return cacheAt;
            }

            public void setCacheAt(String cacheAt) {
                this.cacheAt = cacheAt;
            }
        }

        // 嵌套类：Origin
        public static class Origin {
            private String title;
            private String dynasty;
            private String author;
            private List<String> content;
            private List<String> translate;

            // Getters and setters
            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDynasty() {
                return dynasty;
            }

            public void setDynasty(String dynasty) {
                this.dynasty = dynasty;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public List<String> getContent() {
                return content;
            }

            public void setContent(List<String> content) {
                this.content = content;
            }

            public List<String> getTranslate() {
                return translate;
            }

            public void setTranslate(List<String> translate) {
                this.translate = translate;
            }
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
