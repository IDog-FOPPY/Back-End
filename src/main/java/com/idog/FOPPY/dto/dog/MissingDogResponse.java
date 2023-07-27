package com.idog.FOPPY.dto.dog;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.MissingTime;
import com.idog.FOPPY.domain.PetSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissingDogResponse {
    private Long id;
    private String name;
    private String birth;
    private PetSex sex;
    private Breed breed;
    private String note;
    private String disease;
    private Boolean neutered;
    private String imgUrl;

    private String missingCity;
    private String missingGu;
    private String missingDong;
    private String missingDetailedLocation;
    private String missDate;
    private String missTime;
    private String etc;

    public static MissingDogResponse of(Dog dog) {
        String birthStr = dog.getBirth() != null ? dog.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        String missDateStr = dog.getMissDate() != null ? dog.getMissDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
        String missTimeStr = dog.getMissTime() != null ? dog.getMissTime().format(DateTimeFormatter.ofPattern("HH:mm")) : null;

        return new MissingDogResponse(
                dog.getId(),
                dog.getName(),
                birthStr,
                dog.getSex(),
                dog.getBreed(),
                dog.getNote(),
                dog.getDisease(),
                dog.getNeutered(),
                dog.getImgUrlList().get(0),
                dog.getMissingCity(),
                dog.getMissingGu(),
                dog.getMissingDong(),
                dog.getMissingDetailedLocation(),
                missDateStr,
                missTimeStr,
                dog.getEtc()
        );
    }
}