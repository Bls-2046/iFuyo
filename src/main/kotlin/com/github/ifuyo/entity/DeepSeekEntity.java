package com.github.ifuyo.entity;


import com.google.protobuf.Message;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
public class DeepSeekEntity {
    static class Message {
        private String role;
        @Getter
        private String message;

        public Message(String role, String message) {
            this.role = role;
            this.message = message;
        }
    }

    /**
     * DeepSeek API 请求体
     */
    public static class DeepSeekRequest {
        private String model;
        private List<Message> message;
        private double temperature;
        private int max_tokens;

        public DeepSeekRequest(String model, List<Message> message, double temperature, int max_tokens) {
            this.model = model;
            this.message = message;
            this.temperature = temperature;
            this.max_tokens = max_tokens;
        }
    }

    /**
     * DeepSeek API 响应体
     */
    public static class DeepSeekResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() {
            return choices;
        }

        @Getter
        static class Choice {
            private Message message;
        }
    }
}
