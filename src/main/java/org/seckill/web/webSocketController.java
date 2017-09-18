package org.seckill.web;

import org.seckill.webSocket.SystemWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;

/**
 * Created by Ken Pan on 2017/5/4.
 */
@Controller
@RequestMapping("/webSocket")
public class webSocketController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/webSocket",method = RequestMethod.GET)
    public String webSocket(){
        return "webSocket";
    }

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "{message}/send")
    public ResponseEntity<String> sendMessage(@PathVariable("message")String message){

        systemWebSocketHandler().sendMessageToUsers(new TextMessage(message));

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @Bean
    public SystemWebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }
}
