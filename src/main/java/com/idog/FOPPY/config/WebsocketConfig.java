package com.idog.FOPPY.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹 소켓 연결 endpoint
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("http://localhost:8080", "http://localhost:3000", "http://localhost:3001", "https://foppy.netlify.app")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");  // 구독
        registry.setApplicationDestinationPrefixes("/pub");  // 발행
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(stompHandler);
    }
}

/**
 * enableSimpleBroker("/topic", "/queue")
 * - 메세지 수신 경로
 * - 메세지 브로커가 해당 api를 구독하고 있는 클라이언트에게 메세지를 전달
 * - /topic : 1:N 채팅
 * - /queue : 1:1 채팅
 *
 * setApplicationDestinationPrefixes("/app")
 * - 메세지 전송 경로
 * - 클라이언트가 메세지를 보낼 떄 “/app”로 시작하는 경로로 전송할 경우 Broker가 메세지를 처리
 */
