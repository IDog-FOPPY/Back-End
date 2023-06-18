package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.chat.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void sendMessage(ChatDTO chatDto, SimpMessageHeaderAccessor accessor) {
        template.convertAndSend("/sub/channel/" + chatDto.getChannelId(), chatDto);
    }
}
