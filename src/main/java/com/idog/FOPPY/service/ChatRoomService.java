package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.dto.chat.ChatRoomDTO;
import com.idog.FOPPY.repository.ChatRoomRepository;
import com.idog.FOPPY.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Transactional(rollbackFor = Exception.class)
    public Long join(ChatRoomDTO.Request requestDto) throws IllegalStateException {
        User member1 = userRepository.findById(requestDto.getMember1Id())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + requestDto.getMember1Id()));
        User member2 = userRepository.findById(requestDto.getMember2Id())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + requestDto.getMember2Id()));

        if (requestDto.getMember2Id().equals(requestDto.getMember1Id())) {
            throw new IllegalStateException("자기 자신과 채팅방을 생성할 수 없습니다.");
        }
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByMembers(member1, member2);
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

    @Transactional(readOnly = true)
    public List<ChatRoomDTO.Response> getChatRoomList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = (String)authentication.getPrincipal();

        User user = userRepository.findByEmail(userEmail)  // Assuming you have a findByUsername method
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userEmail));

        return chatRoomRepository.findListByMemberId(user.getId()).stream().map(ChatRoomDTO.Response::of).collect(Collectors.toList());
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
