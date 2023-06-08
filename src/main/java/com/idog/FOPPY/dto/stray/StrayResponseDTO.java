package com.idog.FOPPY.dto.stray;

import com.idog.FOPPY.entity.StrayDogs;
import com.idog.FOPPY.entity.breedState;
import lombok.Builder;

import java.time.LocalDateTime;

public class StrayResponseDTO {
    private Long strayId;
    private Boolean straySex;
    private breedState strayBreed;
    private String findLocation ;
    private LocalDateTime findTime;
    private String description;

    private final LocalDateTime createdDate = LocalDateTime.now();

    @Builder
    public StrayResponseDTO(StrayDogs entity){
        this.strayId = entity.getStrayId();
        this.straySex = entity.getStraySex();
        this.strayBreed = entity.getStrayBreed();
        this.findLocation = entity.getFindLocation();
        this.findTime = entity.getFindTime();
        this.description = entity.getDescription();
    }

}
