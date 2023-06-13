package com.idog.FOPPY.dto.pet;

import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PetRequestDTO {

    private Long petId;
    private String petName;
    private Boolean petSex;
    private breedState petBreed;
    private Long petOld;
    private String disease;
    private Boolean neutered;
    private Boolean missed;
    private String missLocation_city;
    private String missLocation_gu;
    private String missLocation_dong;
    private LocalDateTime missTime;


    public PetDogs toEntity() {
        return PetDogs.builder()
                .petId(petId)
                .petName(petName)
                .petSex(petSex)
                .petBreed(petBreed)
                .petOld(petOld)
                .disease(disease)
                .neutered(neutered)
                .missed(missed)
                .missLocation_city(missLocation_city)
                .missLocation_gu(missLocation_gu)
                .missLocation_dong(missLocation_dong)
                .missTime(missTime)
                .build();
    }
}