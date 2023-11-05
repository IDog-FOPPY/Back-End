package com.idog.FOPPY.config;

import com.idog.FOPPY.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) { // 채널에 메시지를 전달하기 전에 실행되는 메소드
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) { // websocket 연결 요청
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            if (!jwtProvider.validateToken(jwtToken)) { // 토큰 유효성 검사
                throw new AccessDeniedException("유효하지 않은 토큰입니다.");
            }
        }
        // 토큰이 유효하다면 message 전달
        return message;
    }
}
