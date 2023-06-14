package com.idog.FOPPY.service;

import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.repository.PetDogsRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetDogsService {

    @Autowired
    private final PetDogsRepository petDogsRepository;

    /**
     * 반려견 등록
     */
    @Transactional
    public Long save(final PetRequestDTO params) {

        PetDogs petDogs = petDogsRepository.save(params.toEntity());
        return petDogs.getPetId();
    }

    /**
     * 전체 반려견 조회
     */
    public List<PetResponseDTO> findAll() {
        List<PetDogs> list = petDogsRepository.findAll();
        return list.stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * ID로 반려견 상세정보 조회
     */
    @Transactional
    public PetResponseDTO findById(final Long petId) {
        PetDogs petDogs = petDogsRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려견 정보입니다."));
        return new PetResponseDTO(petDogs);
    }

    /**
     * 반려견 정보 수정
     */
    @Transactional
    public Long update(Long petId, PetRequestDTO params) {
        PetDogs petDogs = petDogsRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려견 정보입니다."));

        petDogs.updatePetDogs(params.getPetName(), params.getPetSex(), params.getPetBreed(),
                params.getPetOld(), params.getDisease(), params.getNeutered(), params.getMissed(),
                params.getMissLocation_city(), params.getMissLocation_gu(), params.getMissLocation_dong(), params.getMissTime());

        return petId;
    }


    /**
     * 반려견 정보 삭제
     */
    @Transactional
    public void deleteById(Long petId){
        petDogsRepository.deleteById(petId);
    }

    /**
     * 전체 유기견 목록 조회
     */
    public List<PetResponseDTO> findAllStray() {
        List<PetDogs> list = petDogsRepository.findByMissedIsTrue();
        return list.stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

}



