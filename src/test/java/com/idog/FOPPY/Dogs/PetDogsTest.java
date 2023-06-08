package com.idog.FOPPY.Dogs;

import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import com.idog.FOPPY.repository.PetDogsRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@AutoConfigureTestEntityManager
@SpringBootTest
public class PetDogsTest {

    @Autowired
    private PetDogsRepository petdogsrepository;


    @Test
    //@Transactional
    void save() {

        // 1. 반려견 파라미터 생성
        PetDogs params = PetDogs.builder()
                .petName("일진")
                .petSex(true)
                .petBreed(breedState.포메라니안)
                .petOld(11L)
                .disease("기관지 협착증")
                .neutered(true)
                .build();

        // 2.  저장
        PetDogs pet = petdogsrepository.save(params);

    }

    @Test
    void findAll() {

        // 1. 전체 반려견 수 조회
        long petcount = petdogsrepository.count();
        // 2. 전체 반려견 리스트 조회
        List<PetDogs> petdogs = petdogsrepository.findAll();
    }


    @Test
    void delete() {

        // 1. 반려견 조회
        PetDogs entity1 = petdogsrepository.findById((long) 7).get();
        // 2. 반려견 삭제
        petdogsrepository.delete(entity1);
    }


  
}







