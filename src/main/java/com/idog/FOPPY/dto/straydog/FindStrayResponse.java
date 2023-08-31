package com.idog.FOPPY.dto.straydog;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.PetSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindStrayResponse {
    private int code;

    private Double similarity;
    private Long user_id;

    private Long dog_id;
    private String name;
    private Boolean isMissing;

    private LocalDate birth;
    private String note;
    private PetSex sex;
    private Breed breed;

    private String imgUrl;

    public FindStrayResponse(Double similarity, Dog dog) {
        this.similarity = similarity;
        this.user_id = dog.getUser().getId();
        this.dog_id = dog.getId();

        this.name = dog.getName();
        this.isMissing = dog.getIsMissing();
        this.birth = dog.getBirth();
        this.note = dog.getNote();
        this.sex = dog.getSex();
        this.breed = dog.getBreed();
        this.imgUrl = dog.getImgUrlList().get(0);
    }
}
