package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.ChatRoomMember;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.dto.chat.ChatRoomDTO;
import com.idog.FOPPY.repository.ChatRoomMemberRepository;
import com.idog.FOPPY.repository.ChatRoomRepository;
import com.idog.FOPPY.repository.DogRepository;
import com.idog.FOPPY.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final Logger LOGGER = Logger.getLogger(ChatRoomService.class.getName());

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserRepository userRepository;
    private final DogRepository dogRepository;

    // userId, dogId로 채팅방 생성
    @Transactional(rollbackFor = Exception.class)
    public ChatRoomDTO.JoinResponse join1(ChatRoomDTO.JoinRequest1 requestDto) throws IllegalStateException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long userId = userRepository.findByEmail(email).get().getId();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. email: " + email));
        Dog dog = dogRepository.findById(requestDto.getDogId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 강아지입니다. dogId: " + requestDto.getDogId()));
        User strayDogUser = dog.getUser();

        if (userId.equals(strayDogUser.getId())) {
            throw new IllegalStateException("자기 자신과 채팅방을 생성할 수 없습니다.");
        }

        List<User> members = new ArrayList<>();
        members.add(currentUser);
        members.add(strayDogUser);

        Optional<ChatRoom> chatRoom = chatRoomRepository.findByMembers(members);  // FIXME: 제대로 안되고 있음 - 채팅방 인원수가 2일 때 채팅방 인원이 같은지 확인하도록 고쳐야겠음
        if (chatRoom.isPresent()) { // 이미 존재하는 채팅방이면
            ChatRoomDTO.JoinResponse joinResponse = ChatRoomDTO.JoinResponse.of(chatRoom.get());
            joinResponse.setAllMembers(members.stream().map(member -> ChatRoomDTO.MemberResponse.of(member)).collect(Collectors.toList()));
            joinResponse.setCurrentUser(ChatRoomDTO.MemberResponse.of(currentUser));
            joinResponse.setOtherUsers(members.stream().map(member -> ChatRoomDTO.MemberResponse.of(member)).filter(member -> !member.getId().equals(userId)).collect(Collectors.toList()));
            return joinResponse;
        } else {
            List<ChatRoomMember> chatRoomMembers = new ArrayList<>();
            for (User user : members) {
                ChatRoomMember chatRoomMember = new ChatRoomMember();
                chatRoomMember.setUser(user);
                chatRoomMembers.add(chatRoomMember);
            }

            Long roomId = chatRoomRepository.save(requestDto.toEntity(chatRoomMembers));
            ChatRoom room = chatRoomRepository.findById(roomId).get();
            LOGGER.info(room.getMembers().stream().map(member -> member.getUser().getId()).toList().toString());

            for (ChatRoomMember chatRoomMember : chatRoomMembers) {
                chatRoomMember.setChatRoom(room);
            }

            LOGGER.info(room.getMembers().stream().map(member -> member.getUser().getId()).toList().toString());
            ChatRoomDTO.JoinResponse joinResponse = ChatRoomDTO.JoinResponse.of(room);
            joinResponse.setAllMembers(members.stream().map(member -> ChatRoomDTO.MemberResponse.of(member)).collect(Collectors.toList()));
            joinResponse.setCurrentUser(ChatRoomDTO.MemberResponse.of(currentUser));
            joinResponse.setOtherUsers(members.stream().map(member -> ChatRoomDTO.MemberResponse.of(member)).filter(member -> !member.getId().equals(userId)).collect(Collectors.toList()));
            return joinResponse;
        }
    }

    // userId, userId로 채팅방 생성
    @Transactional(rollbackFor = Exception.class)
    public ChatRoomDTO.JoinResponse join2(ChatRoomDTO.JoinRequest2 requestDto) throws IllegalStateException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long userId = userRepository.findByEmail(email).get().getId();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. email: " + email));

        List<Long> memberIds = requestDto.getOtherUserIds();
        LOGGER.info(memberIds.toString());
        List<User> members = new ArrayList<>();
        for (Long memberId : memberIds) {
            User member = userRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다. userId: " + memberId));
            members.add(member);
        }
        members.add(currentUser);

        Optional<ChatRoom> chatRoom = chatRoomRepository.findByMembers(members);
        if (chatRoom.isPresent()) { // 이미 존재하는 채팅방이면
            ChatRoomDTO.JoinResponse joinResponse = ChatRoomDTO.JoinResponse.of(chatRoom.get());
            joinResponse.setAllMembers(members.stream().map(member -> ChatRoomDTO.MemberResponse.of(member)).collect(Collectors.toList()));
            joinResponse.setCurrentUser(ChatRoomDTO.MemberResponse.of(currentUser));
            joinResponse.setOtherUsers(members.stream().map(member -> ChatRoomDTO.MemberResponse.of(member)).filter(member -> !member.getId().equals(userId)).collect(Collectors.toList()));
            return joinResponse;
        } else {
            List<ChatRoomMember> chatRoomMembers = new ArrayList<>();
            for (User user : members) {
                ChatRoomMember chatRoomMember = new ChatRoomMember();
                chatRoomMember.setUser(user);
                chatRoomMembers.add(chatRoomMember);
            }

            Long roomId = chatRoomRepository.save(requestDto.toEntity(chatRoomMembers));
            ChatRoom room = chatRoomRepository.findById(roomId).get();
            LOGGER.info(room.getMembers().stream().map(member -> member.getUser().getId()).toList().toString());

            for (ChatRoomMember chatRoomMember : chatRoomMembers) {
                chatRoomMember.setChatRoom(room);
            }

            LOGGER.info(room.getMembers().stream().map(member -> member.getUser().getId()).toList().toString());
            ChatRoomDTO.JoinResponse joinResponse = ChatRoomDTO.JoinResponse.of(room);
            joinResponse.setAllMembers(members.stream().map(member -> ChatRoomDTO.MemberResponse.of(member)).collect(Collectors.toList()));
            joinResponse.setCurrentUser(ChatRoomDTO.MemberResponse.of(currentUser));
            joinResponse.setOtherUsers(members.stream().map(member -> ChatRoomDTO.MemberResponse.of(member)).filter(member -> !member.getId().equals(userId)).collect(Collectors.toList()));
            return joinResponse;
        }
    }

    @Transactional(readOnly = true)
    public List<ChatRoomDTO.Response> getChatRoomList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = (String) authentication.getPrincipal();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User ot found with username: " + userEmail));

        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findAllByUserId(user.getId());
        LOGGER.info(chatRoomMembers.toString());
        List<ChatRoom> chatRooms = chatRoomMembers.stream().map(chatRoomMember -> chatRoomMember.getChatRoom()).collect(Collectors.toList());
        LOGGER.info(chatRooms.toString());
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
}
