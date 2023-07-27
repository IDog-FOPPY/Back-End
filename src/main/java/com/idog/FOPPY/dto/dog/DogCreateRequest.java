package com.idog.FOPPY.dto.dog;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.PetSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DogCreateRequest {
    private String name; // 개 이름
    private LocalDate birth; // 생일
    private PetSex sex;
    private Breed breed; // 견종
    private String note; // 메모
    private String disease;  // 질병
    private Boolean neutered;
}
