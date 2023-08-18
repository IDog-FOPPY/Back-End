package com.idog.FOPPY.dto.chat;

import com.idog.FOPPY.domain.ChatMessage;
import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import lombok.*;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class ChatMessageDTO {

    @Getter
    public static class Send {
//        public enum MessageType {
//            JOIN, CHAT, LEAVE
//        }
        private String content;
//        private User sender;
//        private User receiver;
//        private ChatRoom chatRoom;
        private Long senderId;
        private Long receiverId;
        private Long roomId;
//        private MessageType messageType;

        public ChatMessage toEntity(User sender, User receiver, ChatRoom chatRoom) {
            return ChatMessage.builder()
                    .content(content)
                    .sender(sender)
                    .receiver(receiver)
                    .chatRoom(chatRoom)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String content;
        private Long senderId;
        private Long receiverId;
        private Long roomId;
        private String senderNickName;
        private String receiverNickName;
        private String createdAt;

        public static Response of(ChatMessage chatMessage) {
            return Response.builder()
                    .id(chatMessage.getId())
                    .content(chatMessage.getContent())
                    .senderId(chatMessage.getSender().getId())
                    .receiverId(chatMessage.getReceiver().getId())
                    .roomId(chatMessage.getChatRoom().getId())
                    .senderNickName(chatMessage.getSender().getNickName())
                    .receiverNickName(chatMessage.getReceiver().getNickName())
                    .createdAt(chatMessage.getCreatedAt().toString())
                    .build();
        }
    }


}
