package com.idog.FOPPY.dto.dog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.PetSex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogInfoResponse {

    private String name; // 개 이름
    private LocalDate birth; // 생일
    private PetSex sex;
    private Breed breed; // 견종
    private String note; // 메모
    private String disease;  // 질병
    private Boolean neutered;
    private List<String> imgUrlList;

    private Boolean isMissing; // 실종 유무(true=실종 / false=집에 있음)
    private String missingCity; // 실종 장소 (시)
    private String missingGu; // 실종 장소 (구)
    private String missingDong; // 실종 장소 (동)
    private String missingDetailedLocation; // 실종 장소 (상세 주소)
    private LocalDate missDate; // 실종 날짜
    private LocalTime missTime;
    private String etc; // 실종 시 특이 사항

    public DogInfoResponse(Dog dog) {
        this.name = dog.getName();
        this.birth = dog.getBirth();
        this.sex = dog.getSex();
        this.breed = dog.getBreed();
        this.note = dog.getNote();
        this.disease = dog.getDisease();
        this.neutered = dog.getNeutered();
        this.imgUrlList = dog.getImgUrlList() != null ? new ArrayList<>(dog.getImgUrlList()) : new ArrayList<>();

        this.isMissing = dog.getIsMissing();
        this.missingCity = dog.getMissingCity();
        this.missingGu = dog.getMissingGu();
        this.missingDong = dog.getMissingDong();
        this.missingDetailedLocation = dog.getMissingDetailedLocation();
        this.missDate = dog.getMissDate();
        this.missTime = dog.getMissTime();
        this.etc = dog.getEtc();
    }
}
