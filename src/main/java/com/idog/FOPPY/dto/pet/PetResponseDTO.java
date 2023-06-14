package com.idog.FOPPY.dto.pet;

import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PetResponseDTO {
    private Long petId;
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
    private LocalDateTime createdDate = LocalDateTime.now();

    public PetResponseDTO(PetDogs entity) {
        this.petId = entity.getPetId();
        this.petName = entity.getPetName();
        this.petSex= entity.getPetSex();
        this.petBreed= entity.getPetBreed();
        this.petOld= entity.getPetOld();
        this.disease= entity.getDisease();
        this.neutered= entity.getNeutered();
        this.missed= entity.getMissed();
        this.missCity = entity.getMissCity();
        this.missGu = entity.getMissGu();
        this.missDong = entity.getMissDong();
        this.missTime = entity.getMissTime();
        this.createdDate = entity.getCreatedDate();
    }
}
