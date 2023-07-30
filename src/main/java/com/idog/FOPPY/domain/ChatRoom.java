package com.idog.FOPPY.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id1")
    private User member1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id2")
    private User member2;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "last_message_id", nullable = true)
    private ChatMessage lastMessage;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "chat_room_member",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> chatRoomMembers = new HashSet<>();

//    @Builder
//    public ChatRoom(String name, Set<User> chatRoomMembers) {
//        this.name = name;
//        this.chatRoomMembers = chatRoomMembers;
//    }

    public static ChatRoom createChatRoom(String chatRoomName, Set<User> chatRoomMembers) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.name = chatRoomName;
        chatRoom.chatRoomMembers = chatRoomMembers;
        return chatRoom;
    }

    public ChatMessage addChatMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
        if (chatMessage.getChatRoom() != this) {
            chatMessage.setChatRoom(this);
        }
        return chatMessage;
    }

    public void setUser(User user) {
        if(this.member1 != null) {
            this.member1.getChatRooms().remove(this);
        }
        this.chatRoomMembers.add(user);
        if(!user.getChatRooms().contains(this)) {
            user.getChatRooms().add(this);
        }
    }

    public void addChatRoomMembers(User user) {
        this.chatRoomMembers.add(user);
    }
}
