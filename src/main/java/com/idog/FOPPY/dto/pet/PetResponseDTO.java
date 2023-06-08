package com.idog.FOPPY.dto.pet;

import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;

import java.time.LocalDateTime;

public class PetResponseDTO {
    private Long petId;
    private String petName;
    private Boolean petSex;
    private breedState petBreed;
    private Long petOld;
    private String disease;
    private Boolean neutered;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate;

    public PetResponseDTO(PetDogs entity) {
        this.petId = entity.getPetId();
        this.petName = entity.getPetName();
        this.petSex= entity.getPetSex();
        this.petBreed= entity.getPetBreed();
        this.petOld= entity.getPetOld();
        this.disease= entity.getDisease();
        this.neutered= entity.getNeutered();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
