package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.dto.chat.ChatRoomDTO;
import com.idog.FOPPY.repository.ChatRoomRepository;
import com.idog.FOPPY.repository.DogRepository;
import com.idog.FOPPY.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final DogRepository dogRepository;

    // userId, userId로 채팅방 생성
    @Transactional(rollbackFor = Exception.class)
    public Long join(ChatRoomDTO.Request2 requestDto) throws IllegalStateException {
        User member1 = userRepository.findById(requestDto.getMember1Id())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + requestDto.getMember1Id()));
        User member2 = userRepository.findById(requestDto.getMember2Id())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + requestDto.getMember2Id()));

        if (requestDto.getMember2Id().equals(requestDto.getMember1Id())) {
            throw new IllegalStateException("자기 자신과 채팅방을 생성할 수 없습니다.");
        }
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByMembers(member1, member2)
                .or(() -> chatRoomRepository.findByMembers(member2, member1));
        if (chatRoom.isPresent()) { // 이미 존재하는 채팅방이면
            return chatRoom.get().getId();
        } else {
            return chatRoomRepository.save(requestDto.toEntity(member1, member2));
//            return chatRoomRepository.save(ChatRoom.builder()
//                    .member1(request.getMember1())
//                    .member2(request.getMember2())
//                    .build());
        }
    }

    // userId, dogId로 채팅방 생성
    @Transactional(rollbackFor = Exception.class)
    public Long join2(ChatRoomDTO.Request requestDto) throws IllegalStateException {
        User member = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + requestDto.getUserId()));
        Dog dog = dogRepository.findById(requestDto.getDogId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 강아지입니다. dogId: " + requestDto.getDogId()));
        User strayDogMember = dog.getUser();

        if (requestDto.getUserId().equals(strayDogMember.getId())) {
            throw new IllegalStateException("자기 자신과 채팅방을 생성할 수 없습니다.");
        }
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByMembers(member, strayDogMember)
                .or(() -> chatRoomRepository.findByMembers(strayDogMember, member));
        if (chatRoom.isPresent()) { // 이미 존재하는 채팅방이면
            return chatRoom.get().getId();
        } else {
            return chatRoomRepository.save(requestDto.toEntity(member, strayDogMember));
        }
    }

    @Transactional(readOnly = true)
    public List<ChatRoomDTO.Response> getChatRoomList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = (String) authentication.getPrincipal();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User ot found with username: " + userEmail));

        List<ChatRoom> chatRooms = chatRoomRepository.findListByMemberId(user.getId());
        return chatRooms.stream().map(ChatRoomDTO.Response::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChatRoomDTO.Detail getChatRoomDetail(Long roomId) {
        Optional<ChatRoomDTO.Detail> roomDetail = chatRoomRepository.findById(roomId).map(ChatRoomDTO.Detail::of);
        return roomDetail.orElseThrow(() -> new IllegalStateException("The chat room does not exist. roomId: " + roomId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChatRoom(Long roomId) {
        try {
            chatRoomRepository.deleteById(roomId);
        } catch (Exception e) {
            throw new IllegalStateException("The chat room does not exist. roomId: " + roomId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateChatRoomName(Long roomId, String newChatRoomName) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();
        chatRoom.updateChatRoomName(newChatRoomName);
        chatRoomRepository.save(chatRoom);
        return ResponseEntity.ok("The chat room name has been changed.");
    }

}
