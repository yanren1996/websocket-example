package com.yanren.websocketexample.config.wshandler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component("authHandler")
public class AuthHandler extends ChatHandler{

    @Override
    protected String getUserName(WebSocketSession session) {
        return session.getPrincipal().getName();
    }
}
