package com.idog.FOPPY.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter  @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetDogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId; // PK

    private String petName; // 개 이름
    private Boolean petSex; // 개 성별(true=여아 / flase=남아)
    private breedState petBreed; // 견종
    private Long petOld; // 개 나이
    private String disease;  // 질병
    private Boolean neutered; // 중성화 유무(true=유 / false=무)
    private String note; // 메모

    private Boolean missed; // 실종 유무(true=실종 / false=집에 있음)
    private String missCity; // 실종 장소 (시)
    private String missGu; // 실종 장소 (구)
    private String missDong; // 실종 장소 (동)
    private String missDetail; // 실종 장소 (상세 주소)
    private LocalDate missDate; // 실종 날짜
    private LocalTime missTime; //실종 시간
    private String etc; // 실종 시 특이 사항

    private final LocalDateTime createdDate = LocalDateTime.now(); // 생성일

    @ManyToOne
    @JoinColumn(name = "member_uid")
    private Member member;

    @Builder
    public PetDogs(Long petId, String petName, Boolean petSex, breedState petBreed, Long petOld, String disease,
                   Boolean neutered, String note, String missCity, String missGu, String missDong, String missDetail,
                   LocalTime missTime, LocalDate missDate, String etc, Boolean missed) {
        this.petId = petId;
        this.petName = petName;
        this.petSex = petSex;
        this.petBreed = petBreed;
        this.petOld = petOld;
        this.disease = disease;
        this.neutered = neutered;
        this.note = note;
        this.missed = missed;
        this.missCity = missCity;
        this.missGu = missGu;
        this.missDong = missDong;
        this.missDetail = missDetail;
        this.missDate = missDate;
        this.missTime = missTime;
        this.etc = etc;
    }

    public void updatePetDogs(String petName, Boolean petSex, breedState petBreed, Long petOld,
                              String disease, Boolean neutered,String note, Boolean missed,
                              String missCity, String missGu, String missDong,String missDetail,
                              LocalDate missDate,LocalTime missTime, String etc) {
        this.petName = petName;
        this.petSex = petSex;
        this.petBreed = petBreed;
        this.petOld = petOld;
        this.disease = disease;
        this.neutered = neutered;
        this.note = note;
        this.missed = missed;
        this.missCity = missCity;
        this.missGu = missGu;
        this.missDong = missDong;
        this.missDetail = missDetail;
        this.missTime = missTime;
        this.missDate = missDate;
        this.etc = etc;
    }

}