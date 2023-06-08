package com.idog.FOPPY.dto.stray;

import com.idog.FOPPY.entity.StrayDogs;
import com.idog.FOPPY.entity.breedState;

import java.time.LocalDateTime;

public class StrayRequestDTO {
    private Long strayId;
    private Boolean straySex;
    private breedState strayBreed;
    private String findLocation ;
    private LocalDateTime findTime;
    private String description;

    private final LocalDateTime createdDate = LocalDateTime.now();

    public StrayDogs toEntity(){
        return StrayDogs.builder()
                .strayId(strayId)
                .straySex(straySex)
                .strayBreed(strayBreed)
                .findLocation(findLocation)
                .findTime(findTime)
                .description(description)
                .build();
    }
}
