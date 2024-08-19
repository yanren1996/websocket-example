package com.yanren.websocketexample.config.wshandler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component("authHandler")
public class AuthHandler extends ChatHandler{
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession item: this.sessions) {
            item.sendMessage(new TextMessage(session.getPrincipal().getName() + " 說:" + message.getPayload()));
        }
    }
}
