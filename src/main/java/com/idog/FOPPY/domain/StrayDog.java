package com.idog.FOPPY.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "stray_dogs")
@NoArgsConstructor
public class StrayDog{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String missingCity; // 실종 장소 (시)
    private String missingGu; // 실종 장소 (구)
    private String missingDong; // 실종 장소 (동)
    private String missingDetailedLocation; // 실종 장소 (상세 주소)
    private LocalDate missDate; // 실종 날짜
    @Embedded
    private MissingTime missTime; //실종 시간
    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Breed breed; // 견종
    private String etc; // 실종 시 특이 사항

    @ElementCollection
    private List<String> imgUrlList = new ArrayList<>(); // stray Dog image URLs

    @Builder
    public StrayDog(String missingCity, String missingGu, String missingDong,
                    String missingDetailedLocation, LocalDate missDate,
                    MissingTime missTime, Breed breed, String etc, List<String> imgUrlList) {

        this.missingCity = missingCity;
        this.missingGu = missingGu;
        this.missingDong = missingDong;
        this.missingDetailedLocation = missingDetailedLocation;
        this.missDate = missDate;
        this.missTime = missTime;
        this.breed = breed;
        this.etc = etc;
        this.imgUrlList = imgUrlList;
    }

}
