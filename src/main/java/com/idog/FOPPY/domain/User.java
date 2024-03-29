package com.idog.FOPPY.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @OneToMany(mappedBy = "user")
    private final List<Dog> dogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @ToString.Exclude
    private final List<ChatRoomMember> chatRooms = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickName, String phone, String auth) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phone = phone;
    }

    // 연관관계 편의 메서드
    public Dog addDog(String name, LocalDate birth, PetSex sex, Breed breed, String note, String disease, Boolean neutered, List<String> imgUrlList, List<String> noseImgUrlList) {
        Dog dog = Dog.createDog(name, birth, sex, breed, note, disease, neutered, imgUrlList, noseImgUrlList);
        this.dogs.add(dog);
        dog.setUser(this); // Assuming you have a setUser method in Dog entity
        return dog;
    }

    public void addChatRoom(ChatRoomMember chatRoomMember) {
        this.chatRooms.add(chatRoomMember);
        if (chatRoomMember.getUser() != this) {
            chatRoomMember.setUser(this);
        }
    }

    public void changeNickname(String newNickname) {
        this.nickName = newNickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public String getProfileImgUrl() {
        try {
            return this.dogs.get(0).getImgUrlList().get(0);
        } catch (Exception e) {
            return "https://기본프사";
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isAccountNonExpired(){
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
    public boolean isEnabled(){
        return true;
    }

}
