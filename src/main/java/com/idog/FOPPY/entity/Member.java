package com.idog.FOPPY.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name="member")
@NoArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(unique = true)
    private String username;

    private String password;
    private String email;
    private String phoneNum;
    private String address;
    private String profileURL; // 프로필 이미지 경로
    private List<Long> petIDs; // 등록되어 있는 반려견ID 리스트

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Member(
            Long uid,
            String username,
            String password,
            String email,
            String phoneNum,
            String address,
            String profileURL,
            List<Long> petIDs,
            LocalDateTime createdAt) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.profileURL = profileURL;
        this.petIDs = petIDs;
        this.createdAt = createdAt;
    }

    public void updateMember(String username, String password, String email,
                             String phoneNum, String address, String profileURL){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.profileURL = profileURL;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "ROLE_USER";
        });

        return collectors;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
