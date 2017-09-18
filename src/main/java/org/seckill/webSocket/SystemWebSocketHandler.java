package org.seckill.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import sun.security.pkcs11.wrapper.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ken Pan on 2017/5/4.
 */
public class SystemWebSocketHandler implements WebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("connectionEstablished");
        users.add(webSocketSession);

        String userName = (String)webSocketSession.getAttributes().get("WEBSOCKET");

        webSocketSession.sendMessage(new TextMessage("connected"));
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.info("handleMessage : " + webSocketMessage.toString());

        //webSocketSession.sendMessage(new TextMessage(new Date() + ""));
        sendMessageToUsers(new TextMessage(new Date() + ""));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        users.remove(webSocketSession);

        logger.info("handleTransportError : " + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        users.remove(webSocketSession);
        logger.info("afterConnectionClosed ： " + closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
