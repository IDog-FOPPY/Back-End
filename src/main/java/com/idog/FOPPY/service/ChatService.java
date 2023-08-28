package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.ChatMessage;
import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.dto.chat.ChatMessageDTO;
import com.idog.FOPPY.dto.chat.ChatRoomDTO;
import com.idog.FOPPY.repository.ChatRoomRepository;
import com.idog.FOPPY.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final Logger LOGGER = Logger.getLogger(ChatService.class.getName());

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public ChatMessageDTO.Response sendMessage(ChatMessageDTO.Send chatMessageDto) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = (String) authentication.getPrincipal();
//        User currentUser = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. email: " + email));
        User currentUser = userRepository.findById(Long.valueOf(1))  // 테스트용
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + 1));

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 채팅방입니다. chatRoomId: " + chatMessageDto.getRoomId()));
//        List<Long> allMemberIds = chatRoom.getMembers().stream()
//                .map(chatRoomMember -> chatRoomMember.getUser().getId())
//                .collect(Collectors.toList());

        ChatMessage chatMessage = chatRoom.addChatMessage(chatMessageDto.toEntity(currentUser, chatRoom));

        // lastMessage 업데이트
        chatRoom.updateLastMessage(chatMessage);
        chatRoomRepository.save(chatRoom);

        ChatMessageDTO.Response response = ChatMessageDTO.Response.of(chatMessage);
//        response.setSenderId(ChatRoomDTO.MemberResponse.of(currentUser));
//        List<ChatRoomMember> chatRoomMembers = chatRoom.getMembers();
//        List<ChatRoomDTO.MemberResponse> receivers = chatRoomMembers.stream()
//                .filter(chatRoomMember -> !chatRoomMember.getUser().getId().equals(currentUser.getId()))
//                .map(chatRoomMember -> ChatRoomDTO.MemberResponse.of(chatRoomMember.getUser()))
//                .collect(Collectors.toList());
//        response.setReceivers(receivers);

        return response;
    }
}
