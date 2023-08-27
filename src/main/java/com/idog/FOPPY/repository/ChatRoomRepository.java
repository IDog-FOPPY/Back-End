package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.ChatRoomMember;
import com.idog.FOPPY.domain.User;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {

    Long save(ChatRoom chatRoom);
    Optional<ChatRoom> findByMembers(List<User> members);
//    List<ChatRoom> findListByMemberId(Long memberId);
    List<ChatRoomMember> findListByMemberId(Long memberId);
    Optional<ChatRoom> findById(Long roomId);
    void deleteById(Long roomId);
}
