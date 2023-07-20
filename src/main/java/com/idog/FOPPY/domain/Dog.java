package com.idog.FOPPY.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.idog.FOPPY.dto.dog.MissingInfoRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "dogs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name; // 개 이름
    @Column(name = "birth")
    private LocalDate birth; // 생일
    @Enumerated(EnumType.STRING)
    private PetSex sex;
    @Column(name = "breed", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Breed breed; // 견종
    private String note; // 메모
    private String disease;  // 질병

    @ElementCollection
    private List<String> imgUrlList = new ArrayList<>(); // Dog image URLs

    @Column(name = "is_missing", nullable = false)
    private Boolean isMissing; // 실종 유무(true=실종 / false=집에 있음)
    private String missingCity; // 실종 장소 (시)
    private String missingGu; // 실종 장소 (구)
    private String missingDong; // 실종 장소 (동)
    private String missingDetailedLocation; // 실종 장소 (상세 주소)
    private LocalDate missDate; // 실종 날짜
    @Embedded
    @Column(nullable = true)
    private MissingTime missTime; //실종 시간
    private String etc; // 실종 시 특이 사항

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    //== 생성 메서드 ==//
    public static Dog createDog(String name, LocalDate birth, PetSex sex, Breed breed, String note, String disease, List<String> imgUrlList){
        Dog dog = new Dog();
        dog.setName(name);
        dog.setBirth(birth);
        dog.setSex(sex);
        dog.setBreed(breed);
        dog.setNote(note);
        dog.setDisease(disease);
        dog.setIsMissing(false);
        dog.setImgUrlList(imgUrlList);
        return dog;
    }

    public void markAsMissing(MissingInfoRequest missingInfo) {
        this.isMissing = true;
        this.missingCity = missingInfo.getMissingCity();
        this.missingGu = missingInfo.getMissingGu();
        this.missingDong = missingInfo.getMissingDong();
        this.missingDetailedLocation = missingInfo.getMissingDetailedLocation();
        this.missDate = missingInfo.getMissDate();
        this.missTime = missingInfo.getMissTime();
        this.etc = missingInfo.getEtc();
    }

    private void setName(String name) { this.name = name; }
    private void setBirth(LocalDate birth) { this.birth = birth; }
    private void setSex(PetSex sex) { this.sex = sex; }
    private void setBreed(Breed breed) { this.breed = breed; }
    private void setNote(String note) { this.note = note; }
    private void setDisease(String disease) { this.disease = disease; }
    private void setIsMissing(Boolean isMissing) { this.isMissing = isMissing; }
    private void setImgUrlList(List<String> imgUrlList) { this.imgUrlList = imgUrlList; }

    public void setUser(User user) {
        this.user = user;
    }
}