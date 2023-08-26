package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.ChatMessage;
import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.dto.chat.ChatMessageDTO;
import com.idog.FOPPY.repository.ChatRepository;
import com.idog.FOPPY.repository.ChatRoomRepository;
import com.idog.FOPPY.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private Map<String, ChatRoom> chatRooms;

    @Transactional(rollbackFor = Exception.class)
    public Long sendMessage(ChatMessageDTO.Send chatMessageDto) {
        User sender = userRepository.findById(chatMessageDto.getSenderId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + chatMessageDto.getSenderId()));
        User receiver = userRepository.findById(chatMessageDto.getReceiverId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + chatMessageDto.getReceiverId()));

        ChatRoom chatRoom = chatRoomRepository.findByMembers(sender, receiver)
                .orElseGet(() -> chatRoomRepository.findByMembers(receiver, sender)
                        .orElseThrow(() -> new IllegalStateException("존재하지 않는 채팅방입니다. senderId: " + chatMessageDto.getSenderId() + ", receiverId: " + chatMessageDto.getReceiverId())));

        ChatMessage chatMessage = chatRoom.addChatMessage(chatMessageDto.toEntity(sender, receiver, chatRoom));

        Long chatMessageId = chatRepository.save(chatMessage).getId();

        // lastMessage 업데이트
        chatRoom.updateLastMessage(chatMessage);
        chatRoomRepository.save(chatRoom);

        return chatMessageId;
    }
}
