package com.yanren.websocketexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    @Qualifier("myHandler")
    WebSocketHandler myHandler;

    @Autowired
    @Qualifier("chatHandler")
    WebSocketHandler chatHandler;

    @Autowired
    @Qualifier("authHandler")
    WebSocketHandler authHandler;

    @Autowired
    @Qualifier("aiHandler")
    WebSocketHandler aiHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler, "/ws");
        registry.addHandler(chatHandler, "/ws-chat");
        registry.addHandler(authHandler, "/ws-auth-chat");
        registry.addHandler(aiHandler, "/ws-ai");
    }
}
