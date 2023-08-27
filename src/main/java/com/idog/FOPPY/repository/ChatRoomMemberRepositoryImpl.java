package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.ChatRoomMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatRoomMemberRepositoryImpl implements ChatRoomMemberRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<ChatRoomMember> findAllByUserId(Long userId) {
        return em.createQuery("select m from ChatRoomMember m where m.user.id = :userId", ChatRoomMember.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
