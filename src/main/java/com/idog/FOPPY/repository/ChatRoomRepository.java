package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {

    Long save(ChatRoom chatRoom);
    Optional<ChatRoom> findByMembers(User member1, User member2);
    List<ChatRoom> findListByMemberId(Long memberId);
    Optional<ChatRoom> findById(Long roomId);
}

//    private Map<String, ChatRoomDTO> chatRoomDTOMap;
//
//    @PostConstruct
//    private void init() {
//        chatRoomDTOMap = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoomDTO> findAllRoom() {
//        // 채팅방 최근 생성된 순으로 반환
//        List<ChatRoomDTO> result = new ArrayList<>(chatRoomDTOMap.values());
//        Collections.reverse(result);
//        return result;
//    }
//
//    public ChatRoomDTO findRoomById(String id) {
//        return chatRoomDTOMap.get(id);
//    }
//
//    public ChatRoomDTO createChatRoom(String name) {
//        ChatRoomDTO chatRoomDTO = ChatRoomDTO.create(name);
//        chatRoomDTOMap.put(chatRoomDTO.getRoomId(), chatRoomDTO);
//        return chatRoomDTO;
//    }
//}
