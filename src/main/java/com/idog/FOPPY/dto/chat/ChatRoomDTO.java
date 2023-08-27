package com.idog.FOPPY.dto.chat;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.ChatRoomMember;
import com.idog.FOPPY.domain.User;
import lombok.*;

import java.util.List;

public class ChatRoomDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinRequest1 {
        private Long dogId;

        public ChatRoom toEntity(List<ChatRoomMember> members) {
            return ChatRoom.builder()
                    .members(members)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinRequest2 {
        private List<Long> otherUserIds;

        public ChatRoom toEntity(List<ChatRoomMember> members) {
            return ChatRoom.builder()
                    .members(members)
                    .build();
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class JoinResponse {
        private Long roomId;
        private MemberResponse currentUser;
        private List<MemberResponse> otherUsers;
        private List<MemberResponse> allMembers;

        public static JoinResponse of(ChatRoom chatRoom) {
            List<MemberResponse> AllMembers = chatRoom.getMembers().stream().map(member -> {
                return MemberResponse.builder()
                        .id(member.getUser().getId())
                        .nickName(member.getUser().getNickName())
                        .profileImgUrl(member.getUser().getProfileImgUrl())
                        .build();
            }).distinct().toList();

            return JoinResponse.builder()
                    .roomId(chatRoom.getId())
                    .allMembers(AllMembers)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private Long roomId;
        private String lastMessage;
        private String lastMessageCreatedAt;
        private List<MemberResponse> members;

        public static Response of(ChatRoom chatRoom) {
            String lastMessage = chatRoom.getLastMessageId() == null ? "채팅을 시작해보세요!" : chatRoom.getLastMessageId().getContent();
            String lastMessageCreatedAt = chatRoom.getLastMessageId() == null ? "" : chatRoom.getLastMessageId().getCreatedAt().toString();

            List<MemberResponse> members = chatRoom.getMembers().stream().map(member -> {
                return MemberResponse.builder()
                        .id(member.getUser().getId())
                        .nickName(member.getUser().getNickName())
                        .profileImgUrl(member.getUser().getProfileImgUrl())
                        .build();
            }).toList();

            return Response.builder()
                    .roomId(chatRoom.getId())
                    .lastMessage(lastMessage)
                    .lastMessageCreatedAt(lastMessageCreatedAt)
                    .members(members)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberResponse {
        private Long id;
        private String nickName;
        private String profileImgUrl;

        public static MemberResponse of(User user) {
            return MemberResponse.builder()
                    .id(user.getId())
                    .nickName(user.getNickName())
                    .profileImgUrl(user.getProfileImgUrl())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Detail {
        private Long id;
        private String createdAt;
        private List<MemberResponse> members;
        private List<ChatMessageDTO.Response> chatMessages;

        public static Detail of(ChatRoom chatRoom) {
            List<MemberResponse> members = chatRoom.getMembers().stream().map(member -> {
                return MemberResponse.builder()
                        .id(member.getUser().getId())
                        .nickName(member.getUser().getNickName())
                        .profileImgUrl(member.getUser().getProfileImgUrl())
                        .build();
            }).toList();

            return Detail.builder()
                    .id(chatRoom.getId())
                    .members(members)
                    .createdAt(chatRoom.getCreatedAt().toString())
                    .chatMessages(chatRoom.getChatMessages().stream().map(ChatMessageDTO.Response::of).toList())
                    .build();
        }
    }
}
