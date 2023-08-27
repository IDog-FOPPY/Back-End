package com.idog.FOPPY.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @OneToOne
    @JoinColumn(name="sender_id", updatable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id", updatable = false)
    private ChatRoom chatRoom;  // 얘를 통해 recevierId를 알 수 있음

    @Builder
    public ChatMessage(String content, ChatRoom chatRoom, User sender) {
        this.content = content;
        this.chatRoom = chatRoom;
        this.sender = sender;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        if(this.chatRoom != null) {
            this.chatRoom.getChatMessages().remove(this);
        }
        this.chatRoom = chatRoom;
        if(!chatRoom.getChatMessages().contains(this)) {
            chatRoom.getChatMessages().add(this);
        }
    }
}
