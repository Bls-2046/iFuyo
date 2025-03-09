package com.github.ifuyo.apps.navigation.DeepSeek.dialogue;

import com.github.ifuyo.entity.DeepSeekEntity;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class DialogueModel {
    public DialogueModel() {}


//    private final Gson gson = new Gson();
//
//
//    @PostMapping("tall")
//    public String tallQuestion(@org.springframework.web.bind.annotation.RequestBody String question) throws IOException, UnirestException {
//
//        Unirest.setTimeouts(0, 0);
//
//        //DeeseekRequest: 自己的实体类名称
//
//        List<DeepSeekEntity.Message> messages = new ArrayList<>();
//        //给deepSeek一个角色
//        messages.add(DeepSeekEntity.Message.builder().role("system").content("你是一个语言学家").build());
//
//        // question：说你自己想说的话
//        messages.add(DeepSeekEntity.Message.builder().role("user").content(question).build());
//
//        DeepSeekEntity requestBody = DeepSeekEntity.builder()
//                .model("deepseek-chat")
//                .messages(messages)
//                .build();
//        HttpResponse<String> response = Unirest.post("https://api.deepseek.com/chat/completions")
//                .header("Content-Type", "application/json")
//                .header("Accept", "application/json")
//                .header("Authorization", "Bearer "+"自己的key")
//                .body(gson.toJson(requestBody))
//                .asString();
//        return  response.getBody();
//    }

}