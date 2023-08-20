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
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 채팅방입니다. senderId: " + chatMessageDto.getSenderId() + ", receiverId: " + chatMessageDto.getReceiverId()));

        ChatMessage chatMessage = chatRoom.addChatMessage(chatMessageDto.toEntity(sender, receiver, chatRoom));

        Long chatMessageId = chatRepository.save(chatMessage).getId();

        // lastMessage 업데이트
        chatRoom.updateLastMessage(chatMessage);
        chatRoomRepository.save(chatRoom);

        return chatMessageId;
    }

//    @PostConstruct
//    //의존관게 주입완료되면 실행되는 코드
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }
//
//
//    public ChatMessage save(ChatMessage chatMessage) {
//        return chatRepository.save(chatMessage);
//    }
//
//    // 모든 채팅방 조회
//    public List<ChatRoom> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//        List<ChatRoom> chatRooms = new ArrayList<>(chatRooms.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }
//
//    // 채팅방 조회
//    public ChatRoom findRoomById(Long roomId) {
//        return chatRooms.get(roomId);
//    }
//
//    // 채팅방 생성
//    public ChatRoom createChatRoom(String name) {
//        ChatRoom chatRoom = ChatRoom.builder()
//                .name(name)
//                .build();
//        chatRooms.put(chatRoom.getId(), chatRoom);
//        return chatRoom;
//    }
}
