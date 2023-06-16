package com.idog.FOPPY.repository;

import com.idog.FOPPY.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByUid(Long uid);

    Optional<Member> findByUsername(String username);

}
