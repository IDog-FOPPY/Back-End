package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.User;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {

    Long save(ChatRoom chatRoom);
    Optional<ChatRoom> findByMembers(User member1, User member2);
    List<ChatRoom> findListByMemberId(Long memberId);
    Optional<ChatRoom> findById(Long roomId);
    void deleteById(Long roomId);
}
