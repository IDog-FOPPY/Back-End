package com.idog.FOPPY.dto.dog;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.PetSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogResponse {
    private Long id;
    private String name;
    private Boolean isMissing;

    private String birth;
    private String note;
    private PetSex sex;
    private String disease;
    private Breed breed;
    private Boolean neutered;

    private String imgUrl;

    public DogResponse(Dog dog) {
        this.id = dog.getId();
        this.name = dog.getName();
        this.birth = dog.getBirth() != null ? dog.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        this.sex = dog.getSex();
        this.breed = dog.getBreed();
        this.note = dog.getNote();
        this.disease = dog.getDisease();
        this.neutered = dog.getNeutered();
        this.isMissing = dog.getIsMissing();
        this.imgUrl = dog.getImgUrlList().get(0);
    }
}
