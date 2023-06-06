package com.idog.FOPPY.service;

import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.repository.PetDogsRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetDogsService {

    @Autowired
    private final PetDogsRepository petdogsrepository;

    /**
     * 반려견 추가
     */
    @Transactional
    public Long save(final PetRequestDTO params) {

        PetDogs petdogs = petdogsrepository.save(params.toEntity());
        return petdogs.getPetId();
    }

    /**
     * 반려견 정보 조회
     */
    public List<PetResponseDTO> findAll() {
        List<PetDogs> list = petdogsrepository.findAll();
        return list.stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * 반려견 정보 수정
     */
    @Transactional
    public Long update(Long petId, PetRequestDTO params) {
        PetDogs petdogs = petdogsrepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려견이 존재하지 않습니다."));

        petdogs.updatePetDogs(params.toEntity().getPetName(), params.toEntity().getPetSex(),
                params.toEntity().getPetBreed(), params.toEntity().getPetOld(), params.toEntity().getDisease(),
                params.toEntity().getNeutered(),params.toEntity().getModifiedDate());

        return petId;
    }

}



