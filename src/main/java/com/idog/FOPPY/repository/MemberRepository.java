package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    public MemberEntity findByUid(Long uid);

    Optional<MemberEntity> findByUsername(String username);

}
