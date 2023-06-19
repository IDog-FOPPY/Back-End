package com.idog.FOPPY.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private List<Long> petIds;
    private String email;
    private String phoneNum;
    private String address;

    @OneToMany(mappedBy = "member")
    private List<PetDogs> petDogs;

//    @CreatedDate
//    @Column(name="created_at")
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    @Column(name="updated_at")
//    private LocalDateTime updatedAt;

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

//     @Builder
//     public Member(Long uid, String username, String password, List<Long> petIds,
//             String email, String phoneNum, String address, LocalDateTime createdAt) {
//         this.uid = uid;
//         this.username = username;
//         this.password = password;
//         this.petIds = petIds;
//         this.email = email;
//         this.phoneNum = phoneNum;
//         this.address = address;
//         this.createdAt = createdAt;
//     }

//     public void addPet(Long petId){
//         if (petIds == null) {
//             petIds = new ArrayList<>();
//         }
//         petIds.add(petId);
//     }

//     public void update(String username, String password, String email, String phoneNum, String address) {
//         this.username = username;
//         this.password = password;
//         this.email = email;
//         this.phoneNum = phoneNum;
//         this.address = address;
//     }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
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
