package com.idog.FOPPY.config;

import com.idog.FOPPY.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) { // websocket 연결 요청
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            jwtProvider.validateToken(jwtToken); // 토큰 유효성 검사
        }
        return message;
    }
}