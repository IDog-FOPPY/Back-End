package com.idog.FOPPY.Dogs;

import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import com.idog.FOPPY.repository.PetDogsRepository;
import com.idog.FOPPY.service.PetDogsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AutoConfigureTestEntityManager
@SpringBootTest
public class PetDogsTest {

    @Autowired
    private PetDogsRepository petdogsrepository;


    @Test
    void save() {
        // 1. 반려견 파라미터 생성
        PetDogs params = PetDogs.builder()
                .petName("장미")
                .petSex(true)
                .petBreed(breedState.사모예드)
                .petOld(3L)
                .disease(null)
                .neutered(true)
                .note(null)
                .missed(true)
                .missCity("서울특별시")
                .missGu("동작구")
                .missDong("상도동")
                .missDetail(null)
                .missDate(LocalDate.of(2023,06,14))
                .missTime(LocalTime.of(19,47))
                .etc(null)
                .build();

        // 2.  저장
        PetDogs pet = petdogsrepository.save(params);
    }

    @Test
    void findAll() {

        // 1. 전체 반려견 수 조회
        long petcount = petdogsrepository.count();
        System.out.println("전체 반려견 수 = "+petcount);

        // 2. 전체 반려견 리스트 조회
        List<PetDogs> petdogs = petdogsrepository.findAll();
        System.out.println(petdogs);
    }


    @Test
    void delete() {

        // 1. 반려견 조회
        PetDogs entity1 = petdogsrepository.findById((long) 21).get();


        // 2. 반려견 삭제
        petdogsrepository.delete(entity1);

    }

}







