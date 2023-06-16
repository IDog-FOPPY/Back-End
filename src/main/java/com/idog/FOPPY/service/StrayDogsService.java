package com.idog.FOPPY.service;

import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import com.idog.FOPPY.repository.PetDogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StrayDogsService {

    @Autowired
    private final PetDogsRepository petDogsRepository;

    /**
     * 전체 유기견 목록 조회
     */
    public List<PetResponseDTO> findAllStray() {
        List<PetDogs> list = petDogsRepository.findByMissedIsTrue();
        return list.stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * 견종별 소팅
     */
    public List<PetResponseDTO> findByBreed(breedState petBreed) {
        List<PetDogs> list = petDogsRepository.findByPetBreed(petBreed);

        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * 지역별 소팅
     */
    public List<PetResponseDTO> findByLocation(String gu){
        List<PetDogs> list = petDogsRepository.findByMissGu(gu);

        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * 날짜별 소팅
     */
    public List<PetResponseDTO> findByDate(LocalDate date){
        List<PetDogs> list = petDogsRepository.findByMissDate(date);

        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

}
