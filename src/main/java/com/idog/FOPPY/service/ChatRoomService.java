package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.dto.chat.ChatRoomDTO;
import com.idog.FOPPY.repository.ChatRoomRepository;
import com.idog.FOPPY.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        User member1 = userRepository.findById(requestDto.getMemberId1())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + requestDto.getMemberId1()));
        User member2 = userRepository.findById(requestDto.getMemberId2())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + requestDto.getMemberId2()));

        if (requestDto.getMemberId2().equals(requestDto.getMemberId1())) {
            throw new IllegalStateException("자기 자신과 채팅방을 생성할 수 없습니다.");
        }
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByMembers(member1, member2);
        if (chatRoom.isPresent()) {
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
    public List<ChatRoomDTO.Response> getChatRoomList(Long memberId) {
        return chatRoomRepository.findListByMemberId(memberId).stream().map(ChatRoomDTO.Response::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChatRoomDTO.Detail getChatRoomDetail(Long roomId) {
        Optional<ChatRoomDTO.Detail> room = chatRoomRepository.findById(roomId).map(ChatRoomDTO.Detail::of);
        return room.orElseThrow();
    }
}
