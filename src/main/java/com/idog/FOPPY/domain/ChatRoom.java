package com.idog.FOPPY.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_room_name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "last_message_id", nullable = true)
    private ChatMessage lastMessageId;
    private String lastMessageContent;
    private LocalDateTime lastMessageAt;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private final List<ChatRoomMember> members = new ArrayList<>();

    @Builder
    public ChatRoom(List<ChatRoomMember> members) {
        this.members.addAll(members);
    }

    public void addMember(ChatRoomMember chatRoomMember) {
        this.members.add(chatRoomMember);
        if (chatRoomMember.getChatRoom() != this) {
            chatRoomMember.setChatRoom(this);
        }
    }

    public void addMembers(List<ChatRoomMember> members) {
        this.members.addAll(members);
    }

    public ChatMessage addChatMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
        if (chatMessage.getChatRoom() != this) {
            chatMessage.setChatRoom(this);
        }
        return chatMessage;
    }

    public void updateLastMessage(ChatMessage chatMessage) {
        this.lastMessageId = chatMessage;
        this.lastMessageContent = chatMessage.getContent();
        this.lastMessageAt = chatMessage.getCreatedAt();
    }

    public void updateChatRoomName(String chatRoomName) {
        this.name = chatRoomName;
    }
}
