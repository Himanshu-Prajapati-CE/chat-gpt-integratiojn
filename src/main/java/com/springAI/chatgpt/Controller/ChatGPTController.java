package com.springAI.chatgpt.Controller;

import com.springAI.chatgpt.model.ChatGPTRequest;
import com.springAI.chatgpt.model.ChatGPTResponse;
import com.springAI.chatgpt.model.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ChatGPTController {

    @PostMapping("/chat")
    public String getChatGPTResponse(@RequestBody String query) {

        ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        Message message = new Message();
        message.setContent(query);
        message.setRole("user");

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        chatGPTRequest.setMessages(messages);
        chatGPTRequest.setModel("gpt-3.5-turbo");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization",
                "Bearer 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" );

        HttpEntity<ChatGPTRequest> chatGPTRequestHttpEntity = new HttpEntity<>(chatGPTRequest, httpHeaders);
        ResponseEntity<ChatGPTResponse> chatGPTResponse = restTemplate.exchange("https://api.openai.com/v1/chat/completions",
                HttpMethod.POST, chatGPTRequestHttpEntity, ChatGPTResponse.class);

        return  chatGPTResponse.getBody().getChoices().get(0).getMessage().getContent();
    }
}
