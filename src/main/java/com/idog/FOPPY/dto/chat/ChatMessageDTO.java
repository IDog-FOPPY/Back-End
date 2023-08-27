package com.idog.FOPPY.dto.chat;

import com.idog.FOPPY.domain.ChatMessage;
import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import lombok.*;

import java.util.List;


public class ChatMessageDTO {

    @Getter
    public static class Send {
//        public enum MessageType {
//            JOIN, CHAT, LEAVE
//        }
        private String content;
        private Long roomId;
//        private MessageType messageType;

        public ChatMessage toEntity(User sender, ChatRoom chatRoom) {
            return ChatMessage.builder()
                    .content(content)
                    .sender(sender)
                    .chatRoom(chatRoom)
                    .build();
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long messageId;
        private String content;
        private Long roomId;
        private ChatRoomDTO.MemberResponse sender;
        private List<ChatRoomDTO.MemberResponse> receivers;
        private String createdAt;

        public static Response of(ChatMessage chatMessage) {
            return Response.builder()
                    .messageId(chatMessage.getId())
                    .content(chatMessage.getContent())
                    .roomId(chatMessage.getChatRoom().getId())
                    .createdAt(chatMessage.getCreatedAt().toString())
                    .build();
        }
    }
}
