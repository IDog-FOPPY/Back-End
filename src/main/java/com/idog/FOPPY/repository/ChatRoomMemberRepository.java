package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository {
    Optional<List<ChatRoom>> findAllChatRoomByUserId(Long userId);
}
