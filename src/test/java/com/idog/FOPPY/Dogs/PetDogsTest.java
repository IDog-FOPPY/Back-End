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

import java.time.LocalDateTime;
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
                .petName("덕철")
                .petSex(true)
                .petBreed(breedState.말티즈)
                .petOld(5L)
                .disease(null)
                .neutered(true)
                .missed(true)
                .missLocation_city("서울특별시")
                .missLocation_gu("송파구")
                .missLocation_dong("문정동")
                .missTime(LocalDateTime.now())
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
        PetDogs entity1 = petdogsrepository.findById((long) 18).get();
        System.out.println(entity1);
        // 2. 반려견 삭제
        petdogsrepository.delete(entity1);
    }

    @Test
    void update() {

        PetDogs petDogs = petdogsrepository.findById((long)12).get();
        PetDogs params = PetDogs.builder()
                .petId(12L)
                .petName("일진")
                .petSex(false)
                .petBreed(breedState.포메라니안)
                .petOld(11L)
                .disease("기관지 협착증")
                .neutered(true)
                .missed(false)
                .missLocation_city(null)
                .missLocation_gu(null)
                .missLocation_dong(null)
                .missTime(null)
                .build();

        petDogs.updatePetDogs(params.getPetName(), params.getPetSex(), params.getPetBreed(),
                params.getPetOld(), params.getDisease(), params.getNeutered(), params.getMissed(),
                params.getMissLocation_city(), params.getMissLocation_gu(), params.getMissLocation_dong(), params.getMissTime());
    }
}







