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

    @Column
    private String password;

//    @OneToMany(mappedBy = "member")
//    private List<PetDogs> petDogs;

    private List<Long> petIds;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Member(
            Long uid,
            String username,
            String password,
            List<Long> petIds,
            LocalDateTime createdAt) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.petIds = petIds;
        this.createdAt = createdAt;
    }

    public void addPet(Long petId){
        if (petIds == null) {
            petIds = new ArrayList<>();
        }
        petIds.add(petId);
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
