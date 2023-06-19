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
    private List<Long> petIds;
    private String email;
    private String phoneNum;
    private String address;

//    @OneToMany(mappedBy = "member")
//    private List<PetDogs> petDogs;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Member(Long uid, String username, String password, List<Long> petIds,
            String email, String phoneNum, String address, LocalDateTime createdAt) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.petIds = petIds;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.createdAt = createdAt;
    }

    public void addPet(Long petId){
        if (petIds == null) {
            petIds = new ArrayList<>();
        }
        petIds.add(petId);
    }

    public void removePet(Long petId){
        if (petIds == null) {
            petIds = new ArrayList<>();
        }
        petIds.remove(petId);
    }

    public void update(String username, String password, String email, String phoneNum, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
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
