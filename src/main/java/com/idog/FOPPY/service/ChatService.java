package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.ChatMessage;
import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.dto.chat.ChatMessageDTO;
import com.idog.FOPPY.repository.ChatRepository;
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
    private Map<String, ChatRoom> chatRooms;

    @Transactional(rollbackFor = Exception.class)
    public Long sendMessage(ChatMessageDTO.Send chatMessageDto) {
        ChatRoom chatRoom = chatRooms.get(chatMessageDto.getChatRoom().getId());
        ChatMessage chatMessage = chatRoom.addChatMessage(chatMessageDto.toEntity());
        return chatRepository.save(chatMessage).getId();
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
