package com.idog.FOPPY.dto.chat;

import com.idog.FOPPY.domain.ChatMessage;
import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import lombok.*;


public class ChatMessageDTO {

    @Setter
    @Getter
    public static class Send {
//        public enum MessageType {
//            JOIN, CHAT, LEAVE
//        }
        private String content;
        private Long senderId;
        private Long roomId;
//        private MessageType type;

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
        private Long senderId;
//        private List<ChatRoomDTO.MemberResponse> receivers;
        private String createdAt;

        public static Response of(ChatMessage chatMessage) {
            return Response.builder()
                    .messageId(chatMessage.getId())
                    .content(chatMessage.getContent())
                    .roomId(chatMessage.getChatRoom().getId())
                    .senderId(chatMessage.getSender().getId())
                    .createdAt(chatMessage.getCreatedAt().toString())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Notification {
        public enum NotificationType {
            NEWCHAT
        }
        private Long roomId;
        private Long senderId;
        private NotificationType type;
    }
}
