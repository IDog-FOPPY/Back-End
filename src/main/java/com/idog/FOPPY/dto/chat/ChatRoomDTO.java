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
        private String createdAt;

        public static Response of(ChatRoom chatRoom) {
            return Response.builder()
                    .id(chatRoom.getId())
                    .member1Id(chatRoom.getMember1().getId())
                    .member2Id(chatRoom.getMember2().getId())
                    .member1NickName(chatRoom.getMember1().getNickName())
                    .member2NickName(chatRoom.getMember2().getNickName())
                    .createdAt(chatRoom.getCreatedAt().toString())
                    .build();
        }
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
        private String member1ProfileImage;
        private String member2ProfileImage;
        private String createdAt;
        private List<ChatMessageDTO.Response> chatMessages;

        public static Detail of(ChatRoom chatRoom) {
            String member1ProfileImage, member2ProfileImage;
            try {
                member1ProfileImage = chatRoom.getMember1().getDogs().get(0).getImgUrlList().get(0);
            } catch (Exception e) {
                member1ProfileImage = "https://기본프사";
            }
            try {
                member2ProfileImage = chatRoom.getMember2().getDogs().get(0).getImgUrlList().get(0);
            } catch (Exception e) {
                member2ProfileImage = "https://기본프사";
            }

            return Detail.builder()
                    .id(chatRoom.getId())
                    .member1Id(chatRoom.getMember1().getId())
                    .member2Id(chatRoom.getMember2().getId())
                    .member1NickName(chatRoom.getMember1().getNickName())
                    .member2NickName(chatRoom.getMember2().getNickName())
                    .member1ProfileImage(member1ProfileImage)
                    .member2ProfileImage(member2ProfileImage)
                    .createdAt(chatRoom.getCreatedAt().toString())
                    .chatMessages(chatRoom.getChatMessages().stream().map(ChatMessageDTO.Response::of).toList())
                    .build();
        }
    }
}
