package com.idog.FOPPY.dto.pet;

import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;

public class PetRequestDTO {

    private Long petId;
    private String petName;
    private boolean petSex;
    private breedState petBreed;
    private Long petOld;
    private String disease;
    private boolean neutered;

    public PetDogs toEntity() {
        return PetDogs.builder()
                .petId(petId)
                .petName(petName)
                .petSex(petSex)
                .petBreed(petBreed)
                .petOld(petOld)
                .disease(disease)
                .neutered(neutered)
                .build();
    }
}