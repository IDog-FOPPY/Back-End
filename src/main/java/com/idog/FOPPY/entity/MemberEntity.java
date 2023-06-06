package com.idog.FOPPY.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity(name="member")
@NoArgsConstructor
public class MemberEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String memberUsername;

    @Column
    private String memberPassword;

    @Column
    private String memberEmail;

    @Builder
    public MemberEntity(
            Long memberId,
            String memberPassword,
            String memberUsername,
            String memberEmail) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberUsername = memberUsername;
        this.memberEmail = memberEmail;
    }

}
