package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoom;
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
    public Optional<ChatRoom> findByMembers(User member1, User member2) {
        List<ChatRoom> result = em.createQuery("select c from ChatRoom c where c.member1 = :member1 and c.member2 = :member2", ChatRoom.class)
                .setParameter("member1", member1)
                .setParameter("member2", member2)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<ChatRoom> findListByMemberId(Long memberId) {
        return em.createQuery("select c from ChatRoom c where c.member1.id = :memberId or c.member2.id = :memberId", ChatRoom.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void deleteById(Long roomId) {
        em.remove(em.find(ChatRoom.class, roomId));
    }
}
