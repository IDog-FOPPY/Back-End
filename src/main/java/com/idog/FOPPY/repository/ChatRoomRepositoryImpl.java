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
    public Optional<ChatRoom> findByMembers(List<User> members) {
        String query = "select c from ChatRoom c where " +
                "size(c.members) = :memberCount and " +
                "not exists (" +
                "    select m from ChatRoomMember m " +
                "    where m.chatRoom = c and m.user not in :members" +
                ")";

        List<ChatRoom> result = em.createQuery(query, ChatRoom.class)
                .setParameter("memberCount", members.size())
                .setParameter("members", members)
                .getResultList();

        if (!result.isEmpty()) {
            return result.stream().findAny();
        }
        return Optional.empty();
    }

    @Override
//    public List<ChatRoom> findListByMemberId(Long memberId) {
//        return em.createQuery("select c from ChatRoom c where c.member1.id = :memberId or c.member2.id = :memberId", ChatRoom.class)
//                .setParameter("memberId", memberId)
//                .getResultList();
//    }
//    public List<ChatRoom> findListByMemberId(Long memberId) {
//        return em.createQuery("select c from ChatRoom c where :memberId member of c.members", ChatRoom.class)
//                .setParameter("memberId", memberId)
//                .getResultList();
//    }
    public List<ChatRoomMember> findListByMemberId(Long memberId) {
        return em.createQuery("select m from ChatRoomMember m where m.user.id = :memberId", ChatRoomMember.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void deleteById(Long roomId) {
        em.remove(em.find(ChatRoom.class, roomId));
    }
}
