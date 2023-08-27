package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoom;
import com.idog.FOPPY.domain.ChatRoomMember;
import com.idog.FOPPY.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long save(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom.getId();
    }

    @Override
    public Optional<ChatRoom> findById(Long roomId) {
        return Optional.ofNullable(em.find(ChatRoom.class, roomId));
    }

    @Override
    public Optional<ChatRoom> findAllByUsers(List<User> users) {
        // members의 수와 일치하고, members가 속한 채팅방이 존재하면 해당 채팅방을 반환
        Long memberCount = (long) users.size();
        String query = "select cr from ChatRoom cr " +
                "join cr.members crm " +
                "where crm.user in :members " +
                "group by cr " +
                "having count(distinct crm.user) = :memberCount and count(distinct crm) = :memberCount";

        List<ChatRoom> result = em.createQuery(query, ChatRoom.class)
                .setParameter("memberCount", memberCount)
                .setParameter("members", users)
                .getResultList();

        if (!result.isEmpty()) {
            return result.stream().findAny();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long roomId) {
        em.remove(em.find(ChatRoom.class, roomId));
    }
}
