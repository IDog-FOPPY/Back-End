package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoomMember;

import java.util.List;

public interface ChatRoomMemberRepository {
    List<ChatRoomMember> findAllByUserId(Long userId);
}
