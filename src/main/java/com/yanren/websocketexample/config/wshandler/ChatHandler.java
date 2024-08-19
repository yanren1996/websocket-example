package com.yanren.websocketexample.config.wshandler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Component("chatHandler")
public class ChatHandler extends AbstractWebSocketHandler {
    // 線上名單
    protected Set<WebSocketSession> sessions = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 新連接建立後將它加入線上名單
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 斷開連線的人移除線上名單
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 每個線上名單的人互相看的到各自發送的訊息
        for (WebSocketSession item : sessions) {
            // 因為這個實作是匿名的，先借sessionID的最後兩碼當作名字
            item.sendMessage(new TextMessage(getUserName(session) + "說了" + message.getPayload()));
        }
    }

    // 我們學學自己實現控制反轉
    protected String getUserName(WebSocketSession session) {
        return session.getId().substring(34);
    }
}
