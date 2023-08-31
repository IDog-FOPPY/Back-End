package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ChatRoomMemberRepositoryImpl implements ChatRoomMemberRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<List<ChatRoom>> findAllChatRoomByUserId(Long userId) {
        // userId를 가진 유저가 속한 채팅방을 모두 반환
        String query = "select crm.chatRoom from ChatRoomMember crm " +
                "where crm.user.id = :userId ";

        List<ChatRoom> result = em.createQuery(query, ChatRoom.class)
                .setParameter("userId", userId)
                .getResultList();

        if (!result.isEmpty()) {
            return Optional.of(result);
        }
        return Optional.empty();
    }

    @Override
    public void deleteAllByChatRoomId(Long roomId) {
        String query = "delete from ChatRoomMember crm " +
                "where crm.chatRoom.id = :chatRoomId ";

        em.createQuery(query)
                .setParameter("chatRoomId", roomId)
                .executeUpdate();
    }
}
