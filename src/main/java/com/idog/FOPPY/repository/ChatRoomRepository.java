package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.ChatRoomMember;
import com.idog.FOPPY.domain.User;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {

    Long save(ChatRoom chatRoom);
    Optional<ChatRoom> findById(Long roomId);
    Optional<ChatRoom> findAllByUsers(List<User> users);

    void deleteById(Long roomId);
}
