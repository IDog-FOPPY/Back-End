package com.idog.FOPPY.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StrayDogs{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strayId; // PK

    private Boolean straySex; // 개 성별
    private breedState strayBreed; // 견종

    private String findLocation ; // 유기견 발견 장소
    private LocalDateTime findTime; // 유기견 발견 시간
    private String description;  // 유기견 상세 설명

    private final LocalDateTime createdDate = LocalDateTime.now(); // 생성일

    @Builder
    public StrayDogs(Long strayId, Boolean straySex, breedState strayBreed, String findLocation,
                     LocalDateTime findTime, String description){
        this.strayId = strayId;
        this.straySex = straySex;
        this.strayBreed = strayBreed;
        this.findLocation = findLocation;
        this.findTime = findTime;
        this.description = description;
    }
}