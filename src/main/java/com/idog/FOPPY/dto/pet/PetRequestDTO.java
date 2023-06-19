package com.idog.FOPPY.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class PetRequestDTO {

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
    private String etc;

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    @Schema(example = "22:38")
    private LocalTime missTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate missDate;

    @Builder
    public PetRequestDTO(String petName, Boolean petSex, Long petOld, Boolean neutered) {
        this.petName = petName;
        this.petSex = petSex;
        this.petOld = petOld;
        this.neutered = neutered;
    }

    public PetDogs toEntity() {
        return PetDogs.builder()
                .petName(petName)
                .petSex(petSex)
                .petBreed(petBreed)
                .petOld(petOld)
                .disease(disease)
                .neutered(neutered)
                .note(note)
                .missed(missed)
                .missCity(missCity)
                .missGu(missGu)
                .missDong(missDong)
                .missDetail(missDetail)
                .missTime(missTime)
                .missDate(missDate)
                .etc(etc)
                .build();
    }
}
