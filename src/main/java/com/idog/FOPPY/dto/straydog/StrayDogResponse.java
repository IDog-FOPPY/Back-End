package com.idog.FOPPY.dto.straydog;

import com.idog.FOPPY.domain.Breed;

import com.idog.FOPPY.domain.MissingTime;

import com.idog.FOPPY.domain.StrayDog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StrayDogResponse {
    private Long id;

    private String imgUrl;
    private String missingCity;
    private String missingGu;
    private String missingDong;
    private String missingDetailedLocation;
    private LocalDate missDate;
    private MissingTime missTime;
    private Breed breed;
    private String etc;

    public static StrayDogResponse of(StrayDog stray) {
        return new StrayDogResponse(
                stray.getId(),
                stray.getImgUrlList().get(0),
                stray.getMissingCity(),
                stray.getMissingGu(),
                stray.getMissingDong(),
                stray.getMissingDetailedLocation(),
                stray.getMissDate(),
                stray.getMissTime(),
                stray.getBreed(),
                stray.getEtc()
        );
    }
}