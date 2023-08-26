package com.idog.FOPPY.dto.chat;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import lombok.*;

import java.util.List;

public class ChatRoomDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long userId;
        private Long dogId;

        public ChatRoom toEntity(User member1, User member2) {
            return ChatRoom.builder()
                    .member1(member1)
                    .member2(member2)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request2 {
//        private User member1;
//        private User member2;
        private Long member1Id;
        private Long member2Id;

        public ChatRoom toEntity(User member1, User member2) {
            return ChatRoom.builder()
                    .member1(member1)
                    .member2(member2)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private Long member1Id;
        private Long member2Id;
        private String member1NickName;
        private String member2NickName;
        private String member1ProfileImgUrl;
        private String member2ProfileImgUrl;
        private String lastMessage;
        private String lastMessageCreatedAt;
//        private String createdAt;

        public static Response of(ChatRoom chatRoom) {
            String member1ProfileImgUrl = getProfileImgUrl(chatRoom.getMember1());
            String member2ProfileImgUrl = getProfileImgUrl(chatRoom.getMember2());
            String lastMessage = chatRoom.getLastMessageId() == null ? "채팅을 시작해보세요!" : chatRoom.getLastMessageId().getContent();
            String lastMessageCreatedAt = chatRoom.getLastMessageId() == null ? "" : chatRoom.getLastMessageId().getCreatedAt().toString();

            return Response.builder()
                    .id(chatRoom.getId())
                    .member1Id(chatRoom.getMember1().getId())
                    .member2Id(chatRoom.getMember2().getId())
                    .member1NickName(chatRoom.getMember1().getNickName())
                    .member2NickName(chatRoom.getMember2().getNickName())
                    .member1ProfileImgUrl(member1ProfileImgUrl)
                    .member2ProfileImgUrl(member2ProfileImgUrl)
                    .lastMessage(lastMessage)
                    .lastMessageCreatedAt(lastMessageCreatedAt)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class JoinResponse {
        private Long roomId;
        private Long senderId;
        private Long receiverId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Detail {
        private Long id;
        private Long member1Id;
        private Long member2Id;
        private String member1NickName;
        private String member2NickName;
        private String member1ProfileImgUrl;
        private String member2ProfileImgUrl;
        private String createdAt;
        private List<ChatMessageDTO.Response> chatMessages;

        public static Detail of(ChatRoom chatRoom) {
            String member1ProfileImgUrl = getProfileImgUrl(chatRoom.getMember1());
            String member2ProfileImgUrl = getProfileImgUrl(chatRoom.getMember2());

            return Detail.builder()
                    .id(chatRoom.getId())
                    .member1Id(chatRoom.getMember1().getId())
                    .member2Id(chatRoom.getMember2().getId())
                    .member1NickName(chatRoom.getMember1().getNickName())
                    .member2NickName(chatRoom.getMember2().getNickName())
                    .member1ProfileImgUrl(member1ProfileImgUrl)
                    .member2ProfileImgUrl(member2ProfileImgUrl)
                    .createdAt(chatRoom.getCreatedAt().toString())
                    .chatMessages(chatRoom.getChatMessages().stream().map(ChatMessageDTO.Response::of).toList())
                    .build();
        }
    }

    private static String getProfileImgUrl(User user) {
        try {
            return user.getDogs().get(0).getImgUrlList().get(0);
        } catch (Exception e) {
            return "https://기본프사";
        }
    }
}
