package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.chat.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/public") // endpoint: /app/public
    @SendTo("/topic/messages") // /topic/messages 구독자에게 메시지 전달
    public MessageDTO receivePublicMessage(@Payload MessageDTO message) {
        return message;
    }

    @MessageMapping("/private") // endpoint: /app/private
    public MessageDTO receivePrivateMessage(@Payload MessageDTO message) {
        template.convertAndSendToUser(message.getReceiver(), "/queue", message); // endpoint: /user/{username}/queue
        return message;
    }
}
