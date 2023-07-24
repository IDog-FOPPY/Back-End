package com.idog.FOPPY.dto.straydog;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.MissingTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StrayInfoRequest {
    private String missingCity; // 실종 장소 (시)
    private String missingGu; // 실종 장소 (구)
    private String missingDong; // 실종 장소 (동)
    private String missingDetailedLocation; // 실종 장소 (상세 주소)
    private LocalDate missDate; // 실종 날짜
    private MissingTime missTime; //실종 시간
    private Breed breed; // 견종
    private String etc; // 실종 시 특이 사항

}
