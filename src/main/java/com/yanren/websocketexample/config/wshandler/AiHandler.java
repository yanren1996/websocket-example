package com.yanren.websocketexample.config.wshandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Map;

@Component("aiHandler")
public class AiHandler extends TextWebSocketHandler {

    @Autowired
    ChatModel chatModel;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Prompt prompt = new Prompt(message.getPayload());

        Flux<ChatResponse> resFlux = chatModel.stream(prompt);

        resFlux.subscribe(
                consumer -> {
                    try {
                        var output = consumer.getResult().getOutput();
                        Map<String, Object> properties = output.getMetadata();
                        ChatResponseVo vo = new ChatResponseVo(
                                (String) properties.get("id"),
                                output.getContent(),
                                (String) properties.get("finishReason")
                        );
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(vo)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}

record ChatResponseVo(
        String id,
        String content,
        String finishReason
){}
