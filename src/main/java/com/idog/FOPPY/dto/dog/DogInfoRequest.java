package com.idog.FOPPY.dto.dog;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.PetSex;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DogInfoRequest {
    private String name; // 개 이름
    private LocalDate birth; // 생일
    private PetSex sex;
    private Breed breed; // 견종
    private String note; // 메모
    private String disease;  // 질병
    private Boolean neutered;

    private Boolean isMissing; // 실종 정보
    private String missingCity; // 실종 장소 (시)
    private String missingGu; // 실종 장소 (구)
    private String missingDong; // 실종 장소 (동)
    private String missingDetailedLocation; // 실종 장소 (상세 주소)
    private LocalDate missDate; // 실종 날짜
    private LocalTime missTime; //실종 시간
    private String etc; // 실종 시 특이 사항
}
