package com.idog.FOPPY.dto.dog;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.MissingTime;
import com.idog.FOPPY.domain.PetSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissingDogResponse {
    private Long id;
    private String name;
    private LocalDate birth;
    private PetSex sex;
    private Breed breed;
    private String note;
    private String disease;
    private String imgUrl;

    private String missingCity;
    private String missingGu;
    private String missingDong;
    private String missingDetailedLocation;
    private LocalDate missDate;
    private MissingTime missTime;
    private String etc;

    public static MissingDogResponse of(Dog dog) {
        return new MissingDogResponse(
                dog.getId(),
                dog.getName(),
                dog.getBirth(),
                dog.getSex(),
                dog.getBreed(),
                dog.getNote(),
                dog.getDisease(),
                dog.getImgUrlList().get(0),
                dog.getMissingCity(),
                dog.getMissingGu(),
                dog.getMissingDong(),
                dog.getMissingDetailedLocation(),
                dog.getMissDate(),
                dog.getMissTime(),
                dog.getEtc()
        );
    }
}