package com.idog.FOPPY.dto.pet;

import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PetRequestDTO {

    private String petName;
    private Boolean petSex;
    private breedState petBreed;
    private Long petOld;
    private String disease;
    private Boolean neutered;
    private Boolean missed;
    private String missCity;
    private String missGu;
    private String missDong;
    private LocalDateTime missTime;


    @Builder
    public PetDogs toEntity() {
        return PetDogs.builder()
                .petName(petName)
                .petSex(petSex)
                .petBreed(petBreed)
                .petOld(petOld)
                .disease(disease)
                .neutered(neutered)
                .missed(missed)
                .missCity(missCity)
                .missGu(missGu)
                .missDong(missDong)
                .missTime(missTime)
                .build();
    }
}
