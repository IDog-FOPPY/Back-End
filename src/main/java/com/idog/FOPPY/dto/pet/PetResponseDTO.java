package com.idog.FOPPY.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class PetResponseDTO {
    private Long petId;
    private String petName;
    private Boolean petSex;
    private breedState petBreed;
    private Long petOld;
    private String disease;
    private Boolean neutered;
    private String note;
    private Boolean missed;
    private String missCity;
    private String missGu;
    private String missDong;
    private String missDetail;

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime missTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate missDate;

    private String etc;
    private LocalDateTime createdDate = LocalDateTime.now();

    public PetResponseDTO(PetDogs entity) {
        this.petId = entity.getPetId();
        this.petName = entity.getPetName();
        this.petSex= entity.getPetSex();
        this.petBreed= entity.getPetBreed();
        this.petOld= entity.getPetOld();
        this.disease= entity.getDisease();
        this.neutered= entity.getNeutered();
        this.note = entity.getNote();
        this.missed= entity.getMissed();
        this.missCity = entity.getMissCity();
        this.missGu = entity.getMissGu();
        this.missDong = entity.getMissDong();
        this.missDetail = entity.getMissDetail();
        this.missTime = entity.getMissTime();
        this.missDate = entity.getMissDate();
        this.etc = entity.getEtc();
        this.createdDate = entity.getCreatedDate();
    }
}
